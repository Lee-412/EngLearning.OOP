module englishlearningapp.englearning {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires mysql.connector.java;
    requires voicerss.tts;
    requires java.desktop;
    requires AnimateFX;
    requires javafx.web;
    requires javafx.base;
    requires javafx.media;

    opens englishlearningapp.englearning to javafx.fxml;
    exports englishlearningapp.englearning;
    exports englishlearningapp.englearning.Controller;
    opens englishlearningapp.englearning.Controller to javafx.fxml;
    exports englishlearningapp.englearning.Game;
    opens englishlearningapp.englearning.Game to javafx.fxml;
}