/**
 * Implementación de la clase Puerta.
 * @version 4.0
 * @author <b> Planet Express </b><br>
 * Nombre y apellidos: Javier García Valencia
 * Curso: 3º GIIIS
 * Asignatura Desarrollo de Programas<br/>
 * Curso 15/16
 */

package Mapa;

import ED.Arbol;

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

	/**
	 * Constructor parametrizado de la clase Puerta. Se introduce la altura del
	 * árbol como parámetro.
	 * 
	 * @param _altura
	 *            entero con la altura del árbol para abrir la puerta.
	 */
	private Puerta(int _altura) {
		abierta = true;
		configurada = false;
		altura = _altura;
		probados = new Arbol<Midicloriano>();
		cerradura = new Arbol<Midicloriano>();
		combinacion = new Midicloriano[capacidad];
		generarMidiclorianos();
	}

	/**
	 * Método que no se ejecutará nunca, pero es necesario para el patrón de
	 * diseño de tipo Singleton.
	 * 
	 * @return singleton con una instancia de la clase Puerta.
	 */
	public static Puerta obtenerInstancia() {
		if (singleton == null) {
			singleton = new Puerta(0);
		}
		return singleton;
	}

	/**
	 * Método que devuelve una instancia creada de la clase puerta con el patrón
	 * de diseño Singleton.
	 * 
	 * @param altura
	 *            entero con el número de la altura del árbol para que la puerta
	 *            se abra.
	 * @return singleton con una instancia creada de la clase Puerta.
	 */
	public static Puerta obtenerInstanciaParam(int altura) {
		if (singleton == null) {
			singleton = new Puerta(altura);
		}
		return singleton;
	}

	/**
	 * Método que devuelve el estado de la puerta; abierta o cerrada.
	 * 
	 * @return String indicando "abierta" si se encuentra abierta y "cerrada" en
	 *         caso contrario.
	 */
	public String obtenerEstadoPuerta() {
		if (abierta == true) {
			return "abierta";
		} else {
			return "cerrada";
		}
	}

	/**
	 * Método que devuelve la altura del árbol para que se abriera la puerta.
	 * 
	 * @return entero con la altura
	 */
	public int obtenerAltura() {
		return altura;
	}

	/**
	 * Método que genera los midiclorianos necesarios para la combinación
	 * secreta de la puerta. La componen los midiclorianos con identificadores
	 * impares del al 29.
	 * 
	 */
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

	/**
	 * Método que crea la cerradura de la puerta tal y como se indica en la
	 * documentación.
	 * 
	 * @param izquierda
	 *            entero indicando la posición del vector.
	 * @param derecha
	 *            entero indicando la posición final.
	 */
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

	/**
	 * Método que configura la puerta para cerrarla. Se crea la cerradura y se
	 * pone el booleano que indica si está abierta a false.
	 *
	 */
	public void configurarPuerta() {
		crearCerradura(0, capacidad);
		abierta = false;
		configurada = true;
	}

	/**
	 * Método que comprueba si se cumplen las condiciones que se especifican en
	 * la documentación para que la puerta se abra.
	 * 
	 * @return booleano con valor true si se cumplen las condiciones indicando
	 *         que la puerta se puede abrir o false en caso contrario
	 */
	public boolean comprobarCondiciones() {
		if (cerradura.profundidad() < altura && cerradura.cuantosInternos() >= cerradura.cuantasHojas()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método toString de la clase Puerta para imprimir la puerta por pantalla.
	 *
	 */
	public String toString() {
		String cadena = "";
		cadena = cadena + cerradura;
		return cadena;
	}

	/**
	 * Método que obtiene un String con los identificadores de los midiclorianos
	 * que se han probado en la cerradura de la puerta.
	 * 
	 * @return
	 */
	public String obtenerProbados() {
		String cadena = "";
		cadena = cadena + probados;
		return cadena;
	}

	/**
	 * Método que prueba un midicloriano en la cerradura. Lo único que hace es
	 * probar si coincide, en caso afirmativo lo borra de la cerradura y lo
	 * introduce en la lista de probados. En caso negativo, solamente lo incluye
	 * en la lista de probados para que no se vuelva a probar.
	 * 
	 * @param midi
	 *            midicloriano que se quiere probar
	 */
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

	/**
	 * Método que devuelve el booleando indicando si la puerta está abierta o
	 * no.
	 * 
	 * @return booleano a true si la puerta está abierta o a false en caso
	 *         contrario.
	 */
	public boolean obtenerBoolPuerta() {
		return abierta;
	}

}
