import javax.swing.JFrame;

public class GameFrame extends JFrame{

	
	
	GameFrame() {
		
		this.add(new GamePanel());
		this.setTitle("Snake"); // we add a title
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // here we add screen off button.
		this.setResizable(false); // here we resize the screen
		this.pack();
		this.setVisible(true); // if we don't turn on visibility nothing will show up
		this.setLocationRelativeTo(null);
		
		
	}
	
	
	
}
