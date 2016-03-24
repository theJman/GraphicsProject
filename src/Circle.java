import java.awt.geom.Point2D;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

/**
 * 
 */

/**
 * @author JeremyLittel
 *
 */
public class Circle extends Shape {

	protected double radius = 100;
	
	/**
	 * @param nView
	 */
	public Circle(View nView) {
		super(nView);
		
	}
	
	public void update(GLAutoDrawable drawable){
		super.update(drawable);
		if (center.x + convertWidth(radius) > 1.0 || center.x - convertWidth(radius) < -1.0) {
			velocity.x *= -1;
		}
		if (center.y + convertHeight(radius) > 1.0 || center.y - convertHeight(radius) < -1.0) {
			velocity.y *= -1;
		}
	}
	
	@Override
	public void render(GLAutoDrawable drawable){
		GL2		gl = drawable.getGL().getGL2();
		drawCircle(gl);
	}
	
	
	//private methods
	private void drawCircle(GL2 gl){
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		setColor(gl, 255, 0, 0);
		gl.glVertex2d(center.x, center.y);
		
		for (int i=0; i<=32; i++)
		{
			double a = (2.0 * Math.PI) * (i / 32.0);
			double x = center.x + convertWidth(radius) * Math.cos(a);
			double y = center.y + convertHeight(radius) * Math.sin(a);
			gl.glVertex2d(x,y);
		}
		gl.glEnd();
		
		//make more of a ring
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		setColor(gl, 0, 0, 0);
		gl.glVertex2d(center.x, center.y);
		
		for (int i=0; i<=32; i++)
		{
			double a = (2.0 * Math.PI) * (i / 32.0);
			double x = center.x + convertWidth(radius*.9) * Math.cos(a);
			double y = center.y + convertHeight(radius*.9) * Math.sin(a);
			gl.glVertex2d(x,y);
		}
		gl.glEnd();
	}

	//getters and setters
	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	
	
	

}
