package edu.eci.arsw.labs;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LabRmiServer {

    //methods
    public static void main(String[] args) {
        try {
            LabService service = new LabServiceImpl();
            Registry registry = LocateRegistry.createRegistry(23000);
            registry.rebind("Equipment service", service);
            System.out.println("Equipment service published on port 23000");
            Thread.sleep(Long.MAX_VALUE);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
