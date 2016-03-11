/**
 * Carrotland
 * 
 * The rabbits are free at last, free from that horrible zombie science experiment. They need a happy, safe home, where they can recover. 
 * You have a dream, a dream of carrots, lots of carrots, planted in neat rows and columns! But first, you need some land. 
 * And the only person who is selling land is Farmer Frida. Unfortunately, not only does she have only one plot of land, she also doesn't know how big it is
 * - only that it is a triangle. However, she can tell you the location of the three vertices, which lie on the 2-D plane and have integer coordinates.
 * Of course, you want to plant as many carrots as you can. But you also want to follow these guidelines: The carrots may only be planted at points with
 * integer coordinates in the 2-D plane. They must lie within the plot of land and not on the boundaries. For example, if the vertices were
 * (-1, -1), (1, 0), and (0, 1), then you can plant only one carrot at (0,0). Write a function answer(vertices), which, when given a list of three
 * vertices, returns the maximum number of carrots you can plant. The vertices list will contain exactly three elements, and each element will be
 * a list of two integers representing the x and y coordinates of a vertex. All coordinates will have absolute value no greater than 1000000000. The three
 * vertices will not be collinear.
 * 
 * Test Cases
 * 
 * Inputs:
 * 		(int) vertices = [[2, 3], [6, 9], [10, 160]]
 * Output:
 * 		(int) 289
 * 
 * Inputs:
 * 		(int) vertices = [[91207, 89566], [-88690, -83026], [67100, 47194]]
 * Output:
 * 		(int) 1730960165
 */

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class Carrotland {
	public static void main(String[] args) {
		int[][] input = {{91207, 89566}, {-88690, -83026}, {67100, 47194}};
		
		System.out.println(answer(input));
	}
	
	public static int answer(int[][] vertices) {
		int minY = Math.min(Math.min(vertices[0][1], vertices[1][1]), vertices[2][1]);
		int maxY = Math.max(Math.max(vertices[0][1], vertices[1][1]), vertices[2][1]);
		long bound = 0L;	
		long triLattices = 0;
		
		vertices = bubbleSort(vertices, 0);
		
		//Special Case
		if (vertices[1][1] != minY && vertices[1][1] != maxY)
			bound = rectArea(vertices[0][0], vertices[1][0], minY, maxY) + rectArea(vertices[1][0] + 1, vertices[2][0], vertices[1][1], maxY);
		else
			bound = rectArea(vertices[0][0], vertices[2][0], minY, maxY);
		
		triLattices = getNumLattices(vertices[0][0], vertices[1][0], vertices[0][1], vertices[1][1]);
		triLattices += getNumLattices(vertices[1][0], vertices[2][0], vertices[1][1], vertices[2][1]) - 1;
		triLattices += getNumLattices(vertices[0][0], vertices[2][0], vertices[0][1], vertices[2][1]) - 2;

		return (int)(bound - triLattices);
	}
	
	private static long rectArea(int x1, int x2, int y1, int y2) {
		return (long)(x2 - x1 + 1) * (y2 - y1 + 1);
	}
	
	private static double triArea(int b, int h) {
		long bh = (long)b * h;
		return 0.5 * bh;
	}

	private static long getNumLattices(int x1, int x2, int y1, int y2) {
		int deltaY = Math.abs(y2 - y1);
		int deltaX = Math.abs(x2 - x1);
		long b = (long)gcd(deltaY, deltaX) + deltaY + deltaX;
		double a = triArea(deltaX, deltaY);
		return (long)(a - b / 2.0 + 1) + b;
	}
	
	private static int[][] bubbleSort(int[][] a, int idx) {
		boolean swapped = true;
		int len = a.length;
		
		for (int j = len - 1; j > 0 && swapped; j--) {
			swapped = false;
			
			for (int i = 0; i < j; i++) {
				if (a[i][idx] > a[i + 1][idx]) {
					int[] temp = a[i];
					a[i] = a[i + 1];
					a[i + 1] = temp;
					swapped = true;
				}
			}
		}
		
		return a;
	}
	
	private static int gcd(int a, int b) {
		if (a < b) {
			int temp = a;
			a = b;
			b = temp;
		}
		
		return (b == 0) ? a : gcd(b, a % b); 
	}
}

