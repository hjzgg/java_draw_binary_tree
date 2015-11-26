package com.hjzgg.rbt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RBTGraphic {
	private RBTNode T = null;
	private final int distNode = 50;//节点之间的距离
	private final int heightNode = 50;//节点的高度
	private final int widthNode = 50;//节点的宽度
	private final int levelHeight = 100;//层与层之间的高度
	private ArrayList<Rectangle> line = new ArrayList<Rectangle>();
	private int curY = 0;
	private int curX = 10;
	
	public RBTGraphic(RBTNode T) {
		super();
		this.T = T;
		prepareGraphic();
	}
	
	public class TreeFrame extends JFrame{
		private JPanel panel = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				for(Rectangle rect : line){
					g.drawLine(rect.x, rect.y, rect.width, rect.height);
				}
			}
		};
		private JScrollPane scrollPane = new JScrollPane(panel);
		public TreeFrame() throws HeadlessException {
			super();
			init();
		}

		public TreeFrame(String title) throws HeadlessException {
			super(title);
			init();
		}
		
		private void init(){
			setLayout(new BorderLayout());
			panel.setLayout(null);
			drawTree(T);
			add(scrollPane, BorderLayout.CENTER);
			int width = curX + 50;
			int height = curY + 50;
			//这里要设置面板的PreferredSize，如果当前Frame大小不能显示PreferredSize那么才会出现滚动条
			panel.setPreferredSize(new Dimension(width, height));
			if(width > 600) width = 600;
			if(height > 300) height = 500;
			setBounds(400, 100, width, height);
			setVisible(true);
		}
		
		public void drawTree(RBTNode o){
			if(o==null) return;
			JLabel label = new JLabel(o.key+"", JLabel.CENTER);
			label.setBounds(o.getRect());
			label.setFont(new Font("宋体",Font.BOLD, 32));
			label.setForeground(Color.WHITE);
			label.setOpaque(true);
			if(o.color == RBTNode.RED)
				label.setBackground(Color.RED);
			else
				label.setBackground(Color.BLACK);
			panel.add(label);
			if(o.child[0]==null && o.child[1]==null) return;
			int x = o.getRect().x+ widthNode/2;
			int y = o.getLevel()*levelHeight+heightNode;
			for(int i=0; i<2; ++i){
				drawTree(o.child[i]);
				if(o.child[i]==null) continue;
				int xx = o.child[i].getRect().x + widthNode/2;
				int yy = y+levelHeight-heightNode;
				line.add(new Rectangle(x, y, xx, yy));
			}
		}
	}
	
	private void prepareNodeLevel(RBTNode o, int level){//确定每一个节点的层次
		if(o==null) return; 
		o.setLevel(level);
		prepareNodeLevel(o.child[0], level+1);
		prepareNodeLevel(o.child[1], level+1);
		if(curY < (level+1)*levelHeight) curY = (level+1)*levelHeight;
	}
	
	private void prepareNodeSize(RBTNode o){//确定节点的坐标位置
		if(o==null) return;
		if(o.child[0]==null && o.child[1]==null){//终结点
			int x = curX; curX+=distNode+widthNode;
			int y = o.getLevel()*levelHeight;
			o.setRect(new Rectangle(x, y, widthNode, heightNode));
			return;
		}
		prepareNodeSize(o.child[0]);
		prepareNodeSize(o.child[1]);
		
		int leftChildx = o.child[0] != null ? o.child[0].getRect().x : o.child[1].getRect().x;
		int rightChildx = o.child[1] == null ? o.child[0].getRect().x : o.child[1].getRect().x;
		//根据左右两边孩子的节点，确定父节点的坐标尺寸
		int parentx = (leftChildx+rightChildx)/2;
		int parenty =o.getLevel()*levelHeight;
		o.setRect(new Rectangle(parentx, parenty, widthNode, heightNode));
	}
	
	private void prepareGraphic(){
		prepareNodeLevel(T, 0);
		prepareNodeSize(T);
	}
	
}

