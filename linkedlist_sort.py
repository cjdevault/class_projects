"""
File: linkedlist_sort.py
Author: Christopher De Vault
Course: CSC120 FA 2024
Purpose: This program is an exercise in implementing a specific sorting
algorithm for a linked list. The linked list and node classes were given while
the sort method was left to be implemented. The algorithm returns a sorted 
linked list in descending order. 
"""
class LinkedList:
    def __init__(self):
        self._head = None
    
    # sort the nodes in the list
    def sort(self):
        """
        This method returns a descending order sorted linked list by creating 
        a sorted list that is then referenced by the existing linked list, thus
        modifying the linked list. It creates a sorted list that maintains
        descending order rank from each node in the original linked list. 
        
        Returns:
            None
        """
        # your code goes here
        if self._head is None or self._head._next is None:
            return  # No need to sort if the list is empty or has one element
        
        sorted_list = LinkedList()  # Initialize sorted_list as a LinkedList

        while self._head is not None:
            # Remove the head of the list to be sorted
            curr_element = self._head
            self._head = self._head._next  # Move head to the next element
            
            # Insert curr_element into the correct position in the sorted list
            if sorted_list._head is None or \
            sorted_list._head._value < curr_element._value:
                # Insert at the head of the sorted list if the largest element
                curr_element._next = sorted_list._head
                sorted_list._head = curr_element
            else:
                # Find the correct position in the sorted list
                current = sorted_list._head
                while current._next is not None and current._next._value \
                >= curr_element._value:
                    current = current._next
                
                # Insert curr_element after 'current'
                curr_element._next = current._next
                current._next = curr_element
        
        # Update the original list's head to the sorted list's head
        self._head = sorted_list._head
        
    # add a node to the head of the list
    def add(self, node):
        node._next = self._head
        self._head = node
        
    # remove a node from the head of the list and return the node
    def remove(self):
        assert self._head != None
        _node = self._head
        self._head = _node._next
        _node._next = None
        return _node
    
    # insert node2 after node1
    def insert(self, node1, node2):
        assert node1 != None
        node2._next = node1._next
        node1._next = node2
    
    def __str__(self):
        string = 'List[ '
        curr_node = self._head
        while curr_node != None:
            string += str(curr_node)
            curr_node = curr_node.next()
        string += ']'
        return string

class Node:
    def __init__(self, value):
        self._value = value
        self._next = None
    
    def __str__(self):
        return str(self._value) + "; "
    
    def value(self):
        return self._value
    
    def next(self):
        return self._next

def main():
    # Initialize linked list, input file, and Python list
    linked_list = LinkedList()
    filename = input()
    
    infile = open(filename, "r")
    for line in infile:
        # Process each line and add numbers as nodes to the linked list
        numbers = line.strip().split()
        for num in numbers:
            node = Node(int(num))
            linked_list.add(node)
    
    # Sort the linked list in descending order
    linked_list.sort()
    # Print the sorted list
    print(linked_list)
        
main()