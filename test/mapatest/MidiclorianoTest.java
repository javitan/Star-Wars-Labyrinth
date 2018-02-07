package mapatest;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import Mapa.Midicloriano;

public class MidiclorianoTest {
	static Midicloriano midi;
	static Midicloriano midi2;

	@BeforeClass
	public static void setUp() {
		midi = new Midicloriano(8);
		midi2 = new Midicloriano(23);
	}

	@Test
	public void testObtenerIdMidi() {
		assertEquals(8, midi.obtenerIdMidi().intValue());
		assertNotEquals(25, midi2.obtenerIdMidi().intValue());
	}

	@Test
	public void testCompareTo() {
		assertEquals(0, midi.compareTo(midi));
		assertNotEquals(0, midi.compareTo(midi2));
	}

}
