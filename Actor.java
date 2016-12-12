
public abstract class Actor implements TurnBased{
	private int hp;
	private int x;
	private int y;
	private char icon;
	private Stage stage;
	private char steppedOn;
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
	}
	protected void setStage(int x, int y, Stage stage){
		this.x = x;
		this.y = y;
		this.stage = stage;
		steppedOn = stage.getPoint(x, y);
		stage.setPoint(x, y, icon);
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	protected boolean isColliding(Actor a){
		return (a != this && a.getX() == x && a.getY() == y);
	}
	public void move(String direction){
		stage.setPoint(x, y, steppedOn);
		int oldX = x;
		int oldY = y;
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
				y++;
				x++;
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
			System.err.println("Invalid direction in actor");
			break;
		}
		//Check to see if an actor was already there, if so, move back
		for(Actor a : stage.getActors())
			if(isColliding(a)){
				x = oldX;
				y = oldY;
				System.out.println("Is colliding");
			}
		steppedOn = stage.setPoint(x, y, icon);
		System.out.println("x: "+x+" y: "+y);
	}
	public abstract void takeTurn();

}
