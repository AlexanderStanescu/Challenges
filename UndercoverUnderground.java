/**
 *  As you help the rabbits establish more and more resistance groups to fight
	against Professor Boolean, you need a way to pass messages back and forth.
	
	Luckily there are abandoned tunnels between the warrens of the rabbits,
	and you need to find the best way to use them.
	
	In some cases, Beta Rabbit wants a high level of interconnectedness,
	especially when the groups show their loyalty and worthiness.
	
	In other scenarios the groups should be less intertwined,
	in case any are compromised by enemy agents or zombits.
	
	Every warren must be connected to every other warren somehow,
	and no two warrens should ever have more than one tunnel between them.
	
	Your assignment: count the number of ways to connect the resistance warrens.
	For example, with 3 warrens (denoted A, B, C) and 2 tunnels, there are three
	distinct ways to connect them:
	
	A-B-C
	A-C-B
	C-A-B
	
	With 4 warrens and 6 tunnels, the only way to connect them is to connect
	each warren to every other warren.
	
	Write a function answer(N, K) which returns the number of ways to connect
	N distinctly labelled warrens with exactly K tunnels, so that there is a
	path between any two warrens.
	
	The return value must be a string representation of the total number of
	ways to do so, in base 10. N will be at least 2 and at most 20.
	K will be at least one less than N and at most (N * (N - 1)) / 2
 */

import java.math.BigInteger;

public class UndercoverUnderground {
	//Caches to avoid calculating more than once.
	private static BigInteger[][] binomCache = new BigInteger[191][191];
	private static BigInteger[][] solveCache = new BigInteger[191][191];
	
	public static void main(String[] args) {
		System.out.println(answer(20, 140));
	}
	
	/**
	 * Initializes two caches for storing values to avoid calculating the values more than once. Converts
	 * the calculated number of connected graphs over n vertices and k edges to a string value, and returns it.
	 * @param N The total number of vertices
	 * @param K
	 * @return The number of connected graphs over N vertices and K edges in String format
	 */
	public static String answer(int N, int K) {
		//Fill caches with zeros
		for (int i = 0; i < 191; i++) {
			for (int j = 0; j < 191; j++) {
				binomCache[i][j] = BigInteger.ZERO;
				solveCache[i][j] = BigInteger.ZERO;
			}
		}
			
		return solve(N, K).toString();
	}
	
	/**
	 * Calculates by using an exponential generating function of the set of labelled trees with a root.
	 * Once the total number of trees is known, we iterate through by removing one vertex (with degree 1),
	 * and repeating this process until no vertices are left. We subtract the combinations in which we have unconnected,
	 * or duplicate trees to arrive at an answer for the number of connected graphs over N vertices based on K edges.
	 * More information can be found here: http://math.stackexchange.com/questions/689526/how-many-connected-graphs-over-v-vertices-and-e-edges
	 * @param N The number of vertices
	 * @param K The number of edges to use
	 * @return The number of connected graphs over N vertices and K edges.
	 */
	public static BigInteger solve(int N, int K) {		
		BigInteger total = BigInteger.valueOf(0);
		BigInteger sub = BigInteger.valueOf(0);
		
		//Check if solution is in cache already
		if (solveCache[N][K] != BigInteger.ZERO)
			return solveCache[N][K];
		
		//These cannot be possible, so we return 0
		if (K < N - 1 || K > N * (N - 1) / 2)
			return BigInteger.ZERO;	
			
		//The
		if (K == N - 1) {
			//Check if we have input n = 1, k = 0. If so, return 1
			if (N < 2) {
				solveCache[N][K] = BigInteger.ONE;
				return solveCache[N][K];
			}
				
			//The number of trees in a graph g for n vertices is given by Cayley's Formula: n^(n-2) 
			//For cases in which k = n - 1, we have only trees so we can use this formula
			solveCache[N][K] = BigInteger.valueOf(N).pow(N - 2);
			return solveCache[N][K];
		}

		//Store total number of subgraphs of a graph g with n labeled vertices up to k edges
		total = choose((N * (N - 1)) / 2, K);
		
		//Loop to find duplicate/unconnected graphs
		for (int i = 0; i <= N - 2; i++) {
			sub = BigInteger.valueOf(0);
			
			//Find all duplicates and accumulate to sub
			for (int j = 0; j <= K; j++) {
				sub = sub.add(choose((N - 1 - i) * (N - 2 - i) / 2, j).multiply(solve(i + 1, K - j)));
			}
			
			//Subtract all duplicates from total
			total = total.subtract(choose(N - 1, i).multiply(sub));
		}
		
		//Store in cache, since it doesn't exist yet
		solveCache[N][K] = total;
		return total;
	}
	
	/**
	 * Calculates the binomial coefficient (n choose k).
	 * @param n The group of objects to choose from
	 * @param k The number of objects to choose
	 * @return The binomial coefficient (n choose k).
	 */
	private static BigInteger choose(int n, int k) {
		//Check if number was computed previously
		if (binomCache[n][k].compareTo(BigInteger.ZERO) != 0)
			return binomCache[n][k];
		
		BigInteger ans = BigInteger.valueOf(1);
		
		//Not possible, return 0
		if (k > n)
			return BigInteger.valueOf(0);
		
		//Coefficients are symmetric, this reduces the cost of the function by looping fewer times
		if (k > n - k)
			k = n - k;
		
		//Calculate coefficient
		for (int i = 0; i < k; i++) {
			ans = ans.multiply(BigInteger.valueOf(n - i));
			ans = ans.divide(BigInteger.valueOf(i + 1));
		}
		
		//Record number in cache and return
		binomCache[n][k] = ans;
		return ans;
	}
}
