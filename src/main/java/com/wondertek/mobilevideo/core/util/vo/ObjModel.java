package com.wondertek.mobilevideo.core.util.vo;

import java.io.Serializable;
import java.util.Date;

public class ObjModel<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	public Date date;
	public int count;
	public String desc;
	public Boolean tag = false;
	public T tModel;
	
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public T gettModel() {
		return tModel;
	}
	
	public void settModel(T tModel) {
		this.tModel = tModel;
	}

	public Boolean getTag() {
		return tag;
	}

	public void setTag(Boolean tag) {
		this.tag = tag;
	}
	
}
