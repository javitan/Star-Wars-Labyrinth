package Personajes;

public class FamiliaReal extends Personaje {

	public FamiliaReal(String _nombre, char _marca, int _turno) {
		nombre = _nombre;
		marca = _marca;
		turno = _turno;
		Dir[] direcciones = { Dir.S, Dir.S, Dir.E, Dir.E, Dir.N, Dir.E, Dir.N, Dir.E, Dir.S, Dir.E, Dir.S, Dir.S, Dir.O,
				Dir.S, Dir.E, Dir.S };
		for (int i = 0; i < direcciones.length; i++) {
			movimientos.add(direcciones[i]);
		}
	}

	public String toString() {
		return super.toString();
	}

}
