
import java.awt.*;
import javax.swing.*;
 
public class Ball {
  // Box height and width
  private int width;
  private int height;
 
  // Ball Size
  private float radius = 10; 
  private float diameter = radius * 2;

  // Center of ball
  public float X = radius + 20;
  public float Y = radius + 20;

  // Color of ball
  private Color ballColor;
 
  // Direction
  private float dx = 3;
  private float dy = 3;

  //Speed multiplier
  private float speed = 1;

  // Constructor sets default speed (dx, dy) and color (blue)
  // Still need the width/height
  public Ball(int w, int h) {
    this.width = w;
    this.height = h;
    this.ballColor = Color.BLUE;
  }

  // Constructor to set custom speed and color
  public Ball(int w, int h, float indx, float indy, float spd, Color c) {
    this.width = w;
    this.height = h;
    this.dx = indx;
    this.dy = indy;
    this.speed = speed;
    this.ballColor = c;
  }

  // Make a step and change the ball parameters
  public int[] moveBall () {

    // Update the X and Y coordinates
    X = X + dx*speed;
    Y = Y + dy*speed;
 
    // Check boundaries and if necessary update the
    // x component of the direction vector, dx
    if ((X - radius) < 0) {
      dx = -dx; 
      X = radius; 
    } 
    else if ((X + radius) > width) {
             dx = -dx;
             X = width - radius;
    }
 
    // Check boundaries and if necessary update the
    // y component of the direction vector, dy
    if ((Y - radius) < 0) {
      dy = -dy;
      Y = radius;
    } 
    else if ((Y + radius) > height) {
             dy = -dy;
             Y = height - radius;
    }
    int[] coords = {(int)X,(int)Y};
    return coords;
  }
 
  // Accessors
  // Might also want mutators here (i.e., setX())
  // so that the starting point of a ball can be randomized
  public int getX () {
    return (int)X;
  }

  public int getY () {
    return (int)Y;
  }

  public Color getColor () {
    return ballColor;
  }

  public void setColor (Color c) {
    this.ballColor = c;
  }

  public void setSpeed(float spd) {
    this.speed=spd;
  }
 
  // Draw into a provided graphics context
  public void paintBall (Graphics g) {
    g.setColor(ballColor);
    g.fillOval((int)(X-radius), (int)(Y-radius), (int)diameter, (int)diameter);
  }
}
