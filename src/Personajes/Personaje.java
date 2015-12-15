/**
 * Implementación de la clase Personaje.
 * @version 4.0
 * @author <b> Planet Express </b><br>
 * Nombre y apellidos: Javier García Valencia
 * Curso: 3º GIIIS
 * Asignatura Desarrollo de Programas<br/>
 * Curso 15/16
 */

package Personajes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Mapa.Estacion;
import Mapa.Galaxia;
import Mapa.Midicloriano;
import Mapa.Puerta;

public class Personaje {

	protected String nombre;
	protected int turno;
	protected char marca;
	protected LinkedList<Midicloriano> listaMidi;
	protected boolean movido;
	protected Queue<Dir> movimientos;
	protected String tipo;

	/**
	 * Constructor por defecto de la clase Personaje. Dado que los demás tipos
	 * de personaje heredan de este y los atributos se modifican, da igual con
	 * los valores que se inicialice.
	 * 
	 */
	public Personaje() {
		nombre = "";
		marca = '?';
		listaMidi = new LinkedList<Midicloriano>();
		movimientos = new LinkedList<Dir>();
		movido = false;
		turno = 0;
		tipo = "";
	}

	/**
	 * Método que aumenta el turno del personaje.
	 */
	public void aumentarTurno() {
		turno++;
	}

	/**
	 * Método que devuelve el tipo de personaje que es.
	 * 
	 * @return String con el tipo del personaje
	 */
	public String devolverTipo() {
		return tipo;
	}

	/**
	 * Método que inserta un ArrayList con las direcciones en el peronsje.
	 * 
	 * @param direcciones
	 *            ArrayList con las direcciones que se insertarán
	 */
	public void insertarMovimientos(ArrayList<Dir> direcciones) {
		for (int i = 0; i < direcciones.size(); i++) {
			movimientos.add(direcciones.get(i));
		}
	}

	/**
	 * Método que mueve al personaje de una estación a otra dependiendo de la
	 * dirección que se obtenga como parámetro.
	 * 
	 * @param x
	 *            fila en la que se encuentra el personaje
	 * @param y
	 *            columna en la que se encuentra el personaje
	 * @param direccion
	 *            Dir con la dirección a la que va a moverse
	 * @throws IOException
	 */
	public void moverPersonaje(int x, int y, Dir direccion) throws IOException {
		Galaxia galaxia = Galaxia.obtenerInstancia();
		Estacion estacion = null;
		if (direccion == Dir.N && x > 0) {
			estacion = galaxia.devolverEstacion(x - 1, y);
			estacion.insertarPersonaje(this);
			this.accionEstacion(estacion);
		} else if (direccion == Dir.S && x < galaxia.obtenerFilas() - 1) {
			estacion = galaxia.devolverEstacion(x + 1, y);
			estacion.insertarPersonaje(this);
			this.accionEstacion(estacion);
		} else if (direccion == Dir.E && y < galaxia.obtenerColumnas() - 1) {
			estacion = galaxia.devolverEstacion(x, y + 1);
			estacion.insertarPersonaje(this);
			this.accionEstacion(estacion);
		} else if (direccion == Dir.O && y > 0) {
			estacion = galaxia.devolverEstacion(x, y - 1);
			estacion.insertarPersonaje(this);
			this.accionEstacion(estacion);
		} else {
			galaxia.devolverEstacion(x, y).insertarPersonaje(this);
		}
		this.personajeMovido(); // pone movido a true
		galaxia.devolverEstacion(x, y).obtenerColaPersonajes().remove();
	}

	/**
	 * Método que devuelve el turno del personaje.
	 * 
	 * @return entero con el número del turno del personaje
	 */
	public int obtenerTurno() {
		return turno;
	}

	/**
	 * Método que devuelve la marca del personaje.
	 * 
	 * @return char con la marca del personaje.
	 */
	public char obtenerMarca() {
		return marca;
	}

	/**
	 * Método que devuelve la lista de midiclorianos que tiene el personaje.
	 * 
	 * @return ArrayList con los midiclorianos del personaje
	 */
	public LinkedList<Midicloriano> obtenerListaMidiclorianos() {
		return listaMidi;
	}

	/**
	 * Método que realiza la acción de la puerta para todos los robots excepto
	 * para el imperial, ya que su acción es diferente. Lo que hacen es probar
	 * los midiclorianos que tienen en la puerta; concretamente el último
	 * midicloriano que han recogido.
	 * 
	 * @param estacion
	 *            estación en la que se encuentra el personaje.
	 */
	public void accionPuerta(Estacion estacion) {
		if (estacion.obtenerPuerta() != null && !listaMidi.isEmpty() && !estacion.obtenerPuerta().obtenerBoolPuerta()) {
			Puerta puerta = Puerta.obtenerInstancia();
			puerta.probarMidicloriano(listaMidi.getLast());
			listaMidi.removeLast();
		}
	}

	/**
	 * Método que devuelve un string con las direcciones del personaje para
	 * imprimirlos por pantalla o escribirlos en el fichero de log.
	 * 
	 * @return String con las direcciones del personaje separadas por espacios
	 */
	public String devolverDirecciones() {
		String cadena = "";
		Queue<Dir> cola = new LinkedList<Dir>();
		cola.addAll(movimientos);
		while (!cola.isEmpty()) {
			cadena = cadena + cola.peek();
			cadena = cadena + " ";
			cola.remove();
		}
		cadena = cadena.substring(0, cadena.length() - 1);
		return cadena;
	}

	/**
	 * Método que devuelve un string con los midiclorianos para posteriormente
	 * imprimirlos por pantalla o escribirlos en el fichero de log.
	 * 
	 * @return String con los midiclorianos separados por espacios
	 */
	public String devolverMidiclorianos() {
		String cadena = " ";
		for (int i = listaMidi.size() - 1; i >= 0; i--) {
			cadena = cadena + listaMidi.get(i);
			cadena = cadena + " ";
		}
		cadena = cadena.substring(0, cadena.length() - 1);
		return cadena;
	}

	public void accionEstacion(Estacion estacion) {
		if (estacion.mostrarMidiclorianos() != " ") {
			listaMidi.add(estacion.obtenerPrimerMidicloriano());
			estacion.borrarPrimerMidicloriano();
		}
	}

	/**
	 * Método que devuelve el valor del booleano que indica si el personaje se
	 * ha movido o no.
	 * 
	 * @return booleano con true si se ha movido o false en caso contrario.
	 */
	public boolean movido() {
		return movido;
	}

	/**
	 * Método que devuelve el movimiento siguiente de la cola de movimientos.
	 * 
	 * @return Dir con el movimiento siguiente del personaje.
	 */
	public Dir obtenerMovimiento() {
		Dir movimiento = movimientos.peek();
		movimientos.remove();
		return movimiento;
	}

	/**
	 * Método que cambia el valor del booleano que indica que el personaje se ha
	 * movido.
	 * 
	 */
	public void personajeMovido() {
		movido = true;
	}

	/**
	 * Método que cambia el valor del booleano que indica que el personaje no se
	 * ha movido.
	 * 
	 */
	public void personajeNoMovido() {
		movido = false;
	}

	/**
	 * Método que devuelve la cola de los movimientos del personaje.
	 * 
	 * @return Cola de movimientos del personaje.
	 */
	public Queue<Dir> obtenerColaMovimientos() {
		return movimientos;
	}

	/**
	 * Método que realiza el algoritmo de la mano derecha para que, antes de la
	 * simulación, el personaje introduzca en su cola de movimientos las
	 * direcciones que le permitirán moverse por el laberinto como si llevara la
	 * mano derecha siempre puesta en la pared.
	 * 
	 * @param _origen
	 *            sala desde donde comienza el personaje
	 * @throws IOException
	 */
	protected void manoDerecha(int _origen) throws IOException {
		char dirActual = ' ';
		int origen = _origen;
		int columnas = Galaxia.obtenerInstancia().obtenerColumnas();
		int filas = Galaxia.obtenerInstancia().obtenerFilas();
		int idSalaFin = Galaxia.obtenerInstancia().devolverSalaPuerta();
		if (origen + columnas < filas * columnas - 1) {
			if (Galaxia.obtenerInstancia().sonAdyacentes(origen, origen + columnas)) {
				dirActual = 'S';
			} else if (Galaxia.obtenerInstancia().sonAdyacentes(origen, origen + 1)) {
				dirActual = 'E';
			}
		} else {
			dirActual = 'E';
		}
		while (origen != idSalaFin) {
			switch (dirActual) {
			case 'S':
				// oste
				if (Galaxia.obtenerInstancia().sonAdyacentes(origen, origen - 1)) {
					movimientos.add(Dir.O);
					dirActual = 'O';
					origen = origen - 1;
				}
				// sur
				else if (Galaxia.obtenerInstancia().sonAdyacentes(origen, origen + columnas)) {
					movimientos.add(Dir.S);
					dirActual = 'S';
					origen = origen + columnas;
				}
				// este
				else if (Galaxia.obtenerInstancia().sonAdyacentes(origen, origen + 1)) {
					movimientos.add(Dir.E);
					dirActual = 'E';
					origen = origen + 1;
				}
				// marcha atras (norte)
				else if (Galaxia.obtenerInstancia().sonAdyacentes(origen, origen - columnas)) {
					movimientos.add(Dir.N);
					dirActual = 'N';
					origen = origen - columnas;
				}
				break;
			case 'N':
				// este
				if (Galaxia.obtenerInstancia().sonAdyacentes(origen, origen + 1)) {
					movimientos.add(Dir.E);
					dirActual = 'E';
					origen = origen + 1;
				}
				// norte
				else if (Galaxia.obtenerInstancia().sonAdyacentes(origen, origen - columnas)) {
					movimientos.add(Dir.N);
					dirActual = 'N';
					origen = origen - columnas;
				}
				// oeste
				else if (Galaxia.obtenerInstancia().sonAdyacentes(origen, origen - 1)) {
					movimientos.add(Dir.O);
					dirActual = 'O';
					origen = origen - 1;
				}
				// marcha atras (sur)
				else if (Galaxia.obtenerInstancia().sonAdyacentes(origen, origen + columnas)) {
					movimientos.add(Dir.S);
					dirActual = 'S';
					origen = origen + columnas;
				}
				break;
			case 'E':
				// sur
				if (Galaxia.obtenerInstancia().sonAdyacentes(origen, origen + columnas)) {
					movimientos.add(Dir.S);
					dirActual = 'S';
					origen = origen + columnas;
				}
				// este
				else if (Galaxia.obtenerInstancia().sonAdyacentes(origen, origen + 1)) {
					movimientos.add(Dir.E);
					dirActual = 'E';
					origen = origen + 1;
				}
				// norte
				else if (Galaxia.obtenerInstancia().sonAdyacentes(origen, origen - columnas)) {
					movimientos.add(Dir.N);
					dirActual = 'N';
					origen = origen - columnas;
				}
				// marcha atras (oeste)
				else if (Galaxia.obtenerInstancia().sonAdyacentes(origen, origen - 1)) {
					movimientos.add(Dir.O);
					dirActual = 'O';
					origen = origen - 1;
				}
				break;
			case 'O':
				// norte
				if (Galaxia.obtenerInstancia().sonAdyacentes(origen, origen - columnas)) {
					movimientos.add(Dir.N);
					dirActual = 'N';
					origen = origen - columnas;
				}
				// oeste
				else if (Galaxia.obtenerInstancia().sonAdyacentes(origen, origen - 1)) {
					movimientos.add(Dir.O);
					dirActual = 'O';
					origen = origen - 1;
				}
				// sur
				else if (Galaxia.obtenerInstancia().sonAdyacentes(origen, origen + columnas)) {
					movimientos.add(Dir.S);
					dirActual = 'S';
					origen = origen + columnas;
				}
				// marcha atras (este)
				else if (Galaxia.obtenerInstancia().sonAdyacentes(origen, origen + 1)) {
					movimientos.add(Dir.E);
					dirActual = 'E';
					origen = origen + 1;
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Método toString para imprimir por pantalla el personaje.
	 * 
	 */
	public String toString() {
		String string;
		string = Character.toString(this.marca);
		return string;
	}

	/**
	 * Método que insertar en la cola de movimientos del personaje la dirección
	 * Dir que obtiene por parámetro.
	 *
	 * @param movimiento
	 *            Dir con la dirección que se va a insertar en la cola de
	 *            movimientos del personaje
	 */
	public void ponerDireccion(Dir movimiento) {
		movimientos.add(movimiento);
	}

}
