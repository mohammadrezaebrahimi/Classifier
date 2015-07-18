package resolute.logic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.tools.XMLException;


/**
 * @author Ebrahimi
 * @version 1.0
 */
public class Identification {

	public LearningProcess m_LearningProcess;	
	public ApplicationProcess m_ApplicationProcess;

	public void initialize(){
		RapidMiner.setExecutionMode(ExecutionMode.COMMAND_LINE);
		RapidMiner.init();
	}
	
	public Map<String, Double> runTrain(ProcessManager.mode m, String positivePath, String negativePath){
		Map<String, Double> res= new HashMap<String, Double>();
		try {
			LearningProcess learningProcess = new LearningProcess(positivePath, negativePath);
			res = learningProcess.run(m);
		} catch (IOException | XMLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public Map<String, Double> runTest(ProcessManager.mode m, String testPath){
		Map<String, Double> res= new HashMap<String, Double>();
		try {
			ApplicationProcess applicationProcess = new ApplicationProcess(testPath);
			res = applicationProcess.run(m);
		} catch (IOException | XMLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
}