/**
 * Implementación de la clase Lista.
 * @version 2.0
 * @author <b> Planet Express </b><br>
 * Nombre y apellidos: Javier Garcia Valencia
 * Curso: 2o GIIIS
 * Asignatura Desarrollo de Programas<br/>
 * Curso 15/16
 */

package ED;

public class Lista<TipoDato extends Comparable<TipoDato>> {
	/** Puntero al primer elemento de la Lista */
	private Nodo<TipoDato> primero;

	/** Puntero al ultimo elemento de la Lista */
	private Nodo<TipoDato> ultimo;

	/** Tamaño de la Lista */
	Integer capacidad = 0;

	public class Nodo<TipoDato extends Comparable<TipoDato>> {
		/** Data almacenado en cada nodo */
		private TipoDato dato;
		/** Enlace al siguiente elemento */
		private Nodo<TipoDato> siguiente;
		/** Enlace al elemento anterior */
		private Nodo<TipoDato> anterior;

		public Nodo(Nodo<TipoDato> anterior, TipoDato dato, Nodo<TipoDato> siguiente) {
			this.dato = dato;
			this.siguiente = siguiente;
			this.anterior = anterior;
		}

		/**
		 * Metodo que devuelve el siguiente nodo de la Lista (para recorridos
		 * con la lista).
		 * 
		 * @return el elemento siguiente.
		 */
		public Nodo<TipoDato> siguiente() {
			return siguiente;
		}

		/**
		 * Metodo que devuelve el anterior nodo de la Lista (para recorridos con
		 * la lista).
		 * 
		 * @return el elemento anterior.
		 */
		public Nodo<TipoDato> anterior() {
			return anterior;
		}

		/**
		 * Metodo para consultar un dato.
		 * 
		 * @return el dato contenido en el nodo actual.
		 */
		public TipoDato consultar() {
			return dato;
		}
	}// Clase Nodo.

	/**
	 * Metodo constructor por defecto de la clase Lista.
	 */
	public Lista() {
		primero = ultimo = null;
		capacidad = 0;
	}

	/**
	 * Metodo constructor parametrizado de la clase Lista.
	 * 
	 * @param valor
	 *            es el nuevo elemento en la lista.
	 */
	public Lista(TipoDato dato) {
		insertarFinal(dato);
	}

	/**
	 * Metodo que devuelve el elemento del inicio de la Lista.
	 * 
	 * @return el primer elemento.
	 */
	public TipoDato obtenerPrimero() {
		return primero.dato;
	}

	/**
	 * Metodo que devuelve el ultimo elemento de la Lista.
	 * 
	 * @return el último elemento.
	 */
	public TipoDato obtenerUltimo() {
		return ultimo.dato;
	}

	/**
	 * Metodo que devuelve el inicio de la Lista.
	 * 
	 * @return primero.
	 */
	public Nodo<TipoDato> primero() {
		return primero;
	}

	/**
	 * Metodo que devuelve el final de la Lista.
	 * 
	 * @return ultimo.
	 */
	public Nodo<TipoDato> ultimo() {
		return ultimo;
	}

	/**
	 * Metodo para comprobar si la lista esta vacia o no.
	 * 
	 * @return true si esta vacia o false en caso contrario.
	 */
	public boolean estaVacia() {
		return (capacidad == 0);
	}

	/**
	 * Metodo para comprobar el tamano de la lista.
	 * 
	 * @return capacidad de la lista.
	 */
	public Integer capacidad() {
		return capacidad;
	}

	/**
	 * Método para consultar un dato almacenado en una posición.
	 * 
	 * @param pos
	 *            posición de un elemento en la lista.
	 * 
	 * @return el dato contenido en el nodo que está en la posición pos de la
	 *         lista.
	 */
	public TipoDato consultarDato(Integer pos) {
		TipoDato d = null;
		Nodo<TipoDato> iter = primero;
		Integer i = 0;
		boolean encontrado = false;
		while (i <= pos && !encontrado && iter != null) {
			if (i == pos) {
				encontrado = true;
				d = iter.dato;
			}
			i++;
			iter = iter.siguiente;
		}
		return d;
	}

	/**
	 * Permite insertar al final de la lista.
	 * 
	 * @param dato
	 *            valor que se va a insertar.
	 */
	public void insertarFinal(TipoDato dato) {
		Nodo<TipoDato> l = ultimo;
		Nodo<TipoDato> nodo = new Nodo<TipoDato>(l, dato, null);
		ultimo = nodo;
		if (l == null) {
			primero = nodo;
		} else {
			l.siguiente = nodo;
		}
		capacidad++;
	}

	public String toString() {
		String string = "";
		for (int i = 0; i < capacidad; i++) {
			string = string + consultarDato(i) + " ";
		}
		return string;
	}

	/**
	 * Permite eliminar el último elemento de la Lista.
	 * 
	 */
	public void eliminarUltimo() {
		if (ultimo == null) {
			primero = null;
		} else {
			ultimo.siguiente = null;
		}
		capacidad--;
	}

	/**
	 * Permite eliminar el primer elemento de la Lista.
	 * 
	 */
	public void eliminarPrimero() {
		if (primero != null) {
			primero = primero.siguiente();
			capacidad--;
		}
	}

	/**
	 * Permite eliminar un dato almacenado en la lista.
	 * 
	 * @param dato
	 *            valor que se eliminara de la lista.
	 * @return la posicion en la que se almacenaba el dato.
	 */
	public int eliminarDato(TipoDato dato) {
		int contador = -1; // Para que el primero esté en la posición 0.
		boolean encontrado = false;
		Nodo<TipoDato> aux = primero;
		if (pertenece(dato)) {
			if (!estaVacia()) {
				while (!encontrado) {
					if (dato.compareTo(aux.dato) == 0) {
						aux.anterior.siguiente = aux.siguiente;
						aux.siguiente.anterior = aux.anterior;
						capacidad--;
						encontrado = true;
					}
					aux = aux.siguiente();
					contador++;
				}
			}
		}
		return contador;
	}

	/**
	 * Permite insertar de forma ordenada en la lista.
	 * 
	 * @param Data
	 *            valor que se va a insertar.
	 */
	public void insertarOrdenado(TipoDato dato) {
		Nodo<TipoDato> aux = primero;
		boolean insertado = false;
		if (estaVacia()) {
			insertarFinal(dato);
		} else {
			while (aux != null && !insertado) {
				if (aux.dato.compareTo(dato) >= 0) {
					insertarAntes(dato, aux.dato);
					insertado = true;
				} else {
					aux = aux.siguiente;
				}
				if (aux == null) {
					insertarFinal(dato);
				}
			}
		}
	}

	/**
	 * Permite insertar al principio de la lista.
	 * 
	 * @param Data
	 *            valor que se va a insertar.
	 */
	public void insertarPrimero(TipoDato dato) {
		Nodo<TipoDato> nuevo;
		if (estaVacia()) {
			nuevo = new Nodo<TipoDato>(null, dato, null);
			primero = nuevo;
			capacidad++;
		} else {
			nuevo = new Nodo<TipoDato>(null, dato, primero);
			primero.anterior = nuevo;
			primero = nuevo;
			capacidad++;
		}
	}

	/**
	 * comprueba si un valor existe en la lista.
	 * 
	 * @param Data
	 *            valor que se va a comprobar.
	 * @return true si el Data existe en la lista o false en caso contrario.
	 */
	public boolean pertenece(TipoDato dato) {
		Nodo<TipoDato> aux = primero;
		for (int i = 0; i < capacidad; i++) {
			if (!estaVacia()) {
				if (dato.compareTo(aux.dato) == 0) {
					return true;
				} else {
					aux = aux.siguiente;
				}
			}
		}
		return false;
	}

	/**
	 * Permite insertar delante de un valor dado.
	 * 
	 * @param Data
	 *            valor que se va a insertar.
	 * @param valorbuscar
	 *            valor delante del cual se insertara el nuevo dato.
	 */
	public void insertarAntes(TipoDato dato, TipoDato dato2) {
		boolean encontrado = false;
		Nodo<TipoDato> aux = primero;
		Nodo<TipoDato> nuevo = null;
		if (pertenece(dato2)) {
			if (primero.dato.compareTo(dato2) == 0) {
				nuevo = new Nodo<TipoDato>(null, dato, primero);
				primero.anterior = nuevo;
				primero = nuevo;
				capacidad++;
			} else {
				while (!encontrado) {
					if (aux.dato.compareTo(dato2) == 0) {
						nuevo = new Nodo<TipoDato>(aux.anterior, dato, aux);
						aux.anterior.siguiente = nuevo;
						aux.anterior = nuevo;
						encontrado = true;
						capacidad++;
					} else {
						aux = aux.siguiente;
					}
				}
			}
		}
	}

	/**
	 * Permite insertar detras de un valor dado.
	 * 
	 * @param Data
	 *            valor que se va a insertar.
	 * @param valorbuscar
	 *            valor detras del cual se insertara el nuevo dato.
	 */
	public void insertarDespues(TipoDato dato, TipoDato dato2) {
		boolean encontrado = false;
		Nodo<TipoDato> aux = primero;
		Nodo<TipoDato> nuevo = null;
		if (pertenece(dato2)) {
			if (ultimo.dato.compareTo(dato2) == 0) {
				nuevo = new Nodo<TipoDato>(ultimo, dato, null);
				ultimo.siguiente = nuevo;
				ultimo = nuevo;
				capacidad++;
			} else {
				while (!encontrado) {
					if (aux.dato.compareTo(dato2) == 0) {
						nuevo = new Nodo<TipoDato>(aux, dato, aux.siguiente);
						aux.siguiente = nuevo;
						nuevo.siguiente.anterior = nuevo;
						encontrado = true;
						capacidad++;
					} else {
						aux = aux.siguiente;
					}
				}
			}
		}
	}

	/**
	 * Permite insertar en una posicion dada.
	 * 
	 * @param Data
	 *            valor que se va a insertar.
	 * @param index
	 *            posicion en la cual se insertara el nuevo Data.
	 */
	public void insertarEnPosicion(TipoDato dato, int posicion) {
		if (posicion == capacidad) {
			Nodo<TipoDato> nuevo;
			nuevo = new Nodo<TipoDato>(ultimo.anterior, dato, ultimo);
			ultimo.anterior.siguiente = nuevo;
			ultimo.anterior = nuevo;
			capacidad++;
		} else if (posicion == 0) {
			insertarPrimero(dato);
		} else if (!estaVacia() && posicion < capacidad) {
			Nodo<TipoDato> aux = primero;
			Nodo<TipoDato> nuevo;
			for (int i = 0; i < posicion; i++) {
				aux = aux.siguiente;
			}
			nuevo = new Nodo<TipoDato>(aux.anterior, dato, aux);
			aux.anterior.siguiente = nuevo;
			aux.anterior = nuevo;
			capacidad++;
		}
	}

	/**
	 * Permite eliminar el dato almacenado en una posicion dada.
	 * 
	 * @param posicion
	 *            posicion del dato que se eliminara.
	 */
	public void eliminarPosicion(int posicion) {
		Nodo<TipoDato> aux = primero;
		if (posicion == 0) {
			eliminarPrimero();
		} else if (posicion == capacidad) {
			eliminarUltimo();
		} else if (posicion < capacidad) {
			for (int i = 0; i < posicion; i++) {
				aux = aux.siguiente;
			}
			aux.anterior.siguiente = aux.siguiente;
			aux.siguiente.anterior = aux.anterior;
			capacidad--;
		}
	}

	/**
	 * Cambia el valor almacenado en una posicion por otro.
	 * 
	 * @param index
	 *            posicion del dato que se cambiara.
	 * @param Data
	 *            nuevo valor por el que se sustituira el valor que hay
	 *            almacenado actualmente.
	 */
	public void cambiar(int posicion, TipoDato dato) {
		eliminarPosicion(posicion);
		insertarEnPosicion(dato, posicion);
	}

	/**
	 * Metodo principal. Hace pruebas de inserciones y borrados en la lista y
	 * muestra el resultado obtenido.
	 * 
	 * @param args
	 *            Argumentos que recibe el programa principal.
	 */
	// public static void main(String args[]) {
	// Integer[] dataSet = { new Integer(2), new Integer(8), new Integer(3),
	// new Integer(1), new Integer(4), new Integer(5), new Integer(6),
	// new Integer(7), new Integer(9), new Integer(0) };
	//
	// // Pruebas de inserciones al final y al inicio
	// Lista<Integer> list = new Lista();
	// for (int i = 0; i < dataSet.length; i++) {
	// list.insertarFinal(dataSet[i]);
	// }
	//
	// // Mostrando la lista
	// Lista.Nodo iteratorNode = list.primero();
	// while (iteratorNode != null) {
	// System.out.print(iteratorNode.consultar() + " : ");
	// iteratorNode = iteratorNode.siguiente();
	// }
	// System.out.println("\n--------------------------");
	//
	// // Mostrando la lista
	// for (int i = 0; i < list.capacidad(); i++) {
	// System.out.print(list.consultarDato(i) + " : ");
	// }
	// System.out.println("\n--------------------------");
	//
	// for (int i = 0; i < 5; i++)
	// list.eliminarUltimo();
	//
	// // Mostrando la lista
	// for (int i = 0; i < list.capacidad(); i++) {
	// System.out.print(list.consultarDato(i) + " : ");
	// }
	// System.out.println("\n--------------------------");
	//
	// // Eliminar
	//
	// int n = list.eliminarDato(8);
	// System.out.println(n);
	//
	// list.cambiar(list.capacidad, 51);
	//
	// list.insertarOrdenado(1);
	// list.insertarOrdenado(5);
	// list.insertarOrdenado(2);
	// System.out.println(list);
	// }
}
