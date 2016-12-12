import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
public class Menu extends JPanel{
	private List<MenuOption> options;
	private int selected;
	private FLGame game;
	private BoxLayout layout;
	
	public Menu(int verticalOffset){
		options = new ArrayList<MenuOption>();
		selected = -1;
		this.setBackground(Color.BLACK);
		setActionAndInputMaps();
		layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		this.add(Box.createVerticalStrut(verticalOffset));
	}

	private void setActionAndInputMaps(){
		InputMap im = getInputMap();
		ActionMap am = getActionMap();
		im.put(KeyStroke.getKeyStroke("UP"), "up");
		im.put(KeyStroke.getKeyStroke("DOWN"), "down");
		im.put(KeyStroke.getKeyStroke("ENTER"), "enter");
		im.put(KeyStroke.getKeyStroke("KP_UP"), "up");
		im.put(KeyStroke.getKeyStroke("KP_DOWN"), "down");
		
		am.put("up", upAction);
		am.put("down", downAction);
		am.put("enter", enterAction);
	}
	public FLGame getFLGame(){
		return game;
	}
	public void addOption(MenuOption option){
		options.add(option);
		this.add(option);
		option.setAlignmentX(Component.CENTER_ALIGNMENT);
		option.setAlignmentY(Component.CENTER_ALIGNMENT);
		if(selected == -1)
			setSelected(0);
		
	}
	public void setSelected(int index){
		if(selected == -1){
			selected = index;
			options.get(selected).setSelected(true);
		}
		else{
			index = Math.abs(index) % options.size();
			options.get(selected).setSelected(false);
			selected = index;
			options.get(index).setSelected(true);
		}
		
	}
	protected void setFLGame(FLGame game){
		this.game = game;
	}
	//move one down the list, if at bottom go to top
	private Action downAction = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			if(!options.isEmpty()){
				setSelected(selected+1);
			}
				
		}
	};
	//Move one up the list, if at top go to bottom
	private Action upAction = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e){
			if(!options.isEmpty()){
				setSelected((selected == 0)?options.size()-1:selected-1);
			}
		}
	};
	private Action enterAction = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e){
			options.get(selected).choose(e);
		}
	};

}
