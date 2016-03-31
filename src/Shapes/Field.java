package Shapes;


import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import Base.View;

/**
 * This class will just be the build of the playing field. It will be purely for aesthetic purposes.
 * Will not interact with collisions or the game.
 * @author kobypascual
 *
 */
public class Field extends Shape{

	protected double percentHeight = 1;
	protected double percentWidth = 1;
	protected double radius = 300;

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
		if(View.neonMode)
		{
			drawField(gl,0,0,0);
		}
		else
		{
			drawField(gl,255,255,255);
		}
		drawLines(gl);
		drawCircles(gl);
		drawLinesInCircles(gl);
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

	private void drawLines(GL2 gl)
	{
		// TODO deal with line sizes
		// Draw the lines
		gl.glLineWidth(50f);
		gl.glBegin(GL2.GL_LINES);
			// Center line
			if(View.neonMode)
			{
				setColor(gl,View.globR,View.globG,View.globB);
			}
			else
			{
				setColor(gl,255,0,0);
			}
			gl.glVertex2d(convertWidth(view.getWidth()*0), convertHeight(view.getHeight()*percentHeight));
			gl.glVertex2d(convertWidth(view.getWidth()*0), convertHeight(view.getHeight()*-percentHeight));

			// TODO some math issues here, I'm wanting to divide this into thirds essentially
			// TODO it's late and i'm not getting the math right.
			// Left and right lines
			if(View.neonMode)
			{
				setColor(gl,View.globR,View.globG,View.globB);
			}
			else
			{
				setColor(gl,68,214,243);
			}
			gl.glVertex2d(convertWidth(view.getWidth()*-(1.0/3)*percentWidth), convertHeight(view.getHeight()*percentHeight));
			gl.glVertex2d(convertWidth(view.getWidth()*-(1.0/3)*percentWidth), convertHeight(view.getHeight()*-percentHeight));
			gl.glVertex2d(convertWidth(view.getWidth()*(1.0/3)*percentWidth), convertHeight(view.getHeight()*percentHeight));
			gl.glVertex2d(convertWidth(view.getWidth()*(1.0/3)*percentWidth), convertHeight(view.getHeight()*-percentHeight));
		gl.glEnd();
		gl.glLineWidth(1);
	}

	private void drawCircles(GL2 gl){
		// TODO circle size is currently hard coded. need to do it as a percentage with the window size

		double x = convertWidth(view.getWidth()*(1.0/1.75));
		double y = convertHeight(view.getHeight()*(1.0/2));
		if(View.neonMode)
		{
			drawCircle(gl,radius,0,0,View.globR,View.globG,View.globB);		// Center circle
			drawCircle(gl,radius*.7,-x,y,View.globR,View.globG,View.globB);		// Left top circle
			drawCircle(gl,radius*.7,x,y,View.globR,View.globG,View.globB);		// Right top circle
			drawCircle(gl,radius*.7,-x,-y,View.globR,View.globG,View.globB);		// Left bottom circle
			drawCircle(gl,radius*.7,x,-y,View.globR,View.globG,View.globB);		// Right bottom circle
		}
		else
		{
			drawCircle(gl,radius,0,0,255,0,0);		// Center circle
			drawCircle(gl,radius*.7,-x,y,255,0,0);		// Left top circle
			drawCircle(gl,radius*.7,x,y,255,0,0);		// Right top circle
			drawCircle(gl,radius*.7,-x,-y,255,0,0);		// Left bottom circle
			drawCircle(gl,radius*.7,x,-y,255,0,0);		// Right bottom circle
		}
		

	}

	private void drawCircle(GL2 gl, double radius, double centerX, double centerY, double r, double g, double b){
		drawCircle(gl, radius, centerX, centerY, r, g, b, 255.0);
	}

	private void drawCircle(GL2 gl, double radius, double centerX, double centerY, double r, double g, double b, double alpha){
		// Color portion of the circle
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		setColor(gl, r, g, b, alpha);
		gl.glVertex2d(centerX,centerY);
		for (int i=0; i<=32; i++)
		{
			double a = (2.0 * Math.PI) * (i / 32.0);
			double x = centerX + convertWidth(radius) * Math.cos(a);
			double y = centerY + convertHeight(radius) * Math.sin(a);
			gl.glVertex2d(x,y);
		}
		gl.glEnd();

		// White it out
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		if(View.neonMode)
		{
			setColor(gl,0,0,0);
		}
		else
		{
			setColor(gl,255,255,255);
		}
		gl.glVertex2d(centerX, centerY);
		for (int i=0; i<=32; i++)
		{
			double a = (2.0 * Math.PI) * (i / 32.0);
			double x = centerX + convertWidth(radius*.9) * Math.cos(a);
			double y = centerY + convertHeight(radius*.9) * Math.sin(a);
			gl.glVertex2d(x,y);
		}
		gl.glEnd();

		if(centerX != 0){
			// Inner circle in outer circles
			// Color portion of the circle
			gl.glBegin(GL2.GL_TRIANGLE_FAN);
			setColor(gl, r, g, b, alpha);
			gl.glVertex2d(centerX,centerY);
			for (int i=0; i<=32; i++)
			{
				double a = (2.0 * Math.PI) * (i / 32.0);
				double x = centerX + convertWidth(radius*.1) * Math.cos(a);
				double y = centerY + convertHeight(radius*.1) * Math.sin(a);
				gl.glVertex2d(x,y);
			}
			gl.glEnd();
		}


	}

	private void drawLinesInCircles(GL2 gl){

	}

	private void loadImageCenterIce(GL2 gl){

	}

}
