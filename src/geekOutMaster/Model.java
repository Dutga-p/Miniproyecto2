package geekOutMaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
/**
 * Clase Model
 * @autor Mayra Alejandra Sanchez - mayra.alejandra.sanchez@correounivalle.edu.co - 202040506
 * @autor Brayan Stiven Sanchez - brayan.sanchez.leon@correounivalle.edu.co - 202043554
 * @version 1.0.0 fecha 17/1/2022
 */
public class Model {

    private int dadoRandom;
    private ArrayList<Dado> Dado;
    private ArrayList<Dado> DadoInactivos;
    private ArrayList<Dado> DadoUtilizados;
    private HashMap<String, String> nombreAAccion; // Sirve para identificar la accion por medio de su nombre
    private HashMap<String, String> nombreAEstado; // Sirve para identificar cuales Dado son activos e inactivos por medio de su nombre
    private HashMap<String, Dado> nombreAObjeto; // Sirve para retornar el objeto por medio de su nombre

    public Model(){
        nombreAAccion = new HashMap<>();
        nombreAObjeto = new HashMap<>();
        nombreAEstado = new HashMap<>();
        DadoInactivos = new ArrayList<>();
        DadoUtilizados = new ArrayList<>();
    }
    /**
     * @param nombreArray
     * Retorna el array ingresado
     * @return Arraylist
     */
    public ArrayList listaDado(String nombreArray){
        ArrayList<Dado> auxiliar;
        if(nombreArray == "activos"){
            auxiliar = Dado;
        }else{
            if(nombreArray == "inactivos"){
                auxiliar = DadoInactivos;
            }else{
                auxiliar = DadoUtilizados;
            }
        }
        return auxiliar;
    }

    /**
     * Remueve un dado de la zona de Dado activos y lo adiciona a la zona de utilizados
     * @param nombreDado
     */
    public void DadoUtilizados(String nombreDado){
        for(int dado=0; dado < Dado.size(); dado++){
            nombreAObjeto.put(Dado.get(dado).getNombreDado(), Dado.get(dado));
        }

        DadoUtilizados.add(nombreAObjeto.get(nombreDado));
        nombreAObjeto.get(nombreDado).setActivoInactivo("utilizado");
        Dado.remove(nombreAObjeto.get(nombreDado));
        identidadDado("activos");
        identidadDado("utilizados");
        identidadDado("inactivos");
        nombreAObjeto.clear();
    }
    /**
     * Inicio del juego
     */
    public void lanzamientoDado(){

        //Creación de los 10 Dado
        Dado = new ArrayList<>();
        for(int dado=0; dado < 10; dado++){
            Dado.add(new Dado());
        }

        asignacionAcciones(); // Asigna todas las acciones del ArrayList Dado
        setActivo(); // Establece estado activo a todos los Dado
        DadoInactivos(); // Selecciona 3 Dado inactivos y los borra del Arraylist Dado
        identidadDado("activos"); // Actualiza los nombres del ArrayList Dado
        identidadDado("inactivos"); // Actualiza los nombres del ArrayList DadoInactivos
    }

    /**
     * Escoge 3 Dado inactivos al azar
     */
    public void DadoInactivos(){
        for(int inactivo=0; inactivo < 3; inactivo++){
            Random random = new Random();
            dadoRandom = random.nextInt(Dado.size());
            Dado.get(dadoRandom).setActivoInactivo("inactivo");
            DadoInactivos.add(Dado.get(dadoRandom));
            Dado.remove(dadoRandom);
        }

        identidadDado("activos");
        identidadDado("inactivos");
    }

    /**
     * Asigna el nombre a cada dado dependiendo del ArrayList
     * @param array
     */
    public void identidadDado(String array){
        if(array == "activos"){
            for(int dado=0; dado < Dado.size(); dado++){
                Dado.get(dado).setNombreDado("dado" + String.valueOf(dado+1));
            }
        }else{
            if(array == "inactivos"){
                for(int dado=0; dado < DadoInactivos.size(); dado++){
                    DadoInactivos.get(dado).setNombreDado("dado" + String.valueOf(dado+1));
                }
            }else{
                for(int dado=0; dado < DadoUtilizados.size(); dado++){
                    DadoUtilizados.get(dado).setNombreDado("dado" + String.valueOf(dado+1));
                }
            }
        }
    }

    /**
     * Establece el estado inicial (activo) del ArrayList Dado
     */
    public void setActivo(){
        for(int dado=0; dado < Dado.size(); dado++){
            Dado.get(dado).setActivoInactivo("activo");
        }
    }

    /**
     * Asigna las acciones de cada dado dependiendo de su numero de accion
     */
    public void listaAcciones(){
        for(int numero=0; numero < Dado.size(); numero++){
            switch(Dado.get(numero).getNumAccion()){
                case 1:
                    Dado.get(numero).setAccion("mepple");
                    break;
                case 2:
                    Dado.get(numero).setAccion("superheroe");
                    break;
                case 3:
                    Dado.get(numero).setAccion("dragon");
                    break;
                case 4:
                    Dado.get(numero).setAccion("corazon");
                    break;
                case 5:
                    Dado.get(numero).setAccion("cohete");
                    break;
                case 6:
                    Dado.get(numero).setAccion("42");
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Asigna las acciones a cada dado
     */
    public void asignacionAcciones(){
        for(int i=0; i < Dado.size(); i++){
            Dado.get(i).setNumAccion();
        }

        listaAcciones();
    }

    /**
     * Retorna la acción de un dado, dependiendo del array donde este
     * @param _nombreDado
     * @param nombreArray
     * @return accionDado
     */
    public String getAccionDado(String _nombreDado, String nombreArray){
        String accionDado = "";
        if(nombreArray == "activos"){
            for(int dado=0; dado < Dado.size(); dado++){
                nombreAAccion.put(Dado.get(dado).getNombreDado(), Dado.get(dado).getAccion());
            }
            accionDado = nombreAAccion.get(_nombreDado);
        }else{
            for(int dado=0; dado < DadoInactivos.size(); dado++){
                nombreAAccion.put(Dado.get(dado).getNombreDado(), Dado.get(dado).getAccion());
            }
            accionDado = nombreAAccion.get(_nombreDado);
        }
        return accionDado;
    }

    /**
     * Asigna una nueva accion al dado seleccionado (accion del mepple)
     * @param nombreDado
     */
    public void accionMepple(String nombreDado){
        for(int dado=0; dado < Dado.size(); dado++){
            nombreAObjeto.put(Dado.get(dado).getNombreDado(), Dado.get(dado));
        }

        nombreAObjeto.get(nombreDado).setNumAccion();
        listaAcciones();
        nombreAObjeto.clear();
    }

    /**
     * La cara del dado cambia a su cara opuesta (accion del superheroe)
     * @param nombreDado
     */
    public void accionSuperHeroe(String nombreDado){
        for(int dado=0; dado < Dado.size(); dado++){
            nombreAObjeto.put(Dado.get(dado).getNombreDado(), Dado.get(dado));
        }

        if(nombreAObjeto.get(nombreDado).getAccion() == "mepple"){
            nombreAObjeto.get(nombreDado).setNumAccionNoAleatorio(5);
            listaAcciones();
        }else{
            if(nombreAObjeto.get(nombreDado).getAccion() == "superheroe"){
                nombreAObjeto.get(nombreDado).setNumAccionNoAleatorio(3);
                listaAcciones();
            }else{
                if(nombreAObjeto.get(nombreDado).getAccion() == "dragon"){
                    nombreAObjeto.get(nombreDado).setNumAccionNoAleatorio(2);
                    listaAcciones();
                }else{
                    if(nombreAObjeto.get(nombreDado).getAccion() == "corazon"){
                        nombreAObjeto.get(nombreDado).setNumAccionNoAleatorio(6);
                        listaAcciones();
                    }else{
                        if(nombreAObjeto.get(nombreDado).getAccion() == "cohete"){
                            nombreAObjeto.get(nombreDado).setNumAccionNoAleatorio(1);
                            listaAcciones();
                        }else{
                            if(nombreAObjeto.get(nombreDado).getAccion() == "42"){
                                nombreAObjeto.get(nombreDado).setNumAccionNoAleatorio(4);
                                listaAcciones();
                            }
                        }
                    }
                }
            }
        }
        nombreAObjeto.clear();
    }

    /**
     * Se activa el dado seleccionado de la zona de inactivos (accion del corazon)
     * @param nombreDado
     */
    public void accionCorazon(String nombreDado){
        for(int dado=0; dado < DadoInactivos.size(); dado++){
            nombreAObjeto.put(DadoInactivos.get(dado).getNombreDado(), DadoInactivos.get(dado));
        }

        nombreAObjeto.get(nombreDado).setNumAccion();
        nombreAObjeto.get(nombreDado).setActivoInactivo("activo");
        Dado.add(nombreAObjeto.get(nombreDado));
        listaAcciones();
        DadoInactivos.remove(nombreAObjeto.get(nombreDado));
        identidadDado("activos");
        identidadDado("inactivos");
        nombreAObjeto.clear();
    }

    /**
     * Convierte el dado seleccionado en inactivo (accion del cohete)
     * @param nombreDado
     */
    public void accionCohete(String nombreDado){
        for(int dado=0; dado < Dado.size(); dado++){
            nombreAObjeto.put(Dado.get(dado).getNombreDado(), Dado.get(dado));
        }

        nombreAObjeto.get(nombreDado).setActivoInactivo("inactivo");
        DadoInactivos.add(nombreAObjeto.get(nombreDado));
        Dado.remove(nombreAObjeto.get(nombreDado));
        identidadDado("activos");
        identidadDado("inactivos");
        nombreAObjeto.clear();
    }
}
