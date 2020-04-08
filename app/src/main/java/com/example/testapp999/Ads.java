package com.example.testapp999;

public class Ads {
	public static final String id160 = "https://i.simpalsmedia.com/999.md/BoardImages/160x120/";
	private String photoRaw;
	private String title;
	private String price;

	public Ads(String title, String price, String photo) {
		this.title = title;
		this.price = price;
		this.photoRaw = photo;
	}

	public String getPhotoRaw() {
		return photoRaw;
	}

	public String getTitle() {
		return title;
	}

	public String getPrice() {
		return price;
	}

	public void setPhoto(String photo) {
		this.photoRaw = photo;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
