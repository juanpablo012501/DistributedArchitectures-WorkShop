package edu.eci.arsw.labs;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabServiceImpl extends UnicastRemoteObject implements LabService {

    //attributes
    private Map<String, Equipment> equipments = new HashMap<>();

    //methods
    public LabServiceImpl() throws RemoteException {
        super();
        equipments.put("E001", new Equipment("E001", "microscopio", "L301"));
        equipments.put("E002", new Equipment("E002", "osciloscopio", "L301"));
        equipments.put("E003", new Equipment("E003", "calorímetro", "L303"));
        equipments.put("E004", new Equipment("E004", "cromatógrafo", "L303"));
    }

    @Override
    public List<String> consultarEquipos() throws RemoteException {
        List<String> list = new ArrayList<>();
        for (Equipment equip : equipments.values()) {
            list.add(equip.toText());
        }
        return list;
    }

    @Override
    public String consultarEquipo(String codigo) throws RemoteException {
        Equipment equip = equipments.get(codigo);
        if (equip != null) return equip.toText();
        return "El equipo " + codigo + " no existe";
    }

    @Override
    public boolean reservarEquipo(String codigo) throws RemoteException {
        Equipment equip = equipments.get(codigo);
        if (equip != null &&  equip.isAvailable()) {
            equip.setAvailable(false);
            return true;
        }
        return false;
    }

    @Override
    public boolean liberarEquipo(String codigo) throws RemoteException {
        Equipment equip = equipments.get(codigo);
        if(equip != null && !equip.isAvailable()) {
            equip.setAvailable(true);
            return true;
        }
        return false;
    }
}
