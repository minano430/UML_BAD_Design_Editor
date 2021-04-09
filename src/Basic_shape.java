import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Vector;

public abstract class Basic_shape {

	public Vector<small_rec> select_array;
	public Vector<Basic_shape> composite_array;
	public boolean selected;
	public Canvas canvas;
	protected Font font = new Font(Font.DIALOG, Font.BOLD, 14);
	public String text;
	protected int x1, y1, x2, y2, ID;
	protected int mid_x, mid_y;
	public int depth;
	static int count = 0;

	Basic_shape() {
		composite_array = new Vector<Basic_shape>();
	}

	Basic_shape(int x1, int y1, int x2, int y2, Canvas canvas) {
		composite_array = new Vector<Basic_shape>();
		selected = false;
		select_array = new Vector<small_rec>();

		mid_x = (x1 + x2) / 2;
		mid_y = (y1 + y2) / 2;

		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.canvas = canvas;
		text = "Object_name";
		depth = count;
	}

	public void move(int delta_x, int delta_y) {
		x1 += delta_x;
		x2 += delta_x;
		y1 += delta_y;
		y2 += delta_y;
		for (int i = 0; i < select_array.size(); i++) {
			Basic_shape obj = select_array.get(i);
			obj.move(delta_x, delta_y);
		}
	}

	public int get_depth() {
		return depth;
	}
	public void set_depth(int i) {
		depth = 0;
	}
	public void deeper() {
		depth++;
	}
	public boolean is_composite() {
		return !composite_array.isEmpty();
	}

	public boolean check_dragged(int x1, int y1, int x2, int y2) {
		int ru_point_x = (x1<=x2) ? x1:x2,ld_point_x = (x1>x2) ? x1:x2;
		int ru_point_y = (y1<=y2) ? y1:y2,ld_point_y = (y1>y2) ? y1:y2;
		
		if (ru_point_x < this.x1 && ru_point_y < this.y1 && ld_point_x > this.x2 && ld_point_y > this.y2)
			return true;
		else
			return false;
	}

	public boolean check_click(int x1, int y1) {
		return true;
	}

	// extend
	public void draw() {

	}

	public void add(Basic_shape e) {

	}

	public void draw_selected() {

	}

	public Basic_shape getID(int id) {
		return null;
	}

	public int port_distance(int x1, int y1) {
		int x, y, count = 5;
		float distance = 100000f;
		for (int i = 0; i < select_array.size(); i++) {
			x = select_array.get(i).x1;
			y = select_array.get(i).y1;
			float p2p_dis = (x1 - x) * (x1 - x) + (y1 - y) * (y1 - y);
			if (distance > p2p_dis) {
				count = i;
				distance = p2p_dis;
			}
		}
//		System.out.println("Port_num: " + count);

		return count;
	}

	public void create_port() {
		select_array.add(new small_rec(x2 - 3, mid_y - 3, canvas, 0));
		select_array.add(new small_rec(mid_x - 3, y2 - 3, canvas, 1));
		select_array.add(new small_rec(x1 - 3, mid_y - 3, canvas, 2));
		select_array.add(new small_rec(mid_x - 3, y1 - 3, canvas, 3));
	}

	public small_rec get_point(int port_num) {
		return select_array.get(port_num);
	}
}

class rec extends Basic_shape {

	protected static int width = 60;
	protected static int height = 60;

	rec(int x1, int y1, Canvas canvas) {
		super(x1, y1, x1 + width, y1 + height, canvas);

		ID = get_depth();
		create_port();

		count++;
	}

	@Override
	public void draw() {
		Graphics g = canvas.getGraphics();
		g.setColor(Color.GRAY);
		g.fillRect(x1, y1, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x1, y1, width, height);
		g.drawLine(x1, y1 + height / 3, x1 + width, y1 + height / 3);
		g.drawLine(x1, y1 + 2 * height / 3, x1 + width, y1 + 2 * height / 3);
		
		int stringWidth = g.getFontMetrics(font).stringWidth(text);
		double empty = (Math.abs(x1-x2) - stringWidth)/2;
		g.setFont(font);	
		g.drawString(text, x1 + (int)empty, y1 + 13);
	}

	@Override
	public Basic_shape getID(int id) {
		if (ID == id)
			return this;
		else
			return null;
	}

	@Override
	public boolean check_click(int x1, int y1) {
		if (x1 > this.x1 && y1 > this.y1 && x1 < this.x2 && y1 < this.y2)
			return true;
		else
			return false;
	}

	@Override
	public void draw_selected() {
		if (selected) {
			for (int i = 0; i < select_array.size(); i++) {
				Basic_shape obj = select_array.get(i);
				obj.draw();
			}
		}
	}

}

class circle extends Basic_shape {

	protected static int width = 100, half_width = 50;
	protected static int height = 60, half_height = 30;

	circle(int x1, int y1, Canvas canvas) {
		super(x1, y1, x1 + width, y1 + height, canvas);

		ID = get_depth();
		create_port();

		count++;
	}

	@Override
	public Basic_shape getID(int id) {
		if (ID == id)
			return this;
		else
			return null;
	}

	@Override
	public void draw() {
		Graphics g = canvas.getGraphics();
		g.setColor(Color.GRAY);
		g.fillOval(x1, y1, width, height);
		g.setColor(Color.black);
		g.drawOval(x1, y1, width, height);
		
		int stringWidth = g.getFontMetrics(font).stringWidth(text);
		double empty = (Math.abs(x1-x2) - stringWidth)/2;
		g.setFont(font);	
		g.drawString(text, x1 + (int)empty, y1 + 30);
	}

	@Override
	public boolean check_click(int x1, int y1) {
		int mid_point_x = this.x1 + width / 2, mid_point_y = this.y1 + height / 2;
		if ((float) Math.pow((x1 - mid_point_x) / half_width, 2)
				+ (float) Math.pow((y1 - mid_point_y) / half_height, 2) <= 1.0f) {
			return true;
		} else
			return false;
	}

	@Override
	public void draw_selected() {
		if (selected) {
			for (int i = 0; i < select_array.size(); i++) {
				Basic_shape obj = select_array.get(i);
				obj.draw();
			}
		}
	}
}

class small_rec extends Basic_shape {

	public int port_num;
	protected static int width = 6;
	protected static int height = 6;

	small_rec(int x1, int y1, Canvas canvas, int port_num) {
		super(x1, y1, x1 + width, y1 + height, canvas);
		this.port_num = port_num;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		Graphics g = canvas.getGraphics();
		g.setColor(Color.black);
		g.fillRect(x1, y1, width, height);
	}

}

class composite extends Basic_shape {

	composite() {

		// TODO Auto-generated constructor stub
	}

	public void select(boolean change) {
		for (int i = 0; i < composite_array.size(); i++) {
			Basic_shape obj = composite_array.get(i);
			obj.selected = change;
		}
	}

	@Override
	public void add(Basic_shape e) {
		composite_array.add(e);
		select(false);
	}

	@Override
	public void draw() {

		if (selected)
			select(true);
		else
			select(false);

		for (int i = 0; i < composite_array.size(); i++) {
			Basic_shape obj = composite_array.get(i);
			obj.draw();
			obj.draw_selected();
		}
	}

	@Override
	public boolean check_click(int x1, int y1) {
		for (int i = 0; i < composite_array.size(); i++) {
			Basic_shape obj = composite_array.get(i);
			if (obj.check_click(x1, y1))
				return true;
		}
		return false;
	}

	@Override
	public boolean check_dragged(int x1, int y1, int x2, int y2) {
		for (int i = 0; i < composite_array.size(); i++) {
			Basic_shape obj = composite_array.get(i);
			if (obj.check_dragged(x1, y1, x2, y2))
				return true;
		}
		return false;
	}

	@Override
	public void move(int delta_x, int delta_y) {
		for (int i = 0; i < composite_array.size(); i++) {
			Basic_shape obj = composite_array.get(i);
			obj.move(delta_x, delta_y);
		}
	}

	@Override
	public Basic_shape getID(int id) {
		for (int i = 0; i < composite_array.size(); i++) {
			Basic_shape obj = composite_array.get(i);
			if (obj.getID(id) != null) {
				return composite_array.get(i);
			}
		}
		return null;
	}
}

abstract class line {

	public MyCanvas canvas;
	protected int x1, y1, ID1, x2, y2, ID2, port1, port2;
	protected static int width = 6;
	protected static int height = 6;

	protected double ARROW_HEIGHT = 10, ARROW_LENGTH = 5;

	public line(int ID1, int port1, int ID2, int port2, MyCanvas canvas) {
		// TODO Auto-generated constructor stub
		this.ID1 = ID1;
		this.port1 = port1;
		this.ID2 = ID2;
		this.port2 = port2;
		this.canvas = canvas;
	}

	protected static double[] rotateVec(int px, int py, double ang, boolean isChLen, double newLen) {
		double mathstr[] = new double[2];
		double vx = px * Math.cos(ang) - py * Math.sin(ang);
		double vy = px * Math.sin(ang) + py * Math.cos(ang);
		if (isChLen) {
			double d = Math.sqrt(vx * vx + vy * vy);
			vx = vx / d * newLen;
			vy = vy / d * newLen;
			mathstr[0] = vx;
			mathstr[1] = vy;
		}
		return mathstr;
	}

	public void draw() {

	}
}

class gen_line extends line {

	public gen_line(int ID1, int port1, int ID2, int port2, MyCanvas canvas) {
		super(ID1, port1, ID2, port2, canvas);
		// TODO Auto-generated constructor stub
	}

	protected void drawAL(int sx, int sy, int ex, int ey) {
		Graphics2D g2 = (Graphics2D) canvas.getGraphics();
		double H = ARROW_HEIGHT; // 箭头高度
		double L = ARROW_LENGTH; // 底边的一半
		int x3 = 0;
		int y3 = 0;
		int x4 = 0;
		int y4 = 0;
		double awrad = Math.atan(L / H); // 箭头角度
		double arraow_len = Math.sqrt(L * L + H * H); // 箭头的长度
		double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
		double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
		double x_3 = ex - arrXY_1[0]; // (x3,y3)是第一端点
		double y_3 = ey - arrXY_1[1];
		double x_4 = ex - arrXY_2[0]; // (x4,y4)是第二端点
		double y_4 = ey - arrXY_2[1];

		Double X3 = new Double(x_3);
		x3 = X3.intValue();
		Double Y3 = new Double(y_3);
		y3 = Y3.intValue();
		Double X4 = new Double(x_4);
		x4 = X4.intValue();
		Double Y4 = new Double(y_4);
		y4 = Y4.intValue();
		g2.drawLine(sx, sy, ex, ey);
		g2.drawLine(ex, ey, x3, y3);
		g2.drawLine(ex, ey, x4, y4);

	}

	@Override
	public void draw() {
		Graphics g = this.canvas.getGraphics();
		Basic_shape obj;
		g.setColor(Color.black);
		for (int i = 0; i < canvas.shape_array.size(); i++) {

			obj = canvas.shape_array.get(i).getID(ID1);
			if (obj != null) {
				obj = obj.get_point(port1);
				x1 = obj.x1;
				y1 = obj.y1;
			}
			obj = canvas.shape_array.get(i).getID(ID2);
			if (obj != null) {
				obj = obj.get_point(port2);
				x2 = obj.x1;
				y2 = obj.y1;
			}
		}
		drawAL(x1 + width / 2, y1 + height / 2, x2 + width / 2, y2 + height / 2);
	}
}

class assoc_line extends line {

	public assoc_line(int ID1, int port1, int ID2, int port2, MyCanvas canvas) {
		super(ID1, port1, ID2, port2, canvas);
		// TODO Auto-generated constructor stub
	}

	protected void drawAL(int sx, int sy, int ex, int ey) {
		Graphics2D g2 = (Graphics2D) canvas.getGraphics();
		double H = ARROW_HEIGHT; // 箭头高度
		double L = ARROW_LENGTH; // 底边的一半
		int x3 = 0;
		int y3 = 0;
		int x4 = 0;
		int y4 = 0;
		double awrad = Math.atan(L / H); // 箭头角度
		double arraow_len = Math.sqrt(L * L + H * H); // 箭头的长度
		double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
		double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
		double x_3 = ex - arrXY_1[0]; // (x3,y3)是第一端点
		double y_3 = ey - arrXY_1[1];
		double x_4 = ex - arrXY_2[0]; // (x4,y4)是第二端点
		double y_4 = ey - arrXY_2[1];
		double total_length = Math.sqrt(Math.pow(ex - sx, 2) + Math.pow(ey - sy, 2));
		int mid_point_x = (int) (((total_length - H) / total_length) * (ex-sx)) + sx;
		int mid_point_y = (int) (((total_length - H) / total_length) * (ey-sy)) + sy;

		Double X3 = new Double(x_3);
		x3 = X3.intValue();
		Double Y3 = new Double(y_3);
		y3 = Y3.intValue();
		Double X4 = new Double(x_4);
		x4 = X4.intValue();
		Double Y4 = new Double(y_4);
		y4 = Y4.intValue();

		g2.drawLine(sx, sy, mid_point_x, mid_point_y);
		g2.drawLine(x3, y3, x4, y4);
		g2.drawLine(ex, ey, x3, y3);
		g2.drawLine(ex, ey, x4, y4);

	}

	@Override
	public void draw() {
		Graphics g = this.canvas.getGraphics();
		Basic_shape obj;
		g.setColor(Color.black);
		for (int i = 0; i < canvas.shape_array.size(); i++) {

			obj = canvas.shape_array.get(i).getID(ID1);
			if (obj != null) {
				obj = obj.get_point(port1);
				x1 = obj.x1;
				y1 = obj.y1;
			}
			obj = canvas.shape_array.get(i).getID(ID2);
			if (obj != null) {
				obj = obj.get_point(port2);
				x2 = obj.x1;
				y2 = obj.y1;
			}
		}
		drawAL(x1 + width / 2, y1 + height / 2, x2 + width / 2, y2 + height / 2);
		// Line
	}

}

class compos_line extends line {

	public compos_line(int ID1, int port1, int ID2, int port2, MyCanvas canvas) {
		super(ID1, port1, ID2, port2, canvas);
		// TODO Auto-generated constructor stub
	}

	protected void drawAL(int sx, int sy, int ex, int ey) {
		Graphics2D g2 = (Graphics2D) canvas.getGraphics();
		double H = ARROW_HEIGHT; // 箭头高度
		double L = ARROW_LENGTH; // 底边的一半
		int x3 = 0;
		int y3 = 0;
		int x4 = 0;
		int y4 = 0;
		double awrad = Math.atan(L / H); // 箭头角度
		double arraow_len = Math.sqrt(L * L + H * H); // 箭头的长度
		double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
		double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
		double x_3 = ex - arrXY_1[0]; // (x3,y3)是第一端点
		double y_3 = ey - arrXY_1[1];
		double x_4 = ex - arrXY_2[0]; // (x4,y4)是第二端点
		double y_4 = ey - arrXY_2[1];
		double total_length = Math.sqrt(Math.pow(ex - sx, 2) + Math.pow(ey - sy, 2));
		int mid_point_x = (int) (((total_length - 2*H) / total_length) * (ex-sx)) + sx;
		int mid_point_y = (int) (((total_length - 2*H) / total_length) * (ey-sy)) + sy;

		Double X3 = new Double(x_3);
		x3 = X3.intValue();
		Double Y3 = new Double(y_3);
		y3 = Y3.intValue();
		Double X4 = new Double(x_4);
		x4 = X4.intValue();
		Double Y4 = new Double(y_4);
		y4 = Y4.intValue();

		g2.drawLine(sx, sy, mid_point_x, mid_point_y);
		g2.drawLine(mid_point_x, mid_point_y, x4, y4);
		g2.drawLine(mid_point_x, mid_point_y, x3, y3);
		g2.drawLine(ex, ey, x3, y3);
		g2.drawLine(ex, ey, x4, y4);

	}

	@Override
	public void draw() {
		Graphics g = this.canvas.getGraphics();
		Basic_shape obj;
		g.setColor(Color.black);
		for (int i = 0; i < canvas.shape_array.size(); i++) {

			obj = canvas.shape_array.get(i).getID(ID1);
			if (obj != null) {
				obj = obj.get_point(port1);
				x1 = obj.x1;
				y1 = obj.y1;
			}
			obj = canvas.shape_array.get(i).getID(ID2);
			if (obj != null) {
				obj = obj.get_point(port2);
				x2 = obj.x1;
				y2 = obj.y1;
			}
		}
		drawAL(x1 + width / 2, y1 + height / 2, x2 + width / 2, y2 + height / 2);
		// Line
	}
}