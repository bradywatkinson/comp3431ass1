package ass1.tests;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;

import ass1.*;

/**
 * A simple test class for assignment1
 * 
 * @author angf
 */
public class TestPolygon {

	static PolygonalGameObject p;
	
	public static void createTestShapes(){
		
        // Create a polygon
        double colour1[] = {1,0.5,0.7,1};
        double colour2[] = {1,1,1,0};
        double points[] = {-1,0,1,-1,0,-1};
        p = new RotatingPolygonalGameObject(GameObject.ROOT,points,colour1,colour2);
        
	}
   
    /**
     * A simple example of how to use PolygonalGameObject, CircularGameObject and LineObject
     * 
     * and also how to put together a simple scene using the game engine.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Initialise OpenGL
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities(glprofile);
        
        // create a GLJPanel to draw on
        GLJPanel panel = new GLJPanel(glcapabilities);

        // Create a camera
        Camera camera = new Camera(GameObject.ROOT);
        camera.setScale(2); // scale up the camera so we can see more of the world  
              
        createTestShapes();
        
        // Add the game engine
        GameEngine engine = new GameEngine(camera);
        panel.addGLEventListener(engine);

        // Add an animator to call 'display' at 60fps        
        FPSAnimator animator = new FPSAnimator(60);
        animator.add(panel);
        animator.start();

        // Put it in a window
        JFrame jFrame = new JFrame("Test Shapes");
        jFrame.add(panel);
        jFrame.setSize(400, 400);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
