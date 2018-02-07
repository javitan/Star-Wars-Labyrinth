package personajestest;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import Mapa.Estacion;
import Mapa.Puerta;
import Personajes.Imperial;

public class ImperialTest {
	static Imperial imperial;
	static Estacion estacion;
	static Estacion estacion2;
	static Puerta puerta;

	@BeforeClass
	public static void setUp() {
		imperial = new Imperial("imperial", 'i', 1);
		puerta = Puerta.obtenerInstancia(); // altura = 0
		estacion = new Estacion(100);
		estacion2 = new Estacion(101);
	}

	@Test
	public void testAccionPuerta() {
		// cuando se encuentra en la sala de la puerta no se mueve
		estacion.ponerPuerta(puerta);
		estacion.insertarPersonaje(imperial);
		imperial.accionPuerta(estacion);
		assertFalse(imperial.movido());
	}

	@Test
	public void testAccionEstacion() {
		// si la sala es par (la 100 lo es), entonces soltará el último
		// midicloriano que lleve el Imperial (el 29)
		imperial.accionEstacion(estacion);
		assertEquals(estacion.mostrarMidiclorianos(), " 29");
	}

}
