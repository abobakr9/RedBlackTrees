package eg.edu.alexu.csd.filestructure.redblacktree;

public class Node <T extends Comparable<T>, V> implements INode<T,V>{
    private INode left =null;
    private INode right =null;
    private INode parent =null;
    private T key = null;
    private V value = null;
    private boolean color;
    private boolean nil=false;

    public Node(T Key,V Value) {
        this.key = Key;
        this.value=Value;
        left = right = parent= null;
        color=RED;
        nil = false;
    }
    public Node(T Key,V Value,boolean n,boolean c) {
        this.key = Key;
        this.value=Value;
        left = right = parent= null;
        color=c;
        nil=n;
    }

    @Override
    public void setParent(INode<T, V> parent) {
     this.parent=parent;
    }

    @Override
    public INode<T, V> getParent() {
        return parent;
    }

    @Override
    public void setLeftChild(INode<T, V> leftChild) {
     left=leftChild;
    }

    @Override
    public INode<T, V> getLeftChild() {
        return left;
    }

    @Override
    public void setRightChild(INode<T, V> rightChild) {
        right=rightChild;
    }

    @Override
    public INode<T, V> getRightChild() {
        return right;
    }

    @Override
    public T getKey() {
        return key;
    }

    @Override
    public void setKey(T key) {
      this.key=key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public void setValue(V value) {
     this.value=value;
    }

    @Override
    public boolean getColor() {
        return color;
    }

    @Override
    public void setColor(boolean color) {
       this.color=color;
    }

    @Override
    public boolean isNull() {
        return nil;
    }

}
