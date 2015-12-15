/**
 * Implementación de la clase Imperial.
 * @version 4.0
 * @author <b> Planet Express </b><br>
 * Nombre y apellidos: Javier García Valencia
 * Curso: 3º GIIIS
 * Asignatura Desarrollo de Programas<br/>
 * Curso 15/16
 */

package Personajes;

import Mapa.Estacion;
import Mapa.Midicloriano;
import Mapa.Puerta;

public class Imperial extends Personaje {

	/**
	 * Constructor parametrizado de la clase Imperial.
	 * 
	 * @param _nombre
	 *            String con el nombre del personaje
	 * @param _marca
	 *            char con la letra correspondiente a la marca del personaje
	 * @param _turno
	 *            entero con el turno en el que comenzará el personaje
	 */
	public Imperial(String _nombre, char _marca, int _turno) {
		nombre = _nombre;
		marca = _marca;
		turno = _turno;
		tipo = "imperial";
		insertarMidiclorianosImperial();
	}

	/**
	 * Método que inserta los midiclorianos de inicio en el personaje. Inserta
	 * los impares que son los que soltará por las salas.
	 * 
	 */
	public void insertarMidiclorianosImperial() {
		for (int i = 1; i <= 29; i++) {
			if (i % 2 != 0) {
				Midicloriano midi = new Midicloriano(i);
				listaMidi.add(midi);
			}
		}
	}

	/**
	 * Método que devuelve la dirección Dir a la que se moverá el personaje.
	 * Como este personaje tiene un movimiento cíclico, en lugar de borrar la
	 * dirección que se usará, se volverá a encolar, haciendo que siempre siga
	 * la misma ruta.
	 * 
	 */
	public Dir obtenerMovimiento() {
		Dir movimiento = movimientos.peek();
		movimientos.remove();
		movimientos.add(movimiento);
		return movimiento;
	}

	/**
	 * Método que realiza la acción cuando se encuentra en la sala de la puerta.
	 * Concretamente la acción que realiza es la de comprobar si está abierta la
	 * puerta; en caso afirmativo, la cerrará.
	 * 
	 */
	public void accionPuerta(Estacion estacion) {
		if (estacion.obtenerPuerta() != null && estacion.obtenerPuerta().obtenerBoolPuerta()) {
			Puerta.obtenerInstancia().configurarPuerta();
		}
	}

	/**
	 * Método que realizará en las salas que no son la de la puerta de salida.
	 * Concretamente, si el identificador de la sala en la que se encuentra es
	 * par, soltará un midicloriano; el último que le dieron al principio.
	 * 
	 */
	public void accionEstacion(Estacion estacion) {
		if (!listaMidi.isEmpty() && estacion.obtenerIdEstacion() % 2 == 0) {
			Midicloriano midi = listaMidi.getLast();
			estacion.insertarMidicloriano(midi); // inserta el último
			listaMidi.removeLast();
		}
	}

	/**
	 * Método toString de la clase Imperial para pintar el personaje por
	 * pantalla. Llama al de la clase madre (Personaje).
	 * 
	 */
	public String toString() {
		return super.toString();
	}

}
