package Base;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import Shapes.Circle;

//******************************************************************************

/**
 *
 * @author  Team Awesome
 */
public final class KeyHandler extends KeyAdapter
{
	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private final View	view;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public KeyHandler(View view)
	{
		this.view = view;

		Component	component = view.getComponent();

		component.addKeyListener(this);
	}

	//**********************************************************************
	// Override Methods (KeyListener)
	//**********************************************************************

	public void		keyPressed(KeyEvent e)
	{
		Point2D.Double	p = view.getOrigin();
		double			a = (Utilities.isShiftDown(e) ? 0.01 : 0.1);

		switch (e.getKeyCode())
		{	
			case KeyEvent.VK_N:
				view.neonMode = !view.neonMode; break;
				
			case KeyEvent.VK_MINUS:
				view.deleteCircle();	break;
				
			case KeyEvent.VK_EQUALS:
				view.addCircle();	break;

			case KeyEvent.VK_DELETE:
				view.clear();
				return;
				
				//left mallet movement
			case KeyEvent.VK_W:
				view.getLeftMallet().moveUp();
				break;
			case KeyEvent.VK_S:
				view.getLeftMallet().moveDown();
				break;
			case KeyEvent.VK_A:
				view.getLeftMallet().moveLeft();
				break;
			case KeyEvent.VK_D:
				view.getLeftMallet().moveRight();
				break;
				
				//right mallet movement
			case KeyEvent.VK_UP:
				view.getRightMallet().moveUp();
				break;
			case KeyEvent.VK_DOWN:
				view.getRightMallet().moveDown();
				break;
			case KeyEvent.VK_LEFT:
				view.getRightMallet().moveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				view.getRightMallet().moveRight();
				break;
		}

		view.setOrigin(p);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		
		switch (e.getKeyCode()) {
		//left mallet movement
		case KeyEvent.VK_W:
			view.getLeftMallet().stopUp();
			break;
		case KeyEvent.VK_S:
			view.getLeftMallet().stopDown();
			break;
		case KeyEvent.VK_A:
			view.getLeftMallet().stopLeft();
			break;
		case KeyEvent.VK_D:
			view.getLeftMallet().stopRight();
			break;
			
			//right mallet.stopment
		case KeyEvent.VK_UP:
			view.getRightMallet().stopUp();
			break;
		case KeyEvent.VK_DOWN:
			view.getRightMallet().stopDown();
			break;
		case KeyEvent.VK_LEFT:
			view.getRightMallet().stopLeft();
			break;
		case KeyEvent.VK_RIGHT:
			view.getRightMallet().stopRight();
			break;
		}
	}
}

//******************************************************************************
