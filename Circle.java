import java.awt.*;

public class Circle extends Rectangle {
    private int radius = 100; // initial size of the circle

    // constructor creates circle at given location with given dimensions
    public Circle(int centerX, int centerY) {
        super(centerX - 100 / 2, centerY - 100 / 2, 100, 100);
    }

    // draws the current location of the circle to the screen
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(x, y, radius, radius);

        g.setColor(Color.gray);
        g.fillOval(x + 10, y + 10, radius - 20, radius - 20);
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
        this.y = centerY ;
    }
}
