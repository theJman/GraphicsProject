package Shapes;
import com.jogamp.opengl.GLAutoDrawable;

import Base.View;

/**
 *
 */

/**
 * Creates a puck that is based off of a circle
 * @author JeremyLittel
 *
 */
public class Puck extends Circle {

	/**
	 * @param nView
	 */
	public Puck(View nView) {
		super(nView, true);
	}
	
	
	@Override
	public void update(GLAutoDrawable drawable) {

		//friction
		velocity.x /= 1.001;
		velocity.y /= 1.001;
		
		super.update(drawable);
	}

}
