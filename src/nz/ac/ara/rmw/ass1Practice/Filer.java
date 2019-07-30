package nz.ac.ara.rmw.ass1Practice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Filer implements IFiler {

	@Override
	public void save(String fileName, String[][] level) {
		BufferedWriter bw = null;
	      try {
	    	  
	    	  int numRows = level.length;
	    	  int numCols = level[0].length;
	    	  String fileContent = "";
	    	  
	    	  // calc string from array
	    	  for(int x = 0; x < numRows; x++) {
	    		  for(int y = 0; y < numCols; y++) {
	    			  fileContent += level[x][y];
	  		      }
	    		  fileContent += System.lineSeparator();
      		  }

	    	  File file = new File(fileName);

		  if (!file.exists()) {
		     file.createNewFile();
		  }

		  FileWriter fw = new FileWriter(file);
		  bw = new BufferedWriter(fw);
		  bw.write(fileContent);
	      System.out.println("File written Successfully");

	      } catch (IOException ioe) {
		   ioe.printStackTrace();
		}
		finally
		{ 
		   try{
		      if(bw!=null)
			 bw.close();
		   }catch(Exception ex){
		       System.out.println("Error in closing the BufferedWriter"+ex);
		    }
		}
	}

	@Override
	public String[][] load(String fileName) {
        // This will reference one line at a time
	    try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        int col = line.length();
	        while (line != null) {
            	sb.append(line);
	            line = br.readLine();
	        }
	        
	        int row = sb.length() / col;
	        String level = sb.toString();
	        
	        String[][] map = new String[row][col];
	        int c = 0;
	        for (int x = 0; x < row; x++) {
	            for (int y = 0; y < col; y++) {
            		map[x][y] = Character.toString(level.charAt(c));
            		c++;
	            }
	        }
	        
        	br.close();
        	return map;
	    } 
	    catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");    
            return null;
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'"); 
            return null;
        }          
    }

}
