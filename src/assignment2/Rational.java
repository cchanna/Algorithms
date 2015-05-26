package assignment2;

public class Rational {
	private final int numerator, denominator;

	public Rational(int numerator, int denominator) {
		if (denominator == 0) {
			System.err.println("That is not a rational  number");
		}
		if (denominator < 0) {
			denominator = denominator * -1;
			numerator = numerator * -1;
		}
		int gcd = gcd(numerator,denominator);
		this.numerator = numerator/gcd;
		this.denominator = denominator/gcd;
	}
	public Rational plus(Rational b) {
		return new Rational((numerator*b.denominator + b.numerator*denominator),(denominator*b.denominator));
	}

	public Rational minus(Rational b) {
		return new Rational((numerator*b.denominator - b.numerator*denominator),(denominator*b.denominator));
	}

	public Rational times(Rational b) {
		return new Rational(numerator * b.numerator,denominator * b.denominator);
	}

	public Rational dividedBy(Rational b) {
		return new Rational(numerator * b.denominator,denominator * b.numerator);
	}

	public boolean equals(Object that) {
		if (this == that) return true;
		if (that == null) return false;
		if (this.getClass() != that.getClass()) return false;
		if (this.numerator != ((Rational)that).numerator) return false;
		if (this.denominator != ((Rational)this).denominator) return false;
		return true;
	}

	public String toString() {
		return numerator + "/" + denominator;
	}
	
	private static int gcd(int p, int q) {
		p = Math.abs(p); q = Math.abs(q);
		if (q == 0) return p;
		int r = p % q;
		return gcd(q,r);
	}
	
}
