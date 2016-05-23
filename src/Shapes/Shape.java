package Shapes;
import java.awt.geom.Point2D;
import java.util.UUID;

import Base.View;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.awt.TextRenderer;

/**
 * Base Class for all of the shapes that we need to draw
 * @author JeremyLittel
 *
 */
public class Shape {
	//Instance variables
	public UUID id = UUID.randomUUID();
	protected View view;//pointer to the view
	public Point2D.Double center;//center position of the shape
	public Point2D.Double velocity;//velocity of the shape
	protected Point2D.Double acceleration;//acceleration of the shape

	/**
	 * Construct a new shape with a reference to a view's gl
	 * @param nGL
	 */
	public Shape(View nView) {
		view = nView;
		center = new Point2D.Double(0, 0);
		velocity = new Point2D.Double(0, 0);
		acceleration = new Point2D.Double(0, 0);

	}

	/**
	 * Methods for easily setting the color
	 * @param gl
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setColor(GL2 gl, double r, double g, double b, double a){
		gl.glColor4d(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
	}
	public void setColor(GL2 gl, double r, double g, double b){
		setColor(gl, r, g, b, 255.0);
	}
	public void setColor(TextRenderer renderer, float r, float g, float b){
		renderer.setColor(r/255.0f, g/255.0f, b/255.0f, 1.0f);
	}

	public double convertWidth(double w){
		return ((w) ) / view.getWidth();
	}
	public double convertHeight(double h){
		return ((h) ) / view.getHeight();
	}

	//------------------Important two methods used by view-----------------
	/**
	 * Base update method of every shape
	 * SUBCLASS SHOULD CALL THIS METHOD BEFORE OWN LOGIC
	 */
	public void update(GLAutoDrawable drawable){
		GL2		gl = drawable.getGL().getGL2();

		//basic movement
		velocity.x += acceleration.x;
		velocity.y += acceleration.y;
		center.x += velocity.x;
		center.y += velocity.y;

	}

	/**
	 * Base render method for every shape
	 * SHOULD BE OVERRIDDEN
	 */
	public void render(GLAutoDrawable drawable){
		GL2		gl = drawable.getGL().getGL2();

	}

	//getters and setters
	public Point2D.Double getVelocity(){
		return velocity;
	}
	public Point2D.Double getCenter(){
		return center;
	}
	public Point2D.Double getAcceleration(){
		return acceleration;
	}

	/**
	 * Setter for velocity
	 * Override if you do not want the velocity to change
	 * @param v
	 */
	public void setVelocity(Point2D.Double v){
		velocity = v;
	}


	public Point2D.Double getCenterNotInView(){
		int				w = view.getWidth();
		int				h = view.getHeight();
		Point2D.Double	p = view.getOrigin();
		double			 vx = (center.x -p.x + 1.0) * w / 2.0;
		double			 vy = -(center.y - p.y -1.0) * h / 2.0;

		return new Point2D.Double(vx, vy);
	}

	public void setCenterNotInView(Point2D.Double c){
		int				w = view.getWidth();
		int				h = view.getHeight();
		Point2D.Double	p = view.getOrigin();
		double			cx = p.x + (c.x * 2.0) / w - 1.0;
		double			cy = p.y - (c.y * 2.0) / h + 1.0;

		center.x = cx;
		center.y = cy;
	}

	public Point2D.Double getVelocityNotInView(){
		int				w = view.getWidth();
		int				h = view.getHeight();
		Point2D.Double	p = view.getOrigin();
		double			 vx = (velocity.x -p.x + 1.0) * w / 2.0;
		double			 vy = -(velocity.y - p.y -1.0) * h / 2.0;

		return new Point2D.Double(vx, vy);
	}

	public void setVelocityToInView(Point2D.Double nVel){
		int				w = view.getWidth();
		int				h = view.getHeight();
		Point2D.Double	p = view.getOrigin();
		double			vx = p.x + (nVel.x * 2.0) / w - 1.0;
		double			vy = p.y - (nVel.y * 2.0) / h + 1.0;

		velocity.x = vx;
		velocity.y = vy;
	}
}
