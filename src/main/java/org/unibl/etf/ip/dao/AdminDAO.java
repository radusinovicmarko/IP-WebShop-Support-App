package org.unibl.etf.ip.dao;

import org.unibl.etf.ip.model.dto.Admin;

public interface AdminDAO {
	
	Admin getByUsername(String username);

}
