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
	private int marca;
	private Puerta puerta;
	private Queue<Personaje> colaPersonajes;
	private LinkedList<Midicloriano> listaMidiclorianos;

	public Estacion(int _id) {
		id = _id;
		marca = _id;
		puerta = null;
		colaPersonajes = new LinkedList<Personaje>();
		listaMidiclorianos = new LinkedList<Midicloriano>();
	}

	public void ponerPuerta(Puerta _puerta) {
		puerta = _puerta;
	}

	public int obtenerIdEstacion() {
		return id;
	}

	public int obtenerMarca() {
		return marca;
	}

	public void ponerMarca(int _marca) {
		marca = _marca;
	}

	// public LinkedList<Midicloriano> obtenerListaMidiclorianos() {
	// return listaMidiclorianos;
	// }

	public Puerta obtenerPuerta() {
		return puerta;
	}

	public void insertarPersonaje(Personaje _pers) {
		colaPersonajes.add(_pers);
	}

	public void borrarPersonaje(Personaje _pers) {
		colaPersonajes.remove(_pers);
	}

	public Queue<Personaje> obtenerColaPersonajes() { //TODO
		return colaPersonajes;
	}

	public Personaje obtenerPrimero() {
		return colaPersonajes.peek();
	}

	public Midicloriano obtenerPrimerMidicloriano() {
		return listaMidiclorianos.getFirst();
	}

	public void borrarPrimerMidicloriano() {
		listaMidiclorianos.removeFirst();
	}

	public void insertarMidicloriano(Midicloriano _midi) {
		listaMidiclorianos.addLast(_midi);
		Collections.sort(listaMidiclorianos);
	}

	public String mostrarMidiclorianos() {
		String cadena = " ";
		for (int i = 0; i < listaMidiclorianos.size(); i++) {
			Midicloriano midi = listaMidiclorianos.get(i);
			cadena = cadena + midi.obtenerIdMidi();
			if (midi != listaMidiclorianos.getLast()) {
				cadena = cadena + " ";
			}
		}
		return cadena;
	}
}
