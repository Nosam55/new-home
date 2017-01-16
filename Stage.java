import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
public class Stage extends JPanel implements TurnBased{
	private List<Actor> actors;
	private int xMax;
	private int yMax;
	private List<Tile> tiles;
	private Tile[][] tileArr;
	private FLGame game;
	private int xoff;
	private int yoff;

	public Stage(char[][] charset, int xoffset, int yoffset){
		tiles = new ArrayList<Tile>();
		xoff = xoffset;
		yoff = yoffset;
		//We record the bounds of the map because we are putting the Tiles in a list sorted by draw order
		xMax = getLongestRowLength(charset);
		yMax = charset.length;
		tileArr = new Tile[yMax][xMax];
		for(int r = 0; r < charset.length; r++)
			for(int c = 0; c<charset[r].length; c++){
				//Conversion: arr[r][c] -> (c,r)
				Tile t = new Tile(charset[r][c], c, r, xoffset, yoffset, Color.GREEN);
				tiles.add(t);
				tileArr[r][c] = t;
			}
		actors = new ArrayList<Actor>();
		this.setBackground(Color.BLACK);
		setActionAndInputMaps();
	}
	public void addActor(Actor a, int x, int y){
		//Eventually the list will be sorted by something like agility
		actors.add(a);
		if(isInBounds(x,y))
			a.setStage(x, y, this);
		else
			a.setStage(0, 0, this);
	}
	public void removeActor(Actor a){
		a.onExit();
		actors.remove(a);
	}
	private static int getLongestRowLength(char[][] arr){
		int longestLength = 0;
		for(char[] a : arr)
			if(a.length > longestLength)
				longestLength = a.length;
		return longestLength;
	}
	public boolean isInBounds(int x, int y){
		//Conversion: (x,y) -> charset[y][x]
		if(y >-1 && y < yMax)
			if(x > -1 && x < xMax)
				return true;
		return false;
	}
	public char getPoint(int x, int y){
		//Conversion: (x,y) -> tileArr[y][x]
		return tileArr[y][x].getChar();
	}
	public char setPoint(int x, int y, char c){
		//Conversion: (x,y) -> tileArr[y][x]
		return tileArr[y][x].setChar(c);
	}
	public List<Actor> getActors(){
		return actors;
	}
	protected void setFLGame(FLGame game){
		this.game = game;
	}
	public FLGame getFLGame(){
		return game;
	}
	private void setActionAndInputMaps(){
		InputMap im = getInputMap();
		ActionMap am = getActionMap();

		im.put(KeyStroke.getKeyStroke("UP"), "up");
		im.put(KeyStroke.getKeyStroke("DOWN"), "down");
		im.put(KeyStroke.getKeyStroke("LEFT"), "left");
		im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
		im.put(KeyStroke.getKeyStroke("NUMPAD8"), "up");
		im.put(KeyStroke.getKeyStroke("NUMPAD2"), "down");
		im.put(KeyStroke.getKeyStroke("NUMPAD4"), "left");
		im.put(KeyStroke.getKeyStroke("NUMPAD6"), "right");
		im.put(KeyStroke.getKeyStroke("NUMPAD7"), "upLeft");
		im.put(KeyStroke.getKeyStroke("NUMPAD9"), "upRight");
		im.put(KeyStroke.getKeyStroke("NUMPAD1"), "downLeft");
		im.put(KeyStroke.getKeyStroke("NUMPAD3"), "downRight");
		im.put(KeyStroke.getKeyStroke("ESCAPE"), "escape");

		am.put("up", upAction);
		am.put("down", downAction);
		am.put("left", leftAction);
		am.put("right", rightAction);
		am.put("upLeft", upLeftAction);
		am.put("upRight", upRightAction);
		am.put("downLeft", downLeftAction);
		am.put("downRight", downRightAction);
		am.put("escape", escapeAction);
	}
	@Override
	public void takeTurn() {

	}
	private Action escapeAction;
	public void setEscapeAction(Action a){
		escapeAction = a;
		setActionAndInputMaps();
	}
	private Action upAction = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e){
			System.out.println("est");
			for(Actor a : actors)
				if(a instanceof Player)
					a.move(Actor.UP);
			game.endTurn();
		}
	};
	private Action downAction = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e){
			for(Actor a : actors)
				if(a instanceof Player)
					a.move(Actor.DOWN);
			game.endTurn();
		}
	};
	private Action leftAction = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e){
			for(Actor a : actors)
				if(a instanceof Player)
					a.move(Actor.LEFT);
			game.endTurn();
		}
	};
	private Action rightAction = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e){
			for(Actor a : actors)
				if(a instanceof Player)
					a.move(Actor.RIGHT);
			game.endTurn();
		}
	};
	private Action upLeftAction = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e){
			for(Actor a : actors)
				if(a instanceof Player)
					a.move(Actor.UP_LEFT);
			game.endTurn();
		}
	};
	private Action upRightAction = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e){
			for(Actor a : actors)
				if(a instanceof Player)
					a.move(Actor.UP_RIGHT);
			game.endTurn();
		}
	};
	private Action downLeftAction = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e){
			for(Actor a : actors)
				if(a instanceof Player)
					a.move(Actor.DOWN_LEFT);
			game.endTurn();
		}
	};
	private Action downRightAction = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e){
			for(Actor a : actors)
				if(a instanceof Player)
					a.move(Actor.DOWN_RIGHT);
			game.endTurn();
		}
	};
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Comparator<Tile> comp = new Comparator<Tile>(){
			@Override
			public int compare(Tile o1, Tile o2) {
				return o2.getDrawOrder() - o1.getDrawOrder();
			}
		};
		tiles.sort(comp);
		for(Tile tile: tiles)
			tile.paint(g);

	}
	protected class Tile{
		private Color color;
		private char icon;
		private int x;
		private int y;
		private int xoff;
		private int yoff;
		private int drawOrder;
		public static final int FONT_SIZE = 25;

		public Tile(char icon, int x, int y, int xoffset, int yoffset, Color col){
			this.icon = icon;
			this.x = x;
			this.y = y;
			xoff = xoffset;
			yoff = yoffset;
			color = col;
			drawOrder = Character.getType(icon);
		}

		public int getDrawOrder(){return drawOrder;}
		public void setColor(Color col){
			color = col;
		}
		public char getChar(){
			return icon;
		}
		public char setChar(char c){
			char old = icon;
			icon = c;
			return old;
		}
		public void paint(Graphics g){
			String s = Character.toString(icon);
			boolean is28 = Character.getType(icon) == 28;
			int offset = 2;
			//		System.out.println("Type of " + s +Character.getType(icon));
			//A character that is type 28 is bigger and off-center, so we fixx that shizzle mah nizzle
			int xpos = xoff + x*FONT_SIZE;
			int ypos = yoff + y*(FONT_SIZE);

			//Save the old shit so we can put it back later
			Color old = g.getColor();
			Font oldFont = g.getFont();
			
			//Draw a black box behind the thing
			g.setColor(Color.BLACK);
			g.drawRect(xpos, ypos, FONT_SIZE, FONT_SIZE);

			//Draw the thing
			g.setColor(color);
			g.setFont(Font.decode("PxPlus IBM BIOS-"+FONT_SIZE));
			//If its an actor, make it the actor's color
			for(Actor a : actors)
				if(a.getX() == x && a.getY() == y)
					g.setColor(a.getColor());
			g.drawString(s, xpos, ypos);

			//Remember what I said about keeping the shit so we could put it back?
			g.setColor(old);
			g.setFont(oldFont);
		}
	}
}
