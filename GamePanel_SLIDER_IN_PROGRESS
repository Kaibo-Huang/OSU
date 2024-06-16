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

	JLabel osuLabel, backLabel, titleLabel;

	// check if circle or slider has appeared
	private static boolean[] appearC = new boolean[10];
	private static boolean[] appearS = new boolean[10];
	private static boolean[] appearR = new boolean[10];

	// declare 9 circle and slider objects
	private Circle c1, c2, c3, c4, c5, c6, c7, c8, c9;
	private Slider s1, s2, s3, s4, s5, s6, s7, s8, s9;
	private Reverse r1, r2, r3, r4, r5, r6, r7, r8, r9;

	private JButton playButton, exitButton; // menu buttons
	private JButton tutorial, easy, medium, hard, play;// level buttons

	boolean playTutorial, playEasy, playMedium, playHard;

	private boolean isTitleScreen = true, endScreen = false;;
	private BufferedImage backgroundImage, logo, back, title;
	private BufferedImage Score300 = loadImage("Images/300red.jpg");
	private BufferedImage Score100 = loadImage("Images/100red.jpg");

	private static int mX, mY;
	private byte level;

	private int totalMisses = 0, clicked300 = 0, clicked100 = 0, maxCombo = 0;
	private int totalTutorial = 300 * 32, totalEasy = 300 * 65, totalMedium = 300 * 31, totalHard = 300 * 53;

	// map elements (x position, y position, time of placement)
	private int[][] tutorialMap = {
			{ 640, 238, 1039, 999, 1039, 238, 511, 637, 650, 919, 1200, 644, 363, 85, 834, 438, 847, 679, 90, 158, 235,
					341, 651, 879, 529, 120, 529, 962, 1142, 654, 100, 554, 150, 150 },
			{ 360, 130, 130, 389, 636, 646, 237, 100, 646, 517, 389, 124, 253, 383, 318, 319, 650, 311, 383, 559, 732,
					333, 48, 72, 696, 550, 676, 676, 120, 561, 674, 710, 360, 500 },
			{ 3491, 20106, 21952, 22875, 23798, 25645, 27029, 27491, 29337, 30260, 31183, 33029, 33952, 34875, 57029,
					60722, 62568, 86568, 89337, 89799, 90260, 92106, 93491, 93953, 96722, 97645, 100414, 101337, 104106,
					105029, 107799, 108722, 112270, 112995 } };

	private int[][] easyMap = {
			{ 273, 471, 1202, 641, 80, 491, 1222, 992, 411, 1192, 165, 601, 1002, 210, 431, 611, 1152, 1022, 701, 521,
					160, 601, 1102, 341, 263, 528, 912, 491, 932, 200, 141, 340, 1222, 471, 100, 852, 1092, 521, 130,
					691, 1212, 611, 311, 481, 1052, 791, 90, 180, 571, 1192, 621, 802, 300, 401, 862, 120, 464, 221,
					912, 972, 200, 611, 600, 871, 200 },
			{ 619, 96, 233, 690, 233, 481, 361, 72, 561, 473, 656, 626, 377, 201, 209, 104, 241, 385, 587, 289, 80, 545,
					257, 112, 373, 231, 300, 489, 257, 136, 529, 600, 433, 569, 241, 112, 626, 553, 241, 120, 409, 377,
					144, 561, 104, 473, 361, 513, 473, 345, 545, 441, 184, 553, 136, 607, 519, 289, 160, 634, 529, 120,
					580, 634, 385 },
			{ 239, 1635, 3030, 4360, 5821, 7216, 8612, 9309, 11402, 12798, 15188, 16016, 17681, 18777, 21519, 21866,
					22914, 23263, 24658, 26053, 28146, 29497, 30846, 32194, 33542, 38915, 39947, 41632, 42981, 44329,
					45677, 47026, 48374, 49722, 51071, 52419, 53767, 54778, 55790, 56801, 57812, 59160, 60509, 63205,
					65902, 67250, 68599, 70284, 70958, 72306, 73992, 75677, 76688, 79385, 82082, 83430, 84778, 88823,
					90172, 91520, 92868, 94217, 95565, 96913, 98262 } };

	private int[][] mediumMap = {
			{ 177, 596, 934, 1154, 1175, 779, 777, 531, 288, 718, 919, 804, 596, 779, 962, 779, 944, 741, 551, 724, 313,
					478, 253, 113, 293, 524, 644, 867, 1172, 867, 723 },
			{ 319, 405, 251, 148, 513, 359, 537, 233, 395, 339, 419, 687, 616, 509, 615, 509, 387, 158, 457, 337, 201,
					48, 371, 397, 537, 738, 469, 622, 612, 431, 251 },
			{ 433, 1393, 2353, 3313, 4273, 5233, 6193, 8113, 9073, 10033, 10513, 11473, 11953, 13393, 13873, 15313,
					15793, 16753, 17713, 19153, 20593, 21553, 22513, 23953, 24433, 25393, 26353, 27793, 28273, 29233,
					30193 } };
	private int[][] hardMap = {
			{ 160, 378, 521, 939, 237, 1100, 193, 1060, 746, 559, 491, 849, 180, 1177, 135, 1082, 862, 288, 76, 548,
					1145, 343, 754, 303, 1072, 105, 381, 577, 223, 391, 766, 90, 378, 518, 1090, 470, 902, 688, 711,
					1037, 840, 1280, 1012, 338, 110, 854, 683, 528, 738, 1010, 626, 772, 834 },
			{ 321, 603, 257, 489, 178, 457, 592, 132, 491, 305, 616, 134, 433, 279, 146, 635, 527, 225, 569, 718, 303,
					431, 539, 52, 493, 355, 678, 221, 221, 450, 164, 305, 190, 676, 580, 457, 477, 90, 251, 455, 250,
					537, 700, 287, 540, 110, 495, 132, 203, 590, 225, 62, 515 },
			{ 4957, 5616, 6268, 6901, 7493, 8140, 8725, 9319, 9876, 10420, 10961, 11394, 11890, 12338, 12796, 13237,
					13456, 14096, 14515, 14935, 15354, 15774, 16193, 16621, 17257, 17668, 18078, 18489, 18875, 19283,
					19834, 20414, 20775, 21167, 21528, 21911, 22272, 22684, 23045, 23407, 23771, 24134, 24541, 25086,
					25495, 25850, 26441, 26981, 27344, 27753, 28491, 28673, 29131 } };

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

		isTitleScreen = true;
		titleLabel = new JLabel();
		titleLabel.setBounds(0, 0, GAME_WIDTH, GAME_HEIGHT);

		title = loadImage("Images/titleBackground.jpg");
		titleLabel.setIcon(new ImageIcon(title));

		osuLabel = new JLabel();
		osuLabel.setBounds(GAME_WIDTH / 2 - 225, GAME_HEIGHT / 2 - 225, 450, 450);
		this.add(osuLabel);
		this.add(titleLabel);

		logo = loadImage("Images/Logo.jpg");

		osuLabel.setIcon(new ImageIcon(logo));

		osuLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				osuLabel.setBounds(GAME_WIDTH / 2 - 275, GAME_HEIGHT / 2 - 225, 450, 450);
				playClicks("Music/MARUClick.wav");
				showGameOptions();
			}
		});

		this.setVisible(true);

		// add PLAY button
		playButton = new JButton("PLAY");
		playButton.setFont(new Font("Arial", Font.PLAIN, 24));
		playButton.setBorderPainted(false);
		playButton.setFocusPainted(false);
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playClicks("Music/PlayClick.wav");
				showLevels(); // calls startGame when pressed
			}
		});

		// add EXIT button
		exitButton = new JButton("EXIT");
		exitButton.setFont(new Font("Arial", Font.PLAIN, 24));
		exitButton.setFocusPainted(false);
		exitButton.setBorderPainted(false);


		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0); // exits when pressed
			}
		});
		play = new JButton("PLAY!");
		play.setFont(new Font("Arial", Font.PLAIN, 24));
		play.setFocusPainted(false);
		play.setBorderPainted(false);
		play.setContentAreaFilled(false);
		// add Tutorial button
		tutorial = new JButton("Tutorial");
		tutorial.setFont(new Font("Arial", Font.PLAIN, 24));
		tutorial.setFocusPainted(false);
		tutorial.setBorderPainted(false);

		tutorial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				level = 0;
				addPlayButton();

			}
		});

		// add Easy button
		easy = new JButton("Easy");
		easy.setFont(new Font("Arial", Font.PLAIN, 24));
		easy.setFocusPainted(false);
		easy.setBorderPainted(false);

		easy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				level = 1;
				addPlayButton();

			}
		});

		// add Medium button
		medium = new JButton("Medium");
		medium.setFont(new Font("Arial", Font.PLAIN, 24));
		medium.setFocusPainted(false);
		medium.setBorderPainted(false);

		medium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				level = 2;
				addPlayButton();

			}
		});

		// add Hard button
		hard = new JButton("Hard");
		hard.setFont(new Font("Arial", Font.PLAIN, 24));
		hard.setFocusPainted(false);
		hard.setBorderPainted(false);
		hard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				level = 3;
				addPlayButton();

			}
		});

		backLabel = new JLabel();
		back = loadImage("Images/back.jpg");

		backLabel.setIcon(new ImageIcon(back));

		backLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				backLabel.setBounds(50, GAME_HEIGHT - 100, 200, 50);
				playClicks("Music/MARUClick.wav");
				showGameOptions();
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
		osuLabel.setBounds(GAME_WIDTH / 4, GAME_HEIGHT / 2 - 225, 450, 450);
		playButton.setBounds(GAME_WIDTH / 2 + 100, GAME_HEIGHT / 2 - 50, 200, 50);
		exitButton.setBounds(GAME_WIDTH / 2 + 100, GAME_HEIGHT / 2 + 40, 200, 50);

		this.add(osuLabel);
		this.add(playButton);
		this.add(exitButton);
		this.add(titleLabel);
		this.repaint();
	}

	// adds the buttons to show the level
	public void showLevels() {
		this.removeAll();

		tutorial.setBounds(GAME_WIDTH / 2 + 200, GAME_HEIGHT / 4 - 50, 200, 50);
		easy.setBounds(GAME_WIDTH / 2 + 200, GAME_HEIGHT / 4 + 50, 200, 50);
		medium.setBounds(GAME_WIDTH / 2 + 200, GAME_HEIGHT / 2 - 50, 200, 50);
		hard.setBounds(GAME_WIDTH / 2 + 200, GAME_HEIGHT / 2 + 50, 200, 50);
		play.setBounds(GAME_WIDTH - 250, GAME_HEIGHT - 100, 200, 50);
		play.setVisible(false);
		backLabel.setBounds(50, GAME_HEIGHT - 340, 450, 450);

		this.add(tutorial);
		this.add(easy);
		this.add(medium);
		this.add(hard);
		this.add(play);
		this.add(backLabel);
		this.add(titleLabel);

		totalMisses = 0;
		clicked300 = 0;
		clicked100 = 0;
		maxCombo = 0;

		this.repaint();
	}

	public void addPlayButton() {
	    play.setVisible(true);
	    
	    // Remove any existing ActionListeners
	    ActionListener[] listeners = play.getActionListeners();
	    for (ActionListener listener : listeners) {
	        play.removeActionListener(listener);
	    }
	    
	    // Add the new ActionListener
	    play.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            playClicks("Music/PlayClick.wav");
	            isTitleScreen = false;

	            stopMenu();
	            if (level == 0) {
	                playTutorial = true;
	                tutorial();
	            } else if (level == 1) {
	                playEasy = true;
	                easy();
	            } else if (level == 2) {
	                playMedium = true;
	                medium();
	            } else if (level == 3) {
	                playHard = true;
	                hard();
	            }
	        }
	    });
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
			setBackgroundImage("Images/tutorial.jpg");
			g.drawImage(backgroundImage, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
			Circle.c = Color.green;
			Slider.c = Color.green;
			Reverse.c = Color.green;
			Slider.moveC = Color.yellow;
			Reverse.moveC = Color.yellow;
		} else if (playEasy) {
			setBackgroundImage("Images/easy.jpg");
			g.drawImage(backgroundImage, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
			Circle.c = Color.pink;
			Slider.c = Color.pink;
			Reverse.c = Color.pink;
			Slider.moveC = Color.yellow;
			Reverse.moveC = Color.yellow;
		} else if (playMedium) {
			setBackgroundImage("Images/medium.jpg");
			g.drawImage(backgroundImage, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
			Circle.c = Color.blue;
			Slider.c = Color.blue;
			Reverse.c = Color.blue;
			Slider.moveC = Color.yellow;
			Reverse.moveC = Color.yellow;
		} else if (playHard) {
			setBackgroundImage("Images/hard.jpg");
			g.drawImage(backgroundImage, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
			Circle.c = Color.magenta;
			Slider.c = Color.magenta;
			Reverse.c = Color.magenta;
			Slider.moveC = Color.yellow;
			Reverse.moveC = Color.yellow;
		} else if (endScreen) {
			setBackgroundImage("Images/easy.jpg");
			g.drawImage(backgroundImage, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
		}
		if (isTitleScreen) {
			setBackgroundImage("Images/titleBackground.jpg");
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
		if (appearC[1]) {
			if (c1.scoreState == 3) {
				Timer timer = new Timer();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						g.drawImage(Score300, c1.initialX, c1.initialY, 200, 200, null);
					}
				};

				// Schedule the task to run once, after a delay of 0 milliseconds,
				// and then every 1000 milliseconds (1 second) thereafter
				timer.scheduleAtFixedRate(task, 0, 200);

				// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						timer.cancel(); // Cancel the timer to stop further executions
						c1.scoreState = 0; // Reset scoreState after starting the timer
					}
				}, 200);

			} else if (c1.scoreState == 1) {
				Timer timer = new Timer();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						g.drawImage(Score100, c1.initialX, c1.initialY, 200, 200, null);
					}
				};

				// Schedule the task to run once, after a delay of 0 milliseconds,
				// and then every 1000 milliseconds (1 second) thereafter
				timer.scheduleAtFixedRate(task, 0, 200);

				// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						timer.cancel(); // Cancel the timer to stop further executions
						c1.scoreState = 0; // Reset scoreState after starting the timer
					}
				}, 200);
			}
		}
		if (appearC[2]) {
			if (c2.scoreState == 3) {
				Timer timer = new Timer();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						g.drawImage(Score300, c2.initialX, c2.initialY, 200, 200, null);
					}
				};

				// Schedule the task to run once, after a delay of 0 milliseconds,
				// and then every 1000 milliseconds (1 second) thereafter
				timer.scheduleAtFixedRate(task, 0, 200);

				// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						timer.cancel(); // Cancel the timer to stop further executions
						c2.scoreState = 0; // Reset scoreState after starting the timer
					}
				}, 200);

			} else if (c2.scoreState == 1) {
				Timer timer = new Timer();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						g.drawImage(Score100, c2.initialX, c2.initialY, 200, 200, null);
					}
				};

				// Schedule the task to run once, after a delay of 0 milliseconds,
				// and then every 1000 milliseconds (1 second) thereafter
				timer.scheduleAtFixedRate(task, 0, 200);

				// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						timer.cancel(); // Cancel the timer to stop further executions
						c2.scoreState = 0; // Reset scoreState after starting the timer
					}
				}, 200);
			}
		}
		if (appearC[3]) {
			if (c3.scoreState == 3) {
				Timer timer = new Timer();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						g.drawImage(Score300, c3.initialX, c3.initialY, 200, 200, null);
					}
				};

				// Schedule the task to run once, after a delay of 0 milliseconds,
				// and then every 1000 milliseconds (1 second) thereafter
				timer.scheduleAtFixedRate(task, 0, 200);

				// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						timer.cancel(); // Cancel the timer to stop further executions
						c3.scoreState = 0; // Reset scoreState after starting the timer
					}
				}, 200);

			} else if (c3.scoreState == 1) {
				Timer timer = new Timer();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						g.drawImage(Score100, c3.initialX, c3.initialY, 200, 200, null);
					}
				};

				// Schedule the task to run once, after a delay of 0 milliseconds,
				// and then every 1000 milliseconds (1 second) thereafter
				timer.scheduleAtFixedRate(task, 0, 200);

				// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						timer.cancel(); // Cancel the timer to stop further executions
						c3.scoreState = 0; // Reset scoreState after starting the timer
					}
				}, 200);
			}
		}

		if (appearC[4]) {

			if (c4.scoreState == 3) {
				Timer timer = new Timer();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						g.drawImage(Score300, c4.initialX, c4.initialY, 200, 200, null);
					}
				};

				// Schedule the task to run once, after a delay of 0 milliseconds,
				// and then every 1000 milliseconds (1 second) thereafter
				timer.scheduleAtFixedRate(task, 0, 200);

				// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						timer.cancel(); // Cancel the timer to stop further executions
						c4.scoreState = 0; // Reset scoreState after starting the timer
					}
				}, 200);

			} else if (c4.scoreState == 1) {
				Timer timer = new Timer();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						g.drawImage(Score100, c4.initialX, c4.initialY, 200, 200, null);
					}
				};

				// Schedule the task to run once, after a delay of 0 milliseconds,
				// and then every 1000 milliseconds (1 second) thereafter
				timer.scheduleAtFixedRate(task, 0, 200);

				// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						timer.cancel(); // Cancel the timer to stop further executions
						c4.scoreState = 0; // Reset scoreState after starting the timer
					}
				}, 200);
			}
		}
		if (appearC[5]) {
			if (c5.scoreState == 3) {
				Timer timer = new Timer();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						g.drawImage(Score300, c5.initialX, c5.initialY, 200, 200, null);
					}
				};

				// Schedule the task to run once, after a delay of 0 milliseconds,
				// and then every 1000 milliseconds (1 second) thereafter
				timer.scheduleAtFixedRate(task, 0, 200);

				// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						timer.cancel(); // Cancel the timer to stop further executions
						c5.scoreState = 0; // Reset scoreState after starting the timer
					}
				}, 200);

			} else if (c5.scoreState == 1) {
				Timer timer = new Timer();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						g.drawImage(Score100, c5.initialX, c5.initialY, 200, 200, null);
					}
				};

				// Schedule the task to run once, after a delay of 0 milliseconds,
				// and then every 1000 milliseconds (1 second) thereafter
				timer.scheduleAtFixedRate(task, 0, 200);

				// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						timer.cancel(); // Cancel the timer to stop further executions
						c5.scoreState = 0; // Reset scoreState after starting the timer
					}
				}, 200);
			}
		}
		if (appearC[6]) {
			if (c6.scoreState == 3) {
				Timer timer = new Timer();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						g.drawImage(Score300, c6.initialX, c6.initialY, 200, 200, null);
					}
				};

				// Schedule the task to run once, after a delay of 0 milliseconds,
				// and then every 1000 milliseconds (1 second) thereafter
				timer.scheduleAtFixedRate(task, 0, 200);

				// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						timer.cancel(); // Cancel the timer to stop further executions
						c6.scoreState = 0; // Reset scoreState after starting the timer
					}
				}, 200);

			} else if (c6.scoreState == 1) {
				Timer timer = new Timer();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						g.drawImage(Score100, c6.initialX, c6.initialY, 200, 200, null);
					}
				};

				// Schedule the task to run once, after a delay of 0 milliseconds,
				// and then every 1000 milliseconds (1 second) thereafter
				timer.scheduleAtFixedRate(task, 0, 200);

				// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						timer.cancel(); // Cancel the timer to stop further executions
						c6.scoreState = 0; // Reset scoreState after starting the timer
					}
				}, 200);
			}
		}
		if (appearC[7]) {
			if (c7.scoreState == 3) {
				Timer timer = new Timer();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						g.drawImage(Score300, c7.initialX, c7.initialY, 200, 200, null);
					}
				};

				// Schedule the task to run once, after a delay of 0 milliseconds,
				// and then every 1000 milliseconds (1 second) thereafter
				timer.scheduleAtFixedRate(task, 0, 200);

				// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						timer.cancel(); // Cancel the timer to stop further executions
						c7.scoreState = 0; // Reset scoreState after starting the timer
					}
				}, 200);

			} else if (c7.scoreState == 1) {
				Timer timer = new Timer();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						g.drawImage(Score100, c7.initialX, c7.initialY, 200, 200, null);
					}
				};

				// Schedule the task to run once, after a delay of 0 milliseconds,
				// and then every 1000 milliseconds (1 second) thereafter
				timer.scheduleAtFixedRate(task, 0, 200);

				// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						timer.cancel(); // Cancel the timer to stop further executions
						c7.scoreState = 0; // Reset scoreState after starting the timer
					}
				}, 200);
			}
		}
		if (appearC[8]) {

			if (c8.scoreState == 3) {
				Timer timer = new Timer();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						g.drawImage(Score300, c8.initialX, c8.initialY, 200, 200, null);
					}
				};

				// Schedule the task to run once, after a delay of 0 milliseconds,
				// and then every 1000 milliseconds (1 second) thereafter
				timer.scheduleAtFixedRate(task, 0, 200);

				// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						timer.cancel(); // Cancel the timer to stop further executions
						c8.scoreState = 0; // Reset scoreState after starting the timer
					}
				}, 200);

			} else if (c8.scoreState == 1) {
				Timer timer = new Timer();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						g.drawImage(Score100, c8.initialX, c8.initialY, 200, 200, null);
					}
				};

				// Schedule the task to run once, after a delay of 0 milliseconds,
				// and then every 1000 milliseconds (1 second) thereafter
				timer.scheduleAtFixedRate(task, 0, 200);

				// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						timer.cancel(); // Cancel the timer to stop further executions
						c8.scoreState = 0; // Reset scoreState after starting the timer
					}
				}, 200);
			}
		}
		if (appearC[9]) {
			if (c9.scoreState == 3) {
				Timer timer = new Timer();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						g.drawImage(Score300, c9.initialX, c9.initialY, 200, 200, null);
					}
				};

				// Schedule the task to run once, after a delay of 0 milliseconds,
				// and then every 1000 milliseconds (1 second) thereafter
				timer.scheduleAtFixedRate(task, 0, 200);

				// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						timer.cancel(); // Cancel the timer to stop further executions
						c9.scoreState = 0; // Reset scoreState after starting the timer
					}
				}, 200);

			} else if (c9.scoreState == 1) {
				Timer timer = new Timer();

				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						g.drawImage(Score100, c9.initialX, c9.initialY, 200, 200, null);
					}
				};

				// Schedule the task to run once, after a delay of 0 milliseconds,
				// and then every 1000 milliseconds (1 second) thereafter
				timer.scheduleAtFixedRate(task, 0, 200);

				// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						timer.cancel(); // Cancel the timer to stop further executions
						c9.scoreState = 0; // Reset scoreState after starting the timer
					}
				}, 200);
			}
		}

		if (s1.scoreState == 3) {

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score300, s1.scoreX - 100, s1.scoreY - 150, 200, 200, null);
				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					s1.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);

		} else if (s1.scoreState == 1) {
			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					g.drawImage(Score100, s1.scoreX - 100, s1.scoreY - 150, 200, 200, null);
				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					s1.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);
		}

		if (s2.scoreState == 3) {

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score300, s2.scoreX - 100, s2.scoreY - 150, 200, 200, null);
				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					s2.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);

		} else if (s2.scoreState == 1) {
			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					g.drawImage(Score100, s2.scoreX - 100, s2.scoreY - 150, 200, 200, null);
				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					s2.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);
		}

		if (s3.scoreState == 3) {

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score300, s3.scoreX - 100, s3.scoreY - 150, 200, 200, null);
				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					s3.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);

		} else if (s3.scoreState == 1) {
			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					g.drawImage(Score100, s3.scoreX - 100, s3.scoreY - 150, 200, 200, null);
				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					s3.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);
		}

		if (s4.scoreState == 3) {

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score300, s4.scoreX - 100, s4.scoreY - 150, 200, 200, null);
				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					s4.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);

		} else if (s4.scoreState == 1) {
			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					g.drawImage(Score100, s4.scoreX - 100, s4.scoreY - 150, 200, 200, null);
				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					s4.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);
		}

		if (s5.scoreState == 3) {

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score300, s5.scoreX - 100, s5.scoreY - 150, 200, 200, null);
				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					s5.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);

		} else if (s5.scoreState == 1) {
			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					g.drawImage(Score100, s5.scoreX - 100, s5.scoreY - 150, 200, 200, null);
				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					s5.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);
		}

		if (s6.scoreState == 3) {

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score300, s6.scoreX - 100, s6.scoreY - 150, 200, 200, null);
				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					s6.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);

		} else if (s6.scoreState == 1) {
			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					g.drawImage(Score100, s6.scoreX - 100, s6.scoreY - 150, 200, 200, null);
				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					s6.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);
		}

		if (s7.scoreState == 3) {

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score300, s7.scoreX - 100, s7.scoreY - 150, 200, 200, null);
				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					s7.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);

		} else if (s7.scoreState == 1) {
			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					g.drawImage(Score100, s7.scoreX - 100, s7.scoreY - 150, 200, 200, null);
				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					s7.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);
		}

		if (s8.scoreState == 3) {

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score300, s8.scoreX - 100, s8.scoreY - 150, 200, 200, null);
				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					s8.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);

		} else if (s8.scoreState == 1) {
			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					g.drawImage(Score100, s8.scoreX - 100, s8.scoreY - 150, 200, 200, null);
				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					s8.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);
		}

		if (s9.scoreState == 3) {

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score300, s9.scoreX - 100, s9.scoreY - 150, 200, 200, null);
				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					s9.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);

		} else if (s9.scoreState == 1) {
			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					g.drawImage(Score100, s9.scoreX - 100, s9.scoreY - 150, 200, 200, null);
				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					s9.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);
		}

		if (r1.scoreState == 3) {

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score300, r1.scoreX - 100, r1.scoreY - 200, 200, 200, null);

				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					r1.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);

		} else if (r1.scoreState == 1) {
			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score100, r1.scoreX - 100, r1.scoreY - 200, 200, 200, null);

				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					r1.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);
		}

		if (r2.scoreState == 3) {

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score300, r2.scoreX - 100, r2.scoreY - 200, 200, 200, null);

				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					r2.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);

		} else if (r2.scoreState == 1) {
			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score100, r2.scoreX - 100, r2.scoreY - 200, 200, 200, null);

				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					r2.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);
		}

		if (r3.scoreState == 3) {

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score300, r3.scoreX - 100, r3.scoreY - 200, 200, 200, null);

				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					r3.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);

		} else if (r3.scoreState == 1) {
			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score100, r3.scoreX - 100, r3.scoreY - 200, 200, 200, null);

				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					r3.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);
		}

		if (r4.scoreState == 3) {

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score300, r4.scoreX - 100, r4.scoreY - 200, 200, 200, null);

				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					r4.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);

		} else if (r4.scoreState == 1) {
			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score100, r4.scoreX - 100, r4.scoreY - 200, 200, 200, null);

				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					r4.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);
		}

		if (r5.scoreState == 3) {

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score300, r5.scoreX - 100, r5.scoreY - 200, 200, 200, null);

				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					r5.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);

		} else if (r5.scoreState == 1) {
			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score100, r5.scoreX - 100, r5.scoreY - 200, 200, 200, null);

				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					r5.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);
		}

		if (r6.scoreState == 3) {

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score300, r6.scoreX - 100, r6.scoreY - 200, 200, 200, null);

				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					r6.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);

		} else if (r6.scoreState == 1) {
			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score100, r6.scoreX - 100, r6.scoreY - 200, 200, 200, null);

				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					r6.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);
		}

		if (r7.scoreState == 3) {

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score300, r7.scoreX - 100, r7.scoreY - 200, 200, 200, null);

				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					r7.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);

		} else if (r7.scoreState == 1) {
			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score100, r7.scoreX - 100, r7.scoreY - 200, 200, 200, null);

				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					r7.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);
		}

		if (r8.scoreState == 3) {

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score300, r8.scoreX - 100, r8.scoreY - 200, 200, 200, null);

				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					r8.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);

		} else if (r8.scoreState == 1) {
			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score100, r8.scoreX - 100, r8.scoreY - 200, 200, 200, null);

				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					r8.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);
		}

		if (r9.scoreState == 3) {

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score300, r9.scoreX - 100, r9.scoreY - 200, 200, 200, null);

				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					r9.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);

		} else if (r9.scoreState == 1) {
			Timer timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					g.drawImage(Score100, r9.scoreX - 100, r9.scoreY - 200, 200, 200, null);

				}
			};

			// Schedule the task to run once, after a delay of 0 milliseconds,
			// and then every 1000 milliseconds (1 second) thereafter
			timer.scheduleAtFixedRate(task, 0, 400);

			// Schedule a timer to cancel the task after 1 second (1000 milliseconds)
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					timer.cancel(); // Cancel the timer to stop further executions
					r9.scoreState = 0; // Reset scoreState after starting the timer
				}
			}, 400);
		}

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
					s.scoreState = 3;
					clicked300++;
					maxCombo = Math.max(combo, maxCombo);

				} else if (s.goodClick == true) {
					combo++;
					Score.score += 100 * combo;
					s.scoreState = 1;
					clicked100++;
					maxCombo = Math.max(combo, maxCombo);

				} else {
					combo = 0;
					totalMisses++;

				}
				appearS[s.id] = false;
				s.scoreX = s.initialX;
				s.scoreY = s.initialY;

				s.initialX = 2000;
				s.initialY = 2000;

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
						r.scoreState = 3;
						clicked300++;
						maxCombo = Math.max(combo, maxCombo);

					} else if (r.goodClick == true) {
						combo++;
						Score.score += 100 * combo;
						r.scoreState = 1;
						clicked100++;
						maxCombo = Math.max(combo, maxCombo);

					} else {
						combo = 0;
						totalMisses++;

					}
					appearR[r.id] = false;
					r.scoreX = r.initialX;
					r.scoreY = r.initialY;

					r.initialX = 2000;
					r.initialY = 2000;

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
					mX = e.getX();
					mY = e.getY();

					// Check if the click falls inside the circle
					if (s.isMouseClickedInside(mX, mY) && !s.isClicked) {
						s.isClicked = true;

						if (s.moveRadius <= 130) {
							s.goodClick = true;
							playClicks("Music/clickSound.wav");
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

			addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					// Get the coordinates of the mouse
					mX = e.getX();
					mY = e.getY();

				}
			});

			// checks for 'z' or 'x' keys
			addKeyListener(new KeyAdapter() {
				@Override

				public void keyPressed(KeyEvent e) {

					if (e.getKeyCode() == KeyEvent.VK_Z) {
						s.zPressed = true;

						if (s.isMouseClickedInside(mX, mY) && !s.isClicked) {
							s.isClicked = true;

							if (s.moveRadius <= 130) {
								s.goodClick = true;
								playClicks("Music/clickSound.wav");
							} else {
								s.goodClick = false;
							}
						}
					} else if (e.getKeyCode() == KeyEvent.VK_X) {
						s.xPressed = true;
						if (s.isMouseClickedInside(mX, mY) && !s.isClicked) {
							s.isClicked = true;

							if (s.moveRadius <= 130) {
								s.goodClick = true;
								playClicks("Music/clickSound.wav");
							} else {
								s.goodClick = false;
							}
						}
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

			s.moveRadius = 150;

			addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
					s.mousePressed = false;
				}
			});

			addKeyListener(new KeyAdapter() {

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
					mX = e.getX();
					mY = e.getY();

					// Transform the mouse coordinates to the rotated coordinate system
					AffineTransform at = new AffineTransform();
					at.rotate(-Math.toRadians(s.angle), s.initialX, s.initialY);
					Point transformedPoint = new Point(mX, mY);
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
					mX = e.getX();
					mY = e.getY();

					// Check if the click falls inside the circle
					if (r.isMouseClickedInside(mX, mY) && !r.isClicked) {
						r.isClicked = true;
						if (r.moveRadius <= 130) {
							r.goodClick = true;

							playClicks("Music/clickSound.wav");
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

			addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					// Get the coordinates of the mouse
					mX = e.getX();
					mY = e.getY();

				}
			});

			// checks for 'z' or 'x' keys
			addKeyListener(new KeyAdapter() {
				@Override

				public void keyPressed(KeyEvent e) {

					if (e.getKeyCode() == KeyEvent.VK_Z) {
						r.zPressed = true;
						if (r.isMouseClickedInside(mX, mY) && !r.isClicked) {
							r.isClicked = true;

							if (r.moveRadius <= 130) {
								r.goodClick = true;
								playClicks("Music/clickSound.wav");
							} else {
								r.goodClick = false;
							}
						}
					} else if (e.getKeyCode() == KeyEvent.VK_X) {
						r.xPressed = true;
						if (r.isMouseClickedInside(mX, mY) && !r.isClicked) {
							r.isClicked = true;

							if (r.moveRadius <= 130) {
								r.goodClick = true;
								playClicks("Music/clickSound.wav");
							} else {
								r.goodClick = false;
							}
						}
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
			r.moveRadius = 150;
			addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
					r.mousePressed = false;
				}
			});

			addKeyListener(new KeyAdapter() {

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
					mX = e.getX();
					mY = e.getY();

					// Transform the mouse coordinates to the rotated coordinate system
					AffineTransform at = new AffineTransform();
					at.rotate(-Math.toRadians(r.angle), r.initialX, r.initialY);
					Point transformedPoint = new Point(mX, mY);
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
				mX = e.getX();
				mY = e.getY();

				// Check if the click falls inside the circle
				if (c.isMouseClickedInside(mX, mY) && c.isClicked == false) {
					c.isClicked = true;
					if (c.moveRadius <= 120) {
						combo++;
						Score.score += 300 * combo;
						c.scoreState = 3;
						clicked300++;
						maxCombo = Math.max(combo, maxCombo);
						repaint();
						playClicks("Music/clickSound.wav");
					} else if (c.moveRadius <= 130) {
						combo++;
						Score.score += 100 * combo;
						playClicks("Music/clickSound.wav");
						clicked100++;
						maxCombo = Math.max(combo, maxCombo);
						repaint();
						c.scoreState = 1;
					}

					else {
						c.moveRadius = 80;
						combo = 0;
						totalMisses++;
					}
				}
			}

		});

		if (c.moveRadius == 80 && c.isClicked == false) {
			combo = 0;
		}

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_X) {
					// Get the current mouse location
					Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
					mX = mouseLocation.x;
					mY = mouseLocation.y;

					// Convert to component's coordinate system
					Point componentLocation = getLocationOnScreen();
					mX -= componentLocation.x;
					mY -= componentLocation.y;

					if (c.isMouseClickedInside(mX, mY) && c.isClicked == false) {
						c.isClicked = true;
						if (c.moveRadius <= 120) {
							combo++;
							Score.score += 300 * combo;
							c.scoreState = 3;

							repaint();
							playClicks("Music/clickSound.wav");
						} else if (c.moveRadius <= 130) {
							combo++;
							Score.score += 100 * combo;
							playClicks("Music/clickSound.wav");
							repaint();
							c.scoreState = 1;
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
	public void add(Circle c, int x, int y, long t, int n) {
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
				c.num = n;

			}
		};
		timer.schedule(task, t);
	}

	// method to add slider objects with correct coordinates, time, length, angle
	// and speed
	public void add(Slider s, int x, int y, long t, int l, int a, double mT, int n) {
		Timer timer = new Timer();

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
				s.isClicked = false;
				s.num = n;
			}
		};
		timer.schedule(task, t);

	}

	public void add(Reverse r, int x, int y, long t, int l, int a, double mT, int n) {
		Timer timer = new Timer();

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
				r.isClicked = false;
				r.num = n;
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
		this.remove(backLabel);

		// begin tutorial soundtrack
		playSound("Music/tutorial.wav");

		// add circles and sliders for map, with correct arguments
		add(c1, tutorialMap[0][0], tutorialMap[1][0], tutorialMap[2][0], 1);

		add(c2, tutorialMap[0][1], tutorialMap[1][1], tutorialMap[2][1], 1);
		add(c3, tutorialMap[0][2], tutorialMap[1][2], tutorialMap[2][2], 2);
		add(c4, tutorialMap[0][3], tutorialMap[1][3], tutorialMap[2][3], 3);
		add(c5, tutorialMap[0][4], tutorialMap[1][4], tutorialMap[2][4], 4);
		add(c6, tutorialMap[0][5], tutorialMap[1][5], tutorialMap[2][5], 5);
		add(c7, tutorialMap[0][6], tutorialMap[1][6], tutorialMap[2][6], 6);
		add(c8, tutorialMap[0][7], tutorialMap[1][7], tutorialMap[2][7], 7);
		add(c9, tutorialMap[0][8], tutorialMap[1][8], tutorialMap[2][8], 8);
		add(c1, tutorialMap[0][9], tutorialMap[1][9], tutorialMap[2][9], 9);
		add(c2, tutorialMap[0][10], tutorialMap[1][10], tutorialMap[2][10], 1);
		add(c3, tutorialMap[0][11], tutorialMap[1][11], tutorialMap[2][11], 2);
		add(c4, tutorialMap[0][12], tutorialMap[1][12], tutorialMap[2][12], 3);
		add(c5, tutorialMap[0][13], tutorialMap[1][13], tutorialMap[2][13], 4);

		add(s1, tutorialMap[0][14], tutorialMap[1][14], tutorialMap[2][14], 180, 0, 2.193, 1);
		add(s2, tutorialMap[0][15], tutorialMap[1][15], tutorialMap[2][15], 100, 90, 0.650, 2);
		add(r1, tutorialMap[0][16], tutorialMap[1][16], tutorialMap[2][16], 300, 270, 1.096, 3);

		add(s4, tutorialMap[0][17], tutorialMap[1][17], tutorialMap[2][17], 300, 180, 1.807, 1);
		add(c6, tutorialMap[0][18], tutorialMap[1][18], tutorialMap[2][18], 2);
		add(c7, tutorialMap[0][19], tutorialMap[1][19], tutorialMap[2][19], 3);
		add(s5, tutorialMap[0][20], tutorialMap[1][20], tutorialMap[2][20], 150, 350, 0.750, 4);
		add(s6, tutorialMap[0][21], tutorialMap[1][21], tutorialMap[2][21], 150, 280, 0.456, 5);
		add(c8, tutorialMap[0][22], tutorialMap[1][22], tutorialMap[2][22], 6);
		add(s7, tutorialMap[0][23], tutorialMap[1][23], tutorialMap[2][23], 300, 70, 1.609, 7);
		add(c9, tutorialMap[0][24], tutorialMap[1][24], tutorialMap[2][24], 8);
		add(r2, tutorialMap[0][25], tutorialMap[1][25], tutorialMap[2][25], 500, 300, 0.954, 9);
		add(s9, tutorialMap[0][26], tutorialMap[1][26], tutorialMap[2][26], 100, 200, 0.351, 1);
		add(s1, tutorialMap[0][27], tutorialMap[1][27], tutorialMap[2][27], 300, 240, 1.406, 2);
		add(c1, tutorialMap[0][28], tutorialMap[1][28], tutorialMap[2][28], 3);
		add(s2, tutorialMap[0][29], tutorialMap[1][29], tutorialMap[2][29], 250, 250, 1.193, 4);
		add(c2, tutorialMap[0][20], tutorialMap[1][30], tutorialMap[2][30], 5);
		add(s3, tutorialMap[0][31], tutorialMap[1][31], tutorialMap[2][31], 220, 300, 1.405, 6);
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
		this.remove(backLabel);

		playSound("Music/easy.wav");

		add(s1, easyMap[0][0], easyMap[1][0], easyMap[2][0], 250, 270, 0.900, 1);
		add(s2, easyMap[0][1], easyMap[1][1], easyMap[2][1], 250, 0, 0.800, 2);
		add(c1, easyMap[0][2], easyMap[1][2], easyMap[2][2], 3);
		add(c2, easyMap[0][3], easyMap[1][3], easyMap[2][3], 4);
		add(s3, easyMap[0][4], easyMap[1][4], easyMap[2][4], 300, 70, 0.800, 5);
		add(s4, easyMap[0][5], easyMap[1][5], easyMap[2][5], 300, 0, 0.800, 6);
		add(c3, easyMap[0][6], easyMap[1][6], easyMap[2][6], 7);
		add(s5, easyMap[0][7], easyMap[1][7], easyMap[2][7], 200, 180, 0.500, 8);
		add(s6, easyMap[0][8], easyMap[1][8], easyMap[2][8], 300, 0, 0.600, 9);
		add(s7, easyMap[0][9], easyMap[1][9], easyMap[2][9], 200, 230, 0.700, 1);
		add(c6, easyMap[0][10], easyMap[1][10], easyMap[2][10], 2);
		add(s8, easyMap[0][11], easyMap[1][11], easyMap[2][11], 200, 0, 0.600, 3);
		add(s9, easyMap[0][12], easyMap[1][12], easyMap[2][12], 200, 190, 0.600, 4);
		add(r1, easyMap[0][13], easyMap[1][13], easyMap[2][13], 350, 90, 0.600, 5);
		add(c8, easyMap[0][14], easyMap[1][14], easyMap[2][14], 6);
		add(s1, easyMap[0][15], easyMap[1][15], easyMap[2][15], 200, 0, 0.600, 7);
		add(c9, easyMap[0][16], easyMap[1][16], easyMap[2][16], 8);
		add(s2, easyMap[0][17], easyMap[1][17], easyMap[2][17], 200, 100, 0.600, 9);
		add(s3, easyMap[0][18], easyMap[1][18], easyMap[2][18], 350, 180, 0.600, 1);
		add(r2, easyMap[0][19], easyMap[1][19], easyMap[2][19], 200, 0, 0.500, 2);
		add(s4, easyMap[0][20], easyMap[1][20], easyMap[2][20], 200, 80, 0.500, 3);
		add(s5, easyMap[0][21], easyMap[1][21], easyMap[2][21], 200, 10, 0.500, 4);
		add(s6, easyMap[0][22], easyMap[1][22], easyMap[2][22], 200, 200, 0.500, 5);
		add(s7, easyMap[0][23], easyMap[1][23], easyMap[2][23], 200, 200, 0.500, 6);
		add(s8, easyMap[0][24], easyMap[1][24], easyMap[2][24], 450, 0, 2.500, 7);
		add(s9, easyMap[0][25], easyMap[1][25], easyMap[2][25], 200, 0, 0.600, 8);
		add(c5, easyMap[0][26], easyMap[1][26], easyMap[2][26], 9);
		add(s2, easyMap[0][27], easyMap[1][27], easyMap[2][27], 200, 30, 0.600, 1);
		add(s3, easyMap[0][28], easyMap[1][28], easyMap[2][28], 200, 210, 0.600, 2);
		add(s4, easyMap[0][29], easyMap[1][29], easyMap[2][29], 200, 110, 0.600, 3);
		add(r3, easyMap[0][30], easyMap[1][30], easyMap[2][30], 170, 0, 0.400, 4); // move up
		add(s5, easyMap[0][31], easyMap[1][31], easyMap[2][31], 400, 0, 0.600, 5);
		add(s6, easyMap[0][32], easyMap[1][32], easyMap[2][32], 400, 200, 0.600, 6);
		add(s7, easyMap[0][33], easyMap[1][33], easyMap[2][33], 400, 180, 0.600, 7);
		add(s8, easyMap[0][34], easyMap[1][34], easyMap[2][34], 200, 310, 0.600, 8);
		add(s9, easyMap[0][35], easyMap[1][35], easyMap[2][35], 300, 50, 0.600, 9);
		add(s1, easyMap[0][36], easyMap[1][36], easyMap[2][36], 300, 160, 0.600, 1);
		add(s2, easyMap[0][37], easyMap[1][37], easyMap[2][37], 200, 180, 0.500, 2);
		add(s3, easyMap[0][38], easyMap[1][38], easyMap[2][38], 200, 10, 0.600, 3);
		add(c4, easyMap[0][39], easyMap[1][39], easyMap[2][39], 4);
		add(s4, easyMap[0][40], easyMap[1][40], easyMap[2][40], 400, 180, 0.700, 5);
		add(s5, easyMap[0][41], easyMap[1][41], easyMap[2][41], 400, 180, 0.600, 6);
		add(r4, easyMap[0][42], easyMap[1][42], easyMap[2][42], 170, 10, 0.500, 7);
		add(r5, easyMap[0][43], easyMap[1][43], easyMap[2][43], 170, 10, 0.500, 8);
		add(s6, easyMap[0][44], easyMap[1][44], easyMap[2][44], 400, 180, 0.600, 9);
		add(s7, easyMap[0][45], easyMap[1][45], easyMap[2][45], 400, 180, 0.600, 1);
		add(r6, easyMap[0][46], easyMap[1][46], easyMap[2][46], 170, 270, 0.500, 2);
		add(s8, easyMap[0][47], easyMap[1][47], easyMap[2][47], 200, 20, 0.500, 3);
		add(c9, easyMap[0][48], easyMap[1][48], easyMap[2][48], 4);
		add(c1, easyMap[0][49], easyMap[1][49], easyMap[2][49], 5);
		add(r7, easyMap[0][50], easyMap[1][50], easyMap[2][50], 250, 280, 0.700, 6);
		add(s2, easyMap[0][51], easyMap[1][51], easyMap[2][51], 180, 0, 0.500, 7);
		add(s3, easyMap[0][52], easyMap[1][52], easyMap[2][52], 180, 190, 1.000, 8);
		add(s4, easyMap[0][53], easyMap[1][53], easyMap[2][53], 180, 350, 1.000, 9);
		add(s5, easyMap[0][54], easyMap[1][54], easyMap[2][54], 180, 180, 0.600, 1);
		add(s6, easyMap[0][55], easyMap[1][55], easyMap[2][55], 180, 0, 0.600, 2);
		add(s7, easyMap[0][56], easyMap[1][56], easyMap[2][56], 400, 0, 2.500, 3);
		add(s8, easyMap[0][57], easyMap[1][57], easyMap[2][57], 200, 120, 1.000, 4);
		add(s9, easyMap[0][58], easyMap[1][58], easyMap[2][58], 200, 700, 0.600, 5);
		add(s1, easyMap[0][59], easyMap[1][59], easyMap[2][59], 300, 180, 0.700, 6);
		add(s2, easyMap[0][60], easyMap[1][60], easyMap[2][60], 200, 270, 0.700, 7);
		add(s3, easyMap[0][61], easyMap[1][61], easyMap[2][61], 300, 350, 0.700, 8);
		add(s4, easyMap[0][62], easyMap[1][62], easyMap[2][62], 300, 110, 0.700, 9);
		add(s5, easyMap[0][63], easyMap[1][63], easyMap[2][63], 400, 180, 0.700, 1);
		add(s6, easyMap[0][64], easyMap[1][64], easyMap[2][64], 600, 0, 2.500, 2);

	}

	// code to run the Medium game mode
	private void medium() {

		this.remove(playButton);
		this.remove(exitButton);
		this.remove(tutorial);
		this.remove(easy);
		this.remove(medium);
		this.remove(hard);
		this.remove(backLabel);

		playSound("Music/medium.wav");

		add(s1, mediumMap[0][0], mediumMap[1][0], mediumMap[2][0], 150, 0, 0.600, 1);
		add(s2, mediumMap[0][1], mediumMap[1][1], mediumMap[2][1], 150, 0, 0.600, 2);
		add(s3, mediumMap[0][2], mediumMap[1][2], mediumMap[2][2], 150, 310, 0.600, 3);
		add(s4, mediumMap[0][3], mediumMap[1][3], mediumMap[2][3], 150, 80, 0.600, 4);
		add(s5, mediumMap[0][4], mediumMap[1][4], mediumMap[2][4], 150, 200, 0.600, 5);
		add(s6, mediumMap[0][5], mediumMap[1][5], mediumMap[2][5], 150, 150, 0.600, 6);
		add(r1, mediumMap[0][6], mediumMap[1][6], mediumMap[2][6], 150, 80, 0.540, 7);
		add(s7, mediumMap[0][7], mediumMap[1][7], mediumMap[2][7], 150, 190, 0.600, 8);
		add(s8, mediumMap[0][8], mediumMap[1][8], mediumMap[2][8], 150, 20, 0.600, 9);
		add(c1, mediumMap[0][9], mediumMap[1][9], mediumMap[2][9], 1);
		add(s9, mediumMap[0][10], mediumMap[1][10], mediumMap[2][10], 150, 80, 0.600, 2);
		add(c2, mediumMap[0][11], mediumMap[1][11], mediumMap[2][11], 3);
		add(r2, mediumMap[0][12], mediumMap[1][12], mediumMap[2][12], 150, 160, 0.600, 4);
		add(c3, mediumMap[0][13], mediumMap[1][13], mediumMap[2][13], 5);
		add(r3, mediumMap[0][14], mediumMap[1][14], mediumMap[2][14], 150, 30, 0.600, 6);
		add(c4, mediumMap[0][15], mediumMap[1][15], mediumMap[2][15], 7);
		add(s1, mediumMap[0][16], mediumMap[1][16], mediumMap[2][16], 150, 280, 0.600, 8);
		add(s2, mediumMap[0][17], mediumMap[1][17], mediumMap[2][17], 150, 100, 0.600, 9);
		add(r4, mediumMap[0][18], mediumMap[1][18], mediumMap[2][18], 150, 170, 0.600, 1);
		add(r5, mediumMap[0][19], mediumMap[1][19], mediumMap[2][19], 150, 10, 0.600, 2);
		add(s3, mediumMap[0][20], mediumMap[1][20], mediumMap[2][20], 150, 250, 0.600, 3);
		add(s4, mediumMap[0][21], mediumMap[1][21], mediumMap[2][21], 150, 70, 0.600, 4);
		add(r6, mediumMap[0][22], mediumMap[1][22], mediumMap[2][22], 150, 20, 0.600, 5);
		add(c5, mediumMap[0][23], mediumMap[1][23], mediumMap[2][23], 6);
		add(s5, mediumMap[0][24], mediumMap[1][24], mediumMap[2][24], 150, 100, 0.600, 7);
		add(s6, mediumMap[0][25], mediumMap[1][25], mediumMap[2][25], 150, 280, 0.600, 8);
		add(r7, mediumMap[0][26], mediumMap[1][26], mediumMap[2][26], 150, 10, 0.600, 9);
		add(c6, mediumMap[0][27], mediumMap[1][27], mediumMap[2][27], 1);
		add(s7, mediumMap[0][28], mediumMap[1][28], mediumMap[2][28], 150, 270, 0.600, 2);
		add(s8, mediumMap[0][29], mediumMap[1][29], mediumMap[2][29], 150, 190, 0.600, 3);
		add(r8, mediumMap[0][30], mediumMap[1][30], mediumMap[2][30], 150, 340, 0.600, 4);

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
		this.remove(backLabel);

		playSound("Music/hard.wav");

		add(c1, hardMap[0][0], hardMap[1][0], hardMap[2][0], 1);
		add(c2, hardMap[0][1], hardMap[1][1], hardMap[2][1], 2);
		add(c3, hardMap[0][2], hardMap[1][2], hardMap[2][2], 3);
		add(c4, hardMap[0][3], hardMap[1][3], hardMap[2][3], 4);
		add(c5, hardMap[0][4], hardMap[1][4], hardMap[2][4], 5);
		add(c6, hardMap[0][5], hardMap[1][5], hardMap[2][5], 6);
		add(c7, hardMap[0][6], hardMap[1][6], hardMap[2][6], 7);
		add(c8, hardMap[0][7], hardMap[1][7], hardMap[2][7], 8);
		add(c9, hardMap[0][8], hardMap[1][8], hardMap[2][8], 9);
		add(c1, hardMap[0][9], hardMap[1][9], hardMap[2][9], 1);
		add(c2, hardMap[0][10], hardMap[1][10], hardMap[2][10], 2);
		add(c3, hardMap[0][11], hardMap[1][11], hardMap[2][11], 3);
		add(c4, hardMap[0][12], hardMap[1][12], hardMap[2][12], 4);
		add(c5, hardMap[0][13], hardMap[1][13], hardMap[2][13], 5);
		add(c6, hardMap[0][14], hardMap[1][14], hardMap[2][14], 6);
		add(c7, hardMap[0][15], hardMap[1][15], hardMap[2][15], 7);
		add(c8, hardMap[0][16], hardMap[1][16], hardMap[2][16], 8);
		add(c9, hardMap[0][17], hardMap[1][17], hardMap[2][17], 9);
		add(c1, hardMap[0][18], hardMap[1][18], hardMap[2][18], 1);
		add(c2, hardMap[0][19], hardMap[1][19], hardMap[2][19], 2);
		add(c3, hardMap[0][20], hardMap[1][20], hardMap[2][20], 3);
		add(c4, hardMap[0][21], hardMap[1][21], hardMap[2][21], 4);
		add(c5, hardMap[0][22], hardMap[1][22], hardMap[2][22], 5);
		add(c6, hardMap[0][23], hardMap[1][23], hardMap[2][23], 6);
		add(c7, hardMap[0][24], hardMap[1][24], hardMap[2][24], 7);
		add(c8, hardMap[0][25], hardMap[1][25], hardMap[2][25], 8);
		add(c9, hardMap[0][26], hardMap[1][26], hardMap[2][26], 9);
		add(c1, hardMap[0][27], hardMap[1][27], hardMap[2][27], 1);
		add(c2, hardMap[0][28], hardMap[1][28], hardMap[2][28], 2);
		add(c3, hardMap[0][29], hardMap[1][29], hardMap[2][29], 3);
		add(c4, hardMap[0][30], hardMap[1][30], hardMap[2][30], 4);
		add(c5, hardMap[0][31], hardMap[1][31], hardMap[2][31], 5);
		add(c6, hardMap[0][32], hardMap[1][32], hardMap[2][32], 6);
		add(c7, hardMap[0][33], hardMap[1][33], hardMap[2][33], 7);
		add(c8, hardMap[0][34], hardMap[1][34], hardMap[2][34], 8);
		add(c9, hardMap[0][35], hardMap[1][35], hardMap[2][35], 9);
		add(c1, hardMap[0][36], hardMap[1][36], hardMap[2][36], 1);
		add(c2, hardMap[0][37], hardMap[1][37], hardMap[2][37], 2);
		add(c3, hardMap[0][38], hardMap[1][38], hardMap[2][38], 3);
		add(c4, hardMap[0][39], hardMap[1][39], hardMap[2][39], 4);
		add(c5, hardMap[0][40], hardMap[1][40], hardMap[2][40], 5);
		add(c6, hardMap[0][41], hardMap[1][41], hardMap[2][41], 6);
		add(c7, hardMap[0][42], hardMap[1][42], hardMap[2][42], 7);
		add(c8, hardMap[0][43], hardMap[1][43], hardMap[2][43], 8);
		add(c9, hardMap[0][44], hardMap[1][44], hardMap[2][44], 9);
		add(c1, hardMap[0][45], hardMap[1][45], hardMap[2][45], 1);
		add(c2, hardMap[0][46], hardMap[1][46], hardMap[2][46], 2);
		add(c3, hardMap[0][47], hardMap[1][47], hardMap[2][47], 3);
		add(c4, hardMap[0][48], hardMap[1][48], hardMap[2][48], 4);
		add(c5, hardMap[0][49], hardMap[1][49], hardMap[2][49], 5);
		add(c6, hardMap[0][50], hardMap[1][50], hardMap[2][50], 6);
		add(c7, hardMap[0][51], hardMap[1][51], hardMap[2][51], 7);
		add(c8, hardMap[0][52], hardMap[1][52], hardMap[2][52], 8);

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

			clip.addLineListener(new LineListener() {
				@Override
				public void update(LineEvent event) {
					if (event.getType() == LineEvent.Type.STOP) {
						playHard = false;
						endScreen = true;
						clip.close();
					}
				}
			});

			clip.start(); // Start playing the sound
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	private void playClicks(String soundFile) {
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
