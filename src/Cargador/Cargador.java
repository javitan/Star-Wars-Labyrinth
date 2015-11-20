package Cargador;

import java.util.List;

/**
 * Clase creada para ser usada en la utilidad cargador
 * contiene el main del cargador. Se crea una instancia de la clase Galaxia, una instancia de la clase Cargador
 * y se procesa el fichero de inicio, es decir, se leen todas las líneas y se van creando todas las instancias de la simulación
 * 
 * @version 4.0 -  23/10/2015 
 * @author Profesores DP
 */
public class Cargador {
	/**  
	número de elementos distintos que tendrá la simulación - Galaxia, FamiliaReal, Jedi, Contrabandista, Imperial
	*/
	static final int NUMELTOSCONF  = 5;
	/**  
	atributo para almacenar el mapeo de los distintos elementos
	*/
	static private DatoMapeo [] mapeo;
	
	/**
	 *  constructor por defecto
	 */
	Cargador() {
		mapeo = new DatoMapeo[NUMELTOSCONF];
		mapeo[0]= new DatoMapeo("GALAXIA", 5);
		mapeo[1]= new DatoMapeo("FAMILIAREAL", 4);
		mapeo[2]= new DatoMapeo("JEDI", 4);
		mapeo[3]= new DatoMapeo("CONTRABANDISTA", 4);
		mapeo[4]= new DatoMapeo("IMPERIAL", 4);
	}
	
	/**
	 *  busca en mapeo el elemento leído del fichero inicio.txt y devuelve la posición en la que está 
	 *  @param elto elemento a buscar en el array
	 *  @return res posición en mapeo de dicho elemento
	 */
	private int queElemento(String elto)  {
	    int res=-1;
	    boolean enc=false;

	    for (int i=0; (i < NUMELTOSCONF && !enc); i++)  {
	        if (mapeo[i].getNombre().equals(elto)) {
	            res=i;
	            enc=true;
	        }
	    }
	    return res;
	}
	
	/**
	 *  método que crea las distintas instancias de la simulación 
	 *  @param elto nombre de la instancia que se pretende crear
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo de la instancia
	 */
	public void crear(String elto, int numCampos, List<String> vCampos)	{
	    //Si existe elemento y el número de campos es correcto, procesarlo... si no, error
	    int numElto = queElemento(elto);

	    //Comprobación de datos básicos correctos
	    if ((numElto!=-1) && (mapeo[numElto].getCampos() == numCampos))   {
	       //procesar
	       switch(queElemento(elto)) {
	        case 0:	   
	            crearGalaxia(numCampos,vCampos);
	            break;
	        case 1:
	            crearFamiliaReal(numCampos,vCampos);
	            break;
	        case 2:
	            crearJedi(numCampos,vCampos);
	            break;
	        case 3:
	            crearContrabandista(numCampos,vCampos);
	            break;
	        case 4:
	            crearImperial(numCampos,vCampos);
	            break;
	     	}
	    }
	    else
	        System.out.println("ERROR Cargador::crear: Datos de configuración incorrectos... " + elto + "," + numCampos+"\n");
	}

	/**
	 *  método que crea una instancia de la clase Galaxia
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearGalaxia(int numCampos, List<String> vCampos){
	    System.out.println("Creada galaxia: " + vCampos.get(1) + "\n");
	    //inicializar galaxia
	}
	/**
	 *  método que crea una instancia de la clase FamiliaReal
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearFamiliaReal(int numCampos, List<String> vCampos){
	    System.out.println("Creado FamiliaReal: " + vCampos.get(1) + "\n");
	    //Registrar FamiliaReal en la galaxia
	}
	/**
	 *  método que crea una instancia de la clase Jedi
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearJedi(int numCampos, List<String> vCampos){
	    System.out.println("Creado Jedi: " + vCampos.get(1) + "\n");
	    //Registrar Jedi en la galaxia
	}	
	/**
	 *  método que crea una instancia de la clase Contrabandista
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearContrabandista(int numCampos, List<String> vCampos){
	    System.out.println("Creado Contrabandista: " + vCampos.get(1) + "\n");
	    //Registrar Contrabandista en la galaxia
	}	
	/**
	 *  método que crea una instancia de la clase Asimo
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo
	 */
	private void crearImperial(int numCampos, List<String> vCampos){
	    System.out.println("Creado Imperial: " + vCampos.get(1) + "\n");
	    //Registrar Imperial en la galaxia
	}	
}
