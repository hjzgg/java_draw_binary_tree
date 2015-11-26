package com.hjzgg.rbt;

import java.util.Scanner;

public class RBTree {
	public RBTNode T = null;
	private boolean isRoot = true;//是不是第一个插入的节点，也就是根节点
	
	public void rotateT(RBTNode o, int rotate){//rotate表明旋转方向
		RBTNode k = o.child[rotate^1];
		if(o.parent==null)
			T = k;
		else if(o.parent.child[0] == o)
			o.parent.child[0] = k;
		else
			o.parent.child[1] = k;
		k.parent = o.parent;
		o.child[rotate^1] = k.child[rotate];
		k.child[rotate] = o;
		o.parent = k;
	}
	
	private void rbtInsertFixup(RBTNode o){//红黑树平衡的调整,并更改节点的颜色
		if(o.parent.color == RBTNode.RED){
			int childIndex;//左子树或者是右子树索引
			if(o.parent == o.parent.parent.child[0])
				childIndex = 0;
			else 
				childIndex = 1;
			
			//找到o节点对应的叔节点
			RBTNode ou = o.parent.parent.child[childIndex^1];
			if(ou!=null && ou.color == RBTNode.RED){//如果叔节点的颜色是红色
				o.parent.parent.color = RBTNode.RED;
				ou.color = RBTNode.BLACK;
				o.parent.color = RBTNode.BLACK;
			} else {//叔节点是空或者是黑色
				if(o == o.parent.child[childIndex^1]){
					o = o.parent;
					rotateT(o, childIndex);
				}
				o.parent.color = RBTNode.BLACK;
				o.parent.parent.color = RBTNode.RED;
				rotateT(o.parent.parent, childIndex^1);
			}
			T.color = RBTNode.BLACK;
		}
	}
	
	public void outT(RBTNode o){
		if(o==null) return;
		System.out.print(o.key+" ");
		outT(o.child[0]);
		outT(o.child[1]);
	}
	
	public void rbtInsert(RBTNode o, RBTNode op, int key, int childIndex){//红黑树的插入
		if(o == null){
			o = new RBTNode(op, key, RBTNode.RED);
			if(op==null){
				T = o;
				T.color = RBTNode.BLACK;
			} else {
				op.child[childIndex] = o;
				o.parent = op;
			}
			if(o.color==RBTNode.RED)
				rbtInsertFixup(o);
		} else if(o.key > key){
			rbtInsert(o.child[0], o, key, 0);
			if(o.color==RBTNode.RED)
				rbtInsertFixup(o);
		} else {
			rbtInsert(o.child[1], o, key, 1);
			if(o.color==RBTNode.RED)
				rbtInsertFixup(o);
		}
	}
	
	public void rbtDelete(){//红黑树的删除
		
	}
	

	public static void main(String[] args) {
		RBTree rbt = new RBTree();
		Scanner scan = new Scanner(System.in);
		for(int i=0; i<15; ++i){
			int key = scan.nextInt();
			rbt.rbtInsert(rbt.T, null, key, 0);
//			rbt.outT(rbt.T);
//			System.out.println();
		}
		new RBTGraphic(rbt.T).new TreeFrame("红黑树");
	}
}

/*
  2 3 4 6 7 9 11 9 18 14 12 17 19 22 20
  */
