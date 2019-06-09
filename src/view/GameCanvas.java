package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import model.Box;

public class GameCanvas extends JPanel {
	private Color backColor = Color.GRAY, frontColor = Color.orange;
	private int rows, cols, score = 0, scoreForLevelUpdate = 0;
	private Box[][] boxes;
	private int boxWidth=25, boxHeight=25;
	private boolean gameOver=false;
	public boolean pau=false;

	/**
	 * 画布类的构造函数
	 * @param rows int, 画布的行数
	 * @param cols int, 画布的列数
	 * 行数和列数决定着画布拥有方格的数目
	 */
	public GameCanvas(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.setOpaque(false);
		boxes = new Box[rows][cols];
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++) {
				boxes[i][j] = new Box(false);
			}
		}
		setBounds(0, 0, 300, 500);//设置相对位置坐标
		setBorder(new EtchedBorder(
		        EtchedBorder.RAISED, Color.white, new Color(148, 145, 140)));
	}

	/**
	 * 画布类的构造函数
	 * @param rows 与public GameCanvas(int rows, int cols)同
	 * @param cols 与public GameCanvas(int rows, int cols)同
	 * @param backColor Color, 背景色
	 * @param frontColor Color, 前景色
	 */
	public GameCanvas(int rows, int cols,
	                  Color backColor, Color frontColor) {
		this(rows, cols);
		this.backColor = backColor;
		this.frontColor = frontColor;
	}

	/**
	 * 设置游戏背景色彩
 	 * @param backColor Color, 背景色彩
	 */
	public void setBackgroundColor(Color backColor) {
		this.backColor = backColor;
	}

	/**
	 * 取得游戏背景色彩
 	 * @return Color, 背景色彩
	 */
	public Color getBackgroundColor() {
		return backColor;
	}

	/**
	 * 设置游戏方块色彩
 	 * @param frontColor Color, 方块色彩
	 */
	public void setBlockColor(Color frontColor) {
		this.frontColor = frontColor;
	}

	/**
	 * 取得游戏方块色彩
 	 * @return Color, 方块色彩
	 */
	public Color getBlockColor() {
		return frontColor;
	}

	/**
	 * 取得画布中方格的行数
	 * @return int, 方格的行数
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * 取得画布中方格的列数
	 * @return int, 方格的列数
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * 取得游戏成绩
	 * @return int, 分数
	 */
	public int getScore() {
		return score;
	}

	/**
	 * 取得自上一次升级后的积分
	 * @return int, 上一次升级后的积分
	 */
	public int getScoreForLevelUpdate() {
		return scoreForLevelUpdate;
	}


	/**
	 * 得到某一行某一列的方格引用。
	 * @param row int, 要引用的方格所在的行
	 * @param col int, 要引用的方格所在的列
	 * @return ErsBox, 在row行col列的方格的引用
	 */
	public Box getBox(int row, int col) {
		if (row < 0 || row > boxes.length - 1
		        || col < 0 || col > boxes[0].length - 1)
			return null;
		return boxes[row][col];
	}
	/**
	 * 消行
	 */
	public void delete(int row) {
		//创建新Box存储新状态，不能直接赋值，赋值只是指向同一个对象
		for (int i = row; i > 0; i--) {
			for (int j = 0; j < 12; j++)
				boxes[i][j] = new Box(boxes[i - 1][j].isColorBox());
		}
		repaint();
	}

	/**
	 * 自动上涨
	 */
	public void addRow() {
		for (int i = 0; i <19 ; i++) {
			for (int j = 0; j < 12; j++) {
				boxes[i][j] = new Box(boxes[i + 1][j].isColorBox());
			}
		}
		for (int i = 0; i < 12; i++) {
			int a = (int) (Math.random() * 2);
			if (a == 1)
				boxes[19][i] = new Box(true);
			else
				boxes[19][i] = new Box(false);
		}
		repaint();
	}
	/**
	 * 画组件。
	 * @param g 图形设备环境
	 */
	public void paint(Graphics g) {
		super.paint(g);
		if (gameOver) {	
			paintGame(g);
			g.setColor(Color.RED);
			g.setFont(new Font("黑体", Font.BOLD, 80));
			g.drawString("GAME", 50, 220);
			g.drawString("OVER", 50, 310);
		} else if(pau==true){
		
			paintGame(g);
			g.setColor(Color.BLUE);
			g.setFont(new Font("华文行楷", Font.BOLD, 40));
			g.drawString("暂停", 100, 200);
			g.drawString("按C继续！", 60, 310);

		}else
			paintGame(g);
	}

	public void paintGame(Graphics g) {
		g.setColor(Color.BLUE);
		g.draw3DRect(0, 0, 298, 498, true);
		g.setColor(frontColor);
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++) {				
				if (boxes[i][j].isColorBox() == true) {					
					g.setColor(frontColor);// 获取方格颜色
					g.fill3DRect(j * boxWidth, i * boxHeight, boxWidth,
							boxHeight, true);// j，i是相反过来的}
			}
		}}
		
	}
	/**
	 * 游戏结束
	 */
	public boolean setGameOver(boolean go){
		gameOver=go;
		return gameOver;
	}

	/**
	 * 画布重置
	 */
	public void reset() {
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++)
				boxes[i][j].setColor(false);
		}

		repaint();
	}

}
