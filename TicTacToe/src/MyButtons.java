import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class MyButtons extends JButton{
	
	//1 - active(not clicked) or 0 - not active(clicked)button
	
	private int state;
	
	//which player has this button: 0 for no one,1 for X, 2 for O
	
	private int XOstate;
	
	private int iCoordinate;
	private int jCoordinate;
	
	public MyButtons(int i, int j) {
			//set to not clicked button(active)
			setState(1); 
			//this button doesn't belong to	
			setXOstate(0); 
			setBackground(new Color(0,200,250));
			
			Border lineBorder = new LineBorder(Color.BLACK, 2);
			setBorder(lineBorder);
			setICoordinate(i);
			setJCoordinate(j);
			
			setFont(new Font("Arial", Font.BOLD, 40));
			setForeground(Color.BLACK);
	}
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public void setXOstate(int x) {
		this.XOstate = x;
	}
	public int getXOstate() {
		return XOstate;
	}
	public void setICoordinate(int iCoordinate) {
		this.iCoordinate = iCoordinate;
	}
	public void setJCoordinate(int JCoordinate) {
		this.jCoordinate = JCoordinate;
	}
	
	public int getICoordinate() {
		return iCoordinate;
	}
	public int getJCoordinate() {
		return jCoordinate;
	}
	public boolean isEqual(MyButtons a) {
		
		if(getICoordinate()==a.getICoordinate() && getJCoordinate()==a.getJCoordinate()) return true;
		return false;
	}

}
