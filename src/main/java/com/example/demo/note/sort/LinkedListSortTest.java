package com.example.demo.note.sort;



/**
 * 单项链表翻转
 * @author Administrator
 */
public class LinkedListSortTest {
	
	public static void main(String[] args) {
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		node1.setNext(node2);
		node2.setNext(node3);
		System.out.println("打印初始链表……");
		printNode(node1);
		System.out.println("遍历反转后链表……");
		Node reNode = reversal1(node1);
		printNode(reNode);
		System.out.println("递归反转后链表……");
		printNode(reversal2(reNode));
	}
	
	/**
	 * 遍历反转法
	 */
	public static Node reversal1 (Node head){
		Node pre = head; //上一个节点
		Node cur = head.getNext();//当前节点
		Node tmp = null; //临时存储
		head.setNext(null); //将原链表的头指针域置为null作为新链表的尾
		while(cur != null){
			tmp = cur.getNext();
			cur.setNext(pre); //指针互换
			pre = cur; //重新赋值遍历下一个
			cur = tmp;
		}
		return pre;
	}
	
	/**
	 * 递归反转法
	 */
	public static Node reversal2 (Node head){
		if(head == null || head.getNext() == null){
			return head;
		}
		Node reHead = reversal2(head.getNext()); //递归到原链表的尾后开始反转
		head.getNext().setNext(head); // head.getNext()作为当前节点，讲当前节点指针互换
		head.setNext(null); //将原前一节点指针域置为null
		return reHead;
	}
	
	/**
	 * 打印链表
	 */
	public static void printNode(Node node){
		while(node != null){
			System.out.print(node.getData()+"\t");
			node = node.getNext();
		}
		System.out.println();
	}

	
	/**
	 * 单向链表
	 */
	static class Node{
		
		private Node next; //指针域
		
		private int data; //数据域
		
		public Node(int data){
			this.data = data;
		}
		
		public Node getNext() {
			return next;
		}

		public void setNext(Node next) {
			this.next = next;
		}

		public int getData() {
			return data;
		}

		public void setData(int data) {
			this.data = data;
		}
		
	}
}
