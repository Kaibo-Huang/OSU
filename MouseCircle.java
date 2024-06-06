//Don Tran and Kaibo Huang
//June 11, 2024
//This class creates an "approach circle" around the note or slider, in order to provide a "countdown" for the user to click the note or slider with a perfect timing. 

import java.awt.*;

public class MouseCircle extends Rectangle {
    public int radius = 200; // initial size of the circle
    public static final int MAX_RADIUS = 200; // Maximum radius size
    public static final int MIN_RADIUS = 60;  // Minimum radius size

    // constructor creates circle at given location with given dimensions
    public MouseCircle(int centerX, int centerY) {
        super(centerX - 200 / 2, centerY - 200 / 2, 200, 200);
    }

    // draws the current location of the circle to the screen
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillOval(x, y, radius, radius);
        
        g.setColor(Color.white);
        g.fillOval(x + 5, y + 5, radius - 10, radius - 10);
    }

    // method to set the radius and update the rectangle bounds
    public void setRadius(int r) {
        radius = r;
        
    }

    // method to get the radius
    public int getRadius() {
        return radius;
    }

    // method to update the position based on the center
    public void setPosition(int centerX, int centerY) {
        this.x = centerX;
        this.y = centerY;
    }
}
