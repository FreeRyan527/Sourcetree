import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class PanelTitle extends JPanel {

	private static final long serialVersionUID = 1L;

	public PanelTitle() {
		setBounds(new Rectangle(50, 600, 839, 30));
	}

	@Override
	public void paint(Graphics g) {
		g.setFont(new Font("Times",Font.BOLD,16));
		g.drawString(
				"Welcome to play the game of hex. The top side and button side are blue. The left side and right side are red.",
				0, 20);
	}
}