package com.example.gloriosaevoluo;

public class Speedrun {

    private String game;
    private String category;

    private String namePlayer;

    private String time;

    private String region;

    private String idUser;

    public Speedrun(String game, String category, String namePlayer, String time, String region, String idUser) {
        this.game = game;
        this.category = category;
        this.namePlayer = namePlayer;
        this.time = time;
        this.region = region;
        this.idUser = idUser;
    }
    public Speedrun() {
        // Construtor padrão
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNamePlayer() {
        return namePlayer;
    }

    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region =region;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser =idUser;
    }


}

