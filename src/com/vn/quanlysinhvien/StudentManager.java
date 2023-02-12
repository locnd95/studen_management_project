/**
 * 
 */
package com.vn.quanlysinhvien;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author Admin
 *
 */
public class StudentManager {

	Boolean checkMenu = false;
	// Chọn YN
	String choiseYN = "";
	// Kiểm tra nhập đúng Y/N
	boolean checkYN = false;
	// Kiểm tra nhập đúng 1,2,3....vv
	boolean checkThongTinNhap = false;
	Scanner src = new Scanner(System.in);

	String HOST = "localhost";
	String POST = "3306";
	String db_NAME = "DANHSACHSV";
	String DB_URL = "jdbc:mysql://" + HOST + ":" + POST + "/" + db_NAME;
	String USER_NAME = "root";
	String PASSWORD = "root@123";
	Connection conn = null;

	public void hienThiMenu() {
		System.out.println("---------------MENU chính---------------");
		System.out.println("Chức năng 1 : Hiển Thị Thông Tin Toàn Bộ Sinh Viên.");
		System.out.println("Chức năng 2 : Nhập thông tin Sinh Viên.");
		System.out.println("Chức năng 3 : Chỉnh sửa thông tin 1 Sinh Viên theo MSV ");
		System.out.println("Chức năng 4 : Tìm và hiển thị thông tin Sinh Viên theo MSV");
		System.out.println("Chức năng 5 : Tìm và hiển thị thông tin Sinh Viên theo Tên Sinh VIên ");
		System.out
				.println("Chức năng 6 : Hiển thị thông tin các sinh viên Giởi theo thứ tự giảm dần điểm trung bình. ");
		System.out.println("Chức năng 7 : Hiển thị thông tin các sinh viên Khá theo thứ tự giảm dần điểm trung bình.");
		System.out.println(
				"Chức năng 8 : Hiển thị thông tin các sinh viên Trung Bình theo thứ tự giảm dần điểm trung bình.");
		System.out.println("Chức năng 9 : Hiển thị thông tin các sinh viên Yếu theo thứ tự giảm dần điểm trung bình.");
		System.out.println("Chức năng 10 : Xóa thông tin SV theo MSV.");
		System.out.println("Chức năng 11 : Hiển Thị Thông Tin Toàn Bộ Sinh Viên theo thứ tự Anpha-B của FristName.");
		System.out.println("Chức năng 12 : Kết thúc chương trình ( Stop Ứng Dụng).");

	}

//Chức năng 1 : Hiển Thị Thông Tin Toàn Bộ Sinh Viên

	public void hienThiThongTinSV() {

		try {
			do {
				// Tạo Kết nối
				conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
				if (conn != null) {
					System.out.println("Kết nối DataBase thành công ");
					// Tạo Statement
					Statement st = conn.createStatement();
					String hienThiDanhSachSV = "select*from JAVA24_DB.danhsach ";
					ResultSet rs = st.executeQuery(hienThiDanhSachSV);
					// Hiển thị bảng Table
					formatTable();
					int count = 0;
					if (rs.next()) {
						do {
							count++;
							System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|\n", rs.getString(1),
									rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getFloat(6),
									rs.getFloat(7), rs.getFloat(8), rs.getFloat(9));
							;
						} while (rs.next());
					}
					if (count == 0) {
						System.out
								.printf("                               Hiện tại chưa có sinh viên trong danh sách %n");
					}
					System.out.printf(
							"-----------------------------------------------------------------------------------------------------------%n");
				} else {
					System.out.println("Kết nối thất bại");
				}
			} while (kiemTraChonYN());
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	Chức năng 2 : Nhập thông tin Sinh Viên.
	public void chucNang2() {
		System.out.println("Chuc nang 2");
		try {
			do {
				String choiseYN;
				checkThongTinNhap = false;
				checkYN = false;

				// Tạo Kết nối
				conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
				if (conn != null) {
					System.out.println("Kết nối DataBase thành công ");
					// Tạo Statement
					Statement st = conn.createStatement();
					// Check MSV đã tồn tại chưa ??
					String msvNew = "";
					do {
						checkThongTinNhap = false;
						System.out.print("Mời ban tạo MSV mới : ");
						msvNew = src.nextLine().trim().toUpperCase();
						String CheckSVDaTonTaiChua = "select*from JAVA24_DB.danhsach where MSV = '" + msvNew + "'";
						ResultSet rs = st.executeQuery(CheckSVDaTonTaiChua);
						Pattern templateMSV = Pattern.compile("^MSV[0-9]{3}$");
						if (msvNew.trim().isEmpty() || msvNew.trim().equals(null)
								|| !templateMSV.matcher(msvNew).find()) {
							System.err.println("Nhập sai cú pháp ");
							checkThongTinNhap = true;
						} else if (rs.next()) {
							System.out.println("MSV đã tồn tại trong Danh sách");
							checkThongTinNhap = true;
						} else {
							System.out.println("Bạn đã tạo MSV mới là :" + msvNew);
							System.out.println("Bạn có muốn sửa MSV lại k?? ");
							// Hỏi có muốn sửa lại MSV k??
							while (true) {
								System.out.println("Chọn Y để sửa lại ,chọn N để tiếp tục");
								choiseYN = src.nextLine();
								if (!choiseYN.equalsIgnoreCase("Y") && !choiseYN.equalsIgnoreCase("N")) {
									System.out.println("Bạn đã chọn sai");
								} else if (choiseYN.equalsIgnoreCase("N")) {
									System.out.println("Bạn đã tạo MSV mới là :" + msvNew);
									break;
								} else {
									checkThongTinNhap = true;
									break;
								}
							}
						}
					} while (checkThongTinNhap);
					// Nhập Last Name
					String lastNameNew = "";
					do {
						checkThongTinNhap = false;
						System.out.println("Mời bạn nhập Last Name : ");
						lastNameNew = src.nextLine();
						Pattern template = Pattern.compile("^[a-zA-Z ]+$");
//						Pattern template = Pattern.compile("^[^0-9\\w]+$");
						if (lastNameNew.trim().isEmpty() || lastNameNew.trim().equals(null)
								|| !template.matcher(lastNameNew).find()) {
							System.out.println("Nhập sai cú pháp ");
							checkThongTinNhap = true;
						} else {
							System.out.println("Bạn tạo Last Name mới là :" + lastNameNew);
						}
					} while (checkThongTinNhap);
					// Nhập Firt Name
					String firstNameNew = "";
					do {
						checkThongTinNhap = false;
						System.out.println("Mời bạn nhập First Name : ");
						firstNameNew = src.nextLine().trim();
						Pattern template = Pattern.compile("^[a-zA-Z]+$");
//						Pattern template = Pattern.compile("^[^0-9\\w]+$");
						if (firstNameNew.trim().isEmpty() || firstNameNew.trim().equals(null)
								|| !template.matcher(firstNameNew).find()) {
							System.out.println("Nhập sai cú pháp ");
							checkThongTinNhap = true;
						} else {
							System.out.println("Bạn tạo First Name mới là :" + firstNameNew);
						}
					} while (checkThongTinNhap);
					// Chọn giới tính
					String gender = "";
					do {
						checkThongTinNhap = false;
						System.out.println("Mời bạn chọn Giới tính : ");
						System.out.println("Chọn 1 là Nam ,chọn 2 là Nữ ");
						String chonGioiTinh = src.nextLine();
						switch (chonGioiTinh) {
						case "1": {
							gender = "Nam";
							break;
						}
						case "2": {
							gender = "Nữ";
							break;
						}
						default:
							System.out.println("Bạn chọn sai cú pháp ");
							checkThongTinNhap = true;
						}
					} while (checkThongTinNhap);
					// Tạo Class
					String classNhap = "";
					do {
						checkThongTinNhap = false;
						System.out.println("Mời bạn nhập Class : ");
						classNhap = src.nextLine().trim().toUpperCase();
						if (classNhap.equalsIgnoreCase("") || classNhap.equalsIgnoreCase(null)) {
							System.out.println("Lớp sinh viên k hợp lệ");
							checkThongTinNhap = true;
						} else {
							while (true) {
								System.out.println("Bạn có muốn chỉnh sửa lớp của sinh viên");
								System.out.println("Chọn Y để sửa .Chọn N để tiếp tục ");
								String thongTinYN = src.nextLine();
								if (thongTinYN.equalsIgnoreCase("Y")) {
									checkThongTinNhap = true;
									break;
								} else if (thongTinYN.equalsIgnoreCase("N")) {
									System.out.println("Bạn đã tạo lớp của sinh viên " + classNhap);
									break;
								} else {
									System.out.println("Bạn chọn k đúng cú pháp");
								}
							}
						}
					} while (checkThongTinNhap);
					// Check thông tin nhập Math point
					Float matP = 0f;
					do {
						checkThongTinNhap = false;
						System.out.println("Mời bạn nhập diem Math: ");
						try {
							matP = Float.parseFloat(src.nextLine());
							if (matP >= 0 && matP <= 10) {

								System.out.println("Bạn đã nhập điểm Math: " + matP);
							} else {
								System.out.println("Mời bạn nhập điểm từ 0->10 ");
								checkThongTinNhap = true;
							}
						} catch (Exception e) {
							System.out.println("Điểm không hợp lệ ,mời nhập từ 0->10");
							checkThongTinNhap = true;
						}
					} while (checkThongTinNhap);
					// Kết thúc
					// Check thông tin nhập Physical point
					Float phyP = 0f;
					do {
						checkThongTinNhap = false;
						System.out.println("Mời bạn nhập diem Phy: ");
						try {
							phyP = Float.parseFloat(src.nextLine());
							if (phyP >= 0 && phyP <= 10) {
								System.out.println("Bạn đã nhập thành công điểm Physical: " + phyP);
							} else {
								System.out.println("Mời bạn nhập điểm từ 0->10 ");
								checkThongTinNhap = true;
							}
						} catch (Exception e) {
							System.out.println("Điểm không hợp lệ ,mời nhập từ 0->10");
							checkThongTinNhap = true;
						}
					} while (checkThongTinNhap);
					// Kết thúc
					// Check thông tin nhập Chemistry point
					Float cheP = 0f;
					do {
						checkThongTinNhap = false;
						System.out.println("Mời bạn nhập diem Chemistry: ");
						try {
							cheP = Float.parseFloat(src.nextLine());
							if (cheP >= 0 && cheP <= 10) {
								System.out.println("Bạn đã nhập thành công điểm Chemistry: " + cheP);
							} else {
								System.out.println("Mời bạn nhập điểm từ 0->10 ");
								checkThongTinNhap = true;
							}
						} catch (Exception e) {
							System.out.println("Điểm không hợp lệ ,mời nhập từ 0->10");
							checkThongTinNhap = true;
						}
					} while (checkThongTinNhap);
					// Tính điểm AVG dựa theo điểm mới
					float diemTB = (matP + phyP + cheP) / 3;
					Student svNew = new Student(msvNew, lastNameNew, firstNameNew, gender, classNhap, matP, phyP, cheP);
					String INSERTSV = "insert into JAVA24_DB.danhsach values (?,?,?,?,?,?,?,?,?)";
					PreparedStatement preparedStatement = conn.prepareStatement(INSERTSV);
					preparedStatement.setString(1, msvNew);
					preparedStatement.setString(2, lastNameNew);
					preparedStatement.setString(3, firstNameNew);
					preparedStatement.setString(4, gender);
					preparedStatement.setString(5, classNhap);
					preparedStatement.setFloat(6, matP);
					preparedStatement.setFloat(7, phyP);
					preparedStatement.setFloat(8, cheP);
					preparedStatement.setFloat(9, diemTB);
					int INSERTDATA = preparedStatement.executeUpdate();
					// Hiển thị bảng Table
					formatTable();
					if (INSERTDATA != 0) {
						System.out.println(svNew);
						System.out.printf(
								"-----------------------------------------------------------------------------------------------------------%n");
						System.out.println("Bạn đã thêm Sinh viên thành công ");
					} else {
						System.out.println("Thêm không thành công ");
						System.out.printf(
								"-----------------------------------------------------------------------------------------------------------%n");
					}
					// Kết thúc
					// Add thông tin sinh viên vào List thông tin sinh viên
					// Vòng lặp chọn YN để quay lại MENU or ở lại
				} else {
					System.out.println("Kết nối không thành công ");
				}
			} while (kiemTraChonYN());
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void chucNang3() {
//		Chức năng 3 : Chỉnh sửa thông tin 1 Sinh Viên theo MSV.
		System.out.println("Chuc nang 3");
		try {
			String choiseYN;
			checkThongTinNhap = false;
			checkYN = false;
			// Tạo Kết nối
			// Check MSV đã tồn tại chưa ??
			String inputMSV = "";
			String UPDATEMSV = "";
			do {
				conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
				// Tạo Statement
				if (conn != null) {
					System.out.println("Kết nối DataBase thành công ");
					Statement st = conn.createStatement();
					System.out.print("Nhập MSV bạn cần thay đổi thông tin: ");
					inputMSV = src.nextLine().trim().toUpperCase();
					String CheckSV = "select*from JAVA24_DB.danhsach where MSV = '" + inputMSV + "' ";
					String CheckDanhSachSV = "";
					ResultSet rs = st.executeQuery(CheckSV);
					checkThongTinNhap = false;
					Pattern templateMSV = Pattern.compile("^MSV[0-9]{3}$");
					if (inputMSV.trim().isEmpty() || inputMSV.trim().equals(null)
							|| !templateMSV.matcher(inputMSV).find()) {
						System.err.println("Nhập sai cú pháp ");
						checkThongTinNhap = true;
					} else if (!rs.next()) {
						System.out.println("MSV không tồn tại trong Danh sách");
						checkThongTinNhap = true;
					} else {
						String MSV = rs.getString(1);
						String lastName = rs.getString(2);
						String firtName = rs.getString(3);
						String gender = rs.getString(4);
						String classStudent = rs.getString(5);
						Float mathPoints = rs.getFloat(6);
						Float physicalPoints = rs.getFloat(7);
						Float chemistryPoints = rs.getFloat(8);
//						Student student = new Student(MSV, lastName, firtName, gender, classStudent, mathPoints,
//								physicalPoints, chemistryPoints);
						System.out.println("Thông tin của sinh viên cần thay đổi ");
						formatTable();
						System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|\n", rs.getString(1),
								rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getFloat(6),
								rs.getFloat(7), rs.getFloat(8), rs.getFloat(9));
						System.out.printf(
								"-----------------------------------------------------------------------------------------------------------%n");
						String MSVNew = MSV;
						do {
							System.out.println("Bạn có muốn thay đổi MSV k?");
							checkThongTinNhap = false;
							checkYN = false;
							System.out.println("Chọn Y để thay đổi ,Chọn N để bỏ qua ");
							choiseYN = src.nextLine();
							if (!choiseYN.equalsIgnoreCase("Y") && !choiseYN.equalsIgnoreCase("N")) {
								System.out.println("Bạn chọn k đúng ");
								checkYN = true;
							} else if (choiseYN.equalsIgnoreCase("Y")) {
								do {
									checkThongTinNhap = false;
									System.out.println("Mời bạn nhập MSV mới : ");
									MSVNew = src.nextLine().trim().toUpperCase();
									templateMSV = Pattern.compile("^MSV[0-9]{3}$");

									CheckDanhSachSV = "select*from JAVA24_DB.danhsach where MSV ='" + MSVNew + "'";
									rs = st.executeQuery(CheckDanhSachSV);
									if (MSVNew.trim().isEmpty() || MSVNew.trim().equals(null)
											|| !templateMSV.matcher(MSVNew).find()) {
										System.out.println("Nhập sai cú pháp ");
										checkThongTinNhap = true;
									} else if (rs.next()) {
										System.out.println("MSV đã tồn tại ");
										checkThongTinNhap = true;
									} else {
									}
								} while (checkThongTinNhap);
							} else {
								System.out.println("Bạn k thay đổi MSV .");
							}

						} while (checkYN);
						// Lựa chọn Thay đổi Last Name
						String lastNameNew = lastName;
						do {
							System.out.println("Bạn có muốn thay đổi Last Name k?");
							checkThongTinNhap = false;
							checkYN = false;
							System.out.println("Chọn Y để thay đổi ,Chọn N để bỏ qua ");
							choiseYN = src.nextLine();
							if (!choiseYN.equalsIgnoreCase("Y") && !choiseYN.equalsIgnoreCase("N")) {
								System.out.println("Bạn chọn k đúng ");
								checkYN = true;
							} else if (choiseYN.equalsIgnoreCase("Y")) {
								do {
									checkThongTinNhap = false;
									System.out.println("Mời bạn nhập Last Name : ");
									lastNameNew = src.nextLine().trim();
									Pattern template = Pattern.compile("^[a-zA-Z ]+$");
									if (lastNameNew.trim().isEmpty() || lastNameNew.trim().equals(null)
											|| !template.matcher(lastNameNew).find()) {
										System.out.println("Nhập sai cú pháp ");
										checkThongTinNhap = true;
									} else {

									}
								} while (checkThongTinNhap);
							} else {
								System.out.println("Bạn k thay đổi Last Name .");
							}
						} while (checkYN);

						// Lựa chọn Thay đổi Firt Name
						String firtNameNew = firtName;
						do {
							System.out.println("Bạn có muốn thay đổi Firt Name k?");
							checkThongTinNhap = false;
							checkYN = false;
							System.out.println("Chọn Y để thay đổi ,Chọn N để bỏ qua ");
							choiseYN = src.nextLine();
							if (!choiseYN.equalsIgnoreCase("Y") && !choiseYN.equalsIgnoreCase("N")) {
								System.out.println("Bạn chọn k đúng ");
								checkYN = true;
							} else if (choiseYN.equalsIgnoreCase("Y")) {
								do {
									checkThongTinNhap = false;
									System.out.println("Mời bạn nhập Firt Name : ");
									firtNameNew = src.nextLine().trim();
									Pattern template = Pattern.compile("^[a-zA-Z]+$");
									if (firtNameNew.trim().isEmpty() || firtNameNew.trim().equals(null)
											|| !template.matcher(firtNameNew).find()) {
										System.out.println("Nhập sai cú pháp ");
										checkThongTinNhap = true;
									} else {

									}
								} while (checkThongTinNhap);
							} else {
								System.out.println("Bạn k thay đổi Firt Name .");
							}
						} while (checkYN);

						// Lựa chọn Thay đổi Giới tính
						String genderNew = gender;
						System.out.println("Bạn có muốn thay đổi Giới tính k?");
						do {
							checkThongTinNhap = false;
							checkYN = false;
							System.out.println("Chọn Y để thay đổi ,Chọn N để bỏ qua ");
							choiseYN = src.nextLine();
							if (!choiseYN.equalsIgnoreCase("Y") && !choiseYN.equalsIgnoreCase("N")) {
								System.out.println("Bạn chọn k đúng ");
								checkYN = true;
							} else {
								if (choiseYN.equalsIgnoreCase("Y")) {
									do {
										checkThongTinNhap = false;
										System.out.println("Mời bạn chọn Giới tính : ");
										System.out.println("Chọn 1 là Nam ,chọn 2 là Nữ ");
										String chonGioiTinh = src.nextLine();
										switch (chonGioiTinh) {
										case "1": {

											genderNew = "Nam";
											System.out.println("Bạn đã thay đổi giới tính là Nam ");
											break;
										}
										case "2": {

											genderNew = "Nữ";
											System.out.println("Bạn đã thay đổi giới tính là Nữ ");
											break;
										}
										default:
											System.out.println("Bạn chọn sai cú pháp ");
											checkThongTinNhap = true;
										}
									} while (checkThongTinNhap);
								} else {
								}
							}
						} while (checkYN);

						// Lựa chọn Thay đổi Class Student
						// Check thông tin nhập Class
						String classStudentNew = classStudent;
						do {
							System.out.println("Bạn có muốn thay đổi Class k ??? ");
							checkThongTinNhap = false;
							checkYN = false;
							System.out.println("Chọn Y để thay đổi ,Chọn N để bỏ qua ");
							choiseYN = src.nextLine();
							if (!choiseYN.equalsIgnoreCase("Y") && !choiseYN.equalsIgnoreCase("N")) {
								System.out.println("Bạn chọn k đúng ");
								checkYN = true;
							} else if (choiseYN.equalsIgnoreCase("Y")) {
								do {
									checkThongTinNhap = false;
									System.out.println("Mời bạn nhập Class : ");
									classStudentNew = src.nextLine().trim().toUpperCase();
									Pattern template = Pattern.compile("^[a-zA-Z0-9]+$");
									if (classStudentNew.trim().isEmpty() || classStudentNew.trim().equals(null)
											|| !template.matcher(classStudentNew).find()) {
										System.out.println("Nhập sai cú pháp ");
										checkThongTinNhap = true;
									}
//									else {
//									}
								} while (checkThongTinNhap);
							} else {
								System.out.println("Bạn k thay đổi Class .");
							}
						} while (checkYN);

						// Lựa chọn Thay đổi điểm Math
						// Check thông tin nhập Math point
						Float matP = mathPoints;
						do {
							checkThongTinNhap = false;
							checkYN = false;
							System.out.println("Bạn có muốn thay đổi điểm Math k ??? ");
							System.out.println("Chọn Y để thay đổi ,Chọn N để bỏ qua ");
							choiseYN = src.nextLine();
							if (!choiseYN.equalsIgnoreCase("Y") && !choiseYN.equalsIgnoreCase("N")) {
								System.out.println("Bạn chọn k đúng ");
								checkYN = true;
							} else {
								if (choiseYN.equalsIgnoreCase("Y")) {
									do {
										checkThongTinNhap = false;
										System.out.println("Mời bạn nhập diem Math: ");
										try {
											matP = Float.parseFloat(src.nextLine());
											if (matP >= 0 && matP <= 10) {
												checkThongTinNhap = false;
											} else {
												System.out.println("Mời bạn nhập điểm từ 0->10 ");
												checkThongTinNhap = true;
											}
										} catch (Exception e) {
											System.out.println("Điểm không hợp lệ ,mời nhập từ 0->10");
											checkThongTinNhap = true;
										}
									} while (checkThongTinNhap);
								}
							}
						} while (checkYN);

						// Lựa chọn Thay đổi điểm Physical
						System.out.println("Bạn có muốn thay đổi điểm Physical k ??? ");
						// Check thông tin nhập Physical point
						Float phyP = physicalPoints;
						do {
							checkThongTinNhap = false;
							checkYN = false;
							System.out.println("Chọn Y để thay đổi ,Chọn N để bỏ qua ");
							choiseYN = src.nextLine();
							if (!choiseYN.equalsIgnoreCase("Y") && !choiseYN.equalsIgnoreCase("N")) {
								System.out.println("Bạn chọn k đúng ");
								checkYN = true;
							} else if (choiseYN.equalsIgnoreCase("Y")) {
								do {
									checkThongTinNhap = false;
									System.out.println("Mời bạn nhập diem Physical: ");
									try {
										phyP = Float.parseFloat(src.nextLine());
										if (phyP >= 0 && phyP <= 10) {
											checkThongTinNhap = false;
										} else {
											System.out.println("Mời bạn nhập điểm từ 0->10 ");
											checkThongTinNhap = true;
										}
									} catch (Exception e) {
										System.out.println("Điểm không hợp lệ ,mời nhập từ 0->10");
										checkThongTinNhap = true;
									}
								} while (checkThongTinNhap);
							}
						} while (checkYN);

						// Lựa chọn Thay đổi điểm chemistry
						System.out.println("Bạn có muốn thay đổi điểm Chemistry k ??? ");
						// Check thông tin nhập chemistry Point
						Float cheP = chemistryPoints;
						do {
							checkThongTinNhap = false;
							checkYN = false;
							System.out.println("Chọn Y để thay đổi ,Chọn N để bỏ qua ");
							choiseYN = src.nextLine();
							if (!choiseYN.equalsIgnoreCase("Y") && !choiseYN.equalsIgnoreCase("N")) {
								System.out.println("Bạn chọn k đúng ");
								checkYN = true;
							} else if (choiseYN.equalsIgnoreCase("Y")) {
								do {
									checkThongTinNhap = false;
									System.out.println("Mời bạn nhập diem chemistry: ");
									try {
										cheP = Float.parseFloat(src.nextLine());
										if (cheP >= 0 && cheP <= 10) {
											checkThongTinNhap = false;
										} else {
											System.out.println("Mời bạn nhập điểm từ 0->10 ");
											checkThongTinNhap = true;
										}
									} catch (Exception e) {
										System.out.println("Điểm không hợp lệ ,mời nhập từ 0->10");
										checkThongTinNhap = true;
									}
								} while (checkThongTinNhap);
							}
						} while (checkYN);

						Float diemTBNew = (matP + phyP + cheP) / 3;

						String UPDATE_SV = "Update JAVA24_DB.danhsach set MSV = ? ,lastName = ? , firtName = ? , gender = ? ,classStudent = ? , "
								+ "mathPoints = ? , physicalPoints =  ? , chemistryPoints =  ? ,aVG =? where MSV = ? ";
						PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_SV);
						preparedStatement.setString(1, MSVNew);
						preparedStatement.setString(2, lastNameNew);
						preparedStatement.setString(3, firtNameNew);
						preparedStatement.setString(4, genderNew);
						preparedStatement.setString(5, classStudentNew);
						preparedStatement.setFloat(6, matP);
						preparedStatement.setFloat(7, phyP);
						preparedStatement.setFloat(8, cheP);
						preparedStatement.setFloat(9, diemTBNew);
						preparedStatement.setString(10, inputMSV);

						int UPDATE_DATA = preparedStatement.executeUpdate();
						if (UPDATE_DATA > 0) {
							System.out.println("Bạn đã Update thành công ");
							formatTable();
							System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|\n", MSVNew,
									lastNameNew, firtNameNew, genderNew, classStudentNew, matP, phyP, cheP, diemTBNew);

						} else {
							System.out.println("Update không thành công ");
						}
						System.out.printf(
								"-----------------------------------------------------------------------------------------------------------%n");
					}
				} else {
					System.out.println("Kết nối thất bại ");
				}
			} while (kiemTraChonYN());
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	Chức năng 4 : Tìm và hiển thị thông tin Sinh Viên theo MSV.
	public void chucNang4() {
		System.out.println("Chuc nang 4");
		// Dùng MySQL
		try {
			do {
				// Tạo Kết nối
				conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
				if (conn != null) {
					System.out.println("Kết nối DataBase thành công ");
					// Tạo Statement
					Statement st = conn.createStatement();
					System.out.println("Nhập MSV bạn cần tìm: ");
					String inPutMSV = src.nextLine().toUpperCase().trim();
					String SELECT = "select*from JAVA24_DB.danhsach where MSV ='" + inPutMSV + "'";
					ResultSet rs = st.executeQuery(SELECT);
					formatTable();
					if (rs.next()) {
						System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|\n", rs.getString(1),
								rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getFloat(6),
								rs.getFloat(7), rs.getFloat(8), rs.getFloat(9));
					} else {
						System.out.printf("                                       Mã sinh viên không tồn tại %n");
					}
					System.out.printf(
							"-----------------------------------------------------------------------------------------------------------%n");
				} else {
					System.out.println("Kết nối thất bại");
				}
			} while (kiemTraChonYN());
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	Chức năng 5 : Tìm và hiển thị thông tin Sinh Viên theo Tên Sinh VIên ( Tương tự chức năng search ) .
	public void chucNang5() {
		// Dùng MySQL
		try {
			do {
				// Tạo Kết nối
				conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
				if (conn != null) {
					System.out.println("Kết nối DataBase thành công ");
					// Tạo Statement
					Statement st = conn.createStatement();
					System.out.println("Mời bạn nhập tên sinh viên cần tìm kiếm");
					String tenSVNhap = src.nextLine().trim();
					String SELECT = "select*from JAVA24_DB.danhsach where lastName like '%" + tenSVNhap
							+ "%' or firtName like '%" + tenSVNhap + "%'";
					ResultSet rs = st.executeQuery(SELECT);
					// Hiển thị bảng Table
					formatTable();
					int count = 0;
					if (rs.next()) {
						do {
							count++;
							System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|\n", rs.getString(1),
									rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getFloat(6),
									rs.getFloat(7), rs.getFloat(8), rs.getFloat(9));
						} while (rs.next());
					}
					if (count == 0) {
						System.out.printf(
								"                                       Không có tên sinh viên trong danh sách %n");
					}
					System.out.printf(
							"-----------------------------------------------------------------------------------------------------------%n");
				} else {
					System.out.println("Kết nối thất bại");
				}
			} while (kiemTraChonYN());
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

////	Chức năng 6 : Hiển thị thông tin các sinh viên Giởi theo thứ tự giảm dần điểm trung bình. ( DTB >= 8.0)
//	// Sắp xếp danh sách Sinh viên
	public void chucNang6() {
		// Dùng MySQL
		try {
			do {
				// Tạo Kết nối
				conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
				if (conn != null) {
					System.out.println("Kết nối DataBase thành công ");
					// Tạo Statement
					Statement st = conn.createStatement();
					String SAPXEPSVGIOI = "select*from JAVA24_DB.danhsach where aVG >=8 order by aVG desc";
					ResultSet rs = st.executeQuery(SAPXEPSVGIOI);
					System.out.println(
							"Danh sách sinh viên đạt điểm Trung Bình Giỏi theo thứ tự giảm dần diểm trung bình là : ");
					// Hiển thị bảng Table
					formatTable();
					if (rs.next()) {
						do {
							System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|\n", rs.getString(1),
									rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getFloat(6),
									rs.getFloat(7), rs.getFloat(8), rs.getFloat(9));
						} while (rs.next());
					} else {
						System.err.printf(
								"                                     Không có sinh viên giỏi trong danh sách %n");
					}
					System.out.printf(
							"-----------------------------------------------------------------------------------------------------------%n");
				} else {
					System.out.println("Kết nối thất bại ");
				}
			} while (kiemTraChonYN());
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// chức năng 7 :Hiển thị thông tin các sinh viên Khá theo thứ tự giảm dần điểm
	// trung bình.( 8.0 > DTB >= 6.5)
	public void chucNang7() {
		// Dùng MySQL
		try {
			do {
				// Tạo Kết nối
				conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
				if (conn != null) {
					System.out.println("Kết nối DataBase thành công ");
					// Tạo Statement
					Statement st = conn.createStatement();
					String SAPXEPSVKHA = "select*from JAVA24_DB.danhsach where aVG <8  and aVG >=6.5 order by aVG desc";
					ResultSet rs = st.executeQuery(SAPXEPSVKHA);
					System.out.println("Danh sách sinh viên đạt điểm Khá theo thứ tự giảm dần diểm trung bình là : ");
					// Hiển thị bảng Table
					formatTable();
					if (rs.next()) {
						do {
							System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|\n", rs.getString(1),
									rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getFloat(6),
									rs.getFloat(7), rs.getFloat(8), rs.getFloat(9));
						} while (rs.next());
					} else {
						System.out.printf(
								"                                        Không có sinh viên Khá trong danh sách %n");
					}
					System.out.printf(
							"-----------------------------------------------------------------------------------------------------------%n");
				} else {
					System.out.println("Kết nối thất bại");
				}
			} while (kiemTraChonYN());
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// chức năng 8 :Hiển thị thông tin các sinh viên Trung Bình theo thứ tự giảm dần
	// điểm trung bình.(6.5 > DTB >= 5.0)
	public void chucNang8() {
		// Dùng MySQL
		try {
			do {
				// Tạo Kết nối
				conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
				if (conn != null) {
					System.out.println("Kết nối DataBase thành công ");
					// Tạo Statement
					Statement st = conn.createStatement();
					String SAPXEPSVTB = "select*from JAVA24_DB.danhsach where aVG <6.5  and aVG >=5 order by aVG desc";
					ResultSet rs = st.executeQuery(SAPXEPSVTB);
					System.out.println(
							"Danh sách sinh viên đạt điểm Trung Bình theo thứ tự giảm dần diểm trung bình là : ");
					// Hiển thị bảng Table
					formatTable();
					if (rs.next()) {
						do {
							System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|\n", rs.getString(1),
									rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getFloat(6),
									rs.getFloat(7), rs.getFloat(8), rs.getFloat(9));
						} while (rs.next());
					} else {
						System.out.printf(
								"                                      Không có sinh viên Trung Bình trong danh sách %n");
					}
					System.out.printf(
							"-----------------------------------------------------------------------------------------------------------%n");
				} else {
					System.out.println("Kết nối thất bại");
				}
			} while (kiemTraChonYN());
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// chức năng 9 :Hiển thị thông tin các sinh viên Yếu theo thứ tự giảm dần điểm
	// trung bình.( DTB < 5.0)
	public void chucnang9() {
		// Dùng MySQL
		try {
			do {
				// Tạo Kết nối
				conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
				if (conn != null) {
					System.out.println("Kết nối DataBase thành công ");
					Statement st = conn.createStatement();
					String SAPXEPSVY = "select*from JAVA24_DB.danhsach where aVG <5  and aVG >=0 order by aVG desc";
					ResultSet rs = st.executeQuery(SAPXEPSVY);
					System.out
							.println("Danh sách sinh viên đạt điểm TB Yếu theo thứ tự giảm dần diểm trung bình là : ");
					// Hiển thị bảng Table
					formatTable();
					if (rs.next()) {
						do {
							System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|\n", rs.getString(1),
									rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getFloat(6),
									rs.getFloat(7), rs.getFloat(8), rs.getFloat(9));
						} while (rs.next());
					} else {
						System.out.printf(
								"                                  Không có sinh viên đạt điểm TB Yếu trong danh sách %n");
					}
					System.out.printf(
							"-----------------------------------------------------------------------------------------------------------%n");
				} else {
					System.out.println("Kết nối thất bại");
				}
			} while (kiemTraChonYN());
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//		Chức năng 10 : Xóa thông tin SV theo MSV.
	public void chucNang10() {
		// Dùng MySQL
		try {
			// Tạo Kết nối
			do {
				conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
				if (conn != null) {
					System.out.println("Kết nối DataBase thành công ");
					System.out.println("Nhập MSV của sinh viên cần xóa: ");
					String inPutMSV = src.nextLine().trim().toUpperCase();
					// Tạo Statement
					Statement st = conn.createStatement();
					String CheckSV = "select*from JAVA24_DB.danhsach where MSV = '" + inPutMSV + "'";
					ResultSet rs = st.executeQuery(CheckSV);
					System.out.println("Thông tin của sinh viên cần xóa : ");
					// Hiển thị bảng Table
					formatTable();
					if (rs.next()) {
						System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|\n", rs.getString(1),
								rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getFloat(6),
								rs.getFloat(7), rs.getFloat(8), rs.getFloat(9));
						System.out.printf(
								"-----------------------------------------------------------------------------------------------------------%n");
						do {
							System.out.println("Chọn Y :để xóa sinh viên ");
							System.out.println("Chọn N :để hủy xóa và tiếp tục chức năng 10 .");
							choiseYN = src.nextLine();
							if (!choiseYN.equalsIgnoreCase("Y") && !choiseYN.equalsIgnoreCase("N")) {
								System.out.println("Bạn chọn k đúng ");
								checkThongTinNhap = true;
							} else if (choiseYN.equalsIgnoreCase("Y")) {
								checkThongTinNhap = false;
								String DELETESV = "DELETE from JAVA24_DB.danhsach where MSV = '" + inPutMSV + "'";
								int xoaSV = 0;
								xoaSV = st.executeUpdate(DELETESV);
								if (xoaSV != 0) {
									System.out.println("Đã xóa thành công ");
								} else {
									System.err.println("Xóa không thành công ");
								}
							} else {
								System.out.println("Hủy xóa.");
							}
						} while (checkThongTinNhap);
					} else {
						System.err.printf("                               MSV không tồn tại trong Danh sách %n");
						System.out.printf(
								"-----------------------------------------------------------------------------------------------------------%n");
					}
				} else {
					System.out.println("Kết nối thất bại");
				}
			} while (kiemTraChonYN());
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//		Chức năng 11 : Hiển Thị Thông Tin Toàn Bộ Sinh Viên theo thứ tự Anpha-B của FristName.
	public void chucNang11() {

		// Dùng MySQL
		try {
			do {
				// Tạo Kết nối
				conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
				if (conn != null) {
					System.out.println("Kết nối DataBase thành công ");
					Statement st = conn.createStatement();
					String SapXepDanhSachSV = "select*from JAVA24_DB.danhsach order by firtName asc";
					ResultSet rs = st.executeQuery(SapXepDanhSachSV);
					System.out.println("Danh sách sinh viên sắp xếp theo thứ tự Anpha-B ");
					// Hiển thị bảng Table
					formatTable();
					if (rs.next()) {
						do {
							System.out.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|\n", rs.getString(1),
									rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getFloat(6),
									rs.getFloat(7), rs.getFloat(8), rs.getFloat(9));
						} while (rs.next());
					} else {
						System.out.printf("                                     Không có sinh viên trong danh sách %n");
					}
					System.out.printf(
							"-----------------------------------------------------------------------------------------------------------%n");
				} else {
					System.out.println("Kết nối thất bại");
				}
			} while (kiemTraChonYN());
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// Ktra chọn Y/N

	public boolean kiemTraChonYN() {
		do {
			checkThongTinNhap = false;
			System.out.println("Bạn có muốn tiếp tục hay quay lại Menu chính");
			System.out.println("Chọn Y để tiếp tục");
			System.out.println("Chọn N để quay lại Menu chính");
			String luaChon = src.nextLine();
			if (luaChon.equalsIgnoreCase("Y")) {
			} else if (luaChon.equalsIgnoreCase("N")) {
				return false;
			} else {
				System.out.println("Bạn chọn k đúng");
				checkThongTinNhap = true;
			}
		} while (checkThongTinNhap);
		return true;
	}

	// Ktra chọn Y/N

	public boolean suaDoiThongTinVuaNhap() {
		do {
			checkThongTinNhap = false;
			System.out.println("Bạn có muốn sửa lại ");
			System.out.println("Chọn Y để sửa lại thông tin vừa nhập");
			System.out.println("Chọn N để tiếp tục");
			String luaChon = src.nextLine();
			if (luaChon.equalsIgnoreCase("Y")) {
			} else if (luaChon.equalsIgnoreCase("N")) {
				return false;
			} else {
				System.out.println("Bạn chọn k đúng");
				checkThongTinNhap = true;
			}
		} while (checkThongTinNhap);
		return true;
	}

	public void formatTable() {
		System.out.printf(
				"-----------------------------------------------------------------------------------------------------------%n");
		System.out.printf("                                      Danh sách sinh viên  %n");
		System.out.printf(
				"-----------------------------------------------------------------------------------------------------------%n");
		System.out.printf("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10s|%-10s|%-10s|%-6s|%n", "MSV", "Họ và tên", "Tên", "Gender",
				"Lớp", "Điểm toán", "Điểm  lý", "Điểm  hóa", "ĐiểmTB");
		System.out.printf(
				"-----------------------------------------------------------------------------------------------------------%n");

	}
}