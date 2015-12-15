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

	/**
	 * Método que devuelve el número id de la sala que contiene la puerta.
	 * 
	 * @return salaPuerta entero con el id de la sala que contiene la puerta.
	 */
	public int devolverSalaPuerta() {
		return salaPuerta;
	}

	/**
	 * Método que pinta por pantalla y escribe en el fichero log las rutas que
	 * tienen los personajes antes de comenzar la ejecución.
	 * 
	 * @param nomFichero
	 *            nombre del fichero log donde se va a escribir
	 * @param bufferOut
	 *            buffer de salida donde se escribirá
	 */
	private void pintarRutas(String nomFichero, BufferedWriter bufferOut) {
		Queue<Personaje> cola = new LinkedList<Personaje>();
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				cola.addAll(mapa[i][j].obtenerColaPersonajes());
				if (cola != null) {
					while (!cola.isEmpty()) {
						System.out.println("(ruta:" + cola.peek().obtenerMarca() + ":" + " "
								+ cola.peek().devolverDirecciones() + ")");
						try {
							bufferOut.write("(ruta:" + cola.peek().obtenerMarca() + ":" + " "
									+ cola.peek().devolverDirecciones() + ")");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							bufferOut.newLine();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						cola.remove();
					}
				}
			}
		}
	}

	/**
	 * Método que devuelve la estación 1111 correspondiente a la estación de los
	 * ganadores.
	 * 
	 * @return estacionGanadores estación 1111
	 */
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
	 * Método que inserta un personaje de tipo Familia Real y que además le pasa
	 * los movimientos calculados previamente.
	 * 
	 * @param pers
	 *            personaje de tipo Familia Real
	 */
	public void insertarFamilia(Personaje pers) {
		mapa[0][0].insertarPersonaje(pers);
		for (int i = 0; i < movimientosFR.size(); i++) {
			pers.ponerDireccion(movimientosFR.get(i));
		}
	}

	/**
	 * Método que inserta un personaje de tipo Jedi.
	 * 
	 * @param pers
	 *            personaje de tipo Jedi
	 */
	public void insertarJedi(Personaje pers) {
		mapa[0][0].insertarPersonaje(pers);
	}

	/**
	 * Método que inserta un personaje de tipo Contrabandista.
	 * 
	 * @param pers
	 *            personaje de tipo contrabandista
	 */
	public void insertarContrabandista(Personaje pers) {
		mapa[filas - 1][0].insertarPersonaje(pers);
	}

	/**
	 * Método que comprueba si dos salas dadas por parámetro son adyacentes o no
	 * en el grafo.
	 * 
	 * @param origen
	 *            número id de una de las salas que se quiere comprobar
	 * @param destino
	 *            número id de la otra sala que se quiere comprobar
	 * @return booleano indicando true si son adyacentes o false en caso
	 *         contrario
	 */
	public boolean sonAdyacentes(int origen, int destino) {
		if (grafo.adyacente(origen, destino)) {
			return true;
		}
		return false;
	}

	/**
	 * Método que inserta un personaje de tipo Imperial. Además, calcula sus
	 * movimientos y los inserta en el personaje.
	 * 
	 * @param pers
	 *            personaje de tipo Imperial
	 */
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

	/**
	 * Método que realiza toda la ejecución del proyecto.
	 * 
	 * @param nomFichero
	 *            nombre del fichero log
	 * @param bufferOut
	 *            buffer de salida donde se escrbirá la ejecución
	 * @throws IOException
	 */
	public void ejecucion(String nomFichero, BufferedWriter bufferOut) throws IOException {
		bufferOut.write(ficheroTablero());
		bufferOut.newLine();
		pintarRutas(nomFichero, bufferOut);
		repartoMidiclorianos();
		pintarPrimerTurno(nomFichero, bufferOut);
		moverPersonajes(nomFichero, bufferOut);
	}

	/**
	 * Método devuelve una cadena con el tablero sin los personajes.
	 * 
	 * @return string con el tablero sin personajes
	 */
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

	/**
	 * Método que devuelve el flujo de salida para trabajar con el fichero log.
	 * 
	 * @return bufferOut con el flujo de salida
	 */
	public BufferedWriter devolverFlujo() {
		return bufferOut;
	}

	/**
	 * Método que se encarga de realizar los movimientos de los personaje en el
	 * tablero. Además va pintando cada uno de los movimientos seguido de la
	 * información y escribirlo todo en el fichero log.
	 * 
	 * @param nomFichero
	 *            nombre del fichero log
	 * @param bufferOut
	 *            flujo de salida para trabajar con el fichero log
	 * @throws IOException
	 */
	private void moverPersonajes(String nomFichero, BufferedWriter bufferOut) throws IOException {
		Personaje personaje;
		while (turno <= MAXturno + 1 && !Puerta.obtenerInstancia().obtenerBoolPuerta()) {
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

	/**
	 * Método que inserta un personaje obtenido por parámetro en la estación de
	 * los ganadores (estación 1111).
	 * 
	 * @param personaje
	 *            personaje ganador
	 */
	private void insertarSalaGanadores(Personaje personaje) {
		Estacion estacion = devolverEstacionGanadores();
		estacion.insertarPersonaje(personaje);
	}

	/**
	 * Método que imprime por pantalla y escribe en el fichero de log toda la
	 * información correspondiente a los laberintos con los turnos, las
	 * estaciones y la puerta.
	 * 
	 * @param nomFichero
	 *            nombre del fichero log
	 * @param bufferOut
	 *            buffer de salida para trabajar con el fichero log
	 * @throws IOException
	 */
	private void pintarInformacion(String nomFichero, BufferedWriter bufferOut) throws IOException {
		bufferOut.write("(turno:" + (turno - 1) + ")");
		bufferOut.newLine();
		bufferOut.write("(galaxia:" + ((filas) * (columnas) - 1) + ")");
		bufferOut.newLine();
		bufferOut.write("(puerta:" + puerta.obtenerEstadoPuerta() + ":" + puerta.obtenerAltura() + ":" + puerta + ":"
				+ puerta.obtenerProbados() + ")");
		bufferOut.newLine();
		bufferOut.write(this.toString());
		bufferOut.newLine();
		System.out.println("(turno:" + (turno - 1) + ")");
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

	/**
	 * Método que imprime por pantalla y escribe en el fichero la información de
	 * los personajes: su tipo, marca, sala en la que se encuentra, turno y
	 * midiclorianos recogidos.
	 * 
	 * @param nomFichero
	 *            nombre del fichero log
	 * @param bufferOut
	 *            buffer de salida para trabajar con el fichero log
	 * @throws IOException
	 */
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

	/**
	 * Método que escribirá por pantalla toda la información que corresponde al
	 * turno 0. Además la incluirá en el fichero log.
	 * 
	 * @param nomFichero
	 *            nombre del fichero log
	 * @param bufferOut
	 *            buffer de salida para trabajar con el fichero log
	 * @throws IOException
	 */
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

	/**
	 * Método que realiza el reparto de midiclorianos por las salas tal y como
	 * se establece en la documentación.
	 * 
	 */
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

	/**
	 * Método que devuelve el número de filas que tiene el la matriz de las
	 * estaciones.
	 * 
	 * @return número de filas del tablero
	 */
	public int obtenerFilas() {
		return filas;
	}

	/**
	 * Método que devuelve el número de columnas que tiene la matriz de las
	 * estaciones.
	 * 
	 * @return número de columnas del tablero
	 */
	public int obtenerColumnas() {
		return columnas;
	}

	/**
	 * Método que pone el booleano que indica si un personaje se ha movido a
	 * false.
	 * 
	 */
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

	/**
	 * Método que comprueba si en la cola de personajes de la sala queda algún
	 * personaje por moverse.
	 * 
	 * @param colaPersonajes
	 *            Cola de personajes de una estación
	 * @return booleano con el valor true si todos los personajes se han movido;
	 *         false en caso de que exista alguno que no se haya movido aún
	 */
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

	/**
	 * Método que devuelve una estación concreta del tablero, dadas una fila y
	 * columnas por parámetro.
	 * 
	 * @param x
	 *            fila correspondiente a la matriz de estaciones
	 * @param y
	 *            columna correspondiente a la matriz de estaciones
	 * @return estación que se encuentra en la fila x, columna y
	 */
	public Estacion devolverEstacion(int x, int y) {
		return mapa[x][y];
	}

	/**
	 * Método que pinta el tablero sin tirar las paredes.
	 * 
	 * @return string con el tablero con todas las paredes
	 */
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

	/**
	 * Método toString para pintar el laberinto. 
	 *
	 */
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
