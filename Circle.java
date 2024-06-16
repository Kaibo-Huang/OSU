import java.awt.*;

public class Circle extends Rectangle {
	private int radius = 100; // initial size of the circle
	public int moveRadius = 200; // initial size of the circle
	public static final int MAX_RADIUS = 200; // Maximum radius size
	public static final int MIN_RADIUS = 80; // Minimum radius size
	int initialX, initialY;
	int x0, y0;
	int id;
	byte scoreState = 0;
	int num = 0;

	public static Color c;

	boolean isClicked = false;

	// Constructor creates circle at given location with given dimensions
	public Circle(int centerX, int centerY, int i) {
		super(centerX - 100 / 2, centerY - 100 / 2, 100, 100);
		x0 = centerX;
		y0 = centerY;
		initialX = x0 - 100 / 2;
		initialY = y0 - 100 / 2;
		id = i;
	}

	// Draws the current location of the circle to the screen
	public void draw(Graphics g) {
		g.setColor(c);
		g.fillOval(initialX + 10, initialY + 10, radius - 20, radius - 20);

		Graphics2D g2d = (Graphics2D) g;
		Stroke oldStroke = g2d.getStroke(); // Save the old stroke
		// Set the thickness of the outline
		g2d.setColor(Color.white);
		g2d.setStroke(new BasicStroke(8)); // Change the thickness as needed
		g2d.drawOval(initialX + 4, initialY + 4, radius - 8, radius - 8);

		g2d.setColor(c);

		g2d.setStroke(new BasicStroke(5)); // Change the thickness as needed
		g2d.drawOval(x, y, moveRadius, moveRadius);
		g2d.setStroke(oldStroke); // Restore the old stroke

		// Draw the number inside the circle
		g2d.setColor(Color.white);
		Font originalFont = g2d.getFont();
		Font largeFont = originalFont.deriveFont(48f); // Set a larger font size
		g2d.setFont(largeFont);
		FontMetrics fm = g2d.getFontMetrics();

		g2d.drawString(Integer.toString(num), initialX + radius / 2 - fm.stringWidth(Integer.toString(num)) / 2,
				initialY + radius / 2 + fm.getAscent() / 2 - fm.getDescent());
		g2d.setFont(originalFont); // Restore the original font

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

	// Method to check if the mouse click is within the circle
	public boolean isMouseClickedInside(int mouseX, int mouseY) {
		return (double) Math
				.sqrt(Math.pow(mouseX - (x + radius / 2), 2) + Math.pow(mouseY - (y + radius / 2), 2)) <= radius / 2;
	}
}
