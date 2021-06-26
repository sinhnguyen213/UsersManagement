package com.luvina.quanlydiemhocvien.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.luvina.quanlydiemhocvien.user.Account;
import com.luvina.quanlydiemhocvien.user.GiangVien;
import com.luvina.quanlydiemhocvien.user.HocVien;

public class Main {
	public static final String PATH = "D:\\Luvina\\account.txt";
	public static final String PATH_HV = "D:\\Luvina\\Infomation\\HV";
	public static final String PATH_GV = "D:\\Luvina\\Infomation\\GV";

	public static void main(String[] args) {
		createPath();
		Scanner sc = new Scanner(System.in);
		GiangVien gv = null;
		HocVien hv = null;
		ArrayList<HocVien> listHV = null;
		ArrayList<GiangVien> listGV = null;
		try {
			boolean isContinue = true;
			while (isContinue) {
				System.out.println("1. Đăng ký" + "\n2. Đăng nhập" + "\n0. Quit");

				String text = readFile();
				int choice = Integer.parseInt(sc.nextLine());
				if (choice == 1) {
					System.out.println("Nhập tên đăng nhập: ");
					String name = sc.nextLine();
					if (text.contains(name)) {
						System.out.println("Tên đăng nhập đã tồn tại, vui lòng thử lại");
						continue;
					}
					System.out.println("Nhập mật khẩu đăng ký:");
					String pass = sc.nextLine();

					GiangVien g = new GiangVien(name, pass);
					writeFile(PATH, g.accInfo() + ",");
					System.out.println("Đăng ký thành công");
					continue;
				} else if (choice == 2) {
					ArrayList<Account> list = getAccount();
					System.out.println("Nhập tên đăng nhập: ");
					String name = sc.nextLine();

					System.out.println("Nhập mật khẩu đăng nhập:");
					String pass = sc.nextLine();
					boolean foundIt = false;
					for (Account acc : list) {
						if (acc.getId().equals(name) && acc.getPass().equals(pass)) {
							System.out.println("Đăng nhập thành công");
							isContinue = false;
							foundIt = true;
							listHV = getListHocVien();
							listGV = getListGiangVien();
							if (acc.getPhanQuyen().equals("HV")) {
								for (HocVien h : listHV) {
									if (h.getiD().equals(name)) {
										hv = h;
									}
								}
							} else {
								for (GiangVien g : listGV) {
									if (g.getId().equals(name)) {
										gv = new GiangVien(g.getId(), g.getPass());
									}
								}
							}
							break;
						}
					}
					if (!foundIt) {
						System.out.println("Tên đăng nhập hoặc mật khẩu không đúng");
						continue;
					}
				} else if (choice == 0) {
					return;
				} else {
					System.out.println("Nhập không đúng, vui lòng thử lại");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (gv != null) {
			boolean isCon = true;
			while (isCon) {
				System.out.println("1. Tạo tài khoản cho học viên" + "\n2. Xem thông tin tất cả học viên"
						+ "\n3. Xóa học viên" + "\n4. Exit");
				int choice = Integer.parseInt(sc.nextLine());

				switch (choice) {
				case 1:
					try {
						System.out.println("Nhập id học viên");
						String id = sc.nextLine();
						System.out.println("Nhập pass học viên");
						String pass = sc.nextLine();

						System.out.println("Nhập tên học viên");
						String name = sc.nextLine();
						System.out.println("Nhập ngày sinh");
						String dob = sc.nextLine();
						System.out.println("Nhập điểm Văn");
						int diemVan = Integer.parseInt(sc.nextLine());
						System.out.println("Nhập điểm Toán");
						int diemToan = Integer.parseInt(sc.nextLine());
						System.out.println("Nhập điểm Anh");
						int diemAnh = Integer.parseInt(sc.nextLine());
						gv.taoTaiKhoan(id, pass, name, dob, diemVan, diemToan, diemAnh);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case 2:
					gv.inTTHocVien();
					break;
				case 3:
					System.out.println("Nhập ID học viên cần xóa");
					String id = sc.nextLine();
					gv.xoaHocVien(id);
					break;
				case 4:
					isCon = false;
					break;
				default:
					System.out.println("Nhập không hợp lệ");
					continue;
				}
			}
		} else if (hv != null) {
			hv.inTT();
		}
		sc.close();

	}

	// get list Giang vien
	public static ArrayList<GiangVien> getListGiangVien() {
		ArrayList<GiangVien> list = new ArrayList<>();
		try {
			File file = new File(PATH);

			String text = readFile();
			String[] obj = text.split(",");
			for (int i = 0; i < obj.length; i++) {
				String[] info = obj[i].split("-");

				if (obj[i] != null && !obj[i].isEmpty() && info[2].equals("GV")) {
					GiangVien g = new GiangVien(info[0], info[1]);
					list.add(g);

				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return list;
	}

	// get list hoc vien
	public static ArrayList<HocVien> getListHocVien() {
		ArrayList<HocVien> list = new ArrayList<>();
		try {
			File file = new File(PATH_HV);

			File[] files = file.listFiles();
			String text = "";
			for (int i = 0; i < files.length; i++) {
				text = readFile(files[i]);
				String[] info = text.split("-");

				int diemToan = Integer.parseInt(info[3]);
				int diemVan = Integer.parseInt(info[4]);
				int diemAnh = Integer.parseInt(info[5]);
				HocVien hv = new HocVien(info[0], info[1], info[2], diemToan, diemVan, diemAnh);
				hv.setIdGiangVien(info[6]);
				list.add(hv);
			}
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return list;
	}

	// readFile 2
	public static String readFile(File file) {
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

	// readFile
	public static String readFile() {
		String text = "";
		try {
			File file = new File(PATH);
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

	// get list account
	public static ArrayList<Account> getAccount() {
		ArrayList<Account> list = new ArrayList<>();
		try {
			String text = readFile();
			String[] accounts = text.split(",");
			for (int i = 0; i < accounts.length; i++) {
				String[] attributes = accounts[i].split("-");
				Account acc = new Account(attributes[0], attributes[1], attributes[2]);
				list.add(acc);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}

		return list;
	}

	// WriteFile
	public static void writeFile(String PATH, String data) {
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
			FileOutputStream out = new FileOutputStream(file, true);
			byte[] dt = data.getBytes();
			out.write(dt);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// create path
	public static void createPath() {
		File file1 = new File(PATH);
		File file2 = new File(PATH_HV);
		File parentFile1 = file1.getParentFile();
		try {
			if (!file1.exists()) {
				parentFile1.mkdirs();
				boolean result1 = file1.createNewFile();
				if (result1) {
					System.out.println("Đã tạo PATH FILE Account thành công");
				} else {
					System.out.println("Không thể tạo PATH FILE Account");
				}
			}
			if (!file2.exists()) {
				boolean result2 = file2.mkdirs();
				if (result2) {
					System.out.println("Đã tạo PATH folder HV thành công");
				} else {
					System.out.println("Không thể tạo PATH folder HV");
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
