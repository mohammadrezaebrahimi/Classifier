package resolute.logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.rapidminer.operator.Model;
import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.performance.PerformanceVector;
import com.rapidminer.parameter.ParameterType;
import com.rapidminer.parameter.ParameterTypeList;
import com.rapidminer.parameter.Parameters;
import com.rapidminer.tools.XMLException;
import com.rapidminer.tools.parameter.Parameter;
import com.rapidminer.Process;

/**
 * @author Ebrahimi
 * @version 1.0
 */
public class LearningProcess {
	
	String positivePath;
	String negativePath;
	Process pr;
	Map<String, Double> result= new HashMap<String, Double>();

	public LearningProcess(String positivePath, String negativePath) throws IOException, XMLException{		
		
		this.positivePath = positivePath;
		this.negativePath = negativePath;				
		//Do the batch pre-processing on files in the training folder
		BatchPreprocessor batchProcessorPos = new BatchPreprocessor(positivePath);
		BatchPreprocessor batchProcessorNeg = new BatchPreprocessor(negativePath);
		batchProcessorPos.run();
		batchProcessorNeg.run();
	}
	
	public Map<String, Double> run(ProcessManager.mode m) {
		
		try {			
			if (m.toString()=="train_regular")
				pr = new Process(new File(getClass().getResource("/trainSVM.xml").getFile()));
			else
				pr = new Process(new File(getClass().getResource("/trainAnomaly.xml").getFile()));
			Operator processDocuments =  pr.getOperator("Process Documents from Files");
			//set the positive and negative paths in parameters
			List<String[]> innerParamList = new LinkedList<String[]>();
			innerParamList.add(new String[]{"p", positivePath+"\\texts"});
			innerParamList.add(new String[]{"np", negativePath+"\\texts"});
			processDocuments.setListParameter("text_directories", innerParamList);			

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLException e) {
			e.printStackTrace();
		}

		try {
			pr.run();
			
			// get process results
			if (m.toString()=="train_regular"){ // Otherwise the result will be returned as null.
				Operator crossValidationOP = pr.getOperator("Validation");
				Model resultModel = crossValidationOP.getOutputPorts().getPortByName("model").getData(Model.class);
				PerformanceVector performance = crossValidationOP.getOutputPorts().getPortByName("averagable 1").getData(PerformanceVector.class);

				result.put("accuracy", performance.getAveragable("accuracy").getAverage());
				result.put("precision", performance.getAveragable("precision").getAverage());
				result.put("recall", performance.getAveragable("recall").getAverage());
				result.put("AUC", performance.getAveragable("AUC").getAverage());
				result.put("f_measure", performance.getAveragable("f_measure").getAverage());
			}			
		} catch (OperatorException e) {
			e.printStackTrace();
		}
		return result;
	}

}