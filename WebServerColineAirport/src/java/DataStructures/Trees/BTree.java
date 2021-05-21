/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures.Trees;

import DataStructures.SimpleLists.SimpleList;
import MyInterfaces.Keyable;

/**
 * Orden 5 (numero de hijos por nodo son 5) orden debe ser mayor o igual a 2
 * @author javier
 * @param <T> Debe de implementar Keyable
 */
public class BTree <T extends Keyable>{
    public class Node<S>{
        private S data;
        private Node<S> next;
        public Node(S data) {
            this.data = data;
            next = null;
        }

        public S getData() {
            return data;
        }
        
        private int getSize() {
            Node<S> tmp = this;
            int n = 0;
            while(tmp != null){
                tmp = tmp.next;
                n++;
            }
            return n;
        }
    }
    //DESDE AQUI SE HACE EL SPLIT
    private class Page{
        private int size;//Numero de elementos en la lista encabezada por head
        private Node<T> headKeys;
        private Node<Page> headChildren;
        
        private Page(Node<T> headKeys){
            size = 1;
            this.headKeys = headKeys;
            this.headChildren = null;
        }
        
        private Page(Node<T> headKeys, Node<Page> headChildren){
            size = headKeys.getSize();
            this.headKeys = headKeys;
            this.headChildren = headChildren;
        }
        
        private Node<T> getKey(int index){
            if (index >= size || index < 0) {
                throw new IllegalArgumentException("Indice no valido");
            }
            
            Node<T> tmp = headKeys;
            for (int i = 0; i < index; i++) {
                tmp = tmp.next;
            }
            
            return tmp;
        }
        
        private Node<Page> getPage(int index){
            if (index >= size || index < 0) {
                throw new IllegalArgumentException("Indice no valido");
            }
            
            Node<Page> tmp = headChildren;
            for (int i = 0; i < index; i++) {
                tmp = tmp.next;
            }
            
            return tmp;
        }
        
        private boolean isLeaf(){
            return (headChildren == null);
        }
    }
    
    private Page root;
    private final int ORDER;
    
    public BTree(){
        root = null;
        ORDER = 5;
    }
    
    public boolean isEmpty(){
        return root != null;
    }
    
    private String returnString;
    public String add(T data){
        returnString = null;
        //Return string se edita adentro de los metodos recursivos
        if (root == null) {
            root = new Page(new Node<T>(data));
            return returnString;
        }
        Page result = add(root, data);
        //Si result no es null, significa que se le hizo split a la pagina root. Por lo tanto sube de livel el arbol y root de la clase cambia.
        if (result != null) {
            root = result;
        }
        return returnString;
    }
    
    //Si este solo puede agregar en un nodo hoja
    private Page add(Page root, T data) {
        Page result;
        
        //Encuentra el nodo hoja
        if (root.isLeaf()) {
            return add(root, new Page(new Node<T>(data)));
        }
        Node<T> tmpKey = root.headKeys;
        Node<Page> tmpChild = root.headChildren;
        while(tmpKey!=null){
            if (tmpKey.data.getKey() > data.getKey()) {
                break;
            }
            tmpKey = tmpKey.next;
            tmpChild = tmpChild.next;
        }
        result = add(tmpChild.data, data);
        
        //Agrega result. result es el resultado del la operacion que se le hizo al hijo del root de esta llamada al metodo
        if (result != null) {
            return add(root, result);
        }
        
        return null;
    }
    
    //Puede agregar donde sea, newNode es una pagina que contiene 1 nodo nuevo y dos apuntadores, esta pagina es la que se devuelve despues de hacer
    //una division de ser necesaria, y representa el nodo que sobra despues de hacer la divicion.
    //Si el valor retornado es null. No fue necesaria la division.
    //chapuz: se recorre aqui otra vez el Page aunque no deveria de ser necesario. Talvez no se nesesario hacer el caso especial antes de cabeza. si cambio el oden en while
    private Page add(Page root, Page newNode){
        //Inserta en la list de keys
        Node<T> tmpKey = root.headKeys;
        Node<Page> tmpChild = root.headChildren;
        int newKey = newNode.headKeys.data.getKey();
        //Caso especial: si va antes que head
        if (root.headKeys.data.getKey() > newKey) {
            newNode.headKeys.next = root.headKeys;
            root.headKeys = newNode.headKeys;
            
            if (!newNode.isLeaf()) {
                newNode.headChildren.next.next = root.headChildren.next;
                root.headChildren = newNode.headChildren;
            }
        }
        else{
            while(tmpKey.next!=null){
                if (tmpKey.next.data.getKey() > newKey) {
                    break;
                }
                if (tmpKey.next.data.getKey() == newNode.headKeys.data.getKey()) {
                    returnString = "Cambia un valore en el BTree. Llave: " + tmpKey.next.data.getKey();
                    tmpKey.data = newNode.headKeys.data;
                    return null;
                }
                tmpKey = tmpKey.next;
                //Se van a recorrer los hijos de root si no es leaf
                if (!root.isLeaf()) tmpChild = tmpChild.next;
                
            }
            if (tmpKey.data.getKey() == newKey) {
                returnString = "Cambia un valore en el BTree. Llave: " + tmpKey.data.getKey();
                tmpKey.data = newNode.headKeys.data;
                return null;
            }
            newNode.headKeys.next = tmpKey.next;
            tmpKey.next = newNode.headKeys;
            //Solo va a modificar los nodos children si no son null, los nodos children no van a ser nulos cuando newNode provenga del 
            //resultado de un split
            if (!newNode.isLeaf()) {
                newNode.headChildren.next.next = tmpChild.next.next;
                tmpChild.next = newNode.headChildren;
                //root.isLeaf() = false;//Si teine hijos es porque es el resultado de un split y no es nodo leaf
            }
        }
        root.size++;
        
        if (root.size >= 5) {
            return split(root);
        }
        return null;
    }
    
    //NO OLVIDAR QUE CAMBIAR LOS SIZE DE LAS TRES PAGINAS RESULTANTES!!!
    private Page split(Page root){
        int splitIndex = ORDER/2;
        
        Node<T> parentHeadKeys;
        Node<Page> parentHeadChildren;
        
        Node<T> rightHeadKeys;
        Node<Page> rightHeadChildren = null; //Si es un nodo leaf no tiene ningun hijo
        
        Node<T> tmpKey = root.headKeys;
        Node<Page> tmpChild = root.headChildren;
        for (int i = 0; i < ORDER/2 -1; i++) {
            tmpKey = tmpKey.next;
            //Se van a recorrer los hijos de root si no es leaf
            if (!root.isLeaf()) tmpChild = tmpChild.next;
        }
        //En este momento tmpKey.next sera el padre resultante del split.
        //tmpChild.next es el ultimo hijo del hijo izquierdo del nodo padre
        //tmpChild.next.next es el primer hijo dle hijo derecho del nodo padre
        
        rightHeadKeys = tmpKey.next.next;
        tmpKey.next.next = null;
        parentHeadKeys = tmpKey.next;
        tmpKey.next = null;
        
        //Solo va a modificar los nodos children si no son null, los nodos children no van a ser nulos cuando newNode provenga del 
        //resultado de un split
        if (!root.isLeaf()) {
            rightHeadChildren = tmpChild.next.next;
            tmpChild.next.next = null;
        }
        root.size = root.headKeys.getSize();//Actualiza el size de root
        parentHeadChildren = new Node<Page>(root); //apuntador a la izq del nodo padre
        parentHeadChildren.next = new Node<Page>(new Page(rightHeadKeys, rightHeadChildren));
        
        return new Page(parentHeadKeys, parentHeadChildren);
    }
    
    public T search(int key){
        return search(root, key);
    }
    
    private T search(Page root, int key){
        if (root == null) {
            return null;
        }
        Node<T> tmpKey = root.headKeys;
        Node<Page> tmpChild = root.headChildren;
        while(tmpKey!=null){
            if (tmpKey.data.getKey() == key) {
                return tmpKey.data;
            }
            if (tmpKey.data.getKey() > key) {
                break;
            }
            tmpKey = tmpKey.next;
            if (!root.isLeaf()) {
                tmpChild = tmpChild.next;
            }
        }
        if (tmpChild == null) {
            return null;
        }
        return search(tmpChild.data, key);
    }
    
    public void print(){
        print(root, 0);
    }
    
    private void print(Page root, int ind){
        int currInd = ind;
        Node<T> tmpKey = root.headKeys;
        System.out.print(getInd(ind));
        while(tmpKey != null) {
            System.out.print(tmpKey.data.getKey() + ", ");
            tmpKey = tmpKey.next;
        }
        System.out.println();
        Node<Page> tmpChild = root.headChildren;
        while(tmpChild != null) {
            print(tmpChild.data, ind + 1);
            tmpChild = tmpChild.next;
        }
    }
    
    private String getInd(int ind){
        String s = "";
        for (int i = 0; i < ind * 4; i++) {
            s += " ";
        }
        return s;
    }
    
    /**
     * Retorna las lineas con la que se puede generar un dot para vizualizar el BTree
     * @param prefix caracter que se coloca antes de cada nombre de pagina
     * @return 
     */
    public SimpleList<String> getDotLines(String prefix){
        SimpleList<String> lines = new SimpleList<String>();
        
        lines.add("digraph " + prefix + "BTree{");
        lines.add("node[shape = record];");
        lines.add("style=invis;");
        
        if (root == null) {
            lines.add("}");
            return lines;
        }
        
        writeDotLines(root, lines, prefix, false);
        
        lines.add("}");
        
        return lines;
    }
    
    public SimpleList<String> getDotLines(String prefix, boolean writeNames){
        SimpleList<String> lines = new SimpleList<String>();
        
        lines.add("digraph " + prefix + "BTree{");
        lines.add("node[shape = record];");
        lines.add("style=invis;");
        
        if (root == null) {
            lines.add("}");
            return lines;
        }
        
        writeDotLines(root, lines, prefix, writeNames);
        
        lines.add("}");
        
        return lines;
    }
    
    //Puede mejorar. key y keyChild se declaran dos veces, en la ejecucion anterior a la actual en la llamada recursiva
    private void writeDotLines(Page root, SimpleList<String> lines, String p, boolean writeNames) {
        Node<T> tmpKey = root.headKeys;
        Node<Page> tmpChild = root.headChildren;
        int keyRoot = root.headKeys.data.getKey();
        String rootLabel = "";
        while(tmpKey != null) {
            if (!root.isLeaf()) {
                int keyChild = tmpChild.data.headKeys.data.getKey(); //llave del primer elemento de la pagina de tmpChild
                rootLabel += "<" + keyChild + ">";
                lines.add(p + "page" + keyRoot + ":" + keyChild + "->" + p + "page" + keyChild);
                writeDotLines(tmpChild.data, lines, p, writeNames);
                tmpChild = tmpChild.next;
            }
            int key = tmpKey.data.getKey();
            String name = "";
            if (writeNames) { //Esto es un chapuz: keyable no deberia de implementar getName
                name = "\\n" + tmpKey.data.getName();
            }
            rootLabel += "|<" + key + ">" + key + name + "|";
            tmpKey = tmpKey.next;
        }
        if (!root.isLeaf()) {
            int keyChild = tmpChild.data.headKeys.data.getKey(); //llave del primer elemento de la pagina de tmpChild
            rootLabel += "<" + keyChild + ">";
            lines.add(p + "page" + keyRoot + ":" + keyChild + "->" + p + "page" + keyChild);
            writeDotLines(tmpChild.data, lines, p, writeNames);
        }
        lines.add(p + "page" + keyRoot + "[label = \"" + rootLabel + "\"]");//declara el nodo
    }
    
    /**
     * Metodo auxiliar para generar el dot de matris
     * Retorna el valor de la primera llave a la rama que pertenece key
     * SI NO EXISTE KEY PUEDE RETORNAR UN ERROR. SI BTREE ESTA VACIA PUEDE RETORNAR UN ERROR
     * @param key
     * @return 
     */
    public String getPageCode(int key){
        return getPageCode(root, key);
    }
    
    private String getPageCode(Page root, int key){
        if (root == null) {
            return null;
        }
        Node<T> tmpKey = root.headKeys;
        Node<Page> tmpChild = root.headChildren;
        while(tmpKey!=null){
            if (tmpKey.data.getKey() == key) {
                return String.valueOf(root.headKeys.data.getKey());
            }
            if (tmpKey.data.getKey() > key) {
                break;
            }
            tmpKey = tmpKey.next;
            if (!root.isLeaf()) {
                tmpChild = tmpChild.next;
            }
        }
        if (tmpChild == null) {
            return null;
        }
        return getPageCode(tmpChild.data, key);
    }
    
    public SimpleList<T> inorder(){
        SimpleList<T> elements = new SimpleList<T>();
        
        //Chapuz
        if (root == null) {
            return elements;
        }
        
        inorder(root, elements);
        
        return elements;
    }
    
    private void inorder(Page root, SimpleList<T> container) {
        Node<T> tmpKey = root.headKeys;
        Node<Page> tmpChild = root.headChildren;
        int keyRoot = root.headKeys.data.getKey();
        String rootLabel = "";
        while(tmpKey != null) {
            if (tmpChild != null) {
                inorder(tmpChild.data, container);
                tmpChild = tmpChild.next;
            }
            container.add(tmpKey.data);
            tmpKey = tmpKey.next;
        }
        if (tmpChild != null) {
            inorder(tmpChild.data, container);
            tmpChild = tmpChild.next;
        }
    }
}
