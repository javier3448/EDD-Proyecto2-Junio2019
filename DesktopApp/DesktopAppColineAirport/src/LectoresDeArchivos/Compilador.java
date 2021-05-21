/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LectoresDeArchivos;

import desktopappcolineairport.DesktopAppColineAirport;

/**
 * Esta clase no puede ser utilizada por varios thread a la vez
 * @author javier
 */
public class Compilador {
    //private static int posActual = 0;
    
    /**
     * FALTA PROBARLO!!!!!!!!!!!!!!!!!!!!!! 
     * @param dir
     * @return 
     */
    public static String compilarArchivoDestinos(String dir){
        String reporte = "";
        SimpleList<String> lines = MyFileHelper.getLinesInFile(dir);
        if (lines == null) {
            return "Error al tratar de abrir el archivo con direccion: " + dir;
        }
        int nLinea = 0;
        SimpleList<String>.Node node = lines.head;
        String linea;
        while(node != null){
            linea = node.data;
            String[] tokens = linea.split(",");
            if (tokens.length<=0) {
                node = node.next;
                nLinea++;
                continue;
            }
            if (tokens.length > 2) {
                reporte += "Error en linea: " + nLinea + ". Se recivieron mas argumentos de lo necesario. Se ha saltado la linea\n";
                node = node.next;
                nLinea++;
                continue;
            }
            if (tokens.length < 2) {
                reporte += "Error en linea: " + nLinea + ". Se recivieron menos argumentos de lo necesario. Se ha saltado la linea\n";
                node = node.next;
                nLinea++;
                continue;
            }
            String codigo = tokens[0].replaceAll(" |\n|\r|\t", "");
            if (!isInt(codigo)) {
                reporte += "Error en linea: " + nLinea + ". Declaracion de codigo: " + codigo + " no es un int valido. Se ha saltado la linea\n";
                node = node.next;
                nLinea++;
                continue;
            }
            String nombre = tokens[1].replaceAll("\n|\r|\t", "");
            if (!isIdentifier(nombre)) {
                reporte += "Error en linea: " + nLinea + ". Declaracion de nombre: " + nombre + " no es un identificador valido. Se ha saltado la linea\n";
                node = node.next;
                nLinea++;
                continue;
            }
            //Trata de hacer la insercion en el web service
            String resultWebService = DesktopAppColineAirport.addDestino(Integer.valueOf(codigo), nombre);
            if (resultWebService != null) {
                reporte += "Mensaje de web service: " + resultWebService + "\n";
            }
            node = node.next;
            nLinea++;
        }
        
        return reporte;
    }
    
    public static boolean isIdentifier(String str){
        return str.matches("[_a-zA-Z| ][_a-zA-Z0-9| ]*");
    }
    
    public static boolean isInt(String str) {
        return str.matches("[0-9]+");  //match a number with optional '-' and decimal.
    }
    
    public static boolean isDouble(String str) {
        return str.matches("-?[0-9]+(\\.[0-9]+)?");  //match a number with optional '-' and decimal.
    }
    
    /**
     * verifica que el str empieze con < y termine con > y que tenga un identificador en medio
     * @param str
     * @return 
     */
    public static boolean isPiloto(String str){
        if (str.charAt(0) != '<' || str.charAt(str.length() - 1) != '>') {
            return false;
        }
        str = str.replaceAll("<|>", "");
        if (str.length() == 0) {
            return false;
        }
        if (!isIdentifier(str)) {
            return false;
        }
        return true;
    }
    
    /**
     * Retorna el siguiente caracter que no sea uno de los ignorados
     * Si se terminaron las lineas retorna null char
     * @param node
     * @return 
     */
//    private static char nextChar(SimpleList<String>.Node node){
//        posActual++;
//        String s;
//        char c;
//        while(node != null){
//            s = node.data;
//            while(posActual < s.length()){
//                c = s.charAt(posActual);
//                if (c != ' ' && c != '\n' && c != '\t' && c != '\r' && c != '\r') {
//                    return c;
//                }
//                posActual++;
//            }
//            node = node.next;
//            posActual = 0;
//        }
//        return '\u0000';
//    }
    
    public static String compilarArchivoRutas(String dir){
        String reporte = "";
        SimpleList<String> lines = MyFileHelper.getLinesInFile(dir);
        if (lines == null) {
            return "Error al tratar de abrir el archivo con direccion: " + dir;
        }
        int nLinea = 0;
        SimpleList<String>.Node node = lines.head;
        String linea;
        while(node != null){
            linea = node.data;
            String[] tokens = linea.split(",");
            if (tokens.length<=0) {
                node = node.next;
                nLinea++;
                continue;
            }
            if (tokens.length > 5) {
                reporte += "Error en linea: " + nLinea + ". Se recivieron mas argumentos de lo necesario. Se ha saltado la linea\n";
                node = node.next;
                nLinea++;
                continue;
            }
            if (tokens.length < 5) {
                reporte += "Error en linea: " + nLinea + ". Se recivieron menos argumentos de lo necesario. Se ha saltado la linea\n";
                node = node.next;
                nLinea++;
                continue;
            }
            String codigoA = tokens[0].replaceAll(" |\n|\r|\t", "");
            if (!isInt(codigoA)) {
                reporte += "Error en linea: " + nLinea + ". Declaracion de codigoA: " + codigoA + " no es un int valido. Se ha saltado la linea\n";
                node = node.next;
                nLinea++;
                continue;
            }
            String codigoB = tokens[1].replaceAll(" |\n|\r|\t", "");
            if (!isInt(codigoB)) {
                reporte += "Error en linea: " + nLinea + ". Declaracion de codigoB: " + codigoB + " no es un int valido. Se ha saltado la linea\n";
                node = node.next;
                nLinea++;
                continue;
            }
            String costo = tokens[2].replaceAll(" |\n|\r|\t", "");
            if (!isDouble(costo)) {
                reporte += "Error en linea: " + nLinea + ". Declaracion de costo: " + costo + " no es un double valido. Se ha saltado la linea\n";
                node = node.next;
                nLinea++;
                continue;
            }
            String tiempo = tokens[3].replaceAll(" |\n|\r|\t", "");
            if (!isInt(tiempo)) {
                reporte += "Error en linea: " + nLinea + ". Declaracion de tiempo: " + tiempo + " no es un double valido. Se ha saltado la linea\n";
                node = node.next;
                nLinea++;
                continue;
            }
            String piloto = tokens[4].replaceAll(" |\n|\r|\t", "");
            if (!isPiloto(piloto)) {
                reporte += "Error en linea: " + nLinea + ". Declaracion del piloto: " + piloto + " no es un piloto valido. Se ha saltado la linea\n";
                node = node.next;
                nLinea++;
                continue;
            }
            piloto = piloto.replaceAll("<|>", "");
            //Trata de hacer la insercion en el web service
            String resultWebService = DesktopAppColineAirport.addRuta(Integer.valueOf(codigoA), Integer.valueOf(codigoB), 
                                                                      Double.valueOf(costo), Integer.valueOf(tiempo), piloto);
            if (resultWebService != null) {
                reporte += "Mensaje de web service: " + resultWebService + "\n";
            }
            node = node.next;
            nLinea++;
        }
        return reporte;
    }
    
    /**
     * POR AHORA REVIZA QUE VENGA ID RESERVACION PERO NO HACE NADA CON ELLA. LO MISMO CON EL COSTO Y EL TIEMPO
     * @param dir
     * @return 
     */
    public static String compilarArchivoReservas(String dir){
        String reporte = "";
        SimpleList<String> lines = MyFileHelper.getLinesInFile(dir);
        if (lines == null) {
            return "Error al tratar de abrir el archivo con direccion: " + dir;
        }
        int nLinea = 0;
        SimpleList<String>.Node node = lines.head;
        String linea;
        while(node != null){
            linea = node.data;
            String[] tokens = linea.split(",");
            if (tokens.length<=0) {
                node = node.next;
                nLinea++;
                continue;
            }
            if (tokens.length < 6) {
                reporte += "Error en linea: " + nLinea + ". Se recivieron menos argumentos de lo necesario (6 minimo). Se ha saltado la linea\n";
                node = node.next;
                nLinea++;
                continue;
            }
            String cliente = tokens[0].replaceAll("\n|\r|\t", "");
            if (!isIdentifier(cliente)) {
                reporte += "Error en linea: " + nLinea + ". Declaracion de cliente: " + cliente + " no es un identificador valido. Se ha saltado la linea\n";
                node = node.next;
                nLinea++;
                continue;
            }
            String idReserva = tokens[1].replaceAll(" |\n|\r|\t", "");
            if (!isInt(idReserva)) {
                reporte += "Error en linea: " + nLinea + ". Declaracion de idReserva: " + idReserva + " no es un int valido. Se ha saltado la linea\n";
                node = node.next;
                nLinea++;
                continue;
            }
            String costo = tokens[2].replaceAll(" |\n|\r|\t", "");
            if (!isDouble(costo)) {
                reporte += "Error en linea: " + nLinea + ". Declaracion de costo: " + costo + " no es un double valido. Se ha saltado la linea\n";
                node = node.next;
                nLinea++;
                continue;
            }
            String tiempo = tokens[3].replaceAll(" |\n|\r|\t", "");
            if (!isInt(tiempo)) {
                reporte += "Error en linea: " + nLinea + ". Declaracion de tiempo: " + tiempo + " no es un double valido. Se ha saltado la linea\n";
                node = node.next;
                nLinea++;
                continue;
            }
            String destinos = "";
            boolean errorEnDestinos = false;
            for (int i = 4; i < tokens.length; i++) {
                String d = tokens[i].replaceAll(" |\n|\r|\t", "");
                if (!isInt(d)) {
                    errorEnDestinos = true;
                    break;
                }
                destinos += d + ",";
            }
            if (errorEnDestinos) {
                reporte += "Error en linea: " + nLinea + ". Declaracion de destinos: <" + destinos + "> uno o mas de los destinos no es un int valido. Se ha saltado la linea\n";
                node = node.next;
                nLinea++;
                continue;
            }
            destinos = destinos.substring(0, destinos.length() - 1);
            //Trata de hacer la insercion en el web service
            String resultWebService = DesktopAppColineAirport.addReserva(cliente, destinos);
            if (resultWebService != null) {
                reporte += "Mensaje de web service: " + resultWebService + "\n";
            }
            node = node.next;
            nLinea++;
        }
        return reporte;
    }
}
