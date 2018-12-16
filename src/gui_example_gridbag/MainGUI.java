/* This program is to demonstrate clean GUI design and layout
 * The program doesn't do anything in terms of computation it is
 * to demonstrate GUI design only.  The GUI layout uses 4 layout
 * managers Box, BorderLayout, GridBag, and FlowLayout. The code is
 * also to demonstrate how clean a GUI can look along with the code
 * so it is easy to reason about.  
 * 
 * The layout is very similar to Derek Banas Java Tutorial video 30. 
 * I took his basic layout and improved upon it to demonstrate how clean
 * code can look like in GUI design.  */

package gui_example_gridbag;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainGUI extends JFrame{
	private JPanel leftPanel, centerPanel, northPanel, southPanel, flowPanel;
	private JLabel nameLabel, streetLabel, stateLabel, apptLabel, ageLabel;
	private JTextField nameField, streetField;
	private GridBagLayout gbLeft, gbRight;
	private GridBagConstraints gcLeft, gcRight;
	private JComboBox<String> stateComboBox;
	private Box optionBox, genderBox;
	private JCheckBox morningCheckBox, noonCheckBox, eveningCheckBox;
	private ButtonGroup genderGroup;
	private JRadioButton maleRadioBtn, femaleRadioBtn;
	private JSpinner dateSpinner;
	private JTextArea aboutTextArea;
	private JSlider ageSlider;
 
	public MainGUI() {
		initGUI();
	}
	
	private void initGUI() {
		initFrame();
		initObjects();
		setupPanels();
		setupFlowPanelLayout();
		setupLeftPanelLayout();
		setupCenterPanelLayout();
		setupListeners();
	}
	
	//Initialize frame, self explanatory.  The main layout is a border layout.
	private void initFrame() {
		this.setResizable(false);
		this.setSize(620, 340);
		this.setTitle("GUI gridbag example");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
	}
	
	
	//Create or instantiate the new objects or swing components
	private void initObjects() {
		//Panels
		leftPanel = new JPanel();
		centerPanel = new JPanel();
		northPanel = new JPanel();
		southPanel = new JPanel();
		flowPanel = new JPanel();
		
		//Labels
		ageLabel = new JLabel("age: ");
		apptLabel = new JLabel("appointment: ");
		nameLabel = new JLabel("name: ");
		streetLabel = new JLabel("street: ");
		stateLabel = new JLabel("state: ");
		
		//Text Fields
		nameField = new JTextField(10);
		streetField = new JTextField(10);
		
		//Grid bag layout and grid bag constraints
		gbLeft = new GridBagLayout();
		gbRight = new GridBagLayout();
		gcLeft = new GridBagConstraints();
		gcRight = new GridBagConstraints();
		
		//Combo boxes
		stateComboBox = new JComboBox<String>();
		
		//Box
		optionBox = Box.createVerticalBox();
		genderBox = Box.createVerticalBox();
		
		//JCheck boxes
		morningCheckBox = new JCheckBox("morning");
		noonCheckBox = new JCheckBox("afternoon");
		eveningCheckBox = new JCheckBox("evening");
		
		//Groups for radio buttons
		genderGroup = new ButtonGroup();
		
		//Radio Buttons
		maleRadioBtn = new JRadioButton("male");
		femaleRadioBtn = new JRadioButton("female");
		
		//Spinners
		dateSpinner = createSpinner(Calendar.DAY_OF_MONTH, "dd/MMM/yyyy");
		
		//Text Areas
		aboutTextArea = new JTextArea(5, 30);
		
		//Sliders
		ageSlider = new JSlider(1, 99, 25);
	}
	
	//setting special attributes for any component, seperate method so I don't clog up
	//other methods.
	private void setSpecialComponentAttributes() {
		aboutTextArea.setText("Hello World!");
		aboutTextArea.setLineWrap(true);
		
		increaseComponentSize((JSlider)ageSlider, 100, 15);
		
		ageLabel.setText( "age: " + Integer.toString(ageSlider.getValue()));
	}
	
	//add panels to frame and set the layouts for each panel used.
	private void setupPanels() {
		this.add(leftPanel, BorderLayout.WEST);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(southPanel, BorderLayout.SOUTH);
		
		northPanel.setBackground(Color.DARK_GRAY);
		southPanel.setBackground(Color.DARK_GRAY);
		
		flowPanel.setLayout(new FlowLayout());
		
		//by default java doesn't allow you to set component size on some layouts
		//to remedy this call this method and supply the component, be sure to cast it
		//followed by the width and height.  A negative number signifies that you don't
		//want that changed.
		increaseComponentSize((JPanel)leftPanel, 250, -1);
		increaseComponentSize((JPanel)northPanel, -1, 25);
		increaseComponentSize((JPanel)southPanel, -1, 25);
		increaseComponentSize((JPanel)flowPanel, 340, 50);
		
		//assign layouts to panels
		leftPanel.setLayout(gbLeft);
		centerPanel.setLayout(gbRight);
	}
	
	//If you don't want to change width then enter a negative number, likewise for 
	//height.
	private void increaseComponentSize(Component comp, int width, int height) {
		if(width <= -1 || height <= -1) {
			if(width <= -1) {
				width = comp.getWidth();
			}else if(height <= -1) {
				height = comp.getHeight();
			}
		}

		Dimension dim = comp.getPreferredSize();
		dim.width = width;
		dim.height = height;
		comp.setPreferredSize(dim);
	}
	
	//everything needed to setup left panel
	private void setupLeftPanelLayout() {
		//gridBagAttrs(gcLeft, xPos, yPos, width, height, weighX, weightY);
		
		//setup state combo box
		String states[] = {"PA", "MI", "CO", "AK", "KS", "FL", "TX", "OH"};
		stateComboBox.setModel(new DefaultComboBoxModel<String>(states));
		setUpOptionBox("Time of Day");
		
		setSpecialComponentAttributes();
		
		//Row 1
		gridBagAttrs(gcLeft, 0, 0, 1, 1, 1, 0.1);
		gcLeft.fill = GridBagConstraints.NONE;
		gcLeft.anchor = GridBagConstraints.LINE_END;
		leftPanel.add(nameLabel, gcLeft);
		
		gridBagAttrs(gcLeft, 1, 0, 2, 1, 1, 0.1);
		gcLeft.fill = GridBagConstraints.NONE;
		gcLeft.anchor = GridBagConstraints.LINE_START;
		leftPanel.add(nameField, gcLeft);
		
		//Row 2
		gridBagAttrs(gcLeft, 0, 1, 1, 1, 1, 0.1);
		gcLeft.fill = GridBagConstraints.NONE;
		gcLeft.anchor = GridBagConstraints.LINE_END;
		leftPanel.add(streetLabel, gcLeft);
		
		gridBagAttrs(gcLeft, 1, 1, 2, 1, 1, 0.1);
		gcLeft.fill = GridBagConstraints.NONE;
		gcLeft.anchor = GridBagConstraints.LINE_START;
		leftPanel.add(streetField, gcLeft);
		
		//Row 3
		gridBagAttrs(gcLeft, 1, 2, 1, 1, 1, 2.0);
		gcLeft.fill = GridBagConstraints.NONE;
		gcLeft.anchor = GridBagConstraints.FIRST_LINE_END;
		leftPanel.add(stateLabel, gcLeft);
		
		gridBagAttrs(gcLeft, 2, 2, 1, 1, 3.0, 1.0);
		gcLeft.fill = GridBagConstraints.NONE;
		gcLeft.anchor = GridBagConstraints.FIRST_LINE_START;
		leftPanel.add(stateComboBox, gcLeft);
		
		//Row 4
		gridBagAttrs(gcLeft, 1, 3, 2, 1, 1, 12.0);
		gcLeft.fill = GridBagConstraints.NONE;
		gcLeft.anchor = GridBagConstraints.FIRST_LINE_START;
		leftPanel.add(optionBox, gcLeft);
	}
	
	private void setupFlowPanelLayout() {
		flowPanel.add(ageLabel, FlowLayout.LEFT);
		flowPanel.add(dateSpinner, FlowLayout.LEFT);
		flowPanel.add(apptLabel, FlowLayout.LEFT);
		flowPanel.add(ageSlider, FlowLayout.RIGHT);
	}
	
	private void setupCenterPanelLayout() {
		setupGenderBox();
		
		//Row 1
		gridBagAttrs(gcRight, 0, 0, 1, 1, 1, 0.1);
		gcRight.fill = GridBagConstraints.NONE;
		gcRight.anchor = GridBagConstraints.WEST;
		centerPanel.add(genderBox, gcRight);		
		
		//Row 2
		gridBagAttrs(gcRight, 0, 1, 1, 1, 1, 0.1);
		gcRight.fill = GridBagConstraints.NONE;
		gcRight.anchor = GridBagConstraints.FIRST_LINE_START;
		centerPanel.add(flowPanel, gcRight);
		
		//row 3
		gridBagAttrs(gcRight, 0, 2, 2, 4, 5, 5.0);
		gcRight.fill = GridBagConstraints.NONE;
		gcRight.anchor = GridBagConstraints.WEST;
		centerPanel.add(new JScrollPane(aboutTextArea), gcRight);
	}
	
	private void setupListeners() {
		ageSlider.addChangeListener(new SliderListener());
	}
	
	private void setUpOptionBox(String optionBoxTitle) {
		optionBox.setBorder(BorderFactory.createTitledBorder(optionBoxTitle));
		optionBox.add(morningCheckBox);
		optionBox.add(noonCheckBox);
		optionBox.add(eveningCheckBox);
	}

	
	private void setupGenderBox() {
		genderGroup.add(maleRadioBtn);
		genderGroup.add(femaleRadioBtn);
		genderBox.add(maleRadioBtn);
		genderBox.add(femaleRadioBtn);
		genderBox.setBorder(BorderFactory.createTitledBorder("gender"));
		
	}
	
	
	private void gridBagAttrs(GridBagConstraints gcType, int gridX, int gridY, int gridWidth, int gridHeight, double weightX, double weightY) {
		gcType.gridx = gridX;
		gcType.gridy = gridY;
		gcType.gridwidth = gridWidth;
		gcType.gridheight = gridHeight;
		gcType.weightx = weightX;
		gcType.weighty = weightY;
		gcType.insets = new Insets(5, 5, 5, 5);
	}
	
	//Create any date or time spinner, just supply the increment time and format.
	//No minimums and maximums
	private JSpinner createSpinner(int incTime, String format) {
		JSpinner ds;
		Date today = new Date();
		SpinnerDateModel settings = new SpinnerDateModel(today, null, null, incTime);
		ds = new JSpinner(settings);
		JSpinner.DateEditor editDate = new JSpinner.DateEditor(ds, format);
		ds.setEditor(editDate);
		return ds;
	}
	
	//Used for the age slider, only to see value change of slider.
	private class SliderListener implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider slider = (JSlider)e.getSource();
			if(slider == ageSlider) {
				ageLabel.setText("Age: " + ageSlider.getValue());    
			}
			
		}
	}
}
