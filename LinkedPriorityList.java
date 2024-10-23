package model;

/**
 * A priority list implementation using a singly-linked data structure.
 * This class supports basic operations such as insert, remove, and adjusting
 * priorities of elements in a zero-based indexed list where the element at
 * index 0 has the highest priority.
 *
 * @param <E> the type of elements in this list
 * @author cjdevault
 */
public class LinkedPriorityList<E> {

    private class Node {
        E data;
        Node next;

        public Node(E data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node first;
    private int size;
    
    // Create an empty list with zero elements
    public LinkedPriorityList() {
        first = null;
        size = 0;
    }
    
    // Return the size of the list
    public int size() {
        return size;
    }
    
    /**
	 * Return true if there are zero elements in this PriorityList
	 * 
	 * @return true if size() == 0 or false if size() > 0
	 */
    public boolean isEmpty() {
        return size == 0;
    }

    // Return the node at a specified index
    private Node getNodeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
        Node current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    /**
	 * If possible, insert the element at the given index. If index is out of the
	 * valid range of 0..size(), throw new IllegalArgumentException(); When size is
	 * 3, the only possible values for index are 0, 1, 2, and 3 because you can add
	 * an element as the new last, at index size().
	 * 
	 * @param index The index of the element to insert.
	 * @param el    The element to insert
	 * @throws IllegalArgumentException
	 */
    public void insertElementAt(int index, E el) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
        if (index == 0) {
            first = new Node(el, first);
        } else {
            Node prev = getNodeAt(index - 1);
            prev.next = new Node(el, prev.next);
        }
        size++;
    }
    
    /**
	 * If possible, remove the element at the given index. If index is out of the
	 * valid range of 0..size()-1, throw new IllegalArgumentException(); When size
	 * is 3, the only possible values for the parameter index are 0, 1, and 2.
	 * 
	 * @param index The index of the element to remove. All other elements must
	 *              remain in the list in contiguous memory.
	 * @throws IllegalArgumentException
	 */
    public void removeElementAt(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
        if (index == 0) {
            first = first.next;
        } else {
            Node prev = getNodeAt(index - 1);
            prev.next = prev.next.next;
        }
        size--;
    }

    /**
	 * If possible, swap the element located at index with the element at index + 1.
	 * An attempt to lower the priority of an element at index size()-1 has no
	 * effect. All other element must remain in the list. If index is out of the
	 * valid range of 0..size()-1, throw new IllegalArgumentException(); When size
	 * is 3, the only possible values for the parameter index are 0, 1, and 2.
	 * 
	 * @param index The index of the element to be changed with one next to it.
	 * @throws IllegalArgumentException
	 */
    public void lowerPriorityOf(int index) {
        // Ensure the operation is only attempted on a non-empty list and within valid index range
        if (size == 0) {
            throw new IllegalArgumentException("Operation is invalid on an empty list.");
        }
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }
        // Specifically for the last element, or when there's only one element,
        // lowering the priority should have no effect, hence, just return without error.
        if (index == size - 1 || size == 1) {
            return; // No effect for the last element or if there's only one element
        }

        // Proceed with lowering the priority by swapping the data with the next node
        Node current = getNodeAt(index);
        Node next = current.next;
        E temp = current.data;
        current.data = next.data;
        next.data = temp;
    }

    /**
	 * If possible, swap the element located at index with the element at index-1.
	 * An attempt to raise the priority at index 0 has no effect and this method
	 * does not throw an IllegalArgumentException. If index is out of the valid
	 * range of 0..size()-1, throw new IllegalArgumentException();. When size is 3,
	 * the only possible values for for the parameter index are 0, 1, and 2.
	 * 
	 * @param index The index of the element to be changed with one next to it.
	 * @throws IllegalArgumentException
	 */
    public void raisePriorityOf(int index) {
        if (index < 1 || index >= size) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
        if (index == 1) {
            Node temp = first.next;
            first.next = temp.next;
            temp.next = first;
            first = temp;
        } else {
            lowerPriorityOf(index - 1);
        }
    }

    /**
	 * If possible, return a reference to the element at the given index. If index
	 * is out of the valid range of 0..size()-1, throw new
	 * IllegalArgumentException();. When size is 3, the only possible values for the
	 * parameter index are 0, 1, and 2.
	 * 
	 * @param index The index where the element to retrieve should be.
	 * @return A reference to the element at index index.
	 * @throws IllegalArgumentException
	 */
    public E getElementAt(int index) {
        Node current = getNodeAt(index);
        return current.data;
    }

    public Object[] toArray() {
        Object[] result = new Object[size];
        Node current = first;
        for (int i = 0; i < size; i++) {
            result[i] = current.data;
            current = current.next;
        }
        return result;
    }
    
    /**
	 * If possible, move the element at the given index to the end of this list. An
	 * attempt to move the last element to the last has no effect. If the index is
	 * out of the valid range 0..size()-1 throw new IllegalArgumentException(); When
	 * size is 3, the only possible values for the parameter index are 0, 1, and 2.
	 * 
	 * @param index The index of the element to be changed to be the last index.
	 * @throws IllegalArgumentException
	 */
    public void moveToLast(int index) {
        if (index < 0 || index >= size) {
            // Index is out of bounds
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }

        if (index == size - 1 || size == 0 || size == 1) {
            // If trying to move the last element, the list is empty, or has only one element, do nothing
            return;
        }

        // If moving the first element
        if (index == 0) {
            Node movingNode = first;
            first = first.next; // First is now the second element
            
            Node last = first;
            while (last.next != null) {
                // Iterate to find the last node
                last = last.next;
            }
            
            // Append the movingNode to the end
            last.next = movingNode;
            movingNode.next = null; // Since it's the last element now
        } else {
            // For moving an element that is not the first
            Node prev = getNodeAt(index - 1);
            Node movingNode = prev.next;
            
            // If the moving node is not the last
            if (movingNode.next != null) {
                prev.next = movingNode.next; // Link prev to next, effectively removing movingNode from its current place
                
                // Move to last
                Node last = first;
                while (last.next != null) {
                    last = last.next;
                }
                last.next = movingNode;
                movingNode.next = null;
            }
            // If the movingNode is the second to last, it simply becomes the last
            // This case is already handled by our checks and loop.
        }
    }


	/**
	 * If possible, move the element at the given index to the front of this list An
	 * attempt to move the top element to the top has no effect. If the index is out
	 * of the valid range of 0..size()-1, throw new IllegalArgumentException(); When
	 * size is 3, the only possible values for the parameter index are 0, 1, and 2.
	 * 
	 * @param index The index of the element to be changed to the first index.
	 * @throws IllegalArgumentException
	 */
    public void moveToTop(int index) {
        if (index < 0 || index >= size) {
            // If the index is out of bounds, throw an IllegalArgumentException.
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
        if (index == 0 || size == 0 || size == 1) {
            // If the element is already at the top, or if the list is empty or has only one element, do nothing.
            return;
        }

        // If moving the first element after the head to the top
        if (index == 1) {
            Node second = first.next;
            first.next = second.next;
            second.next = first;
            first = second;
            return;
        }

        Node prev = first;
        for (int i = 0; i < index - 1; i++) {
            prev = prev.next; // Find the node just before the one to move
        }

        Node toMove = prev.next; // This is the node to move to the top
        prev.next = toMove.next; // Remove toMove from its current position

        toMove.next = first; // Insert toMove at the beginning
        first = toMove; // Update the first reference to the new top node
    }
}

