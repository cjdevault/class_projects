""" 
File: bball.py
Author: Christopher De Vault
Course: CSC120 FA24
Purpose: This program takes a file of women's NCAA D1 basketball programs with
data for the name, conference, wins, and losses. It parses the file using the
Team class to create a team object. It adds teams to the ConferenceSet class, 
a collection of teams in one conference, and calculates the conference with
the highest win ratio. 
"""
class Team:
    """
    This class represents a Team object. Each team object has a name, 
    conference, and win ratio corresponding to the parsed file input.
    """
    def __init__(self, line):
        """
        This function parses a line from the input file and creates the
        necessary attributes for the Team object. 

        Args:
            line (str): A string of characters in the input file. 
        """
        # Use rfind to retrieve the conference
        conf_start = line.rfind("(")
        conf_end = line.rfind(")")
        
        if line[0].isnumeric():
            # The team name starts after the first numeric character
            team_name = line[1:conf_start].strip()
        else:
            # The team name starts at the beginning
            team_name = line[:conf_start].strip()
        self._name = team_name
        
        # Extract conference without parentheses
        conf = line[conf_start+1:conf_end]
        self._conf = conf
        
        # Parse the wins and losses
        WL_list = line[conf_end+1:].strip().split()
        self._wins = int(WL_list[0])
        self._losses = int(WL_list[1])
        
    def name(self):
        return self._name
    
    def conf(self):
        return self._conf
    
    def win_ratio(self):
        return self._wins / (self._wins + self._losses)
        
    def __str__(self):
        return "{} : {:.10f}".format(self._name, self.win_ratio())
        

class Conference:
    """
    This class creates a conference object that assigns a collection of teams 
    belonging to one conference. It uses a list to organize a collection of 
    teams for a conference, this makes it easy to add on teams. 
    """
    def __init__(self, conf):
        self._conf = conf
        self._teams = []
    
    def name(self):
        return self._conf
    
    def add(self, team):
        self._teams.append(team)
        
    def win_ratio(self):
        """
        This function calculates the average win ratio across all teams in a 
        conference. It leverages the existing win_ratio() method in the team 
        class.

        Returns:
            total_win_ratio / len(self._teams)(float)
        """
        if len(self._teams) == 0:
            return 0
        total_win_ratio = sum([team.win_ratio() for team in self._teams])
        return total_win_ratio / len(self._teams)
        

class ConferenceSet:
    """
    This class organizes the conferences into a dictionary that assigns the 
    conference object list to the correspoinding key which is the conference
    name. 
    """
    def __init__(self):
        self._conferences = {}
    
    def add_team(self, team):
        conf_name = team.conf()
        if conf_name not in self._conferences:
            self._conferences[conf_name] = Conference(conf_name)
        self._conferences[conf_name].add(team)
    
    def get_highest_win_ratio_conferences(self):
        """
        This function retrieves the highest win ratio from each conference
        by iterating through the dictionary and returning the highest average. 
        Since this was already calculates in the subsequent class we just need
        to verify and retrieve. 

        Returns:
            best_conferences(list): A list of the best conferences by win
            ratio for all given teams in the input. 
        """
        # Find the conference(s) with the highest win ratio
        max_ratio = -1
        best_conferences = []
        
        for conference in self._conferences.values():
            ratio = conference.win_ratio()
            if ratio > max_ratio:
                max_ratio = ratio
                best_conferences = [conference]
            elif ratio == max_ratio:
                best_conferences.append(conference)
        
        for i in range(len(best_conferences)):
            for j in range(i + 1, len(best_conferences)):
                if best_conferences[i].name() > best_conferences[j].name():
                    best_conferences[i], best_conferences[j] = \
                    best_conferences[j], best_conferences[i]
        
        return best_conferences

def process_file(filename):
    """
    This function parses the input file and sets any objects from each line 
    in the file. 

    Args:
        filename (.txt file): A .txt file holding information about NCAA 
        basketball teams and their records. 

    Returns:
        conference_set: The ConferenceSet object created by assigning the file
        data to it. 
    """
    conference_set = ConferenceSet()
    infile = open(filename, "r")
    for line in infile:
        # Skip comments and blank lines
        if not line.startswith("#") and line.strip():
            team = Team(line.strip())
            conference_set.add_team(team)
    return conference_set

def main():
    """
    This function helps format the output of the best_conferences to align
    with the expected behavior and also silently prompts the user for the 
    file name. 
    """
    filename = input()
    conference_set = process_file(filename)
    
    # Get the best conference(s) by win ratio
    best_conferences = conference_set.get_highest_win_ratio_conferences()
    
    # Print the results
    for conference in best_conferences:
        print("{} : {}".format(conference.name(), conference.win_ratio()))
    
main()