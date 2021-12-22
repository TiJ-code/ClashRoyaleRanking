package V1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameManager {

    Player[] players;
    Table[] tables;

    int roundNumber;
    String filename;

    GameManager(String filename) {
        players = new Player[20];

        for(int i = 0; i < players.length; i++) {
            players[i] = new Player("KEIN SPIELER");
        }

        tables = new Table[14];

        roundNumber = 1;
        this.filename = filename;

        readFromFile();
        tableGenerator();
        showMenu();
    }

    void showMenu() {
        Scanner in = new Scanner(System.in);
        int selection = -1;

        while (selection != 0) {
            System.out.println("\n\n\t\t***MENU***\n");
            System.out.println("Aktuelle Runde: #" + roundNumber);
            System.out.println("01] Kämpfe der aktuellen Runde");
            System.out.println("02] Gewinner einer Runde eintragen");
            System.out.println("03] Ergebnisse aller Kämpfe angeben");
            System.out.println("04] Alle eleminierten Spieler ausgeben");
            System.out.println("05] Nächste Runde -->");
            System.out.println("99] EXIT");

            System.out.println("\nWähle eine Zahl: ");
            selection = in.nextInt();

            switch (selection) {
                case 1:
                    clearScreen();
                    showMatchesforCurrentRound();
                    break;
                case 2:
                    clearScreen();
                    showMatchesforCurrentRound();
                    registerPlacement();
                    clearScreen();
                    break;
                case 3:
                    clearScreen();
                    showAllRegisteredMatches();
                    break;
                case 4:
                    clearScreen();
                    showEliminatedPlayers();
                    break;
                case 5:
                    clearScreen();
                    nextRound();
                    break;
                case 99:
                    System.out.println("\nBeenden...");
                    System.exit(0);

                default:
                    System.out.println("\nWähle eine Zahl!\n");
            }
        }
    }

    void readFromFile() {
        File file = new File(filename);
        int i = 0;

        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNext()) {
                players[i] = new Player(sc.nextLine());
                i++;
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Es konnte keine Datei gefunden werden!");
        }
    }

    void nextRound() {
        if(roundNumber == 1 & tables[0].placementSet && tables[1].placementSet &&
        tables[2].placementSet && tables[3].placementSet) {
            roundNumber++;
            tableGenerator();
        }
        else if(roundNumber == 2 && tables[4].placementSet && tables[5].placementSet &&
        tables[6].placementSet && tables[7].placementSet) {
            roundNumber++;
            tableGenerator();
        }
        else if(roundNumber == 3 && tables[8].placementSet && tables[9].placementSet &&
                tables[10].placementSet) {
            roundNumber++;
            tableGenerator();
        } else if(roundNumber == 4 && tables[11].placementSet) {
            roundNumber++;
            tableGenerator();
        } else if(roundNumber == 5 && tables[12].placementSet) {
            roundNumber++;
            tableGenerator();
        } else if(roundNumber >= 6 && tables[13].placementSet) {
            System.out.println("Das Tunier ist zuende,\n" +
                    "Das sind die finalen Ergebnisse: ");
            System.out.println("1ter: " + tables[13].placements[0].name);
            System.out.println("2ter: " + tables[13].placements[1].name);
            System.out.println("3ter: " + tables[13].placements[2].name);
            System.out.println("4ter: " + tables[13].placements[3].name);
        } else {
            System.out.println("Manche Kämpfe brauchen noch Platzierungen, diese haben bereits welche: ");
            showAllRegisteredMatches();
        }
    }

    void tableGenerator() {
        int skipper = 0;

        if(roundNumber == 1) {
            for(int i = 0; i < 4; i++) {
                tables[i] = new Table(players[skipper], players[skipper + 1],
                                        players[skipper + 2], players[skipper + 3]);
                skipper += 4;
            }
        }
        else if(roundNumber == 2) {
            for(int i = 4; i < 8; i++) {
                tables[i] = new Table(tables[0].placements[skipper], tables[1].placements[skipper],
                                        tables[2].placements[skipper], tables[3].placements[skipper]);
                skipper++;
            }
        }
        else if(roundNumber == 3) {
            tables[8] = new Table(tables[4].placements[0], tables[4].placements[1],
                                    tables[5].placements[0], tables[5].placements[1]);

            tables[9] = new Table(tables[6].placements[0], tables[6].placements[1],
                                    tables[4].placements[2], tables[4].placements[3]);

            tables[10] = new Table(tables[7].placements[0], tables[7].placements[1],
                                    tables[5].placements[2], tables[5].placements[3]);
        }
        else if(roundNumber == 4) {
            tables[11] = new Table(tables[9].placements[0], tables[9].placements[1],
                                    tables[10].placements[0], tables[10].placements[1]);
        }
        else if(roundNumber == 5) {
            tables[12] = new Table(tables[11].placements[0], tables[11].placements[1],
                                    tables[8].placements[2], tables[8].placements[3]);
        }
        else if(roundNumber == 6) {
            //Finale
            tables[13] = new Table(tables[8].placements[0], tables[8].placements[1],
                                    tables[12].placements[0], tables[12].placements[1]);
        }
    }

    void showMatchesforCurrentRound() {
        if(roundNumber == 1) {
            for(int i = 0; i < 4; i++) {
                System.out.println("Tabelle: #" + (i + 1) + " - " + "MatchID: #" + (i + 1));
                System.out.println(tables[i].p1.name + " vs " + tables[i].p2.name +
                        " vs " + tables[i].p3.name + " vs " + tables[i].p4.name);
                System.out.println("");
            }
        } else if(roundNumber == 2) {
            for(int i = 4; i < 8; i++) {
                System.out.println("Tabelle: #" + (i - 3) + " - " + "MatchID: #" + (i + 1));
                System.out.println(tables[i].p1.name + " vs " + tables[i].p2.name +
                        " vs " + tables[i].p3.name + " vs " + tables[i].p4.name);
                System.out.println("");
            }
        } else if(roundNumber == 3) {
            for(int i = 8; i < 11; i++) {
                System.out.println("Tabelle: #" + (i - 7) + " - " + "MatchID: #" + (i + 1));
                if(i == 8) {
                    System.out.println("*Halbfinale der Siegerrunde - Sieger zieht ins Finale*");
                }
                System.out.println(tables[i].p1.name + " vs " + tables[i].p2.name +
                        " vs " + tables[i].p3.name + " vs " + tables[i].p4.name);
                System.out.println("");
            }
        } else if(roundNumber == 4) {
            System.out.println("Tabelle: #1" + " - " + "MatchID: #12");
            System.out.println(tables[11].p1.name + " vs " + tables[11].p2.name +
                    " vs " + tables[11].p3.name + " vs " + tables[11].p4.name);
            System.out.println("");
        } else if(roundNumber == 5) {
            System.out.println("Tabelle: #1" + " - " + "MatchID: #13");
            System.out.println("*Finale der Verliererrunde - Sieger zieht ins Finale*");
            System.out.println(tables[12].p1.name + " vs " + tables[12].p2.name +
                    " vs " + tables[12].p3.name + " vs " + tables[12].p4.name);
            System.out.println("");
        } else if(roundNumber == 6) {
            System.out.println("Tabelle: #1" + " - " + "MatchID: #14");
            System.out.println("*Finale*");
            System.out.println(tables[13].p1.name + " vs " + tables[13].p2.name +
                    " vs " + tables[13].p3.name + " vs " + tables[13].p4.name);
            System.out.println("");
        }
    }

    void showAllRegisteredMatches() {
        for(int i = 0; i < tables.length; i++) {
            if(tables[i] == null) {
                continue;
            }

            if(tables[i].placementSet == true) {
                System.out.println("MatchID: #" + (i+1));
                for(int j = 0; j < tables[i].placements.length; j++) {
                    System.out.println("#" + (j + 1) + " " + tables[i].placements[j].name);
                }
                System.out.println("");
            }
        }
    }

    void registerPlacement() {
        Scanner in = new Scanner(System.in);
        int table = -1;
        System.out.println("\nWelches Match (MatchID): ");
        table = in.nextInt();
        tables[table-1].setPlacement();
    }

    public static void clearScreen() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    void showEliminatedPlayers() {
        System.out.println("Diese Spieler sind raus aus dem Tunier (Zwei mal verloren): ");
        for(int i = 0; i < players.length; i++) {
            if(players[i].timesLost == 2) {
                System.out.println("\t" + players[i].name);
            }
        }
    }
}
