package resolute;

/**
 * @author Ebrahimi
 * @version 1.0
 */
public class OfflineDataReader extends DataReader {

	public LocalFileSystem m_LocalFileSystem;

	public OfflineDataReader(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

}