/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopappcolineairport;

import MyGui.MyJFrame;


/**
 *
 * @author javier
 */
public class DesktopAppColineAirport {

    //public static int VERMAN_KEY = getVermanKey();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            System.out.println(info.getName());
        }
    }

    
    public static String hello(java.lang.String name) {
        desktop.DesktopWebService_Service service = new desktop.DesktopWebService_Service();
        desktop.DesktopWebService port = service.getDesktopWebServicePort();
        return port.hello(name);
    }

    public static String addRuta(int keyRow, int keyColumn, double costo, int minutes, java.lang.String piloto) {
        desktop.DesktopWebService_Service service = new desktop.DesktopWebService_Service();
        desktop.DesktopWebService port = service.getDesktopWebServicePort();
        return port.addRuta(keyRow, keyColumn, costo, minutes, piloto);
    }

    public static String addDestino(int id, java.lang.String nombre) {
        desktop.DesktopWebService_Service service = new desktop.DesktopWebService_Service();
        desktop.DesktopWebService port = service.getDesktopWebServicePort();
        return port.addDestino(id, nombre);
    }

    public static String addReserva(java.lang.String cliente, java.lang.String stringDestinos) {
        desktop.DesktopWebService_Service service = new desktop.DesktopWebService_Service();
        desktop.DesktopWebService port = service.getDesktopWebServicePort();
        return port.addReserva(cliente, stringDestinos);
    }

    public static String getStringDestinos() {
        desktop.DesktopWebService_Service service = new desktop.DesktopWebService_Service();
        desktop.DesktopWebService port = service.getDesktopWebServicePort();
        return port.getStringDestinos();
    }

    public static String getStringRutasReservas() {
        desktop.DesktopWebService_Service service = new desktop.DesktopWebService_Service();
        desktop.DesktopWebService port = service.getDesktopWebServicePort();
        return port.getStringRutasReservas();
    }

    public static String getStringDestinosRutas() {
        desktop.DesktopWebService_Service service = new desktop.DesktopWebService_Service();
        desktop.DesktopWebService port = service.getDesktopWebServicePort();
        return port.getStringDestinosRutas();
    }

    public static String removeRuta(int keyRow, int keyCol) {
        desktop.DesktopWebService_Service service = new desktop.DesktopWebService_Service();
        desktop.DesktopWebService port = service.getDesktopWebServicePort();
        return port.removeRuta(keyRow, keyCol);
    }

    public static java.util.List<java.lang.String> getDotDestinos() {
        desktop.DesktopWebService_Service service = new desktop.DesktopWebService_Service();
        desktop.DesktopWebService port = service.getDesktopWebServicePort();
        return port.getDotDestinos();
    }

    public static java.util.List<java.lang.String> getDotRutas() {
        desktop.DesktopWebService_Service service = new desktop.DesktopWebService_Service();
        desktop.DesktopWebService port = service.getDesktopWebServicePort();
        return port.getDotRutas();
    }

    public static java.util.List<java.lang.String> getDotReservas() {
        desktop.DesktopWebService_Service service = new desktop.DesktopWebService_Service();
        desktop.DesktopWebService port = service.getDesktopWebServicePort();
        return port.getDotReservas();
    }

    public static java.util.List<java.lang.String> getDotMapa() {
        desktop.DesktopWebService_Service service = new desktop.DesktopWebService_Service();
        desktop.DesktopWebService port = service.getDesktopWebServicePort();
        return port.getDotMapa();
    }

    public static String getPaths(int startKey, int endKey) {
        desktop.DesktopWebService_Service service = new desktop.DesktopWebService_Service();
        desktop.DesktopWebService port = service.getDesktopWebServicePort();
        return port.getPaths(startKey, endKey);
    }
    
    
}
