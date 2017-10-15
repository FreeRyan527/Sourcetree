import java.util.Stack;

public class HexGame {
	//创建各类对象
	public static FrameGame frame = new FrameGame();
	public static PanelGame panelGame = new PanelGame();
	public static PanelButton panelButton = new PanelButton();
	public static PanelShowTurns panelShowTurns = new PanelShowTurns();
	
	public static PanelTitle panelTittle = new PanelTitle();
	
	public static int[][] chessBoard = new int[11][11];
	
	public static Stack<Integer> undoStack = new Stack<Integer>();
	
	public static final int EMPTY = 0; // 用于标记棋盘格子是否为空
	public static final int BLUE = 1;
	public static final int RED = 2;
	public static final int END = 3;
	public static final int SWAP = 4;
	
	public static int flag = BLUE;
	
	public static final int PVP = 1;
	public static final int CVP = 2;
	public static final int PVC = 3;
	
	public static int model = PVP;
	
	public static int depth = 1;
	
	public static ComputerBrain computerBrain = new ComputerBrain();
	
	public static void main(String[] args) {
		panelGame.addMouseListener(new ListenerMouse());
		frame.add(panelGame);
		frame.add(panelButton);
		frame.add(panelShowTurns);
		frame.add(panelTittle);
		frame.setVisible(true);
	}
}