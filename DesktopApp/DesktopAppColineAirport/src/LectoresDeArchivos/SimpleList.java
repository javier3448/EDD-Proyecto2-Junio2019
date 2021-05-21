/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LectoresDeArchivos;

/**
 * @author javier
 */
public class SimpleList <T>{
    
    public class Node{
        public Node next;
        public T data;
        
        Node(T data)
        {
            this.data = data;
            next = null;
        }
    }
    
    
    public Node head;
    private int size = 0;
    
    public SimpleList(){
        head = null;
        size = 0;
    }
    
    public SimpleList(java.util.List<T> l){
        if (l == null) {
            head = null;
            size = 0;
        }
        for(T s : l){
            if (s != null) {
                this.add(s);
            }
        }
    }
    
    public SimpleList(T data){
        head = new Node(data);
        size = 1;
    }
    
    public int getSize() {
        return size;
    }
    
    /**
     * Data no puede ser null. Tira excepcion
     * @param data 
     */
    public void add(T data)
    {
        //Revisa si es null
        if (data == null) {
            throw new IllegalArgumentException("No se puede agregar data null a la lista");
        }
        
        Node tmp = head;
        if (tmp == null) {
            head = new Node(data);
            size++;
            return;
        }
        while(tmp.next != null){
            tmp = tmp.next;
        }
        tmp.next = new Node(data);
        size++;
    }
    
    /**
     * Retorna null si no existe el indice.
     * Retorna out of bounds si el indice es negativo
     * @param index indice en la lista (empieza en 0)
     * @return objeto T en la lista
     */
    public T get(int index)
    {
        //Revisar que el indice no sea valido 
        if (index<0) {
            throw new IndexOutOfBoundsException("Index " + index + " fuera de limite");
        }
        
        if (index>=size) {
            return null;
        }
        
        Node tmp = head;
        for (int i = 0; i < index; i++) {
            tmp = tmp.next;
        }
        return tmp.data;
    }
    
    /**
     * Retorna la primera coincidencia con .equals en la la lista (empieza en 0)
     * Retorna null si no existe el objeto en la lista
     * @param object
     * @return 
     */
    public T get(T object)
    {
        Node tmp = head;
        
        while(tmp != null){
            if (tmp.data.equals(object)) {
                return tmp.data;
            }
            tmp = tmp.next;
        }
        return null;
    }
    
    public boolean contains(T object){
        Node tmp = head;
        
        while(tmp != null){
            if (tmp.data.equals(object)) {
                return true;
            }
            tmp = tmp.next;
        }
        return false;
    }
    
    /**
     * Si no logro remover el nodo retorna false.
     * Out of bounds si el index es negativo
     * @param index
     * @return 
     */
    public boolean remove(int index){
        //Revisar que el indice no sea valido 
        if (index<0) {
            throw new IndexOutOfBoundsException("Index " + index + " fuera de limite");
        }
        
        if (index>=size) {
            return false;
        }
        
        Node oldNode;
        //Caso especial si elimina cabeza
        if (index == 0) {
            oldNode = head;
            head = oldNode.next;
            oldNode.next = null;
            size--;
            return true;
        }
        
        //tmp es uno antes del que se va a eliminar
        Node tmp = head;
        for (int i = 0; i < index-1; i++) {
            tmp = tmp.next;
        }
        oldNode = tmp.next;
        tmp.next = oldNode.next;
        oldNode.next = null;
        size--;
        return true;
    }
    
    public boolean remove(T object){
       //Caso especial si elimina cabeza
       Node oldNode;
        if (head.data.equals(object)) {
            oldNode = head;
            head = oldNode.next;
            oldNode.next = null;
            size--;
            return true;
        }
        
        Node tmp = head;//tmp es el anterior al que se va a eliminar
        while(tmp.next != null){
            if (tmp.next.data.equals(object)) {
                oldNode = tmp.next;
                tmp.next = oldNode.next;
                oldNode.next = null;
                size--;
                return true;
            }
            tmp = tmp.next;
        }
        return false;
    }
    
    public Node getLastNode(){
        if (head == null) {
            return null;
        }
        Node tmp = head;
        
        while(tmp.next != null){
            tmp = tmp.next;
        }
        return tmp;
    }
}
