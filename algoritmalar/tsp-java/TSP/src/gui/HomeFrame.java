package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.Home;

public class HomeFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	Label welcomeMsg = new Label("Graph Visualizer");
	
	Button hamiltonPath = new Button("Hamilton's Path/Circuit");
	Button MHC = new Button("Min Hamilton Circuit");


	public HomeFrame(String name) {
		super(name);

		welcomeMsg.setBounds(160, 50, 370, 30);
		welcomeMsg.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));

		hamiltonPath.setBounds(260, 280, 140, 34);
		MHC.setBounds(420, 280, 140, 34);
		hamiltonPath.setName("hamiltonPath");
		// hamiltonCircuit.setName("hamiltonCircuit");
		MHC.setName("MHC");
		hamiltonPath.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		// hamiltonCircuit.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		MHC.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		ImageIcon i = new ImageIcon("imgs/graph.png");
		JLabel l = new JLabel(i);
		l.setBounds(20, 165, 207, 207);
		ImageIcon ii = new ImageIcon("imgs/view.png");
		JLabel ll = new JLabel(ii);
		ll.setBounds(25 + 207, 150, 11, 230);
		this.add(ll);
		this.add(welcomeMsg);
		this.add(l);
// this.add(hamiltonCircuit);
		this.add(hamiltonPath);
		this.add(MHC);
	// hamiltonCircuit.addMouseListener(a);
		hamiltonPath.addMouseListener(new mouse());
		MHC.addMouseListener(new mouse());
		setLayout(null);
	}

	private static class mouse implements MouseListener {
		public void mouseClicked(MouseEvent event) {

			if (event.getComponent().getName().equals("hamiltonPath")) {
				Home.choice = 7;
				Home.home.copy(new InputFrame("Graph", false));

			}
			if (event.getComponent().getName().equals("FA")) {
				Home.choice = 9;
				Home.home.copy(new InputFrame("Graph", false));
			}
			if (event.getComponent().getName().equals("MHC")) {
				Home.choice = 10;
				Home.home.copy(new InputFrame("Graph", false));

			}

		}

		public void mousePressed(MouseEvent event) {
		}

		public void mouseReleased(MouseEvent event) {
		}

		public void mouseEntered(MouseEvent event) {
		}

		public void mouseExited(MouseEvent event) {
		}
	}
}