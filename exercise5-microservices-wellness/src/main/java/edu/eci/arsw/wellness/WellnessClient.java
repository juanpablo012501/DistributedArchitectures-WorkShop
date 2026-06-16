package edu.eci.arsw.wellness;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class WellnessClient {

    //methods
    public static void main(String[] args) {
        ManagedChannel gymMicroService = ManagedChannelBuilder
                .forAddress("localhost", 50052)
                .usePlaintext()
                .build();

        ManagedChannel appointMicroService = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        GymServiceGrpc.GymServiceBlockingStub gymStub = GymServiceGrpc.newBlockingStub(gymMicroService);
        AppointmentServiceGrpc.AppointmentServiceBlockingStub appointmentStub = AppointmentServiceGrpc.newBlockingStub(appointMicroService);

        //appointments
        System.out.println("Microservicio de citas");
        //prueba 1
        AppointmentRequest appRequest = AppointmentRequest.newBuilder()
                .setStudentId("E001")
                .setServiceType(ServiceType.MEDICINE)
                .setDate("2026-07-12")
                .build();

        AppointmentResponse appResponse = appointmentStub.requestAppointment(appRequest);
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

        appointmentStub.requestAppointment(app1);
        appointmentStub.requestAppointment(app2);
        appointmentStub.requestAppointment(app3);

        CancelRequest cancelRequest = CancelRequest.newBuilder()
                .setAppointmentId("APT3")
                .build();

        CancelResponse cancelResponse = appointmentStub.cancelAppointment(cancelRequest);
        System.out.println(cancelResponse.getResponse());

        //prueba 3
        StudentRequest studentRequest = StudentRequest.newBuilder()
                .setStudentId("E001")
                .build();
        AppointmentList appoints = appointmentStub.getAppointments(studentRequest);
        System.out.println(appoints);

        //gym sessions
        System.out.println("Microservicio de sesiones del gym");
        //prueba 1
        ReserveRequest reserveRequest = ReserveRequest.newBuilder()
                .setStudentId("E001")
                .setTimeSlot("Lunes 8am-10am")
                .build();

        ReserveResponse reserveResponse = gymStub.reserveSession(reserveRequest);
        System.out.println(reserveResponse.getResponse());

        //prueba 2
        ReserveRequest res1 = ReserveRequest.newBuilder()
                .setStudentId("E002")
                .setTimeSlot("Miercoles 2pm-4pm")
                .build();

        ReserveRequest res2 = ReserveRequest.newBuilder()
                .setStudentId("E001")
                .setTimeSlot("Viernes 6pm-8pm")
                .build();

        ReserveRequest res3 = ReserveRequest.newBuilder()
                .setStudentId("E004")
                .setTimeSlot("Lunes 8am-10am")
                .build();

        ReserveRequest remov = ReserveRequest.newBuilder()
                .setStudentId("E006")
                .setTimeSlot("Jueves 1pm-3pm")
                .build();

        gymStub.reserveSession(res1);
        gymStub.reserveSession(res2);
        gymStub.reserveSession(res3);
        gymStub.reserveSession(remov);

        RemoveRequest removeRequest = RemoveRequest.newBuilder()
                .setSessionId("SES6")
                .setStudentId("E006")
                .build();

        RemoveResponse removeResponse = gymStub.removeSession(removeRequest);
        System.out.println(removeResponse.getResponse());

        //prueba 3
        SessionsRequest sessionsRequest = SessionsRequest.newBuilder()
                .setStudentId("E001")
                .build();

        SessionList sessionList = gymStub.consultSessions(sessionsRequest);
        System.out.println(sessionList);
    }
}
