import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import net.xqj.exist.ExistXQDataSource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XQueryService;

/**
 * Clase DAOexistDB permite acceder y gestionar recursos y colecciones de
 * ExistDB
 * 
 * @author  JGA
 * @version DAM-V01
 * 
 */
public class DAOexistDB implements Serializable {

	/************************************************************************************
	 * CONSTANTES
	 ************************************************************************************/
	
	private static final long serialVersionUID = 1L;
	private static final String DRIVER = "org.exist.xmldb.DatabaseImpl";
	

	/************************************************************************************
	 * Atributos de la clase
	 ************************************************************************************/

	private XQDataSource xqs;
	private XQConnection xconn;

	private String ip;
	private String port;
	private String user;
	private String pass;

	private String uri;

	/************************************************************************************
	 * fichero para log
	 ************************************************************************************/
	private FileWriter ficheroOUT;
	private String mensaje;

	/************************************************************************************
	 * constructores
	 ************************************************************************************/

	/**
	 * Constructor de la clase por defecto
	 */
	public DAOexistDB() {
	}

	/**
	 * Constructor de la clase con parametros
	 * 
	 * @param ip     Ip de la servidor DB     
	 * @param port   Puerto de acceso al servidor DB
	 * @param user   Usuario de acceso
	 * @param pass   Contraseña de acceso 
	 */
	public DAOexistDB(String ip, String port, String user, String pass) {
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.pass = pass;
	}

	/************************************************************************************
	 * getters y setters
	 ************************************************************************************/

	/**
	 * @return      Atributo xqs
	 */
	public XQDataSource getXqs() {
		return xqs;
	}

	/**
	 * @param xqs  Atributo xqs
	 *            
	 */
	public void setXqs(XQDataSource xqs) {
		this.xqs = xqs;
	}

	/**
	 * @return       Atributo xconn
	 */
	public XQConnection getXconn() {
		return xconn;
	}

	/**
	 * @param xconn  Atributo xconn
	 *            
	 */
	public void setXconn(XQConnection xconn) {
		this.xconn = xconn;
	}

	/**
	 * @return  	 Atributo ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip 	 Atributo ip
	 *            
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return 		 Atributo port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port   Atributo port
	 *            
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return 	 	 getter del atributo user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user 	 Atributo user
	 * 
	 */
	public void setAdmin(String user) {
		this.user = user;
	}

	/**
	 * @return 		- Atributo pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass 	- Atributo pass
	 *            
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/************************************************************************************
	 * metodos para atributos locales
	 ************************************************************************************/
	

	/**
	 * Metodo privado para conponer la URL de conexion
	 * 
	 * @param ip 		 Ip de conexion
	 * @param puerto 	 Puerto de la conexion
	 * @return 			 URL de conexion
	 * 
	 */
	private void setURI(String ip, String puerto) {
		this.uri = "xmldb:exist://" + ip + ":" + puerto + "/exist/xmlrpc";
	}

	/**
	 * Metodo privado para grabar archivo de log
	 * 
	 * @param mensaje 	- Mensaje a grabar en el archivo de log
	 * 
	 */
	private void writeLOG(String mensaje) {
		System.out.println(mensaje);
		try {
			ficheroOUT.write(mensaje + "\n");  // graba texto en fichero
		} catch (IOException e) {
			System.out.println("ERROR grabando LOG..." + e);
		} 
	}
	/************************************************************************************
	 * METODOS PUBLICOS
	 ************************************************************************************/

	/**
	 * openConexion - Metodo publico para abrir conexion
	 * 
	 * 
	 * @return 		  resultado de la conexion
	 * 
	 */
	public boolean openConexion() {
		
		setURI(this.ip, this.port); // creacion del atributo URI a patir del la ip y el puerto

		xqs = new ExistXQDataSource();

		try {
			ficheroOUT = new FileWriter("DAOexistDB.log"); 
			xqs.setProperty("serverName", this.ip);
			xqs.setProperty("port", this.port);
			xconn = xqs.getConnection();

			mensaje = "Conexión abierta en " + this.ip + ":" + this.port;
			writeLOG(mensaje);
			return true;

		} catch (XQException e) {
			mensaje = "ERROR abriendo conexion..." + e;
			writeLOG(mensaje);
			
			return false;
		} catch (IOException e) {
			System.out.println("ERROR instanciando LOG" + e);
			return false;

			
		}
	}

	/**
	 * closeConexion  Metodo publico para cerrar la conexion
	 * 
	 * @return 	 	 resultado de la desconexion
	 * 
	 */
	public boolean closeConexion() {
		try {
			xconn.close();
			
			mensaje = "Conexión cerrada... " + this.uri;
			writeLOG(mensaje);
			ficheroOUT.close();
			return true;
			
		} catch (XQException e) {
			mensaje = "ERROR cerrando conexión..." + e;
			writeLOG(mensaje);
			
			try {
				ficheroOUT.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
			
		} catch (IOException e) {
			System.out.println("ERROR cerrando LOG" + e);
			return false;
		}
	}

	/**
	 * createColeccion - Metodo publico para cerrar una coleccion en la colleccion padre /db
	 * 
	 * 
	 * @param coleccion         Nombe de la coleccion a crear
	 * @return 				    resultado de la de la creacion colleccion
	 * 
	 */
	public boolean createColeccion(String coleccion) {

		try {
			// initialize database driver
			Class<?> cl = Class.forName(DRIVER);
			Database database = (Database) cl.newInstance();
			database.setProperty("create-database", "true");

			// crear el manegador
			DatabaseManager.registerDatabase(database);
			
			// obtener direccion padre
			Collection col = DatabaseManager.getCollection(this.uri + "/db",this.user, this.pass);

			// verificamos existencia de conexion padre /db
			if (col == null) {
				mensaje = "no existe la coleccon padre /db";
				writeLOG(mensaje);
				return false;
			}

			// Creando la coleccion
			CollectionManagementService colService = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
			colService.createCollection(coleccion);
			mensaje ="Creada la coleccion:" + coleccion;
			writeLOG(mensaje);

			return true;

		} catch (ClassNotFoundException e) {
			mensaje="Error al crear colleccion " + e;
			writeLOG(mensaje);
			return false;
		} catch (InstantiationException e) {
			mensaje="Error al crear colleccion " + e;
			writeLOG(mensaje);
			return false;
		} catch (IllegalAccessException e) {
			mensaje="Error al crear colleccion " + e;
			writeLOG(mensaje);
			return false;
		} catch (XMLDBException e) {
			mensaje ="Error al crear colleccion " + e;
			writeLOG(mensaje);
			return false;
		}
	}

	/**
	 * deleteColeccion - Metodo publico para borrar una coleccion en la colleccion padre /db
	 * 
	 * @param coleccion    nombre de la coleccion a borrar
	 * @return 			   resultado del borrado de la coleccion
	 * 
	 */
	public boolean deleteColeccion(String coleccion) {

		try {
			// initialize database driver
			Class<?> cl = Class.forName(DRIVER);
			Database database = (Database) cl.newInstance();
			database.setProperty("create-database", "true");

			// crear el manegador
			DatabaseManager.registerDatabase(database);
			
			// obtener direccion padre
			Collection col = DatabaseManager.getCollection(this.uri + "/db",this.user, this.pass);

			// verificamos existencia de conexion padre /db
			if (col == null) {
				mensaje="no existe la coleccion padre /db";
				writeLOG(mensaje);
				return false;
			}

			// Creando la coleccion
			CollectionManagementService colService = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
			colService.removeCollection(coleccion);

			mensaje="Borrada la coleccion:" + coleccion;
			writeLOG(mensaje);
			
			return true;

		} catch (ClassNotFoundException e) {
			mensaje="Error al borrar colleccion " + e;
			writeLOG(mensaje);
			return false;
		} catch (InstantiationException e) {
			mensaje="Error al borrar colleccion " + e;
			writeLOG(mensaje);
			return false;
		} catch (IllegalAccessException e) {
			mensaje="Error al borrar colleccion " + e;
			writeLOG(mensaje);
			return false;
		} catch (XMLDBException e) {
			mensaje="Error al borrar colleccion " + e;
			writeLOG(mensaje);
			return false;
		}
	}

	/**
	 * addRecurso Metodo publico para añadir un recurso a una coleccion
	 * 
	 * @param coleccion   collecion a la que añadiremos el recurso
	 * @param urlRecurso  nombre del recurso a añadir a la colleccion
	 * @return 			  resultado de la addicion del recurso
	 *  
	 */

	public boolean addRecurso(String coleccion, String urlRecurso) {

		try {
			// initialize database driver
			Class<?> cl = Class.forName(DRIVER);
			Database database = (Database) cl.newInstance();
			database.setProperty("create-database", "true");

			// crear el manegador
			DatabaseManager.registerDatabase(database);

			// Crear fichero
			File fichero = new File(urlRecurso);

			// obtenemos la colleccion a añadir el recurso
			Collection col = DatabaseManager.getCollection(this.uri + "/db/" + coleccion,this.user, this.pass);

			// verificalos la existencia de la coleccion donde añadir el recurso
			if (col == null) {
				mensaje="no existe la coleccion indicada al crear recuros";
				writeLOG(mensaje);
				return false;
			}

			// Añade el recuro
			Resource recurso = col.createResource(urlRecurso, "XMLResource");
			recurso.setContent(fichero);
			col.storeResource(recurso);
			mensaje="Añadido correctamente el recurso:" + urlRecurso;
			writeLOG(mensaje);

			return true;

		} catch (ClassNotFoundException e) {
			mensaje="Error al crear recurso " + e;
			writeLOG(mensaje);
			return false;
		} catch (InstantiationException e) {
			mensaje="Error al crear recurso " + e;
			writeLOG(mensaje);
			return false;
		} catch (IllegalAccessException e) {
			mensaje="Error al crear recurso " + e;
			writeLOG(mensaje);
			return false;
		} catch (XMLDBException e) {
			mensaje="Error al crear recurso " + e;
			writeLOG(mensaje);
			return false;
		}

	}

	
	/**
	 * deleteRecurso Metodo publico para borrar un recurso de una coleccion
	 * 
	 * @param coleccion      nombre de la colleccion de la que borraremos el recurso
	 * @param urlRecurso     nombre del recurso a eliminar
	 * @return 				 resultado de la eliminacion del recurso
	 *  
	 */

	public boolean deleteRecurso(String coleccion, String urlRecurso) {

		try {
			// initialize database driver
			Class<?> cl = Class.forName(DRIVER);
			Database database = (Database) cl.newInstance();
			database.setProperty("create-database", "true");

			// crear el manegador
			DatabaseManager.registerDatabase(database);

			
			// obtenemos la colleccion a del recurso a borrar
			Collection col = DatabaseManager.getCollection(this.uri + "/db/" + coleccion,this.user, this.pass);

			// verificalos la existencia de la coleccion donde añadir el recurso
			if (col == null) {
				mensaje="no existe la coleccion indicada al borrar recurso";
				writeLOG(mensaje);
				return false;
			}

			// borrado del recurso
			Resource recurso = col.getResource(urlRecurso);
			col.removeResource(recurso);
			
			mensaje="Borrado correctamente el recurso:" + urlRecurso;
			writeLOG(mensaje);
			return true;

		} catch (ClassNotFoundException e) {
			mensaje="Error al borrar recurso " + e;
			writeLOG(mensaje);
			return false;
		} catch (InstantiationException e) {
			mensaje="Error al borrar recurso " + e;
			writeLOG(mensaje);
			return false;
		} catch (IllegalAccessException e) {
			mensaje="Error al borrar recurso " + e;
			writeLOG(mensaje);
			return false;
		} catch (XMLDBException e) {
			mensaje="Error al borrar recurso " + e;
			writeLOG(mensaje);
			return false;
		}

	}
	
	/**
	 * consultaXPATH Metodo publico para ejecutar una sentencia XPATH sobre documento o coleccion
	 *      
	 *      
	 * @param coleccion     	 Nombre de la colleccion donde se realiza la consulta
	 * @param sentenciaXPATH     Instruccion XPATH a ejecutar
	 * @return 					 resultado sentencia XPATH formateada a cadena
	 *  
	 */	
	public String consultaXPATH(String coleccion, String sentenciaXPATH) {
		
		String resultado = "";
		
		// colleccion a del recurso a consultar
		Collection col = null;
		
        
		try {
			// initialize database driver
			
			Class<?> cl = Class.forName(DRIVER);
			Database database;
	
			database = (Database) cl.newInstance();
			database.setProperty("create-database", "true");

			// crear el manegador
			DatabaseManager.registerDatabase(database);
			
			// coleccion a consultar
			col = DatabaseManager.getCollection(this.uri + "/db/" + coleccion,this.user, this.pass); 
			if (col == null) {
				mensaje= "no existe la coleccion indicada en la consulta XPATH";
				writeLOG(mensaje);
				return (mensaje);
			}
			
			XPathQueryService xpqs = (XPathQueryService) col.getService("XPathQueryService","1.0");
			ResourceSet rs = xpqs.query (sentenciaXPATH);
		
			// Comprobamos y mostramos resultado ejecucion consulta
	        
	        ResourceIterator ri = rs.getIterator();
	        if (!ri.hasMoreResources()) {
	          resultado =  ("No se han encontrado resultados");
	          writeLOG(resultado);
	        } else {
	        	Resource res1;
	        	while (ri.hasMoreResources()) {
	        		res1 = ri.nextResource();
	        		resultado = resultado + (String)res1.getContent() + "\n";
	        	}
	        	mensaje = "Ejecutada sentencia XPATH:" + sentenciaXPATH ;
				writeLOG(mensaje);
	        }
			
		} catch (XMLDBException e) {
			resultado = "Error inesperado en metodo consultaXPATH" + e;
			writeLOG(resultado);
		} catch (ClassNotFoundException e) {
			resultado = "Error inesperado en metodo consultaXPATH" + e;
			writeLOG(resultado);
		} catch (InstantiationException e) {
			resultado = "Error inesperado en metodo consultaXPATH" + e;
			writeLOG(resultado);
		} catch (IllegalAccessException e) {
			resultado = "Error inesperado en metodo consultaXPATH" + e;
			writeLOG(resultado);
		}	

		return resultado;
	
	}

	/**
	 * consultaXQUERY Metodo publico para ejecutar una sentencia XQUERY sobre documento o coleccion
	 * 
	 * @param coleccion			 	 Coleccion sobre la que se realiza la consulta (String)
	 * @param sentenciaXQUERY        Instruccion XQUERY a ejecutar 
	 * @return 						 Resultado sentencia XQUERY formateada a cadena
	 *  
	 */	
	public String consultaXQUERY(String coleccion, String sentenciaXQUERY) {
		String resultado = "";
		
		// colleccion a del recurso a consultar
		Collection col = null;
		
        
		try {
			// initialize database driver
			
			Class<?> cl = Class.forName(DRIVER);
			Database database;
	
			database = (Database) cl.newInstance();
			database.setProperty("create-database", "true");

			// crear el manegador
			DatabaseManager.registerDatabase(database);
			
			// coleccion a consultar
			col = DatabaseManager.getCollection(this.uri + "/db/" + coleccion,this.user, this.pass); 
			
			if (col == null) {
				mensaje= "no existe la coleccion indicada en la consulta XQUERY";
				writeLOG(mensaje);
				return (mensaje);
			}
			
			XQueryService xpqs = (XQueryService) col.getService("XQueryService","1.0");
			ResourceSet rs = xpqs.query (sentenciaXQUERY);
		
			// Comprobamos y mostramos resultado ejecucion consulta
	        
	        ResourceIterator ri = rs.getIterator();
	        if (!ri.hasMoreResources()) {
	          resultado =  ("No se han encontrado resultados");
	          writeLOG(resultado);
	        } else {
	        	Resource res1;
	        	while (ri.hasMoreResources()) {
	        		res1 = ri.nextResource();
	        		resultado = resultado + (String)res1.getContent() + "\n";
	        	}
	        	mensaje = "Ejecutada sentencia XQUERY: " + sentenciaXQUERY;
				writeLOG(mensaje);
	        }
			
		} catch (XMLDBException e) {
			resultado = "Error inesperado en metodo consultaXQUERY" + e;
			writeLOG(resultado);
		} catch (ClassNotFoundException e) {
			resultado = "Error inesperado en metodo consultaXQUERY" + e;
			writeLOG(resultado);
		} catch (InstantiationException e) {
			resultado = "Error inesperado en metodo consultaXQUERY" + e;
			writeLOG(resultado);
		} catch (IllegalAccessException e) {
			resultado = "Error inesperado en metodo consultaXQUERY" + e;
			writeLOG(resultado);
		}	
		
		return resultado;	
	
	
	}

	
	
}
