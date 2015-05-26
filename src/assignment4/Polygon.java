package assignment4;

import java.util.Arrays;

import edu.princeton.cs.algs4.Point2D;
import static edu.princeton.cs.introcs.StdDraw.*;

public class Polygon {
	Point2D points[];
	int size;
	
	public Polygon(Point2D points[]) {
		Arrays.sort(points,Point2D.Y_ORDER);
		Arrays.sort(points, 1, points.length, points[0].POLAR_ORDER);
		this.points = points;
		size = points.length;
	}
	
	public void draw() {
		setCanvasSize();
		setPenRadius(.05);
		for (int i=0; i<size; i++) {
			point(points[i].x(),points[i].y());
		}
		setPenRadius(.005);
		for (int i=0; i<size; i++) {
			line(points[i].x(),points[i].y(),points[(i+1)%size].x(),points[(i+1)%size].y());
		}
		
	}
	
}
