//Don Tran and Kaibo Huang
//June 11, 2024
//This class uses the run method to constantly loop through game elements such as notes, sliders, menu and buttons

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

public class GamePanel extends JPanel implements Runnable, KeyListener {

	// dimensions of window
	public static final int GAME_WIDTH = 1280;
	public static final int GAME_HEIGHT = 780;

	// variable declarations
	private Thread gameThread;
	private Image image;
	private Graphics graphics;
	private static Clip menu;

	JLabel label;

	// check if circle or slider has appeared
	private static boolean[] appearC = new boolean[10];
	private static boolean[] appearS = new boolean[10];
	private static boolean[] appearR = new boolean[10];

	// declare 9 circle and slider objects
	private Circle c1, c2, c3, c4, c5, c6, c7, c8, c9;
	private Slider s1, s2, s3, s4, s5, s6, s7, s8, s9;
	private Reverse r1, r2, r3, r4, r5, r6, r7, r8, r9;

	private JButton playButton, exitButton; // menu buttons
	private JButton tutorial, easy, medium, hard, backButton, play;// level buttons

	boolean playTutorial, playEasy, playMedium, playHard;

	private boolean isTitleScreen = true;
	private BufferedImage backgroundImage, logo;

	// map elements (x position, y position, time of placement)
	private int[][] tutorialMap = {
			{ 640, 238, 1039, 999, 1039, 238, 511, 637, 650, 919, 1200, 644, 363, 85, 834, 438, 847, 679, 90, 158, 235,
					341, 651, 879, 529, 120, 529, 962, 1142, 654, 100, 554, 150, 150 },
			{ 360, 132, 132, 389, 636, 646, 237, 100, 646, 517, 389, 124, 253, 383, 318, 319, 650, 311, 383, 559, 732,
					333, 48, 72, 696, 550, 676, 676, 120, 561, 674, 710, 360, 500 },
			{ 3491, 20106, 21952, 22875, 23798, 25645, 27029, 27491, 29337, 30260, 31183, 33029, 33952, 34875, 57029,
					60722, 62568, 86568, 89337, 89799, 90260, 92106, 93491, 93953, 96722, 97645, 100414, 101337, 104106,
					105029, 107799, 108722, 112270, 112995 } };

	// https://osu.ppy.sh/beatmapsets/515069#osu/1095679
	private int[][] easyMap = { {}, {}, {} };

	private int[][] mediumMap = {
			{ 177, 596, 934, 1154, 1175, 779, 777, 531, 288, 718, 919, 804, 596, 779, 962, 779, 944, 741, 551, 724, 313,
					478, 253, 113, 293, 524, 644, 867, 1172, 867, 723 },
			{ 319, 405, 251, 148, 513, 359, 537, 233, 395, 339, 419, 687, 616, 509, 615, 509, 387, 158, 457, 337, 201,
					48, 371, 397, 537, 738, 469, 622, 612, 431, 251 },
			{ 433, 1393, 2353, 3313, 4273, 5233, 6193, 8113, 9073, 10033, 10513, 11473, 11953, 13393, 13873, 15313,
					15793, 16753, 17713, 19153, 20593, 21553, 22513, 23953, 24433, 25393, 26353, 27793, 28273, 29233,
					30193 } };
	private int[][] hardMap = { {}, {}, {} };

	private int combo = 0;

	// constructor to initialize variables and add initial JButtons
	public GamePanel() {
		this.setFocusable(true); // make everything in this class appear on the screen
		this.addKeyListener(this); // start listening for keyboard input

		// initialize circle and slider objects
		c1 = new Circle(2000, 2000, 1);
		c2 = new Circle(2000, 2000, 2);
		c3 = new Circle(2000, 2000, 3);
		c4 = new Circle(2000, 2000, 4);
		c5 = new Circle(2000, 2000, 5);
		c6 = new Circle(2000, 2000, 6);
		c7 = new Circle(2000, 2000, 7);
		c8 = new Circle(2000, 2000, 8);
		c9 = new Circle(2000, 2000, 9);

		s1 = new Slider(2000, 2000, 300, 1, 100);
		s2 = new Slider(2000, 2000, 300, 2, 100);
		s3 = new Slider(2000, 2000, 300, 3, 100);
		s4 = new Slider(2000, 2000, 300, 4, 100);
		s5 = new Slider(2000, 2000, 300, 5, 100);
		s6 = new Slider(2000, 2000, 300, 6, 100);
		s7 = new Slider(2000, 2000, 300, 7, 100);
		s8 = new Slider(2000, 2000, 300, 8, 100);
		s9 = new Slider(2000, 2000, 300, 9, 100);

		r1 = new Reverse(2000, 2000, 300, 1, 100);
		r2 = new Reverse(2000, 2000, 300, 2, 100);
		r3 = new Reverse(2000, 2000, 300, 3, 100);
		r4 = new Reverse(2000, 2000, 300, 4, 100);
		r5 = new Reverse(2000, 2000, 300, 5, 100);
		r6 = new Reverse(2000, 2000, 300, 6, 100);
		r7 = new Reverse(2000, 2000, 300, 7, 100);
		r8 = new Reverse(2000, 2000, 300, 8, 100);
		r9 = new Reverse(2000, 2000, 300, 9, 100);

		this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		playMenu();
		this.setLayout(null);

		label = new JLabel();
		label.setBounds(GAME_WIDTH / 2 - 225, GAME_HEIGHT / 2 - 225, 450, 450);
		this.add(label);

		logo = loadImage("Images/Logo.png");
		label.setIcon(new ImageIcon(logo));

		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				label.setBounds(GAME_WIDTH / 2 - 275, GAME_HEIGHT / 2 - 225, 450, 450);
				playSound("Music/MARUClick.wav");
				showGameOptions();
			}
		});

		this.setVisible(true);

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

				play = new JButton("PLAY!");
				play.setFont(new Font("Arial", Font.PLAIN, 24));
				addPlayButton();
				play.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						playSound("Music/PlayClick.wav");
						isTitleScreen = false;
						playTutorial = true;
						stopMenu();
						tutorial(); // calls startGame when pressed
					}
				});
			}
		});

		// add Easy button
		easy = new JButton("Easy");
		easy.setFont(new Font("Arial", Font.PLAIN, 24));
		easy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				play = new JButton("PLAY!");
				play.setFont(new Font("Arial", Font.PLAIN, 24));
				addPlayButton();
				play.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						playSound("Music/PlayClick.wav");
						isTitleScreen = false;
						playEasy = true;
						stopMenu();
						easy(); // calls startGame when pressed
					}
				});

			}
		});

		// add Medium button
		medium = new JButton("Medium");
		medium.setFont(new Font("Arial", Font.PLAIN, 24));
		medium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				play = new JButton("PLAY!");
				play.setFont(new Font("Arial", Font.PLAIN, 24));
				addPlayButton();
				play.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						playSound("Music/PlayClick.wav");
						isTitleScreen = false;
						playMedium = true;
						stopMenu();
						medium(); // calls startGame when pressed
					}
				});
			}
		});

		// add Hard button
		hard = new JButton("Hard");
		hard.setFont(new Font("Arial", Font.PLAIN, 24));
		hard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				play = new JButton("PLAY!");
				play.setFont(new Font("Arial", Font.PLAIN, 24));
				addPlayButton();
				play.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						playSound("Music/PlayClick.wav");
						isTitleScreen = false;
						playHard = true;
						stopMenu();
						hard(); // calls startGame when pressed
					}
				});
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

		// Start game thread
		gameThread = new Thread(this);
		gameThread.start();
	}

	// move around JButtons to show PLAY and EXIT buttons
	private void showGameOptions() {
		this.removeAll();

		// shifts the MARU! button and adds PLAY and EXIT buttons
		label.setBounds(GAME_WIDTH / 4, GAME_HEIGHT / 2 - 225, 450, 450);
		playButton.setBounds(GAME_WIDTH / 2 + 100, GAME_HEIGHT / 2 - 50, 200, 50);
		exitButton.setBounds(GAME_WIDTH / 2 + 100, GAME_HEIGHT / 2 + 40, 200, 50);

		this.add(label);
		this.add(playButton);
		this.add(exitButton);

		this.repaint();
	}

	// adds the buttons to show the level
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

	public void addPlayButton() {
		play.setBounds(GAME_WIDTH - 250, GAME_HEIGHT - 100, 200, 50);
		this.add(play);
	}

	// paints images off the screen and moves them onto the screen to prevent lag
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

	// draws each of the individual circles, sliders and spinners as well as the
	// score
	public void draw(Graphics g) {

		if (playTutorial) {
			setBackgroundImage("Images/tutorial.png");
			g.drawImage(backgroundImage, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
		} else if (playEasy) {
			setBackgroundImage("Images/easy.png");
			g.drawImage(backgroundImage, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
		} else if (playMedium) {
			setBackgroundImage("Images/medium.png");
			g.drawImage(backgroundImage, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
		} else if (playHard) {
			setBackgroundImage("Images/hard.png");
			g.drawImage(backgroundImage, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
		}
		c1.draw(g);
		c2.draw(g);
		c3.draw(g);
		c4.draw(g);
		c5.draw(g);
		c6.draw(g);
		c7.draw(g);
		c8.draw(g);
		c9.draw(g);

		s1.draw(g);
		s2.draw(g);
		s3.draw(g);
		s4.draw(g);
		s5.draw(g);
		s6.draw(g);
		s7.draw(g);
		s8.draw(g);
		s9.draw(g);

		r1.draw(g);
		r2.draw(g);
		r3.draw(g);
		r4.draw(g);
		r5.draw(g);
		r6.draw(g);
		r7.draw(g);
		r8.draw(g);
		r9.draw(g);

		Score.draw(g);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 20));

		g.drawString(combo + "X", 20, GamePanel.GAME_HEIGHT - 30);
	}

	// ensures smooth movement of closing circle
	public void moveCircle(Circle c) {
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

	// Method to move the slider
	public void moveSlider(Slider s) {
		if (!s.movingAlongPath) {

			if (s.getRadius() > Slider.MIN_RADIUS) {
				s.setRadius(s.getRadius() - 1);
				s.setPosition(s.moveX - s.moveRadius / 2, s.moveY - s.moveRadius / 2);

			} else {
				s.movingAlongPath = true; // Start moving along the path

			}

		} else {
			// Move along the path of the rounded rectangle
			double steps = s.moveTime * 60; // Number of steps to reach the right circle
			double dx = (s.finalX - s.initialX) / (double) steps;

			// Update the position of the circle along the path

			s.moveX += dx;

			// Check if the circle has reached the right circle
			if (s.moveX >= s.initialX + s.length) {
				s.moveX = s.initialX + s.length;

				s.moveY = s.initialY;
				s.movingAlongPath = false; // Reset the state for next move

				if (s.goodSlide && s.goodClick) {
					combo++;
					Score.score += 300 * combo;
				} else if (s.goodSlide || s.goodClick) {
					combo++;
					Score.score += 100 * combo;
					playSound("Music/clickSound.wav");
				} else {
					combo = 0;
				}
				appearS[s.id] = false;
				s.length = 200;
				s.initialX = 2000;
				s.initialY = 2000;
				s.angle = 0;
				s.moveX = 2000;
				s.moveY = 2000;
			}

		}

	}

	// Method to move the reverse slider
	public void moveReverse(Reverse r) {
		if (!r.movingAlongPath) {

			if (r.getRadius() > Reverse.MIN_RADIUS) {
				r.setRadius(r.getRadius() - 1);
				r.setPosition(r.moveX - r.moveRadius / 2, r.moveY - r.moveRadius / 2);

			} else {
				r.movingAlongPath = true; // Start moving along the path

			}

		} else {
			// Move along the path of the rounded rectangle
			double steps = r.moveTime * 60; // Number of steps to reach the right circle
			double dx = (r.finalX - r.initialX) / (double) steps;
			if (!r.reversePath) {

				// Update the position of the circle along the path

				r.moveX += dx;

				// Check if the circle has reached the right circle
				if (r.moveX >= r.initialX + r.length) {
					r.reversePath = true;
				}
			} else {

				r.moveX -= dx;
				// Check if the circle has reached the right circle
				if (r.moveX <= r.initialX) {
					r.moveX = r.initialX;

					r.moveY = r.initialY;
					r.movingAlongPath = false; // Reset the state for next move

					if (r.goodSlide && r.goodClick) {
						combo++;
						Score.score += 300 * combo;
					} else if (r.goodSlide || r.goodClick) {
						combo++;
						Score.score += 100 * combo;
						playSound("Music/clickSound.wav");
					} else {
						combo = 0;
					}
					appearR[r.id] = false;
					r.length = 200;
					r.initialX = 2000;
					r.initialY = 2000;
					r.angle = 0;
					r.moveX = 2000;
					r.moveY = 2000;
					r.reversePath = false;
				}
			}

		}

	}

	// checks if slider objects are clicked at the correct time and moving along
	// their path
	public void checkCollision(Slider s) {
		// Remove existing listeners to avoid duplicates
		for (MouseListener listener : getMouseListeners()) {
			removeMouseListener(listener);
		}
		for (MouseMotionListener listener : getMouseMotionListeners()) {
			removeMouseMotionListener(listener);
		}

		// check for mouse clicks
		if (!s.movingAlongPath) {
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// Get the coordinates of the click
					int mouseX = e.getX();
					int mouseY = e.getY();

					// Check if the click falls inside the circle
					if (s.isMouseClickedInside(mouseX, mouseY) && !s.isClicked) {
						s.isClicked = true;
						if (s.moveRadius <= 132) {
							s.goodClick = true;
							playSound("Music/clickSound.wav");
						} else {
							s.goodClick = false;
						}
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					s.mousePressed = true;
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					s.mousePressed = false;
				}
			});

			// checks for 'z' or 'x' keys
			addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_Z) {
						s.zPressed = true;
					} else if (e.getKeyCode() == KeyEvent.VK_X) {
						s.xPressed = true;
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_Z) {
						s.zPressed = false;
					} else if (e.getKeyCode() == KeyEvent.VK_X) {
						s.xPressed = false;
					}
				}
			});

		} else {

			addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					s.mousePressed = true;
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					s.mousePressed = false;
				}
			});

			addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_Z) {
						s.zPressed = true;
					} else if (e.getKeyCode() == KeyEvent.VK_X) {
						s.xPressed = true;
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_Z) {
						s.zPressed = false;
					} else if (e.getKeyCode() == KeyEvent.VK_X) {
						s.xPressed = false;
					}
				}
			});

			addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					// Get the coordinates of the mouse
					int mouseX = e.getX();
					int mouseY = e.getY();

					// Transform the mouse coordinates to the rotated coordinate system
					AffineTransform at = new AffineTransform();
					at.rotate(-Math.toRadians(s.angle), s.initialX, s.initialY);
					Point transformedPoint = new Point(mouseX, mouseY);
					at.transform(transformedPoint, transformedPoint);

					// Check if the transformed mouse coordinates are within the circle
					double distance = Math.sqrt(
							Math.pow(transformedPoint.x - s.moveX, 2) + Math.pow(transformedPoint.y - s.moveY, 2));
					if (distance > s.moveRadius / 2) {
						s.goodSlide = false;
					}

					// Check if the mouse button, z, or x key is pressed
					else if (!s.mousePressed && !s.zPressed && !s.xPressed) {
						s.goodSlide = false;

					}
				}
			});
		}

		setFocusable(true);
		requestFocusInWindow();
	}

	public void checkCollision(Reverse r) {
		// Remove existing listeners to avoid duplicates
		for (MouseListener listener : getMouseListeners()) {
			removeMouseListener(listener);
		}
		for (MouseMotionListener listener : getMouseMotionListeners()) {
			removeMouseMotionListener(listener);
		}

		// check for mouse clicks
		if (!r.movingAlongPath) {
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// Get the coordinates of the click
					int mouseX = e.getX();
					int mouseY = e.getY();

					// Check if the click falls inside the circle
					if (r.isMouseClickedInside(mouseX, mouseY) && !r.isClicked) {
						r.isClicked = true;
						if (r.moveRadius <= 132) {
							r.goodClick = true;
							playSound("Music/clickSound.wav");
						} else {
							r.goodClick = false;
						}
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					r.mousePressed = true;
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					r.mousePressed = false;
				}
			});

			// checks for 'z' or 'x' keys
			addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_Z) {
						r.zPressed = true;
					} else if (e.getKeyCode() == KeyEvent.VK_X) {
						r.xPressed = true;
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_Z) {
						r.zPressed = false;
					} else if (e.getKeyCode() == KeyEvent.VK_X) {
						r.xPressed = false;
					}
				}
			});

		} else {

			addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					r.mousePressed = true;
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					r.mousePressed = false;
				}
			});

			addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_Z) {
						r.zPressed = true;
					} else if (e.getKeyCode() == KeyEvent.VK_X) {
						r.xPressed = true;
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_Z) {
						r.zPressed = false;
					} else if (e.getKeyCode() == KeyEvent.VK_X) {
						r.xPressed = false;
					}
				}
			});

			addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					// Get the coordinates of the mouse
					int mouseX = e.getX();
					int mouseY = e.getY();

					// Transform the mouse coordinates to the rotated coordinate system
					AffineTransform at = new AffineTransform();
					at.rotate(-Math.toRadians(r.angle), r.initialX, r.initialY);
					Point transformedPoint = new Point(mouseX, mouseY);
					at.transform(transformedPoint, transformedPoint);

					// Check if the transformed mouse coordinates are within the circle
					double distance = Math.sqrt(
							Math.pow(transformedPoint.x - r.moveX, 2) + Math.pow(transformedPoint.y - r.moveY, 2));
					if (distance > r.moveRadius / 2) {
						r.goodSlide = false;
					}

					// Check if the mouse button, z, or x key is pressed
					else if (!r.mousePressed && !r.zPressed && !r.xPressed) {
						r.goodSlide = false;

					}
				}
			});
		}

		setFocusable(true);
		requestFocusInWindow();
	}

	// checks for clicks inside of the circle objects
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
					if (c.moveRadius <= 120) {
						combo++;
						Score.score += 300 * combo;
						playSound("Music/clickSound.wav");
					} else if (c.moveRadius <= 130) {
						combo++;
						Score.score += 100 * combo;
						playSound("Music/clickSound.wav");
					}

					else {
						c.moveRadius = 80;
						combo = 0;
					}
				}
			}
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_X) {
					// Get the current mouse location
					Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
					int mouseX = mouseLocation.x;
					int mouseY = mouseLocation.y;

					// Convert to component's coordinate system
					Point componentLocation = getLocationOnScreen();
					mouseX -= componentLocation.x;
					mouseY -= componentLocation.y;

					// Handle mouse click
					if (c.isMouseClickedInside(mouseX, mouseY) && !c.isClicked) {
						c.isClicked = true;
						if (c.moveRadius <= 120) {
							combo++;
							Score.score += 300 * combo;
							playSound("Music/clickSound.wav");
						} else if (c.moveRadius <= 130) {
							combo++;
							Score.score += 100 * combo;
							playSound("Music/clickSound.wav");
						}

						else {
							c.moveRadius = 80;
							combo = 0;
						}
					}
				}
			}
		});
		setFocusable(true);
		requestFocusInWindow();
	}

	// adds the Circles to the screen and the coordinates when needed
	public void add(Circle c, int x, int y, long t) {
		Timer timer = new Timer();
		c.isClicked = false;

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				c.x0 = x;
				c.y0 = y;

				c.initialX = c.x0 - 100 / 2;
				c.initialY = c.y0 - 100 / 2;

				c.moveRadius = Circle.MAX_RADIUS;

				appearC[c.id] = true;
				c.isClicked = false;
			}
		};
		timer.schedule(task, t);
	}

	// method to add slider objects with correct coordinates, time, length, angle
	// and speed
	public void add(Slider s, int x, int y, long t, int l, int a, double mT) {
		Timer timer = new Timer();
		s.isClicked = false;

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				s.length = l;
				s.initialX = x;
				s.initialY = y;
				s.angle = a;
				s.moveX = x;
				s.moveY = y;
				s.moveRadius = Slider.MAX_RADIUS;
				s.xPressed = false;
				s.zPressed = false;
				s.mousePressed = false;
				s.moveTime = mT;
				appearS[s.id] = true;
			}
		};
		timer.schedule(task, t);

	}

	public void add(Reverse r, int x, int y, long t, int l, int a, double mT) {
		Timer timer = new Timer();
		r.isClicked = false;

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				r.length = l;
				r.initialX = x;
				r.initialY = y;
				r.angle = a;
				r.moveX = x;
				r.moveY = y;
				r.moveRadius = Slider.MAX_RADIUS;
				r.xPressed = false;
				r.zPressed = false;
				r.mousePressed = false;
				r.moveTime = mT;
				appearR[r.id] = true;
			}
		};
		timer.schedule(task, t);

	}

	// ensures smooth movement of the approaching circle
	public void move() {
		if (appearC[1]) {
			moveCircle(c1);
			checkCollision(c1);
		}
		if (appearC[2]) {
			moveCircle(c2);
			checkCollision(c2);
		}
		if (appearC[3]) {
			moveCircle(c3);
			checkCollision(c3);
		}
		if (appearC[4]) {
			moveCircle(c4);
			checkCollision(c4);
		}
		if (appearC[5]) {
			moveCircle(c5);
			checkCollision(c5);
		}
		if (appearC[6]) {
			moveCircle(c6);
			checkCollision(c6);
		}
		if (appearC[7]) {
			moveCircle(c7);
			checkCollision(c7);
		}
		if (appearC[8]) {
			moveCircle(c8);
			checkCollision(c8);
		}
		if (appearC[9]) {
			moveCircle(c9);
			checkCollision(c9);
		}

		if (appearS[1]) {
			moveSlider(s1);
			checkCollision(s1);
		}
		if (appearS[2]) {
			moveSlider(s2);
			checkCollision(s2);
		}
		if (appearS[3]) {
			moveSlider(s3);
			checkCollision(s3);
		}
		if (appearS[4]) {
			moveSlider(s4);
			checkCollision(s4);
		}
		if (appearS[5]) {
			moveSlider(s5);
			checkCollision(s5);
		}
		if (appearS[6]) {
			moveSlider(s6);
			checkCollision(s6);
		}
		if (appearS[7]) {
			moveSlider(s7);
			checkCollision(s7);
		}
		if (appearS[8]) {
			moveSlider(s8);
			checkCollision(s8);
		}
		if (appearS[9]) {
			moveSlider(s9);
			checkCollision(s9);
		}
		if (appearR[1]) {
			moveReverse(r1);
			checkCollision(r1);
		}
		if (appearR[2]) {
			moveReverse(r2);
			checkCollision(r2);
		}
		if (appearR[3]) {
			moveReverse(r3);
			checkCollision(r3);
		}
		if (appearR[4]) {
			moveReverse(r4);
			checkCollision(r4);
		}
		if (appearR[5]) {
			moveReverse(r5);
			checkCollision(r5);
		}
		if (appearR[6]) {
			moveReverse(r6);
			checkCollision(r6);
		}
		if (appearR[7]) {
			moveReverse(r7);
			checkCollision(r7);
		}
		if (appearR[8]) {
			moveReverse(r8);
			checkCollision(r8);
		}
		if (appearR[9]) {
			moveReverse(r9);
			checkCollision(r9);
		}

	}

	// infinite game loop
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long now;

		while (true) {
			now = System.nanoTime();
			delta = delta + (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {
				move();
				repaint();
				delta--;
			}
		}
	}

	// Implementation for tutorial
	private void tutorial() {

		// remove all buttons onscreen
		this.remove(playButton);
		this.remove(exitButton);
		this.remove(tutorial);
		this.remove(easy);
		this.remove(medium);
		this.remove(hard);
		this.remove(backButton);

		// begin tutorial soundtrack
		playSound("Music/tutorial.wav");

		// add circles and sliders for map, with correct arguments
		add(c1, tutorialMap[0][0], tutorialMap[1][0], tutorialMap[2][0]);

		add(c2, tutorialMap[0][1], tutorialMap[1][1], tutorialMap[2][1]);
		add(c3, tutorialMap[0][2], tutorialMap[1][2], tutorialMap[2][2]);
		add(c4, tutorialMap[0][3], tutorialMap[1][3], tutorialMap[2][3]);
		add(c5, tutorialMap[0][4], tutorialMap[1][4], tutorialMap[2][4]);
		add(c6, tutorialMap[0][5], tutorialMap[1][5], tutorialMap[2][5]);
		add(c7, tutorialMap[0][6], tutorialMap[1][6], tutorialMap[2][6]);
		add(c8, tutorialMap[0][7], tutorialMap[1][7], tutorialMap[2][7]);
		add(c9, tutorialMap[0][8], tutorialMap[1][8], tutorialMap[2][8]);
		add(c1, tutorialMap[0][9], tutorialMap[1][9], tutorialMap[2][9]);
		add(c2, tutorialMap[0][10], tutorialMap[1][10], tutorialMap[2][10]);
		add(c3, tutorialMap[0][11], tutorialMap[1][11], tutorialMap[2][11]);
		add(c4, tutorialMap[0][12], tutorialMap[1][12], tutorialMap[2][12]);
		add(c5, tutorialMap[0][13], tutorialMap[1][13], tutorialMap[2][13]);

		add(s1, tutorialMap[0][14], tutorialMap[1][14], tutorialMap[2][14], 180, 0, 2.193);
		add(s2, tutorialMap[0][15], tutorialMap[1][15], tutorialMap[2][15], 100, 90, 0.650);
		add(r1, tutorialMap[0][16], tutorialMap[1][16], tutorialMap[2][16], 300, 270, 1.096);

		add(s4, tutorialMap[0][17], tutorialMap[1][17], tutorialMap[2][17], 300, 180, 1.807);
		add(c6, tutorialMap[0][18], tutorialMap[1][18], tutorialMap[2][18]);
		add(c7, tutorialMap[0][19], tutorialMap[1][19], tutorialMap[2][19]);
		add(s5, tutorialMap[0][20], tutorialMap[1][20], tutorialMap[2][20], 150, 350, 0.750);
		add(s6, tutorialMap[0][21], tutorialMap[1][21], tutorialMap[2][21], 150, 280, 0.456);
		add(c8, tutorialMap[0][22], tutorialMap[1][22], tutorialMap[2][22]);
		add(s7, tutorialMap[0][23], tutorialMap[1][23], tutorialMap[2][23], 300, 70, 1.609);
		add(c9, tutorialMap[0][24], tutorialMap[1][24], tutorialMap[2][24]);
		add(r2, tutorialMap[0][25], tutorialMap[1][25], tutorialMap[2][25], 500, 300, 0.954);
		add(s9, tutorialMap[0][26], tutorialMap[1][26], tutorialMap[2][26], 100, 200, 0.351);
		add(s1, tutorialMap[0][27], tutorialMap[1][27], tutorialMap[2][27], 300, 240, 1.406);
		add(c1, tutorialMap[0][28], tutorialMap[1][28], tutorialMap[2][28]);
		add(s2, tutorialMap[0][29], tutorialMap[1][29], tutorialMap[2][29], 250, 250, 1.193);
		add(c2, tutorialMap[0][20], tutorialMap[1][30], tutorialMap[2][30]);
		add(s3, tutorialMap[0][31], tutorialMap[1][31], tutorialMap[2][31], 220, 300, 1.405);
	}

	// code to run the Easy game mode
	private void easy() {
		// Implementation for medium
		this.remove(playButton);
		this.remove(exitButton);
		this.remove(tutorial);
		this.remove(easy);
		this.remove(medium);
		this.remove(hard);
		this.remove(backButton);
	}

	// code to run the Medium game mode
	private void medium() {

		this.remove(playButton);
		this.remove(exitButton);
		this.remove(tutorial);
		this.remove(easy);
		this.remove(medium);
		this.remove(hard);
		this.remove(backButton);

		playSound("Music/easy.wav");

		add(s1, mediumMap[0][0], mediumMap[1][0], mediumMap[2][0], 150, 0, 0.600);
		add(s2, mediumMap[0][1], mediumMap[1][1], mediumMap[2][1], 150, 0, 0.600);
		add(s3, mediumMap[0][2], mediumMap[1][2], mediumMap[2][2], 150, 310, 0.600);
		add(s4, mediumMap[0][3], mediumMap[1][3], mediumMap[2][3], 150, 80, 0.600);
		add(s5, mediumMap[0][4], mediumMap[1][4], mediumMap[2][4], 150, 200, 0.600);
		add(s6, mediumMap[0][5], mediumMap[1][5], mediumMap[2][5], 150, 150, 0.600);
		add(r1, mediumMap[0][6], mediumMap[1][6], mediumMap[2][6], 150, 80, 0.540);
		add(s7, mediumMap[0][7], mediumMap[1][7], mediumMap[2][7], 150, 190, 0.600);
		add(s8, mediumMap[0][8], mediumMap[1][8], mediumMap[2][8], 150, 20, 0.600);
		add(c1, mediumMap[0][9], mediumMap[1][9], mediumMap[2][9]);
		add(s9, mediumMap[0][10], mediumMap[1][10], mediumMap[2][10], 150, 80, 0.600);
		add(c2, mediumMap[0][11], mediumMap[1][11], mediumMap[2][11]);
		add(r2, mediumMap[0][12], mediumMap[1][12], mediumMap[2][12], 150, 160, 0.600);
		add(c3, mediumMap[0][13], mediumMap[1][13], mediumMap[2][13]);
		add(r3, mediumMap[0][14], mediumMap[1][14], mediumMap[2][14], 150, 30, 0.600);
		add(c4, mediumMap[0][15], mediumMap[1][15], mediumMap[2][15]);
		add(s1, mediumMap[0][16], mediumMap[1][16], mediumMap[2][16], 150, 280, 0.600);
		add(s2, mediumMap[0][17], mediumMap[1][17], mediumMap[2][17], 150, 100, 0.600);
		add(r4, mediumMap[0][18], mediumMap[1][18], mediumMap[2][18], 150, 170, 0.600);
		add(r5, mediumMap[0][19], mediumMap[1][19], mediumMap[2][19], 150, 10, 0.600);
		add(s3, mediumMap[0][20], mediumMap[1][20], mediumMap[2][20], 150, 250, 0.600);
		add(s4, mediumMap[0][21], mediumMap[1][21], mediumMap[2][21], 150, 70, 0.600);
		add(r6, mediumMap[0][22], mediumMap[1][22], mediumMap[2][22], 150, 20, 0.600);
		add(c5, mediumMap[0][23], mediumMap[1][23], mediumMap[2][23]);
		add(s5, mediumMap[0][24], mediumMap[1][24], mediumMap[2][24], 150, 100, 0.600);
		add(s6, mediumMap[0][25], mediumMap[1][25], mediumMap[2][25], 150, 280, 0.600);
		add(r7, mediumMap[0][26], mediumMap[1][26], mediumMap[2][26], 150, 10, 0.600);
		add(c6, mediumMap[0][27], mediumMap[1][27], mediumMap[2][27]);
		add(s7, mediumMap[0][28], mediumMap[1][28], mediumMap[2][28], 150, 270, 0.600);
		add(s8, mediumMap[0][29], mediumMap[1][29], mediumMap[2][29], 150, 190, 0.600);
		add(r8, mediumMap[0][30], mediumMap[1][30], mediumMap[2][30], 150, 340, 0.600);
		// add(c7, easyMap[0][31], easyMap[1][31], easyMap[2][31]);
	}

	// code to run the Hard game mode
	private void hard() {
		// Implementation for hard
		this.remove(playButton);
		this.remove(exitButton);
		this.remove(tutorial);
		this.remove(easy);
		this.remove(medium);
		this.remove(hard);
		this.remove(backButton);

	}

	// plays sound given a file name
	private void playSound(String soundFile) {
		File file;
		AudioInputStream audioStream;
		Clip clip;

		try {
			// open theme song file
			file = new File(soundFile);

			// Get audio input stream
			audioStream = AudioSystem.getAudioInputStream(file);
			// Open and play the theme song
			clip = AudioSystem.getClip();
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

	public void setBackgroundImage(String imagePath) {

		try {
			backgroundImage = ImageIO.read(new File(imagePath));
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private static BufferedImage loadImage(String imagePath) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	@Override
	public void keyTyped(KeyEvent e) {

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
