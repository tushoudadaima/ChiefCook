package message.entity;

public class User {
	private int id;
	private String uname;
	private String password;
	private String headingImage;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return uname;
	}
	public void setName(String uname) {
		this.uname = uname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHeadingImage() {
		return headingImage;
	}
	public void setHeadingImage(String headingImage) {
		this.headingImage = headingImage;
	}
	
}
