package org.unibl.etf.ip.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.unibl.etf.ip.dao.AdminDAO;
import org.unibl.etf.ip.model.dto.Admin;

public class MySQLAdminDAO implements AdminDAO {
	
	private static final String GET_BY_USERNAME = "select * from admin a where a.username=? and a.role=?";
	
	@Override
	public Admin getByUsername(String username) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Admin admin = null;
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			ps = connection.prepareStatement(GET_BY_USERNAME);
			ps.setString(1, username);
			ps.setString(2, "SupportSystemAdmin");
			rs = ps.executeQuery();
			if (rs.next())
				admin = new Admin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
			ps.close();
		} catch (SQLException exp) {
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
		return admin;
	}

}
