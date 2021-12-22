package V2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Tournament
{
    public Tournament(JFrame aFrame, InterfaceManager anIM, UserInterface aUI)
    {
        frame = aFrame;
        IM = anIM;
        UI = aUI;

        playerCount = 0;
        numberOfRounds = 0;
        playerTree = new ArrayList<Player>();
        roundList = new ArrayList<Round>();
        formatList = new TournamentFormat[] { new SingleElim() };
        format = formatList[0];
        seeded = false;
        elimination = true;

        String[] listOfPlayers;

    }

    public static void instantiateMainMenu()
    {
        UI.clearListeners();
        frame.getContentPane().removeAll();
        UI.createMainMenu(frame);

        ChangeListener newListener = new ChangeListener()
        {
            public void stateChanged(ChangeEvent event)
            {
                instantiateFormatMenu();
            }
        };

        ChangeListener loadListener = new ChangeListener()
        {
            public void stateChanged(ChangeEvent event)
            {
            }
        };

        ChangeListener exitListener = new ChangeListener()
        {
            public void stateChanged(ChangeEvent event)
            {
                frame.dispose();
                System.exit(0);
            }
        };

        UI.addChangeListener(newListener);
        UI.addChangeListener(loadListener);
        UI.addChangeListener(exitListener);

        IM.updateWindow(frame);
    }

    public static void instantiateFormatMenu()
    {
        UI.clearListeners();
        frame.getContentPane().removeAll();

        UI.createFormatMenu(frame);

        ChangeListener backListener = new ChangeListener()
        {
            public void stateChanged(ChangeEvent event)
            {
                instantiateMainMenu();
            }
        };

        ChangeListener nextListener = new ChangeListener()
        {
            public void stateChanged(ChangeEvent event)
            {
                if (elimination == true)
                {
                    instantiatePlayerEntryMenu(false);
                }

                else
                {
                    instantiateOptionsMenu();
                }
            }
        };

        ChangeListener exitListener = new ChangeListener()
        {
            public void stateChanged(ChangeEvent event)
            {
                System.out.println(UI.listeners);
                instantiateMainMenu();
            }
        };
        UI.addChangeListener(backListener);
        UI.addChangeListener(nextListener);
        UI.addChangeListener(exitListener);

        for (int i = 0; i < formatList.length; i++)
        {
            final int formatNum = Integer.valueOf(i);
            ChangeListener formatListener = new ChangeListener()
            {
                public void stateChanged(ChangeEvent event)
                {
                    System.out.println(elimination);
                    format = formatList[formatNum];

                    elimination = format.setElimination();

                    System.out.println(formatNum);
                }
            };
            UI.addChangeListener(formatListener);
        }

        IM.updateWindow(frame);
    }

    public static void instantiateOptionsMenu()
    {
        UI.clearListeners();
        frame.getContentPane().removeAll();

        UI.createOptionsMenu(frame);

        IM.updateWindow(frame);

        ChangeListener backListener = new ChangeListener()
        {
            public void stateChanged(ChangeEvent event)
            {
                instantiateFormatMenu();
            }
        };

        ChangeListener nextListener = new ChangeListener()
        {
            public void stateChanged(ChangeEvent event)
            {
                String roundNum = UI.reportUserInput(frame).get(0);
                int numberOfRounds = Integer.valueOf(roundNum);

                instantiatePlayerEntryMenu(true);
            }
        };

        ChangeListener exitListener = new ChangeListener()
        {
            public void stateChanged(ChangeEvent event)
            {
                instantiateMainMenu();
            }
        };
        UI.addChangeListener(backListener);
        UI.addChangeListener(nextListener);
        UI.addChangeListener(exitListener);
    }

    public static void instantiatePlayerEntryMenu(boolean seed)
    {

        UI.clearListeners();
        frame.getContentPane().removeAll();

        UI.createPlayerEntryMenu(frame, seeded);

        IM.updateWindow(frame);


        ChangeListener backListener = new ChangeListener()
        {
            public void stateChanged(ChangeEvent event)
            {
                if (elimination)

                    instantiateFormatMenu();
                else
                    instantiateOptionsMenu();
            }
        };

        ChangeListener nextListener = new ChangeListener()
        {
            public void stateChanged(ChangeEvent event)
            {
                generateTournament();

                UI.clearListeners();
                instantiateRoundMenu(1);

            }
        };

        ChangeListener exitListener = new ChangeListener()
        {
            public void stateChanged(ChangeEvent event)
            {
                System.out.println(UI.listeners);
                instantiateMainMenu();
            }
        };

        UI.addChangeListener(backListener);
        UI.addChangeListener(nextListener);
        UI.addChangeListener(exitListener);
    }

    public static void instantiateRoundMenu(int roundNumber) {
        UI.clearListeners();
        frame.getContentPane().removeAll();

        int beginning = ((int) Math.pow(2, numberOfRounds + 1)) - 2;
        for (int s = 1; s < roundNumber; s++)
            beginning = (beginning / 2) - 1;
        beginning++;
        int end = beginning / 2 - 1;

        for (int y = 0; y < numberOfRounds; y++) {
            final int index = Integer.valueOf(y + 1);
            ChangeListener roundListener = new ChangeListener() {
                public void stateChanged(ChangeEvent event) {
                    instantiateRoundMenu(index);
                }
            };
            UI.addChangeListener(roundListener);
        }

        ChangeListener resultsListener = new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                instantiateResultsMenu(numberOfRounds);
            }

            ;


        };

        UI.addChangeListener(resultsListener);

        for (int i = 0; i < playerTree.size(); i++) {
            final int index = Integer.valueOf(i);
            ChangeListener playerListener = new ChangeListener() {
                public void stateChanged(ChangeEvent event) {
                    format.advancePlayer(index, playerTree);
                }
            };

            UI.addChangeListener(playerListener);
        }

        UI.createRoundMenu(frame, numberOfRounds, beginning, end, playerTree, roundNumber);

        IM.updateWindow(frame);
    }


    private static void instantiateResultsMenu(int resultsNum)
    {
        UI.clearListeners();
        frame.getContentPane().removeAll();

        String resultsText = format.generateResults(playerTree);

        for (int y = 0; y < numberOfRounds; y++)
        {
            final int index = Integer.valueOf(y+1);
            ChangeListener roundListener = new ChangeListener()
            {
                public void stateChanged(ChangeEvent event)
                {
                    instantiateRoundMenu(index);
                }
            };
            UI.addChangeListener(roundListener);
        }

        ChangeListener resultsListener = new ChangeListener()
        {
            public void stateChanged(ChangeEvent event)
            {
                instantiateResultsMenu(numberOfRounds);
            };


        };
        UI.addChangeListener(resultsListener);
        System.out.println(resultsText);

        UI.createResults(frame, numberOfRounds, numberOfRounds, resultsText);
        IM.updateWindow(frame);
    }

    public static void generateTournament()
    {
        String players = UI.reportUserInput(frame).get(0);

        String seedList = "";
        if (seeded == true)
        {
            seedList = UI.reportUserInput(frame).get(1);
        }

        listOfPlayers = format.generatePlayerList(players);

        int byes = format.byePad(listOfPlayers, listOfPlayers.length);
        System.out.println(playerCount);

        playerCount = listOfPlayers.length + byes;

        numberOfRounds = format.determineNumberOfRounds(playerCount, numberOfRounds);

        playerTree = format.generatePairings(listOfPlayers, playerCount, numberOfRounds, byes, seedList);
    }

    public static void main(String[] args)
    {
        InterfaceManager tourIM = new InterfaceManager();
        UserInterface tourUI = new UserInterface();

        JFrame mainFrame = new JFrame();
        mainFrame.setMinimumSize(new Dimension(300,300));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Tournament tournament = new Tournament(mainFrame, tourIM, tourUI);

        tournament.instantiateMainMenu();

    }

    private static JFrame frame;
    private static InterfaceManager IM;
    private static UserInterface UI;

    private static int playerCount;
    private static int numberOfRounds;
    private static String[] listOfPlayers;
    private static ArrayList<Player> playerTree;

    private static TournamentFormat format;
    private static TournamentFormat[] formatList;

    private static ArrayList<Round> roundList;
    private static boolean seeded;
    private static boolean elimination;
    private boolean tiebreaker;
}