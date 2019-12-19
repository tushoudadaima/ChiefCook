package message.discuss.service;

import java.util.List;

import message.discuss.dao.DiscussDao;
import message.entity.Discuss;



public class DiscussService {
	
	public int add(String uname,String vname,String time,String content,String buyerId) {
		return new DiscussDao().addDiscuss(uname, vname, time, content,buyerId);
	}
	
	public List<Discuss> find(String vname){
		return new DiscussDao().findDiscuss(vname);
	}
}
