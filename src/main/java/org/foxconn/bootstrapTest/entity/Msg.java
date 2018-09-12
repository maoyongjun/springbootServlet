package org.foxconn.bootstrapTest.entity;

import java.util.List;

public class Msg {
	String msg;
	private List<LabelFileEntity> files;
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<LabelFileEntity> getFiles() {
		return files;
	}

	public void setFiles(List<LabelFileEntity> files) {
		this.files = files;
	}

	
}
