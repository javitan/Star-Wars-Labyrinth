/**
 * Implementación de la clase Galaxia.
 * @version 4.0
 * @author <b> Planet Express </b><br>
 * Nombre y apellidos: Javier García Valencia
 * Curso: 3º GIIIS
 * Asignatura Desarrollo de Programas<br/>
 * Curso 15/16
 */

package Mapa;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

import Cargador.GenAleatorios;
import ED.Grafo;
import Personajes.Dir;
import Personajes.Personaje;

public class Galaxia {

	private int filas;
	private int columnas;
	private static Galaxia singleton;
	private Estacion mapa[][];
	private Puerta puerta;
	private int MAXturno = 50;
	private int turno = 2;
	private int salaPuerta;
	private ArrayList<Pared> listaParedes;
	private Grafo grafo;
	private ArrayList<Dir> movimientosFR;
	private ArrayList<Integer> salasRepartir;
	private Estacion estacionGanadores;
	private BufferedWriter bufferOut;
	String fichero = "fichero.txt";

	/**
	 * Constructor parametrizado de la clase Galaxia. Al trabajar con un patrón
	 * de diseño de tipo Singleton, el constructor es private para asegurarnos
	 * de que no se puede crear una galaxia desde otra clase, y demás de que la
	 * galaxia solo se creará una vez.
	 * 
	 * @param _columnas
	 *            número de columnas que tendrá la matriz de la galaxia
	 * @param _filas
	 *            número de filas que tendrá la matriz de la galaxia
	 * @param _estacionPuerta
	 *            número de la sala en la que se encontrará la puerta de salida
	 * @param _altura
	 *            número correspondiente a la altura del árbol para configurar
	 *            la puerta
	 * @throws IOException
	 */
	private Galaxia(int _columnas, int _filas, int _estacionPuerta, int _altura) throws IOException {
		grafo = new Grafo();
		filas = _filas;
		columnas = _columnas;
		salaPuerta = _estacionPuerta;
		mapa = new Estacion[filas][columnas];
		estacionGanadores = new Estacion(1111);
		int num = 0;
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				mapa[i][j] = new Estacion(num);
				grafo.nuevoNodo(num);
				// System.out.println("Estación " + num + " creada.");
				num++;
			}
		}
		listaParedes = new ArrayList<Pared>();
		// System.out.println(this);
		insertarParedes();
		tirarParedes();
		puerta = Puerta.obtenerInstanciaParam(_altura);
		puerta.configurarPuerta();
		mapa[_estacionPuerta / _columnas][_estacionPuerta % _columnas].ponerPuerta(puerta);
		movimientosFR = new ArrayList<Dir>();
		salasRepartir = new ArrayList<Integer>();
		ArrayList<Integer> visitados = new ArrayList<Integer>();
		System.out.println(this);
		prof(grafo, 0, visitados);
		grafo.warshall();
		grafo.floyd();
	}

	public int devolverSalaPuerta() {
		return salaPuerta;
	}

	private void pintarRutas(String nomFichero, BufferedWriter bufferOut) throws IOException {
		Queue<Personaje> cola = new LinkedList<Personaje>();
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				cola.addAll(mapa[i][j].obtenerColaPersonajes());
				if (cola != null) {
					while (!cola.isEmpty()) {
						System.out.println("(ruta:" + cola.peek().obtenerMarca() + ":" + " "
								+ cola.peek().devolverDirecciones() + ")");
						bufferOut.write("(ruta:" + cola.peek().obtenerMarca() + ":" + " "
								+ cola.peek().devolverDirecciones() + ")");
						bufferOut.newLine();
						cola.remove();
					}
				}
			}
		}
	}

	private Estacion devolverEstacionGanadores() {
		return estacionGanadores;
	}

	/**
	 * Método estático para crear la puerta. Dado que trabajamos con un patrón
	 * de diseño de tipo Singleton, esto nos garantiza que la galaxia se creará
	 * una sola vez, y que podemos tener acceso a ella desde cualquier clase sin
	 * necesidad de pasarla por parámetro.
	 * 
	 * @param columnas
	 *            número de columnas que tendrá la matriz de la galaxia
	 * @param filas
	 *            número de filas que tendrá la matriz de la galaxia
	 * @param estacionPuerta
	 *            número de la sala que contendrá la puerta de salida
	 * @param altura
	 *            número correspondiente a la altura del árbol para configurar
	 *            la puerta
	 * @return singleton; una instancia creada de la clase galaxia
	 * @throws IOException
	 */
	public static Galaxia obtenerInstanciaParam(int columnas, int filas, int estacionPuerta, int altura)
			throws IOException {
		if (singleton == null) {
			singleton = new Galaxia(columnas, filas, estacionPuerta, altura);
		}
		return singleton;
	}

	/**
	 * Método necesario en el patrón de diseño de tipo Singleton. No se
	 * ejecutará nunca, pero servirá como control de errores. Inicializa una
	 * galaxia con 0 filas, 0 columnas, la puerta en la sala 0 y la altura del
	 * árbol 0.
	 * 
	 * @return singleton; una instancia creada de la clase galaxia
	 * @throws IOException
	 */
	public static Galaxia obtenerInstancia() throws IOException {
		if (singleton == null) {
			singleton = new Galaxia(0, 0, 0, 0);
		}
		return singleton;
	}

	/**
	 * Método que realiza un recorrido en profundiad en el grafo. Además, genera
	 * las direcciones de los movimientos necesarios para ir desde la sala
	 * origen a la sala destino y las almacena en una lista para porteriormente
	 * pasarlas al personaje de tipo Familia Real. También guarda los números de
	 * las salas por las que pasa para hacer el reparto de llaves por las
	 * mismas.
	 * 
	 * @param grafo
	 *            estructura de datos sobre la cual vamos a hacer el recorrido
	 *            en profundidad
	 * @param v
	 *            número correspondiente a la sala de la que se parte
	 * @param visitados
	 *            conjunto para almacenar las salas que ya hemos visitado
	 */
	public void prof(Grafo grafo, int v, ArrayList<Integer> visitados) {
		TreeSet<Integer> ady = new TreeSet<Integer>();
		int w;
		visitados.add(v);
		/* PREWORK */
		if (mapa[v / columnas][v % columnas].obtenerPuerta() != null) {
			// System.err.println(visitados.toString());
			for (int i = 0; i < visitados.size() - 1; i++) {
				salasRepartir.add(visitados.get(i));
				int origen = visitados.get(i);
				int siguiente = visitados.get(i + 1);
				if (siguiente - origen == columnas * -1) { // N
					movimientosFR.add(Dir.N);
				} else if (siguiente - origen == columnas) { // S
					movimientosFR.add(Dir.S);
				} else if (siguiente - origen == 1) { // E
					movimientosFR.add(Dir.E);
				} else if (siguiente - origen == -1) { // O
					movimientosFR.add(Dir.O);
				}
			}
		} else {
			grafo.adyacentes(v, ady);
			while (!ady.isEmpty()) {
				w = ady.first(); // menor
				ady.remove(w);
				if (!visitados.contains(w)) {
					prof(grafo, w, visitados);
					visitados.remove(visitados.size() - 1);
				}
			}
		}
	}

	/**
	 * Método que inserta las paredes en una estructura de datos que usaremos
	 * para hacer el tirado de paredes posteriormente.
	 */
	public void insertarParedes() {
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				if (i > 0) { // N
					Pared pared = new Pared(mapa[i][j].obtenerIdEstacion(), mapa[i - 1][j].obtenerIdEstacion());
					listaParedes.add(pared);
				}
				if (j < columnas - 1) { // E
					Pared pared = new Pared(mapa[i][j].obtenerIdEstacion(), mapa[i][j + 1].obtenerIdEstacion());
					listaParedes.add(pared);
				}
				if (i < filas - 1) { // S
					Pared pared = new Pared(mapa[i][j].obtenerIdEstacion(), mapa[i + 1][j].obtenerIdEstacion());
					listaParedes.add(pared);
				}
				if (j > 0) { // O
					Pared pared = new Pared(mapa[i][j].obtenerIdEstacion(), mapa[i][j - 1].obtenerIdEstacion());
					listaParedes.add(pared);
				}
			}
		}
	}

	/**
	 * Método que realiza el tirado de paredes. Parte del conjunto de todas las
	 * paredes existentes y se van tirando cambiando y extendiendo la marca de
	 * las salas. Para con cuando de acaba el conjunto de paredes y, al final,
	 * todas las salas tienen la misma marca.
	 */
	public void tirarParedes() {
		int pared1;
		int pared2;
		while (!listaParedes.isEmpty()) {
			int aleatorio = GenAleatorios.generarNumero(listaParedes.size());
			pared1 = listaParedes.get(aleatorio).devolverPared1();
			pared2 = listaParedes.get(aleatorio).devolverPared2();
			listaParedes.remove(aleatorio);
			int marcaPared1 = mapa[pared1 / columnas][pared1 % columnas].obtenerMarca();
			int marcaPared2 = mapa[pared2 / columnas][pared2 % columnas].obtenerMarca();
			if (marcaPared1 != marcaPared2) {
				grafo.nuevoArco(pared1, pared2, 1);
				grafo.nuevoArco(pared2, pared1, 1);
				// extenderMarca
				for (int i = 0; i < filas; i++) {
					for (int j = 0; j < columnas; j++) {
						if (mapa[i][j].obtenerMarca() == marcaPared2) {
							mapa[i][j].ponerMarca(marcaPared1);
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param pers
	 */
	public void insertarFamilia(Personaje pers) {
		mapa[0][0].insertarPersonaje(pers);
		for (int i = 0; i < movimientosFR.size(); i++) {
			pers.ponerDireccion(movimientosFR.get(i));
		}
	}

	public void insertarJedi(Personaje pers) {
		mapa[0][0].insertarPersonaje(pers);
	}

	public void insertarContrabandista(Personaje pers) {
		mapa[filas - 1][0].insertarPersonaje(pers);
	}

	public boolean sonAdyacentes(int origen, int destino) {
		if (grafo.adyacente(origen, destino)) {
			return true;
		}
		return false;
	}

	public void insertarImperial(Personaje pers) {
		mapa[filas - 1][columnas - 1].insertarPersonaje(pers);
		int[] esquinas = { mapa[0][columnas - 1].obtenerIdEstacion(), mapa[0][0].obtenerIdEstacion(),
				mapa[filas - 1][0].obtenerIdEstacion(), mapa[filas - 1][columnas - 1].obtenerIdEstacion() };
		int inicio = mapa[filas - 1][columnas - 1].obtenerIdEstacion();
		int indice = 0;
		int fin = esquinas[indice];
		ArrayList<Integer> listamov = new ArrayList<Integer>();
		listamov.add(mapa[filas - 1][columnas - 1].obtenerIdEstacion());
		while (fin != esquinas[3]) {
			while (inicio != fin) {
				listamov.add(grafo.siguiente(inicio, fin));
				inicio = grafo.siguiente(inicio, fin);
			}
			indice++;
			fin = esquinas[indice];
		}
		while (inicio != fin) {
			listamov.add(grafo.siguiente(inicio, fin));
			inicio = grafo.siguiente(inicio, fin);
		}
		for (int i = 0; i < listamov.size() - 1; i++) {
			int origen = listamov.get(i);
			int siguiente = listamov.get(i + 1);
			if (siguiente - origen == columnas * -1) { // N
				pers.ponerDireccion(Dir.N);
			} else if (siguiente - origen == columnas) { // S
				pers.ponerDireccion(Dir.S);
			} else if (siguiente - origen == 1) { // E
				pers.ponerDireccion(Dir.E);
			} else if (siguiente - origen == -1) { // O
				pers.ponerDireccion(Dir.O);
			}
		}
	}

	public void ejecucion(String nomFichero, BufferedWriter bufferOut) throws IOException {
		bufferOut.write(ficheroTablero());
		bufferOut.newLine();
		pintarRutas(nomFichero, bufferOut);
		repartoMidiclorianos();
		pintarPrimerTurno(nomFichero, bufferOut);
		moverPersonajes(nomFichero, bufferOut);
	}

	private String ficheroTablero() {
		Queue<Personaje> cola = new LinkedList<Personaje>();
		String string = " ";
		for (int i = 0; i < columnas; i++) {
			string = string + "_ ";
		}
		string = string + "\n";
		for (int i = 0; i < filas; i++) {
			string = string + "|";
			for (int j = 0; j < columnas; j++) {
				if (cola.size() > 1) {
					string = string + cola.size();
				} else if (cola.size() == 0) {
					if (i < filas - 1
							&& grafo.adyacente(mapa[i][j].obtenerIdEstacion(), mapa[i + 1][j].obtenerIdEstacion())) {
						string = string + " ";
					} else {
						string = string + "_";
					}
				} else if (cola.size() == 1) {
					string = string + cola.element();
				}
				if (j < columnas - 1
						&& grafo.adyacente(mapa[i][j].obtenerIdEstacion(), mapa[i][j + 1].obtenerIdEstacion())) {
					string = string + " ";
				} else {
					string = string + "|";
				}
			}
			string = string + "\n";
		}
		string = string.substring(0, string.length() - 1);
		return string;
	}

	public BufferedWriter devolverFlujo() {
		return bufferOut;
	}

	private void moverPersonajes(String nomFichero, BufferedWriter bufferOut) throws IOException {
		Personaje personaje;
		while (turno <= MAXturno+1 && !Puerta.obtenerInstancia().obtenerBoolPuerta()) {
			for (int i = 0; i < filas; i++) {
				for (int j = 0; j < columnas; j++) {
					if (!mapa[i][j].obtenerColaPersonajes().isEmpty()) {
						while (!comprobarMovidos(mapa[i][j].obtenerColaPersonajes())) {
							personaje = mapa[i][j].obtenerPrimero();
							if (personaje.obtenerTurno() < turno) {
								personaje.accionPuerta(mapa[i][j]);
								if (Puerta.obtenerInstancia().obtenerBoolPuerta()) {
									mapa[i][j].borrarPersonaje(personaje);
									insertarSalaGanadores(personaje);
								}
								personaje.aumentarTurno();
								if (!personaje.obtenerColaMovimientos().isEmpty()) {
									personaje.moverPersonaje(i, j, personaje.obtenerMovimiento());
								}
							} else {
								personaje = mapa[i][j].obtenerPrimero();
								mapa[i][j].obtenerColaPersonajes().remove();
								mapa[i][j].obtenerColaPersonajes().add(personaje);
							}
						}
					}
				}
			}
			pintarInformacion(nomFichero, bufferOut);
			turno++;
			resetBoolPersonajes();
		}
		if (Puerta.obtenerInstancia().obtenerBoolPuerta()) {
			System.out.println("(personajesganadores)");
			bufferOut.write("(personajesganadores)");
			bufferOut.newLine();
			Queue<Personaje> cola = new LinkedList<Personaje>();
			cola.addAll(devolverEstacionGanadores().obtenerColaPersonajes());
			while (!cola.isEmpty()) {
				System.out.println("(ganador:" + cola.peek().devolverTipo() + ":" + cola.peek().obtenerMarca() + ":"
						+ "1111" + ":" + cola.peek().obtenerTurno() + ":" + cola.peek().devolverMidiclorianos() + ")");
				bufferOut.write("(ganador:" + cola.peek().devolverTipo() + ":" + cola.peek().obtenerMarca() + ":"
						+ "1111" + ":" + cola.peek().obtenerTurno() + ":" + cola.peek().devolverMidiclorianos() + ")");
				bufferOut.newLine();
				cola.remove();
			}
		}
	}

	private void insertarSalaGanadores(Personaje personaje) {
		Estacion estacion = devolverEstacionGanadores();
		estacion.insertarPersonaje(personaje);
	}

	private void pintarInformacion(String nomFichero, BufferedWriter bufferOut) throws IOException {
		bufferOut.write("(turno:" + (turno-1) + ")");
		bufferOut.newLine();
		bufferOut.write("(galaxia:" + ((filas) * (columnas) - 1) + ")");
		bufferOut.newLine();
		bufferOut.write("(puerta:" + puerta.obtenerEstadoPuerta() + ":" + puerta.obtenerAltura() + ":" + puerta + ":"
				+ puerta.obtenerProbados() + ")");
		bufferOut.newLine();
		bufferOut.write(this.toString());
		bufferOut.newLine();
		System.out.println("(turno:" + (turno-1) + ")");
		System.out.println("(galaxia:" + ((filas) * (columnas) - 1) + ")");
		System.out.println("(puerta:" + puerta.obtenerEstadoPuerta() + ":" + puerta.obtenerAltura() + ":" + puerta + ":"
				+ puerta.obtenerProbados() + ")");
		System.out.println(this);
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				if (mapa[i][j].mostrarMidiclorianos() != " ") {
					System.out.println("(estacion:" + mapa[i][j].obtenerIdEstacion() + ":"
							+ mapa[i][j].mostrarMidiclorianos() + ")");
					bufferOut.write("(estacion:" + mapa[i][j].obtenerIdEstacion() + ":"
							+ mapa[i][j].mostrarMidiclorianos() + ")");
					bufferOut.newLine();
				}
			}
		}
		// System.out.println("\n");
		pintarInfoPersonajes(nomFichero, bufferOut);
	}

	private void pintarInfoPersonajes(String nomFichero, BufferedWriter bufferOut) throws IOException {
		Queue<Personaje> cola = new LinkedList<Personaje>();
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				cola.addAll(mapa[i][j].obtenerColaPersonajes());
				if (cola != null) {
					while (!cola.isEmpty()) {
						System.out.println("(" + cola.peek().devolverTipo() + ":" + cola.peek().obtenerMarca() + ":"
								+ mapa[i][j].obtenerIdEstacion() + ":" + cola.peek().obtenerTurno() + ":"
								+ cola.peek().devolverMidiclorianos() + ")");
						bufferOut.write("(" + cola.peek().devolverTipo() + ":" + cola.peek().obtenerMarca() + ":"
								+ mapa[i][j].obtenerIdEstacion() + ":" + cola.peek().obtenerTurno() + ":"
								+ cola.peek().devolverMidiclorianos() + ")");
						bufferOut.newLine();
						cola.remove();
					}
				}
			}
		}
	}

	private void pintarPrimerTurno(String nomFichero, BufferedWriter bufferOut) throws IOException {
		bufferOut.write("(turno:0)");
		bufferOut.newLine();
		bufferOut.write("(galaxia:" + ((filas) * (columnas) - 1) + ")");
		bufferOut.newLine();
		bufferOut.write("(puerta:" + puerta.obtenerEstadoPuerta() + ":" + puerta.obtenerAltura() + ":" + puerta + ":"
				+ puerta.obtenerProbados() + ")");
		bufferOut.newLine();
		bufferOut.write(this.toString());
		bufferOut.newLine();
		System.out.println("(turno:0)");
		System.out.println("(galaxia:" + ((filas) * (columnas) - 1) + ")");
		System.out.println("(puerta:" + puerta.obtenerEstadoPuerta() + ":" + puerta.obtenerAltura() + ":" + puerta + ":"
				+ puerta.obtenerProbados() + ")");
		System.out.println(this);
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				if (mapa[i][j].mostrarMidiclorianos() != " ") {
					System.out.println("(estacion:" + mapa[i][j].obtenerIdEstacion() + ":"
							+ mapa[i][j].mostrarMidiclorianos() + ")");
					bufferOut.write("(estacion:" + mapa[i][j].obtenerIdEstacion() + ":"
							+ mapa[i][j].mostrarMidiclorianos() + ")");
					bufferOut.newLine();
				}
			}
		}
		// System.out.println("\n");
		pintarInfoPersonajes(nomFichero, bufferOut);
	}

	private void repartoMidiclorianos() {
		ArrayList<Midicloriano> midiclorianos = new ArrayList<Midicloriano>();
		for (int i = 0; i < 30; i++) {
			Midicloriano midi = new Midicloriano(i);
			midiclorianos.add(midi);
			if (i % 2 != 0) {
				Midicloriano midi2 = new Midicloriano(i);
				midiclorianos.add(midi2);
			}
		}
		int num = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 5; j++) {
				mapa[salasRepartir.get(i) / columnas][salasRepartir.get(i) % columnas]
						.insertarMidicloriano(midiclorianos.get(num));
				num++;
			}
		}
	}

	public int obtenerFilas() {
		return filas;
	}

	public int obtenerColumnas() {
		return columnas;
	}

	public void resetBoolPersonajes() {
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				for (int num = 0; num < mapa[i][j].obtenerColaPersonajes().size(); num++) {
					Personaje personaje = mapa[i][j].obtenerColaPersonajes().peek();
					if (personaje.movido() == true) {
						personaje.personajeNoMovido();
					}
					mapa[i][j].obtenerColaPersonajes().remove();
					mapa[i][j].obtenerColaPersonajes().add(personaje);
				}
			}
		}
	}

	// public boolean comprobarMovidos(Queue<Personaje> colaPersonajes) {
	// boolean movidos = true;
	// Personaje personaje;
	// int i = 0;
	// while (i < colaPersonajes.size() && movidos == true) {
	// personaje = colaPersonajes.peek();
	// if (personaje.movido() == false) {
	// movidos = false;
	// } else {
	// colaPersonajes.remove();
	// colaPersonajes.add(personaje);
	// }
	// i++;
	// }
	// return movidos;
	// }

	public boolean comprobarMovidos(Queue<Personaje> colaPersonajes) {
		boolean movidos = true;
		Personaje personaje;
		int i = 0;
		while (i < colaPersonajes.size() && movidos == true) {
			personaje = colaPersonajes.peek();
			if (personaje.obtenerTurno() < turno) {
				movidos = false;
			} else {
				colaPersonajes.remove();
				colaPersonajes.add(personaje);
			}
			i++;
		}
		return movidos;
	}

	public void borrarPersonaje(int x, int y) {
		mapa[x][y].obtenerColaPersonajes().remove();
	}

	public Estacion devolverEstacion(int x, int y) {
		return mapa[x][y];
	}

	// public void insertar(int x, int y, Personaje personaje) {
	// mapa[x][y].insertarPersonaje(personaje);
	// personaje.accionEstacion(mapa[x][y]);
	// personaje.accionPuerta(mapa[x][y]);
	//
	// }

	// public void moverPersonaje(int x, int y, Personaje personaje, Dir
	// direccion) {
	// if (direccion == Dir.N && x > 0) {
	// mapa[x - 1][y].insertarPersonaje(personaje);
	// personaje.accionEstacion(mapa[x - 1][y]);
	// // personaje.accionPuerta(mapa[x-1][y]);
	// } else if (direccion == Dir.S && x < filas - 1) {
	// mapa[x + 1][y].insertarPersonaje(personaje);
	// personaje.accionEstacion(mapa[x + 1][y]);
	// // personaje.accionPuerta(mapa[x+1][y]);
	// } else if (direccion == Dir.E && y < columnas - 1) {
	// mapa[x][y + 1].insertarPersonaje(personaje);
	// personaje.accionEstacion(mapa[x][y + 1]);
	// // personaje.accionPuerta(mapa[x][y+1]);
	// } else if (direccion == Dir.O && y > 0) {
	// mapa[x][y - 1].insertarPersonaje(personaje);
	// personaje.accionEstacion(mapa[x][y - 1]);
	// // personaje.accionPuerta(mapa[x][y-1]);
	// }
	// personaje.personajeMovido(); // pone movido a true
	// mapa[x][y].obtenerColaPersonajes().remove();
	// }

	// public void pintarMidiclorianos(ArrayList<Midicloriano> _midiclorianos) {
	// for (int i = 0; i < _midiclorianos.size(); i++) {
	// System.out.println(_midiclorianos.get(i));
	// }
	// }

	public String pintarMarcas() {
		String string = " ";
		for (int i = 0; i < columnas; i++) {
			string = string + "_ ";
		}
		string = string + "\n";
		for (int i = 0; i < filas; i++) {
			string = string + "|";
			for (int j = 0; j < columnas; j++) {
				string = string + mapa[i][j].obtenerMarca();
				string = string + "|";
			}
			string = string + "\n";
		}
		return string;
	}

	// public String toString() {
	// String string = " ";
	// for (int i = 0; i < columnas; i++) {
	// string = string + "_ ";
	// }
	// string = string + "\n";
	// for (int i = 0; i < filas; i++) {
	// string = string + "|";
	// for (int j = 0; j < columnas; j++) {
	// if (mapa[i][j].obtenerColaPersonajes().size() > 1) {
	// string = string + mapa[i][j].obtenerColaPersonajes().size();
	// } else if (mapa[i][j].obtenerColaPersonajes().size() == 0) {
	// string = string + "_";
	// } else if (mapa[i][j].obtenerColaPersonajes().size() == 1) {
	// string = string + mapa[i][j].obtenerColaPersonajes().element();
	// }
	// string = string + "|";
	// }
	// string = string + "\n";
	// }
	// return string;
	// }

	public String toString() {
		String string = " ";
		for (int i = 0; i < columnas; i++) {
			string = string + "_ ";
		}
		string = string + "\n";
		for (int i = 0; i < filas; i++) {
			string = string + "|";
			for (int j = 0; j < columnas; j++) {
				if (mapa[i][j].obtenerColaPersonajes().size() > 1) {
					string = string + mapa[i][j].obtenerColaPersonajes().size();
				} else if (mapa[i][j].obtenerColaPersonajes().size() == 0) {
					if (i < filas - 1
							&& grafo.adyacente(mapa[i][j].obtenerIdEstacion(), mapa[i + 1][j].obtenerIdEstacion())) {
						string = string + " ";
					} else {
						string = string + "_";
					}
				} else if (mapa[i][j].obtenerColaPersonajes().size() == 1) {
					string = string + mapa[i][j].obtenerColaPersonajes().element();
				}
				if (j < columnas - 1
						&& grafo.adyacente(mapa[i][j].obtenerIdEstacion(), mapa[i][j + 1].obtenerIdEstacion())) {
					string = string + " ";
				} else {
					string = string + "|";
				}
			}
			string = string + "\n";
		}
		string = string.substring(0, string.length() - 1);
		return string;
	}

}
