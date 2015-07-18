package resolute.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.swing.*;

import resolute.logic.ProcessManager;

/**
 * @author Ebrahimi
 * @version 1.0 * 
 */
public class MainForm extends JFrame {
	
	JTabbedPane tabbedPane = new JTabbedPane();
	JPanelWithBackground trainPanel = new JPanelWithBackground("bg.jpg");
	JPanelWithBackground testPanel = new JPanelWithBackground("bg.jpg");
	JPanelWithBackground sentimentPanel = new JPanelWithBackground("bg.jpg");
	JPanelWithBackground onlinePanel = new JPanelWithBackground("bg.jpg");			
	JLabel trainDescriptionLabel = new JLabel();
	JLabel trainPositiveBrowseLabel = new JLabel();
	JLabel trainNegativeBrowseLabel = new JLabel();
	JLabel trainResultLabel = new JLabel();	
	JTextArea trainResultTextArea = new JTextArea("Accuracy:\nPrecision:\nRecal:\nF-measure:\nAUC:\n");
	FileChooser trainPositiveBrowsePanel = new FileChooser();
	FileChooser trainNegativeBrowsePanel = new FileChooser();
	JButton trainNegativeBrowseButton = new JButton();
	ButtonGroup trainRadioGroup = new ButtonGroup();
	ButtonGroup testRadioGroup = new ButtonGroup();
	JRadioButton trainRegularClassificationRadio = new JRadioButton("regular mode                         ");
	JRadioButton trainAnomalyDetectionRadio = new JRadioButton("anomaly detection mode    ");
	JRadioButton testRegularClassificationRadio = new JRadioButton("regular mode                         ");
	JRadioButton testAnomalyDetectionRadio = new JRadioButton("anomaly detection mode    ");
	JButton trainButton = new JButton();
	
	JLabel testDescriptionLabel = new JLabel();
	JLabel testBrowseLabel = new JLabel();
	FileChooser testBrowsePanel = new FileChooser();
	JTextArea testResultTextArea = new JTextArea("Log File Name........................................,Probability of being Predatory",50,300);
	JScrollPane testScrollPane = new JScrollPane(testResultTextArea);
	JButton testButton = new JButton();
	
	boolean isAnomalyMode = false;
	boolean isTestAnomalyMode = false;
	
	//constructor	
	public MainForm() throws IOException{
		
		///////////////////trainPanel
		trainPanel.add(Box.createRigidArea(new Dimension(10,20)));
		trainPanel.setLayout(new BoxLayout(trainPanel, BoxLayout.PAGE_AXIS));
		
		trainDescriptionLabel.setText("Here you can select the corresponding folders for \"predatory\" and \"suspicious but legal\" samples to train the model.");
		trainDescriptionLabel.setFont(new Font("Arial Bold", Font.ITALIC, 16));
		trainDescriptionLabel.setForeground(Color.WHITE);
		trainPanel.add(trainDescriptionLabel);
		trainPanel.add(Box.createRigidArea(new Dimension(20,60)));
		
		trainPositiveBrowseLabel.setText("Please select the folder containing predatory documents:");
		trainPositiveBrowseLabel.setFont(new Font("Arial Bold", Font.PLAIN, 14));
		trainPositiveBrowseLabel.setForeground(Color.WHITE);
		trainPanel.add(trainPositiveBrowseLabel);
		trainPanel.add(trainPositiveBrowsePanel);
		trainPanel.add(Box.createRigidArea(new Dimension(10,40)));
		
		trainNegativeBrowseLabel.setText("Please select the folder containing legal suspicious documents:");
		trainNegativeBrowseLabel.setFont(new Font("Arial Bold", Font.PLAIN, 14));
		trainNegativeBrowseLabel.setForeground(Color.WHITE);
		trainNegativeBrowseButton.setText("browse");
		trainPanel.add(trainNegativeBrowseLabel);
		//trainPanel.add(trainBrowsepanel2);
		trainPanel.add(trainNegativeBrowsePanel);
		trainPanel.add(Box.createRigidArea(new Dimension(10,40)));
		
		trainRadioGroup.add(trainRegularClassificationRadio);
		trainRadioGroup.add(trainAnomalyDetectionRadio);
		trainPanel.add(trainRegularClassificationRadio);
		trainPanel.add(trainAnomalyDetectionRadio);
		trainPanel.add(Box.createRigidArea(new Dimension(10,10)));
		
		trainRegularClassificationRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JRadioButton source = (JRadioButton)e.getSource();
				if (source.isSelected()){
					isAnomalyMode = false;
				}
			}
		});
		trainAnomalyDetectionRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JRadioButton source = (JRadioButton)e.getSource();
				if (source.isSelected()){
					isAnomalyMode = true;
				}
			}
		});
		
		trainPanel.add(trainButton);
		trainButton.setText("          Start training...          ");
		trainButton.addActionListener(new TrainEvent());			
		trainPanel.add(Box.createRigidArea(new Dimension(10,40)));
		
		trainResultLabel.setText("Training results:");
		trainResultLabel.setFont(new Font("Arial Bold", Font.PLAIN, 14));
		trainResultLabel.setForeground(Color.WHITE);
		trainPanel.add(Box.createRigidArea(new Dimension(200,100)));
		trainPanel.add(trainResultLabel);
		trainResultTextArea.setEditable(false);
		trainResultTextArea.setFont(new Font("Arial Bold", Font.PLAIN, 12));
		trainPanel.add(trainResultTextArea);
		trainPanel.add(Box.createRigidArea(new Dimension(120,100)));
		
		///////////////////////TestPanel
		testPanel.add(Box.createRigidArea(new Dimension(10,20)));
		testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.PAGE_AXIS));
		
		testDescriptionLabel.setText("Here you can select chat-logs and identify the probability of being predatory for each.");
		testDescriptionLabel.setFont(new Font("Arial Bold", Font.ITALIC, 16));
		testDescriptionLabel.setForeground(Color.WHITE);
		testPanel.add(testDescriptionLabel);
		testPanel.add(Box.createRigidArea(new Dimension(20,100)));
		
		testBrowseLabel.setText("Please select the folder containing predatory documents:");
		testBrowseLabel.setFont(new Font("Arial Bold", Font.PLAIN, 14));
		testBrowseLabel.setForeground(Color.WHITE);
		testPanel.add(testBrowseLabel);
		testPanel.add(testBrowsePanel);
		testPanel.add(Box.createRigidArea(new Dimension(10,40)));
		
		testRadioGroup.add(testRegularClassificationRadio);
		testRadioGroup.add(testAnomalyDetectionRadio);
		testPanel.add(testRegularClassificationRadio);
		testPanel.add(testAnomalyDetectionRadio);
		testPanel.add(Box.createRigidArea(new Dimension(10,10)));
		
		testRegularClassificationRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JRadioButton source = (JRadioButton)e.getSource();
				if (source.isSelected()){
					isTestAnomalyMode = false;
				}
			}
		});
		testAnomalyDetectionRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JRadioButton source = (JRadioButton)e.getSource();
				if (source.isSelected()){
					isTestAnomalyMode = true;
				}
			}
		});
		
		testPanel.add(testButton);
		testButton.setText("              Identify...              ");		
		testButton.addActionListener(new TestEvent());
		testPanel.add(Box.createRigidArea(new Dimension(10,40)));
		
		testResultTextArea.setEditable(false);
		testResultTextArea.setFont(new Font("Arial Bold", Font.PLAIN, 12));
		testPanel.add(testScrollPane);
		testPanel.add(Box.createRigidArea(new Dimension(120,100)));		
		
		//getContentPane().add(new JPanelWithBackground("bg.jpg"));
		tabbedPane.add("Model Training", trainPanel);
		tabbedPane.add("Predator Detection", testPanel);
		tabbedPane.add("Sentiment Extraction", sentimentPanel);
		tabbedPane.add("Online Mode", onlinePanel);
		add(tabbedPane);
	}
	
	public static void main(String[] args) throws IOException {			
		MainForm mForm = new MainForm();
		mForm.setTitle("Resolute Project");
		mForm.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//mForm.pack();
		mForm.setSize(1030, 800);
		mForm.setVisible(true);
	}
	
	public class TrainEvent implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			trainResultTextArea.setText("");
			if (trainPositiveBrowsePanel.getSelectedPath()!=null && trainNegativeBrowsePanel.getSelectedPath()!=null)
			{
				String trainPositivepath = trainPositiveBrowsePanel.getSelectedPath();
				String trainNegativePath = trainNegativeBrowsePanel.getSelectedPath();
				ProcessManager pm = new ProcessManager();
				if (!isAnomalyMode){
					Map<String, Double> result = pm.run(ProcessManager.mode.train_regular, trainPositivepath, trainNegativePath);
					trainResultTextArea.setText("Accuracy: "+ result.get("accuracy")+ "\nFalse Alarm Rate: " + result.get("precision")+ "\nLeakage Rate: " + result.get("recall") + "\nF-measure: " + result.get("f_measure") + "\nAUC: " + result.get("AUC"));
				} else
				{
					Map<String, Double> result = pm.run(ProcessManager.mode.train_anomaly, trainPositivepath, trainNegativePath);
					trainResultTextArea.setText("Anomaly model has been trained successfully!");
				}					
			}
			else
				///TODO: Shoot Error in a pop up window
			{}
		}
		
	}
	
	public class TestEvent implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			testResultTextArea.setText("");
			if (testBrowsePanel.getSelectedPath()!=null)
			{
				String testPath = testBrowsePanel.getSelectedPath();
				ProcessManager pm = new ProcessManager();
				if (!isTestAnomalyMode){
					Map<String, Double> result = pm.run(ProcessManager.mode.application_regular, testPath);
					Iterator<Map.Entry<String, Double>> it = result.entrySet().iterator();
					while (it.hasNext()){
						Map.Entry<String, Double> pair = (Map.Entry<String, Double>)it.next();
						testResultTextArea.append("LOG FILE NAME:"+ pair.getKey()+ "........................probability: " + pair.getValue()+ "\n");
					}
				}
				else
				{
					Map<String, Double> result = pm.run(ProcessManager.mode.application_anomaly, testPath);
					Iterator<Map.Entry<String, Double>> it = result.entrySet().iterator();
					String status ="";
					while (it.hasNext()){
						Map.Entry<String, Double> pair = (Map.Entry<String, Double>)it.next();
						status = pair.getValue()==0.0? "normal":"anomalous";
						testResultTextArea.append("LOG FILE NAME:"+ pair.getKey()+ "........................status: " + status + "\n");
					}
				}		
			}
			else
				///TODO: Shoot Error in a pop up window
			{}
		}
		
	}

}