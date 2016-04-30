package Shapes;


import Base.View;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

/**
 * This class will just be the build of the playing field. It will be purely for aesthetic purposes.
 * Will not interact with collisions or the game.
 * @author kobypascual
 *
 */
public class Field extends Shape {

	protected double percentHeight = 1.0;
	protected double percentWidth = 1.0;
	protected double radius = (percentHeight*percentWidth)/.00275;

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
		if(view.neonMode)
		{
			drawField(gl,0,0,0);
		}
		else
		{
			drawField(gl,255,255,255);
		}
		drawLines(gl);
		drawCircles(gl);
		drawGoalBoundary(gl);
		//loadImageCenterIce(gl);
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
		// TODO deal with line sizes between Operating systems
		// Draw the lines
		gl.glLineWidth(20);
		gl.glBegin(GL2.GL_LINES);
			// Center line
			if(view.neonMode)
			{
				setColor(gl,View.globR,View.globG,View.globB);
			}
			else
			{
				setColor(gl,255,0,0);
			}
			gl.glVertex2d(convertWidth(view.getWidth()*0), convertHeight(view.getHeight()*percentHeight*.9));
			gl.glVertex2d(convertWidth(view.getWidth()*0), convertHeight(view.getHeight()*-percentHeight*.9));

			// Left and right lines

			if(view.neonMode)
			{
				setColor(gl,View.globR,View.globG,View.globB);
			}
			else
			{
				setColor(gl,68,214,243);
			}

			gl.glVertex2d(convertWidth(view.getWidth()*-(1.0/3)*percentWidth), convertHeight(view.getHeight()*percentHeight*.9));
			gl.glVertex2d(convertWidth(view.getWidth()*-(1.0/3)*percentWidth), convertHeight(view.getHeight()*-percentHeight*.9));
			gl.glVertex2d(convertWidth(view.getWidth()*(1.0/3)*percentWidth), convertHeight(view.getHeight()*percentHeight*.9));
			gl.glVertex2d(convertWidth(view.getWidth()*(1.0/3)*percentWidth), convertHeight(view.getHeight()*-percentHeight*.9));
		gl.glEnd();
		gl.glLineWidth(1);
	}

	private void drawCircles(GL2 gl){

		// TODO circle size is currently hard coded. need to do it as a percentage with the window size

		double x = convertWidth(view.getWidth()*(1.0/1.75));
		double y = convertHeight(view.getHeight()*(1.0/2));
		if(view.neonMode)
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
		gl.glLineWidth(10);
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

		if(view.neonMode)
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
			drawLinesInCircles(gl, radius*.1, centerX, centerY);
		}
		gl.glLineWidth(1);

	}

	private void drawLinesInCircles(GL2 gl, double radius, double centerX, double centerY){
		gl.glBegin(GL2.GL_LINES);
		gl.glLineWidth(.5f);
			// Top right
			gl.glVertex2d(centerX + convertWidth(radius), centerY + convertHeight(radius));
			gl.glVertex2d(centerX + convertWidth(radius), centerY + 3*convertHeight(radius));
			gl.glVertex2d(centerX + convertWidth(radius), centerY + convertHeight(radius));
			gl.glVertex2d(centerX + 3*convertWidth(radius), centerY + convertHeight(radius));

			// Top left
			gl.glVertex2d(centerX - convertWidth(radius), centerY + convertHeight(radius));
			gl.glVertex2d(centerX - convertWidth(radius), centerY + 3*convertHeight(radius));
			gl.glVertex2d(centerX - convertWidth(radius), centerY + convertHeight(radius));
			gl.glVertex2d(centerX - 3*convertWidth(radius), centerY + convertHeight(radius));

			// Bottom right
			gl.glVertex2d(centerX + convertWidth(radius), centerY - convertHeight(radius));
			gl.glVertex2d(centerX + convertWidth(radius), centerY - 3*convertHeight(radius));
			gl.glVertex2d(centerX + convertWidth(radius), centerY - convertHeight(radius));
			gl.glVertex2d(centerX + 3*convertWidth(radius), centerY - convertHeight(radius));

			// Bottom left
			gl.glVertex2d(centerX - convertWidth(radius), centerY - convertHeight(radius));
			gl.glVertex2d(centerX - convertWidth(radius), centerY - 3*convertHeight(radius));
			gl.glVertex2d(centerX - convertWidth(radius), centerY - convertHeight(radius));
			gl.glVertex2d(centerX - 3*convertWidth(radius), centerY - convertHeight(radius));
		gl.glEnd();
		gl.glLineWidth(1);
	}

	private void loadImageCenterIce(GL2 gl){
		// TODO I cheated and used 2d graphics to add this image.. turns out adding a png is more difficult
		//Image logo = Utilities.fullyLoadImage("wild_logo.png");
	}



	private void drawGoalBoundary(GL2 gl){
		double x = convertWidth(view.getWidth()*percentWidth);
		double y = convertHeight(view.getHeight()*percentHeight*(1.0/3));

		//Border of rectangle
		if(view.neonMode)
		{
			setColor(gl,View.globR,View.globG,View.globB);
		}
		else
		{
			setColor(gl, 255, 0, 0, 255);
		}

		gl.glBegin(GL2.GL_POLYGON);
			gl.glVertex2d(x*.9, y);
			gl.glVertex2d(x*.9 - x*(1.0/9), y);
			gl.glVertex2d(x*.9 - x*(1.0/9), -y);
			gl.glVertex2d(x*.9, -y);
		gl.glEnd();

		gl.glBegin(GL2.GL_POLYGON);
			gl.glVertex2d(-x*.9, y);
			gl.glVertex2d(-x*.9 + x*(1.0/9), y);
			gl.glVertex2d(-x*.9 + x*(1.0/9), -y);
			gl.glVertex2d(-x*.9, -y);
		gl.glEnd();

		// Inside portion of rectangle
		if(view.neonMode)
		{
			setColor(gl,0,0,0);
		}
		else
		{
			setColor(gl, 68, 214, 243, 200);
		}

		gl.glBegin(GL2.GL_POLYGON);
			gl.glVertex2d(x*.9, y*.95);
			gl.glVertex2d((x*.9 - x*(1.0/9)*.95), y*.95);
			gl.glVertex2d((x*.9 - x*(1.0/9)*.95), -y*.95);
			gl.glVertex2d(x*.9, -y*.95);
		gl.glEnd();

		gl.glBegin(GL2.GL_POLYGON);
			gl.glVertex2d(-x*.9, y*.95);
			gl.glVertex2d((-x*.9 + x*(1.0/9)*.95), y*.95);
			gl.glVertex2d((-x*.9 + x*(1.0/9)*.95), -y*.95);
			gl.glVertex2d(-x*.9, -y*.95);
		gl.glEnd();
	}

}