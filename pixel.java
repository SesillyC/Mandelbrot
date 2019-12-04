
public class pixel{
	/*
	 * Holds all our helper methods that convert our numbers into math coordinates
	 */
	  public static double xmapMandelbrot(double x, double y, double a) {
		    return x * x - y * y + a;
		  }

	  public static double ymapMandelbrot(double x, double y, double b) {
		    return 2.0 * x * y + b;
		  }
	  
	  public static int scaleX(double x, double xa, double xb, int w)  {
		    int ivalue;
		    if( x >= xa && x < xb ) {
		      ivalue = (int) ((x - xa)*w/(xb - xa));
		    }
		    else {
		      ivalue = 0;
		      System.out.println("arg " + x + " out of range:[" + xa + "," + xb +"]");
		      System.exit(1);
		    }
		    return ivalue;
		  }

		  public static int scaleY(double y, double ymin, double yb, int h)  {
		    int jvalue;
		    if( y >= ymin && y < yb ) {
		      jvalue = (int) ((y - ymin)*h/(yb - ymin));
		    }
		    else {
		      jvalue = 0;
		      System.out.println("arg " + y + " out of range:[" + ymin + "," + yb +"]");
		      System.exit(2);
		    }
		    return jvalue;
		  }
}
