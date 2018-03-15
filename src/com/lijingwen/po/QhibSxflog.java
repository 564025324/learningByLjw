package com.lijingwen.po;
public class QhibSxflog {
	private static final long serialVersionUID = 1L;

	private String fundid;

	private float sxf;

	private String fundname;

	private Long id;

	public Long getId() {
		return id;
	}

	public String getFundname() {
		return fundname;
	}

	public void setFundname(String fundname) {
		this.fundname = fundname;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFundid() {
		return fundid;
	}

	public void setFundid(String fundid) {
		this.fundid = fundid;
	}

	public float getSxf() {
		return sxf;
	}

	public void setSxf(float sxf) {
		this.sxf = sxf;
	}

	@Override
	public String toString() {
		return "QhibSxflog [fundid=" + fundid + ", sxf=" + sxf + ", fundname="
				+ fundname + ", id=" + id + "]";
	}
}
