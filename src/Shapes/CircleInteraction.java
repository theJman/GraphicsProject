package Shapes;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Class to manage interactions between circles
 * @author JeremyLittel
 *
 */
public class CircleInteraction implements Interaction{
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
			c1.decrementCantBounceTick();//decrement all cant bounce ticks
			if(!alreadyUpdatedCircles.contains(c1) && c1.cantBounceTick == 0){
				//havent updated yet
				//check if it touches another
				for (Circle c2 : circles){
					if(c1 != c2 && !alreadyUpdatedCircles.contains(c2) && c2.cantBounceTick == 0){
						//circles are different and the second has yet to have an interaction
						//find out if the are touching
						Point2D.Double center1 = c1.getCenterNotInView();
						Point2D.Double center2 = c2.getCenterNotInView();
						
						//find distance between
						double distance = center1.distance(center2);

						if(distance <= c1.getRadius()/2 + c2.getRadius()/2){
							//now move the circles so that they are not touching
							boolean stillTouching = true;
							while(stillTouching){
								
								Point2D.Double vecBetween = new Point2D.Double(center2.x-center1.x, center2.y-center1.y);
								System.out.println(center1 + " : "+ center2);
								System.out.println(vecBetween);
								System.out.println(c1 + " : "+ c2);

								double vecMag = Edge.getMagnitude(vecBetween);
								double badDistance = Math.abs(c2.getCenterNotInView().distance(c1.getCenterNotInView()) - c1.getRadius()/2 + c2.getRadius()/2);
								System.out.println(badDistance);

								vecBetween.x /= badDistance;
								vecBetween.y /= badDistance;
								center2.x += vecBetween.x;
								center2.y += vecBetween.y;
								System.out.println("shifting: "+center2);
								System.out.println(c2.center);
								c1.setCenterNotInView(center1);
								c2.setCenterNotInView(center2);
								System.out.println(c2.center);

								//check if we need to keep shifting
								stillTouching = c2.getCenterNotInView().distance(c1.getCenterNotInView()) <= c1.getRadius()/2 + c2.getRadius()/2;
								if(!stillTouching){
									System.out.println("Not still touching");
								}
							}
							
							
							//calculate new velocities
							//normal unit vector
							Point2D.Double nUnit = new Point2D.Double(center2.x-center1.x, center2.y-center1.y);
							double normalMag = Math.sqrt(nUnit.x*nUnit.x + nUnit.y*nUnit.y);
							nUnit.x = nUnit.x/normalMag;
							nUnit.y = nUnit.y/normalMag;
							//tangent unit vector
							Point2D.Double tUnit = new Point2D.Double(-nUnit.y, nUnit.x);
							//velocities
							Point2D.Double v1 = new Point2D.Double(c1.getVelocityNotInView().x, c1.getVelocityNotInView().y);
							Point2D.Double v2 = new Point2D.Double(c2.getVelocityNotInView().x, c2.getVelocityNotInView().y);
							//velocity unit and tangent vectors
							Point2D.Double v1n = dotProduct(nUnit, v1);
							Point2D.Double v1t = dotProduct(tUnit, v1);
							Point2D.Double v2n = dotProduct(nUnit, v2);
							Point2D.Double v2t = dotProduct(tUnit, v2);
							//dummy mass in case we ever want to change these
							double m1 = 10, m2 = 10;
							//calculate new normal velocities
							Point2D.Double v1nCopy = dotProduct(nUnit, v1);
							v1n.x = (v1n.x*(m1-m2) + 2*m2*v2n.x)/(m1+m2);
							v1n.y = (v1n.y*(m1-m2) + 2*m2*v2n.y)/(m1+m2);

							v2n.x = (v2n.x*(m2-m1) + 2*m1*v1nCopy.x)/(m1+m2);
							v2n.y = (v2n.y*(m2-m1) + 2*m1*v1nCopy.y)/(m1+m2);
							
							//calculate final unit and tangent velocities
							v1n = dotProduct(v1n, nUnit);
							v1t = dotProduct(v1t, tUnit);
							v2n = dotProduct(v2n, nUnit);
							v2t = dotProduct(v2t, tUnit);
							
							//final velocities
							c1.setVelocityToInView(vectorAdd(v1n, v1t));
							c2.setVelocityToInView(vectorAdd(v2n, v2t));
							
							//use the cantBounceTick to prevent sticking together
//							c1.cantBounceTick = 40;
//							c2.cantBounceTick = 40;
							
							//we don't need to do anything with this cicles again on this bounce
							alreadyUpdatedCircles.add(c2);
							alreadyUpdatedCircles.add(c1);
							/*
							c1.velocity.x = 0;
							c2.velocity.x = 0;
							c1.velocity.y = 0;
							c2.velocity.y = 0;
							c1.cantBounceTick = 400000;
							c2.cantBounceTick = 400000;
							*/
						}
					
						
					}
				}
			}
			
		}
		
	}
	
	/**
	 * Gets the dot product of two vectors
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Point2D.Double dotProduct(Point2D.Double v1, Point2D.Double v2){
		return new Point2D.Double(v1.x*v2.x, v1.y*v2.y);
	}
	
	public Point2D.Double vectorAdd(Point2D.Double v1, Point2D.Double v2){
		return new Point2D.Double(v1.x+v2.x, v1.y+v2.y);
	}
	
	
	
}
