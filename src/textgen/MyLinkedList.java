package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author  Anvesh
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		head=new LLNode<E>();
		tail=new LLNode<E>();
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		LLNode<E> node=new LLNode<E>(element);
		if(size==0)
		{
		head=tail=node;
		}
		else
		{
		tail.next=node;
		node.prev=tail;
		}
		tail=node;
		size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		if(index>size-1 || index<0)
			 throw new IndexOutOfBoundsException();
		int i=0;
		LLNode<E> n=head;
		while(i<index && n!=null)
		{
		n=n.next;
		i++;
		}
		
		return n.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		if(element==null)
			throw new NullPointerException();
		if(index>size || index<0)
		 throw new IndexOutOfBoundsException();
		int i=0;
		LLNode<E> l=head;
		LLNode<E> node=new LLNode<E>(element);
		while(i<index-1)
		{
		l=l.next;
		i++;
		}
		if(index==0)
		{
		  node.next=head;
		  head.prev=node;
		  head=node;
		}
		else
		{
		LLNode<E> next=l.next;
		node.prev=l;
		l.next=node;
		node.next=next;
		if(next!=null)
		next.prev=node;
		}
		size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		if(index<0 || index>=size)
			throw new IndexOutOfBoundsException();
		E e;
		LLNode<E> l=head;
		if(index==0)
		{
			e=head.data;
			LLNode<E> next=head.next;
			if(next!=null)
			next.prev=null;
			head=next;
		}
		else
		{
         for(int i=0;i<index;i++)
        	 l=l.next;
         e=l.data;
         LLNode<E> prev=l.prev;
         LLNode<E> next=l.next;
         prev.next=next;
         if(next!=null)
         next.prev=prev;
     	}
		size--;
		return e;
	}
	
	public void print()
	{
	LLNode<E> n=tail;
	while(n!=null)
	{
		System.out.println(n.data);
		n=n.prev;
	}
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		if(element==null)
			throw new NullPointerException();
		if(index<0 || index>size-1)
			throw new IndexOutOfBoundsException();
		LLNode<E> n=head;
		LLNode<E> newNode=new LLNode<E>(element);
		for(int i=0;i<index-1;i++)
		{
		n=n.next;
		}
		E el=n.next.data;
		if(index==0)
		{
	         el=head.data;
         newNode.next=head;
         head.prev=newNode;
         head=newNode;

		}
		else
		{
			LLNode<E> next=n.next;
			newNode.prev=n;
			n.next=newNode;
			newNode.next=next;
			if(next!=null)
			next.prev=newNode;
		}
		size++;
		return el;
	}   
	
	
	
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;


	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
  
	public LLNode()
	{
	
	}

}
