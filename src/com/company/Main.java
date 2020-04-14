package com.company;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.IRedBlackTree;
import eg.edu.alexu.csd.filestructure.redblacktree.RedBlackTree;

public class Main {

    public static void main(String[] args) {
        IRedBlackTree r = new RedBlackTree();


        r.insert(5634,5634);
        r.insert(5907,5907);
        r.insert(5794,5794);
        r.insert(2885,2885);
        r.insert(5775,5775);
        r.insert(7897,7897);
        r.insert(9822,9822);
        r.insert(1523,1523);
        r.insert(3185,3185);
        r.insert(9745,9745);
        r.delete(5634);
       /* r.delete(5794);
        r.delete(5775);
        r.delete(9822);
        r.delete(1523);*/
        INode a=r.getRoot();
        System.out.print(r.getRoot().getKey());
        System.out.println(r.getRoot().getColor());
        System.out.print(r.getRoot().getLeftChild().getValue());
        System.out.println(r.getRoot().getLeftChild().getColor());
        System.out.print(r.getRoot().getRightChild().getKey());
        System.out.println(r.getRoot().getRightChild().getColor());
        System.out.print(r.getRoot().getLeftChild().getLeftChild().getValue());
        System.out.println(r.getRoot().getLeftChild().getLeftChild().getColor());
        System.out.print(r.getRoot().getLeftChild().getRightChild().getValue());
        System.out.println(r.getRoot().getLeftChild().getRightChild().getColor());
        System.out.print(r.getRoot().getRightChild().getLeftChild().getKey());
        System.out.println(r.getRoot().getRightChild().getLeftChild().getColor());
        System.out.print(r.getRoot().getRightChild().getRightChild().getKey());
        System.out.println(r.getRoot().getRightChild().getRightChild().getColor());
        System.out.print(r.getRoot().getLeftChild().getLeftChild().getLeftChild().getValue());
        System.out.println(r.getRoot().getLeftChild().getLeftChild().getLeftChild().getColor());
        System.out.print(r.getRoot().getLeftChild().getLeftChild().getRightChild().getValue());
        System.out.println(r.getRoot().getLeftChild().getLeftChild().getRightChild().getColor());
//        System.out.print(r.getRoot().getLeftChild().getRightChild().getLeftChild().getValue());
//        System.out.println(r.getRoot().getLeftChild().getRightChild().getLeftChild().getColor());
//        System.out.print(r.getRoot().getLeftChild().getRightChild().getRightChild().getValue());
  //      System.out.println(r.getRoot().getLeftChild().getRightChild().getRightChild().getColor());
        System.out.print(r.getRoot().getRightChild().getLeftChild().getLeftChild().getKey());
        System.out.println(r.getRoot().getRightChild().getLeftChild().getLeftChild().getColor());
        System.out.print(r.getRoot().getRightChild().getLeftChild().getRightChild().getKey());
        System.out.println(r.getRoot().getRightChild().getLeftChild().getRightChild().getColor());
        System.out.print(r.getRoot().getRightChild().getRightChild().getLeftChild().getKey());
        System.out.println(r.getRoot().getRightChild().getRightChild().getLeftChild().getColor());
        System.out.print(r.getRoot().getRightChild().getRightChild().getRightChild().getKey());
        System.out.println(r.getRoot().getRightChild().getRightChild().getRightChild().getColor());
      //  r.delete(3229);
       /* System.out.println(r.getRoot().getKey());
        System.out.println(r.getRoot().getLeftChild().getValue());
        System.out.println(r.getRoot().getLeftChild().getColor());
        System.out.println(r.getRoot().getRightChild().getKey());
        System.out.println(r.getRoot().getRightChild().getColor());*/

    }
}
