import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import Mapa.Estacion;
import Mapa.Puerta;
import Personajes.Personaje;

public class EstacionTest {
	static Estacion estacion;

	@BeforeClass
	public static void setUp() {
		estacion = new Estacion(5);
	}

//	@Test
//	public void testPonerPuerta() {
//		Puerta puerta = new Puerta();
//		assertTrue(estacion.obtenerPuerta() == null);
//		estacion.ponerPuerta(puerta);
//		assertFalse(estacion.obtenerColaPersonajes() == null);
//	}

	@Test
	public void testInsertarPersonaje() {
		Personaje personaje = new Personaje();
		assertTrue(estacion.obtenerColaPersonajes().isEmpty());
		estacion.insertarPersonaje(personaje);
		assertFalse(estacion.obtenerColaPersonajes().isEmpty());
		estacion.borrarPersonaje(personaje);
	}

	@Test
	public void testObtenerColaPersonajes() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testBorrarPersonaje(){
		fail("Not yet implemented");
	}

	@Test
	public void testObtenerPrimero() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertarMidicloriano() {
		fail("Not yet implemented");
	}

}
