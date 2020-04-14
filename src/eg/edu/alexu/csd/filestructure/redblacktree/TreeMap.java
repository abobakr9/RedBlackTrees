package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;
import java.util.*;

import static eg.edu.alexu.csd.filestructure.redblacktree.INode.BLACK;

public class TreeMap <T extends Comparable<T>, V> implements ITreeMap<T,V> {
    private IRedBlackTree RedBlackTree  ;
    private int Size=0;
    private ArrayList<T> keys = new ArrayList<>();
    private ArrayList<V> values = new ArrayList<>();
    private ArrayList<Map.Entry<T,V>> MyList = new ArrayList<>();
    private T floor=null;
    private T ceiling=null;
    private INode nil =new Node(null,null,true,BLACK);
    public TreeMap(){
        RedBlackTree =new RedBlackTree();
    }

    @Override
    public Map.Entry<T, V> ceilingEntry(T key) {
        T CeilingKey =ceilingKey(key);
        if(CeilingKey ==nil){
            return null;
        }
        V CeilingValue= (V) RedBlackTree.search(CeilingKey);
        return new AbstractMap.SimpleEntry<T, V>(CeilingKey,CeilingValue);
    }

    @Override
    public T ceilingKey(T key) {
        if (key==null){
            throw new RuntimeErrorException(new Error());
        }
        CeilingFloor(RedBlackTree.getRoot(),key);
        T myCeiling = this.ceiling;
        this.ceiling=null;
        this.floor=null;
        return myCeiling;
    }

    @Override
    public void clear() {
        RedBlackTree.clear();
        Size=0;
    }

    @Override
    public boolean containsKey(T key) {
        if (key==null){
            throw new RuntimeErrorException(new Error());
        }
        return RedBlackTree.contains(key);
    }

    @Override
    public boolean containsValue(V value) {
        if (value==null){
            throw new RuntimeErrorException(new Error());
        }
        ClearLists();
        inOrder(RedBlackTree.getRoot());
        if(values.contains(value)){
            return  true;
        }
        else{
            return false;
        }
    }


    @Override
    public Set<Map.Entry<T, V>> entrySet() {
        ClearLists();
        inOrder(RedBlackTree.getRoot());
        Set<Map.Entry<T,V>> mySet = new LinkedHashSet<>(MyList);
        return mySet;
    }

    @Override
    public Map.Entry<T, V> firstEntry() {
        if(RedBlackTree.isEmpty()){
            return null;
        }
        INode node =RedBlackTree.getRoot();

        return new AbstractMap.SimpleEntry<T, V>(firstKey(), (V) getMinimum(node).getValue());
    }

    @Override
    public T firstKey() {
        if(RedBlackTree.isEmpty()){
            return null;
        }
        INode node =RedBlackTree.getRoot();

        return (T) getMinimum(node).getKey();
    }


    @Override
    public Map.Entry<T, V> floorEntry(T key) {
        T FloorKey =floorKey(key);
        if(FloorKey ==null){
            return null;
        }
        V FloorValue= (V) RedBlackTree.search(FloorKey);
        return new AbstractMap.SimpleEntry<T, V>(FloorKey,FloorValue);
    }

    @Override
    public T floorKey(T key) {
        if (key==null){
            throw new RuntimeErrorException(new Error());
        }
        CeilingFloor(RedBlackTree.getRoot(),key);
        T myFloor = this.floor;
        this.ceiling=null;
        this.floor=null;
        return myFloor;
    }

    @Override
    public V get(T key) {
        if (key==null){
            throw new RuntimeErrorException(new Error());
        }
        return (V)RedBlackTree.search(key);
    }



    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey) {
        if (toKey==null){
            throw new RuntimeErrorException(new Error());
        }
        ArrayList<Map.Entry<T, V>>MyHeadMap = new ArrayList<>();
        ClearLists();
        inOrder(RedBlackTree.getRoot());
        if(keys.isEmpty()){
            return null;
        }
        int i =0;
        while (keys.get(i).compareTo(toKey) < 0){
            MyHeadMap.add(MyList.get(i));
            i++;
        }
        return MyHeadMap;
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey, boolean inclusive) {
        ArrayList<Map.Entry<T, V>>MyHeadMap =headMap(toKey);
        if(MyList.size() == 0){
            return null;
        }
        if (inclusive && MyHeadMap.size() != MyList.size()){
            int i=MyHeadMap.size();
            while (keys.get(i).compareTo(toKey) <= 0 ){
                MyHeadMap.add(MyList.get(i));
                i++;
            }
            return MyHeadMap;
        }
        else{
            return MyHeadMap;
        }
    }

    @Override
    public Set<T> keySet() {
        ClearLists();
        inOrder(RedBlackTree.getRoot());
        Set<T> mySet = new LinkedHashSet<>(keys);
        return mySet;
    }

    @Override
    public Map.Entry<T, V> lastEntry() {
        INode node =RedBlackTree.getRoot();
        if (RedBlackTree.isEmpty()){
            return null;
        }

        return new AbstractMap.SimpleEntry<T, V>(lastKey(), (V)getMaximum(node).getValue() );
    }

    @Override
    public T lastKey() {
        if(RedBlackTree.isEmpty()){
            return null;
        }
        INode node =RedBlackTree.getRoot();
        return (T)getMaximum(node).getKey();
    }



    @Override
    public Map.Entry<T, V> pollFirstEntry() {
        if(RedBlackTree.isEmpty()){
            return null ;
        }
        Map.Entry<T,V> Entry=firstEntry();
        RedBlackTree.delete(Entry.getKey());
        Size--;
        return Entry;
    }

    @Override
    public Map.Entry<T, V> pollLastEntry() {
        if(RedBlackTree.isEmpty()){
            return null;
        }
        Map.Entry<T,V> Entry=lastEntry();
        RedBlackTree.delete(Entry.getKey());
        Size--;
        return Entry;
    }

    @Override
    public void put(T key, V value) {
        if (key==null||value==null){
            throw new RuntimeErrorException(new Error());
        }
        if(RedBlackTree.contains(key)){
            RedBlackTree.insert(key,value);}
        else{
            RedBlackTree.insert(key,value);
            Size++;}
    }
    @Override
    public void putAll(Map<T, V> map) {
        if (map==null){
            throw new RuntimeErrorException(new Error());
        }
        for(Map.Entry<T, V> element : map.entrySet()){
            RedBlackTree.insert(element.getKey(),element.getValue());
        }
        Size+=map.size();
    }

    @Override
    public boolean remove(T key) {
        if (key==null){
            throw new RuntimeErrorException(new Error());
        }
        if (RedBlackTree.isEmpty()){
            return false;
        }
        if(RedBlackTree.delete(key)){
            Size--;
            return  true;
        }
        return false;
    }

    @Override
    public int size() {
        return Size;
    }

    @Override
    public Collection<V> values() {
        ClearLists();
        inOrder(RedBlackTree.getRoot());
        return this.values;
    }

    public void inOrder(INode node) {
        if (node.getKey() == null) {
            return; }
        inOrder(node.getLeftChild());
        keys.add((T) node.getKey());
        values.add((V) node.getValue());
        MyList.add(new AbstractMap.SimpleEntry<T, V>((T) node.getKey(),(V) node.getValue()));
        inOrder(node.getRightChild());
    }
    public void postOrder(INode node) {
        if (node.getKey() == null) {
            return; }
        postOrder(node.getRightChild());
        keys.add((T) node.getKey());
        values.add((V) node.getValue());
        MyList.add(new AbstractMap.SimpleEntry<T, V>((T) node.getKey(),(V) node.getValue()));
        postOrder(node.getLeftChild());
    }

    public void ClearLists(){
        keys.clear();
        values.clear();
        MyList.clear();
    }

    public void CeilingFloor(INode root, T keyy) {
        if (root != nil) {
            if (root.getKey().equals(keyy)){
                floor=keyy;
                ceiling=keyy;
            } else if (root.getKey().compareTo(keyy)>0) {

                ceiling = (T) root.getKey();
                CeilingFloor(root.getLeftChild(), keyy);
            } else if (root.getKey().compareTo(keyy)<0) {

                floor= (T) root.getKey();
                CeilingFloor(root.getRightChild(), keyy);
            }
        }



    }
    public INode getMinimum(INode node) {
        while (node.getLeftChild().getKey() != null) {
            node = node.getLeftChild();
        }
        return node;
    }


    public INode getMaximum(INode node) {
        while (node.getRightChild().getKey() != null) {
            node = node.getRightChild();
        }
        return node;
    }
}
