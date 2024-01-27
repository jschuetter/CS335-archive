import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.Date;

public class PrimeCalculator implements ActionListener {
    private JButton enterBtn, saveBtn, resetBtn;
    private JTextField inTxt;
    private JTextArea outTxt;
    final String outFile = "primeCalcOutput.txt";

    PrimeCalculator() {
        JFrame frame = new JFrame("Prime Calculator");
        Container pane = frame.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        frame.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        JLabel inLbl = new JLabel("Enter a number: ");
        inLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        inLbl.setFont(new Font("SansSerif", Font.PLAIN, 24));
        pane.add(inLbl);
        //inLbl.setBounds(20,20,360,30);

        inTxt = new JTextField();
        //inTxt.setBounds(20,50,360,30);
        inTxt.setEditable(true);
        //inTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        inTxt.setHorizontalAlignment(SwingConstants.RIGHT);
        inTxt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150)); //Constrain text field height
        inTxt.setFont(new Font("SansSerif", Font.BOLD, 24));
        inTxt.addActionListener(this);
        pane.add(inTxt);

        enterBtn = new JButton("Check Primality");
        enterBtn.addActionListener(this);
        enterBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        pane.add(enterBtn);

        outTxt = new JTextArea();
        outTxt.setEditable(false);
        outTxt.setFont(new Font("Monospaced", Font.BOLD, 18));
        outTxt.setAlignmentX(Component.LEFT_ALIGNMENT);
        outTxt.setBorder(BorderFactory.createEmptyBorder(2,5,10,5));
        outTxt.setLineWrap(true); outTxt.setWrapStyleWord(true);  //Set text wrapping
        pane.add(outTxt);

        JPanel savePnl = new JPanel();
        savePnl.setLayout(new FlowLayout());
        savePnl.setAlignmentX(Panel.LEFT_ALIGNMENT);
        saveBtn = new JButton("Save output");
        saveBtn.addActionListener(saveOutput);
        //saveBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);

        resetBtn = new JButton("Reset output file");
        resetBtn.addActionListener(resetOutFile);

        savePnl.add(saveBtn); savePnl.add(resetBtn);
        frame.add(savePnl);

        //frame.add(titleLbl); frame.add(inLbl); frame.add(inTxt); frame.add(outTxt); frame.add(btn);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLayout(null);
        frame.setSize(300,400);
        frame.setVisible(true);
    }

    //Write output to text file when saveBtn is clicked
    Action saveOutput = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Try to open file or create if it does not exist
            try {
                FileWriter fOut = new FileWriter(outFile, true);
                fOut.write(outTxt.getText());
                //Get current date & append to entry
                Date now = new Date();
                fOut.write(" - " + now + " \n");
                fOut.close();
                outTxt.append("\nOuptut saved to " + outFile);
            } catch (Exception badFile) {
                outTxt.append("\nError saving output");
            }
        }
    };

    //Clear output file when rstBtn is clicked
    Action resetOutFile = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new FileWriter(outFile, false).close();
                outTxt.setText("Output file cleared");
            } catch (Exception badFile) {
                outTxt.append("\nError clearing output file");
            }
        }
    };

    //Call isPrime function on input of inTxt JTextField
        public void actionPerformed(ActionEvent e) {
            //Input validation
            String result;
            try {
                int num = Integer.parseInt(inTxt.getText());
                boolean prime = isPrime(num);

                if (prime) result = Integer.toString(num) + " is prime!";
                else result = Integer.toString(num) + " is not prime.";
            } catch (Exception ex) {
                result = "Invalid input.";
            }

            outTxt.setText(result);
        }

    public static void main(String[] args) {
        new PrimeCalculator();
    }

    //Checks primality of an integer argument
    //Returns boolean
    static boolean isPrime (int num){
        //Check all numbers up to half the argument for common factors
        for (int i = 2; i <= num/2; i++) {  //Check this condition - i<=num/2??
            if (num%i==0) return false;
        }

        return true; //Return true if no factor is found
    }
}


