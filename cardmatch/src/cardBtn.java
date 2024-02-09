import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Objects;

public class cardBtn extends JButton implements MouseListener {
    private ImageIcon face, back; //Icon objects to store front and back images - declared at construction
    final private int WIDTH = 69, HEIGHT = 100;
    private boolean faceUp = false; //Boolean value - true when card is facing up
    //private ImageIcon back = new ImageIcon("cards/back.png");

    //Default constructor - no parameters
    public cardBtn() {
        setSize(WIDTH,HEIGHT);
        setVisible(true);
        addMouseListener(this);
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
        face = new ImageIcon(Objects.requireNonNull(getImg(sym, num)));
        back = new ImageIcon(Objects.requireNonNull(getImg(-1, -1)));
        flipCard(!faceUp); //Invert boolean value to set default position
        setVisible(true);
        addMouseListener(this);
        //repaint();
    }
    //Overloaded constructor - includes boolean parameter to allow initializing face-up
    public cardBtn(int sym, int num, boolean facing) {
        setSize(WIDTH,HEIGHT);
        face = new ImageIcon(Objects.requireNonNull(getImg(sym, num)));
        back = new ImageIcon(Objects.requireNonNull(getImg(-1, -1)));
        flipCard(!facing);
        setVisible(true);
        addMouseListener(this);
        //repaint();
    }

    //Flip card on mouse click
    @Override
    public void mouseClicked(MouseEvent e) {
        flipCard();
    }

    //Sets the face of the card to a card icon
    public void setCard(int sym, int num) {
        face = new ImageIcon(Objects.requireNonNull(getImg(sym, num)));
        if (faceUp) {
            setIcon(face);
            //repaint();
        }
    }

    //Flips the card to the opposite state:
    //Face-down if currently face-up or vice versa
    private void flipCard() {
        //System.out.println("Flip");
        if (faceUp) {
            setIcon(back);
            faceUp = false;
        }
        else {
            setIcon(face);
            faceUp = true;
        }
        //repaint();
    }
    //Overload of flipCard to allow explicit facing
    //Sets face up if facing=true
    private void flipCard(boolean facing) {
        //System.out.println("Flip");
        if (facing) {
            setIcon(back);
            faceUp = false;
        }
        else {
            setIcon(face);
            faceUp = true;
        }
        //repaint();
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


    /*public void setIcon(int sym, int num) {
        File f = new File(getImgPath(sym, num));
        try{
            Image img = ImageIO.read(f);
            img = img.getScaledInstance(69,100,Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println("Image read error on " + toCard(num) + " of " + toSuit(sym));
            System.out.println("Path: " + f.getAbsolutePath());
            System.out.println(ex);
        }
    }*/

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
}