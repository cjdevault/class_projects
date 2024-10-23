/**
 * This collection class maintains a set of values in their natural order as
 * defined by compareTo. The only type that can be stored in this collection
 * must implement interface Comparable<T>
 *
 * Several methods (at the beginning) are given, another 11 methods must be
 * implemented. The JUnit test completely tests everything. Run code coverage at
 * the end to ensure 100% coverage and all assertions pass in 42 unit tests.
 *
 * To see a summary of TODOs, enter the following in Eclipse:
 *
 * ... Window > Show View > Tasks
 *
 * @author Rick Mercer and CJ De Vault
 *
 * @param <Type> The type argument when constructed can be any type that
 *               implements Comparable.
 */
public class OrderedSet<Type extends Comparable<Type>> {
	// A private class that stores one node in a Binary Search Tree
	private class TreeNode {
		private TreeNode right;
		private Type data;
		private TreeNode left;

		public TreeNode(Type element) {
			left = null;
			data = element;
			right = null;
		}
	} // end class TreeNode

	private TreeNode root;

	// Create an empty OrderedSet
	public OrderedSet() {
		root = null;
	}

	// Given: Insert element into this OrderedSet and return true keeping this an
	// OrderedSet. If element exists, do not change this OrderedSet and return
	// false. A set must have unique elements. Insert element into the correct
	// location to maintain a BinarySearchTree. This algorithm runs O(log n).
	//
	public boolean insert(Type element) {
		if (this.contains(element))
			return false;
		else {
			root = add(root, element);
			return true;
		}
	}

	// Given: A private helper method for insert. Runs O(log)
	private TreeNode add(TreeNode t, Type el) {
		if (t == null)
			t = new TreeNode(el);
		else if (el.compareTo(t.data) < 0)
			t.left = add(t.left, el);
		else if (el.compareTo(t.data) > 0)
			t.right = add(t.right, el);
		return t;
	}

	/*- This different comment beginning keeps leading spaces in comments
	  Given: Return one string that concatenates all elements in this
	 OrderedSet as they are visited in order. Elements are are separated
	 by spaces as in "1 4 9". No space is at the end thanks to trim().
	    4
	   / \
	  1   9
	   
	 */
	@Override
	public String toString() {
		return getAll(root).trim(); // No leading or trailing whitespace
	}

	private String getAll(TreeNode t) {
		if (t == null)
			return "";
		else
			return getAll(t.left) + (t.data + " ") + getAll(t.right);
	}

	// 1) The number of elements in this OrderedSet, which should be 0 when
	// first constructed. This may run O(n) or O(1)--your choice.
	public int size() {
		// TODO Implement this method
		return sizeHelper(root);
	}

	// Private helper method to get size of OrderedSet
	private int sizeHelper(TreeNode t) {
		if (t == null)
			return 0;
		else {
			return (sizeHelper(t.left) + 1 + sizeHelper(t.right));
		}
	}

	//////////////////////////////////////////////////////////////////////////
	// 2) Return true if search equals an element in this OrderedSet.
	// If search is not found, return false.
	//
	public boolean contains(Type search) {
		// TODO Implement this method
		return containsHelper(root, search);
	}

	private boolean containsHelper(TreeNode t, Type search) {
		if (t == null)
			return false;

		// Create comparison variable for parameter node
		int compare = search.compareTo(t.data);

		if (compare == 0) {
			// Found the element
			return true;
		} else if (compare < 0) {
			// Search in the left subtree
			return containsHelper(t.left, search);
		} else {
			// Search in the right subtree
			return containsHelper(t.right, search);
		}
	}

	//////////////////////////////////////////////////////////////////////////
	// 3) Return the element in this OrderedSet that is greater than all other
	// elements If this BST is empty, return null.
	//
	public Type max() {
		// TODO Implement this method
		return maxHelper(root);
	}

	private Type maxHelper(TreeNode t) {
		// Base condition: If the tree is empty or we have reached the end
		if (t == null) {
			return null;
		} else {
			// If there's no right child, then t is the maximum element
			if (t.right == null) {
				return t.data;
			} else {
				// Move to the right child to find the max element
				return maxHelper(t.right);
			}
		}
	}

	//////////////////////////////////////////////////////////////////////////
	// 4) Return the element in this OrderedSet that is less than all other
	// elements. If this BST is empty, return null.
	//
	public Type min() {
		// TODO Implement this method
		// Check if the tree is empty
		if (root == null) {
			return null; // The tree is empty, so it doesn't have a minimum element
		}

		return minHelper(root);
	}

	// Helper method to find the minimum element
	private Type minHelper(TreeNode t) {
		// If there's no left child, then t contains the minimum element
		if (t.left == null) {
			return t.data;
		} else {
			// Keep moving to the left child to find the minimum element
			return minHelper(t.left);
		}
	}

	//////////////////////////////////////////////////////////////////////////
	/*-   
	 *
	5) Return how many nodes are at the given level. If level > the height of the
	tree, return 0. Remember that an empty tree has a height of -1 (page 252).
	    4      There is 1 node on level 0
	   / \
	 3    7    There are 2 nodes on level 1
	/ \    \
	1   5    9  There are 3 nodes in level 2.
	 
	There are 0 nodes at any levels >= 3
	*
	*/
	public int nodesAtLevel(int level) {
		// TODO Implement this method
		return nodesAtLevelHelper(root, level, 0);
	}

	// Helper method to count nodes at a given level with recursion
	private int nodesAtLevelHelper(TreeNode node, int targetLevel, int currentLevel) {
		// Base case: if the node is null, return 0 (no nodes at this level)
		if (node == null) {
			return 0;
		}

		// If the current level matches the target level, return 1 (this node counts)
		if (currentLevel == targetLevel) {
			return 1;
		}

		// Recurse left and right, incrementing the current level
		int leftCount = nodesAtLevelHelper(node.left, targetLevel, currentLevel + 1);
		int rightCount = nodesAtLevelHelper(node.right, targetLevel, currentLevel + 1);

		// Sum the counts from the left and right subtrees and return it
		return leftCount + rightCount;
	}

	//////////////////////////////////////////////////////////////////////////
	// 6) Return the height of the tree.
	//
	public int height() {
		// TODO Implement this method
		return heightHelper(root);
	}

	// Helper method to calculate the height of the tree recursively
	private int heightHelper(TreeNode node) {
		// Base case: an empty tree or leaf's child has a height of -1
		if (node == null) {
			return -1;
		}

		// Recursively find the height of the left and right subtrees
		int leftHeight = heightHelper(node.left);
		int rightHeight = heightHelper(node.right);

		// The height of the tree is the max of the heights of the subtrees, plus one
		return Math.max(leftHeight, rightHeight) + 1;
	}

	//////////////////////////////////////////////////////////////////////////
	// 7) Return the intersection of this OrderedSet and the other OrderedSet
	// as a new OrderedSet. Do not modify this OrderedSet or the other
	// OrderedSet that is passed as an argument to this method. The intersection
	// of two sets is the set of elements that are in both sets. For example,
	// the intersection of {2, 4, 5, 6, 88} and {2, 5, 6, 9} is {2, 5, 6}.
	// One or both OrderedSets may be empty.
	//
	public OrderedSet<Type> intersection(OrderedSet<Type> other) {
		// TODO Implement this method
		// Hint: You could store elements in an ArrayList<Type> with a traversal
		// to get all elements
		OrderedSet<Type> result = new OrderedSet<>();
		intersectHelper(this.root, other, result);
		return result;
	}

	private void intersectHelper(TreeNode node, OrderedSet<Type> other, OrderedSet<Type> result) {
		if (node == null) {
			return;
		}
		intersectHelper(node.left, other, result);
		if (other.contains(node.data)) {
			result.insert(node.data);
		}
		intersectHelper(node.right, other, result);
	}

	//////////////////////////////////////////////////////////////////////////
	// 8) Return the union of this OrderedSet and the other OrderedSet as
	// a new OrderedSet. Do not modify this OrderedSet or the other OrderedSet.
	// The union of two sets is the set all distinct elements in the collection.
	// The union of {2, 4, 6} and {2, 5, 9} is {2, 4, 5, 6, 9}
	//
	public OrderedSet<Type> union(OrderedSet<Type> other) {
		// TODO Implement this method
		// Hint: Create a new OrderedSet<Type> as a local variable. The return it.
		OrderedSet<Type> result = new OrderedSet<>();

		// Add all elements from this set
		unionHelper(this.root, result);

		// Add all elements from the other set
		unionHelper(other.root, result);

		return result;
	}

	private void unionHelper(TreeNode node, OrderedSet<Type> result) {
		if (node == null) {
			return;
		}
		unionHelper(node.left, result); // Traverse left subtree
		result.insert(node.data); // Insert current node's data into result set
		unionHelper(node.right, result); // Traverse right subtree
	}

	//////////////////////////////////////////////////////////////////////////
	// 9) Return an OrderedSet that contains all elements that are greater
	// than or equal to the first parameter inclusive and strictly less than
	// the second parameter exclusive.
	//
	public OrderedSet<Type> subset(Type inclusive, Type exclusive) {
		// TODO Implement this method
		// Hint: Create a new OrderedSet<Type> as a local variable and then return it.
		OrderedSet<Type> result = new OrderedSet<>();
		subsetHelper(this.root, inclusive, exclusive, result);
		return result;
	}

	private void subsetHelper(TreeNode node, Type inclusive, Type exclusive, OrderedSet<Type> result) {
		if (node == null) {
			return; // Base case: If the node is null return immediately
		}

		// If node's data is greater than or equal to 'inclusive' explore left subtree
		if (inclusive.compareTo(node.data) <= 0) {
			subsetHelper(node.left, inclusive, exclusive, result);
		}

		// If node's data is within the specified range add it to the result set
		if (inclusive.compareTo(node.data) <= 0 && exclusive.compareTo(node.data) > 0) {
			result.insert(node.data);
		}

		// If node's data is less than 'exclusive', explore right subtree
		if (exclusive.compareTo(node.data) > 0) {
			subsetHelper(node.right, inclusive, exclusive, result);
		}
	}

	//////////////////////////////////////////////////////////////////////////
	// 10) Return the number of leaves in this OrderedSet.
	// Reminder: leaves are the nodes on the last level of the tree.
	//
	public int leaves() {
		// TODO Implement this method
		return leavesHelper(root);
	}

	private int leavesHelper(TreeNode node) {
		// Base case: if the node is null, it's not a leaf, don't count it
		if (node == null) {
			return 0;
		}

		// If both left and right children are null, this is a leaf node
		if (node.left == null && node.right == null) {
			return 1; // Count this leaf
		}

		// Recursively count leaves in both left and right subtrees and sum them up
		return leavesHelper(node.left) + leavesHelper(node.right);
	}

	//////////////////////////////////////////////////////////////////////////
	// 11) If element equals an element in this OrderedSet, remove it and
	// return true. Return false whenever an element is not found. In all cases,
	// this OrderedSet must retain its ordering property. Use the recommended
	// algorithm that was presented during a recent lecture.
	//
	// Warning: This is a challenging problem, especially when the node to
	// remove has two children.
	//
	public boolean remove(Type element) {
		// TODO Implement this method
		TreeNode curr = root, prev = null;
		boolean isLeftChild = true;

		// Step 1: Find the node (curr) and its parent (prev)
		while (curr != null && !curr.data.equals(element)) {
			prev = curr;
			if (element.compareTo(curr.data) < 0) {
				isLeftChild = true;
				curr = curr.left;
			} else {
				isLeftChild = false;
				curr = curr.right;
			}
		}

		// Case 1: Element not found
		if (curr == null) {
			return false;
		}

		// Case 2: Removing the root with no left child
		if (curr == root && curr.left == null) {
			root = curr.right;
			return true;
		} else if (curr.left == null) { // Case 3: Node with no left child
			if (curr == root) {
				root = curr.right;
			} else if (isLeftChild) {
				prev.left = curr.right;
			} else {
				prev.right = curr.right;
			}
		} else { // Case 4: Node with a left child
			// Find max in the left subtree
			TreeNode maxParent = curr;
			TreeNode maxRight = curr.left;
			while (maxRight.right != null) {
				maxParent = maxRight;
				maxRight = maxRight.right;
			}

			// Replace curr data with maxRight data
			curr.data = maxRight.data;

			// Remove the max node in the left subtree
			if (maxParent == curr) {
				maxParent.left = maxRight.left;
			} else {
				maxParent.right = maxRight.left;
			}
		}

		return true;
	}
}
