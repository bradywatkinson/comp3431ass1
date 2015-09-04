package ass1;

import javax.media.opengl.GL2;

public class RotatingPolygonalGameObject extends PolygonalGameObject {

	private int rotation;
	
	public RotatingPolygonalGameObject(GameObject parent, double[] points,
			double[] fillColour, double[] lineColour) {
		super(parent, points, fillColour, lineColour);
		rotation = 0;
	}


	@Override
	public void update(double dt) {
		this.myRotation = MathUtil.normaliseAngle(this.myRotation+2);
	}
}
