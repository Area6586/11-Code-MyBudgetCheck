module GraboSchichtenarchitektur {
	requires javafx.controls;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;
	
	opens mybudgetcheck.gui to javafx.graphics, javafx.fxml;
}
