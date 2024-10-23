"""
File: dates.py
Author: Christopher De Vault
Course: CSC 120 FALL24
Purpose: The program manages a data base of events by creating date objects
from given input strings and associates a date with an event. It takes date 
and events inputs and will return all the events for a given date. 
"""

class Date:
    """ This class represents a date and corresponding event on that date. 
    
        The class defines methods to retrieve dates and events as well as
        adding an event to a collection of events on a single date. 
    """
    def __init__(self, date, event):
        """ This function defines a date using an outside function to
            standardize the format into canonical date format. It also
            creates a list to store multiple events for one date. 

        Args:
            date (str): A string representing a date in any valid format.  
            event (str): A string representing an event. 
        """
        self._date = canonicalize_date(date)
        self._events = [event]  # Collect events for single date in list

    def get_date(self):
        return self._date

    def get_events(self):
        return sorted(self._events)  # Events are sorted alphabetically

    def add_event(self, event):
        """ This method simply appends an event to the collection of events
            for a date since it was initialized as a list.

        Args:
            event (str): 
        """
        self._events.append(event)

    def __str__(self):
        return "{} : {}".format(self._date, ", ".join(self.get_events()))

class DateSet:
    def __init__(self):
        """ This class represent a dictionary of dates to help organize date
            objects. 
            
            The key is defined as a date with its value pair as the 
            date object. The methods include adding a new date to the
            dictionary as well as retrieving the events for the date. 
        """
        self._dates = {}

    def add_date(self, date, event):
        """ This method adds a date to the dictionary and assigns its 
            corresponding date object as the value.

        Args:
            date (str): A string representing a date in any valid format.  
            event (str): A string representing an event. 
        """
        canonical_date = canonicalize_date(date)
        if canonical_date not in self._dates:
            # for each date key make a date object value 
            self._dates[canonical_date] = Date(date, event)
        else:
            # use add_event for existing keys
            self._dates[canonical_date].add_event(event)

    def get_events_for_date(self, date):
        """ This method retrieves events for any date query. It leverages the 
            dictionary keys to easily find the date object as well as the 
            get_events method to return the list of events. 

        Args:
            date (str): A string representing a date in any valid format.  

        Returns:
            events (list): A list representing all the events for a date.
        """
        canonical_date = canonicalize_date(date)
        if canonical_date in self._dates:
            return self._dates[canonical_date].get_events()
        else:
            return []

    def __str__(self):
        result = []
        for date_obj in self._dates.values():
            result.append(str(date_obj))
        return "\n".join(result)

def canonicalize_date(date_str):
    """ This function helps standardize the format of any inputted date string. 
        It will parse out the string, identify the type of format it is in, 
        retrieve data necessary for a canonical format, then return the 
        canonical date using the format() method. 

    Args:
        date_str (str): Any string representing a date, read from the input 
        file. 

    Returns:
        (str): The canonical date. 
    """
    # Use dictionary to organize months to accomodate month name inputs
    months = {
        "Jan": 1, "Feb": 2, "Mar": 3, "Apr": 4, "May": 5, "Jun": 6,
        "Jul": 7, "Aug": 8, "Sep": 9, "Oct": 10, "Nov": 11, "Dec": 12
    }
    # Can identify date formats by using specific delimeters in split()
    # yyyy-mm-dd
    if "-" in date_str and len(date_str.split("-")) == 3:
        year, month, day = date_str.split("-")
        return "{}-{}-{}".format(int(year), int(month), int(day))

    # mm/dd/yyyy
    elif "/" in date_str and len(date_str.split("/")) == 3:
        month, day, year = date_str.split("/")
        return "{}-{}-{}".format(int(year), int(month), int(day))

    # MonthName dd yyyy
    elif len(date_str.split()) == 3:
        month_name, day, year = date_str.split()
        month = months[month_name]
        return "{}-{}-{}".format(int(year), month, int(day))

def process_infile(filename):
    """ This function processes the input file and indetifies, in each line, 
        the type of operation being requested. It will create a date object
        using the date class if the input is type "I" and will retrieve
        the events from that date object is the input type is "R". This
        function acts as the main logical framework for the entire program. 

    Args:
        filename (txt file): A file of strings with each line being an 
        operation type followed by the corresponding information for that 
        type. 
    """
    date_set = DateSet()
    infile = open(filename, "r")   
    for line in infile:
        line = line.strip()
        # Op type 'I' => add date and event to dictionary
        if line.startswith("I"):
            # maxsplit = 1 to preserve event string format
            parts = line.split(":", 1)
            op_type, rest = parts[0].strip(), parts[1].strip()
            if op_type[0] == "I":
                date_str = op_type[1:].strip()
                event = rest
                date_set.add_date(date_str, event)
            else:
                print("Error - Illegal operation.")
        # Op type 'R' => retrieve the date object for the specified date
        elif line.startswith("R"):
            date_str = line[1:].strip()
            events = date_set.get_events_for_date(date_str)
            canonical_date = canonicalize_date(date_str)
            for event in events:
                print("{}: {}".format(canonical_date, event))

        else:
            print("Error - Illegal operation.")
 
def main():
    """ The main function uses a silent prompt for a file name and calls 
        process_infile() to help parse out the information given in the file.
    """
    filename = input()
    process_infile(filename)

main()