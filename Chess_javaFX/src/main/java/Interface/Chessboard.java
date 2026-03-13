package Interface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Chessboard extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Chessboard.class.getResource("chessboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 16*80, 9*80);
        stage.setMaximized(true);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }
}
