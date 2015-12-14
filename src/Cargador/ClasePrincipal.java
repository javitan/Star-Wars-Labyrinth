package Cargador;

import java.io.BufferedWriter;
/**
 * Clase creada para ser usada en la utilidad cargador
 * contiene el main del cargador. Se crea una instancia de la clase Cargador
 * y se procesa el fichero de inicio, es decir, se leen todas las líneas y se van creando todas las instancias de la simulación
 * 
 * @version 4.0 -  15/10/2014 
 * @author Profesores DP
 */
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import Mapa.Galaxia;

public class ClasePrincipal {
	public static void main(String[] args) {
		/**
		 * instancia asociada al fichero de entrada inicio.txt
		 */
		Cargador cargador = new Cargador();
		try {
			/**
			 * Método que procesa línea a línea el fichero de entrada inicio.txt
			 */
			FicheroCarga.procesarFichero("inicio.txt", cargador);
			BufferedWriter bufferOut;
			String nomFichero = "registro.log";
			bufferOut = new BufferedWriter(new FileWriter(nomFichero));
			Galaxia.obtenerInstancia().ejecucion(nomFichero, bufferOut);
			bufferOut.close();
		} catch (FileNotFoundException valor) {
			System.err.println("Excepción capturada al procesar fichero: " + valor.getMessage());
		} catch (IOException valor) {
			System.err.println("Excepción capturada al procesar fichero: " + valor.getMessage());
		}
	}
}
