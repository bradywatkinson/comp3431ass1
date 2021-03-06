package ass1;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 * A game object that has a polygonal shape.
 * 
 * This class extend GameObject to draw polygonal shapes.
 *
 * @author malcolmr
 */
public class PolygonalGameObject extends GameObject {

	private double[] myPoints;
	private double[] myFillColour;
	private double[] myLineColour;

	/**
	 * Create a polygonal game object and add it to the scene tree
	 * 
	 * The polygon is specified as a list of doubles in the form:
	 * 
	 * [ x0, y0, x1, y1, x2, y2, ... ]
	 * 
	 * The line and fill colours can possibly be null, in which case that part of the object
	 * should not be drawn.
	 *
	 * @param parent The parent in the scene tree
	 * @param points A list of points defining the polygon
	 * @param fillColour The fill colour in [r, g, b, a] form
	 * @param lineColour The outlien colour in [r, g, b, a] form
	 */
	public PolygonalGameObject(GameObject parent, double points[],
			double[] fillColour, double[] lineColour) {
		super(parent);

		myPoints = points;
		myFillColour = fillColour;
		myLineColour = lineColour;
	}

	/**
	 * Get the polygon
	 * 
	 * @return
	 */
	public double[] getPoints() {        
		return myPoints;
	}

	/**
	 * Set the polygon
	 * 
	 * @param points
	 */
	public void setPoints(double[] points) {
		myPoints = points;
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
	 * 
	 * Setting the colour to null means the object should not be filled.
	 * 
	 * @param fillColour The fill colour in [r, g, b, a] form 
	 */
	public void setFillColour(double[] fillColour) {
		myFillColour = fillColour;
	}

	/**
	 * Get the outline colour.
	 * 
	 * @return
	 */
	public double[] getLineColour() {
		return myLineColour;
	}

	/**
	 * Set the outline colour.
	 * 
	 * Setting the colour to null means the outline should not be drawn
	 * 
	 * @param lineColour
	 */
	public void setLineColour(double[] lineColour) {
		myLineColour = lineColour;
	}

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
				gl.glBegin(GL2.GL_POLYGON);
				{
					for (int i=0;i<myPoints.length;i+=2) {
						gl.glVertex2d(myPoints[i], myPoints[i+1]);
					}
					gl.glVertex2d(myPoints[0], myPoints[1]);
				}
				gl.glEnd();
			}
			
			if (myLineColour != null) {
				gl.glColor4d(myLineColour[0],myLineColour[1],myLineColour[2],myLineColour[3]);
				gl.glLineWidth(3);
				gl.glBegin(GL.GL_LINE_STRIP);
				{
					for (int i=0;i<myPoints.length;i+=2) {
						gl.glVertex2d(myPoints[i], myPoints[i+1]);
					}
					gl.glVertex2d(myPoints[0], myPoints[1]);
				}
				gl.glEnd();
			}
		}
		gl.glPopMatrix();

	}
}
