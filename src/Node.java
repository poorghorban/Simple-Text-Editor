import java.util.Map;
import java.util.TreeMap;

public class Node {

    private char character;

    //If this leaf node is True and if not False
    private boolean leaf;

    //If characters create a word True and if not False
    private boolean word;

    //List of child nodes
    private Map<Character , Node> listOfChildern;

    // Constructor
    public Node(char character, boolean leaf, boolean word) {
        this.character = character;
        this.leaf = leaf;
        this.word = word;
        this.listOfChildern = new TreeMap<Character, Node>();
    }

    // get character
    public char getCharacter() {
        return character;
    }

    // node is leaf
    public boolean isLeaf() {
        return leaf;
    }

    // set value for leaf
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    // set value for word
    public void setWord(boolean word) {
        this.word = word;
    }

    // node is word
    public boolean isWord() {
        return word;
    }

    // get list of childern
    public Map<Character, Node> getListOfChildern() {
        return listOfChildern;
    }

    // check exists child node with this key
    public boolean existsChildNode(char key){
        if (this.listOfChildern.containsKey(key)){
            return true;
        }else {
            return false;
        }
    }

    // get child node by key
    public Node getNodeByKey(char key){

        if(this.existsChildNode(key)){
            return this.listOfChildern.get(key);
        }else{
            return null;
        }
    }

    // add child node to list of childern
    public void addChildNode(char key , Node node){
        this.listOfChildern.put(key , node);
    }

    // delete child node from list of childern
    public boolean deleteChildNodeByKey(char key){

        Node node = this.listOfChildern.remove(key);

        if (node != null){
            return false;
        }else{
            return true;
        }
    }
}
