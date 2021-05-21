/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyObjects;

import java.time.Duration;
import java.util.Objects;

/**
 * No puede existir sin dos destinos
 * @author javier
 */
public class Ruta {
    public static final int VERMAN_KEY = initVermanKey();
    //punto1 y punto2 no pueden ser iguales.
    //puntoA siempre va a tener una llave mayor!
    //Los destinos de la ruta no pueden ser modificados
    private Destino puntoA;
    private Destino puntoB;
    private double costo;
    private Duration tiempoVuelo;
    //Almacena la encriptacion de piloto
    private String piloto;
    
    private static int initVermanKey(){
        return (int) (Math.random() * (0b11111111));
    }
    
    private Ruta(Destino puntoA, Destino puntoB) {
        if (puntoA.getKey() == puntoB.getKey()) {
            throw new IllegalArgumentException("No se puede agregar data null a la lista");
        }
        if (puntoB.getKey() > puntoA.getKey()) {
            this.puntoA = puntoB;
            this.puntoB = puntoA;
        }
        else{
            this.puntoA = puntoA;
            this.puntoB = puntoB;
        }
    }

    /**
     * 
     * @param puntoA
     * @param puntoB
     * @param costo
     * @param tiempoVuelo
     * @param piloto EL desencriptado (solo se guarda el encriptado)
     */
    public Ruta(Destino puntoA, Destino puntoB, double costo, Duration tiempoVuelo, String piloto) {
        this(puntoA, puntoB);
        this.costo = costo;
        this.tiempoVuelo = tiempoVuelo;
        this.piloto = encriptar(piloto);
    }
    
    public Ruta(Destino puntoA, Destino puntoB, RutaParams params){
        this(puntoA, puntoB);
        this.costo = params.costo;
        this.tiempoVuelo = params.tiempoVuelo;
        this.piloto = encriptar(params.piloto);
    }
    
    public int getKeyA(){
        return puntoA.getKey();
    }
    
    public int getKeyB(){
        return puntoB.getKey();
    }

    public Destino getPuntoA() {
        return puntoA;
    }

    public Destino getPuntoB() {
        return puntoB;
    }
    
    
    
    private String encriptar(String s){
        char[] caracteres = s.toCharArray();
        char[] caracteresEncriptados = new char[caracteres.length];
        for (int i = 0; i < caracteres.length; i++) {
            caracteresEncriptados[i] =  (char) (caracteres[i] ^ VERMAN_KEY);
        }
        s = new String(caracteresEncriptados);
        return s;
    }
    
    public String getPiloto(boolean desEncriptado){
        if (desEncriptado) {
            return encriptar(piloto);
        }
        return piloto;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ruta other = (Ruta) obj;
        if (!this.puntoA.equals(other.puntoA)) {
            return false;
        }
        if (!this.puntoB.equals(other.puntoB)) {
            return false;
        }
        return true;
    }

    public double getCosto() {
        return costo;
    }

    public Duration getTiempoVuelo() {
        return tiempoVuelo;
    }

    public String getPiloto() {
        return piloto;
    }
    
    /**
     * Setea todos los valores excepto putoA y puntoB
     */
    public void setValues(RutaParams params){
        this.costo = params.costo;
        this.tiempoVuelo = params.tiempoVuelo;
        this.piloto =encriptar(params.piloto);
    }
    
    public static class RutaParams{
        public final double costo;
        public final Duration tiempoVuelo;
        public final String piloto;

        public RutaParams(double costo, Duration tiempoVuelo, String piloto) {
            this.costo = costo;
            this.tiempoVuelo = tiempoVuelo;
            this.piloto = piloto;
        }
    }
}
