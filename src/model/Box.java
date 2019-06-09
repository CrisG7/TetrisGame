package model;

import java.awt.Dimension;

/**
 * �����࣬����ɿ�Ļ���Ԫ�أ����Լ�����ɫ����ʾ������
 */
public class Box  {
	private boolean isColor;

	/**
	 * ������Ĺ��캯��
	 * @param isColor �ǲ�����ǰ��ɫ��Ϊ�˷�����ɫ��
	 *      trueǰ��ɫ��false�ñ���ɫ
	 */
	public Box(boolean isColor) {
		this.isColor = isColor;
	}

	/**
	 * �˷����ǲ�����ǰ��ɫ����
	 * @return boolean,true��ǰ��ɫ���֣�false�ñ���ɫ����
	 */
	public boolean isColorBox() {
		return isColor;
	}

	/**
	 * ���÷������ɫ��
	 * @param isColor boolean,true��ǰ��ɫ���֣�false�ñ���ɫ����
	 */
	public void setColor(boolean isColor) {
		this.isColor = isColor;
	}


}