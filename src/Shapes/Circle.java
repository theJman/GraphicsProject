package Shapes;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import Base.View;

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

	protected double radius = 50;
	protected int cantBounceTick = 0;//only can bounce when this is 0. Added so cicles cannot stick together!:)
	protected int mass = 1;//default mass

	//list of all of the points on the circle(used for bouncing it off of the walls)
	protected ArrayList<Point2D.Double> points;

	protected boolean sparks = false;

	/**
	 * @param nView
	 */
	public Circle(View nView) {
		super(nView);
		points = new ArrayList<Point2D.Double>();
	}

	@Override
	public void update(GLAutoDrawable drawable){
		super.update(drawable);

		/*
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
		*/
	}

	@Override
	public void render(GLAutoDrawable drawable){
		GL2		gl = drawable.getGL().getGL2();
<<<<<<< HEAD
		if(view.neonMode)
=======

		if(View.neonMode)
>>>>>>> Koby-Branch
		{
			drawCircle(gl, true, radius, View.globR,View.globG,View.globB);
		}
		else
		{
			drawCircle(gl, true, radius, 0,255,0);
		}

		drawCircle(gl, false, radius*.9, 0,0,0);

	}

	/**
	 * 	Draws a circle at the shape's center given a gl radius and color
	 * @param gl
	 * @param logPoints should we keep track of these points
	 * @param radius
	 * @param red
	 * @param green
	 * @param blue
	 */
	protected void drawCircle(GL2 gl, boolean logPoints, double radius, double red, double green, double blue){
		drawCircle(gl, logPoints, radius, red, green, blue, 255.0);
	}

	/**
	 * Draws a circle at the shape's center given a gl radius and color
	 * @param gl
	 * @param logPoints
	 * @param radius
	 * @param red
	 * @param green
	 * @param blue
	 * @param alpha
	 */
	protected void drawCircle(GL2 gl, boolean logPoints, double radius, double red, double green, double blue, double alpha){
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		setColor(gl, red, green, blue, alpha);
		gl.glVertex2d(center.x, center.y);
		//reset all of the points
		if(logPoints)points.clear();
		for (int i=0; i<=32; i++)
		{
			double a = (2.0 * Math.PI) * (i / 32.0);
			double x = center.x + convertWidth(radius) * Math.cos(a);
			double y = center.y + convertHeight(radius) * Math.sin(a);
			if (logPoints){
				points.add(new Point2D.Double(x, y)); //add to the list of points
			}
			gl.glVertex2d(x,y);
		}
		gl.glEnd();

		// If there is a collision, create a spark
		if(sparks) drawSparks(gl);
	}

	public void drawSparks(GL2 gl)
	{
		double dist = Math.abs(convertWidth(radius) - Math.sqrt( (convertWidth(radius) * convertWidth(radius)) + (convertHeight(radius) * convertHeight(radius)) ));
		gl.glBegin(GL2.GL_LINES);
			// Draw west spark
			gl.glVertex2d(center.x - convertWidth(radius) - (dist*.5), center.y);
			gl.glVertex2d(center.x - convertWidth(radius) - (dist*.5) - .015, center.y);

			// Draw east spark
			gl.glVertex2d(center.x + convertWidth(radius) + (dist*.5), center.y);
			gl.glVertex2d(center.x + convertWidth(radius) + (dist*.5) + .015, center.y);

			// Draw north spark
			gl.glVertex2d(center.x, center.y + convertHeight(radius) + dist);
			gl.glVertex2d(center.x, center.y + convertHeight(radius) + dist + .015);

			// Draw south spark
			gl.glVertex2d(center.x, center.y - convertHeight(radius) - dist);
			gl.glVertex2d(center.x, center.y - convertHeight(radius) - dist - .015);

			// Draw north west spark
			gl.glVertex2d(center.x - convertWidth(radius), center.y + convertHeight(radius));
			gl.glVertex2d(center.x - convertWidth(radius) - .015, center.y + convertHeight(radius) + .015);

			// Draw north east spark
			gl.glVertex2d(center.x + convertWidth(radius), center.y + convertHeight(radius));
			gl.glVertex2d(center.x + convertWidth(radius) + .015, center.y + convertHeight(radius) + .015);

			// Draw south east spark
			gl.glVertex2d(center.x + convertWidth(radius), center.y - convertHeight(radius));
			gl.glVertex2d(center.x + convertWidth(radius) + .015, center.y - convertHeight(radius) - .015);

			// Draw south west spark
			gl.glVertex2d(center.x - convertWidth(radius), center.y - convertHeight(radius));
			gl.glVertex2d(center.x - convertWidth(radius) - .015, center.y - convertHeight(radius) - .015);

		gl.glEnd();
		sparks = false;
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
