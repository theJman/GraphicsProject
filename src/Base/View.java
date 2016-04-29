package Base;

import java.awt.Component;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;

import Shapes.Circle;
import Shapes.CircleInteraction;
import Shapes.Field;
import Shapes.Interaction;
import Shapes.Mallet;
import Shapes.Puck;
import Shapes.Shape;
import Shapes.Wall;
import Shapes.WallInteraction;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;

//******************************************************************************

/**
 *
 * @author  Team Awesome
 */
public final class View
	implements GLEventListener
{
	//**********************************************************************
	// Public Class Members
	//**********************************************************************

	public static final int				DEFAULT_FRAMES_PER_SECOND = 60;
	private static final DecimalFormat	FORMAT = new DecimalFormat("0.000");
	
	public boolean neonMode;
	

	public static int globR;
		public static boolean globRInc = true;
	public static int globG;
		public static boolean globGInc = true;
	public static int globB;
		public static boolean globBInc = true;

	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private final GLJPanel				canvas;
	private int							w;				// Canvas width
	private int							h;				// Canvas height

	private final KeyHandler			keyHandler;
	private final MouseHandler			mouseHandler;

	private final FPSAnimator			animator;
	private int							counter = 0;	// Frame display counter

	private TextRenderer				renderer;

	private Point2D.Double				origin;		// Current origin coordinates
	private Point2D.Double				cursor;		// Current cursor coordinates
	private ArrayList<Point2D.Double>	points;		// User's polyline points
	private ArrayList<Shape> 			shapes;		//keeps track of all of the shapes on the view
	private ArrayList<Interaction>		shapeInteractions;//keeps track of interactions between shapes
	private CircleInteraction 			circlesInteraction;
	private WallInteraction				wallInteraction; //circles interaction with walls
	//the mallets
	private Mallet						leftMallet;
	private Mallet						rightMallet;
	
	private GameLogic					gameLogic;
	
	private int							frameCounter;
	private int							neonSlowFactor;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public View(GLJPanel canvas)
	{
		this.canvas = canvas;

		// Initialize model
		origin = new Point2D.Double(0.0, 0.0);
		cursor = null;
		points = new ArrayList<Point2D.Double>();

		// Initialize rendering
		canvas.addGLEventListener(this);
		animator = new FPSAnimator(canvas, DEFAULT_FRAMES_PER_SECOND);
		//animator.start();

		// Initialize interaction
		keyHandler = new KeyHandler(this);
		mouseHandler = new MouseHandler(this);

		neonMode = false;
		globR = 100;
		globG = 175;
		globB = 255;

		//initialize shapes
		shapes = new ArrayList<Shape>();

		//interactions
		shapeInteractions = new ArrayList<Interaction>();

		circlesInteraction = new CircleInteraction();

		// Wall
		Wall w1 = new Wall(this);
		w1.getCenter().x = 0;
		w1.getCenter().y = 0;
		w1.getVelocity().x = 0;
		w1.getVelocity().y = 0;

		//wall interactions
		wallInteraction = new WallInteraction(w1);

		// Playing field
		Field field = new Field(this);
		field.getCenter().x = 0;
		field.getCenter().y = 0;
		field.getVelocity().x = 0;
		field.getVelocity().y = 0;

		shapes.add(field);

		shapes.add(w1);

		for(int i=0; i < Application.numCircles; i++)
		{
			Puck newPuck = new Puck(this);
			newPuck.getCenter().x = 0;
			newPuck.getCenter().y = (-.4 + Math.random()*.8);
			newPuck.getVelocity().x = -0.005;
			newPuck.getVelocity().y = -0.0005;
			circlesInteraction.addCircle(newPuck);
			wallInteraction.addCircle(newPuck);
			shapes.add(newPuck);
		}

		/*for(int i = 0; i < 2; i++){
			//create an invisible puck:)
			InvisiblePuck invisiblePuck = new InvisiblePuck(this);
			invisiblePuck.getCenter().x = (Math.random()*1.3)-1;
			invisiblePuck.getCenter().y = (Math.random()*1.3)-1;
			invisiblePuck.getVelocity().x = -0.005;
			invisiblePuck.getVelocity().y = -0.0005;
			circlesInteraction.addCircle(invisiblePuck);
			wallInteraction.addCircle(invisiblePuck);
			shapes.add(invisiblePuck);
		}*/


		//init mallets
		leftMallet = new Mallet(this,true);
		rightMallet = new Mallet(this,false);

		shapes.add(leftMallet);
		shapes.add(rightMallet);
		circlesInteraction.addCircle(leftMallet);
		circlesInteraction.addCircle(rightMallet);
		wallInteraction.addCircle(leftMallet);
		wallInteraction.addCircle(rightMallet);
		leftMallet.getCenter().x = -0.6;
		rightMallet.getCenter().x = 0.6;


		//test shape
		/*Circle c1 = new Circle(this);
		c1.getCenter().x = -0.8;
		c1.getVelocity().x = -0.005;
		c1.getVelocity().y = 0.0005;
		Circle c2 = new Circle(this);
		c2.getVelocity().x = -0.005;
		c2.getVelocity().y = 0.00005;
		circlesInteraction.addCircle(c2);
		circlesInteraction.addCircle(c1);*/

		//add to shapes
		/*shapes.add(c1);
		shapes.add(c2);*/

		//add to interactions
		shapeInteractions.add(circlesInteraction);
		shapeInteractions.add(wallInteraction);

		frameCounter 	= 0;
		neonSlowFactor 	= 2;
		
		gameLogic = new GameLogic(this);
	}

	//**********************************************************************
	// Getters and Setters
	//**********************************************************************

	public int	getWidth()
	{
		return w;
	}

	public int	getHeight()
	{
		return h;
	}

	public Mallet getLeftMallet(){
		return leftMallet;
	}
	public Mallet getRightMallet(){
		return rightMallet;
	}

	public Point2D.Double	getOrigin()
	{
		return new Point2D.Double(origin.x, origin.y);
	}

	public void		setOrigin(Point2D.Double origin)
	{
		this.origin.x = origin.x;
		this.origin.y = origin.y;
		canvas.repaint();
	}

	public Point2D.Double	getCursor()
	{
		return cursor;
	}

	public void		setCursor(Point2D.Double cursor)
	{
		this.cursor = cursor;
		canvas.repaint();
	}

	public void		clear()
	{
		points.clear();
		canvas.repaint();
	}


	//**********************************************************************
	// Mouse movement
	//**********************************************************************

	public void mousePressed(Point2D.Double p){
		for (Shape s : shapes){
			s.getVelocity().x *= 1.5;
			s.getVelocity().y *= 1.5;
		}
	}

	public void mouseDrag(Point2D.Double p){

	}

	public void mouseRelease(Point2D.Double p){

	}


	//**********************************************************************
	// Public Methods
	//**********************************************************************

	public Component	getComponent()
	{
		return canvas;
	}

	//**********************************************************************
	// Override Methods (GLEventListener)
	//**********************************************************************

	@Override
	public void		init(GLAutoDrawable drawable)
	{
		w = drawable.getSurfaceWidth();
		h = drawable.getSurfaceHeight();

		renderer = new TextRenderer(new Font("Monospaced", Font.PLAIN, 12),
									true, true);
	}

	@Override
	public void		dispose(GLAutoDrawable drawable)
	{
		renderer = null;
	}

	@Override
	public void		display(GLAutoDrawable drawable)
	{
		frameCounter++;

		if(frameCounter % neonSlowFactor == 0)
		{
			updateColor();
		}

		//System.out.println("R: " + globR + " G: " + globG + " B: " + globB);
		updateProjection(drawable);

		update(drawable);
		render(drawable);
	}

	@Override
	public void		reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
	{
		this.w = w;
		this.h = h;
	}

	//**********************************************************************
	// Private Methods (Viewport)
	//**********************************************************************

	private void	updateProjection(GLAutoDrawable drawable)
	{
		GL2		gl = drawable.getGL().getGL2();
		GLU		glu = new GLU();

		float	xmin = (float)(origin.x - 1.0);
		float	xmax = (float)(origin.x + 1.0);
		float	ymin = (float)(origin.y - 1.0);
		float	ymax = (float)(origin.y + 1.0);

		gl.glMatrixMode(GL2.GL_PROJECTION);			// Prepare for matrix xform
		gl.glLoadIdentity();						// Set to identity matrix
		glu.gluOrtho2D(xmin, xmax, ymin, ymax);		// 2D translate and scale
	}

	//**********************************************************************
	// Private Methods (Rendering)
	//**********************************************************************

	private void	update(GLAutoDrawable drawable)
	{
		counter++;								// Counters are useful, right?

		//update the interactions
		for(Interaction i : shapeInteractions){
			i.update();
		}

		//update the shapes
		for (Shape s : shapes){
			s.update(drawable);
		}

		gameLogic.update(drawable);
		canvas.repaint();

	}

	private void	render(GLAutoDrawable drawable)
	{
		GL2		gl = drawable.getGL().getGL2();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT);		// Clear the buffer

		//render all of the shapes
		for (Shape s : shapes) {
			s.render(drawable);
		}
		
		gameLogic.render(drawable);

//		drawBounds(gl);							// Unit bounding box
//		drawAxes(gl);							// X and Y axes
//		drawCursor(gl);							// Crosshairs at mouse location
//		drawCursorCoordinates(drawable);		// Draw some text
//		drawPolyline(gl);						// Draw the user's sketch
	}

	//**********************************************************************
	// Private Methods (Scene)
	//**********************************************************************

	/**
	 * Adds a new puck
	 *
	 * @author TreMcP
	 */
	public void addCircle(){
		Circle newPuck = new Circle(this);
		newPuck.getCenter().x = 0;
		newPuck.getCenter().y = -.4 + Math.random()*.8;
		newPuck.getVelocity().x = -0.005;
		newPuck.getVelocity().y = -0.0005;
		circlesInteraction.addCircle(newPuck);
		wallInteraction.addCircle(newPuck);
		shapes.add(newPuck);
	}

	/**
	 * Subtracts a puck
	 *
	 * @author TreMcP
	 */
	public void deleteCircle(){
		circlesInteraction.deleteCircle();
		shapes.remove(shapes.size()-1);
	}

	private void updateColor(){

		if(globR >= 255)
		{
			globRInc = false;
		}
		else if(globR <= 100)
		{
			globRInc = true;
		}

		if(globRInc == true)
		{
			globR+=(Math.random() * 5);
		}
		else
		{
			globR-=(Math.random() * 5);
		}

		if(globG >= 255)
		{
			globGInc = false;
		}
		else if(globG <= 100)
		{
			globGInc = true;
		}

		if(globGInc == true)
		{
			globG+=(Math.random() * 5);
		}
		else
		{
			globG-=(Math.random() * 5);
		}

		if(globB >= 255)
		{
			globBInc = false;
		}
		else if(globB <= 100)
		{
			globBInc = true;
		}

		if(globBInc == true)
		{
			globB+=(Math.random() * 5);
		}
		else
		{
			globB-=(Math.random() * 5);
		}
	}
}



//******************************************************************************
