package edu.eci.arsw.rooms;

import java.util.HashMap;
import java.util.Map;

public class RoomRepository {

    //attributes
    private Map<String, Room> rooms = new HashMap<>();

    //methods
    public RoomRepository() {
        rooms.put("E301", new Room("E301", 40));
        rooms.put("E302", new Room("E302", 30));
        rooms.put("E303", new Room("E303", 35));
        rooms.put("E304", new Room("E304", 40));
    }

    public Room findRoomById(String id) {
        return rooms.get(id);
    }

    public RoomServerResponses bookRoomById(String roomId) {
        Room room = rooms.get(roomId);
        if (room == null) {return RoomServerResponses.ROOM_DOESNT_EXIST;}

        if (room.isAvailable()) {
            room.setAvailable(false);
            return RoomServerResponses.SUCCESSFUL_BOOKING;
        }
        return RoomServerResponses.UNAVAILABLE_ROOM;
    }

    public RoomServerResponses releaseRoomById(String roomId) {
        Room room = rooms.get(roomId);
        if (room == null) {return RoomServerResponses.ROOM_DOESNT_EXIST;}

        if (!room.isAvailable()) {
            room.setAvailable(true);
            return RoomServerResponses.SUCCESSFUL_RELEASE;
        }
        return RoomServerResponses.ROOM_ISNT_BOOKED;
    }
}
