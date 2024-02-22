
import java.awt.*;
import javax.swing.*;

public class BouncingBalls extends JPanel {
    public int num = 4;
    public Ball[] b = new Ball[num];
    public int[][] coords = new int[num][2];
    public static int delay=10;
    private float speed=1;
    private double baseSpeed = 10.0;
    public Color color = new Color(70, 130, 180); //Steel blue

    public Dimension animSpace;
    public BouncingBalls(Dimension d) {
        this.setOpaque(false);
        this.animSpace = d;

        // Set up the bouncing ball with random speeds and colors
        for (int i = 0; i < num; i++) {
            b[i] = new Ball(d.width, d.height,
                    (float) (baseSpeed * Math.random()),
                    (float) (baseSpeed * Math.random()),
                    speed, color);
        }
        // Create a thread to update the animation and repaint
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    // Ask the ball to move itself and then repaint
                    for (int i = 0; i < num; i++) coords[i] = b[i].moveBall();
                    repaint();
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException ex) { }
                }
            }
        };
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the ball
        for (int i = 0; i < num; i++) b[i].paintBall(g);
    }

    public int[][] getCoords() {
        return coords;
    }

    public void setSpeed(int newSpeed) {
        float speedPct = (float) newSpeed / 100;
        for (int i = 0; i < num; i++) b[i].setSpeed(speedPct);
    }
}
