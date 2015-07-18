package resolute.logic;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.rapidminer.Process;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.tools.XMLException;

/**
 * @author Ebrahimi
 * @version 1.0
 */
public class ApplicationProcess {

	
	String path;
	Process pr;
	Map<String, Double> result= new HashMap<String, Double>();

	public ApplicationProcess(String path) throws IOException, XMLException{		
		
		this.path = path;
		//Do the batch pre-processing on files in the training folder
		BatchPreprocessor batchProcessorPos = new BatchPreprocessor(path);
		batchProcessorPos.run();
	}	
	
public Map<String, Double> run(ProcessManager.mode m) {
		
		try {
			if (m.toString()=="application_regular")
				pr = new Process(new File(getClass().getResource("/test.xml").getFile()));
			else
				pr = new Process(new File(getClass().getResource("/testAnomaly.xml").getFile()));
			Operator processDocuments =  pr.getOperator("Process Documents from Files");
			//set the path in parameters
			List<String[]> innerParamList = new LinkedList<String[]>();
			innerParamList.add(new String[]{"dummy", path+"\\texts"});
			processDocuments.setListParameter("text_directories", innerParamList);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLException e) {
			e.printStackTrace();
		}

		try {
			pr.run();
			
			// get process results
			Operator applyOP = pr.getOperator("Apply Model");
			ExampleSet labeledES = applyOP.getOutputPorts().getPortByName("labelled data").getData(ExampleSet.class);

			if (m.toString()=="application_regular"){
			for(Example e : labeledES)
				result.put(e.getNominalValue(e.getAttributes().get("metadata_file")), e.getNumericalValue(e.getAttributes().get("confidence(p)")));
			}
			else // anomaly
			{
				for(Example e : labeledES)
				{
					double anomalyResult = 0; // 0 represents normal and 1 represents anomalous
					anomalyResult = (e.getNominalValue(e.getAttributes().get("prediction(label)"))=="outside")? 0:1;
					result.put(e.getNominalValue(e.getAttributes().get("metadata_file")), anomalyResult);
				}
			}
			
		} catch (OperatorException e) {
			e.printStackTrace();
		}
		return result;
	}

}