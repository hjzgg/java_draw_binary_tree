package com.hjzgg.rbt;

import java.awt.Rectangle;

public class RBTNode {
	public static final boolean RED = true;
	public static final boolean BLACK = false;
	public RBTNode[] child = new RBTNode[2];
	public RBTNode parent;
	public int key;
	public boolean color;
	public RBTNode(RBTNode parent, int key, boolean color) {
		super();
		this.parent = parent;
		this.key = key;
		this.color = color;
		child[0] = child[1] = null;
	}
	
	private int level;//这个节点在树中的层次
	private Rectangle rect;
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Rectangle getRect() {
		return rect;
	}
	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	
}
