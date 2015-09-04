package ass1;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BouncingBall extends CircularGameObject {

	private double angle;
	private double mySpeed;
	public int bounceCount = 0;

	/**
	 * Creates a Bouncing Ball which will bounce within its rectangular parent.
	 * Will also bounce of all other bouncing ball siblings 
	 * @param parent
	 * @param angle
	 * @param radius
	 * @param fillCol
	 * @param lineCol
	 */
	public BouncingBall(BoardObject parent, double angle, double radius, double[] fillCol,double[] lineCol) {
		super(parent, radius, fillCol, lineCol);
		this.angle = angle;
		mySpeed = 0.01;
	}

	public BouncingBall(BoardObject parent, double[] fillCol, double[] lineCol) {
		super(parent, fillCol, lineCol);
		angle = 45;
		mySpeed = 0.01;
	}
	
	public void reflectOffBall (double newAngle) {
		this.angle = MathUtil.normaliseAngle(newAngle+180);
	}

	@Override
	public void update(double dt) {



		this.myTranslation[0] += this.mySpeed * Math.cos(Math.toRadians(angle));
		this.myTranslation[1] += this.mySpeed * Math.sin(Math.toRadians(angle));

		// get the points of my parent
		double[] points = ((PolygonalGameObject) this.getParent()).getPoints();

		if (++this.bounceCount > BoardObject.BOUNCE_THRESHOLD) {

			// for each set of points in my parent, test whether I have collided
			for (int i=0;i<points.length;i+=2) {
				// vertical line:											and I am within radius of the line
				if (points[i%points.length]==points[(i+2)%points.length] && Math.abs(points[i+1]-myTranslation[1]) < radius) {
					angle = -angle;
					this.bounceCount = 0;
					return;
				// horizontal line
				} else if (points[i+1]%points.length==points[(i+3)%points.length] && Math.abs(points[i]-myTranslation[0]) < radius) {
					// no need to normalise because the values are guaranteed to be below 180
					if (angle > 0) angle = 180 - angle;
					else angle = -(180 - Math.abs(angle));
					this.bounceCount = 0;
					return;
				}
			}
		}
	}

}
