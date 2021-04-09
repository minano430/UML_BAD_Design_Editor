import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class main_scene {

	static JFrame frame;

	public static void main(String[] args) {
		set_window();
	}

	public static void set_window() {
		frame = new JFrame();
		frame.addWindowListener(new AdapterDemo());
		frame.setSize(800, 600);
		frame.setLayout(new GridBagLayout());
		frame.setLocationRelativeTo(null);

		MyCanvas canvas = new MyCanvas();
		Button panel = new Button(canvas);
		JMenuBar mb = new JMenuBar();

		mb.add(panel.getfile());
		mb.add(panel.getedit());

		frame.setJMenuBar(mb);
		frame.add(panel.getpanel(), panel.getpanelstyle());
		frame.add(canvas, canvas.getstyle());
		frame.setVisible(true);
	}
}

class AdapterDemo extends WindowAdapter {
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
}