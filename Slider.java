// Don Tran and Kaibo Huang
// June 17, 2024
// This class draws the slider button.

import java.awt.*;  
import java.awt.geom.AffineTransform;  

public class Slider extends Rectangle {  // Declares a class named Slider that extends Rectangle.
    public int radius = 100;  // Initial size of the circles.
    public int moveRadius = 200;  // Initial size of the larger circle.
    public static final int MAX_RADIUS = 200;  // Maximum radius size.
    public static final int MIN_RADIUS = 100;  // Minimum radius size.
    int initialX, initialY;  // Initial position of the circle.
    int finalX, finalY;  // Final position of the right circle.
    int moveX, moveY;  // Current position of the moving circle.
    double moveTime;  // Time for moving along the path.
    boolean mousePressed;  // Indicates if the mouse is pressed.
    boolean zPressed;  // Indicates if the 'z' key is pressed.
    boolean xPressed;  // Indicates if the 'x' key is pressed.

    int id;  // Identifier for the slider.
    int length;  // Length of the slider.
    double angle;  // Rotation angle.

    boolean isClicked;  // Indicates if the slider is clicked.
    boolean movingAlongPath;  // Indicates if the circle is moving along the path.
    boolean goodSlide;  // Indicates if the slide is good.
    boolean goodClick;  // Indicates if the click is good.
    int scoreState;  // State of the score.
    int scoreX, scoreY;  // Position of the score.

    public static Color c;  // Color of the slider.
    public static Color moveC;  // Color of the moving circle.
    int num = 0;  // Number displayed inside the left circle.

    // Constructor with rotation angle
    public Slider(int centerX, int centerY, int l, int i, double angle) {
        super(centerX - 100 / 2, centerY - 100 / 2, 100, 100);  // Calls the superclass constructor with initial dimensions.

        isClicked = false;  // Initializes isClicked to false.
        movingAlongPath = false;  // Initializes movingAlongPath to false.
        goodSlide = true;  // Initializes goodSlide to true.
        goodClick = false;  // Initializes goodClick to false.
        scoreState = 0;  // Initializes scoreState to 0.
        mousePressed = false;  // Initializes mousePressed to false.
        zPressed = false;  // Initializes zPressed to false.
        xPressed = false;  // Initializes xPressed to false.

        initialX = centerX;  // Sets the initialX to centerX.
        initialY = centerY;  // Sets the initialY to centerY.
        moveX = centerX;  // Sets the moveX to centerX.
        moveY = centerY;  // Sets the moveY to centerY.
        id = i;  // Sets the id to i.
        length = l;  // Sets the length to l.
        this.angle = angle;  // Sets the angle.

    }

    // Draws the current location of the circle to the screen
    public void draw(Graphics g) {
        this.finalX = initialX + length;  // Calculates the final position of the right circle.
        this.finalY = initialY;  // Sets the finalY to the same y-position as the initial.

        Graphics2D g2d = (Graphics2D) g;  // Casts the Graphics object to Graphics2D.

        Stroke oldStroke = g2d.getStroke();  // Saves the old stroke.

        g2d.setStroke(new BasicStroke(7));  // Changes the thickness as needed.

        AffineTransform originalTransform = g2d.getTransform();  // Saves the original transform.

        g2d.rotate(Math.toRadians(angle), initialX, initialY);  // Applies rotation.

        g2d.setColor(Color.white);  // Sets the drawing color to white.
        int rectX = initialX - radius / 2;  // Calculates the x-position of the rectangle.
        int rectY = initialY - radius / 2;  // Calculates the y-position of the rectangle.
        g2d.drawRoundRect(rectX, rectY, length + radius, radius, radius, radius);  // Draws the connecting rectangle with rounded edges.
        
        g2d.setColor(c);  // Sets the color to c.
        g2d.fillRoundRect(rectX + 2, rectY + 2, length + radius - 4, radius - 4, radius, radius);  // Fills the rounded rectangle.
        g2d.setColor(Color.white);  // Sets the drawing color to white.
        
        g2d.drawOval(initialX - radius / 2, initialY - radius / 2, radius, radius);  // Draws the left circle.
        g2d.drawOval(initialX + length - radius / 2, initialY - radius / 2, radius, radius);  // Draws the right circle.

        g2d.setStroke(new BasicStroke(7));  // Changes the thickness as needed.

        g2d.setColor(moveC);  // Sets the color to moveC.
        g2d.drawOval(moveX - moveRadius / 2, moveY - moveRadius / 2, moveRadius, moveRadius);  // Draws the larger circle around the left circle.

        g2d.setTransform(originalTransform);  // Restores the original transform.
        g2d.setColor(Color.white);  // Sets the drawing color to white.
        Font originalFont = g2d.getFont();  // Saves the original font.
        Font largeFont = originalFont.deriveFont(48f);  // Sets a larger font size.
        g2d.setFont(largeFont);  // Applies the larger font.
        FontMetrics fm = g2d.getFontMetrics();  // Gets the font metrics.

        g2d.drawString(Integer.toString(num), initialX - fm.stringWidth(Integer.toString(num)) / 2,
                initialY + fm.getAscent() / 2 - fm.getDescent());  // Draws the number inside the left circle.
        g2d.setFont(originalFont);  // Restores the original font.

        g2d.setStroke(oldStroke);  // Restores the old stroke.
    }

    // Method to set the radius and update the rectangle bounds
    public void setRadius(int r) {
        moveRadius = r;  // Sets the moveRadius to r.
    }

    // Method to get the radius
    public int getRadius() {
        return moveRadius;  // Returns the moveRadius.
    }

    // Method to update the position based on the center
    public void setPosition(int centerX, int centerY) {
        this.x = centerX;  // Sets the x-position.
        this.y = centerY;  // Sets the y-position.
    }

    // Method to check if the mouse click is within the blue moving circle
    public boolean isMouseClickedInside(int mouseX, int mouseY) {
        return (double) Math.sqrt(Math.pow(mouseX - moveX, 2) + Math.pow(mouseY - moveY, 2)) <= moveRadius / 2+ 2;  // Checks if the mouse click is inside the moving circle.
    }
}
