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

	Score score;

	static boolean[] appearC = new boolean[10];

	private Circle c1;
	private Circle c2;
	private Circle c3;
	private Circle c4;
	private Circle c5;
	private Circle c6;
	private Circle c7;
	private Circle c8;
	private Circle c9;

	private JButton maruButton, playButton, exitButton; // menu buttons
	private JButton tutorial, easy, medium, hard, backButton; // level buttons

	private boolean isTitleScreen = true;
	static Clip menu;

	public GamePanel() {
		this.setFocusable(true); // make everything in this class appear on the screen
		this.addKeyListener(this); // start listening for keyboard input

		score = new Score();

		c1 = new Circle(2000, 2000, 1);
		c2 = new Circle(2000, 2000, 2);
		c3 = new Circle(2000, 2000, 3);
		c4 = new Circle(2000, 2000, 4);
		c5 = new Circle(2000, 2000, 5);
		c6 = new Circle(2000, 2000, 6);
		c7 = new Circle(2000, 2000, 7);
		c8 = new Circle(2000, 2000, 8);
		c9 = new Circle(2000, 2000, 9);

		this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		playMenu();

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
				showLevels(); // calls startGame when pressed
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

		// add Tutorial button
		tutorial = new JButton("Tutorial");
		tutorial.setFont(new Font("Arial", Font.PLAIN, 24));
		tutorial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound("Music/PlayClick.wav");
				isTitleScreen = false;
				stopMenu();
				tutorial(); // calls startGame when pressed
			}
		});

		// add Easy button
		easy = new JButton("Easy");
		easy.setFont(new Font("Arial", Font.PLAIN, 24));
		easy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound("Music/PlayClick.wav");
				isTitleScreen = false;
				stopMenu();
				easy(); // calls startGame when pressed

				add(c1, 600, 300);
			}
		});

		// add Medium button
		medium = new JButton("Medium");
		medium.setFont(new Font("Arial", Font.PLAIN, 24));
		medium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound("Music/PlayClick.wav");
				isTitleScreen = false;
				stopMenu();
				medium(); // calls startGame when pressed
			}
		});

		// add Hard button
		hard = new JButton("Hard");
		hard.setFont(new Font("Arial", Font.PLAIN, 24));
		hard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound("Music/PlayClick.wav");
				isTitleScreen = false;
				stopMenu();
				hard(); // calls startGame when pressed
			}
		});

		// add Back button
		backButton = new JButton("BACK");
		backButton.setFont(new Font("Arial", Font.PLAIN, 24));
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound("Music/PlayClick.wav");
				showGameOptions(); // calls startGame when pressed
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
		this.removeAll();

		// shifts the MARU! button and adds PLAY and EXIT buttons
		maruButton.setBounds(GAME_WIDTH / 2 - 200, GAME_HEIGHT / 2 - 50, 200, 50);
		playButton.setBounds(GAME_WIDTH / 2 + 100, GAME_HEIGHT / 2 - 100, 200, 50);
		exitButton.setBounds(GAME_WIDTH / 2 + 100, GAME_HEIGHT / 2, 200, 50);

		this.add(maruButton);
		this.add(playButton);
		this.add(exitButton);

		this.repaint();
	}

	public void showLevels() {
		this.removeAll();

		tutorial.setBounds(GAME_WIDTH / 2 + 200, GAME_HEIGHT / 4 - 50, 200, 50);
		easy.setBounds(GAME_WIDTH / 2 + 200, GAME_HEIGHT / 4 + 50, 200, 50);
		medium.setBounds(GAME_WIDTH / 2 + 200, GAME_HEIGHT / 2 - 50, 200, 50);
		hard.setBounds(GAME_WIDTH / 2 + 200, GAME_HEIGHT / 2 + 50, 200, 50);
		backButton.setBounds(50, GAME_HEIGHT - 100, 200, 50);

		this.add(tutorial);
		this.add(easy);
		this.add(medium);
		this.add(hard);
		this.add(backButton);

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
		c1.draw(g);
		c2.draw(g);
		c3.draw(g);
		c4.draw(g);
		c5.draw(g);
		c6.draw(g);
		c7.draw(g);
		c8.draw(g);
		c9.draw(g);

		score.draw(g);
	}

	public void moveCircle(Circle c) {
		System.out.println(c.getRadius());
		if (c.getRadius() > Circle.MIN_RADIUS) {
			c.setRadius(c.getRadius() - 1);

			c.setPosition(c.x0 - c.moveRadius / 2, c.y0 - c.moveRadius / 2);

		} else {
			appearC[c.id] = false;
			c.setRadius(80);

			c.setPosition(2000, 2000);
			c.x0 = 2000;
			c.y0 = 2000;
			c.initialX = 2000;
			c.initialY = 2000;
		}
	}

	public void collision() {

	}

	public void checkCollision(Circle c) {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Get the coordinates of the click
				int mouseX = e.getX();
				int mouseY = e.getY();

				// Check if the click falls inside the circle
				if (c.isMouseClickedInside(mouseX, mouseY) && c.isClicked == false) {
					c.isClicked = true;
					if (c.moveRadius <= 100) {
						Score.score++;
					} else {
						c.moveRadius = 80;
						Score.score--;
					}
				}
			}
		});
	}

	public void add(Circle c, int x, int y) {
		c.x0 = x;
		c.y0 = y;
		
		 c.initialX = c.x0- 100 / 2;
	        c.initialY = c.y0- 100 / 2;

		appearC[c.id] = true;
	}

	public void move() {
		if (appearC[1] == true) {
			moveCircle(c1);
			checkCollision(c1);
		}
		if (appearC[2] == true) {
			moveCircle(c2);
			checkCollision(c2);
		}
		if (appearC[3] == true) {
			moveCircle(c3);
			checkCollision(c3);
		}
		if (appearC[4] == true) {
			moveCircle(c4);
			checkCollision(c4);
		}
		if (appearC[5] == true) {
			moveCircle(c5);
			checkCollision(c5);
		}
		if (appearC[6] == true) {
			moveCircle(c6);
			checkCollision(c6);
		}
		if (appearC[7] == true) {
			moveCircle(c7);
			checkCollision(c7);
		}
		if (appearC[8] == true) {
			moveCircle(c8);
			checkCollision(c8);
		}
		if (appearC[9] == true) {
			moveCircle(c9);
			checkCollision(c9);
		}
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
				collision();
				repaint();
				delta--;
			}
		}
	}

	private void tutorial() {
		// Implementation for tutorial
	}

	private void easy() {
		// Implementation for easy
	}

	private void medium() {
		// Implementation for medium
	}

	private void hard() {
		// Implementation for hard
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

	// Method to stop playing the game music
	public static void stopGame() {
		menu.stop();
		menu.close();
	}

	// Method to play menu music
	public static void playMenu() {
		try {
			File file = new File("Music/Menu.wav"); // Open menu music file
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

	public void stall(long deltaTime, Circle c) {
		long currentTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - currentTime < deltaTime) {
			System.out.println();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Implementation for keyTyped
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Implementation for keyPressed
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Implementation for keyReleased
	}
}
