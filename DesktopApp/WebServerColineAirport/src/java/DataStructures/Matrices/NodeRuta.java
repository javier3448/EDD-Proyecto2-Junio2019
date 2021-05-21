/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures.Matrices;

import MyObjects.Ruta;

/**
 *
 * @author javier
 */
public class NodeRuta {
    public NodeRuta right;
    public NodeRuta down;
    public Ruta ruta;

    public NodeRuta(Ruta ruta) {
        this.ruta = ruta;
    }
    
    public int getKeyRow() {
        return ruta.getKeyA();
    }
    
    public int getKeyCol() {
        return ruta.getKeyB();
    }
}
