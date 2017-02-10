import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.*;

public class StatusPanel extends JPanel {
	private Actor player;
	private JLabel health;
	public StatusPanel(){
		health = new JLabel();
		this.setBackground(Color.BLACK);
		health.setForeground(Color.WHITE);
		health.setFont(new Font("PxPlus IBM BIOS", Font.PLAIN, 18));
		health.setText("Health: ");
		add(health);
	}
	public void setPlayer(Actor player){
		this.player = player;
		health.setText("Health: "+player.getHp());
		repaint();
	}
	public void paint(Graphics g){
		super.paint(g);
		if(player != null)
			health.setText("Health: "+player.getHp());
		health.repaint();
	}
}
