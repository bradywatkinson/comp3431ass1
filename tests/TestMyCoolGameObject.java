package ass1.tests;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;

import ass1.*;

/**
 * A simple class to view MyCoolGameObject
 *
 * @author angf
 */
public class TestMyCoolGameObject {

   
    /**
     * A simple test for MyCoolGameObject
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
        
        // Add Key Inputs
        MyCoolGameObject cgo = new MyCoolGameObject();
		cgo.translate(-0.2,0.2);
		cgo.rotate(45);
		cgo.scale(0.25);
        
        // Add the game engine
        GameEngine engine = new GameEngine(camera);
        panel.addGLEventListener(engine);
        panel.addKeyListener(cgo);
        panel.setFocusable(true);

        // Add an animator to call 'display' at 60fps        
        FPSAnimator animator = new FPSAnimator(60);
        animator.add(panel);
        animator.start();

        // Put it in a window
        JFrame jFrame = new JFrame("Test Shapes");
        jFrame.add(panel);
        jFrame.setSize(600, 600);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
