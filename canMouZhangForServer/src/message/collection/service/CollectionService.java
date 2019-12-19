package message.collection.service;

import java.io.IOException;
import java.util.List;

import message.collection.dao.CollectionDao;
import pachong.search.entity.Show;



public class CollectionService {
	
	public int add(String uname,String vname) {
		return new CollectionDao().addCollection(uname, vname);
	}
	
	public int delete(String uname,String vname) {
		return new CollectionDao().deleteCollection(uname, vname);
	}
	
	public List<Show> find(String uname) throws IOException{
		return new CollectionDao().findCollection(uname);
	}
	
	public String check(String uname,String vname) {
		return new CollectionDao().check(uname,vname);
	}
}
