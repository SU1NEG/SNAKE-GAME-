import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;



public class GamePanel extends JPanel implements ActionListener{


	int SCREEN_WIDTH = 1300;  // width of screen.
	int SCREEN_HEIGHT = 750;  // height of screen.
	int UNIT_SIZE = 40;       // each unit size on the screen.
	int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);  // how much unit size is on the screen.
	int DELAY = 50;    // we change the delay time manually. (millisecond)
	int x[] = new int[GAME_UNITS];   // how many game units do we have horizontally.
	int y[] = new int[GAME_UNITS];   // how many game units do we have vertically.
	int bodyParts = 6;   // unit size of snake.
	int applesEaten;  // number of apples eaten.
	int appleX;   // the horizontal position of the apple.
	int appleY;   // the vertical position of the apple.
	char direction = 'R';  // the initial starting direction of the snake "right".
	boolean running = false;  //  we check if the game is running. 
	Timer timer;  // delay time.
	Random random; // this will make the apple come out randomly.



	GamePanel() {

		random = new Random();  // We initialize the random object.
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));  // we adjust the size of the screen.
		this.setBackground(new Color(66, 66, 245)); // screen background color "!!!".Those are hexadecimal numbers.
		this.setFocusable(true);  // if we don't do this the screen will go black.
		this.addKeyListener(new MyKeyAdapter());  // we call the arrow buttons with which we play the game.
		startGame();  // game start method.
	}


	public void startGame() {

		newApple(); //  we call new apple method.
		running = true; // we mean the game is running.
		timer = new Timer(DELAY,this); // we initialize the delay time.
		timer.start(); //  we start time.

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		draw(g);  // we call the draw method.

	}

	public void draw(Graphics g) {        //   ???

		if(running) {  //  if the game is running...
			g.setColor(Color.yellow); // we chose our apple is yellow.
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);  //  we choose the size and location of the apple in a random place.

			for(int i = 0; i< bodyParts;i++) {         
				if(i == 0) {  //   for the head of the snake...
					g.setColor(Color.black);           //  his head is black. 
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);  //  we fill in the rectangle we chose for the head.
				}
				else {  //  for the snake's body...
					g.setColor(new Color(0, 0, 0));    // we choose the color.(black)
					//	g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));  //  ​​🤩​🤩​🤩 this is the best part I save it for last.
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);  // we fill in the rectangle of the body.
				}

			}

			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		}
		else {
			gameOver(g); // we call game over method.


		}
	}
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;  // take out an apple in a random place.(vertical)
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE; // take out an apple in a random place.(horizontally) 
	}

	public void move() {            // for moving  
		for(int i = bodyParts;i>0;i--) {
			x[i] = x[i-1]; //  the head of the snake come to the previous body part (horizontal)
			y[i] = y[i-1]; //  the head of the snake come to the previous body part (vertical)
		}

		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;  //  to go up.
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;  //  to go down.
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;  //  to go left.
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;  //  to go right.
			break;
		}
	}	

	public void checkApple() {

		if((x[0] == appleX) && (y[0] == appleY)) {  //   if the head is cross with an apple...
			bodyParts++;  //  increase the body.
			applesEaten++;  // increase the number of apples eaten.
			newApple();  //  add new apple. 
		}


	}


	public void checkCollisions() {

		
		for(int i = bodyParts;i>0;i--) {  
			if((x[0] == x[i]) && (y[0] == y[i])) {  //  stop the game if his head crosses any of his body.
				running = false;
			}
		}
		
		if(x[0] < 0) {   //  check if head touches left border and if it touches stops the game.
			running = false;
		}
		
		if(x[0] > SCREEN_WIDTH) {   //  check if head touches right border and if it touches stops the game.
			running = false;
		}
		
		if(y[0] < 0) {  //  check if head touches top border and if it touches stops the game.
			running = false;
		}
		
		if(y[0] > SCREEN_HEIGHT) {  //  check if head touches bottom border and if it touches stops the game.
			running = false;
		}

		if(!running) {  // if the game is not running it stops the time.
			timer.stop();
		}
	}	

	public void gameOver(Graphics g) { // for the screen after death


		//Score
		g.setColor(Color.red);  // getting red color
		g.setFont( new Font("Ink Free",Font.BOLD, 40)); // font , size , pixels bla bla. 
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75)); // font , size , pixels bla bla.
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);



	}	


	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {   //  if the game is running call these functions.
			move();
			checkApple();
			checkCollisions();
		}
		repaint();

	}

	public class MyKeyAdapter extends KeyAdapter {   // for add keyboard elements

		@Override
		public void keyPressed(KeyEvent e) {

			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {  //   if it doesn't go to the right, when I press the left arrow key, it will go to the left.
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {  //   if it doesn't go to the left, when I press the right arrow key, it will go to the right.
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {  //   if it doesn't go down, when I press the up arrow key, it goes up.
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {  //   if it doesn't go up, when I press the down arrow key, it goes down.
					direction = 'D';
				}
				break;
			}



			switch(e.getKeyCode()) {   // same as above.
			case KeyEvent.VK_A:
				if(direction != 'R') {   // same
					direction = 'L';
				}
				break;
			case KeyEvent.VK_D:
				if(direction != 'L') {   // same
					direction = 'R';
				}
				break;
			case KeyEvent.VK_W:
				if(direction != 'D') {   //  same  
					direction = 'U';
				}
				break;
			case KeyEvent.VK_S:
				if(direction != 'U') {   //  same
					direction = 'D';
				}
				break;
			}

		}

	}

}
