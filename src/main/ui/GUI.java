package ui;


import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// a GUI for the game
public class GUI extends JFrame implements ActionListener {

    private JFrame frame;
    private JPanel inventoryPanel;
    private JPanel statsPanel;
    private JPanel buttonPanel;
    private JPanel textPanel;
    private JPanel quitPanel;
    private JButton saveButton;
    private JButton characterButton;
    private JButton loadButton;
    private JButton quitButton;
    private JButton scenariosButton;
    private JButton continueButton;
    private JButton startButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton backScenario;
    private JButton backCharCreate;
    private JButton itemInfo;
    private JButton useButton;
    private JButton discardButton;
    private JButton usePotion;
    private JButton backToRest;
    private JTextField name;
    private JTextField removeScen;
    private JTextField scenName;
    private JTextField scen1;
    private JTextField scen2;
    private JTextField scen3;
    private JTextField scen4;
    private JTextField scen5;
    private JTextField scen6;
    private JTextField selectItem;
    private ImageIcon startIcon;
    private JLabel info;
    private JLabel startLabel;
    private JLabel itemList;
    private JLabel itemStats;
    private JLabel potionCount;
    private JLabel endMessage;
    private JLabel scenariosList;
    private Game game;
    private int itemPointer;

    // Creates new GUI
    public GUI() {
        game = new Game();

        frame = new JFrame();
        frame.setSize(1000,1000);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        layouts();
        loadImage();
        buttonsStart();
        buttonsScenario();
        buttonsRest();
        quitPanel.add(quitButton);
        menu();
        frame.setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: loads the start image
    private void loadImage() {
        String sep = System.getProperty("file.separator");
        startIcon = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "forest.jpg");
        startLabel = new JLabel(startIcon);
    }

    // MODIFIES: this
    // EFFECTS: Generates panels
    private void layouts() {
        statsPanel = new JPanel();
        statsPanel.setBounds(0, 0, 250, 500);
        statsPanel.setBackground(Color.lightGray);

        inventoryPanel = new JPanel();
        inventoryPanel.setBounds(250,0, 750, 500);


        textPanel = new JPanel();
        textPanel.setBounds(0,500, 1000, 100);
        textPanel.setBackground(Color.lightGray);

        buttonPanel = new JPanel();
        buttonPanel.setBounds(0,600, 800, 400);
        buttonPanel.setBackground(Color.darkGray);

        quitPanel = new JPanel();
        quitPanel.setBounds(800,600, 200, 400);

        frame.add(statsPanel);
        frame.add(inventoryPanel);
        frame.add(buttonPanel);
        frame.add(textPanel);
        frame.add(quitPanel);
    }

    // MODIFIES: this
    // EFFECTS: Generates buttons associated with main menu
    private void buttonsStart() {
        startButton = new JButton();
        scenariosButton = new JButton();
        loadButton = new JButton();
        quitButton = new JButton();
        characterButton = new JButton();
        backCharCreate = new JButton();

        buttonInit(startButton, 250, 250, "Start");
        buttonInit(scenariosButton, 0, 0, "Scenarios");
        buttonInit(loadButton, 500, 0, "Load");
        buttonInit(quitButton, 750, 0, "Quit");
        buttonInit(characterButton, 750, 0, "Create New Character");
        buttonInit(backCharCreate, 0, 0, "Back");
    }

    // MODIFIES: this
    // EFFECTS: Generates buttons associated with scenario menu
    private void buttonsScenario() {
        backScenario = new JButton();
        addButton = new JButton();
        removeButton = new JButton();
        buttonInit(backScenario, 0, 0, "Back");
        buttonInit(addButton, 750, 0, "Add Scenario");
        buttonInit(removeButton, 500, 0, "Remove Scenario");

    }

    // MODIFIES: this
    // EFFECTS: Generates buttons associated with rest menu
    private void buttonsRest() {
        saveButton = new JButton();
        continueButton = new JButton();
        itemInfo = new JButton();
        useButton = new JButton();
        discardButton = new JButton();
        usePotion = new JButton();
        backToRest = new JButton();
        buttonInit(saveButton, 500, 0, "Save");
        buttonInit(continueButton, 0, 0, "Continue");
        buttonInit(itemInfo, 800, 100, "Item Info");
        buttonInit(useButton, 800, 0, "Use Item for Stats");
        buttonInit(discardButton, 800, 100, "Discard Item for EXP");
        buttonInit(usePotion, 800, 400, "Use Potion");
        buttonInit(backToRest, 800, 400, "Back");
    }

    // MODIFIES: button
    // EFFECTS: general button initiation method
    private void buttonInit(JButton button, int x, int y, String label) {
        button.setSize(100,100);
        button.setBounds(x, y, 100, 100);
        button.setText(label);
        button.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: Creates main menu
    private void menu() {
        buttonPanel.add(startButton);
        buttonPanel.add(scenariosButton);
        buttonPanel.add(loadButton);
        inventoryPanel.add(startLabel);
    }

    // MODIFIES: this
    // EFFECTS: Removes main menu
    private void menuGone() {
        frame.setVisible(false);
        buttonPanel.remove(startButton);
        buttonPanel.remove(scenariosButton);
        buttonPanel.remove(loadButton);
        inventoryPanel.remove(startLabel);
    }

    // MODIFIES: this
    // EFFECTS: Creates character creation menu
    private void charCreateMenu() {
        menuGone();
        name = new JTextField();
        name.setPreferredSize(new Dimension(250,40));
        textPanel.add(name);
        textPanel.add(characterButton);
        buttonPanel.add(backCharCreate);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates scenario menu
    private void scenariosMenu() {
        menuGone();
        buttonPanel.add(backScenario);
        scenInit();
        textPanel.add(scenName);
        textPanel.add(scen1);
        textPanel.add(scen2);
        textPanel.add(scen3);
        textPanel.add(scen4);
        textPanel.add(scen5);
        textPanel.add(scen6);
        textPanel.add(addButton);
        buttonPanel.add(removeScen);
        buttonPanel.add(removeButton);
        scenariosListing();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates the scenario listing
    private void scenariosListing() {
        scenariosList = new JLabel();
        ArrayList<String> names = game.getScenarioNames();
        StringBuilder full = new StringBuilder();
        for (int x = 0; x < names.size(); x++) {
            full.append(x).append(": ").append(names.get(x)).append("<br> ");
        }
        scenariosList.setText("<html><body>" + full + "</body></html>");
        inventoryPanel.add(scenariosList);
    }



    // MODIFIES: this
    // EFFECTS: Initializes text fields in scenario menu
    private void scenInit() {
        scenName = new JTextField();
        scen1 = new JTextField();
        scen2 = new JTextField();
        scen3 = new JTextField();
        scen4 = new JTextField();
        scen5 = new JTextField();
        scen6 = new JTextField();
        removeScen = new JTextField();
        scenName.setPreferredSize(new Dimension(250,40));
        scen1.setPreferredSize(new Dimension(40,40));
        scen2.setPreferredSize(new Dimension(40,40));
        scen3.setPreferredSize(new Dimension(40,40));
        scen4.setPreferredSize(new Dimension(40,40));
        scen5.setPreferredSize(new Dimension(40,40));
        scen6.setPreferredSize(new Dimension(40,40));
        removeScen.setPreferredSize(new Dimension(40,40));
    }

    // MODIFIES: this
    // EFFECTS: Goes back from scenario menu to main menu
    private void backFromScenarios() {
        frame.setVisible(false);
        buttonPanel.remove(backScenario);
        textPanel.remove(scenName);
        textPanel.remove(scen1);
        textPanel.remove(scen2);
        textPanel.remove(scen3);
        textPanel.remove(scen4);
        textPanel.remove(scen5);
        textPanel.remove(scen6);
        textPanel.remove(addButton);
        buttonPanel.remove(removeScen);
        buttonPanel.remove(removeButton);
        inventoryPanel.remove(scenariosList);
        menu();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Goes back from character creation menu to main menu
    private void backFromCharCreate() {
        frame.setVisible(false);
        textPanel.remove(name);
        textPanel.remove(characterButton);
        buttonPanel.remove(backCharCreate);
        menu();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates in-game character and progresses to rest menu
    private void createCharacter(String s) {
        game.createCharacter(s);
        frame.setVisible(false);
        textPanel.remove(name);
        textPanel.remove(characterButton);
        buttonPanel.remove(backCharCreate);
        restMenu();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Skips character creation
    private void playFromLoad() {
        frame.setVisible(false);
        menuGone();
        restMenu();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates stats readout
    private void createStatsReadout() {
        info = new JLabel();
        info.setBounds(0,0,250, 500);
        info.setText("<html><body>Name: " + game.getName() + "<br>Level: " + game.getLevel()
                + "<br>Health: " + game.getHealth()
                + "<br>VIT: " + game.getStat("VIT") + "<br>AGI: " + game.getStat("AGI")
                + "<br>STR: " + game.getStat("STR") + "<br>MAG: " + game.getStat("MAG")
                + "<br>INT: " + game.getStat("INT") + "<br>LUC: " + game.getStat("LUC")
                + "<br>Clearings: " + game.getClearings() + "</body></html>");
    }

    // MODIFIES: this
    // EFFECTS: Creates rest menu
    private void restMenu() {
        frame.setVisible(false);
        buttonPanel.add(continueButton);
        buttonPanel.add(saveButton);
        createStatsReadout();
        statsPanel.add(info);
        createItemList();
        inventoryPanel.add(itemList);
        selectItem = new JTextField();
        selectItem.setPreferredSize(new Dimension(40,40));
        selectItem.setBounds(0, 0, 800, 0);
        inventoryPanel.add(selectItem);
        inventoryPanel.add(itemInfo);
        countingPotion();
        inventoryPanel.add(potionCount);
        inventoryPanel.add(usePotion);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates potion count readout
    private void countingPotion() {
        potionCount = new JLabel();
        potionCount.setBounds(0,400,250, 500);
        potionCount.setText("Potions: " + game.getPotions());
    }

    // MODIFIES: this
    // EFFECTS: Creates item list readout
    private void createItemList() {
        itemList = new JLabel();
        itemList.setBounds(0,0,250, 500);
        StringBuilder full = new StringBuilder();
        for (int x = 0; x < game.getItems().size(); x++) {
            full.append(x).append(": ").append(game.getItems().get(x).getName()).append("<br> ");
        }
        itemList.setText("<html><body>" + full + "</body></html>");
    }

    // MODIFIES: this
    // EFFECTS: Finds and resolves a scenario
    private void findAndResolveScenario() {
        game.findScenario();
        if (game.getHealth() == 0) {
            endGameState();
        } else {
            removeRestMenu();
            restMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes the rest menu
    private void removeRestMenu() {
        statsPanel.remove(info);
        inventoryPanel.remove(selectItem);
        inventoryPanel.remove(itemList);
        inventoryPanel.remove(itemInfo);
        inventoryPanel.remove(potionCount);
        inventoryPanel.remove(usePotion);
        buttonPanel.remove(continueButton);
        buttonPanel.remove(saveButton);
    }

    // MODIFIES: this
    // EFFECTS: Displays the end game state
    private void endGameState() {
        frame.setVisible(false);
        endMessage = new JLabel();
        endMessage.setBounds(0,0,250, 500);
        endMessage.setText(game.getName() + "'s journey ended at level " + game.getLevel() + " after "
                + game.getClearings() + " clearings.");
        buttonPanel.remove(continueButton);
        buttonPanel.remove(saveButton);
        removeRestMenu();
        inventoryPanel.add(endMessage);

        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Exits item menu
    private void exitItemMenu() {
        inventoryPanel.remove(useButton);
        inventoryPanel.remove(discardButton);
        inventoryPanel.remove(itemStats);
        inventoryPanel.remove(backToRest);
        removeRestMenu();
        restMenu();
    }

    // MODIFIES: this
    // EFFECTS: Resets the scenario listings
    private void resetScenarioListing() {
        frame.setVisible(false);
        inventoryPanel.remove(scenariosList);
        scenariosListing();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Shows item stats
    private void showItemInfo(int i) {
        frame.setVisible(false);
        if (i >= 0 && i < game.getItemListSize()) {
            itemPointer = i;
            itemStats = new JLabel();
            itemStats.setBounds(0,0,250, 500);
            Item item = game.getItems().get(itemPointer);
            StringBuilder full = new StringBuilder();
            for (int x = 0; x < item.getEffectsAsStrings().size(); x++) {
                full.append(item.getEffectsAsStrings().get(x)).append("<br> ");
            }
            itemStats.setText("<html><body>" + full + "</body></html>");
            initShowItemsMenu();
            frame.setVisible(true);
        } else {
            removeRestMenu();
            restMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: Initializes the item stats menu
    private void initShowItemsMenu() {
        inventoryPanel.remove(itemList);
        inventoryPanel.remove(itemInfo);
        inventoryPanel.remove(selectItem);
        inventoryPanel.remove(potionCount);
        inventoryPanel.remove(usePotion);
        buttonPanel.remove(continueButton);
        buttonPanel.remove(saveButton);
        inventoryPanel.add(itemStats);
        inventoryPanel.add(useButton);
        inventoryPanel.add(discardButton);
        inventoryPanel.add(backToRest);
    }

    // MODIFIES: this
    // EFFECTS: uses a potion (if possible) and returns to rest menu
    private void usingPotion() {
        frame.setVisible(false);
        game.usePotion();
        removeRestMenu();
        restMenu();
        frame.setVisible(true);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: actions performed with a button is pressed
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quitButton) {
            System.exit(0);
        }

        mainMenuActions(e);
        charCreateActions(e);
        scenarioMenuActions(e);
        restMenuActions(e);
        inventoryMenuActions(e);

        if (e.getSource() == saveButton) {
            game.saveGame();
        }

    }

    // MODIFIES: this
    // EFFECTS: actions associated with the rest menu
    private void restMenuActions(ActionEvent e) {
        if (e.getSource() == continueButton) {
            findAndResolveScenario();
        }
        if (e.getSource() == saveButton) {
            game.saveGame();
        }
        if (e.getSource() == itemInfo) {
            try {
                showItemInfo(Integer.parseInt(selectItem.getText()));
            } catch (Exception f) {
                removeRestMenu();
                restMenu();
            }
        }
        if (e.getSource() == usePotion) {
            usingPotion();
        }

    }

    // MODIFIES: this
    // EFFECTS: actions associated with the inventory menu
    private void inventoryMenuActions(ActionEvent e) {
        if (e.getSource() == useButton) {
            game.use(itemPointer);
            exitItemMenu();
        }
        if (e.getSource() == discardButton) {
            game.discard(itemPointer);
            exitItemMenu();
        }
        if (e.getSource() == backToRest) {
            exitItemMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: actions associated with the character creation menu
    public void charCreateActions(ActionEvent e) {
        if (e.getSource() == characterButton) {
            createCharacter(name.getText());
        }
        if (e.getSource() == backCharCreate) {
            backFromCharCreate();
        }
    }

    // MODIFIES: this
    // EFFECTS: actions associated with the main menu
    public void mainMenuActions(ActionEvent e) {
        if (e.getSource() == startButton) {
            charCreateMenu();
        }
        if (e.getSource() == scenariosButton) {
            scenariosMenu();
        }
        if (e.getSource() == loadButton) {
            if (endMessage != null) {
                inventoryPanel.remove(endMessage);
            }
            game.loadGame();
            playFromLoad();
        }
    }

    // MODIFIES: this
    // EFFECTS: actions associated with the scenario menu
    public void scenarioMenuActions(ActionEvent e) {
        if (e.getSource() == backScenario) {
            backFromScenarios();
        }
        if (e.getSource() == removeButton) {
            try {
                game.removeScenario(Integer.parseInt(removeScen.getText()));
                resetScenarioListing();
            } catch (Exception c) {
                resetScenarioListing();
            }
        }
        if (e.getSource() == addButton) {
            try {
                game.makeScenario(scenName.getText(), Integer.parseInt(scen1.getText()),
                        Integer.parseInt(scen2.getText()), Integer.parseInt(scen3.getText()),
                        Integer.parseInt(scen4.getText()), Integer.parseInt(scen5.getText()),
                        Integer.parseInt(scen6.getText()));
                resetScenarioListing();
            } catch (Exception c) {
                resetScenarioListing();
            }
        }
    }
}
