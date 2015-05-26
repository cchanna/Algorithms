package assignment6;

import java.awt.Color;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Point2D;
import static edu.princeton.cs.introcs.StdRandom.*;
import static edu.princeton.cs.introcs.StdDraw.*;

public class SpaceDisagreement {
	static final double CIRCLE = .0175;
	static final double LINE = .0025;
	static final double CONNECT_DISTANCE = .15;

	static final Color SPACE = BLACK;
	static final Color MARKED = RED;
	static final Color CONNECTION = BOOK_BLUE;

	static final Color STAR = WHITE;
	
	protected Graph map;
	protected Point2D[] coordinates;
	protected int numStars;
	protected boolean[] marked;
	protected Biconnected biconnected;
	
	public SpaceDisagreement(int numStars) {
		this.numStars = numStars;
		map = new Graph(numStars);
		marked = new boolean[numStars];
		coordinates = new Point2D[numStars];
		for (int i = 0; i < numStars; i++) {
			coordinates[i] = new Point2D(uniform(),uniform());
		}
		for (int i = 0; i < numStars; i++) {
			for (int j = i+1; j < numStars; j++) {
				System.out.print(coordinates[i].distanceTo(coordinates[j]));
				if (coordinates[i].distanceTo(coordinates[j]) < CONNECT_DISTANCE) {
					map.addEdge(i, j);
					System.out.print(" connected");
				
				}
				System.out.println();
			}
		}
		biconnected = new Biconnected(map);
	}
	
	public void draw() {
		setCanvasSize(1000, 600);
		show(10);
		clear(SPACE);
		setPenRadius(LINE);
		setPenColor(CONNECTION);
		for (int i = 0; i < numStars; i++) {
			for (int v : map.adj(i)) {
				line(coordinates[v].x(), coordinates[v].y(), coordinates[i].x(), coordinates[i].y());
			}
		}
		setPenRadius(CIRCLE + 2*LINE);
		for (int i = 0; i <numStars; i++) {
			if (biconnected.isArticulation(i)) setPenColor(MARKED);
			else setPenColor(STAR);
			point(coordinates[i].x(),coordinates[i].y());
		}	
	}
	
}