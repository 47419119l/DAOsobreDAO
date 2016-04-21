import javax.xml.bind.JAXBException;
import java.util.Scanner;

/**
 * Created by 47419119l on 20/04/16.
 */
public class Controller {
    public static void main(String[] args) throws JAXBException {
        DAOexistDB dao = new DAOexistDB("localhost", "8080", "admin", "sandrass501lol");
        DaoSobreDao  dsd= new DaoSobreDao();
        int menu =6;
        dao.openConexion();
        dao.createColeccion("dadesempresa");
        dsd.configuracioInicial();

        Scanner teclat = new Scanner (System.in);
        System.out.println("1.Donar de alta empleats");
        System.out.println("2.Donar de alta clients");
        System.out.println("3.Eliminar empleats");
        System.out.println("4.Eliminar clients");
        System.out.println("5.Consultat empleats");
        System.out.println("6.Consultar factures d'un client");
        switch (menu){
            case 1:
                dsd.afeguirEmpleat();
                dao.addRecurso("dadesempresa", "dades.xml");
                break;
            case 2:
                dsd.afeguirClients();
                dao.addRecurso("dadesempresa", "dades.xml");
                break;
            case 3:
                if( dsd.eliminarEmpleat()){
                    System.out.println("Eliminat el empleat");
                    dao.addRecurso("dadesempresa", "dades.xml");
                }else{
                    System.out.println("No existeix cap empleat amb aquest id");
                }
                break;
            case 4:
                if( dsd.eliminarClients()){
                    System.out.println("Eliminat el client");
                    dao.addRecurso("dadesempresa", "dades.xml");
                }else{
                    System.out.println("No existeix cap client amb aquest dni");
                }
                break;
            case 5:
                dsd.consultarEmpleats();
                break;
            case 6:
                dsd.consultarFacturesDeUnClient();
                break;
            case 7:
                System.out.println("Fi del programa");
                break;

        }

        dao.closeConexion();


    }
}
