/**
 * As more and more rabbits were rescued from Professor Booleans horrid laboratory, you had to develop a system to track them, since some habitually continue to gnaw on the heads of their brethren and need extra supervision. For obvious reasons, you based your rabbit survivor tracking system on a binary search tree, but all of a sudden that decision has come back to haunt you.

To make your binary tree, the rabbits were sorted by their ages (in days) and each, luckily enough, had a distinct age. For a given group, the first rabbit became the root, and then the next one (taken in order of rescue) was added, older ages to the left and younger to the right. The order that the rabbits returned to you determined the end pattern of the tree, and herein lies the problem.

Some rabbits were rescued from multiple cages in a single rescue operation, and you need to make sure that all of the modifications or pathogens introduced by Professor Boolean are contained properly. Since the tree did not preserve the order of rescue, it falls to you to figure out how many different sequences of rabbits could have produced an identical tree to your sample sequence, so you can keep all the rescued rabbits safe.

For example, if the rabbits were processed in order from [5, 9, 8, 2, 1], it would result in a binary tree identical to one created from [5, 2, 9, 1, 8]. 

You must write a function answer(seq) that takes an array of up to 50 integers and returns a string representing the number (in base-10) of sequences that would result in the same tree as the given sequence.

Languages
=========

To provide a Python solution, edit solution.py
To provide a Java solution, edit solution.java

Test cases
==========

Inputs:
    (int list) seq = [5, 9, 8, 2, 1]
Output:
    (string) "6"

Inputs:
    (int list) seq = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
Output:
    (string) "1"
 */

import java.math.BigInteger;

public class BinaryBunnies {
	static class Math {
		/**
		 * Calculates the binomial coefficient (n choose k).
		 * @param n The group of objects to choose from
		 * @param k The number of objects to choose
		 * @return The binomial coefficient (n choose k).
		 */
		private static int choose(int n, int k) {
			return (k == 0 || k == n) ? 1 : choose(n - 1, k - 1) + choose(n - 1, k);
		}
	}
	
	static class Node {
		int val;
		Node left;
		Node right;
		
		Node(int val) {
			this.val = val;
		}
	}
	
	static class BST {
		Node root;
		
		BST() { root = null; }
		
		public void insert(int val) {			
			root = insert(root, val);
		}
		
		public BigInteger getNumberOfPossibleInputs() {
			return getNumberOfPossibleInputs(root);
		}
		
		private Node insert(Node n, int val) {
			if (n == null)
				return new Node(val);
			
			if (val < n.val) {
				n.left = insert(n.left, val);
			} else {
				n.right = insert(n.right, val);
			}
			
			return n;
		}
		
		private int getNumberOfNodesInSubtree(Node n) {
			int num = 1;
			
			if (n == null)
				return 0;
			
			num += getNumberOfNodesInSubtree(n.left);
			num += getNumberOfNodesInSubtree(n.right);
				
			return num;
		}
		
		private BigInteger getNumberOfPossibleInputs(Node n) {			
			if (n == null)
				return BigInteger.ONE;
			
			int cLeft = getNumberOfNodesInSubtree(n.left);
			int cRight = getNumberOfNodesInSubtree(n.right);
			BigInteger numLeft = getNumberOfPossibleInputs(n.left);
			BigInteger numRight = getNumberOfPossibleInputs(n.right);
			
			return BigInteger.valueOf(Math.choose(cLeft + cRight, cRight)).multiply(numLeft).multiply(numRight);
		}
	}
	
	public static String answer(int[] seq) {
		BST bst = new BST();
		BigInteger answer;
		
		for (int i : seq)
			bst.insert(i);
		
		answer = bst.getNumberOfPossibleInputs();
		return answer.toString();
	}
	
	public static void main(String[] args) {
		int seq[] = {81, 75, 52, 59, 71, 39, 48, 50, 22, 42, 69, 35, 2, 12, 29, 8, 45, 19, 16, 5, 27, 36};
		
		System.out.println(answer(seq));
	}
}
