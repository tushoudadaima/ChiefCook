package canMouZhang.entity;

public class Buyer {
	private String buyerId;
	private String buyerName;
	private String buyerPassword;
	private String buyerHeadImg;
	private String buyerAddr;
	
	public Buyer() {
		super();
	}
	
	public Buyer(String buyerId, String buyerName, String buyerPassword, String buyerHeadImg, String buyerAddr) {
		super();
		this.buyerId = buyerId;
		this.buyerName = buyerName;
		this.buyerPassword = buyerPassword;
		this.buyerHeadImg = buyerHeadImg;
		this.buyerAddr = buyerAddr;
	}
	
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerPassword() {
		return buyerPassword;
	}
	public void setBuyerPassword(String buyerPassword) {
		this.buyerPassword = buyerPassword;
	}
	public String getBuyerHeadImg() {
		return buyerHeadImg;
	}
	public void setBuyerHeadImg(String buyerHeadImg) {
		this.buyerHeadImg = buyerHeadImg;
	}
	public String getBuyerAddr() {
		return buyerAddr;
	}
	public void setBuyerAddr(String buyerAddr) {
		this.buyerAddr = buyerAddr;
	}
	

}
