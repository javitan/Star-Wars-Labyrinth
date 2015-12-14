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

	public Personaje() {
		nombre = "";
		marca = '?';
		listaMidi = new LinkedList<Midicloriano>();
		movimientos = new LinkedList<Dir>();
		movido = false;
		turno = 0;
		tipo = "";
	}

	public void aumentarTurno() {
		turno++;
	}

	public String devolverTipo() {
		return tipo;
	}

	public void insertarMovimientos(ArrayList<Dir> direcciones) {
		for (int i = 0; i < direcciones.size(); i++) {
			movimientos.add(direcciones.get(i));
		}
	}

	public void moverPersonaje(int x, int y, Dir direccion) throws IOException {
		Galaxia galaxia = Galaxia.obtenerInstancia();
		Estacion estacion = null;
		if (direccion == Dir.N && x > 0) {
			// mapa[x - 1][y].insertarPersonaje(personaje);
			// personaje.accionEstacion(mapa[x - 1][y]);
			estacion = galaxia.devolverEstacion(x - 1, y);
			estacion.insertarPersonaje(this);
			this.accionEstacion(estacion);
		} else if (direccion == Dir.S && x < galaxia.obtenerFilas() - 1) {
			// mapa[x + 1][y].insertarPersonaje(personaje);
			// personaje.accionEstacion(mapa[x + 1][y]);
			estacion = galaxia.devolverEstacion(x + 1, y);
			estacion.insertarPersonaje(this);
			this.accionEstacion(estacion);
		} else if (direccion == Dir.E && y < galaxia.obtenerColumnas() - 1) {
			// mapa[x][y + 1].insertarPersonaje(personaje);
			// personaje.accionEstacion(mapa[x][y + 1]);
			estacion = galaxia.devolverEstacion(x, y + 1);
			estacion.insertarPersonaje(this);
			this.accionEstacion(estacion);
		} else if (direccion == Dir.O && y > 0) {
			// mapa[x][y - 1].insertarPersonaje(personaje);
			// personaje.accionEstacion(mapa[x][y - 1]);
			estacion = galaxia.devolverEstacion(x, y - 1);
			estacion.insertarPersonaje(this);
			this.accionEstacion(estacion);
		} else {
			galaxia.devolverEstacion(x, y).insertarPersonaje(this);
		}
		this.personajeMovido(); // pone movido a true
		galaxia.devolverEstacion(x, y).obtenerColaPersonajes().remove();
	}

	public int obtenerTurno() {
		return turno;
	}

	public String obtenerNombre() {
		return nombre;
	}

	public char obtenerMarca() {
		return marca;
	}

	public LinkedList<Midicloriano> obtenerListaMidiclorianos() {
		return listaMidi;
	}

	public void accionPuerta(Estacion estacion) {
		if (estacion.obtenerPuerta() != null && !listaMidi.isEmpty() && !estacion.obtenerPuerta().obtenerBoolPuerta()) {
			Puerta puerta = Puerta.obtenerInstancia();
			puerta.probarMidicloriano(listaMidi.getLast());
			listaMidi.removeLast();
		}
	}

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

	public String devolverMidiclorianos() {
		String cadena = " ";
		for (int i = listaMidi.size() - 1; i >= 0; i--) {
			cadena = cadena + listaMidi.get(i);
			cadena = cadena + " ";
		}
		cadena = cadena.substring(0, cadena.length() - 1);
		return cadena;
	}

	// public void insertarMovimientos(Dir[] direcciones){
	// for (int i = 0; i < direcciones.length; i++){
	// movimientos.add(direcciones[i]);
	// }
	// }

	public void accionEstacion(Estacion estacion) {
		if (estacion.mostrarMidiclorianos() != " ") {
			listaMidi.add(estacion.obtenerPrimerMidicloriano());
			estacion.borrarPrimerMidicloriano();
		}
	}

	public boolean movido() {
		return movido;
	}

	public Dir obtenerMovimiento() {
		Dir movimiento = movimientos.peek();
		movimientos.remove();
		return movimiento;
	}

	public void personajeMovido() {
		movido = true;
	}

	public void personajeNoMovido() {
		movido = false;
	}

	public Queue<Dir> obtenerColaMovimientos() {
		return movimientos;
	}

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

	public String toString() {
		String string;
		string = Character.toString(this.marca);
		return string;
	}

	public void ponerDireccion(Dir movimiento) {
		movimientos.add(movimiento);
	}

}
