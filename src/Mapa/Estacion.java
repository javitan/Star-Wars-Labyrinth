/**
 * Implementación de la clase Estacion.
 * @version 2.0
 * @author <b> Planet Express </b><br>
 * Nombre y apellidos: Javier Garcia Valencia
 * Curso: 2o GIIIS
 * Asignatura Desarrollo de Programas<br/>
 * Curso 15/16
 */

package Mapa;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import Personajes.Personaje;

public class Estacion {

	private int id;
	private Puerta puerta;
	private Queue<Personaje> colaPersonajes;
	private LinkedList<Midicloriano> listaMidiclorianos;

	public Estacion() {
		id = -1;
		puerta = null;
	}

	public Estacion(int _id) {
		id = _id;
		puerta = null;
		colaPersonajes = new LinkedList<Personaje>();
		listaMidiclorianos = new LinkedList<Midicloriano>();
	}

	public void ponerPuerta(Puerta _puerta) {
		puerta = _puerta;
	}
	
	public int obtenerIdEstacion(){
		return id;
	}
	
	public LinkedList<Midicloriano> obtenerListaMidiclorianos(){
		return listaMidiclorianos;
	}
	
	public Puerta obtenerPuerta(){
		return puerta;
	}

	public void insertarPersonaje(Personaje _pers) {
		colaPersonajes.add(_pers);
	}
	
	public void borrarPersonaje(Personaje _pers){
		colaPersonajes.remove(_pers);
	}
	
	public Queue<Personaje> obtenerColaPersonajes(){
		return colaPersonajes;
	}
	
	public Personaje obtenerPrimero(){
		return colaPersonajes.peek();
	}
	
	public void insertarMidicloriano(Midicloriano _midi){
		listaMidiclorianos.addLast(_midi);
		Collections.sort(listaMidiclorianos); //se supone que ordena la lista (?)
	}

	// private List<Personaje> listaPersonajes;
	//
	// public Estacion(){
	// listaPersonajes = new List<Personaje>();
	// }
	//
	// public void meterPersonaje(Personaje pers){
	// listaPersonajes.addLast(pers);
	// }
	//
	// public void pintar() {
	// for (int i = 0; i < listaPersonajes.size(); i++){
	// System.out.println(listaPersonajes.get(i));
	// }
	// }
	//
	// public Personaje obtenerUltimo() {
	// Personaje aux = new Personaje();
	// aux = listaPersonajes.getLast();
	// listaPersonajes.removeLast();
	// return aux;
	// }
	//
	// public int elementos() {
	// return listaPersonajes.size();
	// }

}
