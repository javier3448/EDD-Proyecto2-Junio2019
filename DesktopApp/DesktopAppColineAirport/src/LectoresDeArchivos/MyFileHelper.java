/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LectoresDeArchivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author javier
 */
public class MyFileHelper {
    public static void writeFile(SimpleList<String> lines, String dir){
        try (FileWriter writer = new FileWriter(dir);
            BufferedWriter bw = new BufferedWriter(writer)) {   
            if (lines == null) {
                return;
            }
            SimpleList<String>.Node tmp = lines.head;
            while(tmp != null){
		bw.write(tmp.data);
                bw.newLine();
                tmp = tmp.next;
            }

	} catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            
	}
    }
    
    public static void writeFile(String[] lines, String dir){
        try (FileWriter writer = new FileWriter(dir);
            BufferedWriter bw = new BufferedWriter(writer)) {   

            
            for (int i = 0; i < lines.length; i++) {
                bw.write(lines[i]);
                bw.newLine();
            }

	} catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            
	}
    }
    
    //Retorna null si no se pudo abrir el archivo
    public static SimpleList<String> getLinesInFile(String dir){
        SimpleList<String> lines = new SimpleList<String>();
        String line;
        try {
            FileReader fileReader = new FileReader(dir);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }   

            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            return null;
        }
        catch(IOException ex) {
            return null;
        }
        return lines;
    }
}
