package edu.eci.arsw.wellness;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GymServer {

    //methods
    public static void main(String[] args) {
        Server server = ServerBuilder.forPort(50052)
                .addService(new GymServiceImp())
                .build();

        try {
            server.start();
            System.out.println("Servidor gRPC de sesiones del gym iniciado en el puerto 50052");
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
