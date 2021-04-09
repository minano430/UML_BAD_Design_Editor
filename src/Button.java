import java.awt.Canvas;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Button implements ActionListener {

	public static int number;

	public MyCanvas canvas;

	// Each Mode
	public Mode create_mode, select_mode, association_mode, use_case, generalization_mode, composition_mode;

	static JPanel panel;

	static JButton[] buttons;

	static JMenu file, edit;

	static JMenuItem Group, UnGroup, Edit;

	public Button(MyCanvas canvas) {

		this.canvas = canvas;
		create_mode = new create_class(canvas);
		select_mode = new select(canvas);
		association_mode = new association(canvas);
		use_case = new usecase(canvas);
		generalization_mode = new generalization(canvas);
		composition_mode = new composition(canvas);

		panel = new JPanel();
		panel.setBorder(BorderFactory.createLoweredBevelBorder());
		panel.setLayout(new GridBagLayout());
		number = 0;

		file = new JMenu("file");
		edit = new JMenu("edit");
		Group = new JMenuItem("Group");
		UnGroup = new JMenuItem("UnGroup");
		Edit = new JMenuItem("edit");

		Group.setActionCommand("6");
		Group.addActionListener(this);
		UnGroup.setActionCommand("7");
		UnGroup.addActionListener(this);
		Edit.setActionCommand("8");
		Edit.addActionListener(this);

		edit.add(Group);
		edit.add(UnGroup);
		edit.add(Edit);

		JButton[] buttons = new JButton[6];
		buttons[0] = new JButton("select");
		buttons[1] = new JButton("association");
		buttons[2] = new JButton("generalization");
		buttons[3] = new JButton("composition");
		buttons[4] = new JButton("draw_class");
		buttons[5] = new JButton("use_case");

		for (int i = 0; i < buttons.length; i++) {
			GridBagConstraints button_loc = new GridBagConstraints();
			button_loc.gridx = 0;
			button_loc.gridy = i;
			button_loc.gridwidth = 1;
			button_loc.gridheight = 1;
			button_loc.weightx = 0;
			button_loc.weighty = 0;
			button_loc.fill = GridBagConstraints.BOTH;
			button_loc.anchor = GridBagConstraints.NORTH;

			String command = Integer.toString(i);
			buttons[i].setActionCommand(command);
			buttons[i].addActionListener(this);
			panel.add(buttons[i], button_loc);
		}

	}

	public JPanel getpanel() {
		return panel;
	}

	public JMenu getfile() {
		return file;
	}

	public JMenu getedit() {
		return edit;
	}

	public void changeText() {
		JFrame inputTextFrame = new JFrame("Change Object Name");
		inputTextFrame.setSize(400, 100);
		inputTextFrame.getContentPane().setLayout(new GridLayout(0, 1));

		JPanel panel = null;
		panel = new JPanel();
		panel.setLayout((LayoutManager) new BoxLayout(panel, BoxLayout.X_AXIS));

		JTextField Text = new JTextField("Object Name");
		panel.add(Text);
		inputTextFrame.getContentPane().add(panel);

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JButton confirm = new JButton("OK");
		panel.add(confirm);

		JButton cancel = new JButton("Cancel");
		panel.add(cancel);

		inputTextFrame.getContentPane().add(panel);

		inputTextFrame.setLocationRelativeTo(null);
		inputTextFrame.setVisible(true);
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for (int i = 0; i < canvas.shape_array.size(); i++) {
					Basic_shape obj = canvas.shape_array.get(i);
					if (obj.selected) {
						obj.text = Text.getText();
					}
				}
				inputTextFrame.dispose();

			}
		});

		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputTextFrame.dispose();
			}
		});
	}
	public void group_select() {
		Basic_shape composite = new composite();
		for (int i = 0; i < canvas.shape_array.size(); i++) {
			Basic_shape obj = canvas.shape_array.get(i);
			if (obj.selected) {
				composite.add(obj);
				canvas.shape_array.remove(i);
				i--;
			}
		}
		canvas.shape_array.add(composite);
	}

	public void UnGroup() {
		Vector<Basic_shape> temp = new Vector<Basic_shape>();
		for (int i = 0; i < canvas.shape_array.size(); i++) {
			Basic_shape obj = canvas.shape_array.get(i);
			if (obj.selected && obj.is_composite()) {
				for (int j = 0; j < obj.composite_array.size(); j++) {
					temp.add(obj.composite_array.get(j));
				}
				canvas.shape_array.remove(i);
				i--;
			}
		}
		for (int i = 0; i < temp.size(); i++) {
			Basic_shape obj = temp.get(i);
			canvas.shape_array.add(obj);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("0")) {
			canvas.currentMode = select_mode;
			canvas.setCurrentMode();
		} else if (cmd.equals("1")) {
			canvas.currentMode = association_mode;
			canvas.setCurrentMode();
		} else if (cmd.equals("2")) {
			canvas.currentMode = generalization_mode;
			canvas.setCurrentMode();
		} else if (cmd.equals("3")) {
			canvas.currentMode = composition_mode;
			canvas.setCurrentMode();
		} else if (cmd.equals("4")) {
			canvas.currentMode = create_mode;
			canvas.setCurrentMode();
		} else if (cmd.equals("5")) {
			canvas.currentMode = use_case;
			canvas.setCurrentMode();
		} else if (cmd.equals("6")) {
			group_select();
		} else if (cmd.equals("7")) {
			UnGroup();
		} else if (cmd.equals("8")) {
			changeText();
		}
	}

	public GridBagConstraints getpanelstyle() {
		GridBagConstraints panel_loc = new GridBagConstraints();
		panel_loc.gridx = 0;
		panel_loc.gridy = 1;
		panel_loc.gridwidth = 1;
		panel_loc.gridheight = 1;
		panel_loc.weightx = 0;
		panel_loc.weighty = 0;
		panel_loc.fill = GridBagConstraints.NONE;
		panel_loc.anchor = GridBagConstraints.NORTHWEST;
		return panel_loc;
	}

}
