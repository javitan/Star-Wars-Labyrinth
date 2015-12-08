package Personajes;

public class Contrabandista extends Personaje {

	public Contrabandista(String _nombre, char _marca, int _turno) {
		nombre = _nombre;
		marca = _marca;
		turno = _turno;
		Dir[] direcciones = { Dir.E, Dir.E, Dir.E, Dir.E, Dir.O, Dir.N, Dir.E, Dir.E, Dir.S };
		for (int i = 0; i < direcciones.length; i++) {
			movimientos.add(direcciones[i]);
		}
	}

	public String toString() {
		return super.toString();
	}

}
