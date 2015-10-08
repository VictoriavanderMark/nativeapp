package com.example.victoria.ghost;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Leaderboard {

    private ArrayList<Player> players;
    private ArrayList<String> playernames;
    private String SOURCE_FILE = "leaderboard.ghost";

    Leaderboard() {
        players = new ArrayList<Player>();
        playernames = new ArrayList<String>();
        readPlayers();
        readNames();

    }

    public void addPlayer(String name) {
        Player newPlayer = new Player(name);
        players.add(newPlayer);
        playernames.add(name);
    }

    public void removePlayer(String name) {
        players.remove(name);
    }

    public void updateScore(Player player) {
        player.updateScore();
    }

    public ArrayList<String> getPlayerNames() {
        return playernames;
    }

    public void readPlayers() {

        try{

            FileInputStream fin = new FileInputStream(SOURCE_FILE);
            ObjectInputStream ois = new ObjectInputStream(fin);
            players = (ArrayList<Player>) ois.readObject();
            ois.close();


        }catch(IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e) {

        }
    }

    public void readNames() {
        try{
            for (Player p: players) {
                playernames.add(p.getName());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public void update() {
        try{

            FileOutputStream fos = new FileOutputStream(SOURCE_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(players);
            oos.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


}
