import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import java.awt.Font;
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
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JFrame frame;
	private JPanel panel;
	private Border border;

	private JPanel left_panel;
	private Border left_border;

	private JPanel right_panel;
	private Border right_border;

	private JButton[] preset_btn;

	private int rollCounter;

	private String logFileName;
	private FileWriter log;
	private boolean noLogFile;

	// map keynum from 0 to 9 to number of sides
	static private final int SIDES[] = {0, 2, 3, 4, 6, 8, 10, 12, 20, 100};

	public GUI() {
		if(logFileName == null) {
			logFileName = "game.log";
		}

		try {
			log = new FileWriter(logFileName, true);
			LocalDateTime dtNow = LocalDateTime.now(); // Create a date object
			log.write("--------======== New game at " + dtNow +
				" ========--------\n");
			log.flush();
			noLogFile = false;
		}
		catch(Exception e) {
			System.out.println("Could not open game.log for writing");
			noLogFile = true;
		}

		frame = new JFrame();

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
			preset_btn[i] = new JButton(keynum + ": d" + SIDES[keynum]);
			preset_btn[i].addActionListener(this);
			left_panel.add(preset_btn[i]);
		}

		right_panel = new JPanel();
		right_border = BorderFactory.createTitledBorder(border,
			"Throw Results history:");
		right_panel.setBorder(right_border);
		right_panel.setLayout(new GridLayout(1, 1, 10, 0));

		textArea = new JTextArea(9, 60);
		textArea.setEditable(false);
		textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		scrollPane = new JScrollPane(textArea,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		right_panel.add(scrollPane);

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

	public void roll(int presetNum) {
		final int sides = SIDES[presetNum];
		Die die = new Die(sides);
		int d[] = new int[10]; // 10 dice trow results
		for(int i = 0; i < 10; ++i) {
			die.roll();
			d[i] = die.getValue();
		}
		++rollCounter;
		int s = 0;

		String header = "Roll " + rollCounter +
			" Preset Number: " + presetNum + " Sides: " + sides;
		String values = String.format(
			"10d%-5d: %4d%4d%4d%4d%4d  |%4d%4d%4d%4d%4d", sides,
			d[0], d[1], d[2], d[3], d[4], d[5], d[6], d[7], d[8], d[9]);
		String sums = String.format(
			"    sums: %4d%4d%4d%4d%4d  |%4d%4d%4d%4d%4d",
			s += d[0], s += d[1], s += d[2], s += d[3], s += d[4], s += d[5],
			s += d[6], s += d[7], s += d[8], s += d[9]);

		System.out.println(header);
		System.out.println(values);
		System.out.println(sums);
		System.out.println();

		if(!noLogFile) {
			try {
				log.write(header + "\n" + values + "\n" + sums + "\n\n");
				log.flush();
			}
			catch(Exception e) {
				System.out.println("Could not writ to log file!");
			}
		}

		textArea.setText("Roll " + rollCounter +
			" Preset Number: " + presetNum + " Sides: " + sides + "\n" +
			values + "\n" + sums + "\n\n" + textArea.getText());

		textArea.setSelectionStart(0);
		textArea.setSelectionEnd(0);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int preset_num = -1;
		int key_code = e.getKeyCode();
		if(key_code > KeyEvent.VK_0 && key_code <= KeyEvent.VK_9 ) {
			preset_num = key_code - KeyEvent.VK_0;
		} else if(key_code > KeyEvent.VK_NUMPAD0 &&
				key_code <= KeyEvent.VK_NUMPAD9) {
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
		roll(Integer.parseInt( e.getActionCommand().substring(0, 1) ));
	}
}
