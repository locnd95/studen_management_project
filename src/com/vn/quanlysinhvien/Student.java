/**
 * 
 */
package com.vn.quanlysinhvien;

/**
 * @author Admin
 *
 */
public class Student {

	private String MSV;
	private String lastName;
	private String firtName;
	private String gender;
	private String classStudent;
	private Float mathPoint;
	private Float physicalPoint;
	private Float chemistryPoint;
	private Float AVG;

//Contructor
	public Student() {
	}

//
	public Student(String mSV, String lastName, String firtName, String gender, String classStudent, Float mathPoint,
			Float physicalPoint, Float chemistryPoint) {
		this.MSV = mSV;
		this.lastName = lastName;
		this.firtName = firtName;
		this.gender = gender;
		this.classStudent = classStudent;
		this.mathPoint = mathPoint;
		this.physicalPoint = physicalPoint;
		this.chemistryPoint = chemistryPoint;

//				
	}

	public String getMSV() {
		return MSV;
	}

	public void setMSV(String mSV) {
		this.MSV = mSV;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirtName() {
		return firtName;
	}

	public void setFirtName(String firtName) {
		this.firtName = firtName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getClassStudent() {
		return classStudent;
	}

	public void setClassStudent(String classStudent) {
		this.classStudent = classStudent;
	}

	public Float getMat() {
		return mathPoint;
	}

	public void setMat(Float mat) {
		this.mathPoint = mat;
	}

	public Float getPhy() {
		return physicalPoint;
	}

	public void setPhy(Float phy) {
		this.physicalPoint = phy;
	}

	public Float getChemis() {
		return chemistryPoint;
	}

	public void setChemis(Float chemis) {
		this.chemistryPoint = chemis;
	}

	public Float getAVG() {
		return (mathPoint + physicalPoint + chemistryPoint) / 3;

	}

	public void setAvg(Float avg) {
		this.AVG = avg;
	}

	//
//	@Override
//	public String toString() {
//		return MSV + "-" + lastName + " " + firtName + "-" + gender + "-" + classStudent + ", mat= " + mathPoint
//				+ ",phy= " + physicalPoint + "- chemis= " + chemistryPoint + ", AVG=" + getAVG();
//	}
	//
	@Override
	public String toString() {
		return String.format("|%-7s|%-20s|%-20s|%-7s|%-7s|%-10.1f|%-10.1f|%-10.1f|%-6.1f|", MSV, lastName,
				firtName, gender, classStudent, mathPoint, physicalPoint, chemistryPoint, getAVG());
	}
	{

	}

}
