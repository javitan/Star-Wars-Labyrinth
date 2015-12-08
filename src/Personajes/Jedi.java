package Personajes;

public class Jedi extends Personaje {

	public Jedi(String _nombre, char _marca, int _turno) {
		nombre = _nombre;
		marca = _marca;
		turno = _turno;
		Dir[] direcciones = { Dir.E, Dir.E, Dir.S, Dir.O, Dir.O, Dir.E, Dir.S, Dir.O, Dir.E, Dir.N, Dir.E, Dir.S, Dir.E,
				Dir.S, Dir.O, Dir.O, Dir.O, Dir.S, Dir.E, Dir.O, Dir.N, Dir.E, Dir.E, Dir.S, Dir.N, Dir.E, Dir.S, Dir.S,
				Dir.O, Dir.O, Dir.O, Dir.E, Dir.E, Dir.E, Dir.E, Dir.O, Dir.N, Dir.E, Dir.E, Dir.S };
		for (int i = 0; i < direcciones.length; i++) {
			movimientos.add(direcciones[i]);
		}
	}

	public String toString() { // TODO
		return super.toString();
	}

}
