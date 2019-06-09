package control;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import model.Box;


/**
 * 预显窗口的实现细节类
 */
public class PreView extends JPanel {
	private Color  frontColor = Color.BLUE;
	private Box[][] boxes =
	        new Box[4][4];

	private int style, boxWidth=25, boxHeight=25;
	private boolean isTiled = false;

	/**
	 * 预显窗口类构造函数
	 */
	public PreView() {
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++)
				boxes[i][j] = new Box(false);
		}
		this.setOpaque(false);//设置透明
	}

	/**
	 * 预显窗口类构造函数
	 * @param backColor Color, 窗口的背景色
	 * @param frontColor Color, 窗口的前景色
	 */
	public PreView( Color frontColor) {
		this();
		this.frontColor = frontColor;
	}

	/**
	 * 设置预显窗口的方块样式
	 * @param style int,对应ErsBlock类的STYLES中的28个值
	 */
	public void setStyle(int style) {
		this.style = style;
		repaint();
	}

	/**
	 * 覆盖JComponent类的函数，画组件。
	 * @param g 图形设备环境
	 */
	public void paint(Graphics g) {
		super.paint(g);
		g.draw3DRect(0, 0, 101, 101, true);
		int key = 0x8000;
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++) {
				if ((key & style) != 0) {
					g.setColor(frontColor);
					g.fill3DRect(j * boxWidth, i * boxHeight, boxWidth,
							boxHeight, true);					
				}
				key >>= 1;
			}
		}
	}
	}

