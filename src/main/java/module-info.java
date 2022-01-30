module PubHunter {
    requires javafx.media;
    requires javafx.maven.plugin;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.web;
    requires java.mail;
    requires java.desktop;
    requires java.sql;
    requires org.json;

    exports view;
    opens view to javafx.fxml;
    exports logic.exception;
    opens logic.exception to javafx.fxml;
    opens logic.bean to javafx.fxml;
    exports logic.bean;
    exports logic.model;
    opens logic.model to javafx.fxml;
    exports logic.controller;
    opens logic.controller to javafx.fxml;
    exports view.javafx.desktop;
    opens view.javafx.desktop to javafx.fxml;
}