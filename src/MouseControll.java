import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JLabel;

public class MouseControll implements MouseListener, MouseMotionListener {

	public Mode current_mode;
	private Mode create_mode;
	private MyCanvas canvas;
	
	MouseControll(MyCanvas canvas){
		this.canvas = canvas;
	}
	public void choose_mode(int num) {
		switch(num) {
			case(0):
			case(1):
			case(2):
			case(3):
			case(4):
				current_mode = create_mode;
			case(5):
		}
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int x1 = arg0.getX();
		int y1 = arg0.getY();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int x1 = arg0.getX();
		int y1 = arg0.getY();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int x1 = arg0.getX();
		int y1 = arg0.getY();
		choose_mode(Button.number);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int x1 = arg0.getX();
		int y1 = arg0.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int x1 = arg0.getX();
		int y1 = arg0.getY();
	}

}


