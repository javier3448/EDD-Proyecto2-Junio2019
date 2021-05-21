/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures.Matrices;

import DataStructures.SimpleLists.SimpleList;
import DataStructures.Trees.BTree;
import MyObjects.Destino;
import MyObjects.Ruta;
import java.time.Duration;

/**
 * MatrixRuta puede agregar nodos a su BTree de indices
 * @author javier
 */
public class MatrixRuta {
    //Contiene los indices derechos e izquierdos en cada nod
    private BTree<Destino> indices;
    
    public MatrixRuta(BTree<Destino> indices){
        this.indices = indices;
    }
    
    //Si ambos ruta.puntoX son iguales tira error.
    //Mala practica: SOBRE ESCRIBIRA LOS VALORES DE ruta.puntoA y ruta.puntoB para que dichos valores esten contenidos en indices
    //Retorna null si se ingreso el dato exitosamente
    public String add(int keyRow, int keyColumn, Ruta.RutaParams paramsNewRoute){
        //Revisa que keyRow y keyColumn tengan el orden correcto
        if (keyRow < keyColumn) {
            int aux = keyRow;
            keyRow = keyColumn;
            keyColumn = aux;
        }
        
        Destino headRow = indices.search(keyRow);
        Destino headCol = indices.search(keyColumn);
        
        if (headRow == null || headCol == null) {
            return ("no se puede agregar ruta a la matriz. Una de las llaves destino no existe en arbol indices\n" + keyRow + ", " + keyColumn);
        }
        if (keyRow == keyColumn) {
            return "No se pueden agregar una ruta a la matriz con el mismo punto de inicio y de salida";
        }
        
        NodeRuta tmp = headRow.getRight();
        //Si esta vacia la fila
        if (tmp == null) {
            NodeRuta newNode = new NodeRuta(new Ruta(headRow, headCol, paramsNewRoute));
            headRow.setRight(newNode);
            
            return addInColumn(headCol, newNode);
        }
        //Caso especial si inserta en la primera posicion del indice
        if (tmp.getKeyCol() > keyColumn) {
            NodeRuta newNode = new NodeRuta(new Ruta(headRow, headCol, paramsNewRoute));
            //Agrega a la derecha de tmp
            newNode.right = headRow.getRight();
            headRow.setRight(newNode);

            return addInColumn(headCol, newNode);
        }
        //Caso especial: si se trata de insertar un repetido en la primera posicion despues del indice
        if (tmp.getKeyCol() == keyColumn) {
            tmp.ruta.setValues(paramsNewRoute);
            return "Se ha modificado la ruta: " + tmp.getKeyRow() + ", " + tmp.getKeyCol();
        }
        
        while(tmp.right != null){
            if (tmp.getKeyCol() == keyColumn) {
                tmp.ruta.setValues(paramsNewRoute);
                return "Se ha modificado la ruta: " + tmp.getKeyRow() + ", " + tmp.getKeyCol();
            }
            if (tmp.right.getKeyCol() > keyColumn) {
                break;
            }
            tmp = tmp.right;
        }
        
        NodeRuta newNode = new NodeRuta(new Ruta(headRow, headCol, paramsNewRoute));
        //Agrega a la derecha de tmp
        newNode.right = tmp.right;
        tmp.right = newNode;
        
        return addInColumn(headCol, newNode);
    }
    
    private String addInColumn(Destino headCol, NodeRuta newNode) {
        int keyRow = newNode.getKeyRow();
        NodeRuta tmp = headCol.getDown();
        
        if (tmp == null) {
            System.out.println("es null");
            headCol.setDown(newNode);
            return null;
        }
        //Caso especial si inserta en la primera posicion del indice
        if (tmp.getKeyRow() > keyRow) {
            System.out.println("inserta en la cabeza");
            newNode.down = headCol.getDown();
            headCol.setDown(newNode);
            return null;
        }
        while(tmp.down != null){
            if (tmp.down.getKeyRow() > keyRow) {
                break;
            }
            tmp = tmp.down;
        }
        //Agrega abajo de tmp
        newNode.down = tmp.down;
        tmp.down = newNode;
        return null;
    }
    
    public Ruta get(int keyRow, int keyColumn){
        //Revisa que keyRow y keyColumn tengan el orden correcto
        if (keyRow < keyColumn) {
            int aux = keyRow;
            keyRow = keyColumn;
            keyColumn = aux;
            System.out.println("hace el cambio");
        }
        
        Destino headRow = indices.search(keyRow);
        
        if (headRow == null) {
            return null;
        }
        
        NodeRuta tmp = headRow.getRight();
        
        while(tmp != null){
            if (tmp.getKeyCol() == keyColumn) {
                return tmp.ruta;
            }
            tmp = tmp.right;
        }
        return null;
    }
    
    public SimpleList<Ruta> toList(){
        SimpleList<Ruta> rutas = new SimpleList<Ruta>();
        SimpleList<Destino>.Node headDestinos = indices.inorder().head;
        while(headDestinos != null){
            if (headDestinos.data.getRight() != null) {
                NodeRuta tmp = headDestinos.data.getRight();
                while(tmp != null){
                    rutas.add(tmp.ruta);
                    tmp = tmp.right;
                }
            }
            headDestinos = headDestinos.next;
        }
        return rutas;
    }
    
    //Arrgelo: Se podria hacer que remove in column y remove revice si head es null por separado para que se pueda llamar a removeInColumn desdes afuera de remove
    public String remove(int keyRow, int keyColumn){
        //Revisa que keyRow y keyColumn tengan el orden correcto
        if (keyRow < keyColumn) {
            int aux = keyRow;
            keyRow = keyColumn;
            keyColumn = aux;
        }
        
        Destino headRow = indices.search(keyRow);
        Destino headCol = indices.search(keyColumn);
        
        if (headRow == null || headCol == null) {
            return ("no se puede remover la ruta de la matriz. Una de las llaves destino no existe en arbol indices\n" + keyRow + ", " + keyColumn);
        }
        
        NodeRuta tmp = headRow.getRight();
        //Si esta vacia la fila
        if (tmp == null) {
            return "No existe la ruta en la matriz: \n" + keyRow + ", " + keyColumn;
        }
        //Caso especial si remueve la cabeza
        if (tmp.getKeyCol() == keyColumn) {
            headRow.setRight(tmp.right);
            return removeInColumn(headCol, keyRow);
        }
        
        while(tmp.right != null){
            if (tmp.right.getKeyCol() == keyColumn) {
                tmp.right = tmp.right.right;
                return removeInColumn(headCol, keyRow);
            }
            tmp = tmp.right;
        }
        
        return "No existe la ruta en la matriz: \n" + keyRow + ", " + keyColumn;
    }
    
    private String removeInColumn(Destino headCol, int keyRow){
        NodeRuta tmp = headCol.getDown();
        //Si esta vacia la fila
        if (tmp == null) {
            return "No existe la ruta en la matriz: \n" + keyRow + ", " + headCol.getKey();
        }
        //Caso especial si remueve la cabeza
        if (tmp.getKeyRow() == keyRow) {
            headCol.setDown(tmp.down);
            return null;
        }
        
        while(tmp.down != null){
            if (tmp.down.getKeyRow() == keyRow) {
                tmp.down = tmp.down.down;
                return null;
            }
            tmp = tmp.right;
        }
        
        //si este metodo se llama desde remove() antes nunca se deberia de pasar por aqui
        return "No existe la ruta en la columna: \n" + keyRow + ", " + headCol.getKey();
    }
    
    private String getNodeLabel(NodeRuta n){
        Ruta r = n.ruta;
        
        return ("Q." + r.getCosto() + "\\nh: " + r.getTiempoVuelo().toHours() + "\\nP: " + r.getPiloto(true));
    }
    
    private String getNodeName(NodeRuta n){
        return "V" + Integer.toString(n.getKeyRow()) + "_" + Integer.toString(n.getKeyCol());
    }
    
    private String getNodeLabel(SimpleList<Destino>.Node n){
        return Integer.toString(n.data.getKey()) /*+ n.data.getNombre()*/;
    }
    
    private String getNodeName(SimpleList<Destino>.Node n, boolean rowOrCol){
        String s;
        if (rowOrCol == ROW) {
            s = "R";
        }
        else{
            s = "C";
        }
        return s + Integer.toString(n.data.getKey());
    }
    
    private String getGroup(NodeRuta tmpRow) {
        return String.valueOf(tmpRow.getKeyCol());
    }
    
    private String getGroup(SimpleList<Destino>.Node tmpHead, boolean rowOrCol) {
        if (rowOrCol == ROW) {
            return "-1";
        }
        return String.valueOf(tmpHead.data.getKey());
    }
    
    //Estos dos son basicamente enums chafas
    private static final boolean ROW = true;
    private static final boolean COL = !ROW;
    
    //Se puede mejorar limitando las llamadas a getNodeName si guardamos en string 
    //Error si destinos esta vacio!
    //chapucead :( lo ultimo
    public SimpleList<String> getDotLinesMatriz(){
        SimpleList<String> lines = new SimpleList<String>();
        SimpleList<Destino> destinos = indices.inorder();
        String rankMtS = "mt;";
        String rankS = "";
        String ejesHeadRow = "mt";
        String ejesHeadCol = "mt";
        
        lines.add("digraph matrixBTree{");
        lines.add("subgraph clusterMatrix{");
        lines.add("style = invis;\n");
        lines.add("node[shape = box];\n");
        lines.add("mt[ label = \"Mt\", width = 1.5, style = invis, fillcolor = firebrick1, group = -1 ];");
        
        if (destinos.getSize() < 1) {
            lines.add("}");
            return lines;
        }
        
        SimpleList<Destino>.Node tmpHead = destinos.head;
        
        while(tmpHead != null){
            NodeRuta tmpRow = tmpHead.data.getRight();//El que se itera a travez de una misma fila
            if (tmpRow != null) {
                rankS = getNodeName(tmpHead, ROW) + ";";
                lines.add(getNodeName(tmpHead, ROW) + "[label=\"" + getNodeLabel(tmpHead)+"\"" + "group = " + getGroup(tmpHead, ROW) + "];");
                lines.add(getNodeName(tmpHead, ROW) + "->" + getNodeName(tmpRow) + ";");
                ejesHeadRow += "->" + getNodeName(tmpHead, ROW);//Se hace el eje con mt
                while(tmpRow != null){
                    //Se declara el nodo
                    lines.add(getNodeName(tmpRow)+"[label=\"" + getNodeLabel(tmpRow)+"\"" + "group=" + getGroup(tmpRow) + "];");
                    //Se hacen sus ejes
                    if (tmpRow.right != null) {
                        lines.add(getNodeName(tmpRow)+"->"+getNodeName(tmpRow.right)+";");
                    }
                    if (tmpRow.down != null) {
                        lines.add(getNodeName(tmpRow)+"->"+getNodeName(tmpRow.down)+";");
                    }
                    rankS += getNodeName(tmpRow) + ";";//Se agrega al acumulador actual de rankS (rank same)
                    tmpRow = tmpRow.right;
                }
                lines.add("{rank=same " + rankS + "}");
            }
            NodeRuta tmpCol = tmpHead.data.getDown();//El que se itera a travez de una misma columna
            if (tmpCol != null) {
                lines.add(getNodeName(tmpHead, COL) + "[label=\"" + getNodeLabel(tmpHead)+"\"" + "group = " + getGroup(tmpHead, COL) + "];");
                lines.add(getNodeName(tmpHead, COL) + "->" + getNodeName(tmpCol) + ";");
                ejesHeadCol += "->" + getNodeName(tmpHead, COL);//Se hace el eje con mt
                rankMtS += getNodeName(tmpHead, COL) + ";";
            }
            tmpHead = tmpHead.next;
        }
        lines.add(ejesHeadRow + "[style=invis];");
        lines.add(ejesHeadCol + "[style=invis];");
        lines.add("{rank = same " + rankMtS + "}");
        lines.add("}");
        
        //Agrega el cluster de arbolCol
        SimpleList<String> linesColTree = indices.getDotLines("col");
        linesColTree.head.data = linesColTree.head.data.replace("digraph", "subgraph");
        linesColTree.head.data = linesColTree.head.data.replace("colBTree", "clustercolBTree");
        lines.add(linesColTree);
        SimpleList<String> linesRowTree = indices.getDotLines("row");
        linesRowTree.head.data = linesRowTree.head.data.replace("digraph", "subgraph");
        linesRowTree.head.data = linesRowTree.head.data.replace("rowBTree", "clusterrowBTree");
        lines.add(linesRowTree);
        
        //Las relaciones entre arboles y matriz
        lines.add("edge[dir = none]");
        tmpHead = destinos.head;
        boolean constraintRow = false;
        while(tmpHead != null){
            if (tmpHead.data.getRight() != null) {
                String idPagina = indices.getPageCode(tmpHead.data.getKey());
                //Pendiente: Aqui agregar si el ultimo eje es false
                lines.add("rowpage" + idPagina + ":" + tmpHead.data.getKey() + "->" + getNodeName(tmpHead, ROW) + "[constraint=" + constraintRow + "];");
                constraintRow = !constraintRow;
            }
            if (tmpHead.data.getDown() != null) {
                String idPagina = indices.getPageCode(tmpHead.data.getKey());
                lines.add("colpage" + idPagina + ":" + tmpHead.data.getKey() + "->" + getNodeName(tmpHead, COL) + "[constraint=true];");
            }
            tmpHead = tmpHead.next;
        }
        
        lines.add("}");
        
        return lines;
    }
    
    public SimpleList<String> getDotLinesGrafo(){
        SimpleList<String> lines = new SimpleList<String>();
        SimpleList<Destino> destinos = indices.inorder();
        
        lines.add("digraph matrixGrafo{");
        lines.add("style = invis;\n");
        lines.add("edge[dir=none];\n");
        lines.add("layout=fdp;");
        
        if (destinos.getSize() < 1) {
            lines.add("}");
            return lines;
        }
        
        SimpleList<Destino>.Node tmpHead = destinos.head;
        
        while(tmpHead != null){
            lines.add(getNodeName(tmpHead.data) + "[label=\"" + getNodeLabel(tmpHead.data)+"\"" + "];");
            if (tmpHead.data.getRight() != null) {
                NodeRuta tmp = tmpHead.data.getRight();
                while(tmp != null){
                    lines.add(getNodeName(tmpHead.data) + "->" + getNodeName(tmp.ruta.getPuntoB()) + ";");
                    tmp = tmp.right;
                }
            }
            tmpHead = tmpHead.next;
        }
        lines.add("}");
        
        return lines;
    }
    
    private SimpleList<SimpleList<Destino>> returnPaths;
    public SimpleList<SimpleList<Destino>> getPaths(int startKey, int endKey){
        returnPaths = new SimpleList<SimpleList<Destino>>();
        if (indices.search(startKey) == null) {
            System.out.println("No existe un destino con esa llave keyStart");
            return returnPaths;
        }
        if (indices.search(endKey) == null) {
            System.out.println("No existe un destino con esa llave keyEnd");
            return returnPaths;
        }
        
        SimpleList<Integer> visitedNodes = new SimpleList<Integer>();
        SimpleList<Destino> currentPath = new SimpleList<Destino>();
        
        getPaths(startKey, endKey, visitedNodes, currentPath);//ALTERA EL VALOR DE returnPaths
        
        return returnPaths;
    }
    
    private void getPaths(int key, int endKey, SimpleList<Integer> visitedNodes, SimpleList<Destino> currentPath){
        // Mark the current node
        visitedNodes.add(new Integer(key));
        currentPath.add(indices.search(key));
        
        if (key == endKey) 
        {
            returnPaths.add(new SimpleList<Destino>(currentPath));
        }
        else{
            SimpleList<Destino> nodosAdyacentes = indices.search(key).getAdjDestinos();
            SimpleList<Destino>.Node tmp = nodosAdyacentes.head;
            while (tmp != null) {
                if (!visitedNodes.contains(tmp.data.getKey())) {
                    getPaths(tmp.data.getKey(), endKey, visitedNodes, currentPath);
                }
                tmp = tmp.next;
            }
        }
        // Mark the current node
        visitedNodes.remove(new Integer(key));
        currentPath.remove(indices.search(key));
    }
    
    public static void main(String args[]){
        BTree<Destino> indices = new BTree<Destino>();
        indices.add(new Destino(21, "Guate"));
        indices.add(new Destino(3, "Honduras"));
        indices.add(new Destino(5, "Nicaragua"));
        
        indices.add(new Destino(33, "Us"));
        indices.add(new Destino(46, "Salvador"));
        indices.add(new Destino(69, "CostaRica"));
        indices.add(new Destino(75, "Canada"));
        
        indices.add(new Destino(89, "Mexico"));
        
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
        
        SimpleList<String>.Node n =  m.getDotLinesMatriz().head;
        SimpleList<SimpleList<Destino>> recorrido = m.getPaths(33, 3);
        SimpleList<SimpleList<Destino>>.Node listNode = recorrido.head;
        while(listNode != null){
            SimpleList<Destino>.Node destinoNode = listNode.data.head;
            while(destinoNode != null){
                System.out.print(destinoNode.data.getKey() + " ");
                destinoNode = destinoNode.next;
            }
            System.out.println();
            listNode = listNode.next;
        }
        SimpleList<String> linesGrafo = m.getDotLinesGrafo();
        for (int i = 0; i < linesGrafo.getSize(); i++) {
            System.out.println(linesGrafo.get(i));
        }
    }

    private String getNodeName(Destino data) {
        return data.getName().replace(" ", "_") + data.getKey();
    }

    private String getNodeLabel(Destino data) {
        return data.getName();
    }
}
