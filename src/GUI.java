	import java.awt.GridLayout;
	import java.awt.BorderLayout;

	import javax.swing.BorderFactory;
	import javax.swing.JFrame;
	import javax.swing.JPanel;

	public class GUI {
		public GUI() {
			JFrame frame = new JFrame();

			JPanel panel = new JPanel();
			panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
			panel.setLayout(new GridLayout(0, 1));

			frame.add(panel, BorderLayout.CENTER);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle("Java Lab");
			frame.pack();
			frame.setVisible(true);

		}

		public static void main(String[] args) {
			new GUI();
		}
	}
