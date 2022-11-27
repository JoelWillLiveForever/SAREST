package com.example.sarest;

public class Card {
    private String FIO = "";        // ФИО
    private String Subject = "";    // дисциплина
    private int score = 0;          // рейтинг

    public Card(String FIO, String Subject, int score)
    {
        this.FIO = FIO;
        this.Subject = Subject;
        this.score = score;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        Subject = Subject;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
