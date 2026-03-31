import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

// Custom panel for background image
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String path) {
        backgroundImage = new ImageIcon(path).getImage();
        setLayout(new GridLayout(3, 3, 10, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

public class WhackAMole {

    JFrame frame;
    JButton[] buttons = new JButton[9];
    JLabel scoreLabel, timerLabel;

    int score = 0;
    int timeLeft = 30;
    int currentMole = -1;

    Random rand = new Random();

    // Load mole image
    ImageIcon moleIcon = new ImageIcon("images/mole1.png");

    public WhackAMole() {

        // Frame setup
        frame = new JFrame("Whack-A-Mole");
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        // Score Label
        scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.BLUE);
        frame.add(scoreLabel, BorderLayout.NORTH);

        // Background Panel
        JPanel panel = new BackgroundPanel("images/background.jpg.jpg");

        // Create buttons
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            int index = i;

            // Transparent buttons
            buttons[i].setContentAreaFilled(false);
            buttons[i].setBorderPainted(false);
            buttons[i].setFocusPainted(false);
            buttons[i].setOpaque(false);

            // Click logic
            buttons[i].addActionListener(e -> {
                if (index == currentMole) {
                    score++;
                    scoreLabel.setText("Score: " + score);
                    buttons[index].setIcon(null);
                    currentMole = -1;
                }
            });

            panel.add(buttons[i]);
        }

        frame.add(panel, BorderLayout.CENTER);

        // Timer Label
        timerLabel = new JLabel("Time: 30", JLabel.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timerLabel.setForeground(Color.RED);
        frame.add(timerLabel, BorderLayout.SOUTH);

        // Mole spawn timer
        Timer moleTimer = new Timer(800, e -> spawnMole());
        moleTimer.start();

        // Game timer
        Timer gameTimer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft);

            if (timeLeft == 0) {
                ((Timer) e.getSource()).stop();
                moleTimer.stop();

                JOptionPane.showMessageDialog(frame, "Game Over! Score: " + score);
                System.exit(0);
            }
        });
        gameTimer.start();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Spawn mole randomly
    void spawnMole() {
        if (currentMole != -1) {
            buttons[currentMole].setIcon(null);
        }

        currentMole = rand.nextInt(9);
        buttons[currentMole].setIcon(moleIcon);
    }

    public static void main(String[] args) {
        new WhackAMole();
    }
}