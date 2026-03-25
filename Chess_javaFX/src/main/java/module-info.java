module org.devops.chess_javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens org.devops.chess_javafx to javafx.fxml;
    exports org.devops.chess_javafx;
}