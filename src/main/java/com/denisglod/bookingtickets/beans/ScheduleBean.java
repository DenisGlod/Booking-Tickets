package com.denisglod.bookingtickets.beans;

import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleBean {
    private String name;
    private ArrayList<String> data;
    private ArrayList<HashMap<Integer, Boolean>> seats;

    public void setName(String name) {
        this.name = name;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public void setSeats(ArrayList<HashMap<Integer, Boolean>> seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder().append("*** ").append(name).append(" ***");
        for (int i = 0; i < data.size(); i++) {
            result.append("\n--> ").append(data.get(i)).append("\n");
            HashMap<Integer, Boolean> seat = seats.get(i);
            seat.forEach((k, v) -> result.append(v ? k : (char) 27 + "[31m" + k + (char) 27 + "[0m").append("  "));
        }
        result.append("\n");
        return result.toString();
    }
}
