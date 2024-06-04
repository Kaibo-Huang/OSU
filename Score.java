import java.awt.*;

public class Score {
    public static int score = 0; 

    
    public static void draw(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 30);
    }
}
