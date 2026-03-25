package gui;

import controller.*;
import algorithms.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.MapIterator;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.map.HashedMap;

import edu.uci.ics.jung.algorithms.flows.EdmondsKarpMaxFlow;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class PrintingFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	static MyGraph graph = GraphPanel.getGraph();
	final Button back = new Button("Back");
	private static Label msg = null;
	final Button anoter = new Button("Path");
	final Button cycle = new Button("Cycle");
	private static GraphPanel panel;
	private static Vector<MyGraph> paths;
	private static Vector<MyGraph> cycles;
	private static String start = "";
	private static boolean check = true;
	String[] starts;
	String[] ends;
	int choice = 0;

	private void setView() {
		graph = GraphPanel.getGraph();
		setLayout(null);

		back.setBounds(10, 432, 90, 25);
		back.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		back.setName("back");
		add(back);

		back.addMouseListener(new listen());
		anoter.addMouseListener(new listen());
		anoter.setBounds(480, 432, 90, 25);
		anoter.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		anoter.setName("another");

		cycle.addMouseListener(new listen());
		cycle.setBounds(380, 432, 90, 25);
		cycle.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		cycle.setName("cycle");

	}

	public PrintingFrame(int choice) {

		setView();
		this.choice = choice;
		 if (choice == 7)
			hamiltonianAlgorithm();
		else if (choice == 10)
			minimumHamiltonian();
		
	}
	public PrintingFrame(int choice, String from, String to) {
		setView();
		this.choice = choice;
		
	}
			
	public void hamiltonianAlgorithm() {

		msg = new Label("This Graph Has No Hamiltonian Paths");
		msg.setBounds(110, 20, 400, 30);
		msg.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
		add(msg);

		if (hamShit() != 0) {
			msg.setText("Hamiltonian Path Starts from " + start);
			panel = new GraphPanel(paths.get(0));
			panel.setBounds(10, 50, 560, 360);
			panel.vv.setBounds(10, 10, 560 - 20, 360 - 20);
			add(panel);
			add(anoter);
			add(cycle);
		}
	}

	int hamShit() {

		HamiltonPathAlgorithm ham = new HamiltonPathAlgorithm();
		int V = graph.getVertexCount(); // Number of vertices in graph

		if (V == 0)
			return 0;

		HashedMap<String, Integer> map = new HashedMap<String, Integer>();
		int i = 0;
		for (Object o : graph.getVertices().toArray()) {
			map.put(o.toString(), i);
			i++;
		}
		i = 0;
		java.util.List<HamiltonPathAlgorithm.Edge> listOfEgdes = new ArrayList();
		for (Object e : graph.getEdges().toArray()) {
			String from = graph.getEndpoints(e.toString()).getFirst();
			String to = graph.getEndpoints(e.toString()).getSecond();
			HamiltonPathAlgorithm.Edge edge = new HamiltonPathAlgorithm.Edge(map.get(from), map.get(to));
			listOfEgdes.add(edge);
		}

		HamiltonPathAlgorithm.Graph tempG = new HamiltonPathAlgorithm.Graph(listOfEgdes, V);
		Vector<Integer[]> vec = new Vector<Integer[]>();
		for (int j = 0; j < V; ++j) {
			Vector<Integer[]> tempVec = ham.getHamiltonianPaths(tempG, j, V);
			if (tempVec.size() > vec.size()) {
				vec = tempVec;
				i = j;
			}
		}

		{
			MapIterator<String, Integer> it = map.mapIterator();
			while (it.hasNext()) {
				String key = it.next();
				int value = map.get(key);
				if (value == i)
					start = key;
			}
		}

		int current = 0;
		if (vec.size() != 0) {
			starts = new String[vec.size()];
			ends = new String[vec.size()];
			paths = new Vector<MyGraph>();
			for (Integer[] arr : vec) {
				MyGraph element = new MyGraph(EdgeType.UNDIRECTED);
				if (arr.length == 1)
					element.addVertex(graph.getVertices().toArray()[0].toString());
				for (i = 0; i < arr.length - 1; ++i) {
					MapIterator<String, Integer> it = map.mapIterator();
					String from = "", to = "";
					while (it.hasNext()) {
						String key = it.next();
						int value = map.get(key);
						if (value == arr[i])
							from = key;
						if (i == 0 && value == arr[i])
							starts[current] = key;
						if (value == arr[i + 1])
							to = key;
					}
					String cost = graph.findEdge(from, to);
					element.addVertex(from);
					element.addVertex(to);
					element.addEdge(from, to, cost);
					if (i == (arr.length - 2))
						ends[current++] = to;
				}
				paths.add(element);
			}

			cycles = new Vector<MyGraph>();
			for (int j = 0; j < current; ++j) {

				String edge = graph.findEdge(starts[j], ends[j]);
				if (edge != null && !paths.get(j).containsEdge(edge)) {
					MyGraph found = new MyGraph(paths.get(j));
					found.addEdge(starts[j], ends[j], edge);
					cycles.add(found);
				}
			}
		}
		return vec.size();
	}

	
	public void minimumHamiltonian() {
		msg = new Label("No Hamiltonian Circuits");
		msg.setBounds(110, 20, 400, 30);
		msg.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
		add(msg);

		if (hamShit() != 0) {

			msg.setText("Minimum Hamiltonian Circuit [");
			msg.setBounds(20, 20, 400, 30);
			msg.setFont(new Font(Font.DIALOG, Font.PLAIN, 13));

			int minimum = Integer.MAX_VALUE;
			int minIndex = -1;
			for (int i = 0; i < cycles.size(); ++i) {
				MyGraph current = cycles.get(i);
				Object[] edgat = current.getEdges().toArray();
				int localMin = 0;
				for (Object ed : edgat) {
					if (!ed.toString().trim().equals("")) {
						localMin += Integer.parseInt(ed.toString().trim());
					}
				}
				if (minimum > localMin) {
					minimum = localMin;
					minIndex = i;
				}
			}
			if (minIndex != -1) {
				panel = new GraphPanel(graph);
				panel.setBounds(10, 50, 560, 360);
				panel.vv.setBounds(10, 10, 560 - 20, 360 - 20);
				add(panel);

				Object[] to = cycles.get(minIndex).getNeighbors(starts[minIndex]).toArray();
				Vector<String> visited = new Vector<String>();
				visited.add(starts[minIndex]);
				String first = "";
				for (Object o : to) {
					if (o.toString().equals(ends[minIndex]))
						continue;
					first = o.toString();
				}
				visited.add(first);

				while (visited.size() < cycles.get(minIndex).getEdgeCount()) {
					to = cycles.get(minIndex).getNeighbors(first).toArray();
					for (Object o : to) {
						if (visited.contains(o.toString()))
							continue;
						first = o.toString();
					}
					visited.add(first);
				}
				visited.add(starts[minIndex]);

				for (int i = 0; i < visited.size(); ++i) {
					if (i == visited.size() - 1)
						msg.setText(msg.getText() + visited.get(i) + "]");
					else
						msg.setText(msg.getText() + visited.get(i) + " => ");
				}

			} else {
				msg.setText("No Hamiltonian Circuits");
				msg.setBounds(110, 20, 400, 30);
				msg.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));

			}
		}
	}


	private class listen implements MouseListener {

		public void mouseReleased(MouseEvent arg0) {

		}

		public void mousePressed(MouseEvent arg0) {

		}

		public void mouseExited(MouseEvent arg0) {

		}

		public void mouseEntered(MouseEvent arg0) {

		}

		public void mouseClicked(MouseEvent arg0) {
			if (arg0.getComponent().getName().equals("back")) {
				if (choice == 12 || choice == 13)
					Home.home.copy(new InputFrame(graph));
				else
					Home.home.copy(new InputFrame(graph));

			} else if (arg0.getComponent().getName().equals("cycle")) {
				if (cycles.size() == 0)
					return;

				if (check == false)
					return;
				msg.setText("Hamiltonian Circle");
				panel.remove(panel.vv);
				panel.repaint();
				panel.setGraph(cycles.get(0));
				panel.setBounds(10, 50, 560, 360);
				panel.vv.setBounds(10, 10, 560 - 20, 360 - 20);

				check = false;

			} else if (arg0.getComponent().getName().equals("another") && !check) {

				msg.setText("Hamiltonian Path " + "Starts From " + start);
				panel.remove(panel.vv);
				panel.repaint();
				panel.setGraph(paths.get(0));
				panel.setBounds(10, 50, 560, 360);
				panel.vv.setBounds(10, 10, 560 - 20, 360 - 20);
				check = true;

			}
		
		}
	}
}
