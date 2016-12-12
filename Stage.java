import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
public class Stage extends JPanel implements TurnBased{
	private List<Actor> actors;
	private Tile[][] tiles;
	private FLGame game;

	public Stage(char[][] charset, int xoffset, int yoffset){
		tiles = new Tile[charset.length][getLongestRowLength(charset)];
		for(int r = 0; r < charset.length; r++)
			for(int c = 0; c<charset[r].length; c++)
				//Conversion: arr[r][c] -> (c,r)
				tiles[r][c] = new Tile(charset[r][c], c, r, xoffset, yoffset, Color.GREEN);
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
	private static int getLongestRowLength(char[][] arr){
		int longestLength = 0;
		for(char[] a : arr)
			if(a.length > longestLength)
				longestLength = a.length;
		return longestLength;
	}
	public boolean isInBounds(int x, int y){
		//Conversion: (x,y) -> charset[y][x]
		if(y >-1 && y < tiles.length)
			if(x > -1 && x < tiles[y].length)
				return true;
		return false;
	}
	public char getPoint(int x, int y){
		//Conversion: (x,y) -> charset[y][x]
		return tiles[y][x].getChar();
	}
	public char setPoint(int x, int y, char c){
		//Conversion: (x,y) -> arr[y][x]
		return tiles[y][x].setChar(c);
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
		im.put(KeyStroke.getKeyStroke("KP_UP"), "up");
		im.put(KeyStroke.getKeyStroke("KP_DOWN"), "down");

		am.put("up", upAction);
		am.put("down", downAction);
		am.put("left", leftAction);
		am.put("right", rightAction);
	}
	@Override
	public void takeTurn() {

	}

	private Action upAction = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e){
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
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(Tile[] arr : tiles)
			for(Tile tile : arr)
				tile.paint(g);
	}
	protected class Tile{
		private Color color;
		private char icon;
		private int x;
		private int y;
		private int xoff;
		private int yoff;
		public static final int FONT_SIZE = 18;

		public Tile(char icon, int x, int y, int xoffset, int yoffset, Color col){
			this.icon = icon;
			this.x = x;
			this.y = y;
			xoff = xoffset;
			yoff = yoffset;
			color = col;
		}

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
			int xpos = xoff + x*FONT_SIZE;
			int ypos = yoff + y*FONT_SIZE;
			
			Color old = g.getColor();
			Font oldFont = g.getFont();
			g.setColor(Color.BLACK);
			g.drawRect(xpos, ypos, FONT_SIZE, FONT_SIZE);
			g.setColor(color);
			g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, FONT_SIZE));
			g.drawString(s, xpos, ypos);

			g.setColor(old);
			g.setFont(oldFont);
		}
	}
}
