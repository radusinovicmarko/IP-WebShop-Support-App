package org.unibl.etf.ip.dao;

import java.util.List;

import org.unibl.etf.ip.model.dto.Message;

public interface MessageDAO {
	
	List<Message> getAll();
	
	List<Message> getAllByStatus(boolean read);
	
	List<Message> getAllByContent(String content);
	
	Message getById(Integer id);
	
	boolean update(Integer id, Message message);

}
