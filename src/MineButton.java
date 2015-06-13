import java.awt.Insets;

import javax.swing.JButton;


public class MineButton extends JButton {
	private int row;
	private int col;
	private boolean cleared=false;
	
	public MineButton(int row,int col){
		this.row=row;
		this.col=col;
		this.setMargin(new Insets(0,0,0,0));//设置按钮边框和标签间的空白
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public boolean isCleared() {
		return cleared;
	}

	public void setCleared(boolean cleared) {
		this.cleared = cleared;
	}
	

}

