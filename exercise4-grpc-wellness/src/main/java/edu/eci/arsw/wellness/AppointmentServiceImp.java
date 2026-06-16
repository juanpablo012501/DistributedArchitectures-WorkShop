package edu.eci.arsw.wellness;

import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentServiceImp extends AppointmentServiceGrpc.AppointmentServiceImplBase {

    //attributes
    private Map<String, Appointment> appointments = new HashMap<>();
    private long idCount = 1;


    //methods
    public AppointmentServiceImp() {

    }

    @Override
    public void requestAppointment(AppointmentRequest request, StreamObserver<AppointmentResponse> responseObserver) {
        AppointmentResponse response;
        for (Appointment appointment : appointments.values()) {
            if (appointment.getDate().equals(request.getDate()) && appointment.getServiceType().equals(request.getServiceType())) {
                responseObserver.onNext(AppointmentResponse.newBuilder()
                        .setResponse("No se puede agendar la cita")
                        .build()
                );
                responseObserver.onCompleted();
                return;
            }
        }

        Appointment newAppointment = Appointment.newBuilder()
                .setId("APT" + idCount)
                .setStudentId(request.getStudentId())
                .setServiceType(request.getServiceType())
                .setDate(request.getDate())
                .setStatus(Status.REQUESTED)
                .build();
        appointments.put("API" + idCount, newAppointment);
        response = AppointmentResponse.newBuilder()
                .setResponse("Cita agendada con id: API" + idCount)
                .build();
        idCount++;

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void cancelAppointment(CancelRequest request, StreamObserver<CancelResponse> responseObserver) {
        for (Appointment appointment : appointments.values()) {
            if (appointment.getId().equals(request.getAppointmentId())) {
                appointments.remove(appointment.getId());
                responseObserver.onNext(CancelResponse.newBuilder()
                        .setResponse("Cancelación exitosa")
                        .build()
                );
                responseObserver.onCompleted();
                return;
            }
        }
        responseObserver.onNext(CancelResponse.newBuilder().setResponse("La cita no existe").build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAppointments(StudentRequest request, StreamObserver<AppointmentList> responseObserver) {
        List<Appointment> studentAppointments = new ArrayList<>();
        for (Appointment appointment : appointments.values()) {
            if(appointment.getStudentId().equals(request.getStudentId())) {
                studentAppointments.add(appointment);
            }
        }

        responseObserver.onNext(AppointmentList.newBuilder().addAllAppointments(studentAppointments).build());
        responseObserver.onCompleted();
    }

}
