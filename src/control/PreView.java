package control;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import model.Box;


/**
 * Ԥ�Դ��ڵ�ʵ��ϸ����
 */
public class PreView extends JPanel {
	private Color  frontColor = Color.BLUE;
	private Box[][] boxes =
	        new Box[4][4];

	private int style, boxWidth=25, boxHeight=25;
	private boolean isTiled = false;

	/**
	 * Ԥ�Դ����๹�캯��
	 */
	public PreView() {
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++)
				boxes[i][j] = new Box(false);
		}
		this.setOpaque(false);//����͸��
	}

	/**
	 * Ԥ�Դ����๹�캯��
	 * @param backColor Color, ���ڵı���ɫ
	 * @param frontColor Color, ���ڵ�ǰ��ɫ
	 */
	public PreView( Color frontColor) {
		this();
		this.frontColor = frontColor;
	}

	/**
	 * ����Ԥ�Դ��ڵķ�����ʽ
	 * @param style int,��ӦErsBlock���STYLES�е�28��ֵ
	 */
	public void setStyle(int style) {
		this.style = style;
		repaint();
	}

	/**
	 * ����JComponent��ĺ������������
	 * @param g ͼ���豸����
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

