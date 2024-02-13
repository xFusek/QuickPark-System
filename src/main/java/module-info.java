module pl.kul.quickparksystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires eu.hansolo.tilesfx;
    requires org.apache.pdfbox;
    requires java.logging;

    opens pl.kul.quickparksystem.controller to javafx.fxml;
    exports pl.kul.quickparksystem;
    opens pl.kul.quickparksystem.utils to javafx.fxml;
}