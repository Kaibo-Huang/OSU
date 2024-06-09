//Don Tran and Kaibo Huang
//June 11, 2024
//This class creates a new slider object, which will require the user to hover over the shape while holding down their mouse or keyboard bindings. 

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Slider extends Rectangle {
	public int radius = 100; // initial size of the circles
	public int moveRadius = 200; // initial size of the larger circle
	public static final int MAX_RADIUS = 200; // Maximum radius size
	public static final int MIN_RADIUS = 100; // Minimum radius size
	int initialX, initialY;
	int finalX, finalY; // Position of the right circle
	int moveX, moveY;
	

	int id;
	int length;
	double angle; // Rotation angle

	boolean isClicked = false;
	boolean movingAlongPath = false; // Indicates if the circle is moving along the path

	// Constructor creates circle at given location with given dimensions
	public Slider(int centerX, int centerY, int l, int i) {
		super(centerX - 100 / 2, centerY - 100 / 2, 100, 100);
		initialX = centerX;
		initialY = centerY;
		moveX = centerX;
		moveY = centerY;
		id = i;
		length = l;
		this.angle = 0; // Default angle is 0 degrees
		

	}

	// New constructor with rotation angle
	public Slider(int centerX, int centerY, int l, int i, double angle) {
		super(centerX - 100 / 2, centerY - 100 / 2, 100, 100);
		initialX = centerX;
		initialY = centerY;
		moveX = centerX;
		moveY = centerY;
		id = i;
		length = l;
		this.angle = angle; // Set the angle
		

	}

	// Draws the current location of the circle to the screen
	public void draw(Graphics g) {

		this.finalX = initialX + length; // Calculate final position of the right circle
		this.finalY = initialY; // Same y-position as the initial

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		isClicked = false;
		Stroke oldStroke = g2d.getStroke(); // Save the old stroke
		int thickStroke = 10; // Thickness of the stroke for circles
		g2d.setStroke(new BasicStroke(thickStroke)); // Change the thickness as needed

		// Save the original transform
		AffineTransform originalTransform = g2d.getTransform();

		// Apply rotation
		g2d.rotate(Math.toRadians(angle), initialX, initialY);

		// Calculate the positions and dimensions of the shapes
		int circleDiameter = radius;
		int ovalWidth = length;
		int ovalHeight = radius;
		
	
		

		// Draw the left circle
		g2d.drawOval(initialX - circleDiameter / 2, initialY - circleDiameter / 2, circleDiameter, circleDiameter);

		// Draw the right circle
		int rightCircleX = initialX + ovalWidth;
		g2d.drawOval(rightCircleX - circleDiameter / 2, initialY - circleDiameter / 2, circleDiameter, circleDiameter);
	
		/*g2d.setColor(Color.orange);
		g2d.drawOval(initialX + length - radius / 2, initialY - radius / 2, radius, radius);
		g2d.setColor(Color.black);
		*/

		g2d.setStroke(new BasicStroke(3)); // Change the thickness as needed

		// Draw the connecting rectangle with rounded edges
		int rectX = initialX - circleDiameter / 2;
		int rectY = initialY - ovalHeight / 2;
		g2d.drawRoundRect(rectX, rectY, ovalWidth + circleDiameter, ovalHeight, ovalHeight, ovalHeight);

		// Draw the larger circle around the left circle
		g2d.setColor(Color.blue);
		g2d.drawOval(moveX - moveRadius / 2, moveY - moveRadius / 2, moveRadius, moveRadius);

		// Restore the original transform
		g2d.setTransform(originalTransform);

		g2d.setStroke(oldStroke); // Restore the old stroke
	}

	// Method to set the radius and update the rectangle bounds
	public void setRadius(int r) {
		moveRadius = r;
	}

	// Method to get the radius
	public int getRadius() {
		return moveRadius;
	}

	// Method to update the position based on the center
	public void setPosition(int centerX, int centerY) {
		this.x = centerX;
		this.y = centerY;
	}

	// Method to check if the mouse click is within the blue moving circle
	public boolean isMouseClickedInside(int mouseX, int mouseY) {
	    int circleCenterX = moveX;
	    int circleCenterY = moveY;
	    double distance = Math.sqrt(Math.pow(mouseX - circleCenterX, 2) + Math.pow(mouseY - circleCenterY, 2));
	    return distance <= moveRadius / 2;
	}



}
