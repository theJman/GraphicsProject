package Base;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

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

	@Override
	public void		keyPressed(KeyEvent e)
	{
		Point2D.Double	p = view.getOrigin();
		double			a = (Utilities.isShiftDown(e) ? 0.01 : 0.1);

		switch (e.getKeyCode())
		{
			case KeyEvent.VK_N:
				if(view.whichSkin == 0)
					view.whichSkin = 1;
				else if(view.whichSkin == 1)
					view.whichSkin = 0;
				break;

			case KeyEvent.VK_MINUS:
				view.deletePuck();	break;

			case KeyEvent.VK_EQUALS:
				view.addPuck();	break;

			case KeyEvent.VK_DELETE:
				view.clear();
				return;

				//left player (one) mallet movement
			case KeyEvent.VK_W:
				if(!view.getLeftMallet().stop)
					view.getLeftMallet().moveUp();
				break;
			case KeyEvent.VK_S:
				if(!view.getLeftMallet().stop)
					view.getLeftMallet().moveDown();
				break;
			case KeyEvent.VK_A:
				if(!view.getLeftMallet().stop)
					view.getLeftMallet().moveLeft();
				break;
			case KeyEvent.VK_D:
				if(!view.getLeftMallet().stop)
					view.getLeftMallet().moveRight();
				break;

				// left player(one) do powerup
			case KeyEvent.VK_Q:
				view.doPowerup("left");
				break;

			case KeyEvent.VK_E:
				view.doDefense("left");
				break;

				//right player (two) mallet movement
			case KeyEvent.VK_UP:
				if(!view.getRightMallet().stop)
					view.getRightMallet().moveUp();
				break;
			case KeyEvent.VK_DOWN:
				if(!view.getRightMallet().stop)
					view.getRightMallet().moveDown();
				break;
			case KeyEvent.VK_LEFT:
				if(!view.getRightMallet().stop)
					view.getRightMallet().moveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				if(!view.getRightMallet().stop)
					view.getRightMallet().moveRight();
				break;

				// right player do powerup
			case KeyEvent.VK_PERIOD:
				view.doPowerup("right");
				break;

			case KeyEvent.VK_SLASH:
				view.doDefense("right");
				break;

				// Displaymode powerups
			case KeyEvent.VK_1:
				if(view.displaymode)
					view.startLeftPowerup(1);
				break;

			case KeyEvent.VK_2:
				if(view.displaymode)
					view.startLeftPowerup(2);
				break;

			case KeyEvent.VK_3:
				if(view.displaymode)
					view.startLeftPowerup(3);
				break;

			case KeyEvent.VK_4:
				if(view.displaymode)
					view.startLeftPowerup(5);
				break;

			case KeyEvent.VK_5:
				if(view.displaymode)
				{
					GameLogic.leftPlayerScore = 50;
					GameLogic.checkLeftWin();
				}
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
