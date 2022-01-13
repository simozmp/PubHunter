module PubHunter {
    requires javafx.media;
    requires javafx.maven.plugin;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.web;
    requires java.mail;

    exports view;
    opens view to javafx.fxml;
    exports logic;
    opens logic to javafx.fxml;
    exports logic.exception;
    opens logic.exception to javafx.fxml;
    opens logic.bean to javafx.fxml;
    exports logic.bean;
}