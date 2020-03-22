package controller;

import java.util.ArrayList;
import model.RecordsDAO;

public class RecordController {
	private static RecordsDAO dao;
	private static RecordController single_instance = null;

	private RecordController() {
	}

	// limit to one instance
	public static RecordController getInstance() {
		if (single_instance == null) {
			dao = new RecordsDAO();
			single_instance = new RecordController();
		}
		return single_instance;
	}

	public ArrayList<String> getByItemNo(int ItemNo) {
		return dao.getByItemNo(ItemNo);
	}

	public void save(int ItemNo, ArrayList<String> timeRecords) {
		dao.save(ItemNo, timeRecords);
	}

	public void ToCSV() {
		dao.ToCSV();
	}

	public void deleteAll() {
		dao.deleteAll();
	}

	public void closeConnection() {
		dao.closeConnection();
	}
}
