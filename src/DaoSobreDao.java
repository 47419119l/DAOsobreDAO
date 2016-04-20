import generated.ClientType;
import generated.DadesType;
import generated.EmpleatType;
import generated.ProducteType;

import javax.xml.bind.*;
import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 * Created by 47419119l on 20/04/16.
 */
public class DaoSobreDao {
    private static DadesType database;
    private static JAXBContext context;
    private static File databaseFile = new File("dades.xml");
/*

Consultar empleats pels
diferents camps (edat, sou, anys treballats).
Buscar factures en una franja concreta de
temps. Consultar totes les factures d'un client, ...
 */

    /**
     * Configuració jaxB
     */
    public static void configuracioInicial(){

        try {
            context = JAXBContext.newInstance(DadesType.class);
            Unmarshaller UMS = context.createUnmarshaller();
            database  = (DadesType) UMS.unmarshal(databaseFile);
        } catch (JAXBException e) {
        }

    }

    /**
     * afeguirEmpleat Metode per afeguir clients a xml
     * @throws JAXBException
     */
    public static void afeguirEmpleat() throws JAXBException {

        Scanner teclat = new Scanner(System.in);

        System.out.println("Digues el id del empleat : ");
        String id= teclat.nextLine();
        System.out.println("Digues el nom del empleat : ");
        String nom = teclat.nextLine();
        System.out.println("Digues el nom del cognom : ");
        String cognom = teclat.nextLine();
        System.out.println("Digues el seu sou : ");
        String sou = teclat.nextLine();
        System.out.println("Digue els anus treballats : ");
        String anys_treballats = teclat.nextLine();


        EmpleatType empleatType = new EmpleatType();

        empleatType.setNom(nom);
        empleatType.setId(id);
        empleatType.setCognom(cognom);
        empleatType.setSou(sou);
        empleatType.setAnysTreballats(anys_treballats);
        database.getEmpleats().getEmpleat().add(empleatType);

        Marshaller MS = context.createMarshaller();
        MS.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        MS.marshal(database, databaseFile);

    }

    /**
     * afeguirClient Metode per afeguir clients a fitxer xml
     * @throws JAXBException
     */
    public static void afeguirClients() throws JAXBException {

        Scanner teclat = new Scanner(System.in);

        System.out.println("Digues el dni del client  : ");
        String dni= teclat.nextLine();
        System.out.println("Digues el nom del client : ");
        String nom = teclat.nextLine();
        System.out.println("Digues el cognom del client : ");
        String cognom = teclat.nextLine();


        ClientType clientType = new ClientType();
        clientType.setDni(dni);
        clientType.setNom(nom);
        clientType.setCognom(cognom);

        database.getClients().getClient().add(clientType);

        Marshaller MS = context.createMarshaller();
        MS.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        MS.marshal(database, databaseFile);
    }

    /***
     * eliminarClients Metode per eliminar clients.
     * @return boolean true si ha estat eliminat algun dels clients false en cas contrari
     */
    public static boolean eliminarClients(){
        boolean eliminat = false;
        Scanner teclat = new Scanner(System.in);
        System.out.println("Digues el dni del client  : ");
        String dni= teclat.nextLine();

       for(int i =0 ; i< database.getClients().getClient().size(); i++ ){
            if(database.getClients().getClient().get(i).getDni().equals(dni)){
                database.getClients().getClient().remove(i);
                eliminat=true;
                break;
            }
        }
        return eliminat;
    }

    /**
     *  eliminarEmpleat Metode per eliminar empleats
     * @return boolean true si ha estat eliminat algun dels empleats false en cas contrari
     */
    public static boolean eliminarEmpleat(){
        boolean eliminat = false;
        Scanner teclat = new Scanner(System.in);
        System.out.println("Digues el id del empleat  : ");
        String id= teclat.nextLine();
        for(int i =0 ; i< database.getEmpleats().getEmpleat().size(); i++ ){
            if(database.getEmpleats().getEmpleat().get(i).getId().equals(id)){
                database.getEmpleats().getEmpleat().remove(i);
                eliminat=true;
                break;
            }
        }
        return eliminat;
    }


}
