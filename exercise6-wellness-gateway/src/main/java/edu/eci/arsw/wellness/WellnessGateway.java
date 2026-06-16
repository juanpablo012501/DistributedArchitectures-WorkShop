package edu.eci.arsw.wellness;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class WellnessGateway {

    private static AppointmentServiceGrpc.AppointmentServiceBlockingStub appointmentStub;
    private static GymServiceGrpc.GymServiceBlockingStub gymStub;

    public static void main(String[] args) {
        ManagedChannel gymChannel = ManagedChannelBuilder
                .forAddress("localhost", 50052)
                .usePlaintext()
                .build();

        ManagedChannel appointChannel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        appointmentStub = AppointmentServiceGrpc.newBlockingStub(appointChannel);
        gymStub = GymServiceGrpc.newBlockingStub(gymChannel);

        // pruebas aquí
        System.out.println(requestAppointment("E001", ServiceType.MEDICINE, "2026-07-12"));
        System.out.println(reserveGymSession("E001", "Lunes 8am-10am"));
        System.out.println(getStudentWellnessSummary("E001"));
    }

    public static String requestAppointment(String studentId, ServiceType serviceType, String date) {
        AppointmentRequest request = AppointmentRequest.newBuilder()
                .setStudentId(studentId)
                .setServiceType(serviceType)
                .setDate(date)
                .build();

        return appointmentStub.requestAppointment(request).getResponse();
    }

    public static String getStudentWellnessSummary(String studentId) {
        StudentRequest appoints = StudentRequest.newBuilder()
                .setStudentId(studentId)
                .build();

        SessionsRequest  sessions = SessionsRequest.newBuilder()
                .setStudentId(studentId)
                .build();

        return "El estudiante " + studentId + ": \n" + "Citas: \n" + appointmentStub.getAppointments(appoints) + "\n LAs sesiones del gym son: \n"
                + gymStub.consultSessions(sessions);
    }

    public static String reserveGymSession(String studentId, String timeSlot) {
        ReserveRequest reserveRequest = ReserveRequest.newBuilder()
                .setStudentId(studentId)
                .setTimeSlot(timeSlot)
                .build();

        return gymStub.reserveSession(reserveRequest).getResponse();
    }

}
