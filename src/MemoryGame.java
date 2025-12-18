import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemoryGame extends JFrame implements ActionListener {
    private GameBoard board;
    private JButton[][] buttons;
    private GameStats stats;

    private int rows = 4;
    private int cols = 4;

    private JButton firstButton = null;
    private JButton secondButton = null;
    private boolean lockClicks = false;

    private JLabel movesLabel;
    private JLabel pairsLabel;
    private JLabel timeLabel;
    private JPanel gridPanel;
    private JComboBox<String> difficultyBox;

    private Timer gameTimer;
    private int elapsedSeconds = 0;
    private boolean timerRunning = false;

    public MemoryGame() {
        super("Memory Matching Game");

        this.board = new GameBoard(rows, cols);
        this.buttons = new JButton[rows][cols];
        this.stats = new GameStats();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        movesLabel = new JLabel("Moves: 0");
        pairsLabel = new JLabel("Pairs found: 0");
        timeLabel = new JLabel("Time: 0s");
        topPanel.add(movesLabel);
        topPanel.add(Box.createHorizontalStrut(15));
        topPanel.add(pairsLabel);
        topPanel.add(Box.createHorizontalStrut(15));
        topPanel.add(timeLabel);
        add(topPanel, BorderLayout.NORTH);

        gridPanel = new JPanel();
        add(gridPanel, BorderLayout.CENTER);
        createBoardUI();

        JPanel bottomPanel = new JPanel();
        difficultyBox = new JComboBox<>(new String[]{
                "Easy (2x2)",
                "Medium (4x4)",
                "Hard (6x6)"
        });
        difficultyBox.setSelectedIndex(1);

        JButton resetButton = new JButton("New Game");
        resetButton.addActionListener(e -> resetGame());

        bottomPanel.add(new JLabel("Difficulty: "));
        bottomPanel.add(difficultyBox);
        bottomPanel.add(resetButton);
        add(bottomPanel, BorderLayout.SOUTH);

        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedSeconds++;
                timeLabel.setText("Time: " + elapsedSeconds + "s");
            }
        });

        pack();
        setSize(600, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createBoardUI() {
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(rows, cols, 5, 5));

        buttons = new JButton[rows][cols];
        Font buttonFont = new Font("Segoe UI Emoji", Font.BOLD, 28);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                JButton btn = new JButton("?");
                btn.setFont(buttonFont);
                btn.addActionListener(this);
                btn.putClientProperty("row", r);
                btn.putClientProperty("col", c);
                buttons[r][c] = btn;
                gridPanel.add(btn);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void applySelectedDifficulty() {
        String selected = (String) difficultyBox.getSelectedItem();
        if (selected == null) {
            rows = 4;
            cols = 4;
        } else if (selected.startsWith("Easy")) {
            rows = 2;
            cols = 2;
        } else if (selected.startsWith("Medium")) {
            rows = 4;
            cols = 4;
        } else {
            rows = 6;
            cols = 6;
        }
    }

    private void resetGame() {
        applySelectedDifficulty();
        this.board = new GameBoard(rows, cols);
        this.stats = new GameStats();

        firstButton = null;
        secondButton = null;
        lockClicks = false;

        resetTimer();
        updateLabels();
        createBoardUI();
        pack();
        setSize(600, 700);
    }

    private void updateLabels() {
        movesLabel.setText("Moves: " + stats.getMoves());
        pairsLabel.setText("Pairs found: " + stats.getPairsFound());
    }

    private void startTimerIfNeeded() {
        if (!timerRunning) {
            gameTimer.start();
            timerRunning = true;
        }
    }

    private void stopTimer() {
        if (timerRunning) {
            gameTimer.stop();
            timerRunning = false;
        }
    }

    private void resetTimer() {
        stopTimer();
        elapsedSeconds = 0;
        timeLabel.setText("Time: 0s");
    }

    private String getEmojiForValue(int value) {
        String[] emojis = {
                "😀","😁","😂","🤣","😃","😄","😅","😆","😉",
                "😊","😋","😎","😍","😘","😗","😙","😚","🙂"
        };
        if (value >= 1 && value <= emojis.length) {
            return emojis[value - 1];
        } else {
            return "?";
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (lockClicks) {
            return;
        }

        JButton clicked = (JButton) e.getSource();

        int row = (int) clicked.getClientProperty("row");
        int col = (int) clicked.getClientProperty("col");
        Card card = board.getCard(row, col);

        if (card.isMatched() || clicked == firstButton) {
            return;
        }

        startTimerIfNeeded();
        revealCard(clicked, card);

        if (firstButton == null) {
            firstButton = clicked;
        } else {
            secondButton = clicked;
            stats.incrementMoves();
            updateLabels();
            checkForMatch();
        }
    }

    private void revealCard(JButton button, Card card) {
        button.setText(getEmojiForValue(card.getValue()));
    }

    private void hideCard(JButton button) {
        button.setText("?");
    }

    private void checkForMatch() {
        if (firstButton == null || secondButton == null) {
            return;
        }

        int row1 = (int) firstButton.getClientProperty("row");
        int col1 = (int) firstButton.getClientProperty("col");
        int row2 = (int) secondButton.getClientProperty("row");
        int col2 = (int) secondButton.getClientProperty("col");

        Card card1 = board.getCard(row1, col1);
        Card card2 = board.getCard(row2, col2);

        if (card1.getValue() == card2.getValue()) {
            card1.setMatched(true);
            card2.setMatched(true);
            firstButton.setEnabled(false);
            secondButton.setEnabled(false);
            stats.incrementPairsFound();
            updateLabels();

            firstButton = null;
            secondButton = null;

            if (stats.getPairsFound() == board.getTotalPairs()) {
                stopTimer();
                JOptionPane.showMessageDialog(
                        this,
                        "Congratulations! You finished the game in " + stats.getMoves()
                                + " moves and " + elapsedSeconds + " seconds.",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        } else {
            lockClicks = true;
            Timer flipBackTimer = new Timer(800, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    hideCard(firstButton);
                    hideCard(secondButton);
                    firstButton = null;
                    secondButton = null;
                    lockClicks = false;
                }
            });
            flipBackTimer.setRepeats(false);
            flipBackTimer.start();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MemoryGame::new);
    }
}
