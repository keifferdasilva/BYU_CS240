package spell;

public class Trie implements ITrie {

    private Node root;
    private int wordCount;
    private int nodeCount;

    public Trie(){
        root = new Node();
        wordCount = 0;
        nodeCount = 1;
    }
    @Override
    public void add(String word) {
        StringBuilder s = new StringBuilder(word);
        addHelper(root, s);
    }

    private void addHelper(Node node, StringBuilder s){
        if(s.length() == 0){
            if(node.getValue() == 0){
                wordCount++;
            }
            node.incrementValue();
            return;
        }
        char currLetter = s.charAt(0);
        int index = currLetter - 'a';
        s.deleteCharAt(0);

        if(node.getChildren()[index] == null){
            node.getChildren()[index] = new Node();
            nodeCount++;
            addHelper(node.getChildren()[index], s);
        }
        else if(node.getChildren()[index] != null){
            addHelper(node.getChildren()[index], s);
        }


    }

    @Override
    public INode find(String word) {
        StringBuilder s = new StringBuilder(word);
        return findHelper(root, s);
    }

    private INode findHelper(INode node, StringBuilder word){
        if(word.length() == 0){
            if(node.getValue() == 0){
                return null;
            } else if (node.getValue() > 0) {
                return node;
            }
        }
        char currLetter = word.charAt(0);
        int index = currLetter - 'a';
        word.deleteCharAt(0);

        if(node.getChildren()[index] == null){
            return null;
        }
        return findHelper(node.getChildren()[index],word);
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    private void toStringHelper(Node currNode, StringBuilder currWord, StringBuilder output){
        if(currNode.getValue() > 0){
            output.append(currWord.toString());
            output.append("\n");
        }
        for(int i = 0; i < currNode.getChildren().length; i++){
            Node child = currNode.getChildren()[i];
            if(child != null){
                char childLetter = (char)('a' + i);
                currWord.append(childLetter);
                toStringHelper(child, currWord, output);
                currWord.deleteCharAt(currWord.length() - 1);
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder currWord = new StringBuilder();
        StringBuilder output = new StringBuilder();

        toStringHelper(root, currWord, output);

        return output.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }

        if(this == obj){
            return true;
        }

        if(this.getClass() != obj.getClass()){
            return false;
        }

        Trie other = (Trie)obj;

        if((this.wordCount != other.wordCount) || this.nodeCount != other.nodeCount){
            return false;
        }

        return equalsHelper(this.root, other.root);
    }

    private boolean equalsHelper(Node firstRoot, Node secondRoot){
        if(!firstRoot.equals(secondRoot)){
            return false;
        }
        if(firstRoot.getChildren().length != secondRoot.getChildren().length){
            return false;
        }

        for(int i = 0; i < firstRoot.getChildren().length; i++){
            Node child1 = firstRoot.getChildren()[i];
            Node child2 = secondRoot.getChildren()[i];
            if(child1 != null && child2 != null){
                if(!child1.equals(child2)){
                    return false;
                }
                else{
                    equalsHelper(child1, child2);
                }
            }
            else if(child1 == null && child2 == null){
                continue;
            }
        }

        return true;

    }

    @Override
    public int hashCode() {
        int hash = wordCount * nodeCount;
        for(int i = 0; i < root.getChildren().length; i++){
            if(root.getChildren()[i] != null){
                hash += i;
            }
        }
        return hash;
    }
}
