package com.onceas.wm.console.util;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.tdojo.comodel.tree.TreeNode;

/**
 * @author syk
 *
 */
@TreeNode(id = "id", label = "name", children = "children")
public class MenuNode {
	private String id;

	private String name;

	private String type;

	private List<MenuNode> children = new ArrayList<MenuNode>();

	public MenuNode(String id, String name, String type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public MenuNode() {
		super();
	}

	public List<MenuNode> getChildren() {
		return children;
	}

	public void setChildren(List<MenuNode> children) {
		this.children = children;
	}

	public void addChild(MenuNode child){
		if(!children.contains(child)){
			children.add(child);
		}
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MenuNode getChildById(String id){
		if(id == null){
			return null;
		}
		
		for(MenuNode child : children){
			if(id.equals(child.getId())){
				return child;
			}
		}
		
		return null;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[id=").append(id).append(";");
		builder.append("name=").append(name).append(";");
		builder.append("type=").append(type).append(";");
		builder.append("children(").append(children.isEmpty() ? "" : children.toString()).append(")]");
		return builder.toString();
	}
	//for test
	public void pringTree(int depth){
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<depth; i++){
			builder.append("------"); 
		}
		builder.append("[name=").append(name).append(";");
		builder.append("type=").append(type).append("]");
		System.out.println(builder.toString());
		for(MenuNode mbn : getChildren()){
			mbn.pringTree(depth + 1);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		
		if(obj instanceof MenuNode){
			MenuNode other = (MenuNode)obj;
			return this.id.equals(other.id) ;
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	private static final MenuNode EMPTY_MBEAN_NODE = new EmptyMenuNode();
	
	public static MenuNode emptyMenuNode(){
		return EMPTY_MBEAN_NODE;
	}
	
	@TreeNode(id = "id", label = "name", children = "children")
	public static class EmptyMenuNode extends MenuNode{

		private static String EMPTY = "empty mbean node."; 
		@Override
		public String getId() {
			return EMPTY;
		}

		@Override
		public String getName() {
			return EMPTY;
		}

		@Override
		public String getType() {
			return EMPTY;
		}
		
	}
}
