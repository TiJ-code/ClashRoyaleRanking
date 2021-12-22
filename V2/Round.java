package V2;

import java.util.ArrayList;

public class Round {

    public ArrayList<Pairing> pairingList;
    public static ArrayList<String> textPairingList;

    public Round() {
        ArrayList<Pairing> pairingList = new ArrayList<Pairing>();
        ArrayList<String> textPairingList = new ArrayList<String>();
    }

    public void addPairings(ArrayList<Pairing> list) {
        pairingList = list;
    }

    public void addPlayer(String player) {
        textPairingList.add(player);
    }

    public void addPlayers(ArrayList<String> list) {
        textPairingList = list;
    }

    private void convertPairingList() {
        textPairingList.clear();
        for(Pairing entry : pairingList) {
            textPairingList.add(entry.returnP1());
            textPairingList.add(entry.returnP2());
        }
    }

    public static ArrayList<String> textPairings() {
        return textPairingList;
    }
}
