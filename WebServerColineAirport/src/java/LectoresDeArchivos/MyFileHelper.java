/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LectoresDeArchivos;

import DataStructures.SimpleLists.SimpleList;
import java.io.BufferedWriter;
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
}
