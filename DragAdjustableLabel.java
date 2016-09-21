package disc;

import java.awt.Robot;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.stage.Screen;

/**
 * A custom Node which is working like a Slider. <br>
 * <b>!</b> When you hold the mouse on it and drag upside the value is
 * increased. <br>
 * <b>!</b> When you hold the mouse on it and drag down side the value is
 * decreased.
 * 
 * <br>
 * Usage:
 * 
 * <pre>
 * <code>
*     //initialize
*     DragAdjustableLabel dragAdjustableLabel = new DragAdjustableLabel(10, 0, 100);
*     
*     //add it for example to a BorderPane
*     primaryStage.setScene(new Scene(new BorderPane(dragAdjustableLabel)));
 *</code>
 * </pre>
 * 
 * @author GOXR3PLUS
 * @version 1.0
 */
public class DragAdjustableLabel extends Label {

	// Variables
	private int screenX;
	private int screenY;
	private int previousY;
	private int minimumValue;
	private int maximumValue;

	/**
	 * The current value of the DragAdjustableLabel
	 */
	private IntegerProperty currentValue;

	/**
	 * Constructor
	 * 
	 * @param minimumValue
	 *            Minimum Value that the slider can have
	 * @param maximumValue
	 *            Maximum Value that the slider can have
	 */
	public DragAdjustableLabel(int currentValue, int minimumValue, int maximumValue) {

		this.currentValue = new SimpleIntegerProperty(currentValue);
		this.minimumValue = minimumValue;
		this.maximumValue = maximumValue;

		// Add a costume style class
		this.getStyleClass().add("drag-adjustable-label");
		setCursor(Cursor.OPEN_HAND);

		textProperty().bind(this.currentValue.asString().concat(" %"));

		// when the mouse is pressed
		setOnMousePressed(m -> {
			screenX = (int) m.getScreenX();
			screenY = (int) m.getScreenY();
			setCursor(Cursor.NONE); // comment this line to make the cursor
									// visible
		});

		// when the mouse is dragged
		setOnMouseDragged(m -> {

			// calculate the monitor height
			double screenHeight = Screen.getPrimary().getBounds().getHeight();

			// !if the mouse has reached the the top of the monitor
			// or
			// ! if the mouse has reached the bottom of the monitor
			if (m.getScreenY() <= 0 || m.getScreenY() + 10 >= screenHeight) {
				resetMouse();
				return;
			}

			// Calculate the current value
			setCurrentValue(
					getCurrentValue() + (m.getScreenY() == previousY ? 0 : m.getScreenY() > previousY ? -1 : 1));
			previousY = (int) m.getScreenY();
		});

		// when the mouse is released
		setOnMouseReleased(m -> {
			resetMouse();
			setCursor(Cursor.OPEN_HAND);
		});
	}

	/**
	 * Reset the mouse to the default position(which is the center of the
	 * element)
	 * 
	 * @param x
	 * @param y
	 */
	private void resetMouse() {
		try {
			new Robot().mouseMove(screenX, screenY);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Set the current value
	 * 
	 * @param value
	 */
	public void setCurrentValue(int value) {

		// if the value is between the limits
		if (value >= minimumValue && value <= maximumValue)
			currentValue.set(value);
	}

	public int getCurrentValue() {
		return currentValue.get();
	}

	public IntegerProperty currentValueProperty() {
		return currentValue;
	}

}
