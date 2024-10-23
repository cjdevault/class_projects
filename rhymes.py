"""
Author: Christopher De Vault 
File: rhymes.py
Purpose: This program takes a csv file of words and corresponding phonemes and
organizes the data into a dictionary. This dictionary is used to find perfect
rhymes for any given word by verifying certain rhyme conditions using the 
phonemes while checking another word against a user query word input. 
Course: CSC120 FALL 2024
"""
def process_input(infile):
    """This function builds the phoneme dictionary from a csv file input. It
    organizes and cleans the data line by line, adding the full word as a 
    key in the dictionary and initiating the dictionary values as a 2D list. 
    This accounts for multiple pronunciations of a word to be retrieved as 
    sublists. 

    Args:
        infile (.csv file): A CSV file containing words and their phonemes.

    Returns:
        phoneme_dict (dict): A dictionary with words as keys and their 
        pronunciation(s) in a 2D list. 
    """
    pfile = open(infile, "r")
    phoneme_dict = {}
    for line in pfile:
        # 2D List where first is the word, rest are phonemes
        phoneme_data = line.strip().split()
        # Initiate dictionary key value pairs
        word_key = phoneme_data[0]
        phoneme_values = phoneme_data[1:]
        
        # if-else block to handle duplicate keys
        if word_key not in phoneme_dict:
            phoneme_dict[word_key] = [phoneme_values]
        else:
            phoneme_dict[word_key].append(phoneme_values)
    pfile.close()
    return phoneme_dict

def get_phonemes(word, phoneme_dict):
    """This function retrieves the phonemes from a key word input. This is 
    utilized as a helper method. 

    Args:
        word (str): Any string input, valid if found in the dictionary.
        phoneme_dict (dict): A dictionary with words as keys and their 
        pronunciation(s) in a 2D list.

    Returns:
        list: A 2D list containing multiple or one pronunciation of a word, 
        represented as phonemes. 
    """
    if word in phoneme_dict:
        return phoneme_dict[word]

def get_stress(word, phoneme_dict):
    """This function finds the stressed vowel for a word utilizing its 
    corresponding phonemes and will retrieve the index of the stressed vowel, 
    and the stressed phoneme. 

    Args:
        word (str): Any string input, valid if found in the dictionary.
        phoneme_dict (dict): A dictionary with words as keys and their 
        pronunciation(s) in a 2D list.

    Returns:
        stressed_positions(list): A list of indices in the word argument's 
        corresponding phoneme list that identifies where the stressed vowel is. 
        stressed_phonemes(list): A list of the actual phonemes that are the
        stressed vowel in a given word.  
    """
    word_phonemes = get_phonemes(word, phoneme_dict)

    # To store the index of the stressed vowel for each pronunciation
    stressed_positions = [] 
    stressed_phonemes = []

    # Loop over each pronunciation (sublist of phonemes)
    for pronunciation in word_phonemes:
        i = 0 
        while i < len(pronunciation):
            phoneme = pronunciation[i]
            if "1" in phoneme:  # Check for primary stress marker
                stressed_positions.append(i)
                stressed_phonemes.append(phoneme)  # Append the full phoneme
                break  # Stop once we find the first stressed vowel
            i += 1
    
    return stressed_positions, stressed_phonemes  # ([indices], [phonemes])
    
def perfect_rhyme(word1, word2, phoneme_dict):
    """This function is the core of the program. It utilizes any getters to 
    retrieve neccessary information to verify that two words are perfect
    rhymes. It utilizes the phoneme dictionary and the getters to find the 
    stressed vowel and index in the word phoneme list it occurs in. It then
    uses the information of the stressed vowel and its index to verify across
    two different words that they are perfect rhymes. 

    Args:
        word1, word2 (str): Any string input, valid if found in the dictionary.
        phoneme_dict (dict): A dictionary with words as keys and their 
        pronunciation(s) in a 2D list.

    Returns:
        (boolean): Returns true if two words are perfect rhymes and false if
        not the case. 
    """
    # Get the list of pronunciations for both words
    phonemes1_list = get_phonemes(word1, phoneme_dict)
    phonemes2_list = get_phonemes(word2, phoneme_dict)

    # Get the stress indices and stressed phonemes for both words
    stress_indices1, stress_phonemes1 = get_stress(word1, phoneme_dict)
    stress_indices2, stress_phonemes2 = get_stress(word2, phoneme_dict)

    # Iterate over all pronunciations of word1
    for phonemes1 in phonemes1_list:
        # Iterate over all pronunciations of word2
        for phonemes2 in phonemes2_list:
            i = 0
            while i < len(stress_indices1):
                stress_idx1 = stress_indices1[i]
                stress_phoneme1 = stress_phonemes1[i]
                
                j = 0
                while j < len(stress_indices2):
                    stress_idx2 = stress_indices2[j]
                    stress_phoneme2 = stress_phonemes2[j]

                    # Check if stressed vowel and subsequent phonemes match
                    if (stress_phoneme1 == stress_phoneme2 and 
                        phonemes1[stress_idx1:] == phonemes2[stress_idx2:]):
                        
                        # Ensure the phoneme before the stressed vowel differ
                        if phonemes1[stress_idx1-1:stress_idx1] != \
                        phonemes2[stress_idx2-1:stress_idx2]:
                            return True
                    j += 1
                i += 1

    return False

def find_perfect_rhymes(word, phoneme_dict):
    """This function iterates over the entire phoneme_dict to find perfect
    rhymes for a word input. This function is the one that finds the rhyming 
    words while perfect_rhyme just verifies their condition as rhymes. 
    
    Args:
        word (str): Any string input, valid if found in the dictionary.
        phoneme_dict (dict): A dictionary with words as keys and their 
        pronunciation(s) in a 2D list.

    Returns:
        perfect_rhymes(list): A sorted list of words that perfectly rhyme with
        the input word. 
    """
    perfect_rhymes = []
    for other_word in phoneme_dict:
        # Words must not match and must return True in perfect_rhyme()
        if word != other_word and \
        perfect_rhyme(word, other_word, phoneme_dict):
            perfect_rhymes.append(other_word)
    # Sort the results to meet the desired output criteria
    return sorted(perfect_rhymes)    
        
def main():
    """The main function is the top level glue that defines any necessary
    information as variables to use in our find_perfect_rhymes() function. 
    It also prompts the user with a silent prompt for a file name and a
    query to find perfect rhymes from. 
    
    Args: 
        None
    
    Returns: 
        None
    """
    filename = input() 
    query = input().strip().upper()  # Ensures query format is standardized
    phoneme_dict = process_input(filename)
    perfect_rhymes = find_perfect_rhymes(query, phoneme_dict)
    for rhyme in perfect_rhymes:
        print(rhyme)
    
main()