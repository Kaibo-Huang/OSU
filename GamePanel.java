import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {

	// dimensions of window
	public static final int GAME_WIDTH = 1280;
	public static final int GAME_HEIGHT = 780;

	private Thread gameThread;
	private Image image;
	private Graphics graphics;
	private Slider s1;

	private JButton maruButton;
	private JButton playButton;
	private JButton exitButton;

	private boolean isTitleScreen = true;

	public GamePanel() {
		this.setFocusable(true); // make everything in this class appear on the screen
		this.addKeyListener(this); // start listening for keyboard input

		s1 = new Slider(500, 400);

		this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

		// add MARU! button
		maruButton = new JButton("MARU!");
		maruButton.setFont(new Font("Arial", Font.PLAIN, 24));
		maruButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showGameOptions(); // calls showGameOptions when pressed
			}
		});

		// add PLAY button
		playButton = new JButton("PLAY");
		playButton.setFont(new Font("Arial", Font.PLAIN, 24));
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame(); // calls startGame when pressed
			}
		});

		// add EXIT button
		exitButton = new JButton("EXIT");
		exitButton.setFont(new Font("Arial", Font.PLAIN, 24));
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0); // exits when pressed
			}
		});

		// Add MARU! button initially in middle of the screen
		this.setLayout(null);
		maruButton.setBounds(GAME_WIDTH / 2 - 100, GAME_HEIGHT / 2 - 25, 200, 50);
		this.add(maruButton);

		// Start game thread
		gameThread = new Thread(this);
		gameThread.start();
	}

	private void showGameOptions() {

		// shifts the MARU! button and adds PLAY and EXIT buttons
		maruButton.setBounds(GAME_WIDTH / 2 - 200, GAME_HEIGHT / 2 - 50, 200, 50);
		playButton.setBounds(GAME_WIDTH / 2 + 100, GAME_HEIGHT / 2 - 100, 200, 50);
		exitButton.setBounds(GAME_WIDTH / 2 + 100, GAME_HEIGHT / 2, 200, 50);

		this.add(playButton);
		this.add(exitButton);

		this.repaint();
	}

	private void startGame() {
		// Remove buttons and switch to game view
		this.remove(maruButton);
		this.remove(playButton);
		this.remove(exitButton);
		isTitleScreen = false;
		this.repaint();
	}

	public void paint(Graphics g) {
		if (isTitleScreen) {
			super.paint(g);
		} else {
			// We are using "double buffering here"
			image = createImage(GAME_WIDTH, GAME_HEIGHT); // draw off screen
			graphics = image.getGraphics();
			draw(graphics); // update the positions of everything on the screen
			g.drawImage(image, 0, 0, this); // move the image on the screen
		}
	}

	public void draw(Graphics g) {
		s1.draw(g);
	}

	public void checkCollision() {

	}

	public void move() {

	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long now;

		while (true) { // this is the infinite game loop
			now = System.nanoTime();
			delta = delta + (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {
				if (!isTitleScreen) {
					move();
					checkCollision();
				}
				repaint();
				delta--;
			}
		}
	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}
}
