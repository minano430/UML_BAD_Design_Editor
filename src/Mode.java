import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collections;
import java.util.Comparator;

public abstract class Mode implements MouseListener, MouseMotionListener {
	protected MyCanvas canvas;
	public int x1, y1, x2, y2;

	Mode(MyCanvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	public void swap() {

	}
}

class ItemLocationComparator implements Comparator<Basic_shape> {
	public int compare(Basic_shape o1, Basic_shape o2) {
		int diff = (o1.depth - o2.depth)*-1;
		return diff;
	}
}
class create_class extends Mode {

	create_class(MyCanvas canvas) {
		super(canvas);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// System.out.printf("%d,%d",arg0.getX(),arg0.getY());
		canvas.shape_array.add(new rec(arg0.getX(), arg0.getY(), canvas));
		canvas.repaint();
	}
}

class select extends Mode {

	private boolean press_select;
	private int Count = 0;
	
	select(MyCanvas canvas) {
		super(canvas);
		press_select = false;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		press_select = false;
		for (int i = 0; i < canvas.shape_array.size(); i++) {
			canvas.shape_array.get(i).selected = false;
		}
		int count = -1, most_deep = 0;
		for (int i = 0; i < canvas.shape_array.size(); i++) {
			Basic_shape obj = canvas.shape_array.get(i);
			obj.deeper();
			if (obj.check_click(arg0.getX(), arg0.getY())) {
				press_select = true;
				if (most_deep <= obj.get_depth()) {
					most_deep = obj.get_depth();
					count = i;
				}
			}
		}
		if (press_select) {
			canvas.shape_array.get(count).selected = true;
			canvas.shape_array.get(count).set_depth(0);
			Collections.sort(canvas.shape_array, new ItemLocationComparator());
		} else {
			for (int i = 0; i < canvas.shape_array.size(); i++) {
				canvas.shape_array.get(i).selected = false;
			}
		}
		canvas.repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		x1 = arg0.getX();
		y1 = arg0.getY();
		press_select = false;
		int most_deep = 0;
		for (int i = 0; i < canvas.shape_array.size(); i++) {
			Basic_shape obj = canvas.shape_array.get(i);
			if (obj.check_click(arg0.getX(), arg0.getY())) {
				press_select = true;
				if (most_deep <= obj.get_depth()) {
					most_deep = obj.get_depth();
					Count = i;
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		x2 = arg0.getX();
		y2 = arg0.getY();
		int delta_x = x2 - x1, delta_y = y2 - y1;
		if (press_select) {
			Basic_shape obj = canvas.shape_array.get(Count);
			obj.move(delta_x, delta_y);
		} else {
			for (int i = 0; i < canvas.shape_array.size(); i++) {
				Basic_shape obj = canvas.shape_array.get(i);
				if (obj.check_dragged(x1, y1, x2, y2)) {
					press_select = true;
					obj.selected = true;
				} else
					obj.selected = false;
			}
		}
		canvas.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}
}

class association extends Mode {

	public int ID1, ID2, port1, port2;
	private boolean press_select;

	association(MyCanvas canvas) {
		super(canvas);
		// TODO Auto-generated constructor stub
		press_select = false;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		press_select = false;
		int count = -1, most_deep = 0;
		for (int i = 0; i < canvas.shape_array.size(); i++) {
			Basic_shape obj = canvas.shape_array.get(i);
			if (obj.check_click(arg0.getX(), arg0.getY())) {
				press_select = true;
				if (most_deep <= obj.get_depth())
					count = i;
			}
		}
		if (press_select) {
			ID1 = canvas.shape_array.get(count).ID;
			port1 = canvas.shape_array.get(count).port_distance(arg0.getX(), arg0.getY());
//			System.out.println("Port1_num: " + port1);
		} else {
			System.out.println("please click obj!");
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (press_select) {
			press_select = false;
			int count = -1, most_deep = 0;
			for (int i = 0; i < canvas.shape_array.size(); i++) {
				Basic_shape obj = canvas.shape_array.get(i);
				if (obj.check_click(arg0.getX(), arg0.getY())) {
					press_select = true;
					if (most_deep <= obj.get_depth())
						count = i;
				}
			}
			if (press_select && canvas.shape_array.get(count).ID != ID1) {
				ID2 = canvas.shape_array.get(count).ID;
				port2 = canvas.shape_array.get(count).port_distance(arg0.getX(), arg0.getY());
//				System.out.println("Port2_num: " + port2);
				canvas.line_array.add(new gen_line(ID1, port1, ID2, port2, canvas));
			} else
				System.out.println("please select different object!");
			canvas.repaint();
		}
	}
}

class usecase extends Mode {

	usecase(MyCanvas canvas) {
		super(canvas);
		// TODO Auto-generated constructor stub
	}

	public void mouseClicked(MouseEvent arg0) {
		canvas.shape_array.add(new circle(arg0.getX(), arg0.getY(), canvas));
		canvas.repaint();
	}
}

class generalization extends Mode {

	public int ID1, ID2, port1, port2;
	private boolean press_select;

	generalization(MyCanvas canvas) {
		super(canvas);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		press_select = false;
		int count = -1, most_deep = 0;
		for (int i = 0; i < canvas.shape_array.size(); i++) {
			Basic_shape obj = canvas.shape_array.get(i);
			if (obj.check_click(arg0.getX(), arg0.getY())) {
				press_select = true;
				if (most_deep <= obj.get_depth())
					count = i;
			}
		}
		if (press_select) {
			ID1 = canvas.shape_array.get(count).ID;
			port1 = canvas.shape_array.get(count).port_distance(arg0.getX(), arg0.getY());
//			System.out.println("Port1_num: " + port1);
		} else {
			System.out.println("please click obj!");
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (press_select) {
			press_select = false;
			int count = -1, most_deep = 0;
			for (int i = 0; i < canvas.shape_array.size(); i++) {
				Basic_shape obj = canvas.shape_array.get(i);
				if (obj.check_click(arg0.getX(), arg0.getY())) {
					press_select = true;
					if (most_deep <= obj.get_depth())
						count = i;
				}
			}
			if (press_select && canvas.shape_array.get(count).ID != ID1) {
				ID2 = canvas.shape_array.get(count).ID;
				port2 = canvas.shape_array.get(count).port_distance(arg0.getX(), arg0.getY());
//				System.out.println("Port2_num: " + port2);
				canvas.line_array.add(new assoc_line(ID1, port1, ID2, port2, canvas));
			} else
				System.out.println("please select different object!");
			canvas.repaint();
		}
	}
}

class composition extends Mode {

	public int ID1, ID2, port1, port2;
	private boolean press_select;

	composition(MyCanvas canvas) {
		super(canvas);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		press_select = false;
		int count = -1, most_deep = 0;
		for (int i = 0; i < canvas.shape_array.size(); i++) {
			Basic_shape obj = canvas.shape_array.get(i);
			if (obj.check_click(arg0.getX(), arg0.getY())) {
				press_select = true;
				if (most_deep <= obj.get_depth())
					count = i;
			}
		}
		if (press_select) {
			ID1 = canvas.shape_array.get(count).ID;
			port1 = canvas.shape_array.get(count).port_distance(arg0.getX(), arg0.getY());
//			System.out.println("Port1_num: " + port1);
		} else {
			System.out.println("please click obj!");
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (press_select) {
			press_select = false;
			int count = -1, most_deep = 0;
			for (int i = 0; i < canvas.shape_array.size(); i++) {
				Basic_shape obj = canvas.shape_array.get(i);
				if (obj.check_click(arg0.getX(), arg0.getY())) {
					press_select = true;
					if (most_deep <= obj.get_depth())
						count = i;
				}
			}
			if (press_select && canvas.shape_array.get(count).ID != ID1) {
				ID2 = canvas.shape_array.get(count).ID;
				port2 = canvas.shape_array.get(count).port_distance(arg0.getX(), arg0.getY());
//				System.out.println("Port2_num: " + port2);
				canvas.line_array.add(new compos_line(ID1, port1, ID2, port2, canvas));
			} else
				System.out.println("please select different object!");
			canvas.repaint();
		}
	}
}

