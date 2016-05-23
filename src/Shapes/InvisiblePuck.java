/**
 * 
 */
package Shapes;

import com.jogamp.opengl.GLAutoDrawable;

import Base.View;

/**
 * @author JeremyLittel
 *
 */
public class InvisiblePuck extends Circle {

	/**
	 * @param nView
	 */
	public InvisiblePuck(View nView) {
		super(nView, true);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void render(GLAutoDrawable drawable) {
		//don't render, its invisible!
		if(sparks) sparkCount = 4;
		if(sparkCount > 0){
			sparkCount--;
			drawSparks(drawable.getGL().getGL2());
		}
	}

}
