/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LectoresDeArchivos;

import DataStructures.SimpleLists.SimpleList;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author javier
 */
public class GraphvizHelper {
    private static final String DIR_DOT = "zGraphviz/DOT_files/";
    private static final String DIR_IMG = "zGraphviz/Images/";
    /**
     * genera un archivo dot con nombre name.dot en zGraphviz/DOT_files y escribe lines en el, lo compila y luego guarda la imagen en zGraphviz/Images
     * @param lines
     * @param name 
     */
    public static void draw(SimpleList<String> lines, String name){
        String dirDot = DIR_DOT + name + ".dot";
        String dirPng = DIR_IMG + name + ".png";
        MyFileHelper.writeFile(lines, dirDot);
        compileDot(dirDot, dirPng);
    }
    
    /**
     * genera un archivo dot con nombre name.dot en zGraphviz/DOT_files y escribe lines en el, lo compila y luego guarda la imagen en zGraphviz/Images
     * si open es verdadero abre la imagen
     * @param lines
     * @param name 
     * @param open 
     */
    public static void draw(SimpleList<String> lines, String name, boolean open){
        draw(lines, name);
        if (open) {
            try {
                Runtime.getRuntime().exec("nohup display " + DIR_IMG + name +  ".png &");
            } catch (IOException ex) {
                Logger.getLogger(GraphvizHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * compila el archivo y guarda su resultado en la direccionPng
     * @param direccionDot
     * @param direccionPng 
     */
    public static void compileDot(String direccionDot, String direccionPng){
        try
        {       
            ProcessBuilder pbuilder;
            pbuilder = new ProcessBuilder( "dot", "-Tpng", "-o", direccionPng, direccionDot );
            pbuilder.redirectErrorStream( true );
            //Ejecuta el proceso
            pbuilder.start();

        } catch (Exception e) { e.printStackTrace(); }
    }
    
    
    public static void main(String args[]){
        SimpleList<String> lines = new SimpleList<String>();
        lines.add("digraph prrr{");
        lines.add("Javier");
        lines.add("}");
        draw(lines, "Prueba0", true);
    }
}
