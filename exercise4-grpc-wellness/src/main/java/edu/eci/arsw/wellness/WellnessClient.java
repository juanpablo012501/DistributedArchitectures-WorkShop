package edu.eci.arsw.wellness;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class WellnessClient {

    //methods
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        AppointmentServiceGrpc.AppointmentServiceBlockingStub stub = AppointmentServiceGrpc.newBlockingStub(channel);

        //prueba 1
        AppointmentRequest appRequest = AppointmentRequest.newBuilder()
                .setStudentId("E001")
                .setServiceType(ServiceType.MEDICINE)
                .setDate("2026-07-12")
                .build();

        AppointmentResponse appResponse = stub.requestAppointment(appRequest);
        System.out.println(appResponse.getResponse());

        //prueba 2
        AppointmentRequest app1 = AppointmentRequest.newBuilder()
                .setStudentId("E002")
                .setServiceType(ServiceType.DENTISTRY)
                .setDate("2026-08-01")
                .build();

        AppointmentRequest app2 = AppointmentRequest.newBuilder()
                .setStudentId("E001")
                .setServiceType(ServiceType.PSYCHOLOGY)
                .setDate("2026-07-17")
                .build();

        AppointmentRequest app3 = AppointmentRequest.newBuilder()
                .setStudentId("E003")
                .setServiceType(ServiceType.PSYCHOLOGY)
                .setDate("2026-09-28")
                .build();

        stub.requestAppointment(app1);
        stub.requestAppointment(app2);
        stub.requestAppointment(app3);

        CancelRequest cancelRequest = CancelRequest.newBuilder()
                .setAppointmentId("APT3")
                .build();

        CancelResponse cancelResponse = stub.cancelAppointment(cancelRequest);
        System.out.println(cancelResponse.getResponse());

        //prueba 3
        StudentRequest studentRequest = StudentRequest.newBuilder()
                .setStudentId("E001")
                .build();
        AppointmentList appoints = stub.getAppointments(studentRequest);
        System.out.println(appoints);
    }
}
