package util;

public class Constant {
	public static final int LEVEL_1=1000;//等级1速度，1秒
	public static final int LEVEL_2=500;
	public static final int LEVEL_3=100;
	public static final String backGround1="image/1.jpg";
	public static final String backGround2="image/2.jpg";
	/**
	 * 分别对应对10种模型的40种状态
	 */
	public final static int[][] STYLES = {// 共28种状态
		{0xf000, 0x8888, 0xf000, 0x8888}, // 长条型的四种状态
		{0x4e00, 0x4640, 0xe400, 0x4c40}, // 'T'型的四种状态
		{0x4620, 0x6c00, 0x4620, 0x6c00}, // 反'Z'型的四种状态
		{0x2640, 0xc600, 0x2640, 0xc600}, // 'Z'型的四种状态
		{0x6220, 0x1700, 0x2230, 0x7400}, // '7'型的四种状态
		{0x6440, 0xe200, 0x44c0, 0x8e00}, // 反'7'型的四种状态
		{0x6600, 0x6600, 0x6600, 0x6600}, // 方块的四种状态
		{0x8c88,0xf200,0x44c4,0x4f00},//增加的中级样式方块3个
		{0xea00,0xc4c0,0xae00,0xc8c0},
		{0x8c00,0xc800,0xc400,0x4c00},
		{0xac00,0xcc40,0x6e00,0x8cc0},//增加的高级样式方块3个
		{0x4e40,0x4e40,0x4e40,0x4e40},
		{0x8480,0xa400,0x4840,0x4a00},
	};
	/**
	 * 方块的样式数目为7
	 */
	public final static int BLOCK_KIND_NUMBER = 7;
	/**
	 * 每一个样式的方块的反转状态种类为4
	 */
	public final static int BLOCK_STATUS_NUMBER = 4;
	public  static int step = 10;//速度计数，10为1秒
}
