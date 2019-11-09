package com.example.game.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.game.util.DBUtil;

public class PlayerService {

	public boolean validatePlayer(String playerID) {
		boolean result = true;
		Connection conn = DBUtil.getDatabaseConnection();
		try {
			PreparedStatement pstmt = conn.prepareCall("select * from GAMEPLAYERS where PLAYERID=?");
			pstmt.setString(1, playerID);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String id = rs.getString(1);
			if (id.equals(playerID))
				result = false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int rollDice() {
		int min = 1, max = 6;
		int range = max - min + 1;
		return (int) (Math.random() * range) + min;
	}

	public boolean isSnake(int currentCell) {
		boolean result = false;
		Connection conn = DBUtil.getDatabaseConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from SNAKECELLS WHERE SNAKEHEAD=?");
			pstmt.setInt(1, currentCell);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int head = rs.getInt(1);
			if (head == currentCell)
				result = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean isLadder(int currentCell) {
		boolean result = false;

		Connection conn = DBUtil.getDatabaseConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from LADDERCELLS where LADDERBOTTOM=?");
			pstmt.setInt(1, currentCell);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int head = rs.getInt(2);
			if (head == currentCell)
				result = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int getSnakeTail(int currentCell) {
		int tail = 0;
		Connection conn = DBUtil.getDatabaseConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from SNAKECELLS where SNAKEHEAD=?");
			pstmt.setInt(1, currentCell);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			tail = rs.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tail;
	}

	public int getLadderTop(int currentCell) {
		int top = 0;
		Connection conn = DBUtil.getDatabaseConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from LADDERCELLS where LADDERBOTTOM=?");
			pstmt.setInt(1, currentCell);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			top = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return top;
	}

	public void playGame(String playerOneID, String playerTwoID) {
		boolean result1 = validatePlayer(playerOneID);
		boolean result2 = validatePlayer(playerTwoID);
		if (result1 != true || result2 != true)
			System.out.println("Player Not Registered");
		int pos1 = 0, pos2 = 0, flag1 = 0, flag2 = 0;
		int num = 0;
		while (pos1 == 100 || pos2 == 100) {
			flag1 = 0;
			flag2 = 0;
			num = rollDice();
			System.out.println(playerOneID + "rolls dice and obtained" + pos1);
			System.out.print(playerOneID + "moves from " + pos1 + "to the");
			pos1 = pos1 + num;
			System.out.print(pos1);
			System.out.println();
			if (isSnake(pos1) && flag1 == 0) {
				int tail = getSnakeTail(pos1);
				pos1 = pos1 - tail;
				if (pos1 <= 100) {
					System.out.println(playerOneID + "hits snake and reached the cell" + pos1);
					flag1++;
				} else {
					System.out.println(playerOneID + "Invalid Movement");
				}
			}
			if (isLadder(pos1) && flag1 == 0) {
				int top = getLadderTop(pos1);
				pos1 = pos1 + top;
				if (pos1 <= 100) {
					System.out.println(playerOneID + "hits ladder and reached the cell " + pos1);
					flag1++;
				} else {
					System.out.println(playerOneID + "Invalid Movement");
				}
			}
			if (pos1 == 100) {
				System.out.println(playerOneID + "wins the game");
				break;
			} else {
				num = rollDice();
				System.out.println(playerTwoID + "rolls dice and obtained" + pos2);
				System.out.print(playerTwoID + "moves from " + pos2 + "to the");
				pos2 = pos2 + num;
				System.out.print(pos2);
				System.out.println();
				if (isSnake(pos2) && flag2 == 0) {
					int tail = getSnakeTail(pos2);
					pos2 = pos2 - tail;
					if (pos2 <= 100) {
						System.out.println(playerTwoID + "hits snake and reached the cell" + pos2);
						flag2++;
					} else {
						System.out.println(playerTwoID + "Invalid Movement");
					}
				}
				if (isLadder(pos2) && flag2 == 0) {
					int top = getLadderTop(pos2);
					pos2 = pos2 + top;
					if (pos2 <= 100) {
						System.out.println(playerTwoID + "hits ladder and reached the cell " + pos2);
						flag2++;
					} else {
						System.out.println(playerTwoID + "Invalid Movement");
					}
					if (pos2 == 100) {
						System.out.println(playerTwoID + "wins the game");
						break;
					}
				}
			}

		}
	}
}
