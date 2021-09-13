package l07;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BinarySearchTree<E extends Comparable<E>> {
	BSTNode<E> root;
	
	public BinarySearchTree() {
		//initialize empty tree
		this.root = null;
	}
	
	public BinarySearchTree(E... values) {
		//populate tree with nodes in order that they are received
		for(int i = 0; i < values.length; i++) {
			insert(values[i]);
		}
		
	}
	
	public void insert(E nodeValue) { 		//Modified version of insertPoint and insert algorithm from notes combined in one
		BSTNode<E> newNode = new BSTNode<>(nodeValue);
		if(this.root == null) {
			this.root = newNode;	//sets node as root if tree is empty	
		}
		else {
			BSTNode<E> current = this.root;
			
			while(true) { 					
				newNode.parent = current;
			
				if(nodeValue.compareTo(current.data) < 0) {  		//moves to left child node if less than parent
					current = current.left;
					if(current == null) {
						newNode.parent.left = newNode;
						return;
					}
				}
				else if(nodeValue.compareTo(current.data) > 0){		// moves right if greater than parent node
					current = current.right;
					if(current == null) {
						newNode.parent.right = newNode;
						return;
					}
				}
			}	
		}
	}
	
	public void delete(E value) {
		delete(findDelete(value));
	}
	
	
	private void delete(BSTNode<E> node) {
		if(node == null) {
			System.out.println("Nothing to delete");
		}
		else if(isLeaf(node) == true) {					//Checks if is leafNode and removes node
			if(isLeftChild(node) == true) {
				node.parent.left = null;
			}
			else if(isRightChild(node) == true) {
				node.parent.right = null;
			}
		}
		else if(numChildren(node) == 1) {			//Checks if node has 1 child and checks whether to remove from right or left
			if(isLeftChild(node) == true) {
				if(node.left != null) {
					node.parent.left = node.left;
				}
				else if(node.right != null) {
					node.parent.left = node.right;
				}
			}
			else if(isRightChild(node) == true) {
				if(node.left != null) {
					node.parent.right = node.left;
				}
				else if(node.right != null) {
					node.parent.right = node.right;
				}
			}
		}
		else if(numChildren(node) == 2) {
			E minValue = minValue(node.right);
			node.data = minValue;
			delete(node.right);
			
		}
	}
	
	private E minValue(BSTNode<E> node) {
		 
        if(node.left != null) {
            minValue(node.left);
        }
        return node.data;
    }
	
	
	
	
	public boolean isEmpty() {
		if(this.root == null) {
			return true;
		}
		return false;
	}
	
	
	private BSTNode<E> findDelete(E value) {
		BSTNode<E> current = this.root;
		
		while(current != null) {
			if(value.compareTo(current.data) == 0) {
				return current;
			}
			else if(value.compareTo(current.data) < 0) {
				current = current.left;
			}
			else if(value.compareTo(current.data) > 0) {
				current = current.right;
			}
		}
		return null;
	}
	
	
	
	public boolean find(E value) {
		BSTNode<E> current = this.root;
		
		while(current != null) {
			if(value.compareTo(current.data) == 0) {
				return true;
			}
			else if(value.compareTo(current.data) < 0) {
				current = current.left;
			}
			else if(value.compareTo(current.data) > 0) {
				current = current.right;
			}
		}
		return false;
	}
	
	
	
	private boolean isLeaf(BSTNode<E> node) {
		if(node.left == null && node.right == null) {
			return true;
		}
		return false;
	}
	
	
	private int numChildren(BSTNode<E> node) {
		int count = 0;
		
		if(node.left != null) {
			count++;
		}
		if(node.right != null) {
			count++;
		}
		return count;	
	}
	
	
	
	private boolean isLeftChild(BSTNode<E> node) {
		if(node.data.compareTo(node.parent.data) < 0) {
			return true;
		}
		return false;
	}
	
	private boolean isRightChild(BSTNode<E> node) {
		if(node.data.compareTo(node.parent.data) >= 0) {
			return true;
		}
		return false;
	}
	
	
	public ArrayList<E> preorder() {
		BSTNode<E> currentNode = this.root;
		ArrayList<E> preorderlist = new ArrayList<>();
		preorder(preorderlist, currentNode);
		return preorderlist;
	}
	
	private ArrayList<E> preorder(ArrayList<E> list, BSTNode<E> node) {
		if(node != null) {
			list.add(node.data);
			preorder(list, node.left);
			preorder(list, node.right);
		}
		return list;
		
	}
	
	public ArrayList<E> inorder() {
		BSTNode<E> currentNode = this.root;
		ArrayList<E> inorderList = new ArrayList<>();
		inorder(inorderList, currentNode);
		return inorderList;
	}
	
	private ArrayList<E> inorder(ArrayList<E> list, BSTNode<E> node) {
		if(node != null) {
			inorder(list, node.left);
			list.add(node.data);
			inorder(list, node.right);
		}
		return list;
	}
	
	public ArrayList<E> postorder() {
		BSTNode<E> currentNode = this.root;
		ArrayList<E> postorderList = new ArrayList<>();
		postorder(postorderList, currentNode);
		return postorderList;
	}
	
	private ArrayList<E> postorder(ArrayList<E> list, BSTNode<E> node) {
		if(node != null) {
			postorder(list, node.left);
			postorder(list, node.right);
			list.add(node.data);
		}
		return list;
	}
	
	public ArrayList<E> breadthfirst() {
		ArrayList<E> layerList = new ArrayList<>();
		Queue<BSTNode<E>> queue = new LinkedList<>();
		Set<BSTNode<E>> seen = new HashSet<>();
		queue.add(this.root);
		BSTNode<E> current;
		
		while(!queue.isEmpty()) {
			current = queue.poll();
			if(!seen.contains(current)) {
				seen.add(current);
				layerList.add(current.data);
			}
			if(current.left != null) {
				if(!seen.contains(current.left)) {
					queue.add(current.left);
				}
			}
			if(current.right != null) {
				if(!seen.contains(current.right)) {
					queue.add(current.right);
				}
			}
		}
		return layerList;
	}
	
	public String toString() {
		if (this.root.right != null) {
	        this.toString(this.root.right, true, "");
	    }

	    toString(this.root);

	    if (this.root.left != null) {
	        this.toString(this.root.left, false, "");
	    }
	    return null;
	}
	
	private String toString(BSTNode<E> node, boolean isRight, String indent) {
		if (node.right != null) {
	        toString(node.right, true, indent + (isRight ? "        " : " |      "));
	    }

	    System.out.print(indent);

	    if (isRight) {
	        System.out.print(" /");
	    }
	    else {
	        System.out.print(" \\");
	    }
	    System.out.print("----- ");
	    toString(node);
	    if (node.left != null) {
	        toString(node.left, false, indent + (isRight ? " |      " : "        "));
	    }
	    return null;
	}
	
	private String toString(BSTNode<E> node) {
		if (node == null) {
	        System.out.print("<null>");
	    }
	    else {
	        System.out.print(node.data);
	    }
	    System.out.println();
	    return null;
	}	
	
}
