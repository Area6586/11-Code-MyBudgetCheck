package mybudgetcheck.model;

import java.io.Serializable;
import java.time.LocalDate;
//payment class
public class Payment  implements Serializable {
	private static final long serialVersionUID = 4136222750480274368L;
	private int paymentId;
	private String description;
	private double value; 
	private LocalDate date;
	
	public Payment (String description, double value, LocalDate date){
		this.description = description;
		this.value = value;
		this.date = date;
	}
	
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}

}
