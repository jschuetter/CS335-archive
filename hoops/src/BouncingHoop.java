import java.awt.*;
import javax.swing.*;

public class BouncingHoop extends JPanel
{
    private int delay = 10;
    //AnimCurve curve = new AnimCurve();
    AnimHoop hoop;
    int [][] coords;

    BouncingBalls pts;

    //Take BouncingBall object for points
    public BouncingHoop(BouncingBalls ptsIn, Dimension drawSpace)
    {
        pts = ptsIn;
        hoop = new AnimHoop(drawSpace);
        setOpaque(false);
        this.add(hoop);

        //Constant animation
        new Thread(hoop).start();
    }

    /* Hoop object */
    public class AnimHoop extends JComponent implements Runnable
    {
        Bspline[] curves = new Bspline[pts.num];

        public AnimHoop(Dimension drawSpace) {
            this.setPreferredSize(drawSpace);
            for (int i = 0; i < pts.num; i++) {
                curves[i] = new Bspline();
            }
            curves[0].setColor(Color.RED);
            curves[1].setColor(Color.BLUE);
            curves[2].setColor(Color.GREEN);
            curves[3].setColor(Color.YELLOW);
        }
        public void run() {
            while (true) {
                coords = pts.getCoords();
                for (int i = 0; i < pts.num; i++) {
                    curves[i].resetCurve();
                    for (int j = 0; j < pts.num; j++) {
                        int ptOffset = (j+i+2) % pts.num;
                        curves[i].addPoint(coords[ptOffset][0], coords[ptOffset][1]);
                    }
                }

                repaint();
                try {
                    Thread.sleep(pts.delay);
                } catch (InterruptedException e) { }
            }
        }
        public void paint(Graphics g) {
            /* we always want the curve */
            for (int i = 0; i < pts.num; i++) curves[i].paintCurve(g);
        }
    }
}
