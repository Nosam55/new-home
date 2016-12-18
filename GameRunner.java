import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class GameRunner {
	public static void main(String[] args){
		Menu menu = new Menu(200);
		Menu settingsMenu = new Menu(100);
		CharsetReader reader = new CharsetReader("src/Map 1.txt");
		char[][] charset = reader.getChars();
		Stage stage = new Stage(charset, 300,200);
		Actor rando = new Actor('@'){

			@Override
			public void takeTurn() {
				this.move(UP);
			}};
		Action escapeMenuAction = new AbstractAction(){
			public void actionPerformed(ActionEvent e){
				stage.getFLGame().setMenu(menu);
			}
		};
		stage.setEscapeAction(escapeMenuAction);
		rando.setColor(new Color(0xFFFFFF));
		stage.addActor(rando, 4, 5);
		Player player = new Player('\u263A');
		player.setColor(new Color(0x07700));
		stage.addActor(player, 2, 2);
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
		System.out.println((char)3);
	}
}
