import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.io.File;
import java.util.Objects;

public class cardBtn extends JButton {
    private ImageIcon face, back; //Icon objects to store front and back images - declared at construction
    public String suit, val; //Stores current values of card face
    final private int WIDTH = 69, HEIGHT = 100;
    private boolean faceUp = false; //Boolean value - true when card is facing up
    public boolean en = true; //Enable bit - allows card flipping and guess increment when true

    //Default constructor - no parameters
    public cardBtn() {
        setSize(WIDTH,HEIGHT);
        face = null;
        back = new ImageIcon(Objects.requireNonNull(getImg(-1,-1)));
        flipCard(faceUp);
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
    //Pair (-1,-1) indicates card back image
    public cardBtn(int sym, int num) {
        setSize(WIDTH,HEIGHT);
        suit = toSuit(sym);
        val = toCard(num);
        face = new ImageIcon(Objects.requireNonNull(getImg(sym, num)));
        back = new ImageIcon(Objects.requireNonNull(getImg(-1, -1)));
        flipCard(faceUp); //Invert boolean value to set default position
        setVisible(true);
    }
    //Overloaded constructor - includes boolean parameter to allow initializing face-up
    public cardBtn(int sym, int num, boolean facing) {
        setSize(WIDTH,HEIGHT);
        suit = toSuit(sym);
        val = toCard(num);
        face = new ImageIcon(Objects.requireNonNull(getImg(sym, num)));
        back = new ImageIcon(Objects.requireNonNull(getImg(-1, -1)));
        flipCard(facing);
        setVisible(true);
    }

    //Sets the face of the card to a card icon
    public void setCard(int sym, int num) {
        suit = toSuit(sym);
        val = toCard(num);
        face = new ImageIcon(Objects.requireNonNull(getImg(sym, num)));
        if (faceUp) {
            setIcon(face);
        } else setIcon(back);
    }

    //Flips the card to the opposite state:
    //Face-down if currently face-up or vice versa
    public void flipCard() {
        if (faceUp) {
            setIcon(back);
            faceUp = false;
        } else {
            setIcon(face);
            faceUp = true;
        }
    }
    //Overload of flipCard to allow explicit facing
    //Sets face up if facing=true
    public void flipCard(boolean facing) {
        if (facing) {
            setIcon(face);
            faceUp = true;
        } else {
            setIcon(back);
            faceUp = false;
        }
    }


    //Returns image corresponding to suit and number values provided
    //See constructor comment for acceptable parameters
    private Image getImg(int sym, int num) {
        String number, suit;
        number = toCard(num);
        suit = toSuit(sym);
        File f = new File("cards/"+number+"_of_"+suit+".png");
        try{
            Image img = ImageIO.read(f);
            img = img.getScaledInstance(WIDTH,HEIGHT,Image.SCALE_SMOOTH);
            return img;
        } catch (Exception ex) {
            System.out.println("Image read error on " + toCard(num) + " of " + toSuit(sym));
            System.out.println("Path: " + f.getAbsolutePath());
            System.out.println(ex);
        }
        return null;
    }

    //Converts integer value to suit string to allow file reading
    private String toSuit(int in) {
        String suit;
        switch(in) {
            case -1: suit ="card";
                break;
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
    //Converts integer value to number string (for ace or face cards) to allow file reading
    private String toCard(int in) {
        String number;
        switch(in) {
            case -1: number="back";
                break;
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