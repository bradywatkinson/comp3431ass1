package ass1;

import java.util.Date;
import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyCoolGameObject  extends GameObject implements KeyListener {

	private BoardObject board;

	/**
	 * Creates a new MyCoolGameObject with random attributes
	 */
	public MyCoolGameObject() {
		super(GameObject.ROOT);

		makeRandomBoard();
	}

	/**
	 * Creates a new MyCoolGameObject with predefined attributes
	 * @param parent
	 * @param x1 x coord of red ball
	 * @param y1 y coord of red ball
	 * @param a1 starting angle of red ball
	 * @param d1 starting diameter of red ball
	 * @param x2 x coord of blue ball
	 * @param y2 y coord of blue ball
	 * @param a2 starting angle of blue ball
	 * @param d2 starting diameter of blue ball
	 */
	public MyCoolGameObject(GameObject parent, double x1, double y1, double a1, double d1,
			double x2, double y2, double a2, double d2) {
		super(parent);

		double [] points = new double[] {-1.0,-0.7, 1.0,-0.7, 1,0.7, -1,0.7}; 
		board = new BoardObject(this, points, ColourLibrary.white(), ColourLibrary.green());

		BouncingBall ball1 = new BouncingBall(board, a1, d1, ColourLibrary.red(), ColourLibrary.black());
		ball1.setPosition(x1,y1);

		BouncingBall ball2 = new BouncingBall(board, a1, d1, ColourLibrary.blue(), ColourLibrary.black());
		ball2.setPosition(x1,y1);

		board.addBall(ball1);
		board.addBall(ball2);
	}

	/**
	 * Makes a random board and assigns it as this's child
	 */
	private void makeRandomBoard () {
		// initialise the board
		double [] points = new double[] {-1.0,-0.7, 1.0,-0.7, 1,0.7, -1,0.7}; 
		board = new BoardObject(this, points, ColourLibrary.white(), ColourLibrary.green());

		// initialize two Bouncing Balls
		// 	with random starting angle -175 < angle < 175
		// and random diameter of 0.15 < diameter < 0.3
		Random rnd = new Random();
		rnd.setSeed(new Date().hashCode());

		BouncingBall ball1 = new BouncingBall(board, 175-rnd.nextDouble()*350, 0.15+rnd.nextDouble()*0.15, ColourLibrary.red(), ColourLibrary.black());
		ball1.setPosition(-0.69+rnd.nextDouble()*0.38, -0.39+rnd.nextDouble()*0.58);

		BouncingBall ball2 = new BouncingBall(board, 175-rnd.nextDouble()*350, 0.15+rnd.nextDouble()*0.15, ColourLibrary.blue(), ColourLibrary.black());
		ball2.setPosition(0.31+rnd.nextDouble()*0.38, -0.39+rnd.nextDouble()*0.58);

		board.addBall(ball1);
		board.addBall(ball2);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {

		case KeyEvent.VK_SPACE:
			board.destroy();
			makeRandomBoard();
			break;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
