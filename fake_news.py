""" 
File: fake_news.py
Author: Christopher De Vault
Course: CSC120 FA 2024
Purpose: This program utilizes a linked list data structure to analyze a csv
file containing various U.S. news articles. It parses the article titles from
the file and adds its keywords to the linked list with the number of 
occurences with the word as an attribute. It can retrieve words according to 
user inputs from the sorted linked list. 
"""
import csv
import string


class LinkedList:
    """This class represents a linked list data structure. It initializes the 
    head to empty and has various methods to help handle the csv file data. 
    
    The class defines simple special methods along with methods to add, 
    remove and update nodes. It utilizes unique methods tp retrieve a count
    at a specified node position and print nodes with at least a certain 
    target count. 
    """
    def __init__(self):
        """This function intialzes an empty linked list."""
        self._head = None
        
    def is_empty(self):
        return self._head == None
    
    def head(self):
        if self.is_empty():
            return None
        return self._head 
    
    def update_count(self, word):
        """Updates the count of a word in the linked list if it exists. If not, 
        it creates a node with that word and sets its count to zero. 

        Args:
            word (str): A word from a news article title.
        """
        # Create a node if the list is empty
        if self.is_empty():
            n = Node(word)
            self._head = n
            self._count = 1
        # Search for target word through iterating over linked list
        curr = self._head 
        while curr is not None:
            if curr._word == word:
                curr.incr()
            curr = curr._next 
    
    # remove node from head of list
    def rm_from_hd(self):
        """Removes a node from the head of the linked list and sets its new 
        head to the following node. 
        
        Raises: 
            ValueError: Cannot remove from head of an empty linked list.

        Returns:
            curr (node): A reference to the removed node. 
        """
        if self.is_empty():
            raise ValueError("List is empty.")
        curr = self._head 
        self._head = curr._next
        return curr
    
    # source: long problem for sorting a linked list
    # insert node 2 after node 1
    def insert_after(self, node1, node2):
        assert node1 is not None
        node2._next = node1._next
        node1._next = node2
    
    # source: long problem for sorting a linked list
    def sort(self):
        """This method returns a descending order sorted linked list by 
        creating a sorted list that is then referenced by the existing linked
        list. It returns a sorted list that maintains descending order. 
        """
        # No need to sort if the list is empty or has one element
        if self._head is None or self._head._next is None:
            return  
        
        sorted_list = LinkedList()  # Initialize sorted_list as a LinkedList

        while self._head is not None:
            # Remove the head of the list to be sorted
            curr_element = self._head
            self._head = self._head._next  # Move head to the next element
            
            # Insert curr_element into the correct position in the sorted list
            if sorted_list._head is None or \
            sorted_list._head._count < curr_element._count:
                # Insert at the head of the sorted list if the largest element
                curr_element._next = sorted_list._head
                sorted_list._head = curr_element
            else:
                # Find the correct position in the sorted list
                current = sorted_list._head
                while current._next is not None and current._next._count\
                >= curr_element._count:
                    current = current._next
                
                # Insert curr_element after 'curr'
                curr_element._next = current._next
                current._next = curr_element
        
        # Update the original list's head to the sorted list's head
        self._head = sorted_list._head
            
    def get_nth_highest_count(self, n):
        """Returns the count associated with the node in the linked list at
        position n.

        Args:
            n (int): The position in the linked list where the node count is 
            to be retrieved.

        Raises:
            IndexError: Raises an error if n is outside the range of the list.

        Returns:
            curr_count (node): the count of the node at postion n.
        """
        position = 0
        curr = self._head
        while curr._next is not None:
            if position == n:
                return curr._count
            else:
                curr = curr._next 
                position += 1
        
        if curr._next is None:
            if position == n:
                return curr._count
        
        raise IndexError("n is outside range of linked list nodes")
            
    def print_upto_count(self, k):
        """Prints all words in the linked list that have count >= k.

        Args:
            k (int): The target count.
        """
        curr = self._head
        while curr._next is not None:
            if curr._count >= k:
                print(curr)
            curr = curr._next
        
        if curr._next is None:
            if curr._count >= k:
                print(curr)
    
    # source: long problem for sorting a linked list
    # add a node to the head of the list 
    def add(self, word):
        node = Node(word)
        node._next = self._head
        self._head = node
    
    # source: ll_find short problem
    # search the list for item and return True if found and False otherwise
    def find(self, item):
        curr = self._head
        while curr is not None:
            if curr._word == item:
                return True
            curr = curr.next()
        return False
    
    def __str__(self):
        string = 'List-->'
        curr = self._head
        while curr is not None:
            string += str(curr) + ","
            curr = curr._next  
        return string
        
        
class Node:
    """This class represents a node in the linked list. It has attributes
    unique to our csv file input such as word and count. 
    
    This class implements only simple getter, setter, and special methods 
    along with a way to increment the node's count.
    """
    def __init__(self, word):
        """Intializes a node with self._word set to the argument passed 
        and sets the count to 1. 

        Args:
            word (str): A word from a news article title. 
        """
        self._word = word
        self._count = 1
        self._next = None
    
    def word(self):
        return self._word
    
    def count(self):
        return self._count
    
    def next(self):
        return self._next 
    
    def set_next(self, target):
        self._next = target
        
    def incr(self):
        """Increments the count of the node by 1."""
        self._count += 1
        
    def __str__(self):
        return " {}:{}".format(self._word, self._count)
 
    
def process_titles(filename):
    """This function takes the file input and extracts then parses the title to
    be added as nodes into the linked list utilizing the python csv module and 
    helper method. It returns a 2D list containing all properly formatted 
    titles.

    Args:
        filename (csv file): Contains data about U.S. news articles. 

    Returns:
        titles (2D list): A list of lists of cleaned titles. 
    """
    titles = []
    infile = open(filename)
    csvreader = csv.reader(infile)
    for itemlist in csvreader:
        cleaned_list = []
        # ignore file header
        if itemlist[0][0] != "#":
            # extract title from line and remove punctuation
            title = itemlist[4]
            clean_title = remove_punc(title)
            title_list = clean_title.split()
            
            # remove words with length <= 2 and make case insensitive
            for word in title_list:
                if len(word) > 2:
                    cleaned_list.append(word.lower())
            titles.append(cleaned_list)

    infile.close()
    return titles


# source: lab 6 problem 2
# helper method to remove punctuation from titles
def remove_punc(title):
    """Helps remove unwanted punctuation with the python string module. 
    Replaces any punctuation missed by the module with whitespace.

    Args:
        title (str): A U.S. news article title.

    Returns:
        new_title (str): The title with punctuation removed. 
    """
    clean_title = title.strip(string.punctuation)
    new_title = ""
    for word in clean_title:
        for char in word:
            if char not in string.punctuation:
                new_title += char
            else:
                new_title += " "
                
    return new_title
    
    
def main():
    """The main function adds the words in each news title as a node to the 
    linked list or updates the count if it exists. It sorts the linked list 
    by descending order of count. The retrieves all words with count >= k as. 
    """
    titles_llist = LinkedList()
    filename = input()
    title_collection = process_titles(filename)  # 2D List
    for title in title_collection:
        for word in title:
            if titles_llist.find(word):
                titles_llist.update_count(word)
            else:
                titles_llist.add(word)
    
    titles_llist.sort()
    n = int(input())
    k = titles_llist.get_nth_highest_count(n)
    titles_llist.print_upto_count(k)
            
main()