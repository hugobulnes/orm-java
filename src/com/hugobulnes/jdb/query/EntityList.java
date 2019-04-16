package com.hugobulnes.jdb.query;

import java.util.Iterator;

public class EntityList<T> implements Iterable<T>{

    private int size;
    private Node head;

    public class Node{
        public Node next;
        public T element;
        public Node(T element){
            this.element = element;
        }
    }

    public int size(){
        return this.size;
    }

    public T get(int pos) throws Exception{
        if(pos < 0 || pos > this.size){
            throw new IndexOutOfBoundsException();
        }

        Node ptr = head;
        for(int index = 0; index < pos; index++){
            ptr = ptr.next;
        }
        return ptr.element;
    }

    public void add(int pos, T element) throws Exception{
        if(pos < 0 || pos > this.size){
            throw new IndexOutOfBoundsException();
        }
        Node node = new Node(element);
        if(pos == 0){
            node.next = head;
            head = node;
        }else{
            //Traverse
            Node ptr = head;
            for(int index = 0; index < pos -1; index++){
                ptr = ptr.next;
            }
            node.next = ptr.next;
            ptr.next = node;
        }
        this.size++;
    }

    public void add(T element) throws Exception{

        Node node = new Node(element);
        
        if(this.head == null){
            this.head = node;
        }else{
            Node ptr = head;
            while(ptr.next != null){
                ptr = ptr.next;
            }
            ptr.next = node;
        }   
        this.size++;
    }

    public Iterator<T> iterator(){
        return new EntityIterator();
    }

    private class EntityIterator implements Iterator<T>{
        private Node ptr = head;

        public boolean hasNext(){
            return ptr != null;
        }

        public T next(){
            T element = ptr.element;
            ptr = ptr.next;
            return element;
        }
    }

}
