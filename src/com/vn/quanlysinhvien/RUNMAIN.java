/**
 * 
 */
package com.vn.quanlysinhvien;

import java.util.Scanner;

/**
 * @author Admin
 *
 */
public class RUNMAIN {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		StudentManager ChucNang = new StudentManager();
		Scanner src = new Scanner(System.in);

		do {
			ChucNang.hienThiMenu();
			System.out.print("Mời bạn chọn chức năng : ");
			String choise = src.nextLine();
			switch (choise) {
			case "1": {
//				Chức năng 1 : Hiển Thị Thông Tin Toàn Bộ Sinh Viên
				ChucNang.hienThiThongTinSV();
				break;
			}
			case "2": {
//				Chức năng 2 : Nhập thông tin Sinh Viên.
				ChucNang.chucNang2();
				break;
			}
			case "3": {
//				Chức năng 3 : Chỉnh sửa thông tin 1 Sinh Viên theo MSV.
				ChucNang.chucNang3();
				break;
			}
			case "4": {
//				Chức năng 4 : Tìm và hiển thị thông tin Sinh Viên theo MSV.
				ChucNang.chucNang4();
				break;
			}
			case "5": {
//				Chức năng 5 : Tìm và hiển thị thông tin Sinh Viên theo Tên Sinh VIên ( Tương tự chức năng search ) .
				ChucNang.chucNang5();
				break;
			}
			case "6": {
//				Chức năng 6 : Hiển thị thông tin các sinh viên Giởi theo thứ tự giảm dần điểm trung bình. ( DTB >= 8.0)
				ChucNang.chucNang6();
				break;
			}
			case "7": {
//				Chức năng 7 : Hiển thị thông tin các sinh viên Khá theo thứ tự giảm dần điểm trung bình.( 8.0 > DTB >= 6.5)
				ChucNang.chucNang7();
				break;
			}
			case "8": {
//				Chức năng 8 : Hiển thị thông tin các sinh viên Trung Bình theo thứ tự giảm dần điểm trung bình.(6.5 > DTB >= 5.0)
				ChucNang.chucNang8();
				break;
			}
			case "9": {
//				Chức năng 9 : Hiển thị thông tin các sinh viên Yếu theo thứ tự giảm dần điểm trung bình.( DTB < 5.0)
				ChucNang.chucnang9();
				break;
			}
			case "10": {
//				Chức năng 10 : Xóa thông tin SV theo MSV.
				ChucNang.chucNang10();
				break;
			}
			case "11": {
//				Chức năng 11 : Hiển Thị Thông Tin Toàn Bộ Sinh Viên theo thứ tự Anpha-B của FristName.
				ChucNang.chucNang11();
				break;
			}
			case "12": {
//				Chức năng 12 : Kết thúc chương trình ( Stop Ứng Dụng).
				System.out.println("Chuc nang 12");
				System.out.println("Kết thúc chương trình.");
				System.exit(0);
			}
			default:
				System.err.println("Bạn đã nhập sai");
				System.out.println("Mời bạn nhập từ 1-12 :");
			}

		} while (true);

	}

}
