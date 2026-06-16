package edu.eci.arsw.rooms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class RoomClient {

    //methods
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Ingrese el commando seguido de una coma y el id del salón (COMMANDO,ID): ");
        String request = input.nextLine();

        try {
            Socket socket = new Socket("127.0.0.1", 35000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(request);
            String response = in.readLine();
            System.out.println("Respuesta del servidor: " +  response);

            socket.close();
            in.close();
            out.close();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
