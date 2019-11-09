package com.example.game.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.game.bean.LadderBean;
import com.example.game.bean.SnakeBean;
import com.example.game.util.DBUtil;

public class AdminService {

	public boolean validateSnake(SnakeBean sbean) {
		boolean result = false;
		int head = sbean.getHeadCell();
		int tail = sbean.getTailCell();
		if (head < tail || head == tail || !(head >= 2 && head <= 99) || !(tail >= 1 && tail <= 70))
			result = false;
		else
			result = true;
		return result;
	}

	public boolean existingSnake(SnakeBean sbean) {
		boolean result = true;
		Connection conn = DBUtil.getDatabaseConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from SNAKECELLS");
			ResultSet rs = pstmt.executeQuery();
			int head = 0, tail = 0;
			while (rs.next()) {
				head = rs.getInt(1);
				tail = rs.getInt(2);
				if (head == sbean.getHeadCell()) {
					result = false;
					break;
				}
				if (tail == sbean.getTailCell()) {
					result = false;
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public boolean isLadder(SnakeBean sbean) {
		boolean result = true;
		Connection conn = DBUtil.getDatabaseConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from LADDERCELLS");
			ResultSet rs = pstmt.executeQuery();
			int top = 0, bottom = 0;
			while (rs.next()) {
				top = rs.getInt(1);
				bottom = rs.getInt(2);
				if (sbean.getHeadCell() == top || sbean.getHeadCell() == bottom) {
					result = false;
					break;
				}
				if (sbean.getTailCell() == top || sbean.getTailCell() == bottom) {
					result = false;
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String makeSnakeEntry(SnakeBean sbean) {
		String value = "";
		if (sbean == null)
			value = "Invalid Snake Design Request";
		else {
			boolean result1 = validateSnake(sbean);
			boolean result2 = isLadder(sbean);
			boolean result3 = existingSnake(sbean);
			if (result1 == true && result2 == true && result3 == true) {
				Connection conn = DBUtil.getDatabaseConnection();
				try {
					PreparedStatement pstmt = conn.prepareStatement("insert into SNAKECELLS values(?,?)");
					pstmt.setInt(1, sbean.getHeadCell());
					pstmt.setInt(2, sbean.getTailCell());
					int result = pstmt.executeUpdate();
					if (result > 0)
						value = "New Entry Made";

				} catch (SQLException e) {
					e.printStackTrace();
					value = "Failed To Insert Snake";
				}
			} else
				value = "Invalid Snake Design Request";
		}
		return value;
	}

	public boolean validateLadder(LadderBean lbean) {
		boolean result = true;
		int top = lbean.getTopCell();
		int bottom = lbean.getBottomCell();
		if (top < bottom || top == bottom || !(bottom >= 10 && bottom <= 70) || !(top >= 20 && top <= 99))
			result = true;
		return result;
	}

	public boolean existingLadder(LadderBean lbean) {
		boolean result = true;
		Connection conn = DBUtil.getDatabaseConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from LADDERCELLS");
			ResultSet rs = pstmt.executeQuery();
			int top = 0, bottom = 0;
			while (rs.next()) {
				top = rs.getInt(1);
				bottom = rs.getInt(2);
				if (lbean.getTopCell() == top || lbean.getBottomCell() == bottom) {
					result = false;
					break;
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean isSnakeHeadOrTail(int ladderBottom) {
		boolean result = true;
		Connection conn = DBUtil.getDatabaseConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from SNAKECELLS");
			ResultSet rs = pstmt.executeQuery();
			int head = 0, tail = 0;
			while (rs.next()) {
				head = rs.getInt(1);
				tail = rs.getInt(2);
				if (ladderBottom == head || ladderBottom == tail) {
					result = false;
					break;
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String makeLadderEntry(LadderBean lbean) {
		String value = "";
		if (lbean == null)
			value = "Invalid Ladder Design Request";
		else {
			boolean result1 = validateLadder(lbean);
			boolean result2 = isSnakeHeadOrTail(lbean.getBottomCell());
			boolean result3 = existingLadder(lbean);
			if (result1 == true && result2 == true && result3 == true) {
				Connection conn = DBUtil.getDatabaseConnection();
				try {
					PreparedStatement pstmt = conn.prepareStatement("insert into LADDERCELLS values(?,?)");
					pstmt.setInt(1, lbean.getTopCell());
					pstmt.setInt(2, lbean.getBottomCell());
					int res = pstmt.executeUpdate();
					if (res > 0)
						value = "“New Entry Made";
				} catch (SQLException e) {
					e.printStackTrace();
					value = "Failed To Insert Ladder";
				}
			} else
				value = "“Invalid Ladder Design Request";
		}
		return value;
	}

}
