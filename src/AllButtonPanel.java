import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class AllButtonPanel extends JPanel implements ActionListener{
	private int row;//����
	private int col;//����
	private int mineCount;//������
	private JButton[][] allButtons;//���а�ť
	private int[][] allButtonsMark;//���а�ť�ı��
	
	public AllButtonPanel(int row,int col,int mineCount){
		this.row=row;
		this.col=col;
		this.mineCount=mineCount;
		allButtons=new JButton[row][col];
		allButtonsMark=new int[row][col];
		createMine();//�����������
		createButtons();//��ʼ�����а�ť
		init();
	}
	
	private void init(){
		this.setLayout(new GridLayout(row,col));
		for(int i=0;i<allButtons.length;i++){
			for(int j=0;j<allButtons[i].length;j++){
				this.add(allButtons[i][j]);
			}
		}
	}
	
	private void createMine(){
		int n=0;
		while(n<mineCount){//�������mineCount������
			int i=(int)(Math.random()*row);
			int j=(int)(Math.random()*col);
			if(allButtonsMark[i][j]!=-1){
				allButtonsMark[i][j]=-1;//
				n++;
			}
		}
		
		for(int i=0;i<allButtonsMark.length;i++){//����ÿ��λ�õ���Χ������
			for(int j=0;j<allButtonsMark[i].length;j++){
				if(allButtonsMark[i][j]!=-1){
					allButtonsMark[i][j]=getSurroundMineCount(allButtonsMark,i,j);
				}
			}
		}
	}
	
	private int getSurroundMineCount(int[][] data,int i,int j){
		int num=0;//ͳ����Χ������
		if(i-1>=0&&j-1>=0){//���Ϸ�
			num+=(data[i-1][j-1]==-1?1:0);
		}
		if(i-1>=0){//�����
			num+=(data[i-1][j]==-1?1:0);
		}                                  
		if(i-1>=0&&j+1<data[0].length){//���Ϸ�   
			num+=(data[i-1][j+1]==-1?1:0);    
		}
		if(j-1>=0){//���Ϸ�
			num+=(data[i][j-1]==-1?1:0);
		}
		if(j+1<data[0].length){//���ұ�
			num+=(data[i][j+1]==-1?1:0);
		}
		if(i+1<data.length&&j-1>=0){//���·�
			num+=(data[i+1][j-1]==-1?1:0);
		}
		if(i+1<data.length){//���·�
			num+=(data[i+1][j]==-1?1:0);
		}
		if(i+1<data.length&&j+1<data[0].length){//���·�
			num+=(data[i+1][j+1]==-1?1:0);
		}
		return num;	
	}
	
	private void createButtons(){
		for(int i=0;i<allButtons.length;i++){
			for(int j=0;j<allButtons[i].length;j++){
				
				allButtons[i][j]=new MineButton(i,j);
				allButtons[i][j].setSize(6,6);
				allButtons[i][j].addActionListener(this);
				allButtons[i][j].addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent e) {//����¼�
						JButton b=(JButton)e.getSource();
						if(e.getButton()==MouseEvent.BUTTON3){//�Ҽ�
							int remain=Integer.parseInt(LeiFrame.remainMine.getText());
							if(b.getText().equals("")){
								remain--;
								LeiFrame.remainMine.setText(remain+"");
								b.setText("&");
							}else if(b.getText().equals("&")){
								remain++;
								LeiFrame.remainMine.setText(remain+"");
								b.setText("");
							}
							if(remain<=0){
								win();
							}
						}
						if(e.getButton()==MouseEvent.BUTTON1){//���
							if(b.getText().equals("&")){
								lost();//��Ϸ����
							}
						}
					}
				});
			}
		}
	}
	
	public void lost(){//����
			JOptionPane.showMessageDialog(this,"                     ������˼,�����ˣ�","��Ϸʧ��",JOptionPane.CLOSED_OPTION);
		}
	
	public void win(){//Ӯ��
		   JOptionPane.showMessageDialog(this, "                     ��ϲ�㣡��Ӯ�ˣ�","��Ϸʤ��!",JOptionPane.CLOSED_OPTION);
	}

	public void actionPerformed(ActionEvent e) {
		MineButton b=(MineButton)e.getSource();
		int rRow=b.getRow();
		int cCol=b.getCol();
		if(allButtonsMark[rRow][cCol]==-1){//����ǵ���
			for(int i=0;i<allButtons.length;i++){
				for(int j=0;j<allButtons[i].length;j++){
					if(allButtonsMark[i][j]==-1){
						allButtons[i][j].setText("&");
					}else if(allButtonsMark[i][j]==0){
						allButtons[i][j].setText("");
						allButtons[i][j].setBackground(Color.white);
					}else{
						allButtons[i][j].setText(allButtonsMark[i][j]+"");
						allButtons[i][j].setBackground(Color.white);
					}
				}
			}
		}else{//���������
			showEmpty(allButtonsMark,rRow,cCol);
		}
	}
	
	//�ݹ�,�ְ˸������ſ�
	private void showEmpty(int[][] data,int i,int j){
		MineButton b=(MineButton)allButtons[i][j];
		if(b.isCleared()){
			return;
		}
		if(allButtonsMark[i][j]==0){
			b.setBackground(Color.white);
			b.setCleared(true);
			if(i-1>=0&&j-1>=0){//���Ϸ�
				showEmpty(data,i-1,j-1);
			}
			if(i-1>=0){//����
				showEmpty(data,i-1,j);
			}
			if(i-1>=0&&j+1<data[0].length){//���Ϸ�
				showEmpty(data,i-1,j+1);
			}
			if(j-1>=0){//���Ϸ�
				showEmpty(data,i,j-1);
			}
			if(j+1<data[0].length){//���ҷ�
				showEmpty(data,i,j+1);
			}
			if(i+1<data.length&&j-1>=0){//���·�
				showEmpty(data,i+1,j-1);
			}
			if(i+1<data.length){//���·�
				showEmpty(data,i+1,j);
			}
			if(i+1<data.length&&j+1<data[0].length){//���·�
				showEmpty(data,i+1,j+1);
			}
		}else if(allButtonsMark[i][j]>0){
			b.setText(allButtonsMark[i][j]+"");
			b.setBackground(Color.white);
			b.setCleared(true);
		}	
	}
}
