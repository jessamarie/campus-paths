package hw9;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;

import javax.imageio.ImageIO;

/**
 * RPICampusPathsGuiView is the main Graphical Interface for the CampusPaths
 * program, which adds the buttons to the map
 * 
 */

public class CampusPathsGuiView extends JFrame {

	/** Constants */
	private final static int SCREEN_WIDTH = 1024;
	private final static int SCREEN_HEIGHT = 768;

	/**
	 * No Representation Invariant or Abstraction Function
	 */

	// panels
	MapPanel mapPanel;
	JPanel compPanel;

	// Start and End build selection panel
	private JList<String> startList;
	private JList<String> destList;
	private DefaultListModel<String> startModel;
	private DefaultListModel<String> destModel;
	private JLabel startLabel;
	private JLabel destinationLabel;

	// Buttons
	private JButton findRouteButton;
	private JButton resetButton;

	/**
	 * constructor sets up the frame
	 */
	public CampusPathsGuiView() {

		this.setTitle("Campus Paths - By Jessica Barre");
		this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.WHITE);

		designLayout();

		this.pack();
		this.setResizable(true);
		this.setLocationRelativeTo(null);
	}

	/**
	 * designFrame is a helper method creates the frame elements and designs it
	 */
	private void designLayout() {

		this.setLayout(new BorderLayout());
		mapPanel = new MapPanel();
		mapPanel.setBackground(Color.WHITE);

		compPanel = new JPanel();
		compPanel.setPreferredSize(new Dimension(500, 500));
		compPanel.setBackground(Color.PINK);
		compPanel.setLayout(new GridBagLayout());

		buildListPanel();
		buildButtonPanel();
		getContentPane().add(mapPanel, BorderLayout.EAST);
		getContentPane().add(compPanel, BorderLayout.WEST);
	}

	/**
	 * buildLists builds the list components
	 */
	private void buildListPanel() {

		JPanel listPanel = new JPanel(new GridBagLayout());
		listPanel.setBackground(Color.PINK);
		
		startLabel = new JLabel("Choose your starting location: ");
		startLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		startModel = new DefaultListModel<String>();
		startList = new JList<String>(startModel);
		startList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		startList.setSelectedIndex(-1);
		startList.setBorder(new LineBorder(new Color(0, 0, 0)));

		destinationLabel = new JLabel("Choose your destination: ");
		destinationLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		destModel = new DefaultListModel<String>();
		destList = new JList<String>(destModel);
		destList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		destList.setSelectedIndex(-1);
		destList.setBorder(new LineBorder(new Color(0, 0, 0)));

		// add components to list panel and then add list panel to component
		// panel
		addComp(listPanel, startLabel, 0, 0, 1, 1,
				GridBagConstraints.NORTH, GridBagConstraints.BOTH);
		addComp(listPanel, new JScrollPane(startList), 0, 1, 1, 1,
				GridBagConstraints.NORTH, GridBagConstraints.BOTH);
		addComp(listPanel, destinationLabel, 0, 2, 1, 1,
				GridBagConstraints.NORTH, GridBagConstraints.BOTH);
		addComp(listPanel, new JScrollPane(destList), 0, 3, 1, 1,
				GridBagConstraints.NORTH, GridBagConstraints.BOTH);
		addComp(compPanel, listPanel, 0, 0, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH);

	}

	/**
	 * build buttons builds the button components
	 */
	private void buildButtonPanel() {

		JPanel buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setBackground(Color.PINK);

		findRouteButton = new JButton("Find Route");
		resetButton = new JButton("Reset");

		// add components to button and then add button panel to component
		// panel
		addComp(buttonPanel, findRouteButton, 0, 2, 1, 1,
				GridBagConstraints.NORTH, GridBagConstraints.BOTH);
		addComp(buttonPanel, resetButton, 0, 3, 1, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH);// add listeners
		addComp(compPanel, buttonPanel, 0, 1, 2, 2, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH);
	}

	/**
	 * addComp sets the rules for a component destined for a GridBagLayout and
	 * then adds it to a panel
	 */
	private void addComp(JPanel thePanel, JComponent comp, int xPos, int yPos,
			int compWidth, int compHeight, int place, int stretch) {

		GridBagConstraints gridConstraints = new GridBagConstraints();

		gridConstraints.gridx = xPos;
		gridConstraints.gridy = yPos;
		gridConstraints.gridwidth = compWidth;
		gridConstraints.gridheight = compHeight;
		gridConstraints.weightx = 100;
		gridConstraints.weighty = 100;
		gridConstraints.insets = new Insets(5, 5, 5, 5);
		gridConstraints.anchor = place;
		gridConstraints.fill = stretch;

		thePanel.add(comp, gridConstraints);

	}

	/**
	 * resets the frame
	 */
	public void reset() {

		// Reset lists
		destList.setSelectedIndex(0);
		startList.setSelectedIndex(0);
		
		// Reset map
		mapPanel.reset();
	}

	/**
	 * updateBuildingLists updates the building list for the lists
	 * 
	 * @param buildings
	 *            the list of buildings to be added to the map
	 * @modifies startModel and destModel
	 */
	public void updateBuildingLists(String[] buildings) {
		startModel.removeAllElements();
		destModel.removeAllElements();
		for (String b : buildings) {
			startModel.addElement(b);
			destModel.addElement(b);
		}
	}

	/**
	 * updatePoints updates the selected locations on the map with a point
	 * 
	 * @param startX
	 *           the starting x-coordinate
	 * @param startY
	 *            the starting y-coordinate
	 * @param destX
	 *            the destination x-coordinate
	 * @param dextY
	 *            the destination y-coordinate
	 * @modifies correlated values in map panel
	 */
	public void updatePoints(double startX, double startY, double destX,
			double destY) {
		mapPanel.setStartingPoints(startX, startY);
		mapPanel.setDestinationPoints(destX, destY);
		mapPanel.repaint();
	}

	/**
	 * updatePath updates the current path on the map panel
	 * 
	 * @param coordinates in the map panel
	 * @modifies correlated values in map panel
	 */
	public void updatePath(ArrayList<ArrayList<Double>> coordinates) {
		mapPanel.setCurrentPath(coordinates);
		mapPanel.repaint();

	}

	/**
	 * getStart returns the starting location
	 * 
	 * @return the currently selected start value
	 */
	public String getStart() {
		return startList.getSelectedValue();
	}

	/**
	 * getDest returns the destination location
	 * 
	 * @return the currently selected destination value
	 */
	public String getDestination() {
		return destList.getSelectedValue();
	}

	/**
	 * adds a ListSelectionListener to the start list
	 */
	public void addStartListener(ListSelectionListener listenForSelection) {
		startList.addListSelectionListener(listenForSelection);
	}

	/**
	 * adds ListSelectionListener to the destination list
	 */
	public void addDestListener(ListSelectionListener listenForSelection) {
		destList.addListSelectionListener(listenForSelection);
	}

	/**
	 * Adds an ActionListener to the reset button
	 */
	public void addResetListener(ActionListener listenForClick) {
		resetButton.addActionListener(listenForClick);
	}

	/**
	 * Adds an ActionListener to the Find button
	 */
	public void addFindPathtistener(ActionListener listenForClick) {
		findRouteButton.addActionListener(listenForClick);
	}

	/**
	 * Adds a Mouselistener to the mapPanel
	 */
	public void addClickListener(MouseAdapter listenForClick) {
		mapPanel.addMouseListener(listenForClick);
	}

	/**
	 * Displays an error message
	 */
	public void displayErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage);
	}

} // ends class

