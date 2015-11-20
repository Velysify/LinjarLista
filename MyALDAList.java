import java.util.*;
public class MyALDAList<E> implements ALDAList<E>{
	private int size= 0;
	private Node<E> first;
	private Node<E> last;
	private int modCount = 0;
	
	private static class Node<E>{
		public Node(E e){
			element = e;
		}
		
		public E element;
		public Node<E> next;
		public E getElement(){
			return element;
		}
	}
	
	public Node<E> getNode(int index){
		Node<E> node;
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		else{
			node = first;
			for(int i = 0; i < index; i++)
				node = node.next;
		}
		return node;
	}
	
	public void add(E element){
		if(first == null){
			first = new Node<E>(element);
			last = first;
		}
		else{
			last.next = new Node<E>(element);
			last = last.next;
		}
		size++;
		modCount++;
	}

	public void add(int index, E element){
		if ((index) == size()){
			add(element);
			size--;
			modCount--;
		}
		else if(index == 0){
			Node <E> pushedNode = first;
			first = new Node<E>(element);
			first.next = pushedNode;
		}
		else if(index < size()){
			Node<E> insertNode = new Node<E>(element);
			Node <E> pushedNode = getNode(index);
			Node <E> prev = getNode(index-1);
			prev.next = insertNode;
			insertNode.next = pushedNode;
		}
		else if (index > size() || index < 0)
			throw new IndexOutOfBoundsException();
		
		size++;
		modCount++;
	}

	public E remove(int index){
		Node<E> node = getNode(index);
		E element = node.getElement();
		if(contains(element)){
			if(index != 0 && index != size()-1 ){
				Node<E> toBeBacktracked = node.next;
				Node<E> previousNode = getNode(index-1);
				previousNode.next=toBeBacktracked;
			}
			else if(index == 0){
				Node<E> toBeBacktracked = node.next;
				first = toBeBacktracked;
			}
			else{
				Node<E> previousNode = getNode(index-1);
				previousNode.next=null;
				last = previousNode;
			}
		}
		modCount++;
		size--;
		return element;

	}

	public boolean remove(E element){
		boolean removed = false;
		if (contains(element)){
			int index = indexOf(element);
			Node<E> toBeRemoved = getNode(index);
			if(index != 0 && index != size()-1 ){
				Node<E> toBeBacktracked = toBeRemoved.next;
				Node<E> previousNode = getNode(index-1);
				previousNode.next=toBeBacktracked;	
			}
			else if(index == 0){
				Node<E> toBeBacktracked = toBeRemoved.next;
				first = toBeBacktracked;
			}
			else{
				Node<E> previousNode = getNode(index-1);
				previousNode.next=null;
				last = previousNode;
			}

			removed = true;
			modCount++;
			size--;
			}
		return removed;
	}

	public E get(int index){
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		else
		return getNode(index).getElement();
	}

	public boolean contains(E element){
		boolean statement = false;
		Node<E> node = first;
		while(!element.equals(node.getElement())){
			if(!node.equals(last)){
				node = node.next;
			}
			else
				break;
		}
		if(element.equals(node.getElement())){
			statement = true;
		}
		return statement;
	}

	public int indexOf(E element){
		int index = 0;
		Node<E> node = first;
		while(!element.equals(node.getElement())){
			if(!node.equals(last)){
				node = node.next;
				index = index+1;
			}
			else
				return -1;
		}
		return index;
	}

	public void clear(){
		first = null;
		last = null;
		size = 0;
		modCount++;
	}

	public int size(){
		return size; 
	}
	
	public MyALDAListIterator iterator(){
		return new MyALDAListIterator();
	}
	
	public String toString(){
		String printout = "[";
		for(int index = 0; index < size(); ++index){
			Node<E> node = getNode(index);
			String str = String.valueOf(node.getElement());
			if(index == size()-1)
				printout= printout+str;
			else
				printout = printout+str+", ";
		}
		printout = printout+"]";
		return printout;
	}
	
	public class MyALDAListIterator implements Iterator<E>{
		private Node<E> current = first;
		private Node<E> previous = null;
		private int expectedModCount = modCount;
		private boolean okToRemove = false;
		
		public boolean hasNext(){
			if(last == null)
				throw new NoSuchElementException();
			
			else
				return current != last.next;
		}
		
		public E next(){
				if(last == null){
					throw new NoSuchElementException();
				}
				else{
					if(modCount != expectedModCount)
						throw new ConcurrentModificationException();
			
					if(!hasNext())
						throw new NoSuchElementException();
			
					E nextNode = current.element;
					previous = current;
					current = current.next;
					okToRemove = true;
					return nextNode;
				}
		}
		
		public void remove(){
			
			if(modCount != expectedModCount)
				throw new ConcurrentModificationException();
			
			if(okToRemove == false)
				throw new IllegalStateException();
			
			MyALDAList.this.remove((previous.element));

			okToRemove = false;
			expectedModCount++;
			
		}
	}
}
