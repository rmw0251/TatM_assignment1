package nz.ac.ara.rmw.ass1Practice;

import java.io.FileNotFoundException;

public interface IFiler {

	public void save(String fileName, String[][] level);
	public String[][] load(String fileName) throws FileNotFoundException;
	
}
