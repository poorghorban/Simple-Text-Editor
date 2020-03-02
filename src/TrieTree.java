import java.util.Map;

public class TrieTree {

    // Root node
    private Node root;

    // constructor
    public TrieTree() {
        this.root = new Node(' ' , true , false);
    }

    // add word to tire tree
    public void addWord(String word){

        // length word
        int len_word = word.length();

        //
        Node temp = this.root;

        //
        for(int i=0 ; i < len_word ; i++){

            // character index i in string word
            char character = word.charAt(i);

            // condition 1:The current node of its children list should be empty.
            // condition 2: This character is not in the child node list.
            // condition 3: This character is in the child node list.
            if (temp.isLeaf() || !(temp.existsChildNode(character))){
                // create node
                Node child = new Node(character ,true , false);

                // add child node to list of childern parent
                temp.addChildNode(character , child);

                // by add child node to list of childern temp node no leaf
                temp.setLeaf(false);

                // move to child node
                temp = child;
            } else{

                // get child with this character
                Node child = temp.getNodeByKey(character);

                // move to child node
                temp = child;

            }
        }

        //
        temp.setWord(true);
    }

    // delete word from tire tree
    public boolean deleteWord(String word){

        // check exists this word in trie tree
        if (this.existsWord(word)){

            //
            Node temp = this.root;

            this.recursiveDeleteWord(temp , word , 0);

            return true;
        }else{
            return false;
        }
    }

    // check exists word
    public boolean existsWord(String word){

        // length word
        int len_word = word.length();

        //
        Node temp = this.root;

        //
        for(int i=0 ; i < len_word ; i++){

            // character index i in string word
            char character = word.charAt(i);

            // check exists this character to list of childern
            if (temp.isLeaf() || !(temp.existsChildNode(character))){
                return false;
            }else{

                // get child with this character
                Node child = temp.getNodeByKey(character);

                // move to child node
                temp = child;
            }
        }

        // check current node is word
        if (temp.isWord()){
            return true;
        }else{
            return false;
        }
    }

    // recursive delete word from trie tree
    private boolean recursiveDeleteWord(Node node , String word , int index){

        // check end of string
        if (word.length() == index){

            // check node is leaf
            if (node.isLeaf()){
                return true;
            }else{

                //
                node.setWord(false);
                return false;
            }
        }else{

            boolean result_remove_node = recursiveDeleteWord(node.getNodeByKey(word.charAt(index)), word , index+1);

            // condition1: result ---> true , child node with character (index+1) remove from list of childern
            // condition2: result ---> false, child node no remove(include child)
            if (result_remove_node){

                // remove child node with this character
                node.deleteChildNodeByKey(word.charAt(index));

                //condition1: return ture ----> current node is leaf (for remove from list of childern parent)
                // condition2: return false ---> current node no remove(include child)
                if (node.isLeaf() && !(node.isWord())){
                    return true;
                }else{
                    return false;
                }

            }else{
                return false;
            }
        }

    }

    // recursive search
    private String recursiveSearchWord(Node node , String str ){

        if (node.isLeaf()){
            return str;
        }else{
            String word = "";
            if (node.isWord()){
                word = str;
            }

            for (Map.Entry<Character , Node> entry: node.getListOfChildern().entrySet()){
                word = word + " " + recursiveSearchWord(entry.getValue() , str + entry.getKey());
            }

            return word;
        }

    }

    // get node by substring
    public Node getNodeByStr(String str){

        // length str
        int len_str = str.length();

        //
        Node temp = this.root;

        //
        for(int i=0 ; i < len_str ; i++) {

            // character index i in string word
            char character = str.charAt(i);

            if (temp.isLeaf() || !(temp.existsChildNode(character))){
                return null;
            }else {
                // get child with this character
                Node child = temp.getNodeByKey(character);

                // move to child node
                temp = child;

            }
        }
        return temp;
    }

    // get list of word in trie tree
    public String getListOfWordInTrieTree(String str) {

        Node node = this.root;

        if (str != "") {
            node = this.getNodeByStr(str);
        }

        if (node == null) {
            return "";
        } else {
            String list_words = "";
            String[] temp = recursiveSearchWord(node, str).trim().split("\\s+");

            if (temp.length != 0) {
                char alphabet = temp[0].charAt(0);

                for (int i = 0; i < temp.length; i++) {
                    if (temp[i] != "") {
                        if (temp[i].trim().charAt(0) == alphabet) {
                            list_words += temp[i] + " ";

                        } else {
                            alphabet = temp[i].charAt(0);
                            list_words += "\n";
                            list_words += temp[i] + " ";
                        }
                    }
                }
            }
            return list_words;
        }

    }
}
