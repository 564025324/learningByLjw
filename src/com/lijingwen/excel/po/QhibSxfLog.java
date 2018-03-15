package com.lijingwen.excel.po;

public class QhibSxfLog {
	private int id;
	private String fundid;
	private Float sxf;
	private String fundname;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFundid() {
		return fundid;
	}

	public void setFundid(String fundid) {
		this.fundid = fundid;
	}

	public String getFundname() {
		return fundname;
	}

	public void setFundname(String fundname) {
		this.fundname = fundname;
	}

	public Float getSxf() {
		return sxf;
	}

	public void setSxf(Float sxf) {
		this.sxf = sxf;
	}

	@Override
	public String toString() {
		return "QhibSxfLog [id=" + id + ", fundid=" + fundid + ", sxf=" + sxf
				+ ", fundname=" + fundname + "]";
	}

}
