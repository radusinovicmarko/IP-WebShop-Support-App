package org.unibl.etf.ip.model.beans;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.unibl.etf.ip.model.dao.mysql.MySQLAdminDAO;
import org.unibl.etf.ip.model.dto.Admin;

public class AdminBean {
	private boolean loggedIn = false;

	public AdminBean() {
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public boolean login(String username, String password) {
		Admin admin = new MySQLAdminDAO().getByUsername(username);
		if (admin == null) {
			loggedIn = false;
			return false;
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
		if (encoder.matches(password, admin.getPassword())) {
			loggedIn = true;
			return true;
		}
		else {
			loggedIn = false;
			return false;
		}
	}
	
	public void logout() {
		loggedIn = false;
	}
}
