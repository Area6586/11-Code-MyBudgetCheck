package mybudgetcheck.dao;

import java.util.ArrayList;

import mybudgetcheck.model.Payment;

public interface PaymentDAO {
	public void init() throws DBException;
	public void close() throws DBException;
	public void addPayment(Payment payment) throws DBException;
	public ArrayList<Payment> getAllPayments() throws DBException;
	public void deletePayments() throws DBException;
}
