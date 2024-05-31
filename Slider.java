import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Slider extends Rectangle {
	public static final int RADIUS = 100; // size of ball

	// constructor creates ball at given location with given dimensions
	public Slider(int x, int y) {
		super(x, y, RADIUS, RADIUS);

	}

	// draws the current location of the ball to the screen
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillOval(x, y, RADIUS, RADIUS);
		
		g.setColor(Color.gray);
		g.fillOval(x + 10, y + 10, RADIUS - 20, RADIUS - 20);
	}
}
