package pachong.search.entity;

public class Meterial {
	private String vname;
	private String count;
	public String getVname() {
		return vname;
	}
	public void setVname(String vname) {
		this.vname = vname;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		return vname+":"+count;
	}

}