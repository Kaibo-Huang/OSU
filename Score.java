// Don Tran and Kaibo Huang
// June 11, 2024
// This class keeps track of score, accuracy, and combo.

import java.awt.*;  
import java.text.DecimalFormat;  

public class Score {  // Declares a public class named Score.
    public static int score = 0;  // Declares a static integer variable score initialized to 0.
    static DecimalFormat df = new DecimalFormat("00000000");  // Creates a DecimalFormat object to format the score with leading zeros.
    static DecimalFormat p = new DecimalFormat("00.00");  // Creates a DecimalFormat object to format the percentage with two decimal places.

    public static void draw(Graphics g) {  // Defines a static method draw that takes a Graphics object as an argument.
        g.setColor(Color.WHITE);  // Sets the drawing color to white.
        g.setFont(new Font("Arial", Font.BOLD, 50));  // Sets the font to Arial, bold, and size 50.

        g.drawString(df.format(score), GamePanel.GAME_WIDTH - 230, 40);  // Draws the formatted score at the specified coordinates.

        g.setFont(new Font("Arial", Font.BOLD, 25));  // Sets the font to Arial, bold, and size 25.
        if (GamePanel.totalButtons == 0) {  // Checks if totalButtons in GamePanel is zero.
            g.drawString("100%", GamePanel.GAME_WIDTH - 90, 65);  // If true, draws "100%" at the specified coordinates.
        } else {  // If totalButtons is not zero,
            g.drawString(p.format(  // Draws the formatted accuracy percentage.
                    ((GamePanel.clicked300 * 300 + GamePanel.clicked100 * 100) * 100.0) / (GamePanel.totalButtons))  // Calculates the accuracy percentage.
                    + "%", GamePanel.GAME_WIDTH - 100, 65);  // Adds a percentage sign and specifies the coordinates.
        }

        g.drawString(GamePanel.combo + "X", 10, GamePanel.GAME_HEIGHT - 20);  // Draws the combo count followed by an 'X' at the specified coordinates.
    }
}
