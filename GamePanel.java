import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

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
	static Clip menu;

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
				playSound("Music/MARUClick.wav");
				showGameOptions(); // calls showGameOptions when pressed
			}
		});

		// add PLAY button
		playButton = new JButton("PLAY");
		playButton.setFont(new Font("Arial", Font.PLAIN, 24));
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound("Music/PlayClick.wav");
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
				move();
				checkCollision();
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

	private void playSound(String soundFile) {
		try {
			// open theme song file
			File file = new File(soundFile);

			// Get audio input stream
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			// Open and play the theme song
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start(); // Start playing the sound
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	// Method to play menu music
	public static void playMenu() {
		try {
			File file = new File("Music/MainMenu.wav"); // Open menu music file
			AudioInputStream audio = AudioSystem.getAudioInputStream(file); // Get audio input stream
			menu = AudioSystem.getClip(); // Get a clip for playing audio

			menu.open(audio); // Open the audio clip
			menu.addLineListener(event -> {
				// Restart the music if it stops
				if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
					menu.setMicrosecondPosition(0);
					menu.start();
				}
			});
			menu.start(); // Start playing menu music
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method to stop playing the menu music
	public static void stopMenu() {
		if (menu != null && menu.isOpen()) {
			menu.stop(); // Stop the menu music
			menu.setMicrosecondPosition(0); // Reset its position to the beginning
			menu.close(); // Close the clip
		}
	}
}
