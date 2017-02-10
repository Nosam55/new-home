import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class GameRunner {
	public static void main(String[] args){
		//Create the menus
		Menu menu = new Menu(200);
		Menu settingsMenu = new Menu(100);
		//Create the stages
		CharsetReader reader = new CharsetReader("src/Map 1.txt");
		char[][] charset = reader.getChars();
		Stage stage = new Stage(charset, 150,150);
		//Second stage
		reader.setFile("src/Map 2.txt");
		charset = reader.getChars();
		Stage stage2 = new Stage(charset, 150,150);
		//Make the guy that moves
		Actor rando = new Actor('@'){
			private int i = 0;
			public void onCollide(Actor a){
				super.onCollide(a);
				a.damage(13);
			}
			@Override
			public void takeTurn() {
				int rand = i < 4 ? 6 : 2;
				switch(rand){
				case 0:
					this.move(RIGHT);
					break;
				case 1:
					this.move(UP_RIGHT);
					break;
				case 2: 
					this.move(UP);
					break;
				case 3: 
					this.move(UP_LEFT);
					break;
				case 4:
					this.move(LEFT);
					break;
				case 5:
					this.move(DOWN_LEFT);
					break;
				case 6:
					this.move(DOWN);
					break;
				case 7:
					this.move(DOWN_RIGHT);
					break;
				}
				i++;
				i %= 8;
			}};
		//When you hit escape this is what happens
		Action escapeMenuAction = new AbstractAction(){
			public void actionPerformed(ActionEvent e){
				stage.getFLGame().setMenu(menu);
			}
		};
		stage.setEscapeAction(escapeMenuAction);
		//Set the guys color & add him
		rando.setColor(new Color(0xFFFFFF));
		stage.addActor(rando, 6, 5);
		//Same with the player
		Player player = new Player('\u263A');
		player.setColor(Color.WHITE);
		stage.addActor(player, 2, 2);
		//Blank action(for testing/placeholder)
		Action action = new AbstractAction(){
			public void actionPerformed(ActionEvent e){}
		};
		//Other actions, should be self-explanitory
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
		//Center the maps
		stage.centerMap();
		stage2.centerMap();
		//Map Changers
		Actor tp = new MapChange('#', stage2, 6, 0); 
		stage.addActor(tp, 6, 9);
		Actor tp2 = new MapChange('#', stage, 6, 9);
		stage2.addActor(tp2, 6,0);
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
		game.addStage(stage2);
		game.addStage(stage);
		System.out.println("");
	}
}
