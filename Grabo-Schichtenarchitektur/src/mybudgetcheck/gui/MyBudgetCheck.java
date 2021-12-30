package mybudgetcheck.gui;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import mybudgetcheck.model.Payment;
import mybudgetcheck.services.PaymentService;
import mybudgetcheck.services.UIException;


/**
 * Simple application for management of income and expenses
 * @author Marius Hofmeister
 */
public class MyBudgetCheck extends Application {
	PaymentService services = new PaymentService();
	final GridPane gridPane = new GridPane();

	@Override
	public void start(Stage primaryStage) throws UIException {
		
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setHgap(7);
		gridPane.setVgap(7);
		
		final Label lbDescription = new Label(GUIConstants.BESCHREIBUNG);
		final Label lbValue = new Label(GUIConstants.WERT);
		final Label lbDate = new Label(GUIConstants.DATUM);
		final Label lbError = new Label("");
		final Label lbSum = new Label("");
		final TextField tfDescription = new TextField();
		final TextField tfValue = new TextField();
		final TextField tfDate = new TextField();
		final Button btnSave = new Button(GUIConstants.NEUE_EINGABE_SPEICHERN);
		final Button btnDelete = new Button(GUIConstants.DATENBANK_LEEREN);
			
		gridPane.add(lbDescription, 0, 1);
		gridPane.add(lbValue, 1, 1);
		gridPane.add(lbDate, 2, 1);
		gridPane.add(tfDescription, 0, 2);
		gridPane.add(tfValue, 1, 2);
		gridPane.add(tfDate, 2, 2);
		gridPane.add(lbError, 3, 1);
		gridPane.add(btnSave, 3, 2);
		gridPane.add(btnDelete, 4, 2);
		gridPane.add(lbSum, 5, 1);
		
		try {
			services.init();
			printTable(lbError, lbSum);
			
		} catch (UIException e) {
			lbError.setText(GUIConstants.ERROR_WHILE_INITIALIZING_DATABASE);
		}
		
		btnSave.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
					Payment payment = new Payment(
							tfDescription.getText(),
							Double.parseDouble(tfValue.getText()),
							LocalDate.parse(tfDate.getText(), formatter));
					services.addPayment(payment);
				} catch (UIException e) {
					lbError.setText(GUIConstants.ERROR_WHILE_ADDING_PAYMENT_TO_DATABASE);
				}
				printTable(lbError, lbSum);
			}
			
		});
		
		btnDelete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					services.deletePayments();
				} catch (UIException e) {
					lbError.setText(GUIConstants.ERROR_WHILE_DELETING_PAYMENTS_FROM_DATABASE);
				}
				printTable(lbError, lbSum);
			}
			
		});
		
		primaryStage.setScene(new Scene(gridPane, 900, 300));
		primaryStage.setTitle(GUIConstants.GUI_TITLE);
		primaryStage.show();	
	}

	private void printTable(Label lbError, Label lbSum) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		NumberFormat formatterNum = NumberFormat.getCurrencyInstance();
		
		Iterator<Node> it = gridPane.getChildren().iterator();
		while (it.hasNext()) {
			Node node = (Node) it.next();
			if (GridPane.getRowIndex(node) >= 4) {
				it.remove();
			}
		}
		  
		ArrayList<Payment> allPayments;
		try {
			allPayments = services.getAllPayments();
			for (int i=0; i < allPayments.size(); i++) {
				final Label tblDesc = new Label(allPayments.get(i).getDescription());
				final Label tblValue = new Label(""+formatterNum.format(allPayments.get(i).getValue()));
				final Label tblDate = new Label(""+allPayments.get(i).getDate().format(formatter));
				gridPane.add(tblDesc, 0, 4+i);
				gridPane.add(tblValue, 1, 4+i);
				gridPane.add(tblDate, 2, 4+i); 
			}
			
			lbSum.setText(""+services.getSumOfPayments());
		
		} catch (UIException e) {
			lbError.setText(GUIConstants.ERROR_WHILE_RETRIEVING_PAYMENTS_FROM_DATABASE);
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
