/**
 * You think you have Professor Boolean's password to the control center. All you need is confirmation, so that you can use it without being detected. You have managed to capture some minions so you can interrogate them to confirm.

You also have in your possession Professor Boolean's minion interrogation machine (yes, he interrogates his own minions). Its usage is simple: you ask the minion a question and put him in the machine. After some time (specific to the minion), you stop the machine and ask the minion for the answer. With certain probability (again, specific to the minion) you either get the truthful answer or the minion remains silent. Once you have subjected a minion to the machine, you cannot use it on the minion again for a long period.

The machine also has a 'guarantee' setting, which will guarantee that the minion will answer the question truthfully. Unfortunately, that has the potential to cause the minion some irreversible brain damage, so you vow to only use it as a last resort: on the last minion you interrogate.

Since Professor Boolean's password is periodically changed, you would like to know the answer as soon as possible. So you decide to interrogate the minions in an order which will take the least expected time (you can only use the machine on one minion at a time).

For example, you have captured two minions: minion A taking 10 minutes, and giving the answer with probability 1/2, and minion B taking 5 minutes, but giving the answer with probability 1/5.

If you interrogate A first, then you expect to take 12.5 minutes. If you interrogate B first, then you expect to take 13 minutes and thus must interrogate A first for the shortest expected time for getting the answer.

Write a function answer(minions) which given a list of the characteristics of each minion, returns the lexicographically smallest ordering of minions, which gives you the smallest expected time of confirming the password.

The minions are numbered starting from 0.

The minions parameter will be a list of lists.

minions[i] will be a list containing exactly three elements, corresponding to the i^th minion.

The first element of minion[i] will be a positive integer representing the time the machine takes for that minion.

The ratio of the second and third elements will be the probability of that minion giving you the answer: the second element, a positive integer will be the numerator of the ratio and the third element, also a positive integer will be the denominator of the ratio. The denominator will always be greater than the numerator. That is, [time, numerator, denominator].

The return value must be a list of minion numbers (which are integers), depicting the optimal order in which to interrogate the minions. Since there could be multiple optimal orderings, return the lexicographically first optimal list.

There will be at-least two and no more than 50 minions. All the integers in the input will be positive integers, no more than 1024.
 * 
 *
 */

public class MinionInterrogation {
	static class TreeNode implements Comparable<TreeNode> {
		int id;
		int[] value;
		TreeNode left;
		TreeNode right;
		
		public TreeNode(int id, int[] value) {
			this.id = id;
			this.value = value;
		}
		
		public int compareTo(TreeNode other) {
			float forward = this.value[0] + other.value[0] * (1 - (float)(this.value[1]) / this.value[2]);
			float backward = other.value[0] + this.value[0] * (1 - (float)(other.value[1]) / other.value[2]);
			
			if (forward < backward) {
				return -1;
			} else if (forward > backward) {
				return 1;
			} else if (id < other.id) {
				return -1;
			} else if (id > other.id) {
				return 1;
			}
			
			return 0;
		}
	}
	
	static class BinarySearchTree {
		TreeNode root;
		
		public void insert(int id, int[] value) {
			root = insert(root, new TreeNode(id, value));
		}
		
		private TreeNode insert(TreeNode parent, TreeNode node) {						
			if (parent == null) {
				return node;
			} else if (node.compareTo(parent) < 0) {
				parent.left = insert(parent.left, node);
			} else if (node.compareTo(parent) > 0) {
				parent.right = insert(parent.right, node);
			}
			
			return parent;
		}
			
		public int inOrderTraversal(int loc, int[] arr, TreeNode node) {
			if (node == null)
				node = root;
			
			if (node.left != null) {
				loc = inOrderTraversal(loc, arr, node.left);
			}
			
			arr[loc++] = node.id;
			
			if (node.right != null) {
				loc = inOrderTraversal(loc, arr, node.right);
			}
			
			return loc;
		}
		
		public TreeNode getRoot() { return root; }
	}
	
	public static void main(String[] args) {
		int[][] minions = {{390, 185, 624}, {686, 351, 947}, {276, 1023, 1024}, {199, 148, 250}};
		
		for (int i : answer(minions)) {
			System.out.println(i);
		}
	}
	
	public static int[] answer(int[][] minions) {
		int length = minions.length;
		BinarySearchTree bst = new BinarySearchTree();
		int[] answer = new int[length];
		
		for (int i = 0; i < length; i++) {
			bst.insert(i, minions[i]);
		}
		
		bst.inOrderTraversal(0, answer, bst.getRoot());
		
		return answer;
	}
}
