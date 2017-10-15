
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelButton extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btnPVP = new JButton("New Game (PVP)");
	private JButton btnPVC = new JButton("New Game (PVC)");
	private JButton btnCVP = new JButton("New Game (CVP)");
	private JButton btnUndo = new JButton("Undo");
	private JButton btnSwap = new JButton("Swap");
	private JButton btnEasy = new JButton("Easy");
	private JButton btnNormal = new JButton("Normal");
	private JButton btnHard = new JButton("Hard");

	public PanelButton() {
		setBounds(new Rectangle(900, 80, 200, 511));

		btnPVP.addActionListener(new ListenerButton());
		btnPVC.addActionListener(new ListenerButton());
		btnCVP.addActionListener(new ListenerButton());
		btnUndo.addActionListener(new ListenerButton());
		btnSwap.addActionListener(new ListenerButton());
		btnEasy.addActionListener(new ListenerButton());
		btnNormal.addActionListener(new ListenerButton());
		btnHard.addActionListener(new ListenerButton());

		GridLayout layout = new GridLayout(8, 1);
		layout.setVgap(5);
		this.setLayout(layout);
		
		add(btnPVP);
		add(btnPVC);
		add(btnCVP);
		add(btnUndo);
		add(btnSwap);
		add(btnEasy);
		add(btnNormal);
		add(btnHard);
	}
}