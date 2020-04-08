package com.example.testapp999;

public class Ads {
	private String photo;
	private String title;
	private String price;

	public Ads(String title, String price, String photo) {
		this.title = title;
		this.price = price;
		this.photo = photo;
	}

	public String getPhoto() {
		return photo;
	}

	public String getTitle() {
		return title;
	}

	public String getPrice() {
		return price;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
