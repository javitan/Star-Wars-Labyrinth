/**
 * Implementación de la clase FamiliaReal.
 * @version 4.0
 * @author <b> Planet Express </b><br>
 * Nombre y apellidos: Javier García Valencia
 * Curso: 3º GIIIS
 * Asignatura Desarrollo de Programas<br/>
 * Curso 15/16
 */

package Personajes;

public class FamiliaReal extends Personaje {

	/**
	 * Contructor parametrizado de la clase FamiliaReal.
	 * 
	 * @param _nombre
	 *            String con el nombre del personaje
	 * @param _marca
	 *            char con la letra correspondiente a la marca
	 * @param _turno
	 *            entero con el turno en el que empezará el personaje
	 */
	public FamiliaReal(String _nombre, char _marca, int _turno) {
		nombre = _nombre;
		marca = _marca;
		turno = _turno;
		tipo = "familiareal";
	}

	/**
	 * Método toString de la clase FamiliaReal para pintar el personaje por
	 * pantalla. Llama al de la clase madre (Personaje).
	 * 
	 */
	public String toString() {
		return super.toString();
	}

}
