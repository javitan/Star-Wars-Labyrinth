package Personajes;

public class Jedi extends Personaje {

	public Jedi() {
		nombre = "Jedi";
		marca = 'J';
		turno = 0;
		Dir[] direcciones = { Dir.E, Dir.S, Dir.S, Dir.S, Dir.O, Dir.S, Dir.E, Dir.E, Dir.N, Dir.E, Dir.S, Dir.S,
				Dir.E, Dir.E };
		for (int i = 0; i < direcciones.length; i++){
			movimientos.add(direcciones[i]);
		}
	}

	public Jedi(String _nombre) {
		nombre = _nombre;
		marca = 'J';
		Dir[] direcciones = { Dir.E, Dir.S, Dir.S, Dir.S, Dir.O, Dir.S, Dir.E, Dir.E, Dir.N, Dir.E, Dir.S, Dir.S,
				Dir.E, Dir.E };
		for (int i = 0; i < direcciones.length; i++){
			movimientos.add(direcciones[i]);
		}
	}
	
	public String toString(){
		return super.toString();
	}

}
