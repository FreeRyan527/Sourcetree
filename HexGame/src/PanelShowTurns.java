import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class PanelShowTurns extends JPanel {

	private static final long serialVersionUID = 1L;

	public PanelShowTurns() {
		setBounds(new Rectangle(350, 30, 150, 40));
	}

	@Override
	public void paint(Graphics g) {
		if (HexGame.flag == HexGame.BLUE) {
			g.setFont(new Font("Times",Font.BOLD,16));
			g.setColor(Color.BLUE);
			g.drawString("Blue Turn", 0, 20);
		} else if (HexGame.flag == HexGame.RED) {
			g.setFont(new Font("Times",Font.BOLD,16));
			g.setColor(Color.RED);
			g.drawString("Red Turn", 0, 20);
		} else {
			g.setFont(new Font("Times",Font.BOLD,16));
			g.drawString("Game Over", 0, 20);
		}
	}
}