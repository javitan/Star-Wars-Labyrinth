package Personajes;

public class Contrabandista extends Personaje {

	public Contrabandista() {
		nombre = "Contrabandista";
		marca = 'C';
		turno = 0;
		Dir[] direcciones = { Dir.N, Dir.N, Dir.N, Dir.E, Dir.S, Dir.E, Dir.N, Dir.N, Dir.E, Dir.N, Dir.E, Dir.E,
				Dir.S, Dir.S, Dir.S, Dir.S, Dir.S };
		for (int i = 0; i < direcciones.length; i++){
			movimientos.add(direcciones[i]);
		}
	}

	public Contrabandista(String _nombre) {
		nombre = _nombre;
		marca = 'C';
		Dir[] direcciones = { Dir.N, Dir.N, Dir.N, Dir.E, Dir.S, Dir.E, Dir.N, Dir.N, Dir.E, Dir.N, Dir.E, Dir.E,
				Dir.S, Dir.S, Dir.S, Dir.S, Dir.S };
		for (int i = 0; i < direcciones.length; i++){
			movimientos.add(direcciones[i]);
		}
	}
	
	public String toString(){
		return super.toString();
	}

}
