/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures.Manager;

import DataStructures.Hash.HashTableReserva;
import DataStructures.Matrices.MatrixRuta;
import DataStructures.Trees.BTree;
import MyObjects.Destino;

/**
 *
 * @author javier
 */
public class Storage {
    public static final BTree<Destino> DESTINOS = new BTree<Destino>();
    public static final MatrixRuta RUTAS = new MatrixRuta(DESTINOS);
    public static final HashTableReserva RESERVAS = new HashTableReserva();
}
