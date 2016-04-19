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
 * Class for the wall of the rink
 * @author JeremyLittel
 *
 */
public class Wall extends Shape {

	protected double height = 100;
	protected double width = 100;
	//list of all of the edges
	protected ArrayList<Edge> solidEdges;
	protected ArrayList<Edge> drawEdges;
	//list of circles that bounce off of the wall
	protected ArrayList<Circle> circles;
	protected double edgeMult = .95;
	
	/**
	 * @param nView
	 */
	public Wall(View nView) {
		super(nView);
		solidEdges = new ArrayList<Edge>();
		drawEdges = new ArrayList<Edge>();
		circles = new ArrayList<Circle>();
		
		//create the edges
		initEdges(.9,drawEdges);
		initEdges(.9, solidEdges);
	}
	
	/**
	 * Get the edges that need to be interacted with 
	 * @return
	 */
	public ArrayList<Edge> getEdges(){
		return solidEdges;
	}
	
	/**
	 * Create all of the edges for the wall
	 */
	public void initEdges(double edgeMult, ArrayList<Edge> edges){
		//top edge
		edges.add(new Edge(view, new Point2D.Double(edgeMult *.8, edgeMult *1.0), new Point2D.Double(edgeMult *-.8, edgeMult *1.0)));
		//right edge
		edges.add(new Edge(view, new Point2D.Double(edgeMult *1.0, edgeMult *-.7), new Point2D.Double(edgeMult *1.0, edgeMult *.7)));
		//bottom edge
		edges.add(new Edge(view, new Point2D.Double(edgeMult *-.8, edgeMult *-1.0), new Point2D.Double(edgeMult *.8, edgeMult *-1.0)));
		//left edge
		edges.add(new Edge(view, new Point2D.Double(edgeMult *-1.0, edgeMult *.7), new Point2D.Double(edgeMult *-1.0,edgeMult * -.7)));
		
		//bottom right
		createCircularEdges(edges, -Math.PI/2.0,edgeMult * 0.2,edgeMult * 0.3, new Point2D.Double(edgeMult *.8, edgeMult *-.7) , new Point2D.Double(edgeMult *.8, edgeMult *-1.0));
		//top right
		createCircularEdges(edges, 0.0,edgeMult * 0.2, edgeMult *0.3, new Point2D.Double(edgeMult *.8, edgeMult *.7) , new Point2D.Double(edgeMult *1.0,edgeMult * .7));
		//top left
		createCircularEdges(edges, Math.PI/2.0,edgeMult * 0.2, edgeMult *0.3, new Point2D.Double(edgeMult *-.8,edgeMult * .7) , new Point2D.Double(edgeMult *-.8,edgeMult * 1.0));
		//bottom left
		createCircularEdges(edges, Math.PI,edgeMult * 0.2,edgeMult * 0.3, new Point2D.Double(edgeMult *-.8,edgeMult * -.7) , new Point2D.Double(edgeMult *-1.0, edgeMult *-.7));

	}
	
	/**
	 * Create a circlular edge starting at a point and an angle
	 * @param startAngle
	 * @param radius
	 * @param startPoint
	 */
	private void createCircularEdges( ArrayList<Edge> edges,double startAngle, double radiusX, double radiusY, Point2D.Double centerPoint, Point2D.Double startPoint){
		
		//only loop a 4th of the circle
		for (int i=0; i<=8; i++)
		{
			double a = (2.0 * Math.PI) * (i / 32.0) + startAngle;
			double x = centerPoint.x + radiusX * Math.cos(a);
			double y = centerPoint.y + radiusY * Math.sin(a);
			//can create a new edge
			if(i > 0){
				Point2D.Double endPoint = new Point2D.Double(x, y);
				edges.add(new Edge((view), startPoint, endPoint));
				//set the next start point
				startPoint = endPoint;
			}
		}
	}
	
	public void update(GLAutoDrawable drawable){
		super.update(drawable);
		//update all of the edges
		for (Edge e : drawEdges){
			e.update(drawable);
		}
	}

	@Override
	public void render(GLAutoDrawable drawable){
		GL2		gl = drawable.getGL().getGL2();
//		drawRect(gl, height, width, 54,253,17);

		//render all of the edges
		for (Edge e : drawEdges){
			e.render(drawable);
		}
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
