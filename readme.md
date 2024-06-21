# Word completion

A. Create a vocabulary from a set of csv/text files.   
B. Use an AVL tree or red-black tree to store the vocabulary (each node should store a word
and its frequency of occurrence).   
Autocomplete Functionality:   
A. Implement a function that returns word completions based on a given prefix. Traverse the
tree to find the node corresponding to the prefix and collect all words in the subtree.   
B. Rank the word completions based on their frequency of occurrence. Use a min-heap to
store the top suggestions and sort them before displaying to the user.   

# Solution
There were only two options to choose from for this AVL or Red-Black tree.
I chose AVL tree because it is more balanced than Red-Black tree, the insertions take more time but seraches are faster. Our application does not require frequent insertions but it requires frequent searches.   