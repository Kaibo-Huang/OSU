//Don Tran and Kaibo Huang
//June 11, 2024
//This class keeps track of score, accuracy, and combo.

import java.awt.*;
import java.text.DecimalFormat;

public class Score {
    public static long score = 0; 
   static DecimalFormat df = new DecimalFormat("00000000");
    
    public static void draw(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 20));
       
        g.drawString(df.format(score), GamePanel.GAME_WIDTH - 100,20 );
    }
}
