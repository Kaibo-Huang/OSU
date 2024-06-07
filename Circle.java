//Don Tran and Kaibo Huang
//June 11, 2024
//This class creates circle shapes called "notes", which will be the target area for the user to click them with the correct timing. 

import java.awt.*;
import java.awt.event.*;

public class Circle extends Rectangle{
    private int radius = 100; // initial size of the circle
    public int moveRadius = 200; // initial size of the circle
    public static final int MAX_RADIUS = 200; // Maximum radius size
    public static final int MIN_RADIUS = 80;  // Minimum radius size
    int initialX, initialY;
    int x0, y0;
    int id;
    
    boolean isClicked = false;

    // constructor creates circle at given location with given dimensions
    public Circle(int centerX, int centerY, int i) {
        super(centerX - 100 / 2, centerY - 100 / 2, 100, 100);
        x0 =centerX; 
        y0 = centerY;
        initialX = x0- 100 / 2;
        initialY = y0- 100 / 2;
        id = i;
       
    }

    // draws the current location of the circle to the screen
    public void draw(Graphics g) {
    	isClicked = false;
        g.setColor(Color.gray);
        g.fillOval(initialX + 10, initialY + 10, radius - 20, radius - 20);
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        Stroke oldStroke = g2d.getStroke(); // Save the old stroke
        // Set the thickness of the outline
        g2d.setStroke(new BasicStroke(3)); // Change the thickness as needed
        g2d.drawOval(initialX, initialY, radius, radius);
        
        
        g2d.setStroke(new BasicStroke(5)); // Change the thickness as needed
        g2d.drawOval(x, y, moveRadius, moveRadius);
        g2d.setStroke(oldStroke); // Restore the old stroke
        
        
    }

    // method to set the radius and update the rectangle bounds
    public void setRadius(int r) {
    	moveRadius = r;
    }

    // method to get the radius
    public int getRadius() {
        return moveRadius;
    }

    // method to update the position based on the center
    public void setPosition(int centerX, int centerY) {
        this.x = centerX;
        this.y = centerY;
    }

    // method to check if the mouse click is within the circle
    public boolean isMouseClickedInside(int mouseX, int mouseY) {
        int circleCenterX = x + radius / 2;
        int circleCenterY = y + radius / 2;
        double distance = Math.sqrt(Math.pow(mouseX - circleCenterX, 2) + Math.pow(mouseY - circleCenterY, 2));
        return distance <= radius / 2;
    }
    

}
