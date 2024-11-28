	import java.awt.GridLayout;
	import java.awt.BorderLayout;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.KeyEvent;
	import java.awt.event.KeyListener;
	import java.security.Key;

	import javax.swing.*;
	import javax.swing.border.*;

	public class GUI implements ActionListener, KeyListener {

		private JLabel label;
		private JFrame frame;
		private JPanel panel;
		private Border border;

		private JPanel left_panel;
		private Border left_border;

		private JPanel right_panel;
		private Border right_border;

		private JButton[] preset_btn;
		private JLabel[] result_row;

		public GUI() {

			frame = new JFrame();

			label = new JLabel("Click on a preset to make a throw.");

			panel = new JPanel();

			border = BorderFactory.createEmptyBorder(30, 30, 10, 30);
			TitledBorder titled = BorderFactory.createTitledBorder(border,
					"Presets:");

			left_panel = new JPanel();
			left_border = BorderFactory.createTitledBorder(border, "Presets:");
			left_panel.setBorder(left_border);
			left_panel.setLayout(new GridLayout(3, 3, 10, 10));

			preset_btn = new JButton[9];
			for(int i = 0; i < preset_btn.length; ++i) {
				int keynum = (((8 - i) / 3) * 3) + (i % 3) + 1;
				preset_btn[i] = new JButton(String.valueOf(keynum));
				preset_btn[i].addActionListener(this);
				left_panel.add(preset_btn[i]);
			}

			right_panel = new JPanel();
			right_border = BorderFactory.createTitledBorder(border,
				"Throw Results:");
			right_panel.setBorder(right_border);
			right_panel.setLayout(new GridLayout(4, 1, 10, 0));
			right_panel.add(label);

			result_row = new JLabel[3];
			for(int i = 0; i < result_row.length; ++i) {
				result_row[i] = new JLabel();
				right_panel.add(result_row[i]);
			}

			panel.setLayout(new GridLayout(1, 2));
			panel.add(left_panel);
			panel.add(right_panel);

			frame.add(panel, BorderLayout.CENTER);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle("Ultimate Dicer 2025");
			frame.setFocusable(true);
			frame.addKeyListener(this);
			frame.pack();
			frame.setVisible(true);

		}

		public static void main(String[] args) {
			new GUI();
		}

		public void roll(int preset_num) {
			label.setText("Preset Number: " + preset_num);
			result_row[0].setText("4x6: 1 + 5 + 3 + 6 = 18");
			result_row[1].setText("3x10: 4 + 9 + 10 = 23");
			result_row[2].setText("2x20: 19 + 9 = 28");
		}

		@Override
		public void keyReleased(KeyEvent e) {
			label.setText("Key released: " + e.getKeyCode());
			int preset_num = -1;
			int key_code = e.getKeyCode();
			if(key_code > KeyEvent.VK_0 && key_code <= KeyEvent.VK_9 ) {
				preset_num = key_code - KeyEvent.VK_0;
			} else if(key_code > KeyEvent.VK_NUMPAD0
					&& key_code <= KeyEvent.VK_NUMPAD9) {
				preset_num = key_code - KeyEvent.VK_NUMPAD0;
			} else {
				return;
			}
			roll(preset_num);
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			roll(Integer.parseInt(e.getActionCommand()));
		}
}
