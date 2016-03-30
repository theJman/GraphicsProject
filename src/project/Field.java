package project;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

/**
 * This class will just be the build of the playing field. It will be purely for aesthetic purposes.
 * Will not interact with collisions or the game.
 * @author kobypascual
 *
 */
public class Field extends Shape{

	protected double percentHeight = .95;
	protected double percentWidth = .95;

	/**
	 *
	 * @param nView
	 */
	public Field(View nView){
		super(nView);
	}

	@Override
	public void update(GLAutoDrawable drawable){
		super.update(drawable);
	}

	@Override
	public void render(GLAutoDrawable drawable){
		GL2		gl = drawable.getGL().getGL2();
		drawField(gl,255,255,255);
		drawLines(gl);
	}

	private void drawField(GL2 gl, double red, double green, double blue){
		drawField(gl,red, green, blue, 255.0);
	}

	private void drawField(GL2 gl, double red, double green, double blue, double alpha){
		// Draw the ice
		gl.glBegin(GL2.GL_POLYGON);
			setColor(gl, red, green, blue, alpha);
			gl.glVertex2d(convertWidth(view.getWidth()*-percentWidth),  convertHeight(view.getHeight()*percentHeight));
			gl.glVertex2d(convertWidth(view.getWidth()*percentWidth), convertHeight(view.getHeight()*percentHeight));
			gl.glVertex2d(convertWidth(view.getWidth()*percentWidth), convertHeight(view.getHeight()*-percentHeight));
			gl.glVertex2d(convertWidth(view.getWidth()*-percentWidth), convertHeight(view.getHeight()*-percentHeight));
		gl.glEnd();
	}

	private void drawCircles(){
		// TODO note the circles will need to be drawn first so that the middle line does
		// not run right through the center

	}

	private void drawLines(GL2 gl)
	{
		// TODO deal with line sizes
		// Draw the lines
		gl.glLineWidth(50f);
		gl.glBegin(GL2.GL_LINES);
			// Center line
			setColor(gl,255,0,0);
			gl.glVertex2d(convertWidth(view.getWidth()*0), convertHeight(view.getHeight()*percentHeight));
			gl.glVertex2d(convertWidth(view.getWidth()*0), convertHeight(view.getHeight()*-percentHeight));

			// TODO some math issues here, I'm wanting to divide this into thirds essentially
			// TODO it's late and i'm not getting the math right.
			// Left and right lines
			setColor(gl,0,0,255);
			gl.glVertex2d(convertWidth(view.getWidth()*-(1.0/3)*percentWidth), convertHeight(view.getHeight()*percentHeight));
			gl.glVertex2d(convertWidth(view.getWidth()*-(1.0/3)*percentWidth), convertHeight(view.getHeight()*-percentHeight));
			gl.glVertex2d(convertWidth(view.getWidth()*(1.0/3)*percentWidth), convertHeight(view.getHeight()*percentHeight));
			gl.glVertex2d(convertWidth(view.getWidth()*(1.0/3)*percentWidth), convertHeight(view.getHeight()*-percentHeight));
		gl.glEnd();
		gl.glLineWidth(1);
	}

}
