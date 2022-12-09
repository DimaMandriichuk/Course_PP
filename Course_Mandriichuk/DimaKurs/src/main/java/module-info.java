module com.example.dimakurs {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
    requires lombok;
    requires java.sql;

    opens com.example.dimakurs.entity to javafx.base;
    exports com.example.dimakurs.entity;
    opens com.example.dimakurs to javafx.fxml;
    exports com.example.dimakurs;
    exports com.example.dimakurs.controllers;
    opens com.example.dimakurs.controllers to javafx.fxml;
}