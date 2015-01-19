package com.sekwah.modeleditor.windows;

import com.sekwah.modeleditor.modelparts.ModelBox;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ModelEditorWindow extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1349457624026308068L;
	private JPanel contentPane;
	
	private BufferedImage favicon = null;
	private Canvas lwjglCanvas;
	private JPanel editorPane;
	private ModelRenderer modelRender;
	
	public ModelEditorWindow(){
		
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
        
        JTextField nameBox = new JTextField("Box1");
        nameBox.setSelectionColor(new Color(45,135,8));
        nameBox.setPreferredSize(new Dimension(290, nameLabel.getPreferredSize().height + 4));
        editorPane.add(nameBox);
        
        JLabel xRotationLabel = new JLabel("Rot X");
        xRotationLabel.setForeground(new Color(255,255,255));
        xRotationLabel.setPreferredSize(new Dimension(290, xRotationLabel.getPreferredSize().height));
        
        editorPane.add(xRotationLabel);
        
        JSlider xRotation = new JSlider();
        xRotation.setPreferredSize(new Dimension(290, xRotation.getPreferredSize().height));
        editorPane.add(xRotation);
        
        JLabel yRotationLabel = new JLabel("Rot Y");
        yRotationLabel.setForeground(new Color(255,255,255));
        yRotationLabel.setPreferredSize(new Dimension(290, yRotationLabel.getPreferredSize().height));
        
        editorPane.add(yRotationLabel);
        
        JSlider yRotation = new JSlider();
        yRotation.setPreferredSize(new Dimension(290, yRotation.getPreferredSize().height));
        editorPane.add(yRotation);
        
        JLabel zRotationLabel = new JLabel("Rot Z");
        zRotationLabel.setForeground(new Color(255,255,255));
        zRotationLabel.setPreferredSize(new Dimension(290, zRotationLabel.getPreferredSize().height));
        
        editorPane.add(zRotationLabel);
        
        JSlider zRotation = new JSlider();
        zRotation.setPreferredSize(new Dimension(290, zRotation.getPreferredSize().height));
        editorPane.add(zRotation);
        
        JList boxList = new JList();
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
                       modelRender.setSelectedBox(null);
                }
        });
        //newBox.setBorderPainted(false);

        editorPane.add(unselectBox);
        
        JButton newBox = new JButton("New Box");
        newBox.setPreferredSize(new Dimension(142, newBox.getPreferredSize().height + 4));
        //newBox.setBorderPainted(false);
        
        editorPane.add(newBox);
        
        JButton removeBox = new JButton("Delete Box");
        removeBox.setPreferredSize(new Dimension(143, removeBox.getPreferredSize().height + 4));
        //newBox.setBorderPainted(false);
        
        editorPane.add(removeBox);
        
        this.repaint();
        	
        modelRender = new ModelRenderer(lwjglCanvas);
        
        DefaultListModel listModel = new DefaultListModel();
        for(ModelBox box: modelRender.boxList){
        	listModel.addElement(box.name);
        }
        boxList.setModel(listModel);
        
        boxList.addListSelectionListener(new ListSelectionHandler(modelRender));
        
        this.setVisible(true);
        
        Thread renderer = new Thread((Runnable) modelRender);
        renderer.start();
        
        this.setLocationRelativeTo(null);
        
	}
	
	
	
	
	
	public BufferedImage loadTexture(String texture){
		try {
			return ImageIO.read(getClass().getClassLoader().getResource(texture));
		} catch (IOException e) {
			System.out.println("Could not set image: " + texture);
			return null;
		}
	}





	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
