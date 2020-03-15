package controller;

import java.util.ArrayList;

import model.CarsDAO;
import model.pojo.Car;
import utils.Log;

public class CarController {

	private static CarsDAO dao;
	private static CarController single_instance = null;

	private CarController() {
	}

	public static CarController getInstance() {
		if (single_instance == null) {
			dao = new CarsDAO();
			single_instance = new CarController();
		}
		return single_instance;
	}

	public ArrayList<Car> getAllCars() {

		return dao.getAllItems();
	}

	public void updateOrSaveCar(Car car) {

		dao.updateOrSave(car);

	}

	public Car getCarbyID(String id) {

		return dao.getItembyId(id);
	}

	public Boolean deleteCarByID(String id) {
		return dao.deleteCar(id);
	}

	public void closeConnection() {
		dao.closeConnection();

	}

	public void importExcel(String filePath) {
		dao.importExcel(filePath);
	}

	public Car getAuctionItem() {
		return dao.getAuctionItem();
	}

	public void ToCSV() {
		dao.ToCSV();
	}

	public void deleteAll() {
		dao.deleteAll();
	}
}
