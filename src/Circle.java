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

	private double width = 200;
	private double height = 200;
	
	/**
	 * @param nView
	 */
	public Circle(View nView) {
		super(nView);
		velocity.x = 0.005;
		velocity.y = 0.003;
	}
	
	public void update(GLAutoDrawable drawable){
		super.update(drawable);
		if (center.x + convertWidth(width/2) > 1.0 || center.x - convertWidth(width/2) < -1.0) {
			velocity.x *= -1;
		}
		if (center.y + convertHeight(height/2) > 1.0 || center.y - convertHeight(height/2) < -1.0) {
			velocity.y *= -1;
		}
	}
	
	@Override
	public void render(GLAutoDrawable drawable){
		GL2		gl = drawable.getGL().getGL2();
		drawCircle(gl);
	}
	
	
	//private methods
	private void drawCircle(GL2 gl){
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		setColor(gl, 255, 0, 0);
		gl.glVertex2d(center.x, center.y);
		
		for (int i=0; i<=32; i++)
		{
			double a = (2.0 * Math.PI) * (i / 32.0);
			double x = center.x + convertWidth(width/2) * Math.cos(a);
			double y = center.y + convertHeight(height/2) * Math.sin(a);
			gl.glVertex2d(x,y);
		}

		gl.glEnd();
	}

}
