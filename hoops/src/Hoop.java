import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Hoop extends JFrame{
    BouncingBalls ballPnl; BouncingHoop hoopPnl;
    JToggleButton pointsBtn, hoopBtn;
    JSlider speedSlider;

    private static int F_WIDTH = 800;
    private static int F_HEIGHT = 500;
    private static Dimension ANIM_SIZE = new Dimension(700, 400);

    //private static int NUM_BALLS = 4;
    //private Ball[] ball = new Ball[NUM_BALLS];
    //private static double ANIM_SPEED = 10.0;
    //private static int ANIM_DELAY = 10;

    public Hoop (){
        super("Bouncing Hoop");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(F_WIDTH, F_HEIGHT);

        JPanel contentPne = new JPanel();
        this.setContentPane(contentPne);

        JLayeredPane animPne = new JLayeredPane();
        animPne.setPreferredSize(ANIM_SIZE);
        animPne.setMaximumSize(ANIM_SIZE);
        animPne.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));

        ballPnl = new BouncingBalls(ANIM_SIZE);
        ballPnl.setPreferredSize(ANIM_SIZE);
        ballPnl.setMaximumSize(ANIM_SIZE);
        ballPnl.setBounds(0,0,ANIM_SIZE.width, ANIM_SIZE.height);
        animPne.add(ballPnl);

        hoopPnl = new BouncingHoop(ballPnl, ANIM_SIZE);
        hoopPnl.setPreferredSize(ANIM_SIZE);
        hoopPnl.setMaximumSize(ANIM_SIZE);
        hoopPnl.setBounds(0,0, ANIM_SIZE.width, ANIM_SIZE.height);
        animPne.add(hoopPnl, 1);

        contentPne.add(animPne, BorderLayout.CENTER);

        JPanel controlPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pointsBtn = new JToggleButton("Toggle points");
        pointsBtn.addActionListener(togglePoints);
        hoopBtn = new JToggleButton("Toggle hoop");
        hoopBtn.addActionListener(toggleHoop);
        controlPnl.add(pointsBtn); controlPnl.add(hoopBtn);

        speedSlider = new JSlider();
        speedSlider.addChangeListener(changeSpeed);
        speedSlider.setMinimum(1);
        speedSlider.setMaximum(100);
        controlPnl.add(speedSlider);

        contentPne.add(controlPnl, BorderLayout.NORTH);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Hoop();

    }

    ActionListener togglePoints = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ballPnl.isVisible()) ballPnl.setVisible(false);
            else ballPnl.setVisible(true);
        }
    };
    ActionListener toggleHoop = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (hoopPnl.isVisible()) hoopPnl.setVisible(false);
            else hoopPnl.setVisible(true);
        }
    };
    ChangeListener changeSpeed = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            ballPnl.setSpeed(speedSlider.getValue());
        }
    };
}
