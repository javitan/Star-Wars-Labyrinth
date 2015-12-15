/**
 * Implementación de la clase Midicloriano.
 * @version 4.0
 * @author <b> Planet Express </b><br>
 * Nombre y apellidos: Javier García Valencia
 * Curso: 3º GIIIS
 * Asignatura Desarrollo de Programas<br/>
 * Curso 15/16
 */

package Mapa;

public class Midicloriano implements Comparable<Midicloriano> {

	private Integer id;

	/**
	 * Constructor parametrizado de la clase Midicloriano. Crea un midicloriano
	 * con el id que se le pasa por parámetro.
	 * 
	 * @param _id
	 *            entero que le da el valor al número del midicloriano que se
	 *            creará
	 */
	public Midicloriano(int _id) {
		id = _id;
	}

	/**
	 * Método que devuelve el número del midicloriano.
	 * 
	 * @return id entero con el número del midicloriano
	 */
	public Integer obtenerIdMidi() {
		return id;
	}

	/**
	 * Método toString de la clase Midicloriano para imprimir el midicloriano
	 * (con su id) por pantalla.
	 * 
	 */
	public String toString() {
		String cadena = "";
		cadena = cadena + id;
		return (cadena);
	}

	/**
	 * Método que copara un midicloriano con otro dado para ver así si un
	 * midicloriano es igual a otro.
	 * 
	 */
	public int compareTo(Midicloriano midi2) {
		if (this == midi2) {
			return 0;
		}
		return (this.id.compareTo(midi2.obtenerIdMidi()));
	}

}
