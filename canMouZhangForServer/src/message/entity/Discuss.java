package message.entity;

public class Discuss {
	private int discussId;
	private String uname;//��������
	private String vname;//����
	private String time;//����ʱ��
	private String content;//��������
	private String buyerId;
	
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public int getDiscussId() {
		return discussId;
	}
	public void setDiscussId(int discussId) {
		this.discussId = discussId;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getVname() {
		return vname;
	}
	public void setVname(String vname) {
		this.vname = vname;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return uname + ","+time + "," + content+","+buyerId;
	}
	
	
}
