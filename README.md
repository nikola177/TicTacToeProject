# TicTacToeProject

Tic Tac Toe GUI Application

Application was developed in Java.

About application:

  The application is intended for playing popular Tic Tac Toe game. There are two modes: Multiplayer and Singleplayer. Multiplayer for playing
  versus real person on the same computer. Singleplayer for playing versus computer bot with 2 levels: normal and hard. Bot can win, lose or
  play draw vs man. In Game Class, there are several methods for bot's playing, for example 
  
   wiseMove()
   
   This method returns "best" button. If bot can't win or doesn't need to prevent defeat then this method will be called. Result button provides
   minimal number of bad solutions after bot's move. This method also will choose button (if it's possible) to create 2 bot's buttons in a row
   (one need for win).
	

Singleplayer game flow:

* If number of clicked buttons are greater than 2

  then bot is trying to win with method tryToWin(), if can't win, then bot is trying to prevent defeat with method preventDefeat(). If there is
  no need for preventing defeat then bot is playing with methods: wiseMove() or wisestMove() - this method has higher priority. If last two
  methods returns null button(cant find the result) then bot is playing on random free button with method playToRandomFreeButton().

* If number of clicked buttons are smaller than 3:

     * If player had turn to start round then:
  
       Depends on player's first move, bot plays some corner or middle or random button. Also there is some probability for decision making depends
       on level.
     
     * If bot have turn to start round then:
       Bot plays one random free button with method playToRandomFreeButton()
    

Usefulness of the project:

Developing algorithmic thinking, logic, creativity. 

Runing project:

Just pull the code and run it.

Youtube link preview:

https://www.youtube.com/watch?v=eNyPWYorzNM&feature=youtu.be

Communication:

e-mail: nikolarogovic177@gmail.com

