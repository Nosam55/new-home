import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class MenuOption extends JLabel{
	private boolean isSelected;
	private Action onChoose;
	private JLabel label;
	private Color color;
	/*
	 * The MenuOption has an Action that gets performed when it is chosen, it has a text
	 * label, and it knows whether or not is it selected.
	 */
	public MenuOption(String name, Action whenChosen){
		super(name);
		
		setForeground(Color.WHITE);
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
		onChoose = whenChosen;
		this.setVisible(true);
	}
	public boolean isSelected(){
		return isSelected;
	}
	public void setSelected(boolean selected){
		isSelected = selected;
		if(isSelected)
			setForeground(Color.CYAN);
		else
			setForeground(Color.WHITE);
	}
	public void setColor(Color newColor){
		color = newColor;
	}
	
	public void choose(ActionEvent e){
		onChoose.actionPerformed(e);
	}
}
