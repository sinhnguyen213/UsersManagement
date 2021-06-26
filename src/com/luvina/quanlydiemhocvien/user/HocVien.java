package com.luvina.quanlydiemhocvien.user;

public class HocVien {
	// thuoc tinh

	public static final String PATH_INFO = "D:\\Luvina\\Infomation\\HV";
	public static final String PHAN_QUYEN = "HV";

	private String iD;
	private String name;
	private String dob;
	private int diemVan;
	private int diemToan;
	private int diemAnh;
	private String pass;
	private String idGiangVien;

	// phuong thuc
	public HocVien(String iD, String name, String dob, int diemVan, int diemToan, int diemAnh) {
		this.iD = iD;
		this.name = name;
		this.dob = dob;
		this.diemVan = diemVan;
		this.diemToan = diemToan;
		this.diemAnh = diemAnh;

	}

	public String accInfo() {
		return iD + "-" + pass + "-" + PHAN_QUYEN;
	}

	// xem thong tin diêm
	public void inTT() {
		System.out.println(iD + "-" + name + "-" + dob + ":" + "\nĐiểm Văn: " + diemVan + ", Xếp loại: "
				+ xepLoai(diemVan) + "\nĐiểm Toán: " + diemToan + ", Xếp loại: " + xepLoai(diemToan) + "\nĐiểm Anh: "
				+ diemAnh + ", Xếp loại: " + xepLoai(diemAnh));
		System.out.println("Điểm trung bình: " + diemTB());
	}

	// toString
	@Override
	public String toString() {
		return iD + "-" + name + "-" + dob + "-" + diemVan + "-" + diemToan + "-" + diemAnh + "-" + idGiangVien + "-"
				+ PHAN_QUYEN;
	}

	// diem trung binh
	public double diemTB() {
		return (double) (diemVan + diemToan + diemAnh) / 3;
	}

	// xep loai
	public String xepLoai(int diem) {
		String loai = "";
		if (diem >= 9) {
			loai = "A";
		} else if (diem >= 7) {
			loai = "B";
		} else if (diem >= 6) {
			loai = "C";
		} else if (diem >= 4) {
			loai = "D";
		} else {
			loai = "F";
		}
		return loai;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getIdGiangVien() {
		return idGiangVien;
	}

	public void setIdGiangVien(String idGiangVien) {
		this.idGiangVien = idGiangVien;
	}

	public String getiD() {
		return iD;
	}

	public void setiD(String iD) {
		this.iD = iD;
	}

	public String getName() {
		return name;
	}

	public String getDob() {
		return dob;
	}


	public int getDiemVan() {
		return diemVan;
	}

	public int getDiemToan() {
		return diemToan;
	}

	public int getDiemAnh() {
		return diemAnh;
	}

	public static String getPath() {
		return PATH_INFO;
	}

	public static String getPhanQuyen() {
		return PHAN_QUYEN;
	}

}
