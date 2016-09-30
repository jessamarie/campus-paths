package hw9;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * MapPanel class draws the map image to the screen. It is also responsible for
 * drawing and erasing routes on the map.
 */

public class MapPanel extends JPanel {

	/** Constants */
	private final String FILENAME = "hw9/data/RPI_campus_map_2010_extra_nodes_edges.png";
	private final double ACTUAL_WIDTH = 2175.0;
	private final double ACTUAL_HEIGHT = 2000.0; // due to crop

	/**
	 * No Representation Invariant or Abstraction Function
	 */

	/** The map image to be displayed on the panel */
	private BufferedImage mapImage;

	/**
	 * fields which keep track of map size and ratio and help update coordinates
	 */
	private int new_width;
	private int new_height;
	private double width_ratio; // new_width/actual
	private double height_ratio; // new_height/actual

	/** Coordinates of the start and end buildings */
	private double startX;
	private double startY;
	private double destX;
	private double destY;

	/** Coordinates of the desired path */
	ArrayList<ArrayList<Double>> coordinates;

	/**
	 * Constructor
	 */
	public MapPanel() {

		coordinates = null;
		
		startX = 0;
		startY = 0;
		destX = 0;
		destY = 0;

		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		loadImage();

		new_width = mapImage.getWidth();
		new_height = mapImage.getHeight();

		this.setPreferredSize(new Dimension(new_width, new_height));

		width_ratio = new_width / ACTUAL_WIDTH;
		height_ratio = new_height / ACTUAL_HEIGHT;

	}

	/**
	 * LoadImage is a helper method to load the map image
	 */
	private void loadImage() {

		try {

			// Read Image Original size = 2175 x 3400
			BufferedImage readImage = ImageIO.read(new File(FILENAME));

			// Crop Image size = 2175 x 2000
			BufferedImage subImage = readImage.getSubimage(0, 0,
					readImage.getWidth(), readImage.getHeight() - 1400);

			// image is too large, so let's resize it. size = 805 x 740
			mapImage = getScaledImage(subImage,
					(int) (subImage.getWidth(null) / 2.7),
					(int) (subImage.getHeight(null) / 2.7));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @returns preferred Dimension of panel
	 */

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(new_width, new_height);
	}

	/**
	 * paintComponent draws the image to the panel
	 */

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		this.setPreferredSize(new Dimension(new_width, new_height));

		g2d.drawImage(mapImage, 0, 0, mapImage.getWidth(),
				mapImage.getHeight(), null);

		// Draw paths
		if (coordinates != null) {
			drawTheCoordinates(g2d);
		}
		
		//DrawPoints
		drawThePoints(g2d);

		// Update the size of the map
		this.setSize((new Dimension(new_width, new_height)));
	}

	/**
	 * Draws the end points onto the map
	 * 
	 * @param g2d
	 */
	private void drawThePoints(Graphics2D g2d) {
		// Draw the points of the buildings according
		// to the current size of the map
		if (startX != 0 && startY != 0) {
			g2d.setColor(Color.BLUE);
			g2d.fill3DRect((int) (startX * width_ratio),
					(int) (startY * height_ratio), 12, 12, false);
		}

		if (destX != 0 && destY != 0) {
			g2d.setColor(Color.RED);
			g2d.fill3DRect((int) (destX * width_ratio),
					(int) (destY * height_ratio), 12, 12, false);
		}
	}
		
		/**
		 * Draws the route onto the map
		 * @param g2d
		 */
		private void drawTheCoordinates(Graphics2D g2d) {
			
			int currentX = (int) (startX * width_ratio);
			int currentY = (int) (startY * height_ratio);

			for (ArrayList<Double> coordinate : coordinates) {
				int nextX = (int) (coordinate.get(0) * width_ratio) + 1;
				int nextY = (int) (coordinate.get(1) * height_ratio) + 1;
				g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
						BasicStroke.JOIN_ROUND));
				g2d.setColor(Color.getHSBColor(0.83f, 1.00f, 0.25f));
				g2d.drawLine(currentX, currentY, nextX, nextY);

				currentX = nextX;
				currentY = nextY;
			}
			
			// Reset coordinate list so that it doesn't draw
			// until find path button is pressed again
			coordinates = null;
		}

	/**
	 * Resets the Map
	 */
	public void reset() {
		// Reset coordinates
		startX = 0;
		startY = 0;
		destX = 0;
		destY = 0;

		// Reset path
		coordinates = null;

		this.repaint();
	}

	/**
	 * Resizes an image using a Graphics2D object backed by a BufferedImage.
	 * 
	 * @param originalImage
	 *            - source image to scale
	 * @param width
	 *            - desired width
	 * @param height
	 *            - desired height
	 * @return - the new resized image
	 */
	private BufferedImage getScaledImage(BufferedImage originalImage, int width,
			int height) {
		
		double scaleX = (double) width / ACTUAL_WIDTH;
		double scaleY = (double) height / ACTUAL_HEIGHT;
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(
				scaleX, scaleY);
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(
				scaleTransform, AffineTransformOp.TYPE_BILINEAR);

		return bilinearScaleOp.filter(originalImage, new BufferedImage(width, height,
				originalImage.getType()));
	}

	/**
	 * setDestinationPoints changes the coordinates of the starting.
	 * 
	 * @modifies this.startX and this.startY
	 * @effects changes this.startX and this.startY the current building choice
	 */
	public void setStartingPoints(double startX, double startY) {
		this.startX = startX-6;
		this.startY = startY-4;
	}

	/**
	 * setDestinationPoints changes the coordinates of the destination.
	 * 
	 * @modifies this.destX and this.destY
	 * @effects changes this.destX and this.destY the current building choice
	 */
	public void setDestinationPoints(double destX, double destY) {
		this.destX = destX-6;
		this.destY = destY-4;
	}

	/**
	 * setCurrentPath changes the path that the user has chosen.
	 * 
	 * @modifies this.coordinates
	 * @effects changes this.coordinates to the users desired path look up
	 */
	public void setCurrentPath(ArrayList<ArrayList<Double>> coordinates) {
		this.coordinates = coordinates;
	}

}