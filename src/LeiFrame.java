import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

	
public class LeiFrame extends JFrame implements ActionListener{

		private JPanel contentPane;
		
		private JLabel mineLabel,timeLabel;//设置剩余地雷、消耗的时间
		public static JLabel remainMine;//剩余地雷数
		private JLabel time;//消耗时间
		private JPanel center;//中间面板
		private int row,col,mine;//行，列，地雷数

		/**
		 * Create the frame.
		 */
		public LeiFrame() {
			JMenuBar menuBar=new JMenuBar();//菜单栏
			JMenu game=new JMenu("游戏(G)");
			JMenu options=new JMenu("选项(O)");
			JMenu help=new JMenu("帮助(H)");
			JMenuItem item;
			game.add(item=new JMenuItem("新游戏(N)"));item.addActionListener(this);
			game.add(item=new JMenuItem("退出游戏(X)"));item.addActionListener(this);
			options.add(item=new JMenuItem("初级(B)"));
			item.addActionListener(this);
			options.add(item=new JMenuItem("中级(I)"));
			item.addActionListener(this);
			options.add(item=new JMenuItem("高级(V)"));
			item.addActionListener(this);
			help.add(item=new JMenuItem("关于(A)"));
			item.addActionListener(this);
			menuBar.add(game);
			menuBar.add(options);
			menuBar.add(help);
			this.setJMenuBar(menuBar);
			this.setTitle("扫雷");
			this.setLocationRelativeTo(null);
			initSouth();
		}
  
	private void initSouth(){//初始化地雷、计时
		JPanel south=new JPanel();
		mineLabel=new JLabel("剩余地雷：");
		timeLabel=new JLabel("消耗时间：");
		remainMine=new JLabel("10");
		time=new JLabel("0");
		south.add(mineLabel);
		south.add(remainMine);
		south.add(timeLabel);
		south.add(time);
		this.row=9;
		this.col=9;
		this.mine=10;
		restart();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(south,BorderLayout.SOUTH);
		new Thread(){//计时线程
			public void run(){
				while(Integer.parseInt(remainMine.getText())>0){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					time.setText((Integer.parseInt(time.getText())+1)+"");
				}
			}
		}.start();
	}


	public void actionPerformed(ActionEvent e) {//事件处理
		if(e.getActionCommand().equals("初级(B)")){
			this.row=9;
			this.col=9;
			this.mine=10;
			restart();
			return;
		}
		if(e.getActionCommand().equals("中级(I)")){
			this.row=16;
			this.col=16;
			this.mine=40;
			restart();
			return;
		}
		if(e.getActionCommand().equals("高级(V)")){
			this.row=16;
			this.col=30;
			this.mine=99;
			restart();
			return;
		}
		if(e.getActionCommand().equals("新游戏(N)")){
			restart();
			return;
		}
		if(e.getActionCommand().equals("退出游戏(X)")){
			int i = JOptionPane.showConfirmDialog(contentPane, "              确定退出？", "退出",JOptionPane.YES_NO_OPTION);
			if(i == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
			return;
		}
		if(e.getActionCommand().equals("关于(A)")){
			JOptionPane.showMessageDialog(contentPane,
				    "扫雷是一款益智游戏。游戏目标：找出空方块并避免触雷。");
			return;
		}
	}

	private void restart(){//重新开始
		if(center!=null){
			this.remove(center);
		}
		center=new AllButtonPanel(row,col,mine);
		this.add(center,BorderLayout.CENTER);
		this.remainMine.setText(mine+"");
		this.time.setText("0");
		this.setSize(col*30,row*30+10);
		this.setResizable(false);
		this.setVisible(true);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LeiFrame frame = new LeiFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
