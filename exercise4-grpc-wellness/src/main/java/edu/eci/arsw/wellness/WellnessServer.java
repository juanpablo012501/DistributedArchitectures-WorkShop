package edu.eci.arsw.wellness;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class WellnessServer {

    //methods
    public static void main(String[] args) {
        Server server = ServerBuilder.forPort(50051)
                .addService(new AppointmentServiceImp())
                .build();

        try {
            server.start();
            System.out.println("Servidor gRPC de citas iniciado en el puerto 50051");
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
