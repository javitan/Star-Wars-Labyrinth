/**
 * Implementación de la clase Contrabandista.
 * @version 4.0
 * @author <b> Planet Express </b><br>
 * Nombre y apellidos: Javier García Valencia
 * Curso: 3º GIIIS
 * Asignatura Desarrollo de Programas<br/>
 * Curso 15/16
 */

package Personajes;

import java.io.IOException;

import Mapa.Galaxia;

public class Contrabandista extends Personaje {

	/**
	 * Constructor parametrizado de la clase Contrabandista.
	 * 
	 * @param _nombre
	 *            String con el nombre del personaje
	 * @param _marca
	 *            char con la letra correspondiente a su marca
	 * @param _turno
	 *            entero que indica el turno en el que empieza el personaje
	 * @throws IOException
	 */
	public Contrabandista(String _nombre, char _marca, int _turno) throws IOException {
		nombre = _nombre;
		marca = _marca;
		turno = _turno;
		tipo = "contrabandista";
		manoDerecha(calcularOrigen());
	}

	/**
	 * Método que calcula el id de la sala desde la que empieza a moverse el
	 * personaje.
	 * 
	 * @return id de la sala de comienzo
	 * @throws IOException
	 */
	private int calcularOrigen() throws IOException {
		int filas = Galaxia.obtenerInstancia().obtenerFilas();
		return (Galaxia.obtenerInstancia().devolverEstacion(filas - 1, 0).obtenerIdEstacion());
	}

	/**
	 * Método toString de la clase Contrabandista para pintar el personaje por
	 * pantalla. Llama al de la clase madre (Personaje).
	 * 
	 */
	public String toString() {
		return super.toString();
	}

}
