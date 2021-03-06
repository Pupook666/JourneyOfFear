package game.hud;

import game.map.Area;
import game.player.Player;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public abstract class HUD implements Comparable<HUD> {
    
    public static final Color BACKGROUND_BLACK = new Color(0f,0f,0f,0.5f);
    public static final Color FONT_WHITE = new Color(1f,1f,1f,1f);
    
    public abstract int getDepth();
    public abstract void display(Graphics g, Player player, Area currentArea, int camX, int camY);
    public abstract boolean isVisible();
    public abstract void respondToUserInput(Input in);
    
    @Override
    public int compareTo(HUD other) {
        int t = this.getDepth();
        int o = other.getDepth();
        return t>o?1:(t<0?-1:0); 
    }
}
