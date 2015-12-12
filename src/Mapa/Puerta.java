/**
 * Implementación de la clase Puerta.
 * @version 2.0
 * @author <b> Planet Express </b><br>
 * Nombre y apellidos: Javier Garcia Valencia
 * Curso: 2o GIIIS
 * Asignatura Desarrollo de Programas<br/>
 * Curso 15/16
 */

package Mapa;

import ED.Arbol;
import ED.Cola;

public class Puerta {

	private boolean abierta;
	private boolean configurada;
	private Integer capacidad = 15;
	private Integer altura;
	private Integer numMidiclorianos = 30;
	private Arbol<Midicloriano> probados;
	private Arbol<Midicloriano> cerradura;
	private Midicloriano[] combinacion;
	private static Puerta singleton;

	private Puerta(int _altura) {
		abierta = true;
		configurada = false;
		altura = _altura;
		probados = new Arbol<Midicloriano>();
		cerradura = new Arbol<Midicloriano>();
		combinacion = new Midicloriano[capacidad];
		generarMidiclorianos();
	}

	public static Puerta obtenerInstancia() {
		if (singleton == null) {
			singleton = new Puerta(0);
		}
		return singleton;
	}

	public static Puerta obtenerInstanciaParam(int altura) {
		if (singleton == null) {
			singleton = new Puerta(altura);
		}
		return singleton;
	}

	public String obtenerEstadoPuerta() {
		if (abierta == true) {
			return "abierta";
		} else {
			return "cerrada";
		}
	}

	public int obtenerAltura() {
		return altura;
	}

	public void generarMidiclorianos() {
		int j = 0;
		for (int i = 0; i < numMidiclorianos; i++) {
			if (i % 2 != 0) {
				Midicloriano midi = new Midicloriano(i);
				combinacion[j] = midi;
				j++;
			}
		}
	}

	public void crearCerradura(int izquierda, int derecha) {
		int mitad;
		int posicionIntro;
		if (capacidad % 2 == 0) { // Si la capacidad del llavero es par,
									// tomamos mitad = mitad-1.
			if (derecha != izquierda) {
				mitad = (derecha - izquierda - 1) / 2;
				posicionIntro = mitad + izquierda;
				if (!cerradura.pertenece(combinacion[posicionIntro])) {
					cerradura.insertar(combinacion[posicionIntro]);
					// System.out.println(combinacion[posicionIntro].obtenerIdMidi());
				}
				mitad++;
				if (mitad > 0) {
					crearCerradura(izquierda + mitad, derecha);
					crearCerradura(izquierda, derecha - mitad);
				}
			}
		} else { // Si la capacidad del llavero es impar, tomamos la mitad del
					// array.
			if (derecha != izquierda) {
				mitad = (derecha - izquierda) / 2;
				posicionIntro = mitad + izquierda;
				if (!cerradura.pertenece(combinacion[posicionIntro])) {
					cerradura.insertar(combinacion[posicionIntro]);
					// System.out.println(combinacion[posicionIntro].obtenerIdMidi());
				}
				mitad++;
				if (mitad > 0) {
					crearCerradura(izquierda + mitad, derecha);
					crearCerradura(izquierda, derecha - mitad);
				}
			}
		}
	}

	public void configurarPuerta() {
		crearCerradura(0, capacidad);
		abierta = false;
		configurada = true;
	}

	public boolean comprobarCondiciones() {
		if (cerradura.profundidad() < altura && cerradura.cuantosInternos() >= cerradura.cuantasHojas()) {
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		String cadena = "";
		cadena = cadena + cerradura;
		return cadena;
	}

	public String obtenerProbados() {
		String cadena = "";
		cadena = cadena + probados;
		return cadena;
	}

	public void probarMidicloriano(Midicloriano midi) {
		if (probados.pertenece(midi)) {
			System.out.println("ALARMA ACTIVADA: EL MIDICLORIANO " + midi.obtenerIdMidi()
					+ " SE HA INTRODUCIDO MÁS DE UNA VEZ EN LA PUERTA");
		}
		if (cerradura.pertenece(midi)) {
			cerradura.borrar(midi);
			if (comprobarCondiciones()) {
				abierta = true;
			}
		}
		probados.insertar(midi);
	}

//	public void ejecucion(Cola<Midicloriano> colaMid) {
//		generarMidiclorianos();
//		configurarPuerta();
//		while (!colaMid.estaVacia()) {
//			probarMidicloriano(colaMid.primero());
//			colaMid.desencolar();
//		}
//	}

	public boolean obtenerBoolPuerta() {
		return abierta;
	}

}
