package com.sekwah.modeleditor.windows;

import com.sekwah.modeleditor.modelparts.ModelBox;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListSelectionHandler implements ListSelectionListener {

	private final ModelRenderer modelRenderer;

	public ListSelectionHandler(ModelRenderer modelRender) {
		this.modelRenderer = modelRender;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList list = (JList) e.getSource();
		int currentIndex = list.getSelectedIndex();
		modelRenderer.setSelectedBox(null);
		for(int i = 0; i < modelRenderer.boxList.size(); i++){
			ModelBox box = modelRenderer.boxList.get(i);
			if(box.name.equals(list.getSelectedValue().toString())){
				modelRenderer.moveCameraTo(box.xPos, box.yPos, box.zPos);
				modelRenderer.isSlidingCamera = true;
				modelRenderer.setSelectedBox(box);
				break;
			}
		}

	}

}
