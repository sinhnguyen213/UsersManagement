package com.luvina.quanlydiemhocvien.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GiangVien {
	// thuoc tinh
	public static final String PATH_ACC = "D:\\Luvina\\account.txt";
	public static final String PATH_INFO = "D:\\Luvina\\Infomation\\HV";
	public static final String PHAN_QUYEN = "GV";

	private ArrayList<HocVien> listHocVien;
	private File file;

	private String id;
	private String pass;

	// phuong thuc
	public GiangVien() {
		listHocVien = new ArrayList<>();
		listHocVien = getListHocVien();
		file = new File(PATH_INFO);
	}

	public GiangVien(String id, String pass) {
		this.id = id;
		this.pass = pass;
		listHocVien = new ArrayList<>();
		listHocVien = getListHocVien();
		file = new File(PATH_INFO);

	}

	public String accInfo() {
		return id + "-" + pass + "-" + PHAN_QUYEN;
	}

	// tao tai khoan cho hoc vien
	public void taoTaiKhoan(String iD, String pass, String name, String dob, int diemVan, int diemToan, int diemAnh) {
		for (HocVien hv : listHocVien) {
			if (hv.getiD().equals(iD)) {
				System.out.println("ID đã tồn tại, vui lòng thử lại với ID khác");
				return;
			}
		}
		if (diemVan < 0 || diemVan > 10 || diemToan < 0 || diemToan > 10 || diemAnh < 0 || diemAnh > 10) {
			System.out.println("Vui lòng kiểm tra và nhập lại điểm từ 0 - 10");
		}
		HocVien hv = new HocVien(iD, name, dob, diemVan, diemToan, diemAnh);
		hv.setPass(pass);
		hv.setIdGiangVien(this.id);
		listHocVien.add(hv);
		String str = hv.getPath() + "\\" + hv.getiD() + ".txt";
		writeFile(str, hv.toString(),true);
		writeFile(PATH_ACC, hv.accInfo()+",",true);
	}

	// xem danh sach hoc vien
	public void inTTHocVien() {
		for (HocVien hv : listHocVien) {
			System.out.println(hv.toString());
		}
	}

	// xoa học viên
	public void xoaHocVien(String iD) {
		File file1 = new File(PATH_ACC);
		String str = readFile(file1);
		if(!str.contains(iD)) {
			System.out.println("Không tồn tại ID cần xóa");
			return;
		}
		int start = str.indexOf(iD);
		int end = str.indexOf("HV", start)+3;
		
		StringBuilder s = new StringBuilder(str);
		s.delete(start, end);
		try {
			String path = PATH_INFO + "\\" + iD + ".txt";
			File file = new File(path);
			for (int i = 0; i < listHocVien.size(); i++) {
				HocVien hv = listHocVien.get(i);
				if (hv.getiD().equals(iD)) {
					listHocVien.remove(i);
					
					if (file.exists()) {
						boolean result = file.delete();
						if (result) {
							writeFile(PATH_ACC, s.toString(),false);
							System.out.println("Đã xóa thông tin học viên");
						} else {
							System.out.println("Không thể xóa thông tin học viên");
						}
					}
					return;
				}
			}
			System.out.println("Không tìm thấy ID cần xóa trong danh sách");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// readFile
	public String readFile() {
		String text = "";
		try {
			File file = new File(PATH_INFO);
			if (!file.exists()) {

				System.out.println("File không tồn tại, kiểm tra lại đường link");

			} else {
				FileInputStream in = new FileInputStream(file);
				byte[] dt = in.readAllBytes();
				text = new String(dt);
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}

	// WriteFile
	public void writeFile(String PATH, String data, boolean isAppend) {
		try {
			File file = new File(PATH);
			if (!file.exists()) {
				boolean result = file.createNewFile();
				if (result) {
					System.out.println("File đã tạo thành công");
				} else {
					System.out.println("Tạo file thất bại");
					return;
				}
			}
			FileOutputStream out = new FileOutputStream(file, isAppend);
			byte[] dt = data.getBytes();
			out.write(dt);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// get list hoc vien
	public ArrayList<HocVien> getListHocVien() {
		ArrayList<HocVien> list = new ArrayList<>();
		try {
			File file = new File(PATH_INFO);

			File[] files = file.listFiles();
			String text = "";
			for (int i = 0; i < files.length; i++) {
				text = readFile(files[i]);
				String[] info = text.split("-");

				int diemVan = Integer.parseInt(info[3]);
				int diemToan = Integer.parseInt(info[4]);
				int diemAnh = Integer.parseInt(info[5]);
				HocVien hv = new HocVien(info[0], info[1], info[2], diemVan, diemToan, diemAnh);
				hv.setIdGiangVien(info[6]);
				if (hv.getIdGiangVien().equals(this.id)) {
					list.add(hv);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return list;
	}

	// readFile 2
	public String readFile(File file) {
		String text = "";
		try {
			if (!file.exists()) {

				System.out.println("File không tồn tại, kiểm tra lại đường link");

			} else {
				FileInputStream in = new FileInputStream(file);
				byte[] dt = in.readAllBytes();
				text = new String(dt);
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}

	public static String getPathInfo() {
		return PATH_INFO;
	}

	public static String getPhanQuyen() {
		return PHAN_QUYEN;
	}

	public String getId() {
		return id;
	}

	public String getPass() {
		return pass;
	}
	
	

}
