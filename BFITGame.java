
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class BFITGame extends JFrame implements ActionListener {
    private CardLayout cardLayout;
    private JPanel mainPanel, gamePanel;
    private ArrayList<Integer> sequence;
    private int currentStep, score;
    private JLabel promptLabel, scoreLabel;
    private JButton[] numberButtons;
    private int difficulty = 3; // Default difficulty

    public BFITGame() {
        setTitle("BFIT - A Cognitive Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Menu Screen
        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, "Menu");

        // Game Screen
        gamePanel = createGamePanel();
        mainPanel.add(gamePanel, "Game");

        add(mainPanel);
        cardLayout.show(mainPanel, "Menu");
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel titleLabel = new JLabel("BFIT - A Cognitive Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        menuPanel.add(titleLabel);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(this);
        menuPanel.add(startButton);

        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(this);
        menuPanel.add(settingsButton);

        JButton leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.addActionListener(this);
        menuPanel.add(leaderboardButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        menuPanel.add(exitButton);

        return menuPanel;
    }

    private JPanel createGamePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        promptLabel = new JLabel("Memorize the sequence!", SwingConstants.CENTER);
        promptLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(promptLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        numberButtons = new JButton[6];
        for (int i = 0; i < 6; i++) {
            numberButtons[i] = new JButton(String.valueOf(i + 1));
            numberButtons[i].addActionListener(this);
            buttonPanel.add(numberButtons[i]);
        }
        panel.add(buttonPanel, BorderLayout.CENTER);

        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(scoreLabel, BorderLayout.SOUTH);

        return panel;
    }

    private void startGame() {
        score = 0;
        sequence = new ArrayList<>();
        for (int i = 0; i < difficulty; i++) {
            sequence.add((int) (Math.random() * 6 + 1));
        }
        currentStep = 0;
        showSequence();
    }

    private void showSequence() {
        promptLabel.setText("Memorize this: " + sequence.toString());
        Timer timer = new Timer(3000, e -> {
            promptLabel.setText("Your turn!");
            for (JButton button : numberButtons) button.setEnabled(true);
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void checkInput(int number) {
        if (number == sequence.get(currentStep)) {
            currentStep++;
            if (currentStep == sequence.size()) {
                score++;
                scoreLabel.setText("Score: " + score);
                difficulty++;
                startGame();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Wrong! Game Over! Your score: " + score);
            cardLayout.show(mainPanel, "Menu");
        }
    }

    private void showSettings() {
        String[] options = {"3", "4", "5", "6"};
        String choice = (String) JOptionPane.showInputDialog(this, "Select Difficulty:", "Settings",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice != null) {
            difficulty = Integer.parseInt(choice);
        }
    }

    private void showLeaderboard() {
        JOptionPane.showMessageDialog(this, "Leaderboard: Coming soon!");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Start Game")) {
            cardLayout.show(mainPanel, "Game");
            startGame();
        } else if (command.equals("Settings")) {
            showSettings();
        } else if (command.equals("Leaderboard")) {
            showLeaderboard();
        } else {
            for (int i = 0; i < numberButtons.length; i++) {
                if (e.getSource() == numberButtons[i]) {
                    checkInput(i + 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BFITGame game = new BFITGame();
            game.setVisible(true);
        });
    }
}
