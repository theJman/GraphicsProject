package Base;

import java.awt.Component;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

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
import com.jogamp.opengl.util.texture.Texture;
//******************************************************************************
import com.jogamp.opengl.util.texture.TextureIO;

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

	public int whichSkin = 0; // Skin is the look of the field, 0-regular, 1-neon, 3-???
	public boolean reintroduce = false;		// whether or not to reintroduce pucks when they are scored
	public String whichTeam = "none";
	public HashMap<String, Texture> teamlist = new HashMap<String, Texture>();
	public String 	scoreDirection;	// false is right++, true is left++


	public int globR;
		public static boolean globRInc = true;
	public int globG;
		public static boolean globGInc = true;
	public int globB;
		public static boolean globBInc = true;

	public int specialpuckChance = 1;
	public boolean specialpuckExists = false;


	public int leftPowerup = -1;
	public int rightPowerup = -1;
	public int leftEnd = 0;
	public int rightEnd = 0;

	// display mode and loading images
	public boolean displaymode = true;


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

	private GLAutoDrawable draw = null;

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

		addPuck();
//		Puck newPuck = new Puck(this);
//		newPuck.getCenter().x = 0;
//		newPuck.getCenter().y = (-.4 + Math.random()*.8);
//		newPuck.getVelocity().x = -0.005;
//		newPuck.getVelocity().y = -0.0005;
//		circlesInteraction.addCircle(newPuck);
//		wallInteraction.addCircle(newPuck);
//		shapes.add(newPuck);

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

		scoreDirection = "";
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
			if(s instanceof Puck)
			{
				s.getVelocity().x *= 1.5;
				s.getVelocity().y *= 1.5;
			}
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

		if(leftPowerup != -1)
			checkLeftPowerup();
		if(rightPowerup != -1)
			checkRightPowerup();

		//update the interactions
		for(Interaction i : shapeInteractions){
			i.update();
		}

		//update the shapes
		for (int i = 0; i < shapes.size(); i++)
		{
			Shape s = shapes.get(i);
			if(s instanceof Puck && ((Puck) s).scored == true)
			{
				if(((Puck) s).specialpuck) specialpuckExists = false;
				gameLogic.incrementScore(scoreDirection, ((Puck) s).specialpuck);
				circlesInteraction.deleteCircle(s.id);
				wallInteraction.deleteCircle(s.id);
				shapes.remove(i);
				puckcounter -= 1;
				if(reintroduce == true) addPuck();
			}

			s.update(drawable);
		}

		gameLogic.update(drawable);
		canvas.repaint();

		specialPuck();
	}

	private void	render(GLAutoDrawable drawable)
	{
		GL2		gl = drawable.getGL().getGL2();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT);		// Clear the buffer

		if(teamlist.isEmpty())
		{
			draw = drawable;
			loadTeams(drawable);
		}

		//render the shapes
		if(GameLogic.winner)
		{
			int win = -1;
			for(int i = 0; i < shapes.size(); i++)
			{
				if(shapes.get(i) instanceof Field)
					win = i;
				else if(shapes.get(i) instanceof Puck || shapes.get(i) instanceof Circle)
				{
					shapes.get(i).velocity.x = 0.0;
					shapes.get(i).velocity.y = 0.0;
					shapes.get(i).render(drawable);
				}
			}
			shapes.get(win).render(drawable);
		}
		else
		{
			for(Shape s : shapes)
				s.render(drawable);
		}

	}
	//**********************************************************************
	// Private Methods (Scene)
	//**********************************************************************


	//**********************************************************************
	// Utility Methods
	//**********************************************************************
	private void loadTeams(GLAutoDrawable drawable)
	{
		GL2		gl = drawable.getGL().getGL2();

		String[]	list = {"avalanche.png","blues.png","bruins.png","canadiens.png","canes.png","canucks.png",
							"caps.png","coyotes.png","devils.png","ducks.png","flames.png",
							"flyers.png","hawks.png","island.png","jackets.png","jets.png",
							"kings.png","leafs.png","lightning.png","oilers.png","panthers.png",
							"penguins.png","preds.png","rangers.png","sabres.png","senators.png",
							"sharks.png","stars.png","wild.png","wings.png"
							};

		for(String team : list)
		{
			Texture tex = null;
			try
			{
				tex = TextureIO.newTexture(new File(team), true);
				tex.enable(gl);
				tex.bind(gl);
			} catch(IOException ex)
			{
				System.err.println(ex);
			}
			teamlist.put(team,tex);
		}
	}

	private int puckcounter = 0;
	/**
	 * Adds a new puck
	 *
	 */
	public void addPuck(){
		Random rand = new Random();

		Puck newPuck = new Puck(this);
		newPuck.getCenter().x = -.1 + (.1 - -.1) * rand.nextDouble();	// x value inside center circle
		newPuck.getCenter().y = -.1 + (.1 - -.1) * rand.nextDouble();	// y value inside center circle
		newPuck.getVelocity().x = -.005 + (.005 - -.005) * rand.nextDouble();	// random x val
		newPuck.getVelocity().y = -.005 + (.005 - -.005) * rand.nextDouble();	// random y val
		circlesInteraction.addCircle(newPuck);
		wallInteraction.addCircle(newPuck);
		shapes.add(newPuck);
		puckcounter += 1;
	}

	private void specialPuck()
	{
		// This powerup will potentially highlight one puck that will make it worth 2 times the score
		if(Utilities.getChance(specialpuckChance) && !specialpuckExists)
		{
			for(Shape s : shapes)
			{
				if(s instanceof Puck && ((Puck) s).specialpuck == false)
				{
					((Puck) s).specialpuck = true;
					((Puck) s).sparks = true;
					specialpuckExists = true;
					break;
				}
			}
		}
	}

	// TODO
	public void doPowerup(String which)
	{
		if(which.equals("left"))
		{
			if(GameLogic.leftPlayerPowerup.isEmpty()) return;
			else startLeftPowerup(GameLogic.leftPlayerPowerup.pop());
		}
		else if(which.equals("right"))
		{
			if(GameLogic.rightPlayerPowerup.isEmpty()) return;
			else startRightPowerup(GameLogic.rightPlayerPowerup.pop());
		}
	}

	public void doDefense(String which)
	{
		if(which.equals("left"))
		{
			if(GameLogic.leftPlayerDefense.isEmpty()) return;
			else startLeftPowerup(4);
		}
		else if(which.equals("right"))
		{
			if(GameLogic.rightPlayerDefense.isEmpty()) return;
			else startRightPowerup(4);
		}
	}

	public void startLeftPowerup(int powerup)
	{
		switch(powerup)
		{

			case 1:		// freeze opponents mallet powerup for x seconds
				leftPowerup = 1;
				leftEnd = counter + 30;
				rightMallet.stop = true;
				break;

			case 2:		// make oppoents mallet small for x seconds
				leftPowerup = 2;
				leftEnd = counter + 30;
				rightMallet.radius = rightMallet.radius * .25;
				break;

			case 3:		// fire all pucks directly toward the opponent
				for(Shape s : shapes)
				{
					if(s instanceof Puck)
					{
						if(s.center.x < 0 && s.center.y > .4)
						{
							// top left quadrant
							s.velocity.x = .1;
							s.velocity.y = -.035;
						}
						else if(s.center.y > -.5 && s.center.y < .4)
						{
							// mid quadrant
							s.velocity.x = .1;
							s.velocity.y = 0.0;
						}
						else if(s.center.x < 0 && s.center.y < -.4)
						{
							// bottom left quadrant
							s.velocity.x = .1;
							s.velocity.y = .035;
						}
						else if(s.center.x > 0 && s.center.y > .4)
						{
							// top right quadrant
							s.velocity.x = .1;
							s.velocity.y = -.08;
						}
						else if(s.center.x > 0 && s.center.y < -.4)
						{
							// bottom right quadrant
							s.velocity.x = .1;
							s.velocity.y = .08;
						}
					}
				}
				break;

			case 4:
				for(Shape s : shapes)
				{
					if(s instanceof Puck)
					{
						if(s.center.x <= 0.0)
						{
							s.velocity.x = 0.0;
							s.velocity.y = 0.0;
						}
					}
				}
				GameLogic.leftPlayerDefense.pop();
				break;

			case 5:
				for(Shape s : shapes)
				{
					if(s instanceof Puck)
					{
						if(s.center.x <= 0.0)
						{
							s.velocity.x = 0.0;
							s.velocity.y = 0.0;
							// FOR DISPLAY MODE ONLY - DOESN'T POP STACK
						}
					}
				}
				break;
		}
	}

	public void checkLeftPowerup()
	{
		switch(leftPowerup)
		{
			case 1:
				if(counter == leftEnd)
				{
					rightMallet.stop = false;
					leftPowerup = -1;
				}
				break;
			case 2:
				if(counter == leftEnd)
				{
					rightMallet.radius = rightMallet.defaultRadius;
					leftPowerup = -1;
				}
		}
	}

	public void startRightPowerup(int powerup)
	{
		switch(powerup)
		{

			case 1:		// freeze opponents mallet powerup for x seconds
				rightPowerup = 1;
				rightEnd = counter + 30;
				leftMallet.stop = true;
				break;

			case 2:		// make oppoents mallet small for x seconds
				rightPowerup = 2;
				rightEnd = counter + 30;
				leftMallet.radius = leftMallet.radius * .25;
				break;

			case 3:		// fire all pucks directly toward the opponent
				rightPowerup = 3;
				for(Shape s : shapes)
				{
					if(s instanceof Puck)
					{
						if(s.center.x > 0 && s.center.y > .4)
						{
							// top right quadrant
							s.velocity.x = -.1;
							s.velocity.y = -.035;
						}
						else if(s.center.y > -.5 && s.center.y < .4)
						{
							// mid quadrant
							s.velocity.x = -.1;
							s.velocity.y = 0.0;
						}
						else if(s.center.x > 0 && s.center.y < -.4)
						{
							// bottom right quadrant
							s.velocity.x = -.1;
							s.velocity.y = .035;
						}
						else if(s.center.x < 0 && s.center.y > .4)
						{
							// top left quadrant
							s.velocity.x = -.1;
							s.velocity.y = -.08;
						}
						else if(s.center.x < 0 && s.center.y < -.4)
						{
							// bottom left quadrant
							s.velocity.x = -.1;
							s.velocity.y = .08;
						}
					}
				}
				break;

			case 4:
				for(Shape s : shapes)
				{
					if(s instanceof Puck)
					{
						if(s.center.x >= 0.0)
						{
							s.velocity.x = 0.0;
							s.velocity.y = 0.0;
						}
					}
				}
				GameLogic.rightPlayerDefense.pop();
				break;
		}
	}

	public void checkRightPowerup()
	{
		switch(rightPowerup)
		{
			case 1:
				if(counter == rightEnd)
				{
					leftMallet.stop = false;
					rightPowerup = -1;
				}
				break;

			case 2:
				if(counter == rightEnd)
				{
					leftMallet.radius = leftMallet.defaultRadius;
					rightPowerup = -1;
				}
				break;
		}
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

	/**
	 * Subtracts a puck
	 *
	 */
	public void deletePuck(){
		UUID id = null;
		for(int i = shapes.size()-1; i >= 0; i--)
		{
			Shape s = shapes.get(i);
			if(s instanceof Puck)
			{
				id = shapes.get(i).id;
				shapes.remove(i);
				puckcounter -= 1;
				break;
			}
		}

		if(id != null)
		{
			circlesInteraction.deleteCircle(id);
			wallInteraction.deleteCircle(id);
		}
	}

	/**
	 * Simulates the start of a new game
	 */
	public void newGame()
	{
		deleteAllPucks();
		resetMallets();
		resetGameLogic();
		addPuck();
	}

	/**
	 * Resets powerups, scores, and winner
	 */
	public void resetGameLogic()
	{
		GameLogic.winner = false;
		GameLogic.leftPlayerScore = 0;
		GameLogic.rightPlayerScore = 0;
		GameLogic.leftPlayerDefense.clear();
		GameLogic.leftPlayerPowerup.clear();
		GameLogic.rightPlayerDefense.clear();
		GameLogic.rightPlayerPowerup.clear();
	}

	/**
	 * Resets mallets to original position
	 */
	public void resetMallets()
	{
		leftMallet.getCenter().x = -0.6;
		leftMallet.getCenter().y = 0.0;

		rightMallet.getCenter().x = 0.6;
		rightMallet.getCenter().y = 0.0;
	}

	/**
	 * Deletes all pucks from playing field (including interactions)
	 */
	private void deleteAllPucks()
	{
		// remove all pucks
		while(puckcounter > 0) deletePuck();
	}
}



//******************************************************************************
