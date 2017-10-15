import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerButton implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String btnName = e.getActionCommand();// 获取按键名字
		if ("New Game (PVP)".equals(btnName)) {// 新游戏，用于初始化各个全局变量
			for (int row = 0; row < 11; row++) {
				for (int col = 0; col < 11; col++) {
					HexGame.chessBoard[row][col] = 0;
				}
			}
			HexGame.model = HexGame.PVP;
			HexGame.flag = HexGame.BLUE;
			HexGame.panelGame.repaint();
			HexGame.panelShowTurns.repaint();
			ComputerBrain.blueLinkedLength = 0;
			ComputerBrain.redLinkedLength = 0;
		} else if ("New Game (PVC)".equals(btnName)) {
			for (int row = 0; row < 11; row++) {
				for (int col = 0; col < 11; col++) {
					HexGame.chessBoard[row][col] = 0;
				}
			}
			HexGame.model = HexGame.PVC;
			HexGame.flag = HexGame.BLUE;
			HexGame.depth = 1;
			HexGame.panelGame.repaint();
			HexGame.panelShowTurns.repaint();
			ComputerBrain.blueLinkedLength = 0;
			ComputerBrain.redLinkedLength = 0;
		} else if ("New Game (CVP)".equals(btnName)) {
			for (int row = 0; row < 11; row++) {
				for (int col = 0; col < 11; col++) {
					HexGame.chessBoard[row][col] = 0;
				}
			}
			HexGame.model = HexGame.CVP;
			HexGame.flag = HexGame.RED;
			HexGame.depth = 1;
			HexGame.panelGame.repaint();
			HexGame.panelShowTurns.repaint();
			ComputerBrain.blueLinkedLength = 0;
			ComputerBrain.redLinkedLength = 0;
			
			// 电脑下棋
			BoardPoint bp = HexGame.computerBrain.miniMax(3);
			HexGame.chessBoard[bp.getRow()][bp.getColumn()] = HexGame.flag;
			HexGame.undoStack.push(bp.getRow());
			HexGame.undoStack.push(bp.getColumn());
			HexGame.panelGame.repaint();
			HexGame.flag = HexGame.BLUE;
			HexGame.panelShowTurns.repaint();
		} else if ("Undo".equals(btnName)) {// undo button，用于撤销已经走过的棋子
			if (HexGame.model == HexGame.PVP && HexGame.undoStack.size() > 0) {
				int col = HexGame.undoStack.pop();
				int row = HexGame.undoStack.pop();
				HexGame.chessBoard[row][col] = 0;
				if (HexGame.flag == HexGame.BLUE) {
					HexGame.flag = HexGame.RED;
				} else if (HexGame.flag == HexGame.RED) {
					HexGame.flag = HexGame.BLUE;
				}
				HexGame.panelGame.repaint();
				HexGame.panelShowTurns.repaint();
			} else if (HexGame.undoStack.size() > 1) {
				int col = HexGame.undoStack.pop();
				int row = HexGame.undoStack.pop();
				HexGame.chessBoard[row][col] = 0;
				col = HexGame.undoStack.pop();
				row = HexGame.undoStack.pop();
				HexGame.chessBoard[row][col] = 0;
				HexGame.panelGame.repaint();
			}
		}
		
		if ("Swap".equals(btnName)) {
			for (int row = 0; row < 11; row++) {
				for (int col = 0; col < 11; col++) {
					if (HexGame.chessBoard[row][col] == HexGame.BLUE) {
						HexGame.chessBoard[row][col] = HexGame.SWAP;
					}
				}
			}
			for (int row = 0; row < 11; row++) {
				for (int col = 0; col < 11; col++) {
					if (HexGame.chessBoard[row][col] == HexGame.RED) {
						HexGame.chessBoard[row][col] = HexGame.BLUE;
					}
				}
			}
			for (int row = 0; row < 11; row++) {
				for (int col = 0; col < 11; col++) {
					if (HexGame.chessBoard[row][col] == HexGame.SWAP) {
						HexGame.chessBoard[row][col] = HexGame.RED;
					}
				}
			}
			
			if (HexGame.flag == HexGame.BLUE) {
				HexGame.flag = HexGame.RED;
			} else if (HexGame.flag == HexGame.RED) {
				HexGame.flag = HexGame.BLUE;
			}
			
			HexGame.panelGame.repaint();
			HexGame.panelShowTurns.repaint();
		}

		if ("Easy".equals(btnName)) {
			HexGame.depth = 1;
		} else if ("Normal".equals(btnName)) {
			HexGame.depth = 2;
		} else if ("Hard".equals(btnName)) {
			HexGame.depth = 3;
		}
	}
}