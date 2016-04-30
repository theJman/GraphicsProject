package Shapes;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import Base.View;

/**
 * 
 */

/**
 * Class for the mallet
 * @author JeremyLittel
 *
 */
public class Mallet extends Circle {

	private final double moveSpeed = .001;
	private final double moveAccel = .0001;
	private final double maxVel = .035;
	protected boolean isLeft;
	
	/**
	 * @param nView
	 */
	public Mallet(View nView, boolean nIsLeft) {
		super(nView);
		mass = 100;
		isLeft = nIsLeft;
	}
	

	//
	//methods for movement
	public void moveUp(){
		//velocity.y = moveSpeed;
		acceleration.y = moveAccel;
	}
	public void moveDown(){
		//velocity.y = -moveSpeed;
		acceleration.y = -moveAccel;

	}
	public void moveLeft(){
		//velocity.x = -moveSpeed;
		acceleration.x = -moveAccel;
	}
	public void moveRight(){
		//velocity.x = moveSpeed;
		acceleration.x = moveAccel;
	}
	public void stopUp(){
		if(velocity.y > 0)
			velocity.y = 0;
		if(acceleration.y > 0)
			acceleration.y = 0;
	}
	public void stopDown(){
		if(velocity.y < 0)
			velocity.y = 0;
		if(acceleration.y < 0)
			acceleration.y = 0;
	}
	public void stopLeft(){
		if(velocity.x < 0)
			velocity.x = 0;
		if(acceleration.x < 0)
			acceleration.x = 0;
	}
	public void stopRight(){
		if(velocity.x > 0)
			velocity.x = 0;
		if(acceleration.x > 0)
			acceleration.x = 0;
	}
	
	//we don't want this circle bouncing
	@Override
	public void setVelocityToInView(Double nVel) {
		//super.setVelocityToInView(nVel);
	}
	@Override
	public void setVelocity(Double v) {
		//super.setVelocity(v);
		acceleration.x = 0;
		acceleration.y = 0;
	}
	
	//update and render
	@Override
	public void update(GLAutoDrawable drawable) {
		super.update(drawable);
		
		//make sure that it stays on it's own side
		if((isLeft && center.x > 0) || (!isLeft && center.x < 0)){
			center.x = 0;
			velocity.x = 0;
			acceleration.x = 0;
		}
		
		//make sure that we aren't going to fast
		if(velocity.x > maxVel){
			velocity.x = maxVel;
			acceleration.x = 0;
		}
		else if(velocity.x < -maxVel){
			velocity.x = -maxVel;
			acceleration.x = 0;
		}
		else if(velocity.y > maxVel){
			velocity.y = maxVel;
			acceleration.y = 0;
		}
		else if(velocity.y < -maxVel){
			velocity.y = -maxVel;
			acceleration.y = 0;
		}

	}
	
	@Override
	public void render(GLAutoDrawable drawable) {
		//override the rendering of a normal circle
		//don't make it hollow
		GL2		gl = drawable.getGL().getGL2();
		
		
		if(view.neonMode)
		{
			//drawCircle(gl, true, radius, View.globR,View.globG,View.globB);
			drawCircle(gl, true, radius, View.globR,View.globG,View.globB);
		}
		else
		{
			drawCircle(gl, true, radius, 0,255,0);
		}
	}

}
