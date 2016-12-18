import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Tester {
	public static void main(String[] args){
		char[][] chars = {
				{'x','x','x','x','x'},
				{'x','x','x','x','x'},
				{'x','x','x','x','x'}
		};
		Stage stage = new Stage(chars, 50,50);
		System.out.println(stage.isInBounds(0, 0));
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		KeyListener l = new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println(e.getKeyChar()+": "+e.getKeyCode());
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
		frame.addKeyListener(l);
		frame.pack();
		frame.setVisible(true);
	}
}
