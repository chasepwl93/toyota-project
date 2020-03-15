package model.pojo;

public class Car {

	private int id;
	private String imageFile;
	private int itemNo;
	private String brand;
	private String model;
	private int year;
	private String milage;
	private String inspGrade;
	private int startingPrice;
	private int minSellPrice;
	private String sellerInfo;
	private int finalPrice;
	private String status;
	private int bidderNo;

	public Car() {
	}

	public Car(String imageFile, int itemNo, String brand, String model, int year, String milage, String inspGrade,
			int startingPrice, int minSellPrice, String sellerInfo, int finalPrice, String status, int bidderNo) {
		super();
		this.imageFile = imageFile;
		this.itemNo = itemNo;
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.milage = milage;
		this.inspGrade = inspGrade;
		this.startingPrice = startingPrice;
		this.minSellPrice = minSellPrice;
		this.sellerInfo = sellerInfo;
		this.finalPrice = finalPrice;
		this.status = status;
		this.bidderNo = bidderNo;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", imageFile=" + imageFile + ", itemNo=" + itemNo + ", brand=" + brand + ", model="
				+ model + ", year=" + year + ", milage=" + milage + ", inspGrade=" + inspGrade + ", startingPrice="
				+ startingPrice + ", minSellPrice=" + minSellPrice + ", sellerInfo=" + sellerInfo + ", finalPrice="
				+ finalPrice + ", status=" + status + ", bidderNo=" + bidderNo + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageFile() {
		return imageFile;
	}

	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMilage() {
		return milage;
	}

	public void setMilage(String milage) {
		this.milage = milage;
	}

	public String getInspGrade() {
		return inspGrade;
	}

	public void setInspGrade(String inspGrade) {
		this.inspGrade = inspGrade;
	}

	public int getStartingPrice() {
		return startingPrice;
	}

	public void setStartingPrice(int startingPrice) {
		this.startingPrice = startingPrice;
	}

	public int getMinSellPrice() {
		return minSellPrice;
	}

	public void setMinSellPrice(int minSellPrice) {
		this.minSellPrice = minSellPrice;
	}

	public String getSellerInfo() {
		return sellerInfo;
	}

	public void setSellerInfo(String sellerInfo) {
		this.sellerInfo = sellerInfo;
	}

	public int getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(int finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getBidderNo() {
		return bidderNo;
	}

	public void setBidderNo(int bidderNo) {
		this.bidderNo = bidderNo;
	}

	
}
