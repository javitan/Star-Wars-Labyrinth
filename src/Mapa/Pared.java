package Mapa;

public class Pared {

	private int pared1;
	private int pared2;

	public Pared(int _pared1, int _pared2) {
		pared1 = _pared1;
		pared2 = _pared2;
	}

	public int devolverPared1() {
		return pared1;
	}

	public int devolverPared2() {
		return pared2;
	}

	public String toString() {
		return ("(" + pared1 + ", " + pared2 + ")");
	}

}
