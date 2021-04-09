import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.EventListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyCanvas extends Canvas {

	public Vector<Basic_shape> shape_array;
	public Vector<line> line_array;
	private EventListener listener = null;

	protected Mode currentMode = null;

	MyCanvas() {
		shape_array = new Vector<Basic_shape>();
		line_array = new Vector<line>();
		setBackground(Color.WHITE);
		setSize(600, 500);
	}

	public void paint(Graphics g) {
		super.paint(g);
		for (int i = 0; i < shape_array.size(); i++) {
			Basic_shape obj = shape_array.get(i);
			obj.draw();
			obj.draw_selected();
		}
		for (int i = 0; i < line_array.size(); i++) {
			line obj = line_array.get(i);
			obj.draw();
		}
	}

	public void setCurrentMode() {
		removeMouseListener((MouseListener) listener);
		removeMouseMotionListener((MouseMotionListener) listener);
		listener = currentMode;
		addMouseListener((MouseListener) listener);
		addMouseMotionListener((MouseMotionListener) listener);
	}

	public GridBagConstraints getstyle() {
		GridBagConstraints canvas_loc = new GridBagConstraints();
		canvas_loc.gridx = 1;
		canvas_loc.gridy = 1;
		canvas_loc.gridwidth = 1;
		canvas_loc.gridheight = 1;
		canvas_loc.weightx = 0;
		canvas_loc.weighty = 0;
		canvas_loc.fill = GridBagConstraints.NONE;
		canvas_loc.anchor = GridBagConstraints.WEST;
		return canvas_loc;
	}
}