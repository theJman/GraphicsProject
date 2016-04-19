/**
 * 
 */
package Shapes;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author JeremyLittel
 *
 */
public class WallInteraction implements Interaction {

	private Wall wall;
	private ArrayList<Circle> circles;
	
	/**
	 * Create a new WallInteration handler with circles and a wall
	 */
	public WallInteraction(Wall nWall, ArrayList<Circle> nCircles) {
		wall = nWall;
		circles = nCircles;
	}
	
	/**
	 * Create a new wall interaction handler and add circles later
	 * @param nWall
	 */
	public WallInteraction(Wall nWall){
		wall = nWall;
		circles = new ArrayList<Circle>();
	}
	
	/**
	 * Add a cicle
	 * @param c
	 */
	public void addCircle(Circle c){
		circles.add(c);
	}

	/* (non-Javadoc)
	 * @see Shapes.Interaction#update()
	 */
	@Override
	public void update() {
		//check if any of the circles are outside any of the edges
		for(Edge e : wall.getEdges()){
			for (Circle c : circles){
			
				//find out the direction of the circle and then get a couple of the front points to check if it's outside f an edge
				//we use a couple points to handle the case where the circle is moving in almost the same direction as the wall
				Point2D.Double unitVel = Edge.getUnitVector(c.velocity);//direction vector
				Point2D.Double frontPoint = new Point2D.Double();
				double startAngle = Math.atan(unitVel.y/unitVel.x);
				if(unitVel.x < 0)
					startAngle += Math.PI;
				double angle1 = startAngle;
				double angle2 = startAngle - Math.PI/3;
				double angle3 = startAngle + Math.PI/3;
				double[] angles = {angle1,angle2,angle3};
				for(double angle : angles){
					frontPoint.x = c.center.x + c.convertWidth(c.radius) * Math.cos(angle);
					frontPoint.y = c.center.y + c.convertHeight(c.radius) * Math.sin(angle);
					
					if(e.isPointOutside(frontPoint)){
						System.out.println("bounce ball");
						//point went outside this edge
						//double currVelMag = e.getMagnitude(c.velocity);
						double normaldotv = Edge.dotProduct(e.getNormalUnit(), c.velocity)*1.8;
						Point2D.Double normalMult = Edge.scalerMult(e.getNormalUnit(), normaldotv);
						double normalMultSize = Edge.getMagnitude(normalMult);
						
						//this point is more outside than others so use it
						Point2D.Double velU = new Point2D.Double(c.velocity.x, c.velocity.y);
						Point2D.Double newVel = Edge.vectorSub(velU, normalMult);
						
						//move circle
						c.center.x -= normalMult.x;
						c.center.y -= normalMult.y;
						
						c.velocity = newVel;
						break;
					}
				}
			}
		}
	}
	
	

}
