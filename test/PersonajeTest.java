import static org.junit.Assert.*;

import java.util.Queue;

import org.junit.BeforeClass;
import org.junit.Test;

import Personajes.Dir;
import Personajes.Personaje;

public class PersonajeTest {
	static Personaje personaje;

	@BeforeClass
	public static void setUp() {
		personaje = new Personaje();
	}

	@Test
	public void testAccionPuerta() {
		fail("Not yet implemented");
	}

	@Test
	public void testAccionEstacion() {
		fail("Not yet implemented");
	}

	@Test
	public void testMovido() {
		personaje.personajeMovido();
		assertTrue(personaje.movido());
	}

	@Test
	public void testObtenerMovimiento() {
		personaje.ponerDireccion(Dir.S);
		assertEquals(Dir.S, personaje.obtenerMovimiento());
		assertTrue(personaje.obtenerColaMovimientos().isEmpty());
	}

	@Test
	public void testPersonajeMovido() {
		personaje.personajeMovido();
		assertTrue(personaje.movido());
	}

	@Test
	public void testPersonajeNoMovido() {
		personaje.personajeNoMovido();
		assertFalse(personaje.movido());
	}

	@Test
	public void testObtenerColaMovimientos() {
		Queue<Dir> cola = null;
		cola = personaje.obtenerColaMovimientos();
		assertTrue(cola != null);
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testPonerDireccion() {
		Personaje personaje1 = new Personaje();
		assertTrue(personaje1.obtenerColaMovimientos().isEmpty());
		personaje1.ponerDireccion(Dir.N);
		assertTrue(!personaje1.obtenerColaMovimientos().isEmpty());
	}

}
