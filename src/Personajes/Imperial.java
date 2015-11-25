package Personajes;

import Mapa.Estacion;
import Mapa.Midicloriano;
import Mapa.Puerta;

public class Imperial extends Personaje {

	public Imperial() {
		nombre = "Imperial";
		marca = 'I';
		turno = 0;
		Dir[] direcciones = { Dir.N, Dir.N, Dir.O, Dir.N, Dir.N, Dir.O, Dir.S, Dir.O, Dir.O, Dir.N, Dir.N, Dir.O, Dir.S,
				Dir.S, Dir.S, Dir.S, Dir.S, Dir.E, Dir.E, Dir.E, Dir.E, Dir.E };
		for (int i = 0; i < direcciones.length; i++) {
			movimientos.add(direcciones[i]);
		}
		insertarMidiclorianosImperial();
	}

	public Imperial(String _nombre, char _marca, int _turno) {
		nombre = _nombre;
		marca = _marca;
		turno = _turno;
		Dir[] direcciones = { Dir.N, Dir.N, Dir.O, Dir.N, Dir.N, Dir.O, Dir.S, Dir.O, Dir.O, Dir.N, Dir.N, Dir.O, Dir.S,
				Dir.S, Dir.S, Dir.S, Dir.S, Dir.E, Dir.E, Dir.E, Dir.E, Dir.E };
		for (int i = 0; i < direcciones.length; i++) {
			movimientos.add(direcciones[i]);
		}
		insertarMidiclorianosImperial();
	}

	public void insertarMidiclorianosImperial() {
		for (int i = 1; i <= 29; i++) {
			Midicloriano midi = new Midicloriano(i);
			listaMidi.add(midi);
		}
	}

	public void accionPuerta(Estacion estacion) {
		if (estacion.obtenerPuerta() != null && estacion.obtenerPuerta().obtenerBoolPuerta()) {
			Puerta.obtenerInstancia().configurarPuerta();
		}
	}

	public void accionEstacion(Estacion estacion) {
		if (!listaMidi.isEmpty() && estacion.obtenerIdEstacion() % 2 == 0) {
			Midicloriano midi = listaMidi.getLast();
			estacion.insertarMidicloriano(midi); // inserta el último
			listaMidi.removeLast();
		}
	}

	public void cogerMidi(Midicloriano midi) {
		listaMidi.addLast(midi);

	}

	public String toString() {
		return super.toString();
	}

}
