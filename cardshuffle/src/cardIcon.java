import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.io.File;

public class cardIcon extends JLabel {
    //private JButton btn;
    private ImageIcon icon;
    //Constructor
    public cardIcon() {
        setSize(69,100);
        setVisible(true);

    }

    //Constructor
    //Parameters:
    //Num: card number (1-13)
    //Suit: card suit
    //0- clubs
    //1- diamonds
    //2- hearts
    //3- spades
    public cardIcon(int num, int sym) {
        setSize(69,100);
        setIcon(num, sym);
        setVisible(true);
    }

    public void setIcon(int sym, int num) {
        String number, suit;
        number = toCard(num);
        suit = toSuit(sym);
        File f = new File("cards/"+number+"_of_"+suit+".png");
        try{
            Image img = ImageIO.read(f);
            img = img.getScaledInstance(69,100,Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println("Image read error on " + number + " of " + suit);
            System.out.println("Path: " + f.getAbsolutePath());
            System.out.println(ex);
        }
    }

    private String toSuit(int in) {
        String suit;
        switch(in) {
            case 0: suit="clubs";
                break;
            case 1: suit="diamonds";
                break;
            case 2: suit="hearts";
                break;
            default: suit="spades";
        }
        return suit;
    }
    private String toCard(int in) {
        String number;
        switch(in) {
            case 1: number="ace";
                break;
            case 11: number="jack";
                break;
            case 12: number="queen";
                break;
            case 13: number="king";
                break;
            default: number=Integer.toString(in);
        }
        return number;
    }
}
