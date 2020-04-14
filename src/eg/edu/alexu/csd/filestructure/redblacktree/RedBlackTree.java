package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;

import static eg.edu.alexu.csd.filestructure.redblacktree.INode.BLACK;
import static eg.edu.alexu.csd.filestructure.redblacktree.INode.RED;

public class RedBlackTree <T extends Comparable<T>, V> implements IRedBlackTree<T,V> {
    private INode nil =new Node(null,null,true,BLACK);
    private INode root=nil;

    @Override
    public INode<T, V> getRoot() {
        return root;
    }

    @Override
    public boolean isEmpty() {
        return root == nil;
    }

    @Override
    public void clear() {
        root = nil;
    }

    @Override
    public V search(T key) {
        if (key==null){
            throw new RuntimeErrorException(new Error());
        }
        if(isEmpty()){
            return null;
        }
        INode MyNode=NodeSearch(getRoot(),key);
        if(MyNode.getValue() == null || MyNode == null){
            return null;
        }
        else{
            return (V) MyNode.getValue();
        }
    }

    @Override
    public boolean contains(T key) {
        if (key==null){
            throw new RuntimeErrorException(new Error());
        }
        return (NodeSearch(getRoot(),key).getKey() != null) ;
    }

    @Override
    public void insert(T key, V value) {
        if(contains(key)){
            NodeSearch(getRoot(),key).setValue(value);
            return;
        }
        if (key==null||value==null){
            throw new RuntimeErrorException(new Error());
        }
        INode MyNode=new Node(key,value);
        MyNode.setRightChild(nil);
        MyNode.setLeftChild(nil);
        MyNode.setParent(nil);
        if(isEmpty()){
            MyNode.setColor(BLACK);
            root=MyNode;
            MyNode.setRightChild(nil);
            MyNode.setLeftChild(nil);
        }
        else {
            InsertRecurse(root, MyNode);
        }
        InsertRepairTree(MyNode);


    }

    @Override
    public boolean delete(T key) {
        if (key==null){
            throw new RuntimeErrorException(new Error());
        }else {
            if (!contains(key)) {
                return false;
            } else {
                INode replaceNode = nil; // track node that replaces removedOrMovedNode
                INode deleteNode=NodeSearch(getRoot(),key);
                if (deleteNode.getKey() != null && deleteNode != nil) {
                    INode removedOrMovedNode = deleteNode; // same as deleteNode if it has only one child, and otherwise it replaces deleteNode

                    if (deleteNode.getLeftChild().getKey() == null) {
                        replaceNode = deleteNode.getRightChild();
                        rbTreeTransplant(deleteNode, deleteNode.getRightChild());
                    } else if (deleteNode.getRightChild().getKey() == null) {
                        replaceNode = deleteNode.getLeftChild();
                        rbTreeTransplant(deleteNode, deleteNode.getLeftChild());
                    } else {
                        removedOrMovedNode = getMinimum(deleteNode.getRightChild());
                        replaceNode = removedOrMovedNode.getRightChild();
                        if (removedOrMovedNode.getParent() == deleteNode) {
                            replaceNode.setParent(removedOrMovedNode);
                        } else {
                            rbTreeTransplant(removedOrMovedNode, removedOrMovedNode.getRightChild());
                            removedOrMovedNode.setRightChild(deleteNode.getRightChild());
                            removedOrMovedNode.getRightChild().setParent(removedOrMovedNode);
                        }
                        rbTreeTransplant(deleteNode, removedOrMovedNode);
                        removedOrMovedNode.setLeftChild(deleteNode.getLeftChild());
                        removedOrMovedNode.getLeftChild().setParent(removedOrMovedNode);
                        removedOrMovedNode.setColor(deleteNode.getColor());
                    }



                    if(removedOrMovedNode.getColor() == RED || replaceNode.getColor() == RED){
                        replaceNode.setColor(BLACK);
                    }
                   else if (removedOrMovedNode.getColor() == BLACK && replaceNode.getColor() == BLACK) {
                        deleteRBFixup(replaceNode);
                    }
                }
                return true;
            }
        }
    }
    public INode rbTreeTransplant(INode nodeToReplace, INode newNode) {
        if (nodeToReplace.getParent().getKey() == null) {
            this.root = newNode;
        } else if (nodeToReplace == nodeToReplace.getParent().getLeftChild()) {
            nodeToReplace.getParent().setLeftChild(newNode);
        } else {
            nodeToReplace.getParent().setRightChild(newNode);
        }
        newNode.setParent(nodeToReplace.getParent());
        return newNode;
    }
    private void deleteRBFixup(INode x) {
        while (x != root && x.getColor() == BLACK) {

            if (x == x.getParent().getLeftChild()) {
                INode w = x.getParent().getRightChild();
                if (w.getColor() == RED) { // case 1 - sibling is red
                    w.setColor(BLACK);
                    x.getParent().setColor(RED);
                    RotateLeft(x.getParent());
                    w = x.getParent().getRightChild(); // converted to case 2, 3 or 4
                }
                // case 2 sibling is black and both of its children are black
                else if(x.getKey() == null && w.getKey() == null){
                    x = x.getParent();
                }
                else if ( w.getLeftChild().getColor() == BLACK && w.getRightChild().getColor() == BLACK ) {
                    w.setColor(RED);
                    x = x.getParent();
                } else if (w.getKey() != null) {
                    if ( w.getRightChild().getColor() == BLACK) { // case 3 sibling is black and its left child is red and right child is black
                        w.getLeftChild().setColor(BLACK);
                        w.setColor(RED);
                        RotateRight(w);
                        w = x.getParent().getRightChild();
                    }
                    w.setColor(x.getParent().getColor()); // case 4 sibling is black and right child is red
                    x.getParent().setColor(BLACK);
                    w.getRightChild().setColor(BLACK);
                    RotateLeft(x.getParent());
                    x = root;
                } else {
                    x.setColor(BLACK);
                    x = x.getParent();
                }
            } else {
                INode w = x.getParent().getLeftChild();
                if ( w.getColor() == RED) { // case 1 - sibling is red
                    w.setColor(BLACK);
                    (x.getParent()).setColor(RED);
                    RotateRight(x.getParent());
                    w = x.getParent().getLeftChild(); // converted to case 2, 3 or 4
                }
                else if(x.getKey() == null && w.getKey() == null){
                    x = x.getParent();
                }
                // case 2 sibling is black and both of its children are black
                else if ( w.getLeftChild().getColor() == BLACK &&  w.getRightChild().getColor() == BLACK) {
                    w.setColor(RED);
                    x = x.getParent();
                } else if (w.getKey() != null) {
                    if ( w.getLeftChild().getColor() == BLACK) { // case 3 sibling is black and its right child is red and left child is black
                        (w.getRightChild()).setColor(BLACK);
                        w.setColor(RED);
                        RotateLeft(w);
                        w = x.getParent().getLeftChild();
                    }
                    w.setColor(x.getParent().getColor()); // case 4 sibling is black and left child is red
                    (x.getParent()).setColor(BLACK);
                    (w.getLeftChild()).setColor(BLACK);
                    RotateRight(x.getParent());
                    x = root;
                } else {
                    x.setColor(BLACK);
                    x = x.getParent();
                }
            }

        }
        if (x == root){
            x.setColor(BLACK);
        }
    }

    public INode NodeSearch(INode root, T key)
    {

        if (root.getKey() == null || (root.getKey().compareTo(key) == 0))
            return root;


        if (root.getKey().compareTo(key) > 0)
            return NodeSearch(root.getLeftChild(), key);

        return NodeSearch(root.getRightChild(), key);
    }



    public INode GetParent(INode n) {
        // Note that parent is set to null for the root node.
        return (n == null || n== nil) ? nil : n.getParent();
    }

    public INode GetGrandParent(INode n) {
        // Note that it will return nullptr if this is root or child of root
        return GetParent(GetParent(n));
    }

    public INode GetSibling(INode n) {
        INode p = GetParent(n);

        // No parent means no sibling.
        if (p == nil) {
            return nil;
        }

        if (n == p.getLeftChild()) {
            return p.getRightChild();
        } else {
            return p.getLeftChild();
        }
    }

    INode GetUncle(INode n) {
        INode p = GetParent(n);

        // No parent means no uncle
        return GetSibling(p);
    }

    void RotateLeft(INode node) {
        INode temp = node.getRightChild();
        temp.setParent(node.getParent());

        node.setRightChild(temp.getLeftChild());
        if (node.getRightChild() != nil) {
            node.getRightChild().setParent(node);
        }

        temp.setLeftChild(node);
        node.setParent(temp);

        // temp took over node's place so now its parent should point to temp
        if (temp.getParent() != nil) {
            if (node == temp.getParent().getLeftChild()) {
                temp.getParent().setLeftChild(temp);
            } else {
                temp.getParent().setRightChild(temp);
            }
        } else {
            root = temp;
        }
    }
    void RotateRight(INode node) {
        INode temp = node.getLeftChild();
        temp.setParent(node.getParent());

        node.setLeftChild(temp.getRightChild());
        if (node.getLeftChild() != nil) {
            node.getLeftChild().setParent(node);
        }

        temp.setRightChild(node);
        node.setParent(temp);

        // temp took over node's place so now its parent should point to temp
        if (temp.getParent() != nil) {
            if (node == temp.getParent().getLeftChild()) {
                temp.getParent().setLeftChild(temp);
            } else {
                temp.getParent().setRightChild(temp);
            }
        } else {
            root = temp;
        }
    }

    void InsertRecurse(INode root, INode n) {
        // Recursively descend the tree until a leaf is found.
        if (root.getKey() != null)
        {
            if (n.getKey().compareTo(root.getKey()) < 0) {
                if (root.getLeftChild().getKey() != null) {
                    InsertRecurse(root.getLeftChild(), n);
                    return;
                } else {
                    root.setLeftChild(n);
                }
            } else { // n->key >= root->key
                if (root.getRightChild().getKey() != null) {
                    InsertRecurse(root.getRightChild(), n);
                    return;
                } else {
                    root.setRightChild(n);
                }
            }
        }

        // Insert new Node n.
        n.setParent(root);
        n.setLeftChild(nil);
        n.setRightChild(nil);
        n.setColor(RED);
    }
    public void InsertRepairTree(INode n){
        if (n.getParent().getKey() == null) { //case0
            n.setColor(BLACK);
        } else if (n.getParent().getColor() == BLACK) {
            return;
        } else if (GetUncle(n) != nil && GetUncle(n).getColor() == RED) {     // case 1
            n.getParent().setColor(BLACK);
            GetUncle(n).setColor(BLACK);
            GetGrandParent(n).setColor(RED);
            InsertRepairTree(GetGrandParent(n));
        } else {
            INode p = GetParent(n);
            INode g = GetGrandParent(n);
            if (n == p.getRightChild() && p == g.getLeftChild()) { // begin of case 2
                RotateLeft(p);
                n = n.getLeftChild();
            } else if (n == p.getLeftChild() && p == g.getRightChild()) {
                RotateRight(p);
                n = n.getRightChild();
            }                                                       //end of case 2
            p = GetParent(n);                                   // begin of case 3
            g = GetGrandParent(n);

            if (n == p.getLeftChild()) {
                RotateRight(g);
            } else {
                RotateLeft(g);
            }
            p.setColor(BLACK);
            g.setColor(RED);
        }
    }
    public INode getMinimum(INode node) {
        while (node.getLeftChild() != nil) {
            node = node.getLeftChild();
        }
        return node;
    }


    public INode getMaximum(INode node) {
        while (node.getRightChild() != nil) {
            node = node.getRightChild();
        }
        return node;
    }


}