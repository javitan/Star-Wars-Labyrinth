package Personajes;

import java.io.IOException;

import Mapa.Galaxia;

public class Jedi extends Personaje {

	public Jedi(String _nombre, char _marca, int _turno) throws IOException {
		nombre = _nombre;
		marca = _marca;
		turno = _turno;
		tipo = "jedi";
		manoDerecha(0);
	}

	public String toString() { // TODO
		return super.toString();
	}

}
