package mybudgetcheck.services;


import java.util.ArrayList;

import mybudgetcheck.dao.DBException;
import mybudgetcheck.dao.PaymentDAO;
import mybudgetcheck.dao.PaymentFileDAO;
import mybudgetcheck.model.Payment;

/**
 * Business Layer
 **/
public class PaymentService {
	PaymentDAO dao = new PaymentFileDAO();

	public void init() throws UIException {
		try {
			dao.init();
		} catch (DBException e) {
			e.printStackTrace();
			throw new UIException(e.getMessage());
		}
	}

	public void addPayment(Payment payment) throws UIException {
		try {
			dao.addPayment(payment);
		} catch (DBException e) {
			e.printStackTrace();
			throw new UIException(e.getMessage());
		}
	}

	public ArrayList<Payment> getAllPayments() throws UIException  {
		try {
			return dao.getAllPayments();
		} catch (DBException e) {
			e.printStackTrace();
			throw new UIException(e.getMessage());
		}
	}

	public void deletePayments() throws UIException{
		try {
			dao.deletePayments();
		} catch (DBException e) {
			e.printStackTrace();
			throw new UIException(e.getMessage());
		}
	}
	
	public double getSumOfPayments() throws UIException{
		
		try {
			ArrayList<Payment> allPayments = dao.getAllPayments();
			double sum = 0;
			for (int i=0; i < allPayments.size(); i++) {
				sum += allPayments.get(i).getValue();
			}
			return sum;
			
		} catch (DBException e) {
			e.printStackTrace();
			throw new UIException(e.getMessage());
		}
	}

}
