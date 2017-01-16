
public class MapChange extends Actor{
	private Stage to;
	private int x;
	private int y;
	public MapChange(char icon, Stage stage, int x, int y) {
		super(icon);
		to = stage;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void onCollide(Actor collidingActor){
		collidingActor.getStage().removeActor(collidingActor);
		to.addActor(collidingActor, x, y);
		if(collidingActor instanceof Player)
			this.getStage().getFLGame().setStage(to);
	}
	
	@Override
	public void takeTurn() {

	}
	
}
