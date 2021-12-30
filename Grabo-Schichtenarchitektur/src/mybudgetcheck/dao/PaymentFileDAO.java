package mybudgetcheck.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import mybudgetcheck.model.Payment;

/**
 * Data Access Object for operations on file 
 **/
public class PaymentFileDAO implements PaymentDAO {
	private static final String PAYMENTS_FILE = "payments.db";
	private File f;
	private ArrayList<Payment> list;

	public void init() throws DBException{
		f = new File(PAYMENTS_FILE);
	
		try {
			if(!f.exists()) {
				FileOutputStream fos;
					fos = new FileOutputStream(f);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(list);
				oos.close();
			}
			list = new ArrayList<Payment>();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException(e.getMessage());
		}
	}

	public void closeFile() {
		
	}

	public void addPayment(Payment payment) throws DBException {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			list.add(payment);
			oos.writeObject(list);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException(e.getMessage());
		}

	}

	public ArrayList<Payment> getAllPayments() throws DBException {
		try {
			FileInputStream fis;
			fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ArrayList<Payment> tmp = (ArrayList<Payment>) ois.readObject();
			list = tmp != null ? tmp: list;
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException(e.getMessage());
		}
		return list;
	}

	public void deletePayments() throws DBException {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			list.clear();
			oos.writeObject(list);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException(e.getMessage());
		}
	}

	@Override
	public void close() throws DBException {
		//To be implemented	
	}

}
