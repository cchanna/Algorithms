package assignment6;

import java.awt.Color;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.PrimMST;
//import edu.princeton.cs.algs4.Queue;
//import edu.princeton.cs.algs4.UF;
import static edu.princeton.cs.introcs.StdRandom.*;
import static edu.princeton.cs.introcs.StdDraw.*;

public class AnimatedPrim {
	static final double CIRCLE = .0175;
	static final double LINE = .0025;
	static final double THICK_LINE = .01;
	static final int WAIT = 500;
	static final double CONNECT_DISTANCE = .15;

	static final Color SPACE = BLACK;
	
	static final Color ACTIVE = RED;
	static final Color INACTIVE = BOOK_BLUE;
	static final Color TREE = WHITE;
	static final Color DONE = DARK_GRAY;

	static final Color MARKED = BLACK;
	static final Color UNMARKED = WHITE;

	protected EdgeWeightedGraph map;
	protected Point2D[] coordinates;
	protected int numStars;
	protected boolean[] marked;
	protected PrimMST primMST;

	private Edge[] edgeTo; // edgeTo[v] = shortest edge from tree vertex to
	// non-tree vertex
	private double[] distTo; // distTo[v] = weight of shortest such edge
	private IndexMinPQ<Double> pq;
	double minDist;

	public AnimatedPrim(int numStars) {
		this.numStars = numStars;
		map = new EdgeWeightedGraph(numStars);
		marked = new boolean[numStars];
		coordinates = new Point2D[numStars];
		edgeTo = new Edge[map.V()];
		distTo = new double[map.V()];
		pq = new IndexMinPQ<Double>(map.V());

		for (int i = 0; i < numStars; i++) {
			coordinates[i] = new Point2D(uniform(), uniform());
		}
		for (int i = 0; i < numStars; i++) {
			for (int j = i + 1; j < numStars; j++) {
				double distance = coordinates[i].distanceTo(coordinates[j]);
				if (coordinates[i].distanceTo(coordinates[j]) < CONNECT_DISTANCE) {
					map.addEdge(new Edge(i, j, distance));
				}
			}
		}
		show(10);
		setCanvasSize(1000, 600);
		draw();
		findMST();
		draw();
		show(3000);
	}

	public void draw() {
		clear(SPACE);
		setPenRadius(LINE);
		setPenColor(DONE);
		drawDone();
		setPenColor(INACTIVE);
		drawInactive();
		setPenColor(ACTIVE);
		drawActive();
		setPenColor(TREE);
		setPenRadius(THICK_LINE);
		drawTree();
		drawStars();
		show(WAIT);
	}

	private void drawInactive() {
		for (int i = 0; i < numStars; i++) {
			for (Edge e : map.adj(i)) {
				int v = e.either();
				int w = e.other(v);
				setPenRadius(LINE);
				if (!marked[v] && !marked[w]) {
					line(coordinates[v].x(), coordinates[v].y(),
							coordinates[w].x(), coordinates[w].y());
				}
			}
		}
	}

	private void drawDone() {
		for (int i = 0; i < numStars; i++) {
			for (Edge e : map.adj(i)) {
				int v = e.either();
				int w = e.other(v);
				if (marked[v] && marked[w]) {
					if ((edgeTo[v] != e && edgeTo[w] != e)) {
						line(coordinates[v].x(), coordinates[v].y(),
								coordinates[w].x(), coordinates[w].y());
					}
				} else if (marked[v]) {
					if (!pq.isEmpty()) {
						if (e.weight() != distTo[w]) {
							line(coordinates[v].x(), coordinates[v].y(),
									coordinates[w].x(), coordinates[w].y());
						}
					}
				} else if (marked[w]) {
					if (!pq.isEmpty()) {
						if (e.weight() != distTo[v]) {
							line(coordinates[v].x(), coordinates[v].y(),
									coordinates[w].x(), coordinates[w].y());
						}
					}
				}

			}
		}
	}

	private void drawActive() {
		for (int i = 0; i < numStars; i++) {
			for (Edge e : map.adj(i)) {
				int v = e.either();
				int w = e.other(v);
				setPenRadius(LINE);
				if (marked[v] && marked[w]) {

				} else if (marked[v]) {
					if (!pq.isEmpty()) {
						if (e.weight() == distTo[w]) {
							if (pq.minIndex() == w)
								setPenRadius(THICK_LINE);
							line(coordinates[v].x(), coordinates[v].y(),
									coordinates[w].x(), coordinates[w].y());
						}
					}
				} else if (marked[w]) {
					if (!pq.isEmpty()) {
						if (e.weight() == distTo[v]) {
							if (pq.minIndex() == v)
								setPenRadius(THICK_LINE);
							line(coordinates[v].x(), coordinates[v].y(),
									coordinates[w].x(), coordinates[w].y());
						}
					}
				}
			}
		}
	}

	private void drawTree() {
		for (int i = 0; i < numStars; i++) {
			for (Edge e : map.adj(i)) {
				int v = e.either();
				int w = e.other(v);
				if (marked[v] && marked[w]) {
					if ((edgeTo[v] == e || edgeTo[w] == e)) {
						line(coordinates[v].x(), coordinates[v].y(),
								coordinates[w].x(), coordinates[w].y());
					} 
				}
			}
		}
	}
	
	private void drawStars() {
		for (int i = 0; i < numStars; i++) {
			setPenColor(TREE);
			setPenRadius(CIRCLE + 2 * LINE);
			point(coordinates[i].x(), coordinates[i].y());
			setPenRadius(CIRCLE);
			if (marked[i])
				setPenColor(MARKED);
			else
				setPenColor(UNMARKED);
			point(coordinates[i].x(), coordinates[i].y());
		}
	}
		

	public void findMST() {
		for (int v = 0; v < map.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;

		for (int v = 0; v < map.V(); v++)
			// run from each vertex to find
			if (!marked[v])
				prim(map, v); // minimum spanning forest

		// check optimality conditions
		// assert check(map);
	}

	private void prim(EdgeWeightedGraph G, int s) {
		distTo[s] = 0.0;
		pq.insert(s, distTo[s]);
		while (!pq.isEmpty()) {
			draw();
			int v = pq.delMin();
			scan(G, v);
		}
	}

	private void scan(EdgeWeightedGraph G, int v) {
		marked[v] = true;
		for (Edge e : G.adj(v)) {
			int w = e.other(v);
			if (marked[w])
				continue;
			if (e.weight() < distTo[w]) {
				distTo[w] = e.weight();
				edgeTo[w] = e;
				if (pq.contains(w))
					pq.decreaseKey(w, distTo[w]);
				else
					pq.insert(w, distTo[w]);
			}
		}
	}

	// public Iterable<Edge> edges() {
	// Queue<Edge> mst = new Queue<Edge>();
	// for (int v = 0; v < edgeTo.length; v++) {
	// Edge e = edgeTo[v];
	// if (e != null) {
	// mst.enqueue(e);
	// }
	// }
	// return mst;
	// }
	//
	// public double weight() {
	// double weight = 0.0;
	// for (Edge e : edges())
	// weight += e.weight();
	// return weight;
	// }
}
// // check optimality conditions (takes time proportional to E V lg* V)
// private boolean check(EdgeWeightedGraph G) {
//
// // check weight
// double totalWeight = 0.0;
// for (Edge e : edges()) {
// totalWeight += e.weight();
// }
// double EPSILON = 1E-12;
// if (Math.abs(totalWeight - weight()) > EPSILON) {
// System.err.printf(
// "Weight of edges does not equal weight(): %f vs. %f\n",
// totalWeight, weight());
// return false;
// }
//
// // check that it is acyclic
// UF uf = new UF(G.V());
// for (Edge e : edges()) {
// int v = e.either(), w = e.other(v);
// if (uf.connected(v, w)) {
// System.err.println("Not a forest");
// return false;
// }
// uf.union(v, w);
// }
//
// // check that it is a spanning forest
// for (Edge e : edges()) {
// int v = e.either(), w = e.other(v);
// if (!uf.connected(v, w)) {
// System.err.println("Not a spanning forest");
// return false;
// }
// }
//
// // check that it is a minimal spanning forest (cut optimality
// // conditions)
// for (Edge e : edges()) {
//
// // all edges in MST except e
// uf = new UF(G.V());
// for (Edge f : edges()) {
// int x = f.either(), y = f.other(x);
// if (f != e)
// uf.union(x, y);
// }
//
// // check that e is min weight edge in crossing cut
// for (Edge f : G.edges()) {
// int x = f.either(), y = f.other(x);
// if (!uf.connected(x, y)) {
// if (f.weight() < e.weight()) {
// System.err.println("Edge " + f
// + " violates cut optimality conditions");
// return false;
// }
// }
// }
//
// }
//
// return true;
// }
//
// /**
// * Unit tests the <tt>PrimMST</tt> data type.
// // */
// // public static void main(String[] args) {
// // In in = new In(args[0]);
// // EdgeWeightedGraph G = new EdgeWeightedGraph(in);
// // PrimMST mst = new PrimMST(G);
// // for (Edge e : mst.edges()) {
// // StdOut.println(e);
// // }
// // StdOut.printf("%.5f\n", mst.weight());
// // }
//
// }
