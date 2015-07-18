package resolute.ui;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.util.*;


public class FileChooser extends JPanel
   implements ActionListener {
   JButton btn;
   JTextField addressField;
   JFileChooser chooser;
   String choosertitle;
   String selectedPath;
   
  public String getSelectedPath() {
	return selectedPath;
}

public FileChooser() {
    setLayout(new FlowLayout(FlowLayout.LEFT));
    addressField = new JTextField(60);
    btn = new JButton("browse");	
    btn.addActionListener(this);
    add(addressField);
    add(btn);
    setSize(20, 800);
   }

  public void actionPerformed(ActionEvent e) {    
        
    chooser = new JFileChooser(); 
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle(choosertitle);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    // disable the "All files" option.
    chooser.setAcceptAllFileFilterUsed(false);
    //    
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
      System.out.println("getCurrentDirectory(): " 
         +  chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : " 
         +  chooser.getSelectedFile());
      
    //Fill the textField
    addressField.setText(chooser.getSelectedFile().toString());
    selectedPath = chooser.getSelectedFile().toString();
    }
    else {
      System.out.println("No Selection");
      addressField.setText("No Selection");
      }    
     }
   
//  public Dimension getPreferredSize(){
//    return new Dimension(400, 200);
//    }
    
  public static void main(String s[]) {
    JFrame frame = new JFrame("");
    FileChooser panel = new FileChooser();
    frame.addWindowListener(
      new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          System.exit(0);
          }
        }
      );
    frame.getContentPane().add(panel,"Center");
    //frame.setSize(panel.getPreferredSize());
    frame.pack();
    frame.setVisible(true);
    }
}
