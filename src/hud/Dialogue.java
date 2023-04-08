package hud;

import gui.Game_Frame;

import java.awt.*;

public class Dialogue extends HudObject {
        public String str_1;
        public String str_2;

        public Dialogue(){
                str_1 = "Thank you Luca!";
                str_2 = "You brought me back my flower!";
        }

        public void draw(Graphics g) {
                //int strSize = 36;
                g.setColor(new Color(0X4B3A26));
                g.fillRoundRect(5, Game_Frame.HEIGHT * 3/4, Game_Frame.WIDTH - 10, Game_Frame.HEIGHT/4, 10, 10);
                g.setColor(new Color(0X997950));
                g.fillRoundRect(10, Game_Frame.HEIGHT - Game_Frame.HEIGHT/4 + 5, Game_Frame.WIDTH - 20, Game_Frame.HEIGHT/4 - 10, 10, 10);
                g.setColor(Color.black);

                g.setFont(new Font("Monospaced", Font.BOLD, 20));
                g.drawString(str_1, 15, Game_Frame.HEIGHT - Game_Frame.HEIGHT/4 + 10 + g.getFont().getSize());
                g.drawString(str_2, 15, Game_Frame.HEIGHT - Game_Frame.HEIGHT/4 + 10 + 2 * g.getFont().getSize() + 10);
        }
}
