import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Class to manage interactions between circles
 * @author JeremyLittel
 *
 */
public class CircleInteraction {
	protected ArrayList<Circle> circles;//list of all of the circles
	
	/**
	 * Create a new Circle interaction handler
	 */
	public CircleInteraction() {
		circles = new ArrayList<Circle>();
	}
	
	/**
	 * Add a circle to interact with other added circles
	 * @param c
	 */
	public void addCircle(Circle c){
		circles.add(c);
	}
	
	/**
	 * Performs updates on circles if they need to interact with each other
	 */
	public void update(){
		Set<Circle> alreadyUpdatedCircles = new HashSet<Circle>();//set of circles that we have updated and don't need to again
		
		for (Circle c1 : circles){
			if(!alreadyUpdatedCircles.contains(c1)){
				//havent updated yet
				//check if it touches another
				for (Circle c2 : circles){
					if(c1 != c2 && !alreadyUpdatedCircles.contains(c2)){
						//circles are different and the second has yet to have an interaction
						//find out if the are touching
						Point2D.Double center1 = c1.getCenterNotInView();
						Point2D.Double center2 = c2.getCenterNotInView();
						//find distance between
						double distance = center1.distance(center2);
						if(distance < c1.getRadius()/2 + c2.getRadius()/2){
							//they are touching each other
							Point2D.Double c1Vel = new Point2D.Double(c1.velocity.x, c1.velocity.y);
							//flip the velocities
							c1.velocity.x = c2.velocity.x;
							c1.velocity.y = c2.velocity.y;
							c2.velocity.x = c1Vel.x;
							c2.velocity.y = c1Vel.y;
							//make sure we don't flip again
							alreadyUpdatedCircles.add(c2);
							alreadyUpdatedCircles.add(c1);
						}
						
					}
				}
			}
			
		}
		
	}
	
	
}
