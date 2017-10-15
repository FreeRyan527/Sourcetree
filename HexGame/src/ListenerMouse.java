import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

public class ListenerMouse implements MouseListener {

	public static int row = 0;
	public static int col = 0;

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) { // 鼠标监听事件
		if (HexGame.flag != HexGame.END) {
			int x = e.getX();
			int y = e.getY();
			row = calRowIndex(y); // 计算行数
			col = calColIndex(x, row); // 计算列数

			if (play(row, col)) { // 人下棋
				HexGame.panelGame.repaint(); // 重画棋盘及棋子
				if (!checkWin()) { // 判断胜利
					takeTurn(); // 更换下棋方
					if (HexGame.model == HexGame.CVP || HexGame.model == HexGame.PVC) { // 是否与电脑下棋
						computerPlay(); // 电脑下棋
						HexGame.panelGame.repaint(); // 重画棋盘及棋子
						if (!checkWin()) { // 判断胜利
							takeTurn(); // 更换下棋方
						}
					}
				}
			}
		}
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
	}

	/**
	 * 
	 * @param y
	 * @return
	 */
	private int calRowIndex(int y) {
		int row1 = (int) ((y - PanelGame.y0) / (1.5 * PanelGame.r)); // 计算一个六边形最顶点的值
		int row2 = (int) (Math.ceil((y - PanelGame.y0 - 2 * PanelGame.r) / (1.5 * PanelGame.r))); // 计算一个六边形的最下面的值，想上取整，可能两个会不一样
		if (row1 == row2) {
			return row1;
		} else {
			return -1;
		}
	}

	/**
	 * 
	 * @param x
	 * @param row
	 * @return
	 */
	private int calColIndex(int x, int row) {
		int col;
		col = (x - PanelGame.x0 - row * PanelGame.distance) / (2 * PanelGame.distance); // 根据行数算出列数，由于不存在重叠，所以是一个固定的值
		return col;
	}

	/**
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	private boolean play(int row, int col) {
		if (row < 0 || row >= 11 || col < 0 || col >= 11) { // 首先判断行列数是否越界
			return false;
		}

		if (HexGame.chessBoard[row][col] != HexGame.EMPTY) { // 数组不为空，说明此处已有落子，不能在此处落子
			return false;
		}

		HexGame.chessBoard[row][col] = HexGame.flag; // 数组为空的话在此处落子并标记，1为蓝色，2为红色

		HexGame.undoStack.push(row);
		HexGame.undoStack.push(col);

		return true;
	}

	/**
	 * 
	 */
	private void computerPlay() {
		BoardPoint bp = HexGame.computerBrain.miniMax(HexGame.depth);
		HexGame.chessBoard[bp.getRow()][bp.getColumn()] = HexGame.flag;
		HexGame.undoStack.push(bp.getRow());
		HexGame.undoStack.push(bp.getColumn());
	}

	/**
	 * 
	 * @return
	 */
	private boolean checkWin() {
		boolean isWin = false; // 初始化胜利条件为false

		if (HexGame.flag == HexGame.BLUE) {
			for (int col = 0; col < 11; col++) {
				isWin = blueWin(0, col, new int[11][11]);
				if (isWin) {
					break;
				}
			}
		} else if (HexGame.flag == HexGame.RED) {
			for (int row = 0; row < 11; row++) {
				isWin = redWin(row, 0, new int[11][11]);
				if (isWin) {
					break;
				}
			}
		}

		if (isWin) { // 判断胜利标记是否为true，如果为true，证明有一方获胜
			if (HexGame.flag == HexGame.BLUE) {
				JOptionPane.showMessageDialog(null, "Blue Win", "Winner", JOptionPane.INFORMATION_MESSAGE);
			}
			if (HexGame.flag == HexGame.RED) {
				JOptionPane.showMessageDialog(null, "Red Win", "Winner", JOptionPane.INFORMATION_MESSAGE);
			}
			HexGame.flag = HexGame.END; // 将flag改为end型，此次下棋游戏结束
			HexGame.panelShowTurns.repaint();
		}

		return isWin;
	}

	private boolean blueWin(int row, int col, int[][] pathFlag) {// 判断胜利，用的递归，属于深度搜索
		if (pathFlag[row][col] == -1) {
			return false;
		}
		if (HexGame.chessBoard[row][col] == HexGame.BLUE) {
			pathFlag[row][col] = 1;
			if (row == 10) {
				return true;
			}
			if (col - 1 >= 0 && pathFlag[row][col - 1] == 0 && blueWin(row, col - 1, pathFlag)) {
				return true;
			}
			if (col + 1 <= 10 && pathFlag[row][col + 1] == 0 && blueWin(row, col + 1, pathFlag)) {
				return true;
			}
			if (row + 1 <= 10 && col - 1 >= 0 && pathFlag[row + 1][col - 1] == 0
					&& blueWin(row + 1, col - 1, pathFlag)) {
				return true;
			}
			if (row + 1 <= 10 && pathFlag[row + 1][col] == 0 && blueWin(row + 1, col, pathFlag)) {
				return true;
			}
			if (row - 1 > 0 && col >= 0 && pathFlag[row - 1][col] == 0 && blueWin(row - 1, col, pathFlag)) {
				return true;
			}
			if (row - 1 > 0 && col + 1 <= 10 && pathFlag[row - 1][col + 1] == 0
					&& blueWin(row - 1, col + 1, pathFlag)) {
				return true;
			}
		}
		pathFlag[row][col] = -1;
		return false;
	}

	private boolean redWin(int row, int col, int[][] pathFlag) {
		if (pathFlag[row][col] == -1) {
			return false;
		}
		if (HexGame.chessBoard[row][col] == HexGame.RED) {
			pathFlag[row][col] = 1;
			if (col == 10) {
				return true;
			}
			if (row - 1 >= 0 && pathFlag[row - 1][col] == 0 && redWin(row - 1, col, pathFlag)) {
				return true;
			}
			if (row + 1 <= 10 && pathFlag[row + 1][col] == 0 && redWin(row + 1, col, pathFlag)) {
				return true;
			}
			if (row - 1 >= 0 && col + 1 <= 10 && pathFlag[row - 1][col + 1] == 0
					&& redWin(row - 1, col + 1, pathFlag)) {
				return true;
			}
			if (col + 1 <= 10 && pathFlag[row][col + 1] == 0 && redWin(row, col + 1, pathFlag)) {
				return true;
			}
			if (row + 1 <= 10 && col - 1 >= 0 && pathFlag[row + 1][col - 1] == 0
					&& redWin(row + 1, col - 1, pathFlag)) {
				return true;
			}
			if (row <= 10 && col - 1 >= 0 && pathFlag[row + 1][col - 1] == 0 && redWin(row, col - 1, pathFlag)) {
				return true;
			}
		}
		pathFlag[row][col] = -1;
		return false;
	}

	private void takeTurn() { // 更改选手标签，交换下棋
		if (HexGame.flag == HexGame.BLUE) {
			HexGame.flag = HexGame.RED;
			HexGame.panelShowTurns.repaint();
		} else if (HexGame.flag == HexGame.RED) {
			HexGame.flag = HexGame.BLUE;
			HexGame.panelShowTurns.repaint();
		}
	}
}