/*
 * By Sesilly Cruz
 */
import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;

public class mandelbrotSet extends Applet implements Runnable{

	public static int w = 800;
	public static int h = 800;
	public double xa = -2;					// The dimensions to be used 
	public double xb =  1;					// so that we can split up
	public double ya = -1.5;				// our work amongst the threads
	public double yb =  1.5;
	public boolean blank = true;
	public double a = 0;
	public double b = 0;
	public double x = 0;
	public double y = 0;
	public int scaleda,scaledb;
	public int delay = 0;
	public int count = 0;
	public int[] pixels;
	private MemoryImageSource source;
	int alpha = 100;
	int red = 40;
	int green = 100;
	int blue = 40;
	int times = 255;
	private Graphics canvas;
	Image img;
	private Thread t1,t2,t3,t4,t5,t6,t7,t8;
	
	// Method to start off our image and threads
	public void init() {
		canvas = getGraphics();
		img = createImage(w,h);
		pixels = new int[w*h];
		t1 = new Thread(this);
		t2 = new Thread(this);
		t3 = new Thread(this);
		t4 = new Thread(this);
		t5 = new Thread(this);
		t6 = new Thread(this);
		t7 = new Thread(this);
		t8 = new Thread(this);
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		t8.start();
	}

	//Method in which the threads will use to do their respective work
	public void run () {
		double tempA = 0, tempB = 0;
		double tempXA = 0, tempXB = 0;
		double tempYA = 0, tempYB = 0;
		//Check for which threads we will be using 
		if (Thread.currentThread() == t1){
			tempA = a;
			tempB = b;
			tempXA = -2;
			tempXB = -1.625;
			tempYA = -1.5;
			tempYB = 1.5;
		}
		else if (Thread.currentThread() == t2) {
			tempA = a;
			tempB = b;
			tempXA = -1.625;
			tempXB = -1.25;
			tempYA = -1.5;
			tempYB = 1.5;
		}
		else if (Thread.currentThread() == t3) {
			tempA = a;
			tempB = b;
			tempXA = -1.25;
			tempXB = -.875;
			tempYA = -1.5;
			tempYB = 1.5;
		}
		else if (Thread.currentThread() == t4) {
			tempA = a;
			tempB = b;
			tempXA = -.875;
			tempXB = -.5;
			tempYA = -1.5;
			tempYB = 1.5;
		}
		else if (Thread.currentThread() == t5) {
			tempA = a;
			tempB = b;
			tempXA = -.5;
			tempXB = -.125;
			tempYA = -1.5;
			tempYB = 1.5;
		}
		else if (Thread.currentThread() == t6) {
			tempA = a;
			tempB = b;
			tempXA = -.125;
			tempXB = .25;
			tempYA = -1.5;
			tempYB = 1.5;
		}
		else if (Thread.currentThread() == t7) {
			tempA = a;
			tempB = b;
			tempXA = .25;
			tempXB = .625;
			tempYA = -1.5;
			tempYB = 1.5;
		}
		else if (Thread.currentThread() == t8) {
			tempA = a;
			tempB = b;
			tempXA = .625;
			tempXB = 1;
			tempYA = -1.5;
			tempYB = 1.5;
		}
		while (true) {
			// This section is reserved only for t1 to use so that it is done no more than necessary
			synchronized  (t1){
				if(count == 1) {
					int iterations = 0;
					for(int iy = 0;iy < h;iy++){
						for(int ix = 0;ix < w;ix++)
							pixels[iterations++] = (alpha<<24) | (0x00<<16) | 0x00<< 8 | 0xfc; 
					}
					if(blank) { 
						source = new MemoryImageSource(w,h,pixels,0,w);
						blank = false;
					}
					else 
						source.newPixels(0,0,w,h);	
					img = createImage(source);
					paint(canvas);					 
				}
			}
			// Contains a nested for loop in which we create the physical(in a sense) Mandelbrot set
			if(count == 2) {
				double x0,y0;
				for(tempA = tempXA;tempA < tempXB;tempA += ((tempXB - tempXA)/ w)) {
					for(tempB = tempYA;tempB < tempYB; tempB += ((tempYB - tempYA)/ h)) {
						x = 0;
						y = 0;
						int iteration = 0;
						while(((x * x) + (y * y) <= 4) && (iteration != times)) {
							x0 = pixel.xmapMandelbrot(x,y,tempA);
							y0 = pixel.ymapMandelbrot(x,y,tempB);
							x = x0;
							y = y0;
							iteration++;
						}
						// Use our helper methods to move things along
						if(iteration <= times && iteration > 0) {
							scaleda = pixel.scaleX(tempA,xa,xb,w);
							scaledb = pixel.scaleY(tempB,ya,yb,h);
							pixels[(scaledb * w) + scaleda] = (alpha<<24) | (red<<16) | (iteration<<8) ;
						}
					}
					source.newPixels(0,0,w,h);
					img = createImage(source);
					paint(canvas); 
				}
			}
			count++;		 
		}
	}
	// Paint method
	public synchronized void paint(Graphics g) {
		g.drawImage(img,0,0,w,h,null);	
	}

}
