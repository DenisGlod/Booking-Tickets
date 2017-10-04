package com.denisglod.bookingtickets.beans;

public class FilmBean {
    private int key;
    private String name;
    private String date;
    private int seat;
    private boolean incorrectName;
    private boolean incorrectDate;
    private boolean incorrectSeat;
    private boolean incorrectInfo;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public boolean isIncorrectName() {
        return incorrectName;
    }

    public void setIncorrectName(boolean incorrectName) {
        this.incorrectName = incorrectName;
    }

    public boolean isIncorrectDate() {
        return incorrectDate;
    }

    public void setIncorrectDate(boolean incorrectDate) {
        this.incorrectDate = incorrectDate;
    }

    public boolean isIncorrectSeat() {
        return incorrectSeat;
    }

    public void setIncorrectSeat(boolean incorrectSeat) {
        this.incorrectSeat = incorrectSeat;
    }

    public boolean isIncorrectInfo() {
        return incorrectInfo;
    }

    public void setIncorrectInfo(boolean incorrectInfo) {
        this.incorrectInfo = incorrectInfo;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + seat;
    }
}
