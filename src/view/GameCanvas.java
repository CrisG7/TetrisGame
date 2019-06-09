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
	 * ������Ĺ��캯��
	 * @param rows int, ����������
	 * @param cols int, ����������
	 * ���������������Ż���ӵ�з������Ŀ
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
		setBounds(0, 0, 300, 500);//�������λ������
		setBorder(new EtchedBorder(
		        EtchedBorder.RAISED, Color.white, new Color(148, 145, 140)));
	}

	/**
	 * ������Ĺ��캯��
	 * @param rows ��public GameCanvas(int rows, int cols)ͬ
	 * @param cols ��public GameCanvas(int rows, int cols)ͬ
	 * @param backColor Color, ����ɫ
	 * @param frontColor Color, ǰ��ɫ
	 */
	public GameCanvas(int rows, int cols,
	                  Color backColor, Color frontColor) {
		this(rows, cols);
		this.backColor = backColor;
		this.frontColor = frontColor;
	}

	/**
	 * ������Ϸ����ɫ��
 	 * @param backColor Color, ����ɫ��
	 */
	public void setBackgroundColor(Color backColor) {
		this.backColor = backColor;
	}

	/**
	 * ȡ����Ϸ����ɫ��
 	 * @return Color, ����ɫ��
	 */
	public Color getBackgroundColor() {
		return backColor;
	}

	/**
	 * ������Ϸ����ɫ��
 	 * @param frontColor Color, ����ɫ��
	 */
	public void setBlockColor(Color frontColor) {
		this.frontColor = frontColor;
	}

	/**
	 * ȡ����Ϸ����ɫ��
 	 * @return Color, ����ɫ��
	 */
	public Color getBlockColor() {
		return frontColor;
	}

	/**
	 * ȡ�û����з��������
	 * @return int, ���������
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * ȡ�û����з��������
	 * @return int, ���������
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * ȡ����Ϸ�ɼ�
	 * @return int, ����
	 */
	public int getScore() {
		return score;
	}

	/**
	 * ȡ������һ��������Ļ���
	 * @return int, ��һ��������Ļ���
	 */
	public int getScoreForLevelUpdate() {
		return scoreForLevelUpdate;
	}


	/**
	 * �õ�ĳһ��ĳһ�еķ������á�
	 * @param row int, Ҫ���õķ������ڵ���
	 * @param col int, Ҫ���õķ������ڵ���
	 * @return ErsBox, ��row��col�еķ��������
	 */
	public Box getBox(int row, int col) {
		if (row < 0 || row > boxes.length - 1
		        || col < 0 || col > boxes[0].length - 1)
			return null;
		return boxes[row][col];
	}
	/**
	 * ����
	 */
	public void delete(int row) {
		//������Box�洢��״̬������ֱ�Ӹ�ֵ����ֵֻ��ָ��ͬһ������
		for (int i = row; i > 0; i--) {
			for (int j = 0; j < 12; j++)
				boxes[i][j] = new Box(boxes[i - 1][j].isColorBox());
		}
		repaint();
	}

	/**
	 * �Զ�����
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
	 * �������
	 * @param g ͼ���豸����
	 */
	public void paint(Graphics g) {
		super.paint(g);
		if (gameOver) {	
			paintGame(g);
			g.setColor(Color.RED);
			g.setFont(new Font("����", Font.BOLD, 80));
			g.drawString("GAME", 50, 220);
			g.drawString("OVER", 50, 310);
		} else if(pau==true){
		
			paintGame(g);
			g.setColor(Color.BLUE);
			g.setFont(new Font("�����п�", Font.BOLD, 40));
			g.drawString("��ͣ", 100, 200);
			g.drawString("��C������", 60, 310);

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
					g.setColor(frontColor);// ��ȡ������ɫ
					g.fill3DRect(j * boxWidth, i * boxHeight, boxWidth,
							boxHeight, true);// j��i���෴������}
			}
		}}
		
	}
	/**
	 * ��Ϸ����
	 */
	public boolean setGameOver(boolean go){
		gameOver=go;
		return gameOver;
	}

	/**
	 * ��������
	 */
	public void reset() {
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++)
				boxes[i][j].setColor(false);
		}

		repaint();
	}

}
