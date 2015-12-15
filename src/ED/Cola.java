/**
 * Implementación de la clase Cola.
 * @version 4.0
 * @author <b> Planet Express </b><br>
 * Nombre y apellidos: Javier García Valencia
 * Curso: 3º GIIIS
 * Asignatura Desarrollo de Programas<br/>
 * Curso 15/16
 */

package ED;

public class Cola<TipoDato extends Comparable<TipoDato>> {

	/** Puntero al frente de la cola. */
	private Nodo<TipoDato> frente;

	/** Puntero al fin de la cola. */
	private Nodo<TipoDato> fin;

	private class Nodo<TipoDato extends Comparable<TipoDato>> {
		/** Dato almacenado en cada nodo */
		private TipoDato dato;
		/** Enlace al siguiente elemento */
		private Nodo<TipoDato> siguiente;

		Nodo(TipoDato dato) { // Constructor de la clase Nodo.
			this.dato = dato;
			this.siguiente = null;
		}
	} // Clase Nodo.

	/*********************************************************************************************************************/

	/**
	 * Constructor por defecto de la clase Cola.
	 */
	public Cola() {
		frente = null;
		fin = null;
	}

	/**
	 * Metodo que permite quitar un elemento de la cola.
	 */
	public void desencolar() {
		frente = frente.siguiente;
		if (frente == null) {
			fin = null;
		}
	}

	/**
	 * Metodo que permite introducir un dato en la cola.
	 * 
	 * @param dato
	 *            dato que se desea insertar.
	 */
	public void encolar(TipoDato dato) {
		Nodo<TipoDato> aux = new Nodo<TipoDato>(dato);
		if (frente == null) {
			frente = aux;
		} else {
			fin.siguiente = aux;
		}
		fin = aux;
	}

	/**
	 * Metodo que indica si la cola esta vacia o no.
	 * 
	 * @return true si esta vacia; false en caso contrario.
	 */
	public boolean estaVacia() {
		if (frente == null && fin == null) {
			return true;
		}
		return false;
	}

	/**
	 * Metodo que devuelve el primer elemento de la cola.
	 * 
	 * @return el primer elemento.
	 */
	public TipoDato primero() {
		return frente.dato;
	}

	/**
	 * Metodo toString de la clase Cola.
	 */
	public String toString() {
		String string = "";
		Nodo<TipoDato> aux = this.frente;
		for (aux = frente; aux != null; aux = aux.siguiente) {
			if (aux.siguiente != null) {
				string = string + aux.dato + " ";
			}
		}
		return string;
	}

	/**
	 * @param args
	 */
	// public static void main(String[] args) {
	// Cola<Integer> cola = new Cola<Integer>();
	// cola.encolar(1);
	// cola.encolar(2);
	// cola.encolar(3);
	// System.out.println(cola);
	// cola.desencolar();
	// System.out.println(cola);
	// }
}
