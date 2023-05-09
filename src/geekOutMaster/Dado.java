package geekOutMaster;

import java.util.Random;

/**
 * Clase Dado genera un numero del 1 al 6
 * @autor Mayra Alejandra Sanchez - mayra.alejandra.sanchez@correounivalle.edu.co - 202040506
 * @autor Brayan Stiven Sanchez - brayan.sanchez.leon@correounivalle.edu.co - 202043554
 * @version 1.0.0 fecha 17/1/2022
 */
public class Dado {
    private int numeroAccion;
    private String accion;
    private String nombreDado;
    private String activoInactivo;

    public Dado(){
    }

    /**
     * Asigna un numero aleatorio para tener una accion
     */
    public void setNumAccion(){
        int numeroAleatorio;
        Random aleatorio = new Random();
        numeroAleatorio = aleatorio.nextInt(6)+1;
        if(numeroAccion == numeroAleatorio){
            setNumAccion();
        }else{
            numeroAccion = numeroAleatorio;
        }
    }

    /**
     * Se asigna un numero manualmente para tener una accion
     * @param numero
     */
    public void setNumAccionNoAleatorio(int numero){
        numeroAccion = numero;
    }

    /**
     * Se asigna una accion
     * @param _accion
     */
    public void setAccion(String _accion){
        accion = _accion;
    }

    /**
     * Se asigna un nombre al dado
     * @param _nombre
     */
    public void setNombreDado(String _nombre){
        nombreDado = _nombre;
    }

    /**
     * Se asigna un estado (activo, inactivo, utilizado) a un dado
     * @param estado
     */
    public void setActivoInactivo(String estado){
        activoInactivo = estado;
    }

    /**
     * Retorna el numero que representa la accion
     * @return int
     */
    public int getNumAccion(){
        return numeroAccion;
    }

    /**
     * Retorna el nombre de la accion
     * @return String
     */
    public String getAccion(){
        return accion;
    }

    /**
     * Retorna el nombre del dado
     * @return String
     */
    public String getNombreDado(){
        return nombreDado;
    }
}
