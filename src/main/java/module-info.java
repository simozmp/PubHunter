module PubHunter {
    requires javafx.media;
    requires javafx.maven.plugin;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.web;

    exports view;
    opens view to javafx.fxml;
    exports logic;
}