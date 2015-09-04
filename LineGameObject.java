package ass1;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class LineGameObject extends GameObject {

	private double[] myPoints;
	private double[] myLineColour;
	
	public LineGameObject(GameObject parent, double x1, double y1, int x2, int y2, double[] lineCol) {
		super(parent);
		myPoints = new double[] {x1,y1,x2,y2};
		myLineColour = lineCol;
	}

	//Creates a unit vector
	public LineGameObject(GameObject parent, double[] lineCol) {
		super(parent);
		myPoints = new double[] {0,0,1,0}; 
		myLineColour = lineCol;
	}

	/**
	 * Get the line
	 * @return
	 */
	public double[] getPoints() {        
		return myPoints;
	}

	/**
	 * Set the line
	 * @param points
	 */
	public void setPoints(double[] points) {
		myPoints = points;
	}

	/**
	 * Get the line colour.
	 * @return
	 */
	public double[] getLineColour() {
		return myLineColour;
	}

	/**
	 * Set the line colour.
	 * Setting the colour to null means the line should not be drawn
	 * @param lineColour
	 */
	public void setLineColour(double[] lineColour) {
		myLineColour = lineColour;
	}
	
	@Override
	public void drawSelf(GL2 gl) {

		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glPushMatrix();
		{
			if (myLineColour != null) {
				
				gl.glColor4d(myLineColour[0],myLineColour[1],myLineColour[2],myLineColour[3]);
				gl.glBegin(GL2.GL_LINES);
				{
					gl.glVertex2d(myPoints[0], myPoints[1]);
					gl.glVertex2d(myPoints[2], myPoints[3]);
				}
				gl.glEnd();
			}
		}
		gl.glPopMatrix();
	}
}
