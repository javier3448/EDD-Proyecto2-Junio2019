/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjects;
import DataStructures.Matrices.NodeRuta;
import DataStructures.SimpleLists.SimpleList;
import MyInterfaces.Keyable;
/**
 *
 * @author javier
 */
public class Destino implements Keyable{
    private int id;
    private String nombre;
    private NodeRuta right;
    private NodeRuta down;
    
    //Solo para debugging
    public Destino(int id){
        this.id = id;
        this.nombre = Integer.toString(id);
        this.right = null;
        this.down = null;
    }
    
    public Destino(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.right = null;
        this.down = null;
    }

    public Destino(int id, String nombre, NodeRuta right, NodeRuta down) {
        this(id, nombre);
        this.right = right;
        this.down = down;
    }
    
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public NodeRuta getRight() {
        return right;
    }

    public void setRight(NodeRuta right) {
        this.right = right;
    }

    public NodeRuta getDown() {
        return down;
    }

    public void setDown(NodeRuta down) {
        this.down = down;
    }
    
    public SimpleList<Destino> getAdjDestinos(){
        SimpleList<Destino> destinos = new SimpleList<Destino>();
        
        if (this.getRight() != null) {
            NodeRuta tmp = this.getRight();
            while(tmp != null){
                destinos.add(tmp.ruta.getPuntoB());
                tmp = tmp.right;
            }
        }
        if (this.getDown() != null) {
            NodeRuta tmp = this.getDown();
            while(tmp != null){
                destinos.add(tmp.ruta.getPuntoA());
                tmp = tmp.down;
            }
        }
        
        return destinos;
    }

    @Override
    public int getKey() {
        return getId();
    }

    @Override
    public String toString() {
        return "Destino{" + "id=" + id + ", nombre=" + nombre + /*", left=" + left + ", down=" + down +*/ '}';
    }

    @Override
    public String getName() {
        return nombre;
    }
}
