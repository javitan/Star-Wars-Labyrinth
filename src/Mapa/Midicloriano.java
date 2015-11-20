/**
 * Implementación de la clase Midicloriano.
 * @version 2.0
 * @author <b> Planet Express </b><br>
 * Nombre y apellidos: Javier Garcia Valencia
 * Curso: 2o GIIIS
 * Asignatura Desarrollo de Programas<br/>
 * Curso 15/16
 */

package Mapa;

public class Midicloriano implements Comparable<Midicloriano> {

	private Integer id;

	public Midicloriano() {
		id = 0;
	}

	public Midicloriano(int _id) {
		id = _id;
	}

	public Integer obtenerIdMidi() {
		return id;
	}

	public String toString() {
		String cadena = "";
		cadena = cadena + id;
		return (cadena);
	}

	public int compareTo(Midicloriano midi2) {
		if (this == midi2) {
			return 0;
		}
		return (this.id.compareTo(midi2.obtenerIdMidi()));
	}

}
