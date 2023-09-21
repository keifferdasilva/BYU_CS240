package spell;

import java.util.Arrays;

public class Node implements INode {

    private Node[] children = new Node[26];
    private int count;

    public Node() {
        count = 0;
    }

    @Override
    public int getValue() {
        return count;
    }

    @Override
    public void incrementValue() {
        count++;
    }

    @Override
    public Node[] getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Node other = (Node) obj;

        if (this.getValue() != other.getValue()) {
            return false;
        }

        return Arrays.equals(this.getChildren(), other.getChildren());
    }

}
