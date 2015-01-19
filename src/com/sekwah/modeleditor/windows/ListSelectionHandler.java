package com.sekwah.modeleditor.windows;

import com.sekwah.modeleditor.modelparts.ModelBox;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListSelectionHandler implements ListSelectionListener {

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList list = (JList) e.getSource();
		int currentIndex = list.getSelectedIndex();
		for(int i = 0; i < ModelRenderer.boxList.size(); i++){
			ModelBox box = ModelRenderer.boxList.get(i);
			if(box.name.equals(list.getSelectedValue().toString())){
				ModelRenderer.moveCameraTo(box.xPos, box.yPos, box.zPos);
				ModelRenderer.isSlidingCamera = true;
			}
		}

	}

}
