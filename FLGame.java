import java.util.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import java.util.List;

public class FLGame {
	private List<TurnBased> turnBased;
	private boolean isRunning;
	private JFrame gameWindow;
	private Stage activeStage;
	private Menu activeMenu;
	private char[] blockers = {
			'\u2588',
			'\u2554',
			'\u2550',
			'\u2551',
			'\u255A',
			'\u2557',
			'\u255D'
	};

	public FLGame(Menu startingMenu, int width, int height){
		//Load in the CodePage 437 TrueType font
		try{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/CP437.ttf")));
		}
		catch(IOException|FontFormatException e){
			e.printStackTrace();
		}
		//Initialize lists
		turnBased = new ArrayList<TurnBased>();
		
		//Make the game window, set the size, make sure it closes, not resizable
		gameWindow = new JFrame("New Home WIP");
		gameWindow.setPreferredSize(new Dimension(width,height));
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setResizable(false);
		gameWindow.pack();
		gameWindow.setVisible(true);

		//Set the starting stage as the given menu
		setMenu(startingMenu);
	}

	protected void addTurnBased(TurnBased t){
		turnBased.add(t);
	}
	public void endGame(){
		isRunning = false;
	}
	public char[] getBlockers(){
		return blockers;
	}
	public void setStage(Stage stage){
		addStage(stage);
		//If the active screen is a menu, remove it, else remove the stage
		if(activeMenu != null)
			gameWindow.remove(activeMenu);
		else		
			if(activeStage != null)
				gameWindow.remove(activeStage);
		//There is no active menu, so it is null
		activeStage = stage;
		activeMenu = null;
		gameWindow.add(activeStage);
		gameWindow.revalidate();
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				while(!stage.isFocusOwner()){
					stage.requestFocusInWindow();
				}
				stage.repaint();
			}
			
		});
		t.start();
	}
	public void addStage(Stage stage){
		if(!turnBased.contains(stage))
			turnBased.add(stage);
		for(Actor a : stage.getActors())
			if(!turnBased.contains(a))
				turnBased.add(a);
		stage.setFLGame(this);
	}
	public void setMenu(Menu menu){
		//If the active screen is a stage, remove it, else remove active menu
		if(activeStage != null){
			gameWindow.remove(activeStage);
		}
		else
			if(activeMenu != null){
				gameWindow.remove(activeMenu);
			}
				
		//there is no active stage, so it is null
		activeStage = null;
		activeMenu = menu;
		gameWindow.add(menu);
		activeMenu.setFLGame(this);
		gameWindow.revalidate();
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				while(!menu.isFocusOwner())
					menu.requestFocusInWindow();
				menu.repaint();
			}
			
		});
		t.start();
	}
	public void setTurnOrder(TurnBased t, int index){
		if(turnBased.contains(t)){
			int oldIndex = turnBased.indexOf(t);
			TurnBased old = turnBased.set(index, t);
			turnBased.set(oldIndex, old);
		}
	}
	public void endTurn(){
		for(TurnBased t : turnBased)
			t.takeTurn();
		if(activeStage != null)
			activeStage.repaint();
		else if(activeMenu != null)
			activeMenu.repaint();
	}

}
