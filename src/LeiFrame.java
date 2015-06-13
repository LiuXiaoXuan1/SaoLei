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
		
		private JLabel mineLabel,timeLabel;//����ʣ����ס����ĵ�ʱ��
		public static JLabel remainMine;//ʣ�������
		private JLabel time;//����ʱ��
		private JPanel center;//�м����
		private int row,col,mine;//�У��У�������

		/**
		 * Create the frame.
		 */
		public LeiFrame() {
			JMenuBar menuBar=new JMenuBar();//�˵���
			JMenu game=new JMenu("��Ϸ(G)");
			JMenu options=new JMenu("ѡ��(O)");
			JMenu help=new JMenu("����(H)");
			JMenuItem item;
			game.add(item=new JMenuItem("����Ϸ(N)"));item.addActionListener(this);
			game.add(item=new JMenuItem("�˳���Ϸ(X)"));item.addActionListener(this);
			options.add(item=new JMenuItem("����(B)"));
			item.addActionListener(this);
			options.add(item=new JMenuItem("�м�(I)"));
			item.addActionListener(this);
			options.add(item=new JMenuItem("�߼�(V)"));
			item.addActionListener(this);
			help.add(item=new JMenuItem("����(A)"));
			item.addActionListener(this);
			menuBar.add(game);
			menuBar.add(options);
			menuBar.add(help);
			this.setJMenuBar(menuBar);
			this.setTitle("ɨ��");
			this.setLocationRelativeTo(null);
			initSouth();
		}
  
	private void initSouth(){//��ʼ�����ס���ʱ
		JPanel south=new JPanel();
		mineLabel=new JLabel("ʣ����ף�");
		timeLabel=new JLabel("����ʱ�䣺");
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
		new Thread(){//��ʱ�߳�
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


	public void actionPerformed(ActionEvent e) {//�¼�����
		if(e.getActionCommand().equals("����(B)")){
			this.row=9;
			this.col=9;
			this.mine=10;
			restart();
			return;
		}
		if(e.getActionCommand().equals("�м�(I)")){
			this.row=16;
			this.col=16;
			this.mine=40;
			restart();
			return;
		}
		if(e.getActionCommand().equals("�߼�(V)")){
			this.row=16;
			this.col=30;
			this.mine=99;
			restart();
			return;
		}
		if(e.getActionCommand().equals("����Ϸ(N)")){
			restart();
			return;
		}
		if(e.getActionCommand().equals("�˳���Ϸ(X)")){
			int i = JOptionPane.showConfirmDialog(contentPane, "              ȷ���˳���", "�˳�",JOptionPane.YES_NO_OPTION);
			if(i == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
			return;
		}
		if(e.getActionCommand().equals("����(A)")){
			JOptionPane.showMessageDialog(contentPane,
				    "ɨ����һ��������Ϸ����ϷĿ�꣺�ҳ��շ��鲢���ⴥ�ס�");
			return;
		}
	}

	private void restart(){//���¿�ʼ
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
