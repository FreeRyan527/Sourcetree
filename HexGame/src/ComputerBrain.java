import java.util.HashSet;
import java.util.Iterator;

public class ComputerBrain {
	
	// 设置棋盘分数
//	public static int[][] position = { { 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11 },
//									   { 11,  2,  3,  4,  5,  6,  5,  4,  3,  2, 11 },
//									   { 11,  3,  4,  5,  6,  7,  6,  5,  4,  3, 11 },
//									   { 11,  4,  5,  6,  7,  8,  7,  6,  5,  4, 11 },
//									   { 11,  5,  6,  7,  8,  9,  8,  7,  6,  5, 11 },
//									   { 11,  6,  7,  8,  9, 10,  9,  8,  7,  6, 11 },
//									   { 11,  5,  6,  7,  8,  9,  8,  7,  6,  5, 11 },
//									   { 11,  4,  5,  6,  7,  8,  7,  6,  5,  4, 11 },
//									   { 11,  3,  4,  5,  6,  7,  6,  5,  4,  3, 11 },
//									   { 11,  2,  3,  4,  5,  6,  5,  4,  3,  2, 11 },
//									   { 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11 }, };

	public static int blueLinkedLength = 0;// 蓝色棋子长度
	public static int redLinkedLength = 0;// 红色棋子长度

//	public static BoardPoint blueStart = null;
//	public static BoardPoint blueEnd = null;
//	public static BoardPoint redStart = null;
//	public static BoardPoint redEnd = null;
	
	public static HashSet<BoardPoint> hsBlue = new HashSet<BoardPoint>();
	public static HashSet<BoardPoint> hsRed = new HashSet<BoardPoint>();

	//估值函数
	public int valuation() {
		int blueScore = blueLinkedScore(new int[11][11]);
		int redScore = redLinkedScore(new int[11][11]);
		return (blueScore - redScore);
	}

//	  计算蓝色棋子分数
	public int blueLinkedScore(int[][] pathFlag) {// 动态规划
		blueLinkedLength = 0;
		hsBlue.clear();
		for (int row = 0; row < 11; row++) {// 从上往下扫描
			for (int col = 0; col < 11; col++) {
				if (row == 0 && HexGame.chessBoard[0][col] == HexGame.BLUE) {// 如果是第一行，之间寻找蓝色点并标记为1即可
					pathFlag[0][col] = 1;
				} else if (HexGame.chessBoard[row][col] == HexGame.BLUE) {// 如果不是第一行，可以分为3中情况讨论
					// 第一种，先check该棋子上方连着的两个，并选取最大的一个值，加1并赋给自己
					if (pathFlag[row - 1][col] > 0) {
						pathFlag[row][col] = pathFlag[row - 1][col] + 1;
					}
					if (col < 10 && pathFlag[row - 1][col + 1] > pathFlag[row - 1][col]) {
						pathFlag[row][col] = pathFlag[row - 1][col + 1] + 1;
					}

					// 第二种，如果上方没有棋子，接着check该棋子左右两侧的棋子，并选取一个最大值赋给自己
					if (pathFlag[row][col] == 0) {
						if (col > 0 && pathFlag[row][col - 1] > 0) {
							pathFlag[row][col] = pathFlag[row][col - 1];
						}
						if (col < 10 && pathFlag[row][col + 1] > pathFlag[row][col]) {
							pathFlag[row][col] = pathFlag[row][col + 1];
						}
					}

					// 第三种，如果前两种情况都不存在，那么直接给该棋子赋值为1
					if (pathFlag[row][col] == 0) {
						pathFlag[row][col] = 1;
					}

					// 记录最大长度
					if (pathFlag[row][col] > blueLinkedLength) {
						blueLinkedLength = pathFlag[row][col];
					}
				}
			}
		}
//		for (int row = 0; row < 11; row++) {// 从上往下扫描
//			for (int col = 0; col < 11; col++) {
//				if (row == 0 && HexGame.chessBoard[0][col] == HexGame.BLUE) {// 如果是第一行，之间寻找蓝色点并标记为1即可
//					pathFlag[0][col] = 1;
//				} else if (HexGame.chessBoard[row][col] == HexGame.BLUE) {// 如果不是第一行，可以分为3中情况讨论
//					if (pathFlag[row - 1][col] > 0 || pathFlag[row - 1][col + 1] > 0) {// 第一种，先check该棋子上方连着的两个，并选取最大的一个值，加1并赋给自己
//						pathFlag[row][col] = Math.max(pathFlag[row - 1][col], pathFlag[row - 1][col + 1]) + 1;
//					} else if (pathFlag[row][col - 1] > 0 || pathFlag[row][col + 1] > 0) {// 第二种，如果上方没有棋子，接着check该棋子左右两侧的棋子，并选取一个最大值赋给自己
//						pathFlag[row][col] = Math.max(pathFlag[row][col - 1], pathFlag[row][col + 1]);
//					} else {// 第三种，如果前两种情况都不存在，那么直接给该棋子赋值为1
//						pathFlag[row][col] = 1;
//					}
//				}
//				if (pathFlag[row][col] > blueLinkedLength) {
//					blueLinkedLength = pathFlag[row][col];
//				}
//			}
//		}

		for (int col = 0; col < 11; col++) {
			if (pathFlag[0][col] == 1) {
				if (pathFlag[1][col] == 0 && (col == 0 || pathFlag[1][col - 1] == 0)) {
					hsBlue.add(new BoardPoint(0, col));
				}
			}
			if (pathFlag[10][col] == 1) {
				hsBlue.add(new BoardPoint(10, col));
			}
		}
		for (int row = 1; row < 10; row++) {
			for (int col = 0; col < 11; col++) {
				if (pathFlag[row][col] == 1) {
					hsBlue.add(new BoardPoint(row, col));
				}
				if (pathFlag[row][col] == blueLinkedLength) {
					hsBlue.add(new BoardPoint(row, col));
				}
			}
		}

//		
//		int row = blueEnd.getRow();
//		int col = blueEnd.getColumn();
//		int sum = position[row][col];
//		for (int index = blueLinkedLength; index > 1; index--) {
//			if (pathFlag[row - 1][col] == index - 1) {
//				sum += position[row - 1][col];
//				row--;
//			} else if (pathFlag[row - 1][col + 1] == index - 1) {
//				sum += position[row - 1][col + 1];
//				row--;
//				col++;
//			} else if (pathFlag[row][col - 1] == index - 1) {
//				sum += position[row][col - 1];
//				col--;
//			} else if (pathFlag[row][col + 1] == index - 1) {
//				sum += position[row][col + 1];
//				col++;
//			}
//		}
//		blueStart = new BoardPoint(row, col);
		
		return blueLinkedLength;
	}

	//计算红色棋子分数
	public int redLinkedScore(int[][] pathFlag) {// 动态规划
		redLinkedLength = 0;
		hsRed.clear();
		for (int col = 0; col < 11; col++) {
			for (int row = 0; row < 11; row++) {
				if (col == 0 && HexGame.chessBoard[row][0] == HexGame.RED) {
					pathFlag[row][0] = 1;
				} else if (HexGame.chessBoard[row][col] == HexGame.RED) {
					if (pathFlag[row][col - 1] > 0) {
						pathFlag[row][col] = pathFlag[row][col - 1] + 1;
					}
					if (row < 10 && pathFlag[row + 1][col - 1] > pathFlag[row][col - 1]) {
						pathFlag[row][col] = pathFlag[row + 1][col - 1] + 1;
					}

					if (pathFlag[row][col] == 0) {
						if (row < 10 && pathFlag[row + 1][col] > 0) {
							pathFlag[row][col] = pathFlag[row + 1][col];
						}
						if (row > 0 && pathFlag[row - 1][col] > pathFlag[row][col]) {
							pathFlag[row][col] = pathFlag[row - 1][col];
						}
					}

					if (pathFlag[row][col] == 0) {
						pathFlag[row][col] = 1;
					}

					if (pathFlag[row][col] > redLinkedLength) {
						redLinkedLength = pathFlag[row][col];
					}
				}
			}
		}

//		for (int col = 0; col < 11; col++) {
//			for (int row = 0; row < 11; row++) {
//				if (col == 0 && HexGame.chessBoard[row][0] == HexGame.RED) {
//					pathFlag[row][0] = 1;
//				} else if (HexGame.chessBoard[row][col] == HexGame.RED) {
//					if (pathFlag[row][col - 1] > 0 || pathFlag[row + 1][col - 1] > 0) {
//						pathFlag[row][col] = Math.max(pathFlag[row][col - 1], pathFlag[row + 1][col - 1]) + 1;
//					} else if (pathFlag[row + 1][col] > 0 || pathFlag[row - 1][col] > 0) {
//						pathFlag[row][col] = Math.max(pathFlag[row][col - 1], pathFlag[row][col + 1]);
//					} else {
//						pathFlag[row][col] = 1;
//					}
//				}
//			}
//		}

//		for (int row = 0; row < 11; row++) {
//			for (int a : pathFlag[row]) {
//				if (a > redLinkedLength) {
//					redLinkedLength = a;
//				}
//			}
//		}

		for (int row = 0; row < 11; row++) {
			if (pathFlag[row][0] == 1) {
				if (pathFlag[row][1] == 0 && (row == 0 || pathFlag[row - 1][1] == 0)) {
					hsRed.add(new BoardPoint(row, 0));
				}
			}
			if (pathFlag[row][10] == 1) {
				hsRed.add(new BoardPoint(row, 10));
			}
		}
		for (int col = 1; col < 10; col++) {
			for (int row = 0; row < 11; row++) {
				if (pathFlag[row][col] == 1) {
					hsRed.add(new BoardPoint(row, col));
				}
				if (pathFlag[row][col] == redLinkedLength) {
					hsRed.add(new BoardPoint(row, col));
				}
			}
		}

//		int row = redEnd.getRow();
//		int col = redEnd.getColumn();
//		int sum = position[row][col];
//		int index = redLinkedLength;
//		while (index > 1) {
//			if (pathFlag[row][col - 1] == index - 1) {
//				sum += position[row][col - 1];
//				col--;
//				index--;
//			} else if (pathFlag[row + 1][col - 1] == index - 1) {
//				sum += position[row + 1][col - 1];
//				row++;
//				col--;
//				index--;
//			} else if (pathFlag[row + 1][col] == index) {
//				sum += position[row + 1][col];
//				row++;
//			} else if (pathFlag[row - 1][col] == index) {
//				sum += position[row - 1][col];
//				row--;
//			}
//		}
//		redStart = new BoardPoint(row, col);

		return redLinkedLength;
	}

	// 最大最小值算法. 数值越大，对于蓝方越有优势； 数值越小，对于红方越有优势.
	public BoardPoint miniMax(int depth) {
		BoardPoint optimalPoint = null;
		if (HexGame.flag == HexGame.BLUE) {
			if (blueLinkedLength == 0) {
				if (HexGame.chessBoard[5][5] == HexGame.EMPTY) {
					optimalPoint = new BoardPoint(5, 5);
					blueLinkedLength = 1;
				} else if (HexGame.chessBoard[4][5] == HexGame.EMPTY) {
					optimalPoint = new BoardPoint(4, 5);
					blueLinkedLength = 1;
				} else if (HexGame.chessBoard[6][5] == HexGame.EMPTY) {
					optimalPoint = new BoardPoint(6, 5);
					blueLinkedLength = 1;
				}
			} else {
				int best = Integer.MIN_VALUE;
				int val = Integer.MIN_VALUE;
				HashSet<BoardPoint> hsBoardPoint = getPosIndex(HexGame.BLUE);
				for (BoardPoint point : hsBoardPoint) {// 遍历动态数组
					HexGame.chessBoard[point.getRow()][point.getColumn()] = HexGame.BLUE;
					val = Min(depth - 1);// 通过Min方法算出最小处的val
					if (val > best) {
						best = val;
						optimalPoint = point;
					}
					HexGame.chessBoard[point.getRow()][point.getColumn()] = 0;
				}
			}
		} else {
			if (redLinkedLength == 0) {
				if (HexGame.chessBoard[5][5] == HexGame.EMPTY) {
					optimalPoint = new BoardPoint(5, 5);
					redLinkedLength = 1;
				} else if (HexGame.chessBoard[5][4] == HexGame.EMPTY) {
					optimalPoint = new BoardPoint(5, 4);
					redLinkedLength = 1;
				} else if (HexGame.chessBoard[5][6] == HexGame.EMPTY) {
					optimalPoint = new BoardPoint(5, 6);
					redLinkedLength = 1;
				}
			} else {
				int best = Integer.MAX_VALUE;
				int val = Integer.MAX_VALUE;
				HashSet<BoardPoint> hsBoardPoint = getPosIndex(HexGame.RED);
				for (BoardPoint point : hsBoardPoint) {
					HexGame.chessBoard[point.getRow()][point.getColumn()] = HexGame.RED;
					val = Max(depth - 1);
					if (val < best) {
						best = val;
						optimalPoint = point;
					}
					HexGame.chessBoard[point.getRow()][point.getColumn()] = 0;
				}
			}
		}
		return optimalPoint;
	}

	public int Max(int depth) {
		int best = Integer.MIN_VALUE;
		int val = Integer.MIN_VALUE;
		if (depth <= 0) {
			return valuation();
		}
		HashSet<BoardPoint> hsBoardPoint = getPosIndex(HexGame.BLUE);
		for (BoardPoint point : hsBoardPoint) {
			HexGame.chessBoard[point.getRow()][point.getColumn()] = HexGame.BLUE;
			val = Min(depth - 1);
			if (val > best) {
				best = val;
			}
			HexGame.chessBoard[point.getRow()][point.getColumn()] = 0;
		}
		return best;
	}

	public int Min(int depth) {
		int best = Integer.MAX_VALUE;
		int val = Integer.MAX_VALUE;
		if (depth <= 0) {
			return valuation();
		}
		HashSet<BoardPoint> hsBoardPoint = getPosIndex(HexGame.RED);
		for (BoardPoint point : hsBoardPoint) {
			HexGame.chessBoard[point.getRow()][point.getColumn()] = HexGame.RED;
			val = Max(depth - 1);
			if (val < best) {
				best = val;
			}
			HexGame.chessBoard[point.getRow()][point.getColumn()] = 0;
		}
		return best;
	}

// 获取可下棋的位置
	public HashSet<BoardPoint> getPosIndex(int flag) {
		valuation();
		HashSet<BoardPoint> hsBoardPoint = new HashSet<BoardPoint>();
		if (flag == HexGame.BLUE) {// 在这后面写上有那些步骤可以选择 针对蓝色方
			Iterator<BoardPoint> iterator = hsBlue.iterator();
			while (iterator.hasNext()) {
				BoardPoint boardPoint = iterator.next();
				int row = boardPoint.getRow();
				int col = boardPoint.getColumn();
				if (row > 0 && HexGame.chessBoard[row - 1][col] == HexGame.EMPTY) {
					hsBoardPoint.add(new BoardPoint(row - 1, col));
				}
				if (row > 0 && col < 10 && HexGame.chessBoard[row - 1][col + 1] == HexGame.EMPTY) {
					hsBoardPoint.add(new BoardPoint(row - 1, col + 1));
				}
				if (row != 0 && row != 10 && col < 10 && HexGame.chessBoard[row][col + 1] == HexGame.EMPTY) {
					hsBoardPoint.add(new BoardPoint(row, col + 1));
				}
				if (row < 10 && HexGame.chessBoard[row + 1][col] == HexGame.EMPTY) {
					hsBoardPoint.add(new BoardPoint(row + 1, col));
				}
				if (row < 10 && col > 0 && HexGame.chessBoard[row + 1][col - 1] == HexGame.EMPTY) {
					hsBoardPoint.add(new BoardPoint(row + 1, col - 1));
				}
				if (row != 0 && row != 10 && col > 0 && HexGame.chessBoard[row][col - 1] == HexGame.EMPTY) {
					hsBoardPoint.add(new BoardPoint(row, col - 1));
				}
			}
		} else { // 这里面也是步骤 但是是针对红色方
			Iterator<BoardPoint> iterator = hsRed.iterator();
			while (iterator.hasNext()) {
				BoardPoint boardPoint = iterator.next();
				int row = boardPoint.getRow();
				int col = boardPoint.getColumn();
				if (col != 0 && col != 10 && row > 0 && HexGame.chessBoard[row - 1][col] == HexGame.EMPTY) {
					hsBoardPoint.add(new BoardPoint(row - 1, col));
				}
				if (row > 0 && col < 10 && HexGame.chessBoard[row - 1][col + 1] == HexGame.EMPTY) {
					hsBoardPoint.add(new BoardPoint(row - 1, col + 1));
				}
				if (col < 10 && HexGame.chessBoard[row][col + 1] == HexGame.EMPTY) {
					hsBoardPoint.add(new BoardPoint(row, col + 1));
				}
				if (col != 0 && col != 10 && row < 10 && HexGame.chessBoard[row + 1][col] == HexGame.EMPTY) {
					hsBoardPoint.add(new BoardPoint(row + 1, col));
				}
				if (row < 10 && col > 0 && HexGame.chessBoard[row + 1][col - 1] == HexGame.EMPTY) {
					hsBoardPoint.add(new BoardPoint(row + 1, col - 1));
				}
				if (col > 0 && HexGame.chessBoard[row][col - 1] == HexGame.EMPTY) {
					hsBoardPoint.add(new BoardPoint(row, col - 1));
				}
			}
		}
		return hsBoardPoint;
	}
}
