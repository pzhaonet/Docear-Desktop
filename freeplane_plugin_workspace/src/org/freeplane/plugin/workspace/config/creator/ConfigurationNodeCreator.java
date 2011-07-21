package org.freeplane.plugin.workspace.config.creator;

import javax.swing.tree.DefaultMutableTreeNode;

import org.freeplane.core.io.IElementDOMHandler;
import org.freeplane.core.ui.IndexedTree;
import org.freeplane.n3.nanoxml.XMLElement;
import org.freeplane.plugin.workspace.config.node.WorkspaceNode;

public abstract class ConfigurationNodeCreator implements IElementDOMHandler {
	
	abstract public WorkspaceNode getNode(String id, XMLElement data);
	
	
	protected IndexedTree tree;
		
	public ConfigurationNodeCreator(IndexedTree tree) {
		this.tree = tree;
	}
	
	public Object createElement(final Object parent, final String tag, final XMLElement attributes) {
		if (attributes == null) {
			return null;
		}
		String id = attributes.getAttribute("id", null);
		if (id == null) {
			if(WorkspaceCreator.class.isInstance(this)) {
				tree.getRoot().setUserObject(getNode(id, attributes));
			} 
			return parent == null ? Path.emptyPath() : parent;
		}
		final Path path = new Path(parent == null ? null : parent.toString());
		path.setName(id);
		if (!tree.contains(path.path)) {
			tree.addElement(path.parentPath == null ? tree : path.parentPath, this, path.path, IndexedTree.AS_CHILD);
		}
		return path;
	}

	public void endElement(final Object parent, final String tag, final Object userObject, final XMLElement lastBuiltElement) {
		final String id = lastBuiltElement.getAttribute("id", null);
		final Path path = (Path)userObject;
		if (path.path == null) {
			return;
		}
		final DefaultMutableTreeNode treeNode = tree.get(path.path);
		if (treeNode.getUserObject() == this) {
			final WorkspaceNode node = getNode(id, lastBuiltElement);
			if(node != null) 
				treeNode.setUserObject(node);
			else 
				tree.removeElement(path.path);
		}
	}

	
	
	protected static class Path {
		static Path emptyPath() {
			final Path Path = new Path(null);
			Path.path = null;
			return Path;
		}

		String parentPath;
		String path;

		Path(final String path) {
			parentPath = path;
		}

		void setName(final String name) {
			path = parentPath == null ? name : parentPath + '/' + name;
		}

		@Override
		public String toString() {
			return path;
		}
	};
}