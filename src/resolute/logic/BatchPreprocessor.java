package resolute.logic;
/**
 * @author Ebrahimi
 * @version 1.0
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class BatchPreprocessor {

	String path="";
	
	public BatchPreprocessor(String path) throws UnsupportedEncodingException, FileNotFoundException, IOException {		
		this.path = path;
		String tikaPath= this.getClass().getClassLoader().getResource("tika-app-1.8.jar").getPath().substring(1).replace('/', '\\');
		//Create train.bat in the path
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(path + "\\train.bat"), "utf-8"))) {
	   writer.write("copy " + tikaPath + " " + path + "\nmd "+ path +"\\texts\nfor /r %%f in (*.docx) DO java -jar tika-app-1.8.jar -t \"%%f\" > " + path + "\\texts\\\"%%~nf\".txt");
	}
		
		
	}

	private void executeCMD() throws Exception{
		
		Process process =  Runtime.getRuntime().exec("cmd /c train.bat", null, new File(path));
			    
	    printMsg("command" + " stdout:", process.getInputStream());
	    printMsg("command" + " stderr:", process.getErrorStream());
	    process.waitFor();
	    if(process.exitValue() != 0)
	        System.out.println("command" + "exited with value " + process.exitValue());
	}

	private void printMsg(String name, InputStream ins) throws Exception {
	    String line = null;
	    BufferedReader in = new BufferedReader(new InputStreamReader(ins));
	    while((line = in.readLine()) != null){
	        System.out.println(name + " " + line);
	    }
	}
	
	public void run() {
		try {
	        executeCMD();	        		
	    }catch(Exception e){
	        e.printStackTrace();
	    }

	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		BatchPreprocessor bp = new BatchPreprocessor("E:\\University\\RA\\MAXSQ");
		bp.run();
	}
	
//	public static void main(final String[] args) throws IOException,SAXException, TikaException {	      
//		
//		  //detecting the file type
//	      BodyContentHandler handler = new BodyContentHandler();
//	      Metadata metadata = new Metadata();
//	      FileInputStream inputstream = new FileInputStream(new File("example.txt"));
//	      ParseContext pcontext=new ParseContext();
//	      
//	      //Text document parser
//	      TXTParser  TexTParser = new TXTParser();
//	      TexTParser.parse(inputstream, handler, metadata,pcontext);
//	      System.out.println("Contents of the document:" + handler.toString());
//	      System.out.println("Metadata of the document:");
//	      String[] metadataNames = metadata.names();
//	}
	
}
