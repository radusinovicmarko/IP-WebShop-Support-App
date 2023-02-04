package org.unibl.etf.ip.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.ip.dao.MessageDAO;
import org.unibl.etf.ip.model.dto.Message;

public class MySQLMessageDAO implements MessageDAO {
	
	private static final String GET_ALL = "select m.id, m.title, m.content, m.messageRead, u.email from message m inner join user u on m.user_id=u.id";

	private static final String GET_ALL_BY_ID = "select m.id, m.title, m.content, m.messageRead, u.email from message m "
			+ "inner join user u on m.user_id=u.id where m.id=?";
	
	private static final String GET_ALL_BY_CONTENT = "select m.id, m.title, m.content, m.messageRead, u.email from message m "
			+ "inner join user u on m.user_id=u.id where m.content like ?";
	
	private static final String GET_ALL_BY_STATUS = "select m.id, m.title, m.content, m.messageRead, u.email from message m "
			+ "inner join user u on m.user_id=u.id where m.messageRead=?";
	
	private static final String UPDATE = "update message m set m.messageRead=1 where m.id=?";
	
	@Override
	public List<Message> getAll() {
		Connection connection = null;
		Statement s = null;
		ResultSet rs = null;
		List<Message> list = new ArrayList<>();
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			s = connection.createStatement();
			rs = s.executeQuery(GET_ALL);
			while (rs.next()) 
				list.add(new Message(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getString(5)));
			s.close();
		} catch (SQLException exp) {
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
		return list;
	}

	@Override
	public List<Message> getAllByStatus(boolean read) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Message> list = new ArrayList<>();
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			ps = connection.prepareStatement(GET_ALL_BY_STATUS);
			ps.setBoolean(1, read);
			rs = ps.executeQuery();
			while (rs.next()) 
				list.add(new Message(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getString(5)));
			ps.close();
		} catch (SQLException exp) {
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
		return list;
	}

	@Override
	public boolean update(Integer id, Message message) {
		Connection connection = null;
		PreparedStatement ps = null;
		boolean success = false;
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			ps = connection.prepareStatement(UPDATE);
			ps.setInt(1, id);
			ps.executeUpdate();
			if (ps.getUpdateCount() == 1)
				success = true;
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
		return success;
	}

	@Override
	public Message getById(Integer id) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Message message = null;
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			ps = connection.prepareStatement(GET_ALL_BY_ID);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) 
				message = new Message(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getString(5));
			ps.close();
		} catch (SQLException exp) {
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
		return message;
	}

	@Override
	public List<Message> getAllByContent(String content) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Message> list = new ArrayList<>();
		try {
			connection = ConnectionPool.getConnectionPool().checkOut();
			ps = connection.prepareStatement(GET_ALL_BY_CONTENT);
			ps.setString(1, "%" + content + "%");
			rs = ps.executeQuery();
			while (rs.next()) 
				list.add(new Message(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getString(5)));
			ps.close();
		} catch (SQLException exp) {
		} finally {
			ConnectionPool.getConnectionPool().checkIn(connection);
		}
		return list;
	}

}
