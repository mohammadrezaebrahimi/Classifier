package resolute.logic;

import java.util.Map;

import resolute.DataReader;
import resolute.SentimentExtraction;
import resolute.SpaceModel;
import resolute.UIForm;

/**
 * @author Ebrahimi
 * @version 1.0
 */
public class ProcessManager {

	public DataReader m_DataReader;
	public Identification m_Identification;
	public SentimentExtraction m_SentimentExtraction;
	public SpaceModel m_SpaceModel;
	public UIForm m_UIForm;
	
	public static enum mode {
		train_regular,
		application_regular,
		train_anomaly,
		application_anomaly,
		sentiment
	}

	public ProcessManager(){
		Identification identification = new Identification();
		identification.initialize();				
	}
	
	public Map<String, Double> run(mode m, String positivePath, String negativePath){		
		
		Identification identification = new Identification();
		return identification.runTrain(m, positivePath, negativePath);
		
	}
	
	public Map<String, Double> run(mode m, String testPath){		
		
		Identification identification = new Identification();
		return identification.runTest(m,testPath);
	}	
	
	public String run(mode m){		
		
		return null;
	}
	
	
	public static void main(String[] args) {
//		ProcessManager processManagaer = new ProcessManager();
//		Identification.run();
	}
	
	
	
}