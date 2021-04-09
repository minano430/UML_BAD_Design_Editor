
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Testing extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

	JLabel ML = new JLabel("滑鼠作標"); // ML=Mouse Location 滑鼠由標位置
	Color color = Color.black;// 顏色
	int con = 1, drawflag = 1;// 畫筆大小與畫畫記號
	BasicStroke size = new BasicStroke(con, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);// 畫筆粗細
	int x1, y1, x2, y2; // 畫筆getXY位置
	FileDialog openfile, savefile;

	public Testing() {
		// Frame設定
		setTitle("JAVA 小畫家");
		setSize(800, 600);
		setLayout(new BorderLayout());
		// setResizable(false);
		setLocationRelativeTo(null);
		// setBackground(Color.black);

		JMenuBar JMenuBar = new JMenuBar();
		// Menu部分
		JMenu menu = new JMenu("Menu");
		JMenuItem load = new JMenuItem("Load");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem clear = new JMenuItem("Clear");

		// Menu事件監聽
		load.setActionCommand("load");
		load.addActionListener(this);
		save.setActionCommand("save");
		save.addActionListener(this);
		clear.setActionCommand("clear");
		clear.addActionListener(this);

		// Menu新增至畫布panel中
		menu.add(load);
		menu.add(save);
		menu.addSeparator(); // 呼叫 addSeparator() 加入分隔線
		menu.add(clear);
		JMenuBar.add(menu);

		// Draw部分
		JMenu draw = new JMenu("Draw");
		JMenuItem line = new JMenuItem("Line");
		JMenuItem ellipse = new JMenuItem("Ellipse");
		JMenuItem rectangle = new JMenuItem("Rectangle");

		// 各MenuItem事件監聽
		line.setActionCommand("line");
		line.addActionListener(this);
		ellipse.setActionCommand("ellipse");
		ellipse.addActionListener(this);
		rectangle.setActionCommand("rectangle");
		rectangle.addActionListener(this);

		// Draw新增至畫布panel中
		draw.add(line);
		draw.addSeparator(); // 呼叫 addSeparator() 加入分隔線
		draw.add(ellipse);
		draw.addSeparator(); // 呼叫 addSeparator() 加入分隔線
		draw.add(rectangle);
		JMenuBar.add(draw);
		// 將畫布panel顯示在Frame上
		add(JMenuBar, BorderLayout.NORTH);

		// 宣告各種Button與名稱
		JButton black = new JButton("Black");
		JButton red = new JButton("Red");
		JButton green = new JButton("Green");
		JButton blue = new JButton("Blue");
		JButton thin = new JButton("Thin");
		JButton middle = new JButton("Middle");
		JButton thick = new JButton("Thick");

		// 設定Button的顏色
		black.setBackground(Color.black);
		red.setBackground(Color.red);
		green.setBackground(Color.green);
		blue.setBackground(Color.blue);

		// Button新增至畫布panel中
		JPanel toolpanel = new JPanel();
		toolpanel.add(black);
		toolpanel.add(red);
		toolpanel.add(green);
		toolpanel.add(blue);
		toolpanel.add(thin);
		toolpanel.add(middle);
		toolpanel.add(thick);
		toolpanel.add(ML);
		// 將畫布panel顯示在Frame上
		add(toolpanel, BorderLayout.SOUTH);

		// 各組Button事件監聽
		black.setActionCommand("black");
		black.addActionListener(this);
		red.setActionCommand("red");
		red.addActionListener(this);
		green.setActionCommand("green");
		green.addActionListener(this);
		blue.setActionCommand("blue");
		blue.addActionListener(this);
		thick.setActionCommand("thick");
		thick.addActionListener(this);
		middle.setActionCommand("middle");
		middle.addActionListener(this);
		thin.setActionCommand("thin");
		thin.addActionListener(this);

		addMouseListener(this); // 滑鼠移動監聽器
		addMouseMotionListener(this); // 滑鼠移動位置監聽器
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	// Math.abs 計算絕對值然後回傳
	public void paint(Graphics g) {
		super.paint(g);
		g = getGraphics();
		((Graphics2D) g).setStroke(new BasicStroke(con));
		g.setColor(color);

		if (drawflag == 1) // "line"
			g.drawLine(x1, y1, x2, y2);
		else if (drawflag == 2) { // "oval"
			g.drawOval((x1 < x2) ? x1 : x2, (y1 < y2) ? y1 : y2, Math.abs(x2 - x1), Math.abs(y2 - y1));

		} else if (drawflag == 3) { // "rect"
			g.drawRect((x1 < x2) ? x1 : x2, (y1 < y2) ? y1 : y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
			/*
			 * if(x1<x2 && y1<y2) //左上 畫到 右下 g.drawRect(x1, y1, Math.abs(x2-x1) ,
			 * Math.abs(y2-y1)); else if(x1>x2 && y1>y2) //右下 畫到 左上 g.drawRect(x2, y2,
			 * Math.abs(x2-x1) , Math.abs(x2-x1)); else if(x1<x2 && y1>y2) //右上 畫到 左下
			 * g.drawRect(x1, y2, Math.abs(x2-x1) , Math.abs(x2-x1)); else if(x1>x2 &&
			 * y1<y2) //右上 畫到 右下 g.drawRect(x2, y1, Math.abs(x2-x1) , Math.abs(y2-y1));
			 */

		}

		else if (drawflag == 4) { // "clear"
			drawflag = 1;
			g.clearRect(0, 0, getSize().width, getSize().height);
			repaint();
		}
	}

	public static void main(String[] args) {
		new Testing();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd == "clear")
			drawflag = 4;
		else if (cmd == "load") {
			openfile = new FileDialog(this, "new", FileDialog.LOAD);
			openfile.setDirectory("C:/");
			openfile.setFile("demo.txt");
			openfile.setVisible(true);
		} else if (cmd == "save") {
			openfile = new FileDialog(this, "new", FileDialog.SAVE);
			openfile.setDirectory("C:/");
			openfile.setFile("demo.txt");
			openfile.setVisible(true);
		}

		// 畫畫ITEM
		else if (cmd == "line")
			drawflag = 1;
		else if (cmd == "ellipse")
			drawflag = 2;
		else if (cmd == "rectangle")
			drawflag = 3;

		// 顏色BUTTON
		else if (cmd == "black")
			color = new Color(0, 0, 0);
		else if (cmd == "red")
			color = new Color(255, 0, 0);
		else if (cmd == "green")
			color = new Color(0, 255, 0);
		else if (cmd == "blue")
			color = new Color(0, 0, 255);

		// 畫筆粗細 BUTTON
		else if (cmd == "thin") {
			con = 1;
			size = new BasicStroke(con, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
		} else if (cmd == "middle") {
			con = 6;
			size = new BasicStroke(con, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
		} else if (cmd == "thick") {
			con = 9;
			size = new BasicStroke(con, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
		}

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int x = arg0.getX();
		int y = arg0.getY();
		ML.setText("滑鼠位置:X." + x + "Y." + y);
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int x = arg0.getX();
		int y = arg0.getY();
		ML.setText("滑鼠位置：X." + x + " Y." + y);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

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
		x1 = arg0.getX();
		y1 = arg0.getY();
		ML.setText("滑鼠位置:X." + x1 + "Y." + y1);
		// repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		x2 = arg0.getX();
		y2 = arg0.getY();
		ML.setText("滑鼠位置:X." + x2 + "Y." + y2);
		repaint();
	}

}
