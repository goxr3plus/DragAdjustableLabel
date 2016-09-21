package disc;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Tester extends Application {

	DragAdjustableLabel dragAdjustableLabel = new DragAdjustableLabel(10, 0, 100);

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.centerOnScreen();
		primaryStage.setWidth(300);
		primaryStage.setHeight(150);

		// BorderPane
		BorderPane root = new BorderPane();

		// label
		Label helpLabel = new Label("Hold and drag on the element below!");
		root.setTop(helpLabel);

		// Add some inline .css for the dragAdjustableLabel
		// (you can also use external .css)
		// -------------------------->check the DrgAdjustableLabel code--)
		dragAdjustableLabel.setStyle(
				"-fx-background-color:black; -fx-background-radius:15; -fx-text-fill:white; -fx-font-size:16; -fx-font-weight:bold;");
		root.setCenter(dragAdjustableLabel);

		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		primaryStage.setOnCloseRequest(request -> Platform.exit());

	}

	public static void main(String[] args) {
		launch(args);
	}
}
