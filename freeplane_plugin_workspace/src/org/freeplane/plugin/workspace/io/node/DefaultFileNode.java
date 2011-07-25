/**
 * author: Marcel Genzmehr
 * 21.07.2011
 */
package org.freeplane.plugin.workspace.io.node;

import java.io.File;
import java.io.StringWriter;

import org.freeplane.plugin.workspace.WorkspaceController;
import org.freeplane.plugin.workspace.controller.WorkspaceNodeEvent;

/**
 * 
 */
public class DefaultFileNode extends PhysicalNode {

	/***********************************************************************************
	 * CONSTRUCTORS
	 **********************************************************************************/
	
	/**
	 * @param name
	 * @param file
	 */
	public DefaultFileNode(String name, File file) {
		super(name, file);
		// TODO Auto-generated constructor stub
	}
	
	
	/***********************************************************************************
	 * METHODS
	 **********************************************************************************/
		

	/***********************************************************************************
	 * REQUIRED METHODS FOR INTERFACES
	 **********************************************************************************/
	
	public void handleEvent(WorkspaceNodeEvent event) {
		System.out.println("DefaultFileNode: "+ event);
		if(event.getType() == WorkspaceNodeEvent.MOUSE_LEFT_DBLCLICK) {
			StringWriter writer = new StringWriter();
			WorkspaceController.getCurrentWorkspaceController().saveConfigurationAsXML(writer);
			System.out.println(writer.toString());
		} 
		else 
			super.handleEvent(event);
	}

}