package com.search.entity;

public class Methods {
	private String img;
	private String step;
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	@Override
	public String toString() {
		return img + "@" + step;
	}
	public Methods(String img, String step) {
		super();
		this.img = img;
		this.step = step;
	}
	
	

}
