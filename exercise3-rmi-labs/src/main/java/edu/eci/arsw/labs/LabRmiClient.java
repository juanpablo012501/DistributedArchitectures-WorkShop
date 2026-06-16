package edu.eci.arsw.labs;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class LabRmiClient {

    //methods
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 23000);
            LabService service = (LabService) registry.lookup("Equipment service");
            System.out.println("La lista de equipos es: \n");
            List<String> list = service.consultarEquipos();
            for (String s : list) {
                System.out.println(s);
            }
            System.out.println("El quipo de id E003:");
            System.out.println(service.consultarEquipo("E003"));
            System.out.println("Se reservaron los equipos E001 y E004");
            System.out.println("Respuesta al reservar el equipo E001: " + service.reservarEquipo("E001"));
            System.out.println("Respuesta al reservar el equipo E004: " + service.reservarEquipo("E004"));
            System.out.println("Al tratar de liberar el equipo E003 la respuesta es: " + service.liberarEquipo("E003"));
            System.out.println("Se libera el equipo E001 y la respuesta fue: " + service.liberarEquipo("E001"));
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
