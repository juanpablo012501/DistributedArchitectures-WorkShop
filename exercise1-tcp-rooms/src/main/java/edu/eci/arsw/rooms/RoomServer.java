package edu.eci.arsw.rooms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RoomServer {

    //methods
    public static void main(String[] args) {
        RoomRepository repository = new RoomRepository();
        try {
            ServerSocket serverSocket = new ServerSocket(35000);
            System.out.println("Room server listening on port 35000...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String request = in.readLine();
                String response = processRequest(request, repository);
                out.println(response);

                in.close();
                out.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processRequest(String request, RoomRepository repository) {
        String[] clientMessageSplitted = request.split(",");
        String command = clientMessageSplitted[0];
        String roomId = clientMessageSplitted[1];

        switch (command) {
            case "CONSULTAR_SALON":
                Room room = repository.findRoomById(roomId);
                if (room == null) {return "ERROR_SALON_NO_EXISTE";}
                return room.toText();

            case "RESERVAR_SALON":
                RoomServerResponses answer1 = repository.bookRoomById(roomId);
                if (answer1 == RoomServerResponses.ROOM_DOESNT_EXIST) {return "ERROR_SALON_NO_EXISTE";}
                if (answer1 == RoomServerResponses.SUCCESSFUL_BOOKING) {return "SALON_RESERVADO";}
                if (answer1 == RoomServerResponses.UNAVAILABLE_ROOM) {return "ERROR_SALON_YA_RESERVADO";}
                return "ERROR_OPERACION_INVALIDA";

            case "LIBERAR_SALON":
                RoomServerResponses answer2 = repository.releaseRoomById(roomId);
                if (answer2 == RoomServerResponses.ROOM_DOESNT_EXIST) {return "ERROR_SALON_NO_EXISTE";}
                if (answer2 == RoomServerResponses.SUCCESSFUL_RELEASE) {return "LIBERACION_EXITOSA";}
                if (answer2 == RoomServerResponses.UNAVAILABLE_ROOM) {return "ERROR_SALON_NO_ESTABA_RESERVADO";}
                return "ERROR_OPERACION_INVALIDA";

            default:
                return "ERROR_OPERACION_INVALIDA";
        }
    }
}
