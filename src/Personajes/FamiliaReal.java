package Personajes;

public class FamiliaReal extends Personaje {

	public FamiliaReal() {
		nombre = "Familia Real";
		marca = 'F';
		turno = 0;
		Dir[] direcciones = { Dir.S, Dir.S, Dir.E, Dir.E, Dir.N, Dir.E, Dir.N, Dir.E, Dir.S, Dir.E, Dir.S, Dir.S,
				Dir.O, Dir.S, Dir.E, Dir.S };
		for (int i = 0; i < direcciones.length; i++){
			movimientos.add(direcciones[i]);
		}
	}

	public FamiliaReal(String _nombre) {
		nombre = _nombre;
		marca = 'F';
		Dir[] direcciones = { Dir.S, Dir.S, Dir.E, Dir.E, Dir.N, Dir.E, Dir.N, Dir.E, Dir.S, Dir.E, Dir.S, Dir.S,
				Dir.O, Dir.S, Dir.E, Dir.S };
		for (int i = 0; i < direcciones.length; i++){
			movimientos.add(direcciones[i]);
		}
	}
	
	public String toString(){
		return super.toString();
	}

}
