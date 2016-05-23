package Base;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;


/**
 * Graphics Project
 *
 * Air Hockey Simulator
 *
 * @author Team Awesome
 */
public final class Application
{
	//**********************************************************************
	// Public Class Members
	//**********************************************************************


	/**
	 * Change TableHeight to change your window size.
	 * These vars ensure that the standard dimensions of an air hockey table are kept.
	 *
	 * @author tremc_000
	 */
	public static final int			TableHeight		= 800;
	public static final double		TableWidthMult	= 1.8;

	public static final Rectangle	DEFAULT_BOUNDS	=
		new Rectangle(50, 50, (int)(TableHeight * TableWidthMult), TableHeight);

	//**********************************************************************
	// Main
	//**********************************************************************

	public static void	main(String[] args)
	{
		GLProfile		profile = GLProfile.getDefault();
		GLCapabilities	capabilities = new GLCapabilities(profile);
		//GLCanvas		canvas = new GLCanvas(capabilities);
		GLJPanel		canvas = new GLJPanel(capabilities);
		JFrame			frame = new JFrame("Application");

		JMenuBar menubar = new JMenuBar(); 					// menu bar

		JMenu file = new JMenu("File");						// file menu
		final JMenuItem newgame = new JMenuItem("New Game");// file menu item

		JMenu score = new JMenu("Score Limit");				// score menu
		final JMenuItem one = new JMenuItem("1");			// score menu item
		final JMenuItem five = new JMenuItem("5");			// score menu item
		final JMenuItem ten = new JMenuItem("10");			// score menu item
		final JMenuItem twentyfive = new JMenuItem("25");	// score menu item
		final JMenuItem fifty = new JMenuItem("50");		// score menu item
		final JMenuItem hundred = new JMenuItem("100");		// score menu item

		JMenu skin = new JMenu("Skin"); 					// skin menu
		final JMenuItem hockey = new JMenuItem("Hockey"); 	// skin menu item
		final JMenuItem neon = new JMenuItem("Neon");		// skin menu item
		final JMenuItem invertedNeon = new JMenuItem("Invert");// skin menu item

		JMenu introduce = new JMenu("Reintroduce?");		// reintroduce menu
		final JMenuItem yes = new JMenuItem("Yes");			// reintroduce menu item
		final JMenuItem no = new JMenuItem("No");			// reintroduce menu item

		JMenu west = new JMenu("West");						// west team menu
		final JMenuItem wsh = new JMenuItem("WSH");			// capitols
		final JMenuItem pit = new JMenuItem("PIT");			// penguins
		final JMenuItem fla = new JMenuItem("FLA");			// panthers
		final JMenuItem nyr = new JMenuItem("NYR");			// rangers
		final JMenuItem nyi = new JMenuItem("NYI");			// islanders
		final JMenuItem tbl = new JMenuItem("TBL");			// lightning
		final JMenuItem phi = new JMenuItem("PHI");			// flyers
		final JMenuItem det = new JMenuItem("DET");			// redwings
		final JMenuItem bos = new JMenuItem("BOS");			// bruins
		final JMenuItem car = new JMenuItem("CAR");			// hurricanes
		final JMenuItem ott = new JMenuItem("OTT");			// senators
		final JMenuItem njd = new JMenuItem("NJD");			// devils
		final JMenuItem mtl = new JMenuItem("MTL");			// canadiens
		final JMenuItem buf = new JMenuItem("BUF");			// sabres
		final JMenuItem cbj = new JMenuItem("CBJ");			// blue jackets
		final JMenuItem tor = new JMenuItem("TOR");			// maple leaves

		JMenu east = new JMenu("East");						// east team menu
		final JMenuItem none = new JMenuItem("NONE");		// no team selected
		final JMenuItem dal = new JMenuItem("DAL");			// stars - fuck the stars
		final JMenuItem stl = new JMenuItem("STL");			// blues
		final JMenuItem chi = new JMenuItem("CHI");			// blackhawks
		final JMenuItem ana = new JMenuItem("ANA");			// ducks
		final JMenuItem lak = new JMenuItem("LAK");			// kings
		final JMenuItem sjs = new JMenuItem("SJS");			// sharks
		final JMenuItem nsh = new JMenuItem("NSH");			// predators
		final JMenuItem min = new JMenuItem("MIN");			// wild
		final JMenuItem col = new JMenuItem("COL");			// avalanche
		final JMenuItem ari = new JMenuItem("ARI");			// coyotes
		final JMenuItem wpg = new JMenuItem("WPG");			// jets
		final JMenuItem cgy = new JMenuItem("CGY");			// flames
		final JMenuItem van = new JMenuItem("VAN");			// canucks
		final JMenuItem edm = new JMenuItem("EDM");			// oilers

		// Add menu's to menubar
		menubar.add(file);
		menubar.add(score);
		menubar.add(skin);
		menubar.add(introduce);
		menubar.add(west);
		menubar.add(east);

		// Add file menu items
		file.add(newgame);

		// Add score menu items
		score.add(one);
		score.add(five);
		score.add(ten);
		score.add(twentyfive);
		score.add(fifty);
		score.add(hundred);

		// Add skin menu items
		skin.add(hockey);
		skin.add(neon);
		skin.add(invertedNeon);

		// init skin menu items
		hockey.setEnabled(false);

		// Add reintroduce menu items
		introduce.add(yes);
		introduce.add(no);

		// init introduce menu items
		no.setEnabled(false);

		// Add west menu items
		west.add(dal);
		west.add(stl);
		west.add(chi);
		west.add(ana);
		west.add(lak);
		west.add(sjs);
		west.add(nsh);
		west.add(min);
		west.add(col);
		west.add(ari);
		west.add(wpg);
		west.add(cgy);
		west.add(van);
		west.add(edm);

		// Add east menu items
		east.add(none);
		east.add(wsh);
		east.add(pit);
		east.add(fla);
		east.add(nyr);
		east.add(nyi);
		east.add(tbl);
		east.add(phi);
		east.add(det);
		east.add(bos);
		east.add(car);
		east.add(ott);
		east.add(njd);
		east.add(mtl);
		east.add(buf);
		east.add(cbj);
		east.add(tor);

		// frame
		frame.setJMenuBar(menubar);
		frame.setBounds(DEFAULT_BOUNDS);
		frame.getContentPane().add(canvas);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});

		final View			view = new View(canvas);

		// Add action listeners to menu items

		// FILE MENU - action listeners
		newgame.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.newGame();
			}
		});

		// SCORELIMIT - action listeners
		one.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GameLogic.scoreLimit = 1;
				view.newGame();
			}
		});

		five.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GameLogic.scoreLimit = 5;
				view.newGame();
			}
		});

		ten.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GameLogic.scoreLimit = 10;
				view.newGame();
			}
		});

		twentyfive.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GameLogic.scoreLimit = 25;
				view.newGame();
			}
		});

		fifty.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GameLogic.scoreLimit = 50;
				view.newGame();
			}
		});

		hundred.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GameLogic.scoreLimit = 100;
				view.newGame();
			}
		});

		// SKIN MENU - action listeners
		hockey.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichSkin = 0;
				hockey.setEnabled(false);
				neon.setEnabled(true);
				invertedNeon.setEnabled(true);
			}
		});

		neon.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichSkin = 1;
				hockey.setEnabled(true);
				neon.setEnabled(false);
				invertedNeon.setEnabled(true);
			}
		});

		invertedNeon.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichSkin = 2;
				hockey.setEnabled(true);
				neon.setEnabled(true);
				invertedNeon.setEnabled(false);
			}
		});

		// REINTRODUCE - action listeners
		yes.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.reintroduce = true;
				yes.setEnabled(true);
				no.setEnabled(false);
			}
		});

		no.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.reintroduce = false;
				yes.setEnabled(false);
				no.setEnabled(true);
			}
		});

		// WEST ACTION LISTENERS
		dal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "stars.png";
			}
		});

		stl.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "blues.png";
			}
		});

		chi.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "hawks.png";
			}
		});

		ana.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "ducks.png";
			}
		});

		lak.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "kings.png";
			}
		});

		sjs.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "sharks.png";
			}
		});

		nsh.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "preds.png";
			}
		});

		min.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "wild.png";
			}
		});

		col.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "avalanche.png";
			}
		});

		ari.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "coyotes.png";
			}
		});

		wpg.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "jets.png";
			}
		});

		cgy.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "flames.png";
			}
		});

		van.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "canucks.png";
			}
		});

		edm.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "oilers.png";
			}
		});

		// ADD EAST ACTION LISTENERS
		none.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "none";
			}
		});

		wsh.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "caps.png";
			}
		});

		pit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "penguins.png";
			}
		});

		fla.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "panthers.png";
			}
		});

		nyr.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "rangers.png";
			}
		});

		nyi.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "island.png";
			}
		});

		tbl.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "lightning.png";
			}
		});

		phi.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "flyers.png";
			}
		});

		det.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "wings.png";
			}
		});

		bos.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "bruins.png";
			}
		});

		car.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "canes.png";
			}
		});

		ott.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "senators.png";
			}
		});

		njd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "devils.png";
			}
		});

		mtl.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "canadiens.png";
			}
		});

		buf.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "sabres.png";
			}
		});

		cbj.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "jackets.png";
			}
		});

		tor.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.whichTeam = "leafs.png";
			}
		});
	}


}

//******************************************************************************
