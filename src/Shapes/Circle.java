package Shapes;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

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

	public double defaultRadius = 80;
	public double radius = 80;
	protected int cantBounceTick = 0;//only can bounce when this is 0. Added so cicles cannot stick together!:)
	protected int mass = 1;//default mass
	protected int sparkCount = 0;

	//list of all of the points on the circle(used for bouncing it off of the walls)
	protected ArrayList<Point2D.Double> points;

	public boolean sparks = false;
	double red;
	double green;
	double blue;
	boolean puck = false;
	public boolean scored = false;
	public boolean specialpuck = false;

	/**
	 * @param nView
	 */
	public Circle(View nView) {
		super(nView);
		points = new ArrayList<Point2D.Double>();
		puck = false;
	}

	public Circle(View nView, boolean puck) {
		super(nView);
		this.puck = puck;
		points = new ArrayList<Point2D.Double>();


		// Randomly initialize the pucks outlining colors for the regular view
		Random rand = new Random();
		red = 0.0 + (255.0 - 0.0) * rand.nextDouble();
		green = 0.0 + (255.0 - 0.0) * rand.nextDouble();
		blue = 0.0 + (255.0 - 0.0) * rand.nextDouble();

		if(puck)
			radius = 40.0 + (30.0) * rand.nextDouble();
	}

	@Override
	public void update(GLAutoDrawable drawable){
		super.update(drawable);

		double x = convertWidth(view.getWidth());
		double y = convertHeight(view.getHeight()*(1.0/3));
		if(puck && this instanceof Puck)
		{
			if(center.x - convertWidth(radius) < -x*.9 && center.y + convertHeight(radius) < y && center.y - convertHeight(radius) > -y)
			{
				scored = true;
				view.scoreDirection = "right";
			}
			else if(center.x + convertWidth(radius) > x*.9 && center.y + convertHeight(radius) < y && center.y - convertHeight(radius) > -y)
			{
				scored = true;
				view.scoreDirection = "left";
			}
		}
	}

	@Override
	public void render(GLAutoDrawable drawable){
		GL2		gl = drawable.getGL().getGL2();


		if(view.whichSkin == 0)
			drawCircle(gl, true, radius, 0,255,0);
		else if(view.whichSkin == 1)
			drawCircle(gl, true, radius, view.globR,view.globG,view.globB);
		else if(view.whichSkin == 2)
			drawCircle(gl, true, radius, 0,0,0);

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
		if(this instanceof Puck)
		{
			if(specialpuck)
			{
				if(view.whichSkin == 0)
					setColor(gl,view.globR,view.globG,view.globB);
				else if(view.whichSkin == 1)
					setColor(gl,255,255,255);
				else if(view.whichSkin == 2)
					setColor(gl,255,255,255);
			}
			else
			{
				if(view.whichSkin == 0)
					setColor(gl, this.red, this.green, this.blue);
				else if(view.whichSkin == 1)
					setColor(gl, red, green, blue);
				else if(view.whichSkin == 2)
					setColor(gl, red, green, blue);
			}
		}
		else
			setColor(gl, red, green, blue);


		gl.glBegin(GL2.GL_TRIANGLE_FAN);
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

		if(this instanceof Puck)
		{
			if(specialpuck)
			{
				if(view.whichSkin == 0)
					setColor(gl,view.globR,view.globG,view.globB);
				else if(view.whichSkin == 1)
					setColor(gl,255,255,255);
				else if(view.whichSkin == 2)
					setColor(gl,255,255,255);
			}
			else
			{	if(view.whichSkin == 0)
					setColor(gl,0,0,0);
				else if(view.whichSkin == 1)
					setColor(gl,0,0,0);
				else if(view.whichSkin == 2)
					setColor(gl,255,255,255);
			}
		}

		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glVertex2d(center.x, center.y);
		//reset all of the points
		if(logPoints)points.clear();
		for (int i=0; i<=32; i++)
		{
			double a = (2.0 * Math.PI) * (i / 32.0);
			double x = center.x + convertWidth(radius*.8) * Math.cos(a);
			double y = center.y + convertHeight(radius*.8) * Math.sin(a);
			if (logPoints){
				points.add(new Point2D.Double(x, y)); //add to the list of points
			}
			gl.glVertex2d(x,y);
		}
		gl.glEnd();

		// If there is a collision, create a spark
		if(sparks) sparkCount = 4;
		if(sparkCount > 0){
			sparkCount--;
			drawSparks(gl);
		}
	}

	public void drawSparks(GL2 gl)
	{
		if(specialpuck)
		{
			if(view.whichSkin == 0)
				setColor(gl, this.red, this.green, this.blue);
			else if(view.whichSkin == 1)
				setColor(gl, 255,255,255);
			else if(view.whichSkin == 2)
				setColor(gl, 255,255,255);
		}
		else
		{
			if(view.whichSkin == 0)
				setColor(gl, this.red, this.green, this.blue);
			else if(view.whichSkin == 1)
				setColor(gl, view.globR, view.globG, view.globB);
			else if(view.whichSkin == 2)
				setColor(gl, 0,0,0);
		}

		gl.glLineWidth(5.0f);
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

		if(!specialpuck)
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
