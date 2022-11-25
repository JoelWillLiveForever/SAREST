package com.example.sarest;

public class Card {
    private String FIO = "";        // ФИО
    private String Predmet = "";    // дисциплина
    private int score = 0;          // рейтинг

    public Card(String FIO, String Predmet, int score)
    {
        this.FIO = FIO;
        this.Predmet = Predmet;
        this.score = score;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getPredmet() {
        return Predmet;
    }

    public void setPredmet(String predmet) {
        Predmet = predmet;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
