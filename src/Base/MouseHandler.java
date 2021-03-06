package Base;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;

//******************************************************************************

/**
 *
 * @author  Team Awesome
 */
public final class MouseHandler extends MouseAdapter
{
	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private final View	view;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public MouseHandler(View view)
	{
		this.view = view;

		Component	component = view.getComponent();

		component.addMouseListener(this);
		component.addMouseMotionListener(this);
		component.addMouseWheelListener(this);
	}

	//**********************************************************************
	// Override Methods (MouseListener)
	//**********************************************************************

	@Override
	public void		mouseClicked(MouseEvent e)
	{
		Point2D.Double	v = calcCoordinatesInView(e.getX(), e.getY());
//		System.out.println(v.getX() + " : "+ v.getY());

	}

	@Override
	public void		mouseEntered(MouseEvent e)
	{
		Point2D.Double	v = calcCoordinatesInView(e.getX(), e.getY());

		//view.setCursor(v);
	}

	@Override
	public void		mouseExited(MouseEvent e)
	{
		view.setCursor(null);
	}

	@Override
	public void		mousePressed(MouseEvent e)
	{
		Point2D.Double	v = calcCoordinatesInView(e.getX(), e.getY());

		view.mousePressed(v);
	}

	@Override
	public void		mouseReleased(MouseEvent e)
	{
		Point2D.Double	v = calcCoordinatesInView(e.getX(), e.getY());

		view.mouseRelease(v);
	}

	//**********************************************************************
	// Override Methods (MouseMotionListener)
	//**********************************************************************

	@Override
	public void		mouseDragged(MouseEvent e)
	{
		Point2D.Double	v = calcCoordinatesInView(e.getX(), e.getY());

		view.mouseDrag(v);
	}

	@Override
	public void		mouseMoved(MouseEvent e)
	{
//		Point2D.Double	v = calcCoordinatesInView(e.getX(), e.getY());
//
//		view.setCursor(v);
	}

	//**********************************************************************
	// Override Methods (MouseWheelListener)
	//**********************************************************************

	@Override
	public void		mouseWheelMoved(MouseWheelEvent e)
	{
	}

	//**********************************************************************
	// Private Methods
	//**********************************************************************

	private Point2D.Double	calcCoordinatesInView(int sx, int sy)
	{
		int				w = view.getWidth();
		int				h = view.getHeight();
		Point2D.Double	p = view.getOrigin();
		double			vx = p.x + (sx * 2.0) / w - 1.0;
		double			vy = p.y - (sy * 2.0) / h + 1.0;

		return new Point2D.Double(vx, vy);
	}
}

//******************************************************************************
