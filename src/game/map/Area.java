package game.map;

import game.enemy.Enemy;
import game.enemy.EnemyBlob;
import game.enemy.EnemySmartBlob;
import game.environment.GreenSlimePit;
import game.environment.Obstacle;
import game.environment.PinkSlimePit;
import game.environment.Spikes;
import game.environment.Tree;
import game.player.Player;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 * Area functions as a linked list in two dimensions,
 * or a "linked grid."
 */
public class Area {
    
    private TiledMap map;
    private ArrayList<Enemy> enemies;
    private ArrayList<Obstacle> obstacles;
    
    private Area[] adjacent;
    
    private final int width;
    private final int height;
    
    private GameContainer container;
    private final Player player;
    
    public Tile getTile(int x, int y) { return map.getTile(x,y); }
    public ArrayList<Enemy> getEnemies() { return enemies; }
    public ArrayList<Obstacle> getObstacles() { return obstacles; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    // Move adding of creatues out of here; need a factory.
    public Area(int width, int height, GameContainer container, Player player) {
        this.adjacent = new Area[4];
        this.enemies = new ArrayList<Enemy>();
        this.obstacles = new ArrayList<Obstacle>();
        
        this.player = player;
        this.container = container;
        this.width = width;
        this.height = height;
        map = new TiledMap(width/64, height/64);
        
        if (Math.random()<0.5)
            map.fillStandardGrass();
        else
            map.fillStandardCobble();
        
        try {
            addEnemy(new EnemyBlob(player)).init(container);
            addEnemy(new EnemySmartBlob(player)).init(container);
        } catch (SlickException e) {
            System.out.println("Error initializing enemy: " + e);
        }
        
        try {
            addObstacle(new Spikes(player,600,600)).init(container);           
            addObstacle(new GreenSlimePit(player)).init(container);
            addObstacle(new PinkSlimePit(player)).init(container);
        } catch (SlickException e) { 
            System.out.println("Error initializing hazard: " + e);
        }
         try {
            addObstacle(new Tree(player, 1200,1200)).init(container);           
        } catch (SlickException e) { 
            System.out.println("Error initializing hazard: " + e);
        }
    }
    
    // Returns enemy added for chaining.
    public Enemy addEnemy(Enemy e) {
        enemies.add(e);
        return e;
    }
    
    public Enemy addEnemy(Enemy e, int x, int y) {
        e.setX(x);
        e.setY(y);
        return addEnemy(e);
    }
    
    public Obstacle addObstacle(Obstacle o) {
        obstacles.add(o);
        return o;
    }
    
    public void setAdjacent(Area area, int index) {
        adjacent[index] = area;
    }
    
    public Area getAdjacent(int index) {
        Area adjacentArea = adjacent[index];
        if (adjacentArea != null)
            return adjacentArea;
        adjacentArea = new Area(width,height,container,player);
        adjacentArea.setAdjacent(this,(index+2)%4);
        adjacent[index] = adjacentArea;
        return adjacentArea;
    }
}