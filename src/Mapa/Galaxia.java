/**
 * Implementación de la clase Galaxia.
 * @version 2.0
 * @author <b> Planet Express </b><br>
 * Nombre y apellidos: Javier Garcia Valencia
 * Curso: 2o GIIIS
 * Asignatura Desarrollo de Programas<br/>
 * Curso 15/16
 */

package Mapa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import Cargador.GenAleatorios;
import ED.Grafo;
import Personajes.Personaje;

public class Galaxia {

	private int filas;
	private int columnas;
	private static Galaxia singleton;
	private Estacion mapa[][];
	private Puerta puerta;
	private int MAXturno = 50;
	private int turno = 2;
	private ArrayList<Pared> listaParedes;
	private Grafo grafo;

	private Galaxia(int _columnas, int _filas, int _estacionPuerta, int _altura) {
		grafo = new Grafo();
		filas = _filas;
		columnas = _columnas;
		mapa = new Estacion[filas][columnas];
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
		System.out.println(pintarMarcas());
		insertarParedes();
		// Set<Integer> ady = new TreeSet();
		tirarParedes();
		Set<Integer> ady = new HashSet<Integer>();
		grafo.adyacentes(4, ady);
		System.out.println(pintarMarcas());
		puerta = Puerta.obtenerInstanciaParam(_altura);
		puerta.configurarPuerta();
		mapa[_estacionPuerta / _columnas][_estacionPuerta % _columnas].ponerPuerta(puerta);
	}

	public static Galaxia obtenerInstanciaParam(int columnas, int filas, int estacionPuerta, int altura) {
		if (singleton == null) {
			singleton = new Galaxia(columnas, filas, estacionPuerta, altura);
		}
		return singleton;
	}

	public static Galaxia obtenerInstancia() {
		if (singleton == null) {
			singleton = new Galaxia(0, 0, 0, 0);
		}
		return singleton;
	}

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

	public void tirarParedes() {
		int pared1;
		int pared2;
		while (!listaParedes.isEmpty()) {
			int aleatorio = GenAleatorios.generarNumero(listaParedes.size());
			pared1 = listaParedes.get(aleatorio).devolverPared1();
			pared2 = listaParedes.get(aleatorio).devolverPared2();
			if (mapa[pared1 / columnas][pared1 % columnas].obtenerMarca() != mapa[pared2 / columnas][pared2 % columnas]
					.obtenerMarca()) {
				grafo.nuevoArco(pared1, pared2, 1);
				grafo.nuevoArco(pared2, pared1, 1);
				// extenderMarca
				for (int i = 0; i < filas; i++) {
					for (int j = 0; j < columnas; j++) {
						if (mapa[i][j].obtenerMarca() == mapa[pared2 / columnas][pared2 % columnas].obtenerMarca()) {
							mapa[i][j].ponerMarca(mapa[pared1 / columnas][pared1 % columnas].obtenerMarca());
						}
					}
				}
			}
			listaParedes.remove(aleatorio);
		}
	}

	public void insertarFamilia(Personaje pers) {
		mapa[0][0].insertarPersonaje(pers);
	}

	public void insertarJedi(Personaje pers) {
		mapa[0][0].insertarPersonaje(pers);
	}

	public void insertarContrabandista(Personaje pers) {
		mapa[filas - 1][0].insertarPersonaje(pers);
	}

	public void insertarImperial(Personaje pers) {
		mapa[filas - 1][columnas - 1].insertarPersonaje(pers);
	}

	// public void crearPersonajes() {
	// Personaje contra = new Contrabandista();
	// Personaje familia = new FamiliaReal();
	// Personaje jedi = new Jedi();
	// Personaje imperial = new Imperial();
	// mapa[0][0].insertarPersonaje(familia);
	// mapa[0][0].insertarPersonaje(jedi);
	// mapa[filas - 1][columnas - 1].insertarPersonaje(imperial);
	// mapa[filas - 1][0].insertarPersonaje(contra);
	// }

	public void ejecucion() {
		ArrayList<Midicloriano> midiclorianos = new ArrayList<Midicloriano>();
		for (int i = 0; i < 30; i++) {
			Midicloriano midi = new Midicloriano(i);
			midiclorianos.add(midi);
			if (i % 2 != 0) {
				Midicloriano midi2 = new Midicloriano(i);
				midiclorianos.add(midi2);
			}
		}
		// pintarMidiclorianos(midiclorianos);
		Integer salasRepartir[] = { 3, 4, 6, 8, 9, 10, 11, 12, 13 };
		int num = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 5; j++) {
				mapa[salasRepartir[i] / columnas][salasRepartir[i] % columnas]
						.insertarMidicloriano(midiclorianos.get(num));
				num++;
			}
		}
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
				}
			}
		}
		System.out.println("\n");
		Personaje personaje;
		while (turno <= MAXturno && !Puerta.obtenerInstancia().obtenerBoolPuerta()) {
			for (int i = 0; i < filas; i++) {
				for (int j = 0; j < columnas; j++) {
					if (!mapa[i][j].obtenerColaPersonajes().isEmpty()) {
						while (!comprobarMovidos(mapa[i][j].obtenerColaPersonajes())) {
							personaje = mapa[i][j].obtenerPrimero();
							if (personaje.obtenerTurno() < turno) {
								personaje.accionPuerta(mapa[i][j]);
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
			System.out.println("(turno:" + (turno - 1) + ")");
			System.out.println("(galaxia:" + ((filas) * (columnas) - 1) + ")");
			System.out.println("(puerta:" + puerta.obtenerEstadoPuerta() + ":" + puerta.obtenerAltura() + ":" + puerta
					+ ":" + puerta.obtenerProbados() + ")");
			System.out.println(this);
			for (int i = 0; i < filas; i++) {
				for (int j = 0; j < columnas; j++) {
					if (mapa[i][j].mostrarMidiclorianos() != " ") {
						System.out.println("(estacion:" + mapa[i][j].obtenerIdEstacion() + ":"
								+ mapa[i][j].mostrarMidiclorianos() + ")");
					}
				}
			}
			System.out.println("\n");
			turno++;
			resetBoolPersonajes();
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
		String cadena = " ";
		for (int i = 0; i < columnas; i++) {
			cadena = cadena + "_";
		}
		cadena = cadena + "\n";
		for (int i = 0; i < filas; i++) {
			cadena = cadena + "|";
			for (int j = 0; j < columnas; j++) {
				if (mapa[i][j].obtenerColaPersonajes().size() > 1) {
					cadena = cadena + mapa[i][j].obtenerColaPersonajes().size();
				} else if (mapa[i][j].obtenerColaPersonajes().size() == 0) {
					if (i < filas - 1) {
						if (grafo.adyacente(mapa[i][j].obtenerIdEstacion(), mapa[i + 1][j].obtenerIdEstacion())) {
							cadena = cadena + " ";
						} else {
							cadena = cadena + "_";
						}
					} else {
						cadena = cadena + "_";
					}
					if (grafo.adyacente(mapa[i][j].obtenerIdEstacion(), mapa[i][j + 1].obtenerIdEstacion())) {
						cadena = cadena + " ";
					} else {
						cadena = cadena + "|";
					}
				} else if (mapa[i][j].obtenerColaPersonajes().size() == 1) {
					cadena = cadena + mapa[i][j].obtenerColaPersonajes().element();
				}
				// if (j == 0) {
				// cadena = cadena + "|";
				// } else {
				// if (j < columnas - 1) {
				// if (grafo.adyacente(mapa[i][j].obtenerIdEstacion(), mapa[i][j
				// + 1].obtenerIdEstacion())) {
				// cadena = cadena + " ";
				// } else {
				// cadena = cadena + "|";
				// }
				// } else {
				// cadena = cadena + "|";
				// }
				// }
			}
			cadena = cadena + "\n";
		}
		return cadena;
	}

	// public static void main(String[] args) {
	// Galaxia galaxia = Galaxia.obtenerInstancia();
	// galaxia.ejecucion();
	// // System.out.println(galaxia);
	// }

}
