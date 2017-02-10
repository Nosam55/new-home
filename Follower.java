import java.util.*;
public class Follower extends Actor {
	private Actor target;
	public Follower(char icon){
		super(icon);
	}
	@Override
	public void takeTurn() {


	}
	private List<Point> getPath(Actor target){
		List<Point> path = new LinkedList<Point>();

		return path;
	}
	private boolean isPathBlocked(List<Point> path){
		for(Point p : path)
			if(getStage().getFLGame().isBlocked(
					getStage().getPoint(p.getX(), p.getY()) ))
				return true;
		
		return false;

	}
}
