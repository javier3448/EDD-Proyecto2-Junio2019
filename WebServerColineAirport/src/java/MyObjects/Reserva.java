/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjects;

import DataStructures.Matrices.MatrixRuta;
import DataStructures.SimpleLists.SimpleList;
import MyInterfaces.Keyable;
import java.time.Duration;

/**
 *
 * @author javier
 */
public class Reserva implements Keyable{

    //Cuenta el numero de reservas en el sistema para que al generar una nueva reservacion pueda tener un id automaticamente.
    private static int ID_COUNT;
    
    final int id;
    final int key;
    final String cliente;
    double costo;
    Duration tiempo;
    final SimpleList<Ruta> recorrido;

    //Crea una serie de rutas que empiecen en destinos.head y termine en su ultimo elemento ej: destinos{gua,mex,us,can} => this.recorrido = {(gua->mex), (mex->us), (us->can)}
    //Si una generada por destinos no existe en source tira excepcion
    public Reserva(String cliente, SimpleList<Destino> destinos, MatrixRuta source) {
        id = ID_COUNT;
        this.cliente = cliente;
        key = initKey();
        recorrido = initRecorrido(destinos, source);
        initCostoAndTiempo();
        ID_COUNT++;
    }
    
    public Reserva(String cliente, SimpleList<Ruta> recorrido){
        if (recorrido == null) {
            //throw new IllegalArgumentException("no se puede inicializar Reserva con recorrido null");
        }
//        if (recorrido.getSize() < 1) {
//            //Despues de debugging esto no puede ser un comentario
//            //throw new IllegalArgumentException("no se puede inicializar Reserva con recorrido vacio");
//        }
        id = ID_COUNT;
        this.cliente = cliente;
        key = initKey();
        this.recorrido = recorrido;
        /*ESTE IF SOLO ES PARA DEBUGGING*/if (recorrido != null) initCostoAndTiempo();
        ID_COUNT++;
    }

    public int getId() {
        return id;
    }
    
    @Override
    public int getKey() {
        return key;
    }

    public double getCosto() {
        return costo;
    }

    public Duration getTiempo() {
        return tiempo;
    }

    public String getCliente() {
        return cliente;
    }

    public SimpleList<Ruta> getRecorrido() {
        return recorrido;
    }
    
    //Crea una serie de rutas que empiecen en destinos.head y termine en su ultimo elemento ej: destinos{gua,mex,us,can} => this.recorrido = {(gua->mex), (mex->us), (us->can)}
    //Si source no contiene una ruta entre dos destinos adyacentes en la lista tira excepcion. Si destions.size < 2 tira error
    private SimpleList<Ruta> initRecorrido(SimpleList<Destino> destinos, MatrixRuta source) {
        SimpleList<Ruta> rutas = new SimpleList<Ruta>();
        Ruta newRuta;
        SimpleList<Destino>.Node tmp = destinos.head;
        while(tmp.next != null){
            newRuta = source.get(tmp.data.getKey(), tmp.next.data.getKey());
            if (newRuta == null) throw new IllegalArgumentException("No existe el recorriodo en sourcer: " + "puntoA: " + 
                                                                    tmp.data.getKey() + " puntoB: " + tmp.next.data.getKey());
            rutas.add(newRuta);
            tmp = tmp.next;
        }
        return rutas;
    }
    
    private void initCostoAndTiempo() {
        SimpleList<Ruta>.Node tmp = recorrido.head;
        int minutes = 0;
        while(tmp.next != null){
            costo += tmp.data.getCosto();
            minutes += tmp.data.getTiempoVuelo().toHours();
            tmp = tmp.next;
        }
        tiempo = Duration.ofMinutes(minutes);
    }

    private int initKey() {
        return stringToKey(cliente);
    }
    
    /**
     * Retorna el valo que tendria la llave de una reserva con usaurio s
     * @param s
     * @return 
     */
    public static int stringToKey(String s){
        int aux = 0;
        char[] myArray = s.toCharArray();
        for (int i = 0; (i < 3) && (i < myArray.length); i++) {
            aux += (int) myArray[i];
        }
        return aux;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
