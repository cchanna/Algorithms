package assignment1;

import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdRandom;

public class RandomConnections {
	static void run(int n, double p) {
		double theta = 0;
		double r = .5;
		StdDraw.setPenRadius(.01);
		double x[] = new double[n], y[] = new double[n];
		for (int i = 0; i < n; i++) {
			theta += (2*Math.PI)/n;
			x[i] = r * Math.cos(theta) +.5;
			y[i] = r * Math.sin(theta) +.5;
			//StdDraw.point(x[i],y[i]);
			StdDraw.filledCircle(x[i], y[i], .05);
		}
		StdDraw.setPenColor(StdDraw.GRAY);
		for (int i = 0; i < n; i++) {
			for (int j = i+1; j < n; j++) {
				if (StdRandom.bernoulli(p)) {
					StdDraw.line(x[i], y[i], x[j], y[j]);
				}
			}
		}
	}
}
