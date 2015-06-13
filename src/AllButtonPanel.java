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
	private int row;//行数
	private int col;//列数
	private int mineCount;//地雷数
	private JButton[][] allButtons;//所有按钮
	private int[][] allButtonsMark;//所有按钮的标记
	
	public AllButtonPanel(int row,int col,int mineCount){
		this.row=row;
		this.col=col;
		this.mineCount=mineCount;
		allButtons=new JButton[row][col];
		allButtonsMark=new int[row][col];
		createMine();//创建随机地雷
		createButtons();//初始化所有按钮
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
		while(n<mineCount){//随机生成mineCount个地雷
			int i=(int)(Math.random()*row);
			int j=(int)(Math.random()*col);
			if(allButtonsMark[i][j]!=-1){
				allButtonsMark[i][j]=-1;//
				n++;
			}
		}
		
		for(int i=0;i<allButtonsMark.length;i++){//计算每个位置的周围地雷数
			for(int j=0;j<allButtonsMark[i].length;j++){
				if(allButtonsMark[i][j]!=-1){
					allButtonsMark[i][j]=getSurroundMineCount(allButtonsMark,i,j);
				}
			}
		}
	}
	
	private int getSurroundMineCount(int[][] data,int i,int j){
		int num=0;//统计周围的雷数
		if(i-1>=0&&j-1>=0){//左上方
			num+=(data[i-1][j-1]==-1?1:0);
		}
		if(i-1>=0){//正左边
			num+=(data[i-1][j]==-1?1:0);
		}                                  
		if(i-1>=0&&j+1<data[0].length){//右上方   
			num+=(data[i-1][j+1]==-1?1:0);    
		}
		if(j-1>=0){//正上方
			num+=(data[i][j-1]==-1?1:0);
		}
		if(j+1<data[0].length){//正右边
			num+=(data[i][j+1]==-1?1:0);
		}
		if(i+1<data.length&&j-1>=0){//左下方
			num+=(data[i+1][j-1]==-1?1:0);
		}
		if(i+1<data.length){//正下方
			num+=(data[i+1][j]==-1?1:0);
		}
		if(i+1<data.length&&j+1<data[0].length){//右下方
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
					public void mouseClicked(MouseEvent e) {//点击事件
						JButton b=(JButton)e.getSource();
						if(e.getButton()==MouseEvent.BUTTON3){//右键
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
						if(e.getButton()==MouseEvent.BUTTON1){//左键
							if(b.getText().equals("&")){
								lost();//游戏结束
							}
						}
					}
				});
			}
		}
	}
	
	public void lost(){//输了
			JOptionPane.showMessageDialog(this,"                     不好意思,你输了！","游戏失败",JOptionPane.CLOSED_OPTION);
		}
	
	public void win(){//赢了
		   JOptionPane.showMessageDialog(this, "                     恭喜你！你赢了！","游戏胜利!",JOptionPane.CLOSED_OPTION);
	}

	public void actionPerformed(ActionEvent e) {
		MineButton b=(MineButton)e.getSource();
		int rRow=b.getRow();
		int cCol=b.getCol();
		if(allButtonsMark[rRow][cCol]==-1){//如果是地雷
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
		}else{//如果不是雷
			showEmpty(allButtonsMark,rRow,cCol);
		}
	}
	
	//递归,分八个方向排开
	private void showEmpty(int[][] data,int i,int j){
		MineButton b=(MineButton)allButtons[i][j];
		if(b.isCleared()){
			return;
		}
		if(allButtonsMark[i][j]==0){
			b.setBackground(Color.white);
			b.setCleared(true);
			if(i-1>=0&&j-1>=0){//左上方
				showEmpty(data,i-1,j-1);
			}
			if(i-1>=0){//正左方
				showEmpty(data,i-1,j);
			}
			if(i-1>=0&&j+1<data[0].length){//右上方
				showEmpty(data,i-1,j+1);
			}
			if(j-1>=0){//正上方
				showEmpty(data,i,j-1);
			}
			if(j+1<data[0].length){//正右方
				showEmpty(data,i,j+1);
			}
			if(i+1<data.length&&j-1>=0){//左下方
				showEmpty(data,i+1,j-1);
			}
			if(i+1<data.length){//正下方
				showEmpty(data,i+1,j);
			}
			if(i+1<data.length&&j+1<data[0].length){//右下方
				showEmpty(data,i+1,j+1);
			}
		}else if(allButtonsMark[i][j]>0){
			b.setText(allButtonsMark[i][j]+"");
			b.setBackground(Color.white);
			b.setCleared(true);
		}	
	}
}
