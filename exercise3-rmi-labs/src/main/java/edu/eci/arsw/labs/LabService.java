package edu.eci.arsw.labs;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LabService extends Remote {

    //methods
    List<String> consultarEquipos() throws RemoteException;
    String consultarEquipo(String codigo) throws RemoteException;
    boolean reservarEquipo(String codigo) throws RemoteException;
    boolean liberarEquipo(String codigo) throws RemoteException;
}
