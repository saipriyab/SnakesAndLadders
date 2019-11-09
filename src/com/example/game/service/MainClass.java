package com.example.game.service;

import com.example.game.bean.LadderBean;
import com.example.game.bean.RegisterBean;
import com.example.game.bean.SnakeBean;

public class MainClass {

	public static void main(String[] args) {

		RegisterBean rbean = new RegisterBean();
		rbean.setPlayerName("Hari");
		RegistrationService robj = new RegistrationService();
		System.out.println(robj.registerPlayer(rbean));

		RegisterBean rbean1 = new RegisterBean();
		rbean1.setPlayerName(null);
		RegistrationService robj1 = new RegistrationService();
		System.out.println(robj1.registerPlayer(rbean1));

		SnakeBean sbean = new SnakeBean();
		sbean.setHeadCell(40);
		sbean.setTailCell(20);
		AdminService aObj = new AdminService();
		System.out.println(aObj.makeSnakeEntry(sbean));

		SnakeBean sbean1 = new SnakeBean();
		sbean1.setHeadCell(10);
		sbean1.setTailCell(10);
		System.out.println(aObj.makeSnakeEntry(sbean1));

		LadderBean lbean = new LadderBean();
		lbean.setTopCell(40);
		lbean.setBottomCell(11);
		System.out.println(aObj.makeLadderEntry(lbean));

		PlayerService ps = new PlayerService();
		ps.playGame("PL1012", "PL1013");

	}
}
