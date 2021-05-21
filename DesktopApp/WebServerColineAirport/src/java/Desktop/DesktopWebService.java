/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Desktop;

import DataStructures.Manager.Storage;
import DataStructures.SimpleLists.SimpleList;
import MyObjects.Destino;
import MyObjects.Reserva;
import MyObjects.Ruta;
import MyObjects.Ruta.RutaParams;
import java.time.Duration;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author javier
 */
@WebService(serviceName = "DesktopWebService")
public class DesktopWebService {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addRuta")
    public String addRuta(@WebParam(name = "keyRow") int keyRow, @WebParam(name = "keyColumn") int keyColumn, @WebParam(name = "costo") double costo, @WebParam(name = "minutes") int minutes, @WebParam(name = "piloto") String piloto) {
        Ruta.RutaParams params = new RutaParams(costo, Duration.ofMinutes(minutes), piloto);
        return Storage.RUTAS.add(keyRow, keyColumn, params);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addDestino")
    public String addDestino(@WebParam(name = "id") int id, @WebParam(name = "nombre") String nombre) {
        //TODO write your implementation code here:
        return Storage.DESTINOS.add(new Destino(id, nombre));
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "removeRuta")
    public String removeRuta(@WebParam(name = "keyRow") int keyRow, @WebParam(name = "keyCol") int keyCol) {
        return Storage.RUTAS.remove(keyRow, keyCol);
    }
    
    /**
     * 
     * @param cliente
     * @param stringDestinos destinos separados por comas sin espacios. TIENEN QUE SER INT SI NO TIRA EXCEPCION
     * @return 
     */
    @WebMethod(operationName = "addReserva")
    public String addReserva(@WebParam(name = "cliente") String cliente, @WebParam(name = "stringDestinos") String stringDestinos) {
        //TODO write your implementation code here:
        SimpleList<Destino> destinos = new SimpleList<Destino>();
        String[] arrayRutas = stringDestinos.split(",");
        Destino d;
        for (int i = 0; i < arrayRutas.length; i++) {
            d = Storage.DESTINOS.search(Integer.valueOf(arrayRutas[i]));
            if (d == null) {
                return "Error al tratar de agregar la reserva: " + cliente + ". Con destinos: " + stringDestinos +"\n"
                        + "no existe el destino con llave: " + arrayRutas[i];
            }
            destinos.add(d);
        }
        String s;
        try{
            s = Storage.RESERVAS.add(new Reserva(cliente, destinos, Storage.RUTAS));
        }catch(IllegalArgumentException e){
            s = e.getMessage();
        }
        return s;
    }

    /**
     * PROBAR QUE PASA CUANDO SE TRATA DE RETORNAR UN ARRAY!!!!!!!!!!!1
     */
    @WebMethod(operationName = "getDotDestinos")
    public String[] getDotDestinos() {
        SimpleList<String> lines = Storage.DESTINOS.getDotLines("", true);
        SimpleList<String>.Node tmp = lines.head;
        String[] arrayLines = new String[lines.getSize()];
        int i = 0;
        while (tmp != null){
            arrayLines[i] = tmp.data;
            tmp = tmp.next;
            i++;
        }
        return arrayLines;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getDotRutas")
    public String[] getDotRutas() {
        SimpleList<String> lines = Storage.RUTAS.getDotLinesMatriz();
        SimpleList<String>.Node tmp = lines.head;
        String[] arrayLines = new String[lines.getSize()];
        int i = 0;
        while (tmp != null){
            arrayLines[i] = tmp.data;
            tmp = tmp.next;
            i++;
        }
        return arrayLines;
    }

    
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "getVermanKey")
    public int getVermanKey() {
        //TODO write your implementation code here:
        return Ruta.VERMAN_KEY;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getStringDestinos")
    public String getStringDestinos() {
        String s = "Destinos:\n";
        SimpleList<Destino> l = Storage.DESTINOS.inorder();
        if (l == null) {
            return s;
        }
        SimpleList<Destino>.Node node = Storage.DESTINOS.inorder().head;
        while(node != null){
            s += "    " + node.data.getKey() + " " + node.data.getNombre() + "\n";
            node = node.next;
        }
        return s;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getStringRutasReservas")
    public String getStringRutasReservas() {
        SimpleList<Ruta>.Node nodeRuta = Storage.RUTAS.toList().head;
        String s = "Rutas:\n";
        while(nodeRuta != null){
            s += "    " + nodeRuta.data.getKeyA() + " " + nodeRuta.data.getKeyB() + " " + nodeRuta.data.getCosto() + " " + 
                          nodeRuta.data.getTiempoVuelo().toMinutes() + "m " + nodeRuta.data.getPiloto(true) + "\n";
            nodeRuta = nodeRuta.next;
        }
              
        SimpleList<Reserva>.Node nodeReserva = Storage.RESERVAS.toList().head;
        s += "Reservas:\n";
        while(nodeReserva != null){
            s += "    " + nodeReserva.data.getCliente() + " " + nodeReserva.data.getCosto() + " " + 
                          nodeReserva.data.getTiempo().toMinutes() + "m Rutas: ";
            nodeRuta = nodeReserva.data.getRecorrido().head;
            while(nodeRuta != null){
                s += "<" + nodeRuta.data.getKeyA() + ", " + nodeRuta.data.getKeyB() + ">; ";
                nodeRuta = nodeRuta.next;
            }
            s += "\n";
            nodeReserva = nodeReserva.next;
        }
        
        return s;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getStringDestinosRutas")
    public String getStringDestinosRutas() {
        SimpleList<Destino>.Node node = Storage.DESTINOS.inorder().head;
        String s = "Destinos:\n";
        while(node != null){
            s += "    " + node.data.getKey() + " " + node.data.getNombre() + "\n";
            node = node.next;
        }
        
        SimpleList<Ruta>.Node nodeRuta = Storage.RUTAS.toList().head;
        s += "Rutas:\n";
        while(nodeRuta != null){
            s += "    <" + nodeRuta.data.getKeyA() + ", " + nodeRuta.data.getKeyB() + "> " + nodeRuta.data.getCosto() + " " + 
                          nodeRuta.data.getTiempoVuelo().toMinutes() + "m " + nodeRuta.data.getPiloto(true) + "\n";
            nodeRuta = nodeRuta.next;
        }
        
        return s;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getDotReservas")
    public java.lang.String[] getDotReservas() {
        SimpleList<String> lines = Storage.RESERVAS.getDotLines();
        SimpleList<String>.Node tmp = lines.head;
        String[] arrayLines = new String[lines.getSize()];
        int i = 0;
        while (tmp != null){
            arrayLines[i] = tmp.data;
            tmp = tmp.next;
            i++;
        }
        return arrayLines;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getDotMapa")
    public java.lang.String[] getDotMapa() {
        SimpleList<String> lines = Storage.RUTAS.getDotLinesGrafo();
        SimpleList<String>.Node tmp = lines.head;
        String[] arrayLines = new String[lines.getSize()];
        int i = 0;
        while (tmp != null){
            arrayLines[i] = tmp.data;
            tmp = tmp.next;
            i++;
        }
        return arrayLines;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getPaths")
    public String getPaths(@WebParam(name = "startKey") int startKey, @WebParam(name = "endKey") int endKey) {
        if (Storage.DESTINOS.isEmpty()) {
            return "Se conecto con el web service :)";
        }
        SimpleList<SimpleList<Destino>> recorrido = Storage.RUTAS.getPaths(startKey, endKey);
        
        String s = "";
        
        SimpleList<SimpleList<Destino>>.Node listNode = recorrido.head;
        while(listNode != null){
            SimpleList<Destino>.Node destinoNode = listNode.data.head;
            while(destinoNode != null){
                s += destinoNode.data.getKey() + ",";
                destinoNode = destinoNode.next;
            }
            s += "\n";
            listNode = listNode.next;
        }
        
        return s;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "removeReserva")
    public String removeReserva(@WebParam(name = "cliente") String cliente, @WebParam(name = "id") int id) {
        int key = Reserva.stringToKey(cliente);
        return Storage.RESERVAS.remove(key, id);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getReservasCliente")
    public String getReservasCliente(@WebParam(name = "cliente") String cliente) {
        SimpleList<Reserva> reservas = Storage.RESERVAS.get(cliente);
        
        String s = "";
        
        SimpleList<Reserva>.Node nodeReserva = reservas.head;
        s += "Reservas:\n";
        while(nodeReserva != null){
            s += "    " + nodeReserva.data.getCliente() + " " + nodeReserva.data.getCosto() + " " + 
                          nodeReserva.data.getTiempo().toMinutes() + "m Rutas: ";
            SimpleList<Ruta>.Node nodeRuta = nodeReserva.data.getRecorrido().head;
            while(nodeRuta != null){
                s += "<" + nodeRuta.data.getKeyA() + ", " + nodeRuta.data.getKeyB() + ">; ";
                nodeRuta = nodeRuta.next;
            }
            s += "\n";
            nodeReserva = nodeReserva.next;
        }
        return s;
    }
    
    
}
