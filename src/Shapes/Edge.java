/**
 * 
 */
package Shapes;

import java.awt.geom.Point2D;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import Base.View;

/**
 * @author JeremyLittel
 *
 */
public class Edge extends Shape {
	protected Point2D.Double start;
	protected Point2D.Double end;
	
	protected double red = 255, green = 0, blue = 0, alpha = 255; 
	
	/**
	 * @param nView
	 */
	public Edge(View nView) {
		super(nView);

		start = new Point2D.Double(0, 0);
		end = new Point2D.Double(0, 0);
	}
	
	/**
	 * Create a new edge with a start and end point
	 * @param nView
	 * @param nStart
	 * @param nEnd
	 */
	public Edge(View nView, Point2D.Double nStart, Point2D.Double nEnd){
		super(nView);
		start = nStart;
		end = nEnd;
	}
	
	/**
	 * Set the color of this edge
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setColor(double r, double g, double b, double a){
		red = r;
		green = g;
		blue = b;
		alpha = a;
	}

	/**
	 * Get the direction vector
	 * @return
	 */
	public Point2D.Double d(){
		return new Point2D.Double(end.x-start.x, end.y-start.y);
	}
	
	/**
	 * Gets the normal vector to the line
	 * @return
	 */
	public Point2D.Double getNormal(){
		return new Point2D.Double(-d().y,d().x);
		
	}
	
	/**
	 * Get the normal unit vector
	 * @return
	 */
	public Point2D.Double getNormalUnit(){
		Point2D.Double nU = getNormal();
		nU.x /= getMagnitude(getNormal());
		nU.y /= getMagnitude(getNormal());
		return nU;
	}
	
	/**
	 * Cross product of two vectors
	 * @param one
	 * @param two
	 * @return
	 */
	public static double crossProduct(Point2D.Double one, Point2D.Double two){
		return one.x*two.y-one.y*two.x;
	}
	
	/**
	 * Get the magnitude of a vector
	 * @param v
	 * @return
	 */
	public static double getMagnitude(Point2D.Double v){
		return Math.sqrt(v.x*v.x + v.y*v.y);
	}
	
	/**
	 * Dot product
	 * @param one
	 * @param two
	 * @return
	 */
	public static double dotProduct(Point2D.Double one, Point2D.Double two){
		return one.x*two.x + one.y*two.y;
	}
	
	/**
	 * Scaler Multiplication
	 * @param v
	 * @param s
	 * @return
	 */
	public static Point2D.Double scalerMult(Point2D.Double v, double s){
		return new Point2D.Double(v.x*s, v.y*s);
	}
	
	/**
	 * Adds two vectors together
	 * @param one
	 * @param two
	 * @return
	 */
	public static Point2D.Double vectorAdd(Point2D.Double one, Point2D.Double two){
		return new Point2D.Double(one.x+two.x, one.y+two.y);
	}
	
	public static Point2D.Double vectorSub(Point2D.Double one, Point2D.Double two){
		return new Point2D.Double(one.x-two.x, one.y-two.y);
	}
	
	/**
	 * Checks if a point just went outside of the this line
	 * @param point
	 * @return
	 */
	public boolean isPointOutside(Point2D.Double point){
		Point2D.Double directionToPoint = new Point2D.Double(point.x-start.x, point.y-start.y);
		return crossProduct(directionToPoint, d()) > 0;
	}
	
	/**
	 * Check if a point is inside
	 * @param point
	 * @return
	 */
	public boolean isPointInside(Point2D.Double point){
		return !isPointOutside(point);
	}
	
	/**
	 * Get the unit vector from a vector
	 * @param v
	 * @return
	 */
	public static Point2D.Double getUnitVector(Point2D.Double v){
		double mag = getMagnitude(v);
		return new Point2D.Double(v.x/mag, v.y/mag);
	}
	
	/**
	 * Render the line
	 */
	public void render(GLAutoDrawable drawable){
		GL2		gl = drawable.getGL().getGL2();
		gl.glBegin(GL2.GL_LINES);
		setColor(gl, red, green, blue, alpha);
		gl.glVertex2d(start.x, start.y);
		gl.glVertex2d(end.x, end.y);
		gl.glEnd();
	}

	
}
