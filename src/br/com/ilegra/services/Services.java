package br.com.ilegra.services;

import java.util.ArrayList;

/**
 *
 * @author henrique
 */
public interface Services {
    
     public ArrayList<String> processFile(ArrayList linesOfFile);
     public ArrayList readFile(String filename);
     public void writeFile(String nameOfFile, ArrayList<String> dataFile);
     
    
}
