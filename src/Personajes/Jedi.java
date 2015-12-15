/**
 * Implementación de la clase Jedi.
 * @version 4.0
 * @author <b> Planet Express </b><br>
 * Nombre y apellidos: Javier García Valencia
 * Curso: 3º GIIIS
 * Asignatura Desarrollo de Programas<br/>
 * Curso 15/16
 */

package Personajes;

import java.io.IOException;

public class Jedi extends Personaje {

	/**
	 * Constructor parametrizado de la clase Jedi.
	 * 
	 * @param _nombre
	 *            String con el nombre del personaje
	 * @param _marca
	 *            char con la letra correspondiente a la marca del personaje
	 * @param _turno
	 *            entero con el turno que empezará el personaje
	 * @throws IOException
	 */
	public Jedi(String _nombre, char _marca, int _turno) throws IOException {
		nombre = _nombre;
		marca = _marca;
		turno = _turno;
		tipo = "jedi";
		manoDerecha(0);
	}

	/**
	 * Método toString de la clase Jedi para pintar el personaje por pantalla.
	 * Llama al de la clase madre (Personaje).
	 * 
	 */
	public String toString() { // TODO
		return super.toString();
	}

}
