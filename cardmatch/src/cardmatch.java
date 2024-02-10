import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static java.lang.Thread.sleep;

public class cardmatch {
    //Frame objects
    private static final cardBtn[][] cards = new cardBtn[4][13];
    private static final JFrame frame = new JFrame("Match cards!");
    private static final JPanel cardPne = new JPanel(new GridLayout(4,13,3,3));
    private static final JPanel controlPne = new JPanel();
    private static final int MISS_DELAY = 3000; //Delay on missed guess, in ms

    private static cardBtn firstPick = null, secondPick = null; //Stores the first card picked by user

    //Game UI objects
    private static int guessCnt = 0, matchCnt = 0;
    private static JButton resetBtn, quitBtn;
    private static JLabel guessLbl, scoreLbl, gameOverLbl;

    private static boolean interactive = true; // Used to disable user interaction with cards while showing non-match

    public static void main(String[] args) {
        //JFrame frame = new JFrame("Shuffle Cards!");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setSize(1000,550);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Populate cardPne
        for (int i = 0; i <= 3; i++) {
            for (int j = 1; j <= 13; j++) {
                //cards[i][j-1] = new cardBtn(i,j);
                cards[i][j-1] = new cardBtn();
                cards[i][j-1].addMouseListener(cardClicked);
                cardPne.add(cards[i][j-1]);
            }
        }

        //System.out.println("Finished init");
        frame.add(cardPne);

        guessLbl = new JLabel("Guesses made: " + guessCnt);
        controlPne.add(guessLbl);

        resetBtn = new JButton("Reset");
        resetBtn.addActionListener(startNewGame);
        controlPne.add(resetBtn);

        quitBtn = new JButton("Quit");
        quitBtn.addActionListener(quitProg);
        controlPne.add(quitBtn);

        scoreLbl = new JLabel("Score: " + matchCnt);
        controlPne.add(scoreLbl);

        frame.add(controlPne);

        gameOverLbl = new JLabel("Game over. You win!");
        gameOverLbl.setVisible(false);
        frame.add(gameOverLbl);

        frame.setVisible(true);

        //Assign cards for new game
        newGame();
    }

    //Updates labels
    private static void updateLbls() {
        guessLbl.setText("Guesses made: " + guessCnt);
        scoreLbl.setText("Score: " + matchCnt);
    }

    static AbstractAction startNewGame = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            newGame();
        }
    };

    private static void newGame() {
        //Initialize deck array to ensure no duplicates
        //Array contains {suit,number} pairs
        ArrayList<int[]> deck = new ArrayList<int[]>();
        for (int i = 0; i <=3; i++) {
            for (int j = 1; j <= 13; j++) {
                deck.add(new int[] {i,j});
            }
        }

        //Fill cardPne with cards in random order
        Random r = new Random();
        for (int i = 0; i <= 3; i++) {
            for (int j = 1; j <= 13; j++) {
                int nextIndex = r.nextInt(deck.size());
                int[] v = deck.get(nextIndex);
                deck.remove(nextIndex);
                cards[i][j-1].setCard(v[0],v[1]);
                cards[i][j-1].flipCard(false);
                cards[i][j-1].setEnabled(true);
            }
        }
        guessCnt = 0;
        matchCnt = 0;
        updateLbls();
        frame.repaint();
    }
    
    static MouseListener cardClicked = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            //Only enabled if var interactive = true
            if (interactive) {
                if (firstPick != null) {
                    secondPick = (cardBtn) e.getSource();
                    secondPick.flipCard();
                    if (Objects.equals(firstPick.val, secondPick.val)) {
                        //If the cards match, increment score and disable interaction
                        matchCnt++;
                        firstPick.setEnabled(false);
                        secondPick.setEnabled(false);
                        firstPick = null;

                        //Check for game over
                        if (matchCnt >= 26) gameOverLbl.setVisible(true);
                    } else {
                        //If guess is wrong, wait 3s then flip cards back
                        //Uses thread to allow second card to flip before interrupted by sleep() command
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                interactive = false;
                                System.out.println("Interaction disabled");
                                try {
                                    sleep(MISS_DELAY);
                                } catch (InterruptedException ex) {
                                    throw new RuntimeException(ex);
                                }

                                firstPick.flipCard();
                                secondPick.flipCard();
                                interactive = true;
                                firstPick = null;
                                System.out.println("Interaction re-enabled");
                            }
                        }).start();
                    }
                    guessCnt++;
                } else {
                    //If this is the first guess, store the card clicked
                    firstPick = (cardBtn) e.getSource();
                    firstPick.flipCard();
                }
                updateLbls();
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {
        }
        @Override
        public void mouseReleased(MouseEvent e) {
        }
        @Override
        public void mouseEntered(MouseEvent e) {
        }
        @Override
        public void mouseExited(MouseEvent e) {
        }
    };

    static AbstractAction quitProg = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };
}
