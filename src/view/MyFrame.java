package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import control.MusicPlayer;
import control.PreView;
import model.Block;
import model.Box;
import util.Constant;

public class MyFrame extends JFrame{
	JPanel jp_pan=new JPanel();
	JPanel jp_pre=new JPanel();
	JPanel jp_ctrl=new JPanel();
	JPanel jp_scor=new JPanel();
	Customize zi;
	JRadioButtonMenuItem jr1=new JRadioButtonMenuItem("����",true);
	JRadioButtonMenuItem jr2=new JRadioButtonMenuItem("�м�");
	JRadioButtonMenuItem jr3=new JRadioButtonMenuItem("�߼�");
	JLabel jt9=new JLabel("�÷֣�0" );
	static JLabel jt10=new JLabel("�ȼ���1" );
	JMenu m1=new JMenu("��Ϸ");
	JMenu m2=new JMenu("����");
	JCheckBox jc1;
	JSlider jsl;
	static ImageIcon  background = new ImageIcon(Constant.backGround1);
	// �ѱ���ͼƬ�ӵ�label
	static JLabel label = new JLabel(background);
	int scor=0;//��ʼ������Ϊ0
	static int rank=0;//��ʼ���ȼ�Ϊ0
	int highC=0;
	boolean upspeed=false;
	boolean isTime=true;
	boolean runstop;
	static boolean isRank=false;
	static boolean changeBack=false;
	public static boolean playing=false;
	static boolean isMusic=true;
	static boolean high=false;
	PreView pv=new PreView();
	JMenuItem ji1=new JMenuItem("����");
	GameCanvas gc=new GameCanvas(20, 12);//����20��12��
	private Block block=new Block();//��ǰ��
	private int newspeed=1000;//Ĭ�ϵ�ǰ�ȼ�Ϊ1
	MusicPlayer mp=new MusicPlayer();
	Timer time=new Timer();
	MyTask mytask;
	int temp=1;

	public MyFrame(String str){
		super(str);
		this.setSize(450, 570);
		Dimension scrSize = 
	               Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((scrSize.width - getSize().width) / 2,
		        (scrSize.height - getSize().height) / 2);
		this.setLayout(null);
		label.setBounds(0, 0, this.getWidth(), this.getHeight());
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		JPanel imagePanel = (JPanel) this.getContentPane();
		imagePanel.setOpaque(false);
		addMenu();

		ji1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
					
			
				if (playing == true) {
					ji1.setText("����");
					if(isMusic==true)
					{mp.playStart();}
					gc.setGameOver(true);
					gc.repaint();
					MyFrame.rank=11-Constant.step;
					MyFrame.jt10.setText("�ȼ���"+MyFrame.rank);
					 runstop=true;
					 block.isAlive=false;
					 block=new Block();
					 mytask.cancel();

					playing = false;
				} else {
					reset();
					if(isMusic==true)
					{mp.playStart();}
					MyFrame.rank=11-Constant.step;
					MyFrame.jt10.setText("�ȼ���"+MyFrame.rank);
					ji1.setText("������Ϸ");
					playing = true;
					mytask=new MyTask();
					time.schedule(mytask, 0, 100);
					Thread thread = new Thread(new play());
					thread.start();

				}
			};
		});
		this.add(gc);		
		addRight();
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(new MyListener());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
public static void setBackGround(){
	label.setIcon(background);
}
	/**
	 * ��ʱ���䣬�ü�����ʽ��ʵ���ٶȵĸı�
	 * 
	 */
	private class MyTask extends TimerTask {

		@Override
		public void run() {
			temp++;
			if (temp % Constant.step == 0) {
				block.newL = block.x;
				block.newH = block.y + 1;
				if (block.pausing == true)
					return;
				if (high == true) {
					block.earse();
					highC++;
					if (highC == 4) {
						gc.addRow();
						highC = 0;
					}
				}
				if (block.isMoveAble(block.newH, block.newL)) {
					block.earse();
					block.y++;
					block.display();
					gc.repaint();

				} else {
					block.isAlive = false;
					gc.repaint();
				} 
				temp = 1;
			}
		
		}
	}

	private class play implements Runnable {
		public void run() {
			int col = (int) (Math.random() * (gc.getCols() - 3));
			int style = Constant.STYLES[(int) (Math.random() * Block.get_addl())][(int) (Math
					.random() * 4)];
			while (playing) {
				if (block != null) {
					if (block.isAlive) {
						try {
							Thread.currentThread().sleep(100);
						} catch (InterruptedException ie) {
							ie.printStackTrace();
						}
						continue;
					}
				}
				isFullLine();
				if(isGameOver()){
					if(isMusic==true)
					{mp.playGameOver();}
					gc.setGameOver(true);
					gc.repaint();
					ji1.setText("����");
					mytask.cancel();
					playing=false;
					return;
					
				}
				block = new Block(style, -1, col, gc);
				block.jixu();
				gc.repaint();
				block.isAlive = true;
				col = (int) (Math.random() * (gc.getCols() - 3));
				style = Constant.STYLES[(int) (Math.random() * Block.get_addl())][(int) (Math
						.random() * 4)];
				pv.setStyle(style);
			}
		}
		/**
		 * �����ٶ�
		 */
		private void upLevel() {
			if(Constant.step-1<1){
				return;}
			Constant.step=Constant.step-1;	
				rank++;					
				jt10.setText("�ȼ���"+rank);
				
				upspeed=false;
			
		}

		/**
		 * �ж��Ƿ����У�������������з�����
		 */
		private void isFullLine() {
			for (int i = 0; i < 20; i++) {
				int row = 0;
				boolean flag = true;
				for (int j = 0; j < 12; j++) {
					if (!gc.getBox(i, j).isColorBox()) {
						flag = false;
						break;
					}
				}

				if (flag == true) {
					row = i;
					gc.delete(row);
					if(isMusic==true)
					{mp.playEraseSound();}
					addScor();
					if(scor%10==0)
					upspeed=true;
					if(upspeed==true)
						upLevel();
				}
			}

		}

		/**
		 * �÷ֵļ��㷽��
		 */
		private void addScor() {
			scor=scor+10;
			jt9.setText("�÷֣�"+scor);
		}
	
	}
	/**
	 * �ж�����Ƿ��б�ռ��,��Ϸ�Ƿ����
	 */
	private boolean isGameOver() {
		for (int i = 0; i < 12; i++) {
			Box box = gc.getBox(0, i);
			if (box.isColorBox())
				return true;
			
		}return false;
	}

	private void reset() {
		scor=0;
		rank=0;
		jt10.setText("�ȼ���"+rank);
		jt9.setText("�÷֣�"+scor);
		upspeed=false;
		playing=true;
		runstop=false;
		gc.setGameOver(false);
		gc.repaint();
		gc.reset();
	}
	/**
	 * 
	 *�����������������ҡ�
	 */
	private class MyListener extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			int i = e.getKeyCode();
			switch (i) {
			case KeyEvent.VK_UP:
				block.moveUp();
				break;
			case KeyEvent.VK_DOWN:
				block.moveDown();
				break;
			case KeyEvent.VK_LEFT:
				block.moveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				block.moveRight();
				break;
			case KeyEvent.VK_SPACE:
				block.quickDown();
				break;
			case KeyEvent.VK_P:
				block.pause();
				break;
			case KeyEvent.VK_C:
				block.jixu();
				break;

			}
		}
		
	}

			
		/**
		 * �˵���ӷ���
		 */
		private void addMenu() {
		// TODO Auto-generated method stub
		JMenuBar jb1=new JMenuBar();
		m1.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				int i=Block.get_addl();
				if(i==7)
					jr1.setSelected(true);
				else if(i==10)
					jr2.setSelected(true);
				else
					jr3.setSelected(true);
			}
			
		});
		jr1.addActionListener(new MenuActionListener());		
		jr2.addActionListener(new MenuActionListener());		
		jr3.addActionListener(new MenuActionListener());
		ButtonGroup bg=new ButtonGroup();
		bg.add(jr1);
		bg.add(jr2);
		bg.add(jr3);		
		  JMenuItem ji2=new JMenuItem("�Զ���");		  
		ji2.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				zi=new Customize(MyFrame.this,"�Զ���",false,block,gc);
				zi.setVisible(true);
				if(playing==true)
				block.pause();
			}
		});
		JMenuItem ji3=new JMenuItem("�˳�");
		ji3.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(1);//�˳�����
			}
		});
		JMenuItem ji4=new JMenuItem("����");
		ji4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JDialog dl=new Version(MyFrame.this,"�汾��Ϣ",false);

			}
		});
		JMenuItem ji_color=new JMenuItem("������ɫ");
		ji_color.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color newFrontColor = JColorChooser.showDialog(
					    MyFrame.this,"���÷�����ɫ",
	                 gc.getBlockColor());
					if (newFrontColor != null)
						gc.setBlockColor(newFrontColor);
				}
			}
		);
		MyFrame.this.setJMenuBar(jb1);
		jb1.add(m1);
		jb1.add(m2);
		m1.add(ji1);
		m1.add(jr1);
		m1.add(jr2);
		m1.add(jr3);
		m1.add(ji2);
		m1.add(ji_color);
		m1.add(ji3);
		
		m2.add(ji4);	
	}
	/**
	 * �ҽ�������
	 */
	private void addRight() {
		// TODO Auto-generated method stub
		JLabel jt1=new JLabel("��һ��");
		jt1.setFont(new Font("�����п�", Font.BOLD, 18));
		jt1.setOpaque(false);
		jp_pre.setLayout(null);
		jt1.setBounds(5, 0, 80, 20);
		jp_pre.add(jt1);
		pv.setBounds(10, 20, 102, 102);
		jp_pre.add(pv);//���Ԥ������
		jp_pre.setBounds(308, 5, 120, 125);//��������
		jp_pre.setOpaque(false);//���ñ���Ϊ͸��
		MyFrame.this.add(jp_pre);
		JLabel jt2=new JLabel("���ܼ���");
		jt2.setFont(new Font("�����п�", Font.BOLD, 23));
		jt2.setOpaque(false);
		JLabel jt3=new JLabel("�������£���");
		jt3.setFont(new Font("�����п�", Font.BOLD, 15));
		jt3.setOpaque(false);
		JLabel jt4=new JLabel("��ת����");
		jt4.setFont(new Font("�����п�", Font.BOLD, 15));
		jt4.setOpaque(false);
		JLabel jt5=new JLabel("���󣺡�");
		jt5.setFont(new Font("�����п�", Font.BOLD, 15));
		jt5.setOpaque(false);
		JLabel jt6=new JLabel("���ң���");
		jt6.setFont(new Font("�����п�", Font.BOLD, 15));
		jt6.setOpaque(false);
		JLabel jt11=new JLabel("һ�����䣺�ո�");
		jt11.setFont(new Font("�����п�", Font.BOLD, 15));
		jt6.setOpaque(false);
		JLabel jt7=new JLabel("��ͣ��P");
		jt7.setFont(new Font("�����п�", Font.BOLD, 15));
		jt7.setOpaque(false);
		JLabel jt8=new JLabel("������C");
		jt8.setFont(new Font("�����п�", Font.BOLD, 15));
		jt8.setOpaque(false);
		jp_ctrl.setLayout(new GridLayout(8, 1, 0, 0));
		jp_ctrl.add(jt2);
		jp_ctrl.add(jt3);
		jp_ctrl.add(jt4);
		jp_ctrl.add(jt5);
		jp_ctrl.add(jt6);
		jp_ctrl.add(jt11);
		jp_ctrl.add(jt7);
		jp_ctrl.add(jt8);
		jp_ctrl.setOpaque(false);
		jp_ctrl.setBounds(310, 145, 120, 200);
		MyFrame.this.add(jp_ctrl);
		jt9.setOpaque(false);
		jt9.setForeground(Color.BLACK);
		jt10.setOpaque(false);
		jt10.setForeground(Color.BLACK);
		jp_scor.setLayout(new GridLayout(2, 1, 0, 20));
		jp_scor.add(jt9);
		jt9.setFont(new Font("�����п�", Font.BOLD, 26));
		jt10.setFont(new Font("�����п�", Font.BOLD, 26));
		jp_scor.add(jt10);
		jt9.setBackground(Color.LIGHT_GRAY);
		jt10.setBackground(Color.LIGHT_GRAY);
		jp_scor.setOpaque(false);
		jp_scor.setBounds(320, 360, 100, 140);
		MyFrame.this.add(jp_scor);
	}
	
	/**
	 * �˵��ȼ��ļ���
	 *
	 */
	private class MenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem j=((JMenuItem)e.getSource());
			if(j==jr1){
				Block.set_addl(7);
			}
			if(j==jr2){
				Block.set_addl(10);
			}
			if(j==jr3){
				Block.set_addl(13);
			}
				
		}
		
	}
	public static void main(String[] args) {
		new MyFrame("����˹����");
	}
	
	
}
