package Shapes;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import Base.Utilities;

/**
 * Class to manage interactions between circles
 * @author JeremyLittel
 *
 */
public class CircleInteraction implements Interaction{
	public ArrayList<Circle> circles;//list of all of the circles

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

	public void deleteCircle(UUID id){
		for(int i = circles.size()-1; i >= 0; i--)
		{
			if(circles.get(i).id == id)
			{
				circles.remove(i);
				break;
			}
		}
		circles.trimToSize();
	}

	/**
	 * Performs updates on circles if they need to interact with each other
	 */
	@Override
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

							//Adds a beep on collision. We can make this a better sound later.
							Toolkit.getDefaultToolkit().beep();

							//now move the circles so that they are not touching
							boolean stillTouching = true;
							while(stillTouching){

								Point2D.Double vecBetween = new Point2D.Double(center2.x-center1.x, center2.y-center1.y);
//								System.out.println(center1 + " : "+ center2);
//								System.out.println(vecBetween);
//								System.out.println(c1 + " : "+ c2);

								double vecMag = Edge.getMagnitude(vecBetween);
								double badDistance = Math.abs(c2.getCenterNotInView().distance(c1.getCenterNotInView()) - c1.getRadius()/2 + c2.getRadius()/2);
//								System.out.println(badDistance);

								vecBetween.x /= badDistance;
								vecBetween.y /= badDistance;
								center2.x += vecBetween.x;
								center2.y += vecBetween.y;
								c1.setCenterNotInView(center1);
								c2.setCenterNotInView(center2);

								//check if we need to keep shifting
								stillTouching = c2.getCenterNotInView().distance(c1.getCenterNotInView()) <= c1.getRadius()/2 + c2.getRadius()/2;
								if(!stillTouching){
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
							double v1n = dotProduct(nUnit, v1);
							double v1t = dotProduct(tUnit, v1);
							double v2n = dotProduct(nUnit, v2);
							double v2t = dotProduct(tUnit, v2);
							//dummy mass in case we ever want to change these
							double m1 = c1.mass, m2 = c2.mass;
							//calculate new normal velocities
							double v1nP = (v1n*(m1-m2) + 2*m2*v2n)/(m1+m2);
							double v2nP = (v2n*(m2-m1) + 2*m1*v1n)/(m1+m2);

							double v1tP = v1t;
							double v2tP = v2t;

							//calculate final unit and tangent velocities

							Point2D.Double v1nPV = Edge.scalerMult(nUnit, v1nP);
							Point2D.Double v1tPV = Edge.scalerMult(tUnit, v1tP);
							Point2D.Double v2nPV = Edge.scalerMult(nUnit, v2nP);
							Point2D.Double v2tPV = Edge.scalerMult(tUnit, v2tP);

							//final velocities
							c1.setVelocityToInView(vectorAdd(v1nPV, v1tPV));
							c2.setVelocityToInView(vectorAdd(v2nPV, v2tPV));


							//we don't need to do anything with these cicles again on this bounce
							alreadyUpdatedCircles.add(c2);
							alreadyUpdatedCircles.add(c1);
							c1.sparks = true;
							c2.sparks = true;

							double tempr = c1.red;
							double tempg = c1.green;
							double tempb = c1.blue;

							c1.red = c2.red;
							c1.green = c2.green;
							c1.blue = c1.blue;

							c2.red = tempr;
							c2.green = tempg;
							c2.blue = tempb;

							if(Utilities.getChance(5))
							{
								c1.velocity.x *= 1.5;
								c2.velocity.x *= 1.5;
							}
							if(Utilities.getChance(5))
							{
								c1.velocity.y *= 1.5;
								c2.velocity.y *= 1.5;
							}
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
	public double dotProduct(Point2D.Double v1, Point2D.Double v2){
		return v1.x*v2.x + v1.y*v2.y;
	}

	public Point2D.Double vectorAdd(Point2D.Double v1, Point2D.Double v2){
		return new Point2D.Double(v1.x+v2.x, v1.y+v2.y);
	}



}
