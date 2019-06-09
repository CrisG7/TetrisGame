package view;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class Version extends JDialog{
	JLabel jl2=new JLabel("作者：CrisG7");
	JLabel jl3=new JLabel("俄罗斯方块");
	JPanel jp=new JPanel();
	public Version(JFrame j,String s,boolean b){
		super(j,s,b);
		this.setBounds(400, 120, 200, 200);
		this.setVisible(true);
		this.setResizable(false);
		jp.setLayout(new GridLayout(3, 1));
			jp.add(jl2);
			jp.add(jl3);
		this.add(jp);
	}
}
