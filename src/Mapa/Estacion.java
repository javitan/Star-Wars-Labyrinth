/**
 * Implementación de la clase Estacion.
 * @version 4.0
 * @author <b> Planet Express </b><br>
 * Nombre y apellidos: Javier García Valencia
 * Curso: 3º GIIIS
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

	/**
	 * Constructor parametrizado de la clase Estacion. Se introduce el id como
	 * parámetro para darle el id a la estación.
	 * 
	 * @param _id
	 *            entero que indicará el id de la estación
	 */
	public Estacion(int _id) {
		id = _id;
		marca = _id;
		puerta = null;
		colaPersonajes = new LinkedList<Personaje>();
		listaMidiclorianos = new LinkedList<Midicloriano>();
	}

	/**
	 * Método que pone la puerta en la estación. La puerta se pasa por
	 * parámetro.
	 * 
	 * @param _puerta
	 *            Puerta que se introducirá. Si la sala no tiene puerta, el
	 *            valor será null.
	 */
	public void ponerPuerta(Puerta _puerta) {
		puerta = _puerta;
	}

	/**
	 * Método que devuelve el id de la estación.
	 * 
	 * @return entero que indica el id
	 */
	public int obtenerIdEstacion() {
		return id;
	}

	/**
	 * Método que devuelve la marca de la estación.
	 * 
	 * @return entero con la marca de la estación.
	 */
	public int obtenerMarca() {
		return marca;
	}

	/**
	 * Método que pone la marca que se introduce por parámetro a la estación.
	 * 
	 * @param _marca
	 *            entero con el número de la marca que se quiere insertar o
	 *            cambiar.
	 */
	public void ponerMarca(int _marca) {
		marca = _marca;
	}

	/**
	 * Método que obtiene la Puerta de la estación. En caso de que no haya
	 * puerta, devolverá null.
	 * 
	 * @return Puerta con la puerta que haya en la sala o null si no tiene
	 *         puerta asignada.
	 */
	public Puerta obtenerPuerta() {
		return puerta;
	}

	/**
	 * Método que inserta un personaje en la cola de personajes de la estación.
	 * 
	 * @param _pers
	 *            Personaje que se quiere introducir en la estación
	 */
	public void insertarPersonaje(Personaje _pers) {
		colaPersonajes.add(_pers);
	}

	/**
	 * Método que borra un personaje de la estación.
	 * 
	 * @param _pers
	 *            Personaje concreto que se quiere borrar
	 */
	public void borrarPersonaje(Personaje _pers) {
		colaPersonajes.remove(_pers);
	}

	/**
	 * Método que devuelve la cola de personajes que se encuentran en la
	 * estación.
	 * 
	 * @return cola con los personajes de la estación
	 */
	public Queue<Personaje> obtenerColaPersonajes() { // TODO
		return colaPersonajes;
	}

	/**
	 * Método que obtiene el personaje primero de la cola de personajes.
	 * 
	 * @return Personaje que primero entró en la estación
	 */
	public Personaje obtenerPrimero() {
		return colaPersonajes.peek();
	}

	/**
	 * Método que devuelve el primer midicloriano de la lista de midiclorianos
	 * que existen dentro de la estación.
	 * 
	 * @return Midicloriano primero de la lista de midiclorianos
	 */
	public Midicloriano obtenerPrimerMidicloriano() {
		return listaMidiclorianos.getFirst();
	}

	/**
	 * Método que borra el primer midicloriano de la lista de midiclorianos de
	 * dentro de la estación.
	 * 
	 */
	public void borrarPrimerMidicloriano() {
		listaMidiclorianos.removeFirst();
	}

	/**
	 * Método que inserta el midicloriano que entra como parámetro en la lista
	 * de midiclorianos de la estación. Después ordena la lista por el orden de
	 * los midiclorianos.
	 * 
	 * @param _midi
	 *            midicloriano que se va a insertar
	 */
	public void insertarMidicloriano(Midicloriano _midi) {
		listaMidiclorianos.addLast(_midi);
		Collections.sort(listaMidiclorianos);
	}

	/**
	 * Método que devuelve una cadena con los midiclorianos que contiene la
	 * estación.
	 * 
	 * @return String con los midiclorianos ordenados y separados por espacios
	 */
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
