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
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        Stroke oldStroke = g2d.getStroke(); // Save the old stroke
        // Set the thickness of the outline
        g2d.setStroke(new BasicStroke(5)); // Change the thickness as needed
        g2d.drawOval(x, y, radius, radius);
        g2d.setStroke(oldStroke); // Restore the old stroke
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
