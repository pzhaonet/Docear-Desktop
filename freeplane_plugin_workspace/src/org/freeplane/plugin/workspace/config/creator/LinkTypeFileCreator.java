package org.freeplane.plugin.workspace.config.creator;

import java.io.File;

import org.freeplane.n3.nanoxml.XMLElement;
import org.freeplane.plugin.workspace.WorkspaceUtils;
import org.freeplane.plugin.workspace.config.node.LinkTypeFileNode;
import org.freeplane.plugin.workspace.model.creator.AWorkspaceNodeCreator;
import org.freeplane.plugin.workspace.model.node.AWorkspaceTreeNode;

public class LinkTypeFileCreator extends AWorkspaceNodeCreator {

	public LinkTypeFileCreator() {
	}

	@Override
	public AWorkspaceTreeNode getNode(XMLElement data) {
		String type = data.getAttribute("type", "file");
		LinkTypeFileNode node = new LinkTypeFileNode(type);
		

		String path = data.getAttribute("path", null);
		if (path == null) {
			return null;
		}
		//FIXME: DOCEAR - this should be made URI compatible
		node.setLinkPath((new File(path)).toURI()); 		
		String name = data.getAttribute("name", WorkspaceUtils.resolveURI(node.getLinkPath()).getName());
		node.setName(name);
		
		return node;

	}
}