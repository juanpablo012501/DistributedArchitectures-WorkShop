package edu.eci.arsw.wellness;

import io.grpc.stub.StreamObserver;

import java.util.*;

public class GymServiceImp extends GymServiceGrpc.GymServiceImplBase {

    //attributes
    private List<Session> sessions = new ArrayList<>();
    private long idCount = 1;

    //methods
    public GymServiceImp() {}

    @Override
    public void reserveSession(ReserveRequest request, StreamObserver<ReserveResponse> responseObserver) {
        Session session = null;
        for (Session s : sessions) {
            if (s.getTimeSlot().equals(request.getTimeSlot())) {
                session = s;
            }
        }
        if (session == null) {
            sessions.add(Session.newBuilder()
                            .setId("SES" + idCount)
                    .setTimeSlot(request.getTimeSlot())
                            .setStudentId(request.getStudentId())
                    .build()
            );
            idCount++;
        }

        responseObserver.onNext(ReserveResponse.newBuilder().setResponse("Sesión reservada").build());
        responseObserver.onCompleted();
    }

    @Override
    public void removeSession(RemoveRequest request, StreamObserver<RemoveResponse> responseObserver) {
        Iterator<Session> iterator = sessions.iterator();
        while (iterator.hasNext()) {
            Session s = iterator.next();
            if (s.getId().equals(request.getSessionId()) && s.getStudentId().equals(request.getStudentId())) {
                iterator.remove();
                responseObserver.onNext(RemoveResponse.newBuilder().setResponse("Sesión eliminada").build());
                responseObserver.onCompleted();
                return;
            }
        }
        responseObserver.onNext(RemoveResponse.newBuilder().setResponse("La sesión no existe").build());
        responseObserver.onCompleted();
    }

    @Override
    public void consultSessions(SessionsRequest request, StreamObserver<SessionList> responseObserver) {
        List<Session> studentSessions = new ArrayList<>();
        for (Session s : sessions) {
            if(s.getStudentId().equals(request.getStudentId())) {
                studentSessions.add(s);
            }
        }

        responseObserver.onNext(SessionList.newBuilder().addAllSessions(studentSessions).build());
        responseObserver.onCompleted();
    }
}
