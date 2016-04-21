import generated.ClientType;
import generated.DadesType;
import generated.EmpleatType;
import javax.xml.bind.*;
import java.io.File;
import java.util.Scanner;

/**
 * Created by 47419119l on 20/04/16.
 */
public class DaoSobreDao {
    private static DadesType database;
    private static JAXBContext context;
    private static File databaseFile = new File("dades.xml");

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
                ClientType c = database.getClients().getClient().get(i);
                database.getClients().getClient().remove(c);
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
                EmpleatType empleatType = database.getEmpleats().getEmpleat().get(i);
                database.getEmpleats().getEmpleat().remove(empleatType);
                eliminat=true;
                break;
            }
        }
        return eliminat;
    }

    /**
     * consultarEmpleats Metode per consultar els emplats.
     */
    public static void consultarEmpleats(){
        //diferents camps (edat, sou, anys treballats).
        Scanner teclat = new Scanner(System.in);
        System.out.println("Per quin camp vols consultar el empleats ?");
        System.out.println("1. sou");
        System.out.println("2. anys treballats");
        System.out.println("3. id");

        String menu = teclat.nextLine();
        switch (menu){
            case "1":
                System.out.println(" Diguem el sou ");
                String sou = teclat.nextLine();
                for(int i =0 ; i< database.getEmpleats().getEmpleat().size(); i++ ){
                    if(database.getEmpleats().getEmpleat().get(i).getSou().equals(sou)){
                        System.out.println(" - "+database.getEmpleats().getEmpleat().get(i).getId()+" nom :"+ database.getEmpleats().getEmpleat().get(i).getNom() +" "+database.getEmpleats().getEmpleat().get(i).getCognom()+" anys treballats: "+database.getEmpleats().getEmpleat().get(i).getAnysTreballats());
                        break;
                    }
                }
                break;
            case "2":
                System.out.println(" Diguem els anys treballats ");
                String anystreballats = teclat.nextLine();
                for(int i =0 ; i< database.getEmpleats().getEmpleat().size(); i++ ){
                    if(database.getEmpleats().getEmpleat().get(i).getAnysTreballats().equals(anystreballats)){
                        System.out.println(" - "+database.getEmpleats().getEmpleat().get(i).getId()+" nom :"+ database.getEmpleats().getEmpleat().get(i).getNom() +" "+database.getEmpleats().getEmpleat().get(i).getCognom()+" Sou: "+database.getEmpleats().getEmpleat().get(i).getSou());
                        break;
                    }
                }
                break;
            case "3":
                System.out.println(" Diguem el id ");
                String id = teclat.nextLine();
                for(int i =0 ; i< database.getEmpleats().getEmpleat().size(); i++ ){
                    if(database.getEmpleats().getEmpleat().get(i).getId().equals(id)){
                        System.out.println(" -  nom :"+ database.getEmpleats().getEmpleat().get(i).getNom() +" "
                                +database.getEmpleats().getEmpleat().get(i).getCognom()+" Sou: "+database.getEmpleats().getEmpleat().get(i).getSou()
                                +" Any treballats :"+database.getEmpleats().getEmpleat().get(i).getAnysTreballats());
                        break;
                    }
                }
                break;

            default:
                System.out.println("Aquesta opció no existeix");
                break;
        }
    }

    /**
     * Metode per consultar factures d'un client.
     */
    public static void consultarFacturesDeUnClient(){
        Scanner teclat = new Scanner(System.in);
        System.out.println("Digues el dni del client que vols veure la seva factura ?");
        String dni = teclat.nextLine();
        System.out.println("El client amb dni "+dni+" té les factures : ");
        for(int i =0 ; i< database.getFactures().getFactura().size();i++){
            if(database.getFactures().getFactura().get(i).getDniClient().equals(dni)){
                System.out.println(" - Producte : "+database.getFactures().getFactura().get(i).getIdProducte()
                        + " Preu unitat :"+database.getFactures().getFactura().get(i).getPreuUnitat()
                        +" Preu total"+database.getFactures().getFactura().get(i).getPreuTotal()
                        +" iva"+database.getFactures().getFactura().get(i).getIva());
            }
        }

    }

}
