import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public abstract class Actor implements TurnBased{
	private int hp;
	private int x;
	private int y;
	private int oldX;
	private int oldY;
	private char icon;
	private Stage stage;
	private char steppedOn;
	private Color color;
	public static final String UP = "u";
	public static final String DOWN = "d";
	public static final String LEFT = "l";
	public static final String RIGHT = "r";
	public static final String UP_LEFT = "ul";
	public static final String UP_RIGHT = "ur";
	public static final String DOWN_LEFT = "dl";
	public static final String DOWN_RIGHT = "dr";
	public Actor(char icon){
		hp = 100;
		this.icon = icon;
		color = Color.WHITE;
	}
	protected void setStage(int x, int y, Stage stage){
		this.x = x;
		this.y = y;
		oldX = x;
		oldY = y;
		this.stage = stage;
		steppedOn = stage.setPoint(x, y, icon);
		System.out.println( this +" is stepping on "+steppedOn);
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public Color getColor(){
		return color;
	}
	protected boolean isColliding(Actor a){
		return (a != this && a.getX() == x && a.getY() == y);
	}
	protected boolean isBlocked(){
		for(char c : stage.getFLGame().getBlockers())
			if(stage.getPoint(x, y) == c)
				return true;
		return false;
	}
	protected boolean isBlocked(int x, int y){
		for(char c : stage.getFLGame().getBlockers())
			if(stage.getPoint(x, y) == c)
				return true;
		return false;
	}
	public void onExit(){
		stage.setPoint(oldX, oldY, steppedOn);
	}
	public Color setColor(Color c){
		Color old = color;
		color = c;
		return old;
	}
	public boolean move(String direction){
		boolean moved = true;
		oldX = x;
		oldY = y;
		stage.setPoint(x, y, steppedOn);
		switch(direction){
		case UP:
			if(stage.isInBounds(x, y-1))
				y--;
			break;
		case DOWN:
			if(stage.isInBounds(x, y+1))
				y++;
			break;
		case LEFT:
			if(stage.isInBounds(x-1, y))
				x--;
			break;
		case RIGHT:
			if(stage.isInBounds(x+1, y))
				x++;
			break;
		case UP_LEFT:
			if(stage.isInBounds(x-1, y-1)){
				y--;
				x--;
			}
			break;
		case UP_RIGHT:
			if(stage.isInBounds(x+1, y-1)){
				y--;
				x++;
			}
			break;
		case DOWN_LEFT:
			if(stage.isInBounds(x-1, y+1)){
				y++;
				x--;
			}
			break;
		case DOWN_RIGHT:
			if(stage.isInBounds(x+1, y+1)){
				y++;
				x++;
			}
			break;
		default:
			//TODO Add toString()s for all this stuff
			System.err.println("Invalid direction in "+this);
			break;
		}
		if(isBlocked()){
			x = oldX;
			y = oldY;
			moved = false;
		}
		//Check to see if an actor was already there, if so, move back
		for(Actor a : stage.getActors())
			if(isColliding(a)){
				if(a instanceof MapChange){
					a.onCollide(this);
					return true;
				}
				onCollide(a);
				break;
			}
		steppedOn = stage.setPoint(x, y, icon);
		System.out.println( this +" moved to "+steppedOn);
		return moved;
	}
	public void onCollide(Actor collidingActor){
		x = oldX;
		y = oldY;
	}
	public Stage getStage(){
		return stage;
	}
	public String toString(){
		return "actor with icon "+icon+" at "+x+", "+y;
	}
	public abstract void takeTurn();

}
