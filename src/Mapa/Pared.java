/**
 * Implementación de la clase Pared.
 * @version 4.0
 * @author <b> Planet Express </b><br>
 * Nombre y apellidos: Javier García Valencia
 * Curso: 3º GIIIS
 * Asignatura Desarrollo de Programas<br/>
 * Curso 15/16
 */

package Mapa;

public class Pared {

	private int pared1;
	private int pared2;

	/**
	 * Constructor parametrizado de la clase Pared.
	 * 
	 * @param _pared1
	 *            Id de la sala 1 de la pared
	 * @param _pared2
	 *            Id de la sala 2 de la pared
	 */
	public Pared(int _pared1, int _pared2) {
		pared1 = _pared1;
		pared2 = _pared2;
	}

	/**
	 * Método que devuelve la sala 1 de la pared.
	 * 
	 * @return número id de la sala 1
	 */
	public int devolverPared1() {
		return pared1;
	}

	/**
	 * Método que devuelve la sala 2 de la pared.
	 * 
	 * @return número id de la sala 2
	 */
	public int devolverPared2() {
		return pared2;
	}

	/**
	 * Método toString de la clase Pared para imprimirla por pantalla, con las
	 * paredes separadas por comas.
	 * 
	 */
	public String toString() {
		return ("(" + pared1 + ", " + pared2 + ")");
	}

}
