package model;

import util.Constant;
import view.GameCanvas;
import view.MyFrame;

/**
 * 由 4 * 4 个方格（Box）构成一个块， 控制块的移动、下落、变形等
 */

public class Block {

	private GameCanvas gc;
	private Box[][] boxs = new Box[4][4];
	public int  y, x,x1,y1;
	public int style;
	public int newH = 0;
	public int newL = 0;
	private static int st = 7;//默认初级
	public boolean isAlive = false;
	public boolean pausing = false;

	/**
	 * 设置方块数量
	 */
	public static void set_addl(int a) {
		Block.st = a;
	}

	/**
	 * 获取方块数量
	 */
	public static int get_addl() {
		return st;
	}

	/**
	 * 空构造函数
	 */
	public Block() {

	}

	/**
	 * 构造函数，产生一个特定的块
	 * 
	 * @param style
	 *            块的样式，对应STYLES的28个值中的一个
	 * @param y
	 *            起始位置，左上角在canvas中的坐标行
	 * @param x
	 *            起始位置，左上角在canvas中的坐标列
	 * @param level
	 *            游戏等级，控制块的下落速度
	 * @param canvas
	 *            画板
	 */
	public Block(int style, int y, int x,  GameCanvas gc) {
		this.style = style;
		this.y = y;
		this.x = x;
		this.gc = gc;
		int key = 0x8000;
		for (int i = 0; i < boxs.length; i++) {
			for (int j = 0; j < boxs[i].length; j++) {
				boolean isColor = ((style & key) != 0);
				boxs[i][j] = new Box(isColor);
				key >>= 1;
			}
		}
		display();// 调用显示函数

	}
	public void earse() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (boxs[i][j].isColorBox()) {
					Box box = gc.getBox(i + y, j + x);
					if (box == null)
						continue;
					box.setColor(false);
				}
			}
		}
	}
	public void display() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (boxs[i][j].isColorBox()) {
					Box box = gc.getBox(y + i, x + j);// 获得在游戏界面的位置
					if (box == null)
						continue;
					else
						box.setColor(true);
				}
			}
		}
	}

	/**
	 * 左移方法
	 */
	public void moveLeft() {

		newL = x - 1;
		newH = y;
		if (isMoveAble(newH, newL) && !pausing) {
			earse();
			x--;
			display();
			gc.repaint();
		}
	}

	/**
	 * 右移方法
	 */
	public void moveRight() {

		newL = x + 1;
		newH = y;
		if (isMoveAble(newH, newL) && !pausing) {
			earse();
			x++;
			display();
			gc.repaint();
		}
	}

	/**
	 * 向下加速
	 */
	public void moveDown() {
		newL = x;
		newH = y + 1;
		if (pausing == true)
			return;
		if (isMoveAble(newH, newL)) {
			earse();
			y++;
			display();
			gc.repaint();
		} else {
			isAlive = false;
			// mytask.cancel();
		}// 取消定时器任务
	}

	/**
	 * 瞬间到达底部
	 */
	public void quickDown() {
		newL = x;
		newH = y + 1;
		while (isMoveAble(newH, newL)) {
			earse();
			y++;
			display();
			gc.repaint();
			newL = x;
			newH = y + 1;
		}
		isAlive = false;
	}

/*	*//**
	 * 阴影实现
	 *//*
	public void yy() {
		int yy = y;
		newL = x;
		newH = yy + 1;

		while (isMoveAble(newH, newL)) {
			earse();
			yy++;
			display();
			newL = x;
			newH = yy + 1;
		}
		earse();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (boxs[i][j].isColorBox() == true)
					yinying[i][j]=yy+j;
			}
		}
	}*/
	/**
	 * 旋转功能的实现
	 */
	public void moveUp() {
		earse();
		if (pausing == true)
			return;
		if (isChangeAble()) {
			// 获取下一形态
			int key = 0x8000;
			for (int a = 0; a < 4; a++) {
				for (int b = 0; b < 4; b++) {
					boolean isColor = ((this.style & key) != 0);
					boxs[a][b].setColor(isColor);
					key >>= 1;
				}
			}
			display();
			gc.repaint();
		}
	}

	/**
	 * 
	 * 判断是否可以移动 获取下个位置是否为空，或者已经是有颜色的
	 */
	public boolean isMoveAble(int newH, int newL) {
		earse();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (boxs[i][j].isColorBox()) {
					Box box = gc.getBox(newH + i, newL + j);
					if (box == null || (box.isColorBox())) {
						display();// 显示原先的位置
						return false;
					}
				}
			}
		}
		display();
		return true;
	}

	/**
	 * 判断是否可以进行变形
	 */
	private boolean isChangeAble() {
		int key = 0x8000;
		int newStyle = 0;
		earse();
		for (int i = 0; i < st; i++) {
			for (int j = 0; j < 4; j++) {
				if (Constant.STYLES[i][j] == this.style) {
					newStyle = Constant.STYLES[i][(j + 1) % 4];// 取得下一个状态
					break;
				}

				// display();
			}
		}
		// 判断此状态在游戏界面上显示是否有障碍
		for (int a = 0; a < boxs.length; a++) {
			for (int b = 0; b < boxs[a].length; b++) {
				if ((newStyle & key) != 0) {
					Box box = gc.getBox(y + a, x + b);
					if (box == null || box.isColorBox()) {
						// display();
						return false;
					}
				}
				key >>= 1;
			}
		}
		this.style = newStyle;
		return true;
	}

	/**
	 * 暂停
	 */
	public void pause() {
		// TODO Auto-generated method stub
		if (MyFrame.playing == true) {
			pausing = true;
			// isMoveAble(newH, newL);
			gc.pau = true;
			gc.repaint();
		}
	}

	/**
	 * 继续
	 */
	public void jixu() {
		if (MyFrame.playing == true) {
			pausing = false;
			gc.pau = false;
			gc.repaint();
			;
		}
	}

}