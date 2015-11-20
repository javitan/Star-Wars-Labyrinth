/**
 * Implementación de la clase Arbol binario de busqueda.
 * @version 2.0
 * @author <b> Planet Express </b><br>
 * Nombre y apellidos: Javier Garcia Valencia
 * Curso: 2o GIIIS
 * Asignatura Desarrollo de Programas<br/>
 * Curso 15/16
 */

package ED;

public class Arbol<TipoDato extends Comparable<TipoDato>> {

	/** Dato almacenado en cada nodo del Arbol. */
	private TipoDato datoRaiz;

	/** Atributo que indica si el Arbol esta vacio. */
	boolean esVacio;

	/** Hijo izquierdo del nodo actual */
	private Arbol<TipoDato> hIzq;

	/** Hijo derecho del nodo actual */
	private Arbol<TipoDato> hDer;

	/*********************************************************************************************************************/

	/**
	 * Constructor por defecto de la clase. Inicializa un Arbol vacio.
	 */
	public Arbol() {
		this.esVacio = true;
		this.hIzq = null;
		this.hDer = null;
	}

	/**
	 * Crea un nuevo Arbol a partir de los datos pasados por parametro.
	 * 
	 * @param hIzq
	 *            El hijo izquierdo del Arbol que se esta creando.
	 * @param datoRaiz
	 *            Raiz del Arbol que se esta creando.
	 * @param hDer
	 *            El hijo derecho del Arbol que se esta creando.
	 */
	public Arbol(Arbol<TipoDato> hIzq, TipoDato datoRaiz, Arbol<TipoDato> hDer) {
		this.esVacio = false;
		this.datoRaiz = datoRaiz;
		this.hIzq = hIzq;
		this.hDer = hDer;
	}

	/**
	 * Borrar un dato del Arbol.
	 * 
	 * @param dato
	 *            El dato que se quiere borrar.
	 */
	public void borrar(TipoDato dato) {
		if (!vacio()) {
			if (dato.compareTo(this.datoRaiz) < 0) { // dato<datoRaiz
				hIzq = hIzq.borrarOrden(dato);
			} else if (dato.compareTo(this.datoRaiz) > 0) { // dato>datoRaiz
				hDer = hDer.borrarOrden(dato);
			} else // En este caso el dato es datoRaiz
			{
				if (hIzq == null && hDer == null) {
					esVacio = true;
				} else
					borrarOrden(dato);
			}
		}
	}

	/**
	 * Borrar un dato. Este metodo es utilizado por el metodo borrar anterior.
	 * 
	 * @param dato
	 *            El dato a borrar.
	 * @return Devuelve el Arbol resultante despues de haber realizado el
	 *         borrado.
	 */
	private Arbol<TipoDato> borrarOrden(TipoDato dato) {
		TipoDato datoaux;
		Arbol<TipoDato> retorno = this;
		Arbol<TipoDato> aborrar, candidato, antecesor;

		if (!vacio()) {
			if (dato.compareTo(this.datoRaiz) < 0) { // dato<datoRaiz
				if (hIzq != null) {
					hIzq = hIzq.borrarOrden(dato);
				}
			} else if (dato.compareTo(this.datoRaiz) > 0) { // dato>datoRaiz
				if (hDer != null) {
					hDer = hDer.borrarOrden(dato);
				}
			} else {
				aborrar = this;
				if ((hDer == null) && (hIzq == null)) { /* si es hoja */
					retorno = null;
				} else {
					if (hDer == null) { /* Solo hijo izquierdo */
						aborrar = hIzq;
						datoaux = this.datoRaiz;
						datoRaiz = hIzq.obtenerRaiz();
						hIzq.datoRaiz = datoaux;
						hIzq = hIzq.obtenerHijoIzq();
						hDer = aborrar.obtenerHijoDer();

						retorno = this;
					} else if (hIzq == null) { /* Solo hijo derecho */
						aborrar = hDer;
						datoaux = datoRaiz;
						datoRaiz = hDer.obtenerRaiz();
						hDer.datoRaiz = datoaux;
						hDer = hDer.obtenerHijoDer();
						hIzq = aborrar.obtenerHijoIzq();

						retorno = this;
					} else { /* Tiene dos hijos */
						candidato = this.obtenerHijoIzq();
						antecesor = this;
						while (candidato.obtenerHijoDer() != null) {
							antecesor = candidato;
							candidato = candidato.obtenerHijoDer();
						}

						/* Intercambio de datos de candidato */
						datoaux = datoRaiz;
						datoRaiz = candidato.obtenerRaiz();
						candidato.datoRaiz = datoaux;
						aborrar = candidato;
						if (antecesor == this)
							hIzq = candidato.obtenerHijoIzq();
						else
							antecesor.hDer = candidato.obtenerHijoIzq();
					} // Eliminar solo ese nodo, no todo el subarbol
					aborrar.hIzq = null;
					aborrar.hDer = null;
				}
			}
		}
		return retorno;
	}

	/**
	 * Metodo que calcula el numero de nodos hoja.
	 * 
	 * @return int numero de nodos hoja.
	 */
	public int cuantasHojas() {
		int hojas = 0;
		Arbol<TipoDato> aux = null;
		if (!vacio()) {
			if (hIzq == null && hDer == null) {
				hojas++;
			} else {
				if ((aux = obtenerHijoIzq()) != null) {
					hojas = hojas + aux.cuantasHojas();
				}
				if ((aux = obtenerHijoDer()) != null) {
					hojas = hojas + aux.cuantasHojas();
				}
			}
		}
		return hojas;
	}

	/**
	 * Metodo que calcula el nuemero de nodos internos.
	 * 
	 * @return int numero de nodos internos.
	 */
	public int cuantosInternos() {
		int internos = 0;
		Arbol<TipoDato> aux = null;
		if (!vacio()) {
			if (hIzq != null || hDer != null) {
				internos++;
			}
			if ((aux = obtenerHijoIzq()) != null) {
				internos = internos + aux.cuantosInternos();
			}
			if ((aux = obtenerHijoDer()) != null) {
				internos = internos + aux.cuantosInternos();
			}
		}
		return internos;
	}

	/**
	 * Metodo que comprueba si un nodo es hoja o no.
	 * 
	 * @return true si un nodo es hoja y false si no lo es.
	 */
	public boolean esHoja(TipoDato dato) {
		Arbol<TipoDato> aux = this;
		boolean hoja = false;

		if (!vacio()) {
			if (dato.compareTo(this.datoRaiz) == 0) {
				if (hDer == null && hIzq == null) {
					hoja = true;
				}
			} else {
				if (dato.compareTo(this.datoRaiz) < 0) {
					hoja = aux.obtenerHijoIzq().esHoja(dato);
				} else {
					if (dato.compareTo(this.datoRaiz) > 0) {
						hoja = aux.obtenerHijoDer().esHoja(dato);
					}
				}
			}
		}
		return hoja;
	}

	/**
	 * Recorrido inOrden del Arbol.
	 */
	public void inOrden() {
		Arbol<TipoDato> aux = null;
		if (!vacio()) {
			if ((aux = obtenerHijoIzq()) != null) {
				aux.inOrden();
			}

			System.out.println(this.datoRaiz);

			if ((aux = obtenerHijoDer()) != null) {
				aux.inOrden();
			}
		}
	}

	/**
	 * Inserta un nuevo dato en el Arbol.
	 * 
	 * @param dato
	 *            El dato a insertar.
	 * @return verdadero si el dato se ha insertado correctamente, falso en caso
	 *         contrario.
	 */
	public boolean insertar(TipoDato dato) {
		boolean resultado = true;
		if (vacio()) {
			datoRaiz = dato;
			esVacio = false;
		} else {
			if (!(this.datoRaiz.equals(dato))) {
				Arbol<TipoDato> aux;
				if (dato.compareTo(this.datoRaiz) < 0) { // dato < datoRaiz
					if ((aux = obtenerHijoIzq()) == null)
						hIzq = aux = new Arbol<TipoDato>();
				} else { // dato > datoRaiz
					if ((aux = obtenerHijoDer()) == null)
						hDer = aux = new Arbol<TipoDato>();
				}
				resultado = aux.insertar(dato);
			} else
				resultado = false;
		}
		return resultado;
	}

	/**
	 * Devuelve el hijo derecho del Arbol.
	 * 
	 * @return Hijo derecho del Arbol.
	 */
	public Arbol<TipoDato> obtenerHijoDer() {
		return hDer;
	}

	/**
	 * Devuelve el hijo izquierdo del Arbol.
	 * 
	 * @return El hijo izquierdo.
	 */
	public Arbol<TipoDato> obtenerHijoIzq() {
		return hIzq;
	}

	/**
	 * Devuelve la raiz del Arbol.
	 * 
	 * @return La raiz del Arbol.
	 */
	public TipoDato obtenerRaiz() {
		return datoRaiz;
	}

	/**
	 * Comprueba si un dato se encuentra almacenado en el Arbol.
	 * 
	 * @param dato
	 *            El dato a buscar.
	 * @return verdadero si el dato se encuentra en el Arbol, falso en caso
	 *         contrario.
	 */
	public boolean pertenece(TipoDato dato) {
		Arbol<TipoDato> aux = null;
		boolean encontrado = false;
		if (!vacio()) {
			if (this.datoRaiz.equals(dato) || this.datoRaiz.compareTo(dato) == 0)
				encontrado = true;
			else {
				if (dato.compareTo(this.datoRaiz) < 0) // dato < datoRaiz
					aux = obtenerHijoIzq();
				else
					// dato > datoRaiz
					aux = obtenerHijoDer();
				if (aux != null)
					encontrado = aux.pertenece(dato);
			}
		}
		return encontrado;
	}

	/**
	 * Recorrido en postOrden.
	 */
	public void postOrden() {
		Arbol<TipoDato> aux = null;
		if (!vacio()) {
			if ((aux = obtenerHijoIzq()) != null) {
				aux.postOrden();
			}

			if ((aux = obtenerHijoDer()) != null) {
				aux.postOrden();
			}
			System.out.println(this.datoRaiz);
		}
	}

	/**
	 * Recorrido en preOrden.
	 */
	public void preOrden() {
		Arbol<TipoDato> aux = null;
		if (!vacio()) {

			System.out.println(this.datoRaiz);

			if ((aux = obtenerHijoIzq()) != null) {
				aux.preOrden();
			}

			if ((aux = obtenerHijoDer()) != null) {
				aux.preOrden();
			}
		}
	}

	/**
	 * Devuelve la profundidad de un Arbol.
	 * 
	 * @return int profundidad del Arbol
	 */
	public int profundidad() {
		Arbol<TipoDato> aux = null;
		int cont = 0;
		int cont1 = 0;
		if (!vacio()) {
			cont++;
			cont1++;
			if ((aux = obtenerHijoIzq()) != null) {
				cont = 1 + aux.profundidad();
			}
			if ((aux = obtenerHijoDer()) != null) {
				cont1 = 1 + aux.profundidad();
			}
		}
		if (cont1 > cont) {
			cont = cont1;
		}
		return cont;
	}

	/**
	 * Comprueba si el Arbol esta vacio.
	 * 
	 * @return verdadero si el Arbol esta vacio, falso en caso contrario.
	 */
	public boolean vacio() {
		return esVacio;
	}

	public String toString() {
		Arbol<TipoDato> aux = null;
		String string = "";
		if (!vacio()) {
			if ((aux = obtenerHijoIzq()) != null) {
				string = string + aux.toString();
			}

			string = string + " " + datoRaiz;

			if ((aux = obtenerHijoDer()) != null) {
				string = string + aux.toString();
			}
		}
		return string;
	}

	/**
	 * Metodo que main que realiza las pruebas con el Arbol.
	 * 
	 * @param args
	 *            Argumentos del main.
	 */
	// public static void main(String[] args) {
	// Arbol<Integer> arbol = new Arbol<Integer>();
	//
	// Integer[] datos = { new Integer(20), new Integer(7), new Integer(18),
	// new Integer(6), new Integer(5), new Integer(1),
	// new Integer(22), new Integer(21) };
	//
	// for (int i = 0; i < datos.length; i++) {
	// arbol.insertar(datos[i]);
	// }
	//
	// System.out.println(arbol);
	//
	// // Insertando datos repetidos
	// if (arbol.insertar(new Integer(22)) == false)
	// System.out.println("El ABB no admite elementos duplicados");
	//
	// // Pertenencia de un dato
	// if (arbol.pertenece(new Integer(22)))
	// System.out.println("Pertenece");
	// else
	// System.out.println("NO Pertenece");
	//
	// int n = arbol.profundidad();
	// System.out.println("Profundidad: " + n);
	//
	// if (arbol.esHoja(new Integer(7))) {
	// System.out.println("El dato es hoja");
	// } else {
	// System.out.println("El dato no es hoja");
	// }
	//
	// int a = arbol.cuantasHojas();
	// System.out.println("El número de hojas es " + a);
	//
	// int b = arbol.cuantosInternos();
	// System.out.println("El número de nodos internos es " + b);

	// // Recorrido en postOrden
	// arbol.postOrden();
	// System.out.println("PostOrden");
	//
	// // Recorrido en preOrden
	// arbol.preOrden();
	// System.out.println("PreOrden");
	//
	// // Recorrido en inOrden
	// System.out.println("InOrden");
	// arbol.inOrden();
	//
	// // Probando el borrado de diferentes datos -- Descomentar estas
	// lineas
	// // para ver que ocurre
	// arbol.borrar(new Integer(20));
	// System.out.println("Borrado " + 20);
	// arbol.borrar(new Integer(15));
	// System.out.println("Borrado " + 15);
	//
	// // Borrando datos del Arbol
	// for (int i = 0; i < datos.length; i++) {
	// arbol.borrar(datos[i]);
	// System.out.println("Borrado " + datos[i]);
	// arbol.inOrden();
	// }
	// }
}
