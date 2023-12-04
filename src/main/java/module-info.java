module dev.utsawb {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens dev.utsawb to javafx.fxml;
    exports dev.utsawb;
}
