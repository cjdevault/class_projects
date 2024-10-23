'''
File: pokemon.py
Author: Christopher De Vault
Course: CSC120 FA24
Purpose: Processes information from a csv file about Pokemon and their
statistics and organizes the information into a 2-level dictionary. This
poke_dict is used to compute maximum averages for a given statistic like speed, 
hp, etc. A user can enter a stat and this program with return the type of 
Pokemon with that max average for that stat and then the numerical value. 
'''
def process_input(filename):
    """This function processes a given csv file by stripping each line and
    splitting on the comma and appends each processed line as a subslist to 
    an outer list.

    Args:
        filename (.csv file): A csv file containing information about various
        Pokemon with their name, stats, generation, etc. 

    Returns:
        poke_list (list): A iterable object to help with further handling
        large amounts of information of Pokemon. 
    """    
    infile = open(filename, "r")
    poke_list = []
    for line in infile:
        if line[0] != "#":
            poke_list.append(line.strip().split(","))
    infile.close()
    return poke_list

def make_dict(poke_list):
    """This function creates a 2D dictionary, the outer level keys being the 
    Pokemon type and the inner level keys being the name of a Pokemon of that
    outer level type. 

    Args:
        poke_list (list): A 2D list of Pokemon.

    Returns:
        poke_dict: A 2D dictionary to handle information of Pokemon. 
    """
    poke_dict = {}
    for pokemon in poke_list:
        poke_name = pokemon[1]
        poke_type = pokemon[2]
        poke_stats = pokemon[4:]
        # If this sublist has a new pokemon type 
        # => add the pokemon type as key and pokemon name as subdict key
        # => add poke stats as the subdict value
        if poke_type not in poke_dict.keys():
            poke_dict[poke_type] = {poke_name:poke_stats}
        # If the pokemon type already exists 
        # => add the pokemon subdict to the matching key 
        else:
            poke_dict[poke_type][poke_name] = poke_stats

    return poke_dict

def compute_max_averages(poke_dict):
    """Computes the max averages for each stat for each type of Pokemon in the
    2D pokemon dictionary.

    Args:
        poke_dict (dict): A 2D Pokemon dictionary. 

    Returns:
        max_averages: A dictionary that stores max average and pokemon type as
        a tuple value with the stat as its key pair.
    """
    poke_stat_indices = {
            'total': 0,
            'hp': 1,
            'attack': 2,
            'defense': 3,
            'specialattack': 4,
            'specialdefense': 5,
            'speed': 6
        }
    # Creates a dictionary that stores the max average and corresponding 
    # Pokemon type with that stat
    max_averages = {stat: (None, None) for stat in poke_stat_indices}
        
    for poke_type, pokemons in poke_dict.items():
        # Accumulates the stat totals 
        totals = {stat: 0 for stat in poke_stat_indices}
        # Tracks how man Pokemon contribute to the stat totals for averaging
        counts = {stat: 0 for stat in poke_stat_indices}
        
        for stats in pokemons.values():
            for stat, index in poke_stat_indices.items():
                value = int(stats[index])
                totals[stat] += value
                counts[stat] += 1
        
        for stat in poke_stat_indices:
            # If the count is not empty for that stat => Compute average
            if counts[stat] > 0:
                average = totals[stat] / counts[stat]
                if max_averages[stat][0] is None or \
                average > max_averages[stat][1]:
                    max_averages[stat] = (poke_type, average)
    
    return max_averages
    
def print_max_averages(query, poke_dict):
    """Helps format the max average information and process user queries to
    return desired statistics for each Pokemon type. 

    Args:
        query (str): Users given silent prompt for any stat they want.
        poke_dict (dict): A 2D Pokemon dictionary. 

    Returns:
        str: A formatted string representation of the Pokemon type and 
        corresponding numerical average for the desired stat. 
    """ 
    # Ensures user queries are case-insensitive
    query = query.strip().lower()
    
    poke_stat_indices = {
        'total': 0,
        'hp': 1,
        'attack': 2,
        'defense': 3,
        'specialattack': 4,
        'specialdefense': 5,
        'speed': 6
    }
    
    if query not in poke_stat_indices:
        return ""
    # Retrieve the pre-computed max averages
    max_averages = compute_max_averages(poke_dict)
    max_value = max_averages[query][1]
    
    results = []
    for type_key, pokemons in poke_dict.items():
        totals = 0
        counts = 0
        
        for stats in pokemons.values():
            value = int(stats[poke_stat_indices[query]])
            totals += value
            counts += 1
        
        if counts > 0:
            average = totals / counts
            if average == max_value:
                results.append((type_key, average))
    
    results.sort()
    return "\n".join(f"{pokemon_type}: {average}" \
    for pokemon_type, average in results)
    
def main():
    """ Initializes every function with the appropriate argument to ensure
    each function returns the desired output from the user. Also handels edge
    case for user input. 
    """    
    filename = input()
    pokemon_2d_list = process_input(filename)
    poke_dict = make_dict(pokemon_2d_list)
    
    while True:
        query = input()
        if not query:
            break  # Exit the loop if the query is an empty line
        results = print_max_averages(query, poke_dict)
        print(results)

main()