package cn.edu.tit.bean;

public class Subject {
	private String mark;
	private String sbjname;

	public Subject(String mark, String sbjname) {
		this.mark = mark;
		this.sbjname = sbjname;
	}
	public Subject(){}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getSbjname() {
		return sbjname;
	}

	public void setSbjname(String sbjname) {
		this.sbjname = sbjname;
	}

	@Override
	public String toString() {
		return "Subject [mark=" + mark + ", sbjname=" + sbjname + "]";
	}

}
