package model.pojo;

import java.time.LocalTime;

public class Record {

	private int id;
	private int itemNo;
	private String timeStamp;

	private void setCurrentTime() {
		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
		java.time.LocalTime time = java.time.LocalTime.now();
		timeStamp = formatter.format(time);
	}

	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public String getTimestamp() {
		return timeStamp;
	}

	public void setTimestamp(String timestamp) {
		this.timeStamp = timestamp;
	}

}
