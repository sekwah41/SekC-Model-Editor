package com.sekwah.modeleditor.windows;

import com.sekwah.modeleditor.modelparts.ModelBox;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

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
		for(ModelBox box :  modelRenderer.boxList){
			if(box.name.equals(list.getSelectedValue().toString())){
				modelRenderer.moveCameraTo(box.xPos, box.yPos, box.zPos);
				modelRenderer.isSlidingCamera = true;
				modelRenderer.setSelectedBox(box);
				if(box != null){
					ModelEditorWindow.xRotationSlider.setValue((int) (((box.xRotation) / 180) * 50 + 50));
					ModelEditorWindow.yRotationSlider.setValue((int) (((box.yRotation) / 180) * 50 + 50));
					ModelEditorWindow.zRotationSlider.setValue((int) (((box.zRotation) / 180) * 50 + 50));
				}
				break;
			}
            else{
                ModelBox childBox = searchChildren(box.getChildren(), list.getSelectedValue().toString());
                if(childBox != null){
                    modelRenderer.moveCameraTo(childBox.xPos, childBox.yPos, childBox.zPos);
                    modelRenderer.isSlidingCamera = true;
                    modelRenderer.setSelectedBox(childBox);
                    ModelEditorWindow.xRotationSlider.setValue((int) (((childBox.xRotation) / 180) * 50 + 50));
                    ModelEditorWindow.yRotationSlider.setValue((int) (((childBox.yRotation) / 180) * 50 + 50));
                    ModelEditorWindow.zRotationSlider.setValue((int) (((childBox.zRotation) / 180) * 50 + 50));
                    break;
                }
            }
		}

	}

    

    public ModelBox searchChildren(ArrayList<ModelBox> children, String name){
        for(ModelBox box : children){
            if(box.name.equals(name)){
                return box;
            }
            else{
                ModelBox box2 = searchChildren(box.getChildren(),name);
                if(box2 != null){
                    return box2;
                }
            }
        }
        return null;
    }

}
