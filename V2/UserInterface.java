package V2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class UserInterface
{
    public UserInterface()
    {
        listeners = new ArrayList<>();
        userInputFields = new ArrayList<>();
        userInput = new ArrayList<>();
    }
    public void clearListeners()
    {
        listeners.clear();
        userInputFields.clear();
        userInput.clear();
    }

    public void addChangeListener(ChangeListener aListener)
    {
        listeners.add(aListener);
    }

    public void addChangeListener2(int index, ChangeListener aListener)
    {
        listeners.add(index, aListener);
    }

    public static void addTournamentListener(AbstractButton button, final int index)
    {
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                ChangeEvent updateEvent = new ChangeEvent(this);
                listeners.get(index).stateChanged(updateEvent);
            }
        });
    }

    public static ArrayList<String> reportUserInput(JFrame frame)
    {
        for (JTextArea userInputField : userInputFields) userInput.add(userInputField.getText());

        return userInput;
    }

    public static JPanel createNavigationPanel()
    {
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout());

        JButton backButton = new JButton("Zurück");
        JButton nextButton = new JButton("Weiter");
        JButton quitButton = new JButton("Beenden");

        addTournamentListener(backButton, 0);
        addTournamentListener(nextButton, 1);
        addTournamentListener(quitButton, 2);

        navigationPanel.add(backButton);
        navigationPanel.add(nextButton);
        navigationPanel.add(quitButton);

        return navigationPanel;
    }


    public static void createMainMenu(final JFrame frame)
    {
        JButton newButton = new JButton("Neu");
        JButton loadButton = new JButton("Laden [WIP]");
        JButton exitButton = new JButton("Beenden");

        frame.setLayout(new GridLayout(3,1));

        frame.add(newButton);
        frame.add(loadButton);
        frame.add(exitButton);

        addTournamentListener(newButton, 0);
        addTournamentListener(loadButton, 1);
        addTournamentListener(exitButton, 2);
    }

    public static void createFormatMenu(JFrame frame)
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(new JLabel("Format Wählen:"),BorderLayout.WEST);

        JPanel navigationPanel = createNavigationPanel();

        JPanel formatPanel = new JPanel();
        formatPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // JRadioButton SEButton = new JRadioButton();
        // JRadioButton DEButton = new JRadioButton();
        // JRadioButton MMButton = new JRadioButton();
        // JRadioButton SSButton = new JRadioButton();
        // JRadioButton RRButton = new JRadioButton();

        ButtonGroup formatGroup = new ButtonGroup();
        c.gridx = 0;
        for (int y = 0; y < 5; y++)
        {
            JRadioButton formatButton = new JRadioButton();
            if (y == 0)
                formatButton.setSelected(true);

            c.gridy = y;
            formatGroup.add(formatButton);
            formatPanel.add(formatButton,c);
            addTournamentListener(formatButton, y+3);
            c.gridy = y;
        }

        c.gridx = 1;
        c.gridy = 0;
        formatPanel.add(new JLabel("1o1"),c);
        c.gridx = 1;
        c.gridy = 1;
        formatPanel.add(new JLabel("McMahon      [WIP]"),c);
        c.gridx = 1;
        c.gridy = 2;
        formatPanel.add(new JLabel("2o2                  [WIP]"),c);
        c.gridx = 1;
        c.gridy = 3;
        formatPanel.add(new JLabel("Swiss             [WIP]"),c);
        c.gridx = 1;
        c.gridy = 4;
        formatPanel.add(new JLabel("Round-robin  [WIP]"),c);

        JPanel seedPanel = new JPanel();
        seedPanel.setLayout(new BorderLayout());

        JPanel YNPanel = new JPanel();
        YNPanel.setLayout(new BoxLayout(YNPanel, BoxLayout.X_AXIS));

        JRadioButton YButton = new JRadioButton("Ja");
        JRadioButton NButton = new JRadioButton("Nein", true);

        YNPanel.add(YButton);
        YNPanel.add(NButton);

        ButtonGroup YNGroup = new ButtonGroup();
        YNGroup.add(YButton);
        YNGroup.add(NButton);

        seedPanel.add(new JLabel("Voreingestellte Werte? [WIP]"), BorderLayout.NORTH);
        seedPanel.add(Box.createRigidArea(new Dimension(0,10)),BorderLayout.CENTER);
        seedPanel.add(YNPanel, BorderLayout.SOUTH);

        mainPanel.add(titlePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
        mainPanel.add(formatPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
        mainPanel.add(seedPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
        mainPanel.add(navigationPanel);

        frame.add(mainPanel);
    }

    public static void createOptionsMenu(JFrame frame)
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel roundNumPanel = new JPanel();
        roundNumPanel.setLayout(new BoxLayout(roundNumPanel, BoxLayout.X_AXIS));

        roundNumPanel.add(new JLabel("Schreibe die Anzahl der Runden und wähle eine TieBreaker Methode: "));
        JTextArea numberField = new JTextArea("5");
        numberField.setSize(1,1);
        userInputFields.add(numberField);

        JPanel tiePanel = new JPanel();
        tiePanel.setLayout(new BoxLayout(tiePanel, BoxLayout.X_AXIS));

        JRadioButton H2HButton = new JRadioButton("Kopf an Kopf",true);
        JRadioButton RecButton = new JRadioButton("RekordGame [WIP]");

        tiePanel.add(H2HButton);
        tiePanel.add(RecButton);

        ButtonGroup tieGroup = new ButtonGroup();
        tieGroup.add(H2HButton);
        tieGroup.add(RecButton);

        mainPanel.add(roundNumPanel);
        mainPanel.add(tiePanel);
        mainPanel.add(numberField);

        JPanel navigationPanel = createNavigationPanel();

        frame.add(mainPanel);
        frame.add(navigationPanel);
    }

    public static void createPlayerEntryMenu(JFrame frame, boolean seed)
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
        JTextArea playerField = new JTextArea("Schreibe hier alle Teilnehmenden Spieler hinein (Lösche diese Zeile).", 20,50);
        userInputFields.add(playerField);

        if (seed)
        {
            JTextArea seedField = new JTextArea("[WIP] Werte setzen.", 20, 1);
            userInputFields.add(seedField);
            textPanel.add(seedField);
            textPanel.add(Box.createRigidArea(new Dimension(20,10)));
        }
        textPanel.add(playerField);

        JPanel navigationPanel = createNavigationPanel();
        mainPanel.add(textPanel);
        mainPanel.add(navigationPanel);

        frame.add(mainPanel);
    }

    public static void createRoundMenu(JFrame frame, int numberOfRounds, int start, int end, ArrayList<Player> players, int roundNumber) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel roundPanel = createRoundPanel(frame, numberOfRounds, roundNumber, players.size());

        JPanel pairingPanel = new JPanel();
        pairingPanel.setLayout(new GridLayout(0,2));

        for (int i = 0; i < players.size(); i++)
        {

            JButton playerButton = new JButton(players.get(i).name());
            addTournamentListener(playerButton, i+numberOfRounds+1);

            if (i < start && i > end)
            {

                pairingPanel.add(playerButton);
            }
        }

        mainPanel.add(roundPanel, BorderLayout.NORTH);
        mainPanel.add(pairingPanel, BorderLayout.CENTER);
        frame.add(mainPanel);
    }

    public static JPanel createRoundPanel(JFrame frame, int numberOfRounds, int roundNumber, int startIndex)
    {
        startIndex = 0;

        ButtonGroup roundGroup = new ButtonGroup();
        JPanel roundPanel = new JPanel();
        roundPanel.setLayout(new BoxLayout(roundPanel, BoxLayout.X_AXIS));

        for (int i = 0; i < numberOfRounds; i++)
        {
            String roundName = String.valueOf(i+1);
            JToggleButton roundButton;

            if (i == roundNumber-1)
            {
                boolean selectionState = true;
                roundButton = new JToggleButton("Runde " + roundName, selectionState);
            }
            else
            {
                roundButton = new JToggleButton("Runde " + roundName);
            }


            roundPanel.add(roundButton);
            roundGroup.add(roundButton);
            addTournamentListener(roundButton, startIndex+i);
        }

        JToggleButton resultsButton = new JToggleButton("Ergebnisse");
        roundGroup.add(resultsButton);

        addTournamentListener(resultsButton, startIndex+numberOfRounds);
        roundPanel.add(resultsButton);

        return roundPanel;

    }

    public static void createResults(JFrame frame, int numberOfRounds, int beginning, String results)
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel roundPanel = new JPanel();
        roundPanel = createRoundPanel(frame, numberOfRounds, numberOfRounds+1, 0);

        JTextArea textResults = new JTextArea(results);
        textResults.setEditable(false);

        mainPanel.add(roundPanel, BorderLayout.NORTH);
        mainPanel.add(textResults, BorderLayout.CENTER);
        frame.add(mainPanel);

    }


    private JFrame frame;
    public static ArrayList<ChangeListener> listeners;

    private static ArrayList<JTextArea> userInputFields;
    private static ArrayList<String> userInput;
}