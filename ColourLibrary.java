package ass1;

import java.util.Date;
import java.util.Random;

public class ColourLibrary {

	private static Random rnd = new Random(new Date().hashCode());
	
	static double[] white() {
		return new double[] {1,1,1,0};
	}
	
	static double[] black() {
		return new double[] {0,0,0,0};
	}
	
	static double[] red() {
		return new double[] {0.5+rnd.nextDouble()*0.4, 0.2+rnd.nextDouble()*0.2, 0.2+rnd.nextDouble()*0.3, 0};
	}
	
	static double[] green() {
		return new double[] {0.1+rnd.nextDouble()*0.2, 0.6+rnd.nextDouble()*0.4, 0.2+rnd.nextDouble()*0.5, 0};
	}
	
	static double[] blue() {
		return new double[] {0.1+rnd.nextDouble()*0.3, 0.2+rnd.nextDouble()*0.4, 0.5+rnd.nextDouble()*0.5, 0};
	}
}
