import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Game extends JFrame {

	private JPanel contentPane;
	
	
	 /*indicator which represents who has turn for starting round
	  * 1 for X, 2 for O*/
	
	public int globalXOstate;
	
	/*current turn for next move in round.  1 for X; 2 for O
	 * ( it is changing from 1 to 2 and vice versa*/
	
	private int XOstate; 
	
	MyButtons[][] butArray = new MyButtons[3][3];
	
	private int finish;
	
	private JButton btnNewReal;
	private JButton btnNewWithBot;
	
	private int numberOfClickedButtons;
	private JLabel lblPlayer1Wins;
	private JLabel lblPlayer2Wins;
	
	// 1 for bot mode, 0 for no bot mode
	private int botMode;
	JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game frame = new Game();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Game() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 583, 507);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-getSize().height/2);
		
		setFinish(0);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBounds(157, 106, 270, 270);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(3,3));
		
		setNumberOfClickedButtons(0);
		setXOstate(1);// player X starts a game, for every player's or bot's move, it is changing from 1 to 2 and vice versa
		globalXOstate=1; // player X started round (2 player O started..)
		
		JButton btnRestart = new JButton("Restart");
		btnRestart.setBounds(26, 72, 89, 23);
		contentPane.add(btnRestart);
		
		btnRestart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				resetReal();
				setFinish(0);
				setbotMode(0);
				lblPlayer1Wins.setText("0");
				lblPlayer2Wins.setText("0");
				globalXOstate=1;
				setXOstate(1);
				
				int i,j;
				for(i=0; i<3; i++)
					for(j=0; j<3; j++) {
						butArray[i][j].setState(1);
						butArray[i][j].setXOstate(0);
						butArray[i][j].setForeground(Color.BLACK);
						butArray[i][j].setText("");
						
					}
			}
			
		});
		
		btnNewReal = new JButton("Multiplayer");
		btnNewReal.setBounds(157, 72, 118, 23);
		btnNewReal.setEnabled(true);
		
		btnNewReal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				btnNewReal.setEnabled(false);
				btnNewWithBot.setEnabled(false);
				
				setbotMode(0);
				int i,j;
				for(i=0; i<3; i++)
					for(j=0; j<3; j++) {
						butArray[i][j].setState(1);
						butArray[i][j].setXOstate(0);
						butArray[i][j].setForeground(Color.BLACK);
						butArray[i][j].setText("");
						
					}
				setFinish(0);
			}
			
		});
		
		contentPane.add(btnNewReal);

		btnNewWithBot = new JButton("Singleplayer");
		btnNewWithBot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				btnNewReal.setEnabled(false);
				btnNewWithBot.setEnabled(false);
				
				setbotMode(1);
				
				int i,j;
				for(i=0; i<3; i++)
					for(j=0; j<3; j++) {
						butArray[i][j].setState(1);
						butArray[i][j].setXOstate(0);
						butArray[i][j].setForeground(Color.BLACK);
						butArray[i][j].setText("");
						
					}
				setFinish(0);
				
				/*if bot has turn to start round
				then bot plays on random field*/
				
				if(globalXOstate==2) {	
				
					MyButtons mybutton = playToRandomFreeButton();
					
					mybutton.setState(0);
					mybutton.setXOstate(2);
					setNumberOfClickedButtons(1);
					mybutton.setText("O");
					setXOstate(1);
				}
				
			}
		});
		btnNewWithBot.setBounds(309, 72, 118, 23);
		contentPane.add(btnNewWithBot);
		
		JLabel lblPlayer = new JLabel("Player1");
		lblPlayer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPlayer.setBounds(127, 21, 74, 30);
		contentPane.add(lblPlayer);
		
		JLabel lblplayer = new JLabel("Player2");
		lblplayer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblplayer.setBounds(403, 21, 74, 30);
		contentPane.add(lblplayer);
		
		lblPlayer1Wins = new JLabel("0");
		lblPlayer1Wins.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPlayer1Wins.setBounds(189, 22, 56, 29);
		contentPane.add(lblPlayer1Wins);
		
		lblPlayer2Wins = new JLabel("0");
		lblPlayer2Wins.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPlayer2Wins.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlayer2Wins.setBounds(331, 22, 56, 29);
		contentPane.add(lblPlayer2Wins);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"normal", "hard"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(455, 73, 89, 23);
		contentPane.add(comboBox);
		
		JLabel lblLevel = new JLabel("Level:");
		lblLevel.setBounds(480, 48, 46, 14);
		contentPane.add(lblLevel);
		
		for(int i=0; i<3; i++)
			for(int j=0; j<3; j++) {
				butArray[i][j] = new MyButtons(i,j);
				panel.add(butArray[i][j]);
			}
		
		for(int i=0; i<3; i++)
			for(int j=0; j<3; j++) {
				MyButtons helpButton = butArray[i][j];
				helpButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
					if(getbotMode()==1) {
						
						if(helpButton.getState()==1 && getFinish()==0) {
							
							helpButton.setText("X");
							helpButton.setState(0);
							helpButton.setXOstate(1);
							
							setNumberOfClickedButtons(getNumberOfClickedButtons()+1);
							setXOstate(2);
							
							checkWinner(helpButton);
							
							if(getFinish()==0) {
								MyButtons myButton = botPlays(comboBox.getSelectedItem().toString());
								
								myButton.setText("O");
								myButton.setState(0);
								myButton.setXOstate(2);
								
								setNumberOfClickedButtons(getNumberOfClickedButtons()+1);
								setXOstate(1);
								
								checkWinner(myButton);
							}
							
						}

						return;
					}
						
					else if(getbotMode()==0) {
						
							if(helpButton.getState()==1 && getFinish()==0) {
							
							if(getXOstate()==1) {
								
								helpButton.setText("X");
								setXOstate(2);
								helpButton.setState(0);
								setNumberOfClickedButtons(getNumberOfClickedButtons()+1);
								helpButton.setXOstate(1);
								checkWinner(helpButton);
								return;
								
							}
							else if(getXOstate()==2) {
								
								helpButton.setText("O");
								setXOstate(1);
								helpButton.setState(0);
								setNumberOfClickedButtons(getNumberOfClickedButtons()+1);
								helpButton.setXOstate(2);
								checkWinner(helpButton);
								return;
								
							}
						}
					}
				
							
					}
					
				});
			}
	}
	
	public void setXOstate(int x) {
		this.XOstate = x;
	}
	public int getXOstate() {
		return XOstate;
	}
	
	/*Method for checking is there a wining array of 3 buttons
	 *  with same mark(X or O) in a row, where button belongs to it*/
	
	public void checkWinner(MyButtons button) {
		
		int i,j,player;
		
		i = button.getICoordinate();
		j = button.getJCoordinate();
		player = button.getXOstate();
		
		if(i==0 && j==0) {
			
			if(butArray[1][0].getXOstate()==player && butArray[2][0].getXOstate()==player) endGame(i,j,1,0,2,0,player);
			else if(butArray[0][1].getXOstate()==player && butArray[0][2].getXOstate()==player) endGame(i,j,0,1,0,2,player);
			else if(butArray[1][1].getXOstate()==player && butArray[2][2].getXOstate()==player) endGame(i,j,1,1,2,2,player);
			
		}
		
		if(i==0 && j==2) {
			
			if(butArray[0][0].getXOstate()==player && butArray[0][1].getXOstate()==player) endGame(i,j,0,0,0,1,player);
			else if(butArray[1][2].getXOstate()==player && butArray[2][2].getXOstate()==player) endGame(i,j,1,2,2,2,player);
			else if(butArray[1][1].getXOstate()==player && butArray[2][0].getXOstate()==player) endGame(i,j,1,1,2,0,player);
			
		}
		if(i==2 && j==0) {
			
			if(butArray[0][0].getXOstate()==player && butArray[1][0].getXOstate()==player) endGame(i,j,0,0,1,0,player);
			else if(butArray[2][1].getXOstate()==player && butArray[2][2].getXOstate()==player) endGame(i,j,2,1,2,2,player);
			else if(butArray[1][1].getXOstate()==player && butArray[0][2].getXOstate()==player) endGame(i,j,1,1,0,2,player);
			
		}
		if(i==2 && j==2) {
			
			if(butArray[1][2].getXOstate()==player && butArray[0][2].getXOstate()==player) endGame(i,j,1,2,0,2,player);
			else if(butArray[2][1].getXOstate()==player && butArray[2][0].getXOstate()==player) endGame(i,j,2,1,2,0,player);
			else if(butArray[1][1].getXOstate()==player && butArray[0][0].getXOstate()==player) endGame(i,j,1,1,0,0,player);
			
		}
	
		if(i==0 && j==1) {
			
			if(butArray[0][0].getXOstate()==player && butArray[0][2].getXOstate()==player) endGame(i,j,0,0,0,2,player);
			else if(butArray[1][1].getXOstate()==player && butArray[2][1].getXOstate()==player) endGame(i,j,1,1,2,1,player);

		}
		if(i==1 && j==2) {
			
			if(butArray[0][2].getXOstate()==player && butArray[2][2].getXOstate()==player) endGame(i,j,0,2,2,2,player);
			else if(butArray[1][1].getXOstate()==player && butArray[1][0].getXOstate()==player) endGame(i,j,1,1,1,0,player);

		}
		if(i==2 && j==1) {
			
			if(butArray[2][0].getXOstate()==player && butArray[2][2].getXOstate()==player) endGame(i,j,2,0,2,2,player);
			else if(butArray[1][1].getXOstate()==player && butArray[0][1].getXOstate()==player) endGame(i,j,1,1,0,1,player);

		}
		if(i==1 && j==0) {
			
			if(butArray[0][0].getXOstate()==player && butArray[2][0].getXOstate()==player) endGame(i,j,0,0,2,0,player);
			else if(butArray[1][1].getXOstate()==player && butArray[1][2].getXOstate()==player) endGame(i,j,1,1,1,2,player);

		}
		
		if(i==1 && j==1) {
			
			if(butArray[0][1].getXOstate()==player && butArray[2][1].getXOstate()==player) endGame(i,j,0,1,2,1,player);
			else if(butArray[0][2].getXOstate()==player && butArray[2][0].getXOstate()==player) endGame(i,j,0,2,2,0,player);
			else if(butArray[1][0].getXOstate()==player && butArray[1][2].getXOstate()==player) endGame(i,j,1,0,1,2,player);
			else if(butArray[2][2].getXOstate()==player && butArray[0][0].getXOstate()==player) endGame(i,j,2,2,0,0,player);
			
		}
		
		//draw
		if(getNumberOfClickedButtons()==9) {
			 
			 setFinish(1);
			 resetReal();
		}
		
	}
	
	public void setFinish(int finish) {
		this.finish = finish;
	}
	public int getFinish() {
		return finish;
	}
	
	/*Method for ending round, updating result (number of winning rounds),
	 *coloring to red winning buttons, reseting other stuffs.*/
	
	public void endGame(int i, int j, int i2, int j2, int i3, int j3, int player) {
		
		butArray[i][j].setForeground(Color.RED);
		butArray[i2][j2].setForeground(Color.RED);
		butArray[i3][j3].setForeground(Color.RED);
		setFinish(1);
		resetReal();
		
		if(player==1) {
			lblPlayer1Wins.setText(String.valueOf(Integer.valueOf(lblPlayer1Wins.getText())+1));
		}else if(player==2) {
			lblPlayer2Wins.setText(String.valueOf(Integer.valueOf(lblPlayer2Wins.getText())+1));
		}
		
		return;
	}
	
	public void resetReal() {
		btnNewReal.setEnabled(true);
		btnNewWithBot.setEnabled(true);
		setNumberOfClickedButtons(0);
		
		/*at the end of round, player/bot's turn has to be changed*/ 
		
		if(globalXOstate==1) {
			globalXOstate=2;
			setXOstate(2);
		}
		else if(globalXOstate==2) {
			globalXOstate=1;
			setXOstate(1);
		}
		
	}
	public void setNumberOfClickedButtons(int numberOfClickedButtons) {
		this.numberOfClickedButtons = numberOfClickedButtons;
	}
	public int getNumberOfClickedButtons() {
		return numberOfClickedButtons;
	}
	public void setbotMode(int botMode) {
		this.botMode= botMode;
		
	}
	public int getbotMode() {
		return botMode;
	}
	public MyButtons botPlays(String level) {
		MyButtons mybutton = null;
		
		mybutton = playDependsOnLevel(globalXOstate, level);
		return mybutton;

	}
	
	/*This method is used in method playToRandomFreeButton(). Goal of this
	 * method is to pick random number(order number of free button)
	 * in range (1,9-i). Variable i in method playToRandomFreeButton
	 * represents number of clicked buttons in game. */
	
	public int generateRandomNumber(int i) {
	
			Random rand = new Random(); 
			int r = 1 + rand.nextInt(9 - i);
		return r;
	}

	/*This method returns button which is needed for win
	 *(including all possible game's direction for specific button)*/
	
	public MyButtons checkThreeVectors(MyButtons button) {
		
		int i = button.getICoordinate();
		int j = button.getJCoordinate();
		int temporaryState = button.getXOstate();
		
		MyButtons[] resultButtons = new MyButtons[4];
		int numberOfresultButtons=0;
		
		MyButtons[] helpArrayButtons1 = new MyButtons[3];
		MyButtons[] helpArrayButtons2 = new MyButtons[3];
		MyButtons[] helpArrayButtons3 = new MyButtons[3];
		MyButtons[] helpArrayButtons4 = new MyButtons[3];
		
		helpArrayButtons1[0] = butArray[0][j];
		helpArrayButtons1[1] = butArray[1][j];
		helpArrayButtons1[2] = butArray[2][j];
		
		helpArrayButtons2[0] = butArray[i][0];
		helpArrayButtons2[1] = butArray[i][1];
		helpArrayButtons2[2] = butArray[i][2];
		
		helpArrayButtons3[0] = butArray[0][0];
		helpArrayButtons3[1] = butArray[1][1];
		helpArrayButtons3[2] = butArray[2][2];
		
		helpArrayButtons4[0] = butArray[0][2];
		helpArrayButtons4[1] = butArray[1][1];
		helpArrayButtons4[2] = butArray[2][0];
		
		if(isPossibleToMakeWinArray(helpArrayButtons1, temporaryState)==1) 
			for(int ii=0; ii<3; ii++) if(helpArrayButtons1[ii].getXOstate()==0)
				resultButtons[numberOfresultButtons++] = helpArrayButtons1[ii];
				
		if(isPossibleToMakeWinArray(helpArrayButtons2, temporaryState)==1) 
			for(int ii=0; ii<3; ii++) if(helpArrayButtons2[ii].getXOstate()==0) 
				resultButtons[numberOfresultButtons++] = helpArrayButtons2[ii];
			
		if(i==1 && j==1) {
			
			if(isPossibleToMakeWinArray(helpArrayButtons3, temporaryState)==1) 
				for(int ii=0; ii<3; ii++) if(helpArrayButtons3[ii].getXOstate()==0) 
					resultButtons[numberOfresultButtons++] = helpArrayButtons3[ii];
				
			if(isPossibleToMakeWinArray(helpArrayButtons4, temporaryState)==1) 
				for(int ii=0; ii<3; ii++) if(helpArrayButtons4[ii].getXOstate()==0)
					resultButtons[numberOfresultButtons++] = helpArrayButtons4[ii];
		
		}
		else if((i==0 && j==0) || (i==2 && j==2)) {
			
			if(isPossibleToMakeWinArray(helpArrayButtons3, temporaryState)==1) 
				for(int ii=0; ii<3; ii++) if(helpArrayButtons3[ii].getXOstate()==0) 
					resultButtons[numberOfresultButtons++] = helpArrayButtons3[ii];
				
		}
		else if((i==0 && j==2) || (i==2 && j==0)) {
			
			if(isPossibleToMakeWinArray(helpArrayButtons4, temporaryState)==1) 
				for(int ii=0; ii<3; ii++) if(helpArrayButtons4[ii].getXOstate()==0) 
					resultButtons[numberOfresultButtons++] = helpArrayButtons4[ii];
		}
		
		if(numberOfresultButtons==0) return null;
		
		Random random = new Random();
		int index = random.nextInt(numberOfresultButtons);
		
		return resultButtons[index];
	}
	
	/*This method is checking for current array of 3 buttons
	 *is it possible to make win array (+1 button for win)?*/
	
	public int isPossibleToMakeWinArray(MyButtons[] array,int XOstate) {
		int count = 0;
		int freeButtons = 0;
		for(int i=0; i<3; i++) {
			if(array[i].getXOstate()==XOstate) count++;
			if(array[i].getXOstate()==0) freeButtons++;
		}
		
		if(count==2 && freeButtons==1) return 1;
		
		return 0;
	}
	
	/*This method for current bot's button returns the "best" button
	with minimal number of bad scenarios stored in numberOfBadScenario[0]*/
	
	public MyButtons bestSolution(MyButtons button, int[] numberOfBadScenario) {
		
		MyButtons[] array1 = new MyButtons[3];
		MyButtons[] array2 = new MyButtons[3]; 
		MyButtons[] array3 = new MyButtons[3]; 
		MyButtons[] array4 = new MyButtons[3];
		
		int i = button.getICoordinate();
		int j = button.getJCoordinate();
		
		array1[0] = butArray[i][0];
		array1[1] = butArray[i][1];
		array1[2] = butArray[i][2];
		
		array2[0] = butArray[0][j];
		array2[1] = butArray[1][j];
		array2[2] = butArray[2][j];
		
		
		array3[0] = butArray[0][0];
		array3[1] = butArray[1][1];
		array3[2] = butArray[2][2];
		
		
		array4[0] = butArray[0][2];
		array4[1] = butArray[1][1];
		array4[2] = butArray[2][0];
		
	
		MyButtons[] arrayForDeterminateBestButton = new MyButtons[20];
		for(int ii=0; ii<20; ii++) arrayForDeterminateBestButton[ii]=null;
		
		int[] arrayNumbesrOfBadScenariosForEachButton = new int[20];
		
		
		MyButtons[] arrayOfPotentionalResultButtons1 = new MyButtons[2];
		arrayOfPotentionalResultButtons1[0]=null;
		arrayOfPotentionalResultButtons1[1]=null;
		
		int[] arrayOfNumberOfBadScenario1 = new int[2];
		
		MyButtons[] arrayOfPotentionalResultButtons2 = new MyButtons[2];
		arrayOfPotentionalResultButtons2[0]=null;
		arrayOfPotentionalResultButtons2[1]=null;
		
		int[] arrayOfNumberOfBadScenario2 = new int[2];
		
		MyButtons[] arrayOfPotentionalResultButtons3 = new MyButtons[2];
		arrayOfPotentionalResultButtons3[0]=null;
		arrayOfPotentionalResultButtons3[1]=null;
		
		int[] arrayOfNumberOfBadScenario3 = new int[2];
		
		MyButtons[] arrayOfPotentionalResultButtons4 = new MyButtons[2];
		arrayOfPotentionalResultButtons4[0]=null;
		arrayOfPotentionalResultButtons4[1]=null;
		
		int[] arrayOfNumberOfBadScenario4 = new int[2];
		
		int k=0;
		
		//without diagonals
		
		if((i+j)%2==1) { 
			
			passingThroughSingleArrayOfButtons(button, array1, arrayOfPotentionalResultButtons1,arrayOfNumberOfBadScenario1);
			passingThroughSingleArrayOfButtons(button, array2, arrayOfPotentionalResultButtons2,arrayOfNumberOfBadScenario2);
			
		}
		
		//with diagonals but no middle element
		
		else if((i+j)%2==0 && (i!=1 && j!=1)) {
			
			if((i==0 && j==0) || (i==2 && j==2)) {
				passingThroughSingleArrayOfButtons(button, array1, arrayOfPotentionalResultButtons1,arrayOfNumberOfBadScenario1);
				passingThroughSingleArrayOfButtons(button, array2, arrayOfPotentionalResultButtons2,arrayOfNumberOfBadScenario2);
				passingThroughSingleArrayOfButtons(button, array3, arrayOfPotentionalResultButtons3,arrayOfNumberOfBadScenario3);	
			}
			
			else {
				passingThroughSingleArrayOfButtons(button, array1, arrayOfPotentionalResultButtons1,arrayOfNumberOfBadScenario1);
				passingThroughSingleArrayOfButtons(button, array2, arrayOfPotentionalResultButtons2,arrayOfNumberOfBadScenario2);
				passingThroughSingleArrayOfButtons(button, array4, arrayOfPotentionalResultButtons4,arrayOfNumberOfBadScenario4);
				
			}
	
		}
		//with diagonals for middle element
		
		else if(i==1 && j==1) { 
			
			passingThroughSingleArrayOfButtons(button, array1, arrayOfPotentionalResultButtons1,arrayOfNumberOfBadScenario1);
			passingThroughSingleArrayOfButtons(button, array2, arrayOfPotentionalResultButtons2,arrayOfNumberOfBadScenario2);
			passingThroughSingleArrayOfButtons(button, array3, arrayOfPotentionalResultButtons3,arrayOfNumberOfBadScenario3);
			passingThroughSingleArrayOfButtons(button, array4, arrayOfPotentionalResultButtons4,arrayOfNumberOfBadScenario4);
			
		}
		
		for(int ii=0; ii<2; ii++) {
			
			if(arrayOfPotentionalResultButtons1[ii]!=null) {
				arrayForDeterminateBestButton[k] = arrayOfPotentionalResultButtons1[ii];
				arrayNumbesrOfBadScenariosForEachButton[k] = arrayOfNumberOfBadScenario1[ii];
				k++;
				
			}
			if(arrayOfPotentionalResultButtons2[ii]!=null){
				arrayForDeterminateBestButton[k] = arrayOfPotentionalResultButtons2[ii];
				arrayNumbesrOfBadScenariosForEachButton[k] = arrayOfNumberOfBadScenario2[ii];
				k++;
				
			}
			if(arrayOfPotentionalResultButtons3[ii]!=null){
				arrayForDeterminateBestButton[k] = arrayOfPotentionalResultButtons3[ii];
				arrayNumbesrOfBadScenariosForEachButton[k] = arrayOfNumberOfBadScenario3[ii];
				k++;
				
			}
			if(arrayOfPotentionalResultButtons4[ii]!=null){
				arrayForDeterminateBestButton[k] = arrayOfPotentionalResultButtons4[ii];
				arrayNumbesrOfBadScenariosForEachButton[k] = arrayOfNumberOfBadScenario4[ii];
				k++;
				
			}
			
		}
		
		if(k==0) return null;
		if(k==1) return arrayForDeterminateBestButton[0];
		
		/*If there are more than one button with minimal number of bad scenario
		 * then it is better to return one random button of them. If we don't
		 * do that, then user of application can see almost same behavior
		 * of computer bot. This random picking button makes application
		 * "real and alive".*/
		
		int min = arrayNumbesrOfBadScenariosForEachButton[0];
		int count=0;
		int c=0;
		
		for(int p=1; p<k; p++) if(arrayNumbesrOfBadScenariosForEachButton[p]<min) min = arrayNumbesrOfBadScenariosForEachButton[p];
		
		for(int p=0; p<k; p++) if(arrayNumbesrOfBadScenariosForEachButton[p]==min) count++;
		
		MyButtons[] pickOneRandomFromBestButtons = new MyButtons[count];
		
		for(int p=0; p<k; p++) 
			if(arrayNumbesrOfBadScenariosForEachButton[p]==min) pickOneRandomFromBestButtons[c++]=arrayForDeterminateBestButton[p];
		
		Random random = new Random();
		int x = random.nextInt(count);
		
		return pickOneRandomFromBestButtons[x];
		
	}
	
	/*This method will create array arrayOfPotentionalResultButtons1 with 2 buttons and array arrayOfNumberOfBadScenario1
	 * with 2 integers IF it is possible for arrayOfButtons[] which has element button. Element(button) 
	 * arrayOfPotentionalResultButtons1[0] has number of bad scenarios arrayOfNumberOfBadScenario1[0],
	 * arrayOfPotentionalResultButtons1[1] has number of bad scenarios arrayOfNumberOfBadScenario1[1].*/
	
	public void passingThroughSingleArrayOfButtons(MyButtons button, MyButtons[] arrayOfButtons, MyButtons[] arrayOfPotentionalResultButtons1,int[] arrayOfNumberOfBadScenario1) {
		
		MyButtons potentialResultButton = null;
		MyButtons helpButtonForPlayersPredict = null;
		
		int numberOfBadSolutions1=0;
		int countForArray1=0;
		
		int i = button.getICoordinate();
		int j = button.getJCoordinate();
		
		if(isClear(arrayOfButtons)==1) {
			
		/*going through "empty" array of 3 buttons(one button is clicked, 2 are not)*/
			
			for(int k=0; k<3; k++) {
				
				if(arrayOfButtons[k].getICoordinate()!=i || arrayOfButtons[k].getJCoordinate()!=j) {
					
					//Potential bot's result button
					
					potentialResultButton = arrayOfButtons[k];
					potentialResultButton.setXOstate(2);
					
					//this loop will always executes once
					
					for(int kk=0; kk<3; kk++) { 
						
					/*Looking for the rest empty button - player will probably
					 *plays on it(because player needs to prevent his defeat).*/
						
						if(arrayOfButtons[kk].getXOstate()==0) { 
							
							helpButtonForPlayersPredict = arrayOfButtons[kk];
							
							//Player plays on that button
							
							helpButtonForPlayersPredict.setXOstate(1);
							
							/*Passing through all player's buttons and counting arrays with property:
							 * one element is missing for win*/
							
							for(int p=0; p<3; p++)
								for(int m=0; m<3; m++) {
									if(butArray[p][m].getXOstate()==1) {
										if(checkThreeVectors(butArray[p][m])!=null) {
											numberOfBadSolutions1++;
										}
										
									}
								}
							//it counts always one more.
							
							if(numberOfBadSolutions1>1) numberOfBadSolutions1 = numberOfBadSolutions1 -1;
						}
						
					}
					
					arrayOfPotentionalResultButtons1[countForArray1] = potentialResultButton;
					arrayOfNumberOfBadScenario1[countForArray1] = numberOfBadSolutions1;
					
					countForArray1++;
		
					//reseting XOstates and numbers of bad solutions
					
					potentialResultButton.setXOstate(0);
					helpButtonForPlayersPredict.setXOstate(0);
					numberOfBadSolutions1=0;
				}
			}
		}
	}
	
	/*This method returns 1 if in current array are two non clicked buttons
	otherwise it returns 0*/
	
	public int isClear(MyButtons[] array) {
		int br = 0;
		for(int i=0; i<3; i++) if(array[i].getState()==1) br++;
		
		if(br==2) return 1;
		return 0;
	}
	
	/*Method for bot's win*/
	
	public MyButtons tryToWin(int XOstateOfPlayer, String level) {
		
		Random random = new Random();
		int indicator = 1 + random.nextInt(100);
		
		MyButtons mybutton = null;
		for(int i=0; i<3; i++)	
			for(int j=0; j<3; j++) {
				
				if(level.equals("normal")) {  //with probability ~70+% bot will win
						
						if(butArray[i][j].getXOstate()==XOstateOfPlayer) {
						
						mybutton = checkThreeVectors(butArray[i][j]);
						if(mybutton!=null && indicator<71) return mybutton;
						else if(mybutton!=null && indicator>=71) return null;
					}
						
				}else {//hard level always win
					
					if(butArray[i][j].getXOstate()==XOstateOfPlayer) {
						
						mybutton = checkThreeVectors(butArray[i][j]);
						if(mybutton!=null) return mybutton;
					}
				}

			}
		return mybutton;
	}
	
	public MyButtons preventDefeat(int XOstateOfPlayer, String level) {
		
		Random random = new Random();
		int indicator = 1 + random.nextInt(100);
		
		MyButtons mybutton = null;
		
		for(int i=0; i<3; i++)
			for(int j=0; j<3; j++) {
				if(level.equals("normal")) {
					if(butArray[i][j].getXOstate()==XOstateOfPlayer) {
						
						mybutton = checkThreeVectors(butArray[i][j]);
						if(mybutton!=null && indicator<96) return mybutton;
						else if(mybutton!=null && indicator>=96) return null;
					}
				}else {
					if(butArray[i][j].getXOstate()==XOstateOfPlayer) {
						
						mybutton = checkThreeVectors(butArray[i][j]);
						if(mybutton!=null) return mybutton;
					}
				}
				
			}
		return mybutton;
	}

	/*This method returns "best" button. If bot can't win or doesn't
	 *need to prevent defeat then this method will be called.
	 Result button provides minimal number of bad solutions after
	 bot's move. This method if it's possible also will choose
	 button to create 2 bot's buttons in a row-one need for win
	 -(if it's possible)*/
	
	public MyButtons wiseMove(int XOstateOfPlayer) {
		MyButtons mybutton = null;
		MyButtons[] allBotsButtons = new MyButtons[7];
		
		for(int pp=0; pp<7; pp++) allBotsButtons[pp] = null;
		
		int[] allNumberOfBadScenarioForEachButton = new int[7];
		int k = 0;
		
		int[] twoIntElementsArray = new int[2];
		
		/*Passing through all bot's buttons and choosing best button
		 * for return (with bestSolution method) and stores number
		 * of bad scenarios in twoIntElementsArray[0]*/
		
		for(int i=0; i<3; i++)
			for(int j=0; j<3; j++) {
				
					if(butArray[i][j].getXOstate()==XOstateOfPlayer) {
					
						mybutton = bestSolution(butArray[i][j], twoIntElementsArray);
						
						if(mybutton!=null) {
							allBotsButtons[k] = mybutton;
							allNumberOfBadScenarioForEachButton[k] = twoIntElementsArray[0];
							
							k++;
							mybutton=null;
						}
				}
					twoIntElementsArray = new int[2];
			}

		int min = allNumberOfBadScenarioForEachButton[0];

		mybutton = allBotsButtons[0];
		
		/*choosing the best(with minimal number of bad scenarios) button
		through all buttons in array allBotsButtons*/
		
		for(int i=1; i<k; i++) {   

			if(allBotsButtons[i]!=null) {
				
				if(allNumberOfBadScenarioForEachButton[i]<min) {
					
					min = allNumberOfBadScenarioForEachButton[i];
					
					mybutton = allBotsButtons[i];
				}
			}
		}
		
		return mybutton;
	}
	
	/*This method is called in playDependsOnOrder() method after wiseMove().
	 * It will update result wiseMove() if it's possible. Method is looking
	 * for button so that there are 2 bot's arrays with property:
	 * one element is missing for win*/
	
	public MyButtons wisestMove(int XOstateOfPlayer) {
	
			MyButtons[] A = new MyButtons[10];
			int k = 0;
			for(int i=0; i<3; i++)
				for(int j=0; j<3; j++) {
					MyButtons helpButton = butArray[i][j];
					
					if(helpButton.getState()==1) {
						helpButton.setXOstate(XOstateOfPlayer);
						
						if(checkThreeVectors(helpButton)!=null) {
							A[k] = checkThreeVectors(helpButton);
							k++;
						}
						helpButton.setXOstate(0);
					}
				}
	
			for(int i=0; i<k-1; i++)
				for(int j=i+1; j<k; j++) 
					
					if(A[i].isEqual(A[j])) {
					
						return A[i];
				}
		return null;
	}
	
	/*This method return random not clicked button*/
	
	public MyButtons playToRandomFreeButton() {

			int randomNumber = generateRandomNumber(getNumberOfClickedButtons());
			int counter = 0;
			
			for(int i=0; i<3; i++)
				for(int j=0; j<3; j++) {
					if(butArray[i][j].getState()==1) counter++;
					
						if(counter==randomNumber)
							
							return butArray[i][j];
	
				}
		
		return null;
	}
	
	public MyButtons playDependsOnLevel(int globalXOstate, String level) {
		
		/*This is case when player had turn to start round*/
		
		if(globalXOstate==1 && getNumberOfClickedButtons()<3) {
			
			//if player has clicked on middle button(buttArray[1][1]) then bot "should" plays on some corner button.
			
			if(butArray[1][1].getState()==0) {
				
				MyButtons[] corners = new MyButtons[4];
				corners[0] = butArray[0][0];
				corners[1] = butArray[0][2];
				corners[2] = butArray[2][0];
				corners[3] = butArray[2][2];
				
				Random rand = new Random(); 
				
				//x presents random index of array corners
				
				int x = rand.nextInt(4); 
				
				/*Here with probability 50+% or 80+%(depends on the level)
				bot will play on some corner buttons*/
				
				if(level.equals("normal")) {
					
					if(1 + rand.nextInt(10)<6) return corners[x];
					
				}else {
					if(1 + rand.nextInt(10)<9) return corners[x];
				}
				
				/*if expected event has not happend, after this there
				 *  will be again probability of 50% for expected event
				 */
			}
			
			/*if player plays on some corner button, then it's "good"
			 *that bot plays on middle button (buttonArray[1][1])*/
			
			if(butArray[0][0].getState()==0 || butArray[0][2].getState()==0 || butArray[2][0].getState()==0 || butArray[2][2].getState()==0)
			{
				
				Random rand = new Random(); 
				
				//again some probability
				
				if(level.equals("normal")) {
					if(1 + rand.nextInt(10)<7) return butArray[1][1];
				}
				else{
						if(1 + rand.nextInt(10)<9) return butArray[1][1];
				}
			}
			
			//if expected events has not happened, then bot plays on some random not clicked button

			return playToRandomFreeButton();
		}
		
		//This is case when number of clicked buttons is greater than 2
		
		MyButtons mybutton = null;

		//Bot is trying to win if it is possible
		
		mybutton = tryToWin(2,level);
		if(mybutton!=null) return mybutton;
		
		//Bot is trying to prevent defeat if it is possible
		
		mybutton = preventDefeat(1, level);
		if(mybutton!=null) return mybutton;

		//Bot is trying to play wise
		
		mybutton = wiseMove(2);
		
		//Bot is trying to play more wise
		
		MyButtons helpmybutton = wisestMove(2);
		
		if(helpmybutton!=null) return helpmybutton;
		
		if(mybutton!=null) return mybutton;
	
		/*If there is no solution, then bot is going to play on some random non clicked button
		because round must be finished.*/
		
		mybutton = playToRandomFreeButton();
		
		return mybutton;
	}

}
