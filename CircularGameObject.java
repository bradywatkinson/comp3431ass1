package ass1;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class CircularGameObject extends GameObject {

	protected double radius;
	private double[] myFillColour;
	private double[] myLineColour;
	static final int numVertices = 32;

	/**
	 * Circular GameObject with centre 0,0 and radius 1
	 * @param parent
	 * @param fillCol
	 * @param lineCol
	 */
	public CircularGameObject(GameObject parent, double[] fillCol, double[] lineCol) {
		super(parent);
		radius = 1;
		myFillColour = fillCol;
		myLineColour = lineCol;
	}

	/**
	 * Circular GameObject with centre 0,0 and radius d
	 * @param parent
	 * @param d radius of circle
	 * @param fillCol
	 * @param lineCol
	 */
	public CircularGameObject(GameObject parent, double d, double[] fillCol, double[] lineCol) {
		super(parent);
		radius = d;
		myFillColour = fillCol;
		myLineColour = lineCol;
	}

	/**
	 * Get the fill colour
	 * 
	 * @return
	 */
	public double[] getFillColour() {
		return myFillColour;
	}

	/**
	 * Set the fill colour.
	 * Setting the colour to null means the object should not be filled.
	 * @param fillColour The fill colour in [r, g, b, a] form 
	 */
	public void setFillColour(double[] fillColour) {
		myFillColour = fillColour;
	}

	/**
	 * Get the outline colour.
	 * @return
	 */
	public double[] getLineColour() {
		return myLineColour;
	}

	/**
	 * Set the outline colour.
	 * Setting the colour to null means the outline should not be drawn
	 * @param lineColour
	 */
	public void setLineColour(double[] lineColour) {
		myLineColour = lineColour;
	}

	public double getRadius () {
		return radius;
	}
	
	// ===========================================
	// COMPLETE THE METHODS BELOW
	// ===========================================


	/**
	 * @see ass1.spec.GameObject#drawSelf(javax.media.opengl.GL2)
	 */
	@Override
	public void drawSelf(GL2 gl) {


		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glPushMatrix();
		
		{
			if (myFillColour != null) {
				gl.glColor4d(myFillColour[0],myFillColour[1],myFillColour[2],myFillColour[3]);
	
				// approximate a circle with triangle fans
				gl.glBegin(GL2.GL_TRIANGLE_FAN);
				{        	
					gl.glVertex2d(0, 0); //The centre of the circle
					double angle = 0;
					double angleIncrement = 2*Math.PI/numVertices;
					for(int i=0; i <= numVertices; i++){
						angle = i* angleIncrement;
						double x = radius * Math.cos(angle);
						double y = radius * Math.sin(angle);
	
						gl.glVertex2d(x, y);
					}
				}
				gl.glEnd();
			}
			
			if (myLineColour != null) {
				gl.glColor4d(myLineColour[0],myLineColour[1],myLineColour[2],myLineColour[3]);
				gl.glLineWidth(3);
				
				// approximate a circle with triangle fans
				gl.glBegin(GL2.GL_LINE_STRIP);
				{        	
					double angle = 0;
					double angleIncrement = 2*Math.PI/numVertices;
					for(int i=0; i <= numVertices; i++){
						angle = i* angleIncrement;
						double x = radius * Math.cos(angle);
						double y = radius * Math.sin(angle);
	
						gl.glVertex2d(x, y);
					}
				}
				gl.glEnd();
			}
		}
		gl.glPopMatrix();
	}
}
