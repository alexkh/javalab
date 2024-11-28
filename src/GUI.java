	import java.awt.GridLayout;
	import java.awt.BorderLayout;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;

	import javax.swing.*;

	public class GUI implements ActionListener {

		private int count = 0;
		private JLabel label;
		private JFrame frame;
		private JPanel panel;
		private JButton button;

		public GUI() {

			frame = new JFrame();

			button = new JButton("Click me");
			button.addActionListener(this);

			label = new JLabel("Number of clicks: 0");

			panel = new JPanel();
			panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
			panel.setLayout(new GridLayout(0, 1));
			panel.add(button);
			panel.add(label);

			frame.add(panel, BorderLayout.CENTER);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle("Java Project");
			frame.pack();
			frame.setVisible(true);

		}

		public static void main(String[] args) {
			new GUI();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			count++;
			label.setText("Number of clicks: " + count);
		}
}
