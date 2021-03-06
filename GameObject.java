package ass1;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;


/**
 * A GameObject is an object that can move around in the game world.
 * 
 * GameObjects form a scene tree. The root of the tree is the special ROOT object.
 * 
 * Each GameObject is offset from its parent by a rotation, a translation and a scale factor. 
 *
 * @author malcolmr
 */
public class GameObject {

	// the list of all GameObjects in the scene tree
	public final static List<GameObject> ALL_OBJECTS = new ArrayList<GameObject>();

	// the root of the scene tree
	public final static GameObject ROOT = new GameObject();

	// the links in the scene tree
	private GameObject myParent;
	private List<GameObject> myChildren;

	// the local transformation
	//myRotation should be normalised to the range (-180..180)
	protected double myRotation;
	protected double myScale;
	protected double[] myTranslation;

	// is this part of the tree showing?
	private boolean amShowing;

	/**
	 * Special private constructor for creating the root node. Do not use otherwise.
	 */
	private GameObject() {
		myParent = null;
		myChildren = new ArrayList<GameObject>();

		myRotation = 0;
		myScale = 1;
		myTranslation = new double[2];
		myTranslation[0] = 0;
		myTranslation[1] = 0;

		amShowing = true;

		ALL_OBJECTS.add(this);
	}

	/**
	 * Public constructor for creating GameObjects, connected to a parent (possibly the ROOT).
	 *  
	 * New objects are created at the same location, orientation and scale as the parent.
	 *
	 * @param parent
	 */
	public GameObject(GameObject parent) {
		myParent = parent;
		myChildren = new ArrayList<GameObject>();

		parent.myChildren.add(this);

		myRotation = 0;
		myScale = 1;
		myTranslation = new double[2];
		myTranslation[0] = 0;
		myTranslation[1] = 0;

		// initially showing
		amShowing = true;

		ALL_OBJECTS.add(this);
	}

	/**
	 * Remove an object and all its children from the scene tree.
	 */
	public void destroy() {
		for (GameObject child : myChildren) {
			child.destroy();
		}

		myParent.myChildren.remove(this);
		ALL_OBJECTS.remove(this);
	}

	/**
	 * Get the parent of this game object
	 * 
	 * @return
	 */
	public GameObject getParent() {
		return myParent;
	}

	/**
	 * Get the children of this object
	 * 
	 * @return
	 */
	public List<GameObject> getChildren() {
		return myChildren;
	}

	/**
	 * Get the local rotation (in degrees)
	 * 
	 * @return
	 */
	public double getRotation() {
		return myRotation;
	}

	/**
	 * Set the local rotation (in degrees)
	 * 
	 * @return
	 */
	public void setRotation(double rotation) {
		myRotation = MathUtil.normaliseAngle(rotation);
	}

	/**
	 * Rotate the object by the given angle (in degrees)
	 * 
	 * @param angle
	 */
	public void rotate(double angle) {
		myRotation += angle;
		myRotation = MathUtil.normaliseAngle(myRotation);
	}

	/**
	 * Get the local scale
	 * 
	 * @return
	 */
	public double getScale() {
		return myScale;
	}

	/**
	 * Set the local scale
	 * 
	 * @param scale
	 */
	public void setScale(double scale) {
		myScale = scale;
	}

	/**
	 * Multiply the scale of the object by the given factor
	 * 
	 * @param factor
	 */
	public void scale(double factor) {
		myScale *= factor;
	}

	/**
	 * Get the local position of the object 
	 * 
	 * @return
	 */
	public double[] getPosition() {
		double[] t = new double[2];
		t[0] = myTranslation[0];
		t[1] = myTranslation[1];

		return t;
	}

	/**
	 * Set the local position of the object
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(double x, double y) {
		myTranslation[0] = x;
		myTranslation[1] = y;
	}

	/**
	 * Move the object by the specified offset in local coordinates
	 * 
	 * @param dx
	 * @param dy
	 */
	public void translate(double dx, double dy) {
		myTranslation[0] += dx;
		myTranslation[1] += dy;
	}

	/**
	 * Test if the object is visible
	 * 
	 * @return
	 */
	public boolean isShowing() {
		return amShowing;
	}

	/**
	 * Set the showing flag to make the object visible (true) or invisible (false).
	 * This flag should also apply to all descendents of this object.
	 * 
	 * @param showing
	 */
	public void show(boolean showing) {
		amShowing = showing;
	}

	/**
	 * Update the object. This method is called once per frame. 
	 * This does nothing in the base GameObject class. Override this in subclasses.
	 * @param dt The amount of time since the last update (in seconds)
	 */
	public void update(double dt) {
		// do nothing
	}

	/**
	 * Draw the object (but not any descendants)
	 * This does nothing in the base GameObject class. Override this in subclasses.
	 * @param gl
	 */
	public void drawSelf(GL2 gl) {
		/*
		double [] p = this.getGlobalPosition();
		double r = this.getGlobalRotation();
		double s = this.getGlobalScale();

		gl.glTranslated(p[0],p[1],0);
		gl.glRotated(r, 0, 0, 1);
		gl.glScaled(s,s,0);
		 */
	}


	// ===========================================
	// COMPLETE THE METHODS BELOW
	// ===========================================

	/**
	 * Draw the object and all of its descendants recursively.
	 * @param gl
	 */
	public void draw(GL2 gl) {

		// don't draw if it is not showing
		if (!amShowing)	return;

		// save the coordinate frame so people above me are not affected
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glPushMatrix();
		{
			// make my transformations
			gl.glTranslated(this.myTranslation[0],this.myTranslation[1],0);
			gl.glRotated(this.myRotation, 0, 0, 1);
			gl.glScaled(this.myScale,this.myScale,0);

			//draw me
			this.drawSelf(gl);

			//then draw all my children
			for (GameObject child :  myChildren) {
				child.draw(gl);
			}
		}
		gl.glPopMatrix();
	}

	/**
	 * Compute the object's position in world coordinates
	 * @return a point in world coordinates in [x,y] form
	 */
	public double[] getGlobalPosition() {

		//get parents matrix
		double[] t = (this.myParent == null ? new double[] {0,0} : this.myParent.getGlobalPosition());
		double r = (this.myParent == null ? 0 : this.myParent.getGlobalRotation());
		double s = (this.myParent == null ? 1 : this.myParent.getGlobalScale());

		double[][] m = MathUtil.makeTransformationMatrix(t,r,s);

		double[] gp = new double[2];
		gp[0] = m[0][2] + this.myTranslation[0]*m[0][0] + this.myTranslation[1]*m[0][1];
		gp[1] = m[1][2] + this.myTranslation[0]*m[1][0] + this.myTranslation[1]*m[1][1];

		return gp;

	}

	/**
	 * Compute the object's rotation in the global coordinate frame
	 * @return the global rotation of the object (in degrees) and 
	 * normalized to the range (-180, 180) degrees. 
	 */
	public double getGlobalRotation() {

		double r = this.myRotation + (this.myParent == null ? 0 : this.myParent.getGlobalRotation());
		return MathUtil.normaliseAngle(r);
	}

	/**
	 * Compute the object's scale in global terms
	 * @return the global scale of the object 
	 */
	public double getGlobalScale() {

		double s = this.myScale * (this.myParent == null ? 1 : this.myParent.getGlobalScale());
		return s;
	}

	/**
	 * Change the parent of a game object.
	 * 
	 * @param parent
	 */
	public void setParent(GameObject parent) {

		//get current global stuff
		//get new parent global stuff
		//apply the difference

		//get my matrix
		double[] p1 = this.getGlobalPosition();
		double r1 = this.getGlobalRotation();
		double s1 = this.getGlobalScale();

		//get parents matrix
		double[] p2 = (parent == null ? new double[] {0,0} : parent.getGlobalPosition());
		double r2 = (parent == null ? 0 : parent.getGlobalRotation());
		double s2 = (parent == null ? 1 : parent.getGlobalScale());

		double[][] m = MathUtil.makeTransformationMatrix(p2,r2,s2);

		// the need transformations must be the difference of the two and in reverse order
		this.myScale = s1/s2;
		this.myRotation = MathUtil.normaliseAngle(r1 - r2);
		this.myTranslation = MathUtil.SolveLinearEquations2(m[0][0], m[0][1], m[1][0], m[1][1], p1[0]-m[0][2], p1[1]-m[1][2]);;

		// actually reparent
		this.myParent.myChildren.remove(this);
		this.myParent = parent;
		this.myParent.myChildren.add(this);

	}

	public List<GameObject> collision(double[] p) {

		List<GameObject> list = new ArrayList<GameObject>();

		double epsilon = 0.0001;

		for (GameObject test : GameObject.ALL_OBJECTS) {

			// line
			if (test instanceof LineGameObject) {
				double[] points = ((LineGameObject) test).getPoints();

				double[] t = test.getGlobalPosition();
				double r = test.getGlobalRotation();
				double s = test.getGlobalScale();

				double[][] m = MathUtil.makeTransformationMatrix(t,r,s);

				// points needs to be transformed into global coordinates
				double[] pointA = MathUtil.multiply(m, new double[] {points[0],points[1],1});
				double[] pointB = MathUtil.multiply(m, new double[] {points[2],points[3],1});

				// like triangles
				/*			+ B
				 *	   P  + |
				 * 		+	|
				 *    +	|	|
				 * 	+	|	|
				 *+-----|---|
				 * A
				 */
				if ( (p[0]-pointA[0])/(p[1]-pointA[1]) - (pointB[2]-pointA[0])/(pointB[3]-pointA[1]) < epsilon) {
					list.add(test);
				}
			
			// circle
			} else if (test instanceof CircularGameObject) {
				// within radius of each other
				double[] cp = test.getGlobalPosition();
				if (MathUtil.distanceBetweenPoints(p[0], p[1], cp[0], cp[1]) - ((CircularGameObject) test).getRadius()*test.getGlobalScale() < epsilon) {
					list.add(test);
				}
				
			// polygon
			} else if (test instanceof PolygonalGameObject) {
				// ray method
				// for each edge, check if we hit it with our ray
				// vertical rays
				double[] points = ((PolygonalGameObject) test).getPoints();
				int count = 0;
				for (int i=0;i<points.length;i+=2) {
					double m = (points[i+1]-points[(i+3)%points.length])
								/ (points[i+0]-points[(i+2)%points.length]);
					// y - y1 = m ( x - x1 )
					// at x = p[0]:
					double y = m * (p[0] - points[i]) + points[i+1];
					
					// given this y, find out if it crosses at at y > ray origin
					// and crosses on the line segment
					
					if (  y > Math.min(points[i+1],points[(i+3)%points.length])
							&& y <= Math.max(points[i+1],points[(i+3)%points.length]) 
								&& y > p[1]) {
						++count;
					}
				}
				
				// if we have an odd number of count, we are inside!
				if (count%2 == 1) {
					
				}
			}
		}
		return null;
	}
}
