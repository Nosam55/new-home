import java.awt.event.ActionEvent;
import javax.swing.*;

public class GameRunner {
	public static void main(String[] args){
		Menu menu = new Menu(200);
		Menu settingsMenu = new Menu(100);

		char[][] charset = {
				{'\u2554','\u2550','\u2550','\u2550','\u2557'},
				{'\u2551','x','x','x','\u2551'},
				{'\u2551','x','x','x','\u2551'},
				{'\u255A','\u2501','o','\u2501','\u255D'}
		};
		Stage stage = new Stage(charset, 300,200);
		Actor rando = new Actor('O'){

			@Override
			public void takeTurn() {
				this.move(UP);
			}};
		stage.addActor(rando, 3, 3);
		stage.addActor(new Player('@'), 0, 0);
		Action action = new AbstractAction(){
			public void actionPerformed(ActionEvent e){}
		};
		Action playGame = new AbstractAction(){
			public void actionPerformed(ActionEvent e){
				menu.getFLGame().setStage(stage);
			}
		};
		Action settingsAction = new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				menu.getFLGame().setMenu(settingsMenu);
			}
			
		};
		Action backToMain = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				settingsMenu.getFLGame().setMenu(menu);
			}
		};
		Action quitAction = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		};
		
		//Dummy options
		MenuOption play = new MenuOption("Play", playGame);
		MenuOption quit = new MenuOption("Quit", quitAction);
		//Settings menu
		MenuOption sound = new MenuOption("Sound", action);
		MenuOption graphics = new MenuOption("Graphics", action);
		MenuOption back = new MenuOption("Back", backToMain);
		// End settings
		
		
		MenuOption settings = new MenuOption("Settings", settingsAction);
		
		settingsMenu.addOption(sound);
		settingsMenu.addOption(graphics);
		settingsMenu.addOption(back);
		
		menu.addOption(play);
		menu.addOption(settings);
		menu.addOption(quit);
		FLGame game = new FLGame(menu,800,600);
	}
}
