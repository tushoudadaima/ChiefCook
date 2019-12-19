package canMouZhang.entity;

public class Seller {
	private String sellerId;
	private String sellerPassword;
	private String sellerName;
	private String sellerHeadImg;
	private String sellerAddr;
	
	
	
	public Seller() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Seller(String sellerId, String sellerPassword, String sellerName, String sellerHeadImg, String sellerAddr) {
		super();
		this.sellerId = sellerId;
		this.sellerName = sellerName;
		this.sellerPassword = sellerPassword;
		this.sellerHeadImg = sellerHeadImg;
		this.sellerAddr = sellerAddr;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSellerPassword() {
		return sellerPassword;
	}
	public void setSellerPassword(String sellerPassword) {
		this.sellerPassword = sellerPassword;
	}
	public String getSellerHeadImg() {
		return sellerHeadImg;
	}
	public void setSellerHeadImg(String sellerHeadImg) {
		this.sellerHeadImg = sellerHeadImg;
	}
	public String getSellerAddr() {
		return sellerAddr;
	}
	public void setSellerAddr(String sellerAddr) {
		this.sellerAddr = sellerAddr;
	}
	

}
