package Shapes;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import Base.View;




/**
 * 
 */

/**
 * @author JeremyLittel
 *
 */
public class Circle extends Shape {

	protected double radius = 100;
	protected int cantBounceTick = 0;//only can bounce when this is 0. Added so cicles cannot stick together!:)
	
	protected ArrayList<Edge> edges;
	
	/**
	 * @param nView
	 */
	public Circle(View nView) {
		super(nView);
		edges = new ArrayList<Edge>();
	}
	
	public void update(GLAutoDrawable drawable){
		super.update(drawable);
		if (center.x + convertWidth(radius) > 1.0) {
			velocity.x *= -1;
			center.x -= center.x + convertWidth(radius) - 1.0;
		}
		else if(center.x - convertWidth(radius) < -1.0){
			velocity.x *= -1;
			center.x -= center.x - convertWidth(radius) + 1.0;
		}
		if (center.y + convertHeight(radius) > 1.0 ) {
			velocity.y *= -1;
			center.y -= center.y + convertHeight(radius) - 1.0;
		}
		else if( center.y - convertHeight(radius) < -1.0){
			velocity.y *= -1;
			center.y -= center.y - convertHeight(radius) + 1.0;

		}
	}
	
	@Override
	public void render(GLAutoDrawable drawable){
		GL2		gl = drawable.getGL().getGL2();
		if(View.neonMode)
		{
			drawCircle(gl, radius, View.globR,View.globG,View.globB);
		}
		else
		{
			drawCircle(gl, radius, 0,255,0);
		}
		
		drawCircle(gl, radius*.9, 0,0,0);

	}
	
	/**
	 * 	Draws a circle at the shape's center given a gl radius and color
	 * @param gl
	 * @param radius
	 * @param red
	 * @param green
	 * @param blue
	 */
	protected void drawCircle(GL2 gl, double radius, double red, double green, double blue){
		drawCircle(gl, radius, red, green, blue, 255.0);
	}
	
	/**
	 * Draws a circle at the shape's center given a gl radius and color
	 * @param gl
	 * @param radius
	 * @param red
	 * @param green
	 * @param blue
	 * @param alpha
	 */
	protected void drawCircle(GL2 gl, double radius, double red, double green, double blue, double alpha){
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		setColor(gl, red, green, blue, alpha);
		gl.glVertex2d(center.x, center.y);
		ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
		
		for (int i=0; i<=32; i++)
		{
			double a = (2.0 * Math.PI) * (i / 32.0);
			double x = center.x + convertWidth(radius) * Math.cos(a);
			double y = center.y + convertHeight(radius) * Math.sin(a);
			points.add(new Point2D.Double(x, y));
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

	
	
	public void decrementCantBounceTick(){
		if(cantBounceTick > 0)
			cantBounceTick--;
	}
	
	

}
