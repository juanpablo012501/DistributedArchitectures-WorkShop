package edu.eci.arsw.rooms;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;

public class RoomHttpServer {

    //methods
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            RoomRepository repository = new RoomRepository();
            server.createContext("/rooms", new RoomHandler(repository));
            server.createContext("/rooms/reserve", new ReserveHandler(repository));
            server.createContext("/rooms/release", new ReleaseHandler(repository));
            server.setExecutor(null);
            server.start();
            System.out.println("RoomHttpServer listening through http://localhost:8080/room?id=1");

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    static class RoomHandler implements HttpHandler {

        //attributes
        private RoomRepository repository;

        //methods
        public RoomHandler(RoomRepository repository) {
            this.repository = repository;
        }

        @Override
        public void handle(HttpExchange exchange) {
            try {
                String query = exchange.getRequestURI().getQuery();
                String id = extractId(query);
                String response;
                if (id.isEmpty()) {
                    Map<String, Room> rooms = repository.getRooms();
                    response = "<html><body><h1>Room list: \n";
                    for (Room room : rooms.values()) {
                        response += room.toText() + " \n";
                    }
                    response = response + " \n</h1></body></html>";

                } else {
                    Room room = repository.findRoomById(id);

                    if (room == null) {
                        response = "<html><body><h1>El salón no existe</h1></body></html>";
                    } else {
                        response = "<html><body><h1>" + room.toText() + "</h1></body></html>";
                    }

                }

                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        private String extractId(String query) {
            if (query == null || !query.contains("id=")) {
                return "";
            }
            return query.split("id=")[1];
        }
    }

    public static class ReserveHandler implements HttpHandler {

        //attributes
        private RoomRepository repository;

        //methods
        public ReserveHandler(RoomRepository repository) {
            this.repository = repository;
        }

        @Override
        public void handle(HttpExchange exchange) {
            try {
                String query = exchange.getRequestURI().getQuery();
                String id = query.split("id=")[1];
                RoomServerResponses serverResponses = repository.bookRoomById(id);
                String response;

                if (serverResponses == RoomServerResponses.UNAVAILABLE_ROOM) {
                    response = "<html><body><h1>El salón no está disponible</h1></body></html>";
                } else if (serverResponses == RoomServerResponses.ROOM_DOESNT_EXIST) {
                    response = "<html><body><h1>El salón no existe</h1></body></html>";
                } else if (serverResponses == RoomServerResponses.SUCCESSFUL_BOOKING) {
                    response = "<html><body><h1>EL salón ha sido reservado</h1></body></html>";
                } else {
                    response = "<html><body><h1>Operación inválida</h1></body></html>";
                }

                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    static class ReleaseHandler implements HttpHandler {

        //attributes
        private RoomRepository repository;

        //methods
        public ReleaseHandler(RoomRepository repository) {
            this.repository = repository;
        }

        @Override
        public void handle(HttpExchange exchange) {
            try {
                String query = exchange.getRequestURI().getQuery();
                String id = query.split("id=")[1];
                RoomServerResponses serverResponses = repository.releaseRoomById(id);
                String response;

                if (serverResponses == RoomServerResponses.ROOM_ISNT_BOOKED) {
                    response = "<html><body><h1>El salón no está reservado</h1></body></html>";
                } else if (serverResponses == RoomServerResponses.ROOM_DOESNT_EXIST) {
                    response = "<html><body><h1>El salón no existe</h1></body></html>";
                } else if (serverResponses == RoomServerResponses.SUCCESSFUL_RELEASE) {
                    response = "<html><body><h1>EL salón ha sido liberado</h1></body></html>";
                } else {
                    response = "<html><body><h1>Operación inválida</h1></body></html>";
                }

                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
