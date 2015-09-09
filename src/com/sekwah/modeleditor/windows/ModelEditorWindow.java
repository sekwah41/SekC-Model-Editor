package com.sekwah.modeleditor.windows;

import com.sekwah.modeleditor.assets.Assets;
import com.sekwah.modeleditor.modelparts.ModelBox;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class ModelEditorWindow extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1349457624026308068L;

    public static JSlider xRotationSlider = null;
    public static JSlider yRotationSlider = null;
    public static JSlider zRotationSlider = null;
    public static JTextField nameBoxTextField = null;
    public static JSpinner xPosSpinner = null;
    public static JSpinner yPosSpinner = null;
    public static JSpinner zPosSpinner = null;
    public static JSpinner xSizeSpinner = null;
    public static JSpinner ySizeSpinner = null;
    public static JSpinner zSizeSpinner = null;
    public static JSpinner xOffsetSpinner = null;
    public static JSpinner yOffsetSpinner = null;
    public static JSpinner zOffsetSpinner = null;
    public static JSpinner xTextureSpinner = null;
    public static JSpinner yTextureSpinner = null;
    private JList boxList;

    private JPanel contentPane;

    private BufferedImage favicon = null;
    private Canvas lwjglCanvas;
    private JPanel editorPane;
    private ModelRenderer modelRender;

    public ModelEditorWindow(){

        // https://docs.oracle.com/javase/tutorial/uiswing/components/spinner.html

        // http://stackoverflow.com/questions/3949382/jspinner-value-change-events
        // https://docs.oracle.com/javase/tutorial/uiswing/components/spinner.html

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        } catch (Exception e) {
            System.err.println("Look and feel not set.");
        }


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setMinimumSize(new Dimension(800, 600));

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(0, 0,0, 0));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        this.setTitle("SekC's Model Editor");
        this.setSize(1200, 800);

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");

        JMenuItem newProject = new JMenuItem("New");
        newProject.addActionListener(this);
        menu.add(newProject);
        JMenuItem openProject = new JMenuItem("Open");
        openProject.addActionListener(this);
        menu.add(openProject);
        JMenuItem saveProject = new JMenuItem("Save");
        saveProject.addActionListener(this);
        menu.add(saveProject);
        JMenuItem saveAsProject = new JMenuItem("Save As");
        saveAsProject.addActionListener(this);
        menu.add(saveAsProject);
        JMenuItem importPose = new JMenuItem("Import Pose");
        importPose.addActionListener(this);
        menu.add(importPose);
        JMenuItem exportPose = new JMenuItem("Export Pose");
        exportPose.addActionListener(this);
        menu.add(exportPose);

        menuBar.add(menu);

        menuBar.setBackground(new Color(255,255,255));

        this.setJMenuBar(menuBar);

        editorPane = new JPanel();
        editorPane.setBackground(new Color(40,40,40));

        editorPane.setPreferredSize(new Dimension(300, editorPane.getPreferredSize().height));

        favicon = loadTexture("Images/favicon.png");

        this.setIconImage(favicon);

        lwjglCanvas = new Canvas();

        contentPane.add(lwjglCanvas, BorderLayout.CENTER);



        contentPane.add(editorPane, BorderLayout.LINE_START);

        editorPane.setLayout(new FlowLayout());

        JLabel label = new JLabel("Controls");
        label.setForeground(new Color(255,255,255));

        editorPane.add(label);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setForeground(new Color(255,255,255));
        nameLabel.setPreferredSize(new Dimension(290, nameLabel.getPreferredSize().height));

        editorPane.add(nameLabel);

        nameBoxTextField = new JTextField("");
        nameBoxTextField.setEnabled(false);
        nameBoxTextField.setSelectionColor(new Color(45, 135, 8));
        nameBoxTextField.setPreferredSize(new Dimension(290, nameLabel.getPreferredSize().height + 4));
        nameBoxTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ModelBox box = findBox(ModelEditorWindow.nameBoxTextField.getText());
                if(box == null){
                    ModelRenderer.getSelectedBox().name = ModelEditorWindow.nameBoxTextField.getText();

                    DefaultListModel listModel = new DefaultListModel();

                    listModel = ModelEditorWindow.addBoxesList(ModelRenderer.boxList, listModel);
                    // Have an array for selected indecies
                    int selectedIndex = -1;
                    if(!boxList.isSelectionEmpty()){
                        selectedIndex = boxList.getSelectedIndex();
                    }
                    boxList.clearSelection();
                    boxList.setModel(listModel);
                    if(selectedIndex >= 0){
                        boxList.setSelectedIndex(selectedIndex);
                    }
                }
                else if(box != ModelRenderer.selectedBox){
                    JOptionPane.showMessageDialog(Assets.modelEditorWindow, "There is already a box with the name: " + ModelEditorWindow.nameBoxTextField.getText());
                }
            }
        });

        editorPane.add(nameBoxTextField);

        // TODO add input and export box and json data for specific poses

        // TODO Add size and position boxes here
        // TODO allow the position spinners to have smaller values than the set steps, also

        // TODO add texture position boxes

        JLabel posLabel = new JLabel("Position");
        posLabel.setForeground(new Color(255, 255, 255));
        posLabel.setPreferredSize(new Dimension(290, posLabel.getPreferredSize().height));

        editorPane.add(posLabel);
        // TODO make custom spinner models or something to accept different decimal changes
        SpinnerModel xSpinnerModel =
                new SpinnerNumberModel(0, //initial value
                        -999, //min
                        999, //max
                        0.01); // Step, so 1 up or 1 down
        xPosSpinner = new JSpinner(xSpinnerModel);

        xPosSpinner.setPreferredSize(new Dimension(93, xPosSpinner.getPreferredSize().height));

        xPosSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner source = (JSpinner) e.getSource();

                if (ModelRenderer.selectedBox != null) {
                    ModelRenderer.selectedBox.xPos = (float) ((double) source.getValue());
                }

                //System.out.println(source.getValue());
            }
        });

        editorPane.add(xPosSpinner);

        SpinnerModel ySpinnerModel =
                new SpinnerNumberModel(0, //initial value
                        -999, //min
                        999, //max
                        0.01); // Step, so 1 up or 1 down

        yPosSpinner = new JSpinner(ySpinnerModel);

        yPosSpinner.setPreferredSize(new Dimension(93, yPosSpinner.getPreferredSize().height));

        // TODO add some way to change the jump scale and also make it put the values in correctly when the parts are selected, then the next part is texture offset
        // Then finally adding blocks and not just removing them(remember make it so if something is selected it is parented to it if its created or make a new button)

        yPosSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner source = (JSpinner)e.getSource();

                if(ModelRenderer.selectedBox != null){
                    ModelRenderer.selectedBox.yPos = (float) ((double) source.getValue());
                }

                //System.out.println(source.getValue());
            }
        });

        editorPane.add(yPosSpinner);

        SpinnerModel zSpinnerModel =
                new SpinnerNumberModel(0, //initial value
                        -999, //min
                        999, //max
                        0.01); // Step, so 1 up or 1 down

        /*zPosSpinner.setModel(new SpinnerNumberModel(1, null, null, 1) { // Look at these and decide what it will
        be, also see how to make it accept values with more dp but have a higher spinner value
            @Override
            public Object getNextValue() {
                Object nextValue = super.getValue();
                int x = Integer.valueOf(nextValue.toString())*2;
                //Object o = x;
                return x;
            }
        });*/

        zPosSpinner = new JSpinner(zSpinnerModel);

        zPosSpinner.setPreferredSize(new Dimension(93, zPosSpinner.getPreferredSize().height));

        zPosSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner source = (JSpinner) e.getSource();

                if (ModelRenderer.selectedBox != null) {
                    ModelRenderer.selectedBox.zPos = (float) ((double) source.getValue());
                }

                //System.out.println(source.getValue());
            }
        });

        editorPane.add(zPosSpinner);

        JLabel sizeLabel = new JLabel("Size");
        sizeLabel.setForeground(new Color(255,255,255));
        sizeLabel.setPreferredSize(new Dimension(290, sizeLabel.getPreferredSize().height));

        editorPane.add(sizeLabel);

        SpinnerModel xSpinnerModelSize =
                new SpinnerNumberModel(0, //initial value
                        -999, //min
                        999, //max
                        1); // Step, so 1 up or 1 down
        xSizeSpinner = new JSpinner(xSpinnerModelSize);

        xSizeSpinner.setPreferredSize(new Dimension(93, xSizeSpinner.getPreferredSize().height));

        xSizeSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner source = (JSpinner) e.getSource();

                if (ModelRenderer.selectedBox != null) {
                    ModelRenderer.selectedBox.xWidth = (int) source.getValue();
                }

                //System.out.println(source.getValue());
            }
        });

        editorPane.add(xSizeSpinner);

        SpinnerModel ySpinnerModelSize =
                new SpinnerNumberModel(0, //initial value
                        -999, //min
                        999, //max
                        1); // Step, so 1 up or 1 down

        ySizeSpinner = new JSpinner(ySpinnerModelSize);

        ySizeSpinner.setPreferredSize(new Dimension(93, ySizeSpinner.getPreferredSize().height));

        ySizeSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner source = (JSpinner) e.getSource();

                if (ModelRenderer.selectedBox != null) {
                    ModelRenderer.selectedBox.yWidth = (int) source.getValue();
                }

                //System.out.println(source.getValue());
            }
        });

        editorPane.add(ySizeSpinner);

        SpinnerModel zSpinnerModelSize =
                new SpinnerNumberModel(0, //initial value
                        -999, //min
                        999, //max
                        1); // Step, so 1 up or 1 down

        zSizeSpinner = new JSpinner(zSpinnerModelSize);

        zSizeSpinner.setPreferredSize(new Dimension(93, zSizeSpinner.getPreferredSize().height));

        zSizeSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner source = (JSpinner) e.getSource();

                if (ModelRenderer.selectedBox != null) {
                    ModelRenderer.selectedBox.zWidth = (int) source.getValue();
                }

                //System.out.println(source.getValue());
            }
        });

        editorPane.add(zSizeSpinner);


        JLabel xRotationLabel = new JLabel("Rot X");
        xRotationLabel.setForeground(new Color(255,255,255));
        xRotationLabel.setPreferredSize(new Dimension(290, xRotationLabel.getPreferredSize().height));

        editorPane.add(xRotationLabel);

        xRotationSlider = new JSlider();
        xRotationSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                //if (!source.getValueIsAdjusting()) {
                //System.out.println( 360 * ((float) (source.getValue() - 50) / 100F));
                float rotation = 360 * ((float) (source.getValue() - 50) / 100F);
                if(ModelRenderer.selectedBox != null){
                    ModelRenderer.selectedBox.xRotation = rotation;
                }
                //}
            }
        });
        xRotationSlider.setPreferredSize(new Dimension(290, xRotationSlider.getPreferredSize().height));
        editorPane.add(xRotationSlider);

        JLabel yRotationLabel = new JLabel("Rot Y");
        yRotationLabel.setForeground(new Color(255,255,255));
        yRotationLabel.setPreferredSize(new Dimension(290, yRotationLabel.getPreferredSize().height));

        editorPane.add(yRotationLabel);

        yRotationSlider = new JSlider();
        yRotationSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                //if (!source.getValueIsAdjusting()) {
                //System.out.println( 360 * ((float) (source.getValue() - 50) / 100F));
                float rotation = 360 * ((float) (source.getValue() - 50) / 100F);
                if(ModelRenderer.selectedBox != null){
                    ModelRenderer.selectedBox.yRotation = rotation;
                }
                //}
            }
        });
        yRotationSlider.setPreferredSize(new Dimension(290, yRotationSlider.getPreferredSize().height));
        editorPane.add(yRotationSlider);

        JLabel zRotationLabel = new JLabel("Rot Z");
        zRotationLabel.setForeground(new Color(255,255,255));
        zRotationLabel.setPreferredSize(new Dimension(290, zRotationLabel.getPreferredSize().height));

        editorPane.add(zRotationLabel);

        zRotationSlider = new JSlider();
        zRotationSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                //if (!source.getValueIsAdjusting()) {
                //System.out.println( 360 * ((float) (source.getValue() - 50) / 100F));
                float rotation = 360 * ((float) (source.getValue() - 50) / 100F);
                if(ModelRenderer.selectedBox != null){
                    ModelRenderer.selectedBox.zRotation = rotation;
                }
                //}
            }
        });
        zRotationSlider.setPreferredSize(new Dimension(290, zRotationSlider.getPreferredSize().height));
        editorPane.add(zRotationSlider);

        boxList = new JList();
        boxList.setLayoutOrientation(JList.VERTICAL);
        boxList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        boxList.setVisibleRowCount(-1);

        JScrollPane boxListScroller = new JScrollPane(boxList);
        boxListScroller.setPreferredSize(new Dimension(290, 90));

        editorPane.add(boxListScroller);

        JButton unselectBox = new JButton("Unselect");
        unselectBox.setPreferredSize(new Dimension(290, unselectBox.getPreferredSize().height + 4));
        unselectBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                nameBoxTextField.setText("");
                nameBoxTextField.setEnabled(false);
                ModelRenderer.setSelectedBox(null);
                boxList.clearSelection();
            }
        });
        //newBox.setBorderPainted(false);

        editorPane.add(unselectBox);

        JButton newBox = new JButton("New Box");
        newBox.setPreferredSize(new Dimension(142, newBox.getPreferredSize().height + 4));
        newBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO add code to create a new box, resizing and moving is also needed. If a box is already selected then create new as child
                //  or add new button to create child instead.
            }
        });
        //newBox.setBorderPainted(false);

        editorPane.add(newBox);

        JButton removeBox = new JButton("Delete Box");
        removeBox.setPreferredSize(new Dimension(143, removeBox.getPreferredSize().height + 4));
        removeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ModelRenderer.getSelectedBox() != null){
                    ModelRenderer.getSelectedBox().delete();
                    nameBoxTextField.setText("");
                    nameBoxTextField.setEnabled(false);
                    DefaultListModel listModel = new DefaultListModel();

                    listModel = addBoxesList(ModelRenderer.boxList, listModel);

                    boxList.clearSelection();
                    boxList.setModel(listModel);
                }
            }
        });
        //newBox.setBorderPainted(false);

        editorPane.add(removeBox);

        this.repaint();

        modelRender = new ModelRenderer(lwjglCanvas);

        DefaultListModel listModel = new DefaultListModel();

        listModel = addBoxesList(ModelRenderer.boxList, listModel);

        boxList.setModel(listModel);

        boxList.addListSelectionListener(new ListSelectionHandler(modelRender));

        this.setVisible(true);

        Thread renderer = new Thread(modelRender);
        renderer.start();

        this.setLocationRelativeTo(null);

    }

    public DefaultListModel addBoxesList(ModelBox[] boxList, DefaultListModel listModel) {
        for (ModelBox box : boxList) {
            listModel.addElement(box.name);
            addBoxesList(box.getChildren(), listModel);
        }
        return listModel;
    }


    public static DefaultListModel addBoxesList(ArrayList<ModelBox> children, DefaultListModel listModel) {
        for(ModelBox box: children){
            listModel.addElement(box.name);
            addBoxesList(box.getChildren(), listModel);
        }
        return listModel;
    }


    public BufferedImage loadTexture(String texture){
        try {
            return ImageIO.read(getClass().getClassLoader().getResource(texture));
        } catch (IOException e) {
            System.out.println("Could not set image: " + texture);
            return null;
        }
    }

    public static ModelBox findBox(String name){
        for(ModelBox box : ModelRenderer.boxList){
            if(box.name.equals(name)){
                return box;
            }
            else{
                ModelBox childBox = searchChildren(box.getChildren(), name);
                if(childBox != null){
                    return childBox;
                }
            }
        }

        return null;
    }

    private static ModelBox searchChildren(ArrayList<ModelBox> children, String name){
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

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub

    }


}
