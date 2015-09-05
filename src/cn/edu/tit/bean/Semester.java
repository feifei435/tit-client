package cn.edu.tit.bean;

import java.util.Comparator;

public class Semester implements Comparable {
	private int code;
	private String semester;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	@Override
	public String toString() {
		return "Semester [code=" + code + ", semester=" + semester + "]";
	}
	@Override
	public int compareTo(Object another) {
		// TODO Auto-generated method stub
		return this.code - ((Semester) another).getCode();
	}

}
