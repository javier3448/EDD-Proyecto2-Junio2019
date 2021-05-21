/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures.Hash;

import DataStructures.Matrices.MatrixRuta; //PARA DEBUGGING
import DataStructures.SimpleLists.SimpleList; //PARA DEBUGGING
import DataStructures.Trees.BTree; //PARA DEBUGGING
import MyObjects.Destino;
import MyObjects.Reserva;
import MyObjects.Ruta;
import java.time.Duration;
import java.util.Arrays;

/**
 * OJO! TALVEZ SE TENGA QUE HACER UN OVERFLOW DESPUES QUE SE ELIMINA UN DATO
 * @author javier
 */
public class HashTableReserva {
    //Contiene los primeros mil numeros primos empezando en 7
    public static class Primos{
        /**
        * 
        * @param n 
        * @return 
        */
       private static int nextPrime(int n){
           if(isPrime(n)) n = n + 2;
           while(!isPrime(n)){
               n++;
           }
           return n;
       }

       private static boolean isPrime(int n){
           if ((n & 1) == 0) {
               return false;
           }
           int end = n/2;
           for (int i = 3; i < end; i++) {
               if (n%i == 0) {
                   return false;
               }
           }
           return true;
       }
    }
    
    
    Reserva[] myArray;
    //tamanno del array
    int size;
    //Numero de elementos actualmente en la tabla hash
    int elementos;
    
    public HashTableReserva(){
        size = 7;
        myArray = new Reserva[size];
        Arrays.fill(myArray, null);
    }
    
    /**
     * Retorna la posicion correspondiente de la llave en el array pero no resuelve si hay colision
     * @param key
     * @return 
     */
    private int myHash(int key){
        return key % (size);
    }
    
    public String add(Reserva r){
        if (r == null) {
            throw new IllegalArgumentException("no se puede agregar null a la tabla hash");
        }
        int key = r.getKey();
        int index = myHash(key);
        int i = 0;
        while(myArray[index] != null){
            index = key + (i+1)*(i+1);
            index = index % (size);
            i++;
        }
        myArray[index] = r;
        elementos++;
        
        double balance = (double)elementos/(double)size;
        System.out.println(balance);
        if (balance > 0.5) {
            return myOverflow();
        }
        
        return null;
    }
    
    private String myOverflow(){
        Reserva[] oldArray =  myArray;
        size = Primos.nextPrime(size);
        elementos = 0;
        myArray = new Reserva[size];
        
        for (int i = 0; i < oldArray.length; i++) {
            if (oldArray[i] != null) {
                this.add(oldArray[i]);
            }
        }
        
        return "Ocurrio un overflow ahora el tamano del array es: " + size;
    }
    
    /**
     * Retorna todas las reservas el cliente del parametro
     * @param cliente
     * @return 
     */
    public SimpleList<Reserva> get(String cliente){
        SimpleList<Reserva> l = new SimpleList<Reserva>();
        int key = Reserva.stringToKey(cliente);
        int index = myHash(key);
        int i = 0;
        int counter = 0;
        while(myArray[index] != null && counter < size){
            if (myArray[index].getCliente() == cliente) {
                l.add(myArray[i]);
            }
            index = key + (i+1)*(i+1);
            index = index % (size);
            i++;
            counter++;
        }
        return l;
    }
    
    public Reserva get(int key, int idReserva){
        int index = myHash(key);
        int i = 0;
        int counter = 0;
        while(myArray[index] != null && counter < size){
            if (myArray[index].getId() == idReserva) {
                return null;
            }
            index = key + (i+1)*(i+1);
            index = index % (size);
            i++;
            counter++;
        }
        
        return null;
    }
    
    public SimpleList<Reserva> toList(){
        SimpleList<Reserva> list = new SimpleList<Reserva>();
        for (int i = 0; i < size; i++) {
            if (myArray[i] != null) {
                list.add(myArray[i]);
            }
        }
        return list;
    }
    
    public String remove(int key, int idReserva){
        int index = myHash(key);
        int i = 0;
        int counter = 0;
        while(myArray[index] != null && counter < size){
            if (myArray[index].getId() == idReserva) {
                myArray[index] = null;
                elementos--;
                return null;
            }
            index = key + (i+1)*(i+1);
            index = index % (size);
            i++;
            counter++;
        }
        
        return "No se elimina nada";
    }
    
    public void print(){
        String s = "";
        Reserva r;
        for (int i = 0; i < size; i++) {
            r = myArray[i];
            s = "|   ";
            if (myArray[i] != null) {
                s += r.getId();
            }
            s += "   |";
            System.out.print(s);
        }
    }
    
    //Chapus para que no se cague todo con los espacios en el nombre de los paises
    public SimpleList<String> getDotLines(){
        SimpleList<String> lines = new SimpleList<String>();
        
        lines.add("digraph HashT{");
        lines.add("node[shape = record]");
        lines.add("rankdir = LR");
        
        String s = "";
        String recorrido = "";
        String nodos = "";
        for (int i = 0; i < size; i++) {
            if (myArray[i] != null) {
                s += "<" + myArray[i].getId() + ">" + i + "\\nid: " + myArray[i].getId() + "\\nCosto: " + myArray[i].getCosto() + "\\nTiempo: " + myArray[i].getTiempo().toMinutes() + "\\n" 
                     + myArray[i].getCliente();
                SimpleList<Ruta>.Node node = myArray[i].getRecorrido().head;
                if (node != null) {
                    recorrido += "someNode:" + myArray[i].getId() + "->";
                    while(node.next != null){
                        recorrido += node.data.getPuntoA().getName().replaceAll(" ", "_") + myArray[i].getId() + "->" ;
                        nodos += node.data.getPuntoA().getName().replaceAll(" ", "_") + myArray[i].getId() + "[lable=\"" + node.data.getPuntoA().getName() + "\"];\n";
                        node = node.next;
                    }
                    //para el ultimo recorrido
                    recorrido +=  node.data.getPuntoA().getName().replaceAll(" ", "_") + myArray[i].getId() + "->" + node.data.getPuntoB().getName().replaceAll(" ", "_") + myArray[i].getId() + ";\n";
                    nodos += node.data.getPuntoA().getName().replaceAll(" ", "_") + myArray[i].getId() + "[label=\"" + node.data.getPuntoA().getName() + "\"];\n";
                    nodos += node.data.getPuntoB().getName().replaceAll(" ", "_") + myArray[i].getId() + "[label=\"" + node.data.getPuntoB().getName() + "\"];\n";
                }
            }
            else{
                s += String.valueOf(i);
            }
            s += "|";
        }
        
        s = s.substring(0, s.length() - 1);
        
        lines.add("someNode[label = \"" + s + "\"];");
        lines.add(nodos);
        lines.add(recorrido);
        
        lines.add("}");
        
        return lines;
    }
    
//        
//    //Hace un hastable con tamanno igual al primo siguiente de parametro size
//    private HashTableReserva(int size){
//        this.size = Primos.nextPrime(size);
//        myArray = new Reserva[size];
//        Arrays.fill(myArray, null);
//    }
    
    public static void main(String args[]){
        BTree<Destino> indices = new BTree<Destino>();
        indices.add(new Destino(21, "Guate"));
        indices.add(new Destino(3, "Canada"));
        indices.add(new Destino(5, "Us"));
        
        indices.add(new Destino(33, "Belice"));
        indices.add(new Destino(46, "Mexico"));
        indices.add(new Destino(69, "Honduras"));
        indices.add(new Destino(75, "Nicaragua"));
        
        indices.add(new Destino(89, "Panama"));
        
        indices.print();
        
        MatrixRuta m = new MatrixRuta(indices);
        m.add(5, 59, new Ruta.RutaParams(5.0, Duration.ofHours(59), "encriptado"));
        m.add(5, 21, new Ruta.RutaParams(5.0, Duration.ofHours(21), "encriptado"));
        m.add(21, 75, new Ruta.RutaParams(21.0, Duration.ofHours(75), "encriptado"));
        m.add(21, 46, new Ruta.RutaParams(21.0, Duration.ofHours(46), "encriptado"));
        m.add(59, 3, new Ruta.RutaParams(59.0, Duration.ofHours(3), "encriptado"));
        m.add(3, 33, new Ruta.RutaParams(3.0, Duration.ofHours(33), "encriptado"));
        m.add(33, 69, new Ruta.RutaParams(33.0, Duration.ofHours(69), "encriptado"));
        m.add(33, 89, new Ruta.RutaParams(33.0, Duration.ofHours(89), "encriptado"));
        m.add(46, 89, new Ruta.RutaParams(46.0, Duration.ofHours(89), "encriptado"));
        m.add(46, 59, new Ruta.RutaParams(46.0, Duration.ofHours(59), "encriptado"));
        m.add(46, 75, new Ruta.RutaParams(46.0, Duration.ofHours(75), "encriptado"));
        System.out.println(188 % 11);
        HashTableReserva ht = new HashTableReserva();
        
        SimpleList<Ruta> l0 = new SimpleList<Ruta>();
        l0.add(m.get(3, 33));
        Reserva r0 = new Reserva("3ED8xfQJvR", l0);
        System.out.println(r0.getKey());
        Reserva r1 = new Reserva("wqVY8QAXzb", l0);
        System.out.println(r1.getKey());
        Reserva r2 = new Reserva("iQjJaHDmrF", l0);
        System.out.println(r2.getKey());
        Reserva r3 = new Reserva("HOpZ6RIvmh", l0);
        System.out.println(r3.getKey());
        Reserva r4 = new Reserva("jZOUB6fPXI", l0);
        System.out.println(r4.getKey());
        ht.add(r0);
        ht.add(r1);
        ht.add(r2);
        ht.add(r3);
        ht.print();
        ht.add(r4);
        ht.print();
        System.out.println();
        ht.getDotLines();
        
        SimpleList<String>.Node n =  ht.getDotLines().head;
        while(n != null){
            System.out.println(n.data);
            n = n.next;
        }
    }
    
    
}
