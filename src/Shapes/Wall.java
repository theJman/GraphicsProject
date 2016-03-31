package Shapes;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import Base.View;

/**
 * 
 */

/**
 * Class for the wall of the rink
 * @author JeremyLittel
 *
 */
public class Wall extends Shape {

	protected double height = 100;
	protected double width = 100;
	
	/**
	 * @param nView
	 */
	public Wall(View nView) {
		super(nView);
		// TODO Auto-generated constructor stub
	}
	
	public void update(GLAutoDrawable drawable){
		super.update(drawable);
	}

	@Override
	public void render(GLAutoDrawable drawable){
		GL2		gl = drawable.getGL().getGL2();
		drawRect(gl, height, width, 54,253,17);

	}
	
	protected void drawRect(GL2 gl, double height, double width, double red, double green, double blue){
		drawRect(gl, height, width, red, green, blue, 255.0);
	}
	
	protected void drawRect(GL2 gl, double height, double width, double red, double green, double blue, double alpha){
		gl.glLineWidth(20);
		gl.glBegin(GL2.GL_LINE_LOOP);
			setColor(gl, red, green, blue, alpha);
			gl.glVertex2d(-.95,  .95);
			gl.glVertex2d( .95,  .95);
			gl.glVertex2d( .95, -.95);
			gl.glVertex2d(-.95, -.95);
		gl.glEnd();
		gl.glLineWidth(1);
	}
}
