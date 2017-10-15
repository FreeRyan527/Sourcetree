import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class PanelGame extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final int r = 30;
	public static final int xCenter = 30;
	public static final int yCenter = 30;

	public static final int side = r / 2;
	public static final int distance = (int) Math.rint(r * Math.cos(Math.PI / 6));

	public static final int x0 = xCenter - distance;
	public static final int y0 = yCenter - r;
	
	public PanelGame() {
		setBounds(new Rectangle(50, 80, 839, 511));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (int row = 0; row < 11; row++) {
			int xInitial = xCenter + row * distance;
			int yInitial = yCenter + row * (r + side);
			for (int col = 0; col < 11; col++) {
				int x = xInitial + col * (2 * distance);
				if (HexGame.chessBoard[row][col] == 1) {
					paintHex(x, yInitial, g, Color.BLUE);
				} else if (HexGame.chessBoard[row][col] == 2) {
					paintHex(x, yInitial, g, Color.RED);
				} else {
					paintHex(x, yInitial, g);
				}
			}
		}
	}

	public void paintHex(int x, int y, Graphics g, Color c) {
		Polygon p = new Polygon();
		p.addPoint(x, (y + r));
		p.addPoint((x + distance), (y + side));
		p.addPoint((x + distance), (y - side));
		p.addPoint(x, (y - r));
		p.addPoint((x - distance), (y - side));
		p.addPoint((x - distance), (y + side));
		g.setColor(c);
		g.fillPolygon(p);
		g.setColor(Color.BLACK);
		g.drawPolygon(p);
	}

	public void paintHex(int x, int y, Graphics g) {
		Polygon p = new Polygon();
		p.addPoint(x, (y + r));
		p.addPoint((x + distance), (y + side));
		p.addPoint((x + distance), (y - side));
		p.addPoint(x, (y - r));
		p.addPoint((x - distance), (y - side));
		p.addPoint((x - distance), (y + side));
		g.drawPolygon(p);
	}
	
}