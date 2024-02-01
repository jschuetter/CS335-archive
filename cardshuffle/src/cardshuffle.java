import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;


public class cardshuffle {
    private static cardIcon cards[][] = new cardIcon[4][13];
    //private static cardIcon cardLst[] = new cardIcon[52];
    private static JFrame frame = new JFrame("Shuffle cards!");
    private static JPanel cardPne = new JPanel(new GridLayout(4,13,3,3));
    public static void main(String[] args) {
        //JFrame frame = new JFrame("Shuffle Cards!");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setSize(1000,500);

        //Populate cardPne
        for (int i = 0; i <= 3; i++) {
            for (int j = 1; j <= 13; j++) {
                cards[i][j-1] = new cardIcon(i,j);
                cardPne.add(cards[i][j-1]);
            }
        }
        frame.add(cardPne);

        JPanel controlPne = new JPanel();
        JButton shuffleBtn, resetBtn, quitBtn;
        shuffleBtn = new JButton("Shuffle");
        shuffleBtn.addActionListener(shuffleCards);
        controlPne.add(shuffleBtn);

        resetBtn = new JButton("Reset");
        resetBtn.addActionListener(resetCards);
        controlPne.add(resetBtn);

        quitBtn = new JButton("Quit");
        quitBtn.addActionListener(quitProg);
        controlPne.add(quitBtn);

        frame.add(controlPne);
        frame.setVisible(true);
    }

    static AbstractAction shuffleCards = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Shuffling");
            //Initialize deck array to ensure no duplicates
            //Array contains {suit,number} pairs
            ArrayList<int[]> deck = new ArrayList<int[]>();
            for (int i = 0; i <=3; i++) {
                for (int j = 1; j <= 13; j++) {
                    deck.add(new int[] {i,j});
                }
            }

            //Fill cardPne with cards in random order
            //cardPne.removeAll();
            //frame.repaint();
            Random r = new Random();
            for (int i = 0; i <= 3; i++) {
                for (int j = 1; j <= 13; j++) {
                    int nextIndex = r.nextInt(deck.size());
                    int[] v = deck.get(nextIndex);
                    deck.remove(nextIndex);
                    cards[i][j-1].setIcon(v[0],v[1]);
                }
            }
            frame.repaint();
        }
    };

    static AbstractAction resetCards = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i <= 3; i++) {
                for (int j = 1; j <= 13; j++) {
                    cards[i][j-1].setIcon(i, j);
                }
            }
            frame.repaint();
        }
    };

    static AbstractAction quitProg = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };
}
