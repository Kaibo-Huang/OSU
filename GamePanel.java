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

    private MouseCircle mc1;
    private Circle c1;

    private JButton maruButton, playButton, exitButton; // menu buttons
    private JButton tutorial, easy, medium, hard, backButton; // level buttons

    private boolean isTitleScreen = true;
    static Clip menu;

    public GamePanel() {
        this.setFocusable(true); // make everything in this class appear on the screen
        this.addKeyListener(this); // start listening for keyboard input
        
        mc1 = new MouseCircle(500, 400);
        c1 = new Circle(500, 400);

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
        mc1.draw(g);
        c1.draw(g);
    }

    public void checkCollision() {
        // Add collision logic here if needed
    }

    public void move() {
        // Decrease the radius of the mouse circle
        int currentRadius = mc1.getRadius();
        if (currentRadius > MouseCircle.MIN_RADIUS) {
            mc1.setRadius(currentRadius - 1);
            
            mc1.setPosition(500 - mc1.radius/2, 400- mc1.radius/2);
            
        } else {
            mc1.setRadius(MouseCircle.MAX_RADIUS);
           
            mc1.setPosition(500 - mc1.radius/2, 400- mc1.radius/2);
           
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
                checkCollision();
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
