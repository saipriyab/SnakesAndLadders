package com.example.game.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.game.bean.RegisterBean;
import com.example.game.bean.SnakeBean;
import com.example.game.util.DBUtil;

public class RegistrationService {

	public boolean generatePlayerID(RegisterBean rbean) {
		boolean result = false;
		Connection conn = DBUtil.getDatabaseConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select PLAYER_ID_SEQ .nextval from dual");
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int id = rs.getInt(1);
			conn.close();
			rbean.setPlayerID("PL" + id);
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

	public boolean validatePlayerName(RegisterBean rbean) {
		boolean result = false;
		String name = rbean.getPlayerName();
		if (name.length() < 2 || name.isEmpty())
			result = false;
		else {
			name = name.toUpperCase();
			rbean.setPlayerName(name);
			result = true;
		}
		return result;
	}

	public String registerPlayer(RegisterBean rbean) {
		String value = "";
		if (rbean == null || rbean.getPlayerName() == null)
			value = "Inputs Missing";
		else {
			boolean result1 = generatePlayerID(rbean);
			boolean result2 = validatePlayerName(rbean);
			if (result1 == true && result2 == true) {
				Connection conn = DBUtil.getDatabaseConnection();
				try {
					PreparedStatement pstmt = conn.prepareStatement("insert into GAMEPLAYERS values(?,?,?,?)");
					pstmt.setString(1, rbean.getPlayerID());
					pstmt.setString(2, rbean.getPlayerName());
					pstmt.setInt(3, 0);
					pstmt.setInt(4, 1);
					int res = pstmt.executeUpdate();
					if (res > 0)
						value = "User Registered Successfully";
				} catch (SQLException e) {
					e.printStackTrace();
					value = "Registration Failed";
				}
			} else
				value = "Invalid Inputs, Registration Failed";
		}
		return value;
	}

}
