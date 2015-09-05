package cn.edu.tit.bean;

import java.io.Serializable;

public class City implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String province;
	private String name;
	private String number;
	private String pinyin;
	private String py;

	public City() {
	}

	public City(String province, String city, String number, String allPY,
			String allFristPY) {
		super();
		this.province = province;
		this.name = city;
		this.number = number;
		this.pinyin = allPY;
		this.py = allFristPY;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	@Override
	public String toString() {
		return "City [province=" + province + ", name=" + name + ", number="
				+ number + ", pinyin=" + pinyin + ", py=" + py + "]";
	}

}
