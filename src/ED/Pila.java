/**
 * Implementación de la clase Pila.
 * @version 2.0
 * @author <b> Planet Express </b><br>
 * Nombre y apellidos: Javier Garcia Valencia
 * Curso: 2o GIIIS
 * Asignatura Desarrollo de Programas<br/>
 * Curso 15/16
 */

package ED;

public class Pila<TipoDato extends Comparable<TipoDato>> {

	/** Puntero a la cima de la pila */
	private Nodo<TipoDato> cima;

	private class Nodo<TipoDato extends Comparable<TipoDato>> {
		/** Dato almacenado en cada nodo */
		private TipoDato dato;
		/** Enlace al siguiente elemento */
		private Nodo<TipoDato> siguiente;

		Nodo(TipoDato dato) { // Constructor de la clase Nodo.
			this.dato = dato;
			this.siguiente = null;
		}
	}

	/**
	 * Metodo constructor por defecto de la clase Pila.
	 */
	public Pila() {
		cima = null;
	}

	/**
	 * Metodo constructor parametrizado de la clase Pila.
	 * 
	 * @param data
	 *            es el nuevo elemento en la pila.
	 */
	public Pila(TipoDato dato) {
		Nodo<TipoDato> nodo = new Nodo<TipoDato>(dato);
		nodo.siguiente = cima;
		cima = nodo;
	}

	/**
	 * Metodo que permite insertar un dato.
	 * 
	 * @param dato
	 *            valor que se va a insertar.
	 */
	public void apilar(TipoDato dato) {
		Nodo<TipoDato> nodo = new Nodo<TipoDato>(dato);
		nodo.siguiente = cima;
		cima = nodo;
	}

	/**
	 * Elimina un dato de la pila. Se elimina el dato que esta en la cima.
	 */
	public void desapilar() {
		if (!estaVacia()) {
			cima = cima.siguiente;
		}
	}

	/**
	 * Metodo para comprobar si la pila esta vacia o no.
	 * 
	 * @return true si está vacia o false en caso contrario.
	 */
	public boolean estaVacia() {
		return (cima == null);
	}

	/**
	 * Metodo que devuelve el elemento en la cima de la pila.
	 * 
	 * @return la cima de la pila.
	 */
	public TipoDato obtenerCima() {
		return cima.dato;
	}

	public String toString() {
		String string = "";
		Nodo<TipoDato> aux = this.cima;
		for (aux = cima; aux != null; aux = aux.siguiente) {
			string = string + aux.dato + " ";
		}
		return string;
	}

	/**
	 * Metodo principal. Hace pruebas de inserciones y borrados en la pila y
	 * muestra el resultado obtenido.
	 * 
	 * @param args
	 *            Argumentos que recibe el programa principal.
	 */
	// public static void main(String args[]) {
	// Integer[] dataSet = { new Integer(2), new Integer(8), new Integer(3),
	// new Integer(4), new Integer(1), new Integer(5) };
	//
	// // Pruebas de inserciones en la pila.
	// Pila<Integer> pila = new Pila<Integer>();
	// for (int i = 0; i < dataSet.length; i++) {
	// pila.apilar(dataSet[i]);
	// }
	//
	// // Mostrando la pila.
	// // while (!pila.estaVacia()) {
	// // System.out.print(pila.obtenerCima() + " : ");
	// // pila.desapilar();
	// // }
	// //
	// // System.out.println();
	// System.out.println(pila);
	// // pila.toString();
	// }
}
