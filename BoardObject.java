package ass1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BoardObject extends PolygonalGameObject {

	public static final int BOUNCE_THRESHOLD = 4;
	
	private int sound = 0;

	public List<BouncingBall> myBalls = new ArrayList<BouncingBall>();

	public BoardObject(GameObject parent, double[] points, double[] fillColour,
			double[] lineColour) {
		super(parent, points, fillColour, lineColour);
	}

	public void addBall (BouncingBall b) {
		myBalls.add(b);
	}

	/* (non-Javadoc)
	 * @see ass1.GameObject#update(double)
	 */
	@Override
	public void update(double dt) {

		BouncingBall curr;
		BouncingBall test;
		
		++sound;

		for (int i=0;i<myBalls.size()-1;++i) {
			curr = myBalls.get(i);
			if (curr.bounceCount > BOUNCE_THRESHOLD) {
				for (int j=i+1;j<myBalls.size();++j) {
					test = myBalls.get(j);
					if (test.bounceCount > BOUNCE_THRESHOLD) {

						//System.out.println(MathUtil.distanceBetweenPoints(curr.getPosition()[0], curr.getPosition()[1],
						//		test.getPosition()[0], test.getPosition()[1]));
						double currPos[] = curr.getPosition();
						double testPos[] = test.getPosition();

						if (MathUtil.distanceBetweenPoints(currPos[0],currPos[1],testPos[0],testPos[1])
								< curr.getRadius() + test.getRadius()) {

							double deltaX;
							double deltaY;

							if (currPos[0] < testPos[0]) {
								deltaX = testPos[0] - currPos[0];
								deltaY = testPos[1] - currPos[1];
								
								curr.reflectOffBall(Math.toDegrees(Math.atan(deltaY/deltaX)));
								test.reflectOffBall(Math.toDegrees(Math.atan(deltaY/deltaX))+180);
							} else {
								deltaX = currPos[0] - testPos[0];
								deltaY = currPos[1] - testPos[1];
								
								curr.reflectOffBall(Math.toDegrees(Math.atan(deltaY/deltaX))+180);
								test.reflectOffBall(Math.toDegrees(Math.atan(deltaY/deltaX)));
							}	

							System.out.println(sound);
							
							if (sound > 60) {
								sound = 0;
								new Thread(new Runnable() {
									public void run() {
										try {
											AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("Resources/jump.wav"));
											Clip clip = AudioSystem.getClip();
											clip.open(audioIn);
											clip.start();
										} catch (Exception e) {
										}
									}
								}).start();
							}
							
							// System.out.println(Math.toDegrees(Math.atan(deltaY/deltaX))+" "+ Math.toDegrees(Math.atan(-deltaY/deltaX)));

							curr.bounceCount = 0;
							test.bounceCount = 0;
						}
					}
				}
			}
		}
	}
}
