package Interface;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.lang.reflect.Array;

public class ChessboadController {

    @FXML
    private GridPane ChessboardGrid;

//    private Array<Chess>

    @FXML
    private Label TestText;

    public void initialize() {
        Paint bisque = Paint.valueOf("BISQUE");
        Paint black = Paint.valueOf("SADDLEBROWN");

        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                Rectangle rectangle = new Rectangle(90, 90);
                switch ((i + j) %2) {
                    case (0):
                        rectangle.setFill(bisque);
                        break;
                    case (1):
                        rectangle.setFill(black);
                        break;
                }

//                Circle circle = new Circle(25);
//                circle.setFill(Color.GRAY);
//                circle.setCursor(Cursor.HAND);
//                circle.setOnMouseClicked(this::onAbandonButtonClic);

                ChessboardGrid.add(rectangle, i, j);
            }
        }

    }

    @FXML
    protected void onAbandonButtonClic(javafx.scene.input.MouseEvent event) {
        ChessboardGrid.getChildren().clear();
    }
}
