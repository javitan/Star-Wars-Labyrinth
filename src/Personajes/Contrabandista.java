package Personajes;

import java.io.IOException;

import Mapa.Galaxia;

public class Contrabandista extends Personaje {

	public Contrabandista(String _nombre, char _marca, int _turno) throws IOException {
		nombre = _nombre;
		marca = _marca;
		turno = _turno;
		tipo = "contrabandista";
		manoDerecha(calcularOrigen());
	}
	
	private int calcularOrigen() throws IOException{
		int filas = Galaxia.obtenerInstancia().obtenerFilas();
		return (Galaxia.obtenerInstancia().devolverEstacion(filas-1, 0).obtenerIdEstacion());
	}

	public String toString() {
		return super.toString();
	}

}
