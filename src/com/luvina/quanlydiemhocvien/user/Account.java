package com.luvina.quanlydiemhocvien.user;

public class Account {
	private String id;
	private String pass;
	private String phanQuyen;

	public Account(String id, String pass, String phanQuyen) {
		this.id = id;
		this.pass = pass;
		this.phanQuyen = phanQuyen;
	}

	public String toString() {
		return id + "-" + pass + "-" + phanQuyen;
	}

	public String getId() {
		return id;
	}

	public String getPass() {
		return pass;
	}

	public String getPhanQuyen() {
		return phanQuyen;
	}

}
