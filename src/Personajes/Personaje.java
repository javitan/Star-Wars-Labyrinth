package Personajes;

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

	public Personaje() {
		nombre = "";
		marca = '?';
		listaMidi = new LinkedList<Midicloriano>();
		movimientos = new LinkedList<Dir>();
		movido = false;
		turno = 0;
	}

	public void aumentarTurno() {
		turno++;
	}

	public void moverPersonaje(int x, int y, Dir direccion) {
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

	// public void moverPersonaje(int x, int y) {
	// Galaxia galaxia = Galaxia.obtenerInstancia();
	// Dir direccion = movimientos.peek();
	// if (direccion == Dir.N && x > 0) {
	// galaxia.insertar(x-1, y, this);
	// //mapa[x - 1][y].insertarPersonaje(this);
	// } else if (direccion == Dir.S && x < galaxia.obtenerFilas() - 1) {
	// galaxia.insertar(x+1, y, this);
	// //mapa[x + 1][y].insertarPersonaje(this);
	// } else if (direccion == Dir.E && y < galaxia.obtenerColumnas() - 1) {
	// galaxia.insertar(x, y+1, this);
	// //mapa[x][y + 1].insertarPersonaje(this);
	// } else if (direccion == Dir.O && y > 0) {
	// galaxia.insertar(x, y-1, this);
	// //mapa[x][y - 1].insertarPersonaje(this);
	// }
	// this.personajeMovido(); // pone movido a true
	// galaxia.borrarPersonaje(x, y);
	// }

	public String toString() {
		String string;
		string = Character.toString(this.marca);
		return string;
	}

	public void ponerDireccion(Dir movimiento) {
		movimientos.add(movimiento);
	}

}
