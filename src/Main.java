import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    JFrame displayZoneFrame;

    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    private JLabel timerLabel; // Label to display the timer
    private int timeRemaining = 20; // Countdown timer in seconds (changed to 20 seconds)

    private JProgressBar healthBar; // Health bar
    private int health = 100; // Initial health

    public Main() throws Exception {
        // Create the main game window
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(400, 600);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create the start screen panel
        JPanel startScreen = new JPanel();
        startScreen.setLayout(new BorderLayout());
        JLabel title = new JLabel("Welcome to Dungeon Crawler", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        startScreen.add(title, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setFocusable(false);
        startScreen.add(startButton, BorderLayout.SOUTH);

        // Show the start screen first
        displayZoneFrame.getContentPane().add(startScreen);
        displayZoneFrame.setVisible(true);

        // Action to switch to the game screen
        startButton.addActionListener(e -> {
            try {
                startGame();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void startGame() throws Exception {
        // Reset the timer and health values
        timeRemaining = 20; // Reset timer to 20 seconds (changed)
        health = 100; // Reset health to 100

        // Create hero sprite
        DynamicSprite hero = new DynamicSprite(200, 300,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50);

        // Initialize engines
        renderEngine = new RenderEngine(displayZoneFrame);
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero);

        // Timers for updating the game
        Timer renderTimer = new Timer(50, (time) -> renderEngine.update());
        Timer gameTimer = new Timer(50, (time) -> gameEngine.update());
        Timer physicTimer = new Timer(50, (time) -> physicEngine.update());

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        // Set up the game components
        Playground level = new Playground("./data/level1.txt");
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList());

        // Create a timer label
        timerLabel = new JLabel("Timer: " + timeRemaining + " seconds");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create a health bar
        healthBar = new JProgressBar(0, 100);
        healthBar.setValue(health);
        healthBar.setStringPainted(true);
        healthBar.setForeground(Color.GREEN);
        healthBar.setBackground(Color.GRAY);

        // Countdown timer
        Timer countdownTimer = new Timer(1000, e -> updateTimer());
        countdownTimer.start();

        // Replace the start screen with the game screen
        JPanel gamePanel = new JPanel(new BorderLayout());
        gamePanel.add(renderEngine, BorderLayout.CENTER);
        gamePanel.add(timerLabel, BorderLayout.NORTH);
        gamePanel.add(healthBar, BorderLayout.SOUTH);

        displayZoneFrame.getContentPane().removeAll();
        displayZoneFrame.getContentPane().add(gamePanel);
        displayZoneFrame.revalidate();
        displayZoneFrame.repaint();

        // Add key listener for game controls
        displayZoneFrame.addKeyListener(gameEngine);
    }

    private void updateTimer() {
        if (timeRemaining > 0) {
            timeRemaining--;
            timerLabel.setText("Time Remaining: " + timeRemaining + " seconds");
            if (timeRemaining % 5 == 0) {  // Every 5 seconds (changed from every 10 seconds)
                health -= 25;  // Decrease health by 25
                updateHealthBar();  // Update the health bar
            }
        } else {
            timerLabel.setText("Time's Up!");
            levelComplete();
        }
    }

    private void levelComplete() {
        // Handle level completion and show end screen
        JOptionPane.showMessageDialog(displayZoneFrame, "Time's Up!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        int choice = JOptionPane.showOptionDialog(displayZoneFrame, "Do you want to restart?", "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (choice == JOptionPane.YES_OPTION) {
            try {
                // Restart the game and load level1
                startGame();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.exit(0);  // Exit the game
        }
    }

    private void updateHealthBar() {
        // Update the health bar based on the current health value
        healthBar.setValue(health);
        if (health > 50) {
            healthBar.setForeground(Color.GREEN);  // Green if health > 50
        } else if (health > 25) {
            healthBar.setForeground(Color.YELLOW); // Yellow if health between 25 and 50
        } else {
            healthBar.setForeground(Color.RED);    // Red if health < 25
        }
        healthBar.repaint(); // Ensure the health bar is re-rendered
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
    }
}
