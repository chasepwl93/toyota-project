package controller;

import java.util.ArrayList;
import model.RecordsDAO;

public class RecordController {
	private RecordsDAO dao;
	
	public RecordController() {
		dao = new RecordsDAO();
	}
	
	public ArrayList<String> getByItemNo(int ItemNo){
		
		ArrayList<String> itemList = dao.getByItemNo(ItemNo);
		
		return itemList;
	}
	
	public void save(int ItemNo, ArrayList<String> timeRecords) { 
		dao.save(ItemNo, timeRecords);
	}
	
	public void ToCSV()  { 
		dao.ToCSV();
	}
	
	public void deleteAll() {
		dao.deleteAll();
	}

	public void closeConnection() {
		dao.closeConnection();
		
	}
}
