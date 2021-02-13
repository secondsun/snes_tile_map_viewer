module dev.secondsun {
    requires javafx.controls;
    requires javafx.swing;
    requires javafx.fxml;

    requires java.se;

    opens dev.secondsun to javafx.fxml;
    opens dev.secondsun.controller to javafx.fxml;
    exports dev.secondsun;
}
