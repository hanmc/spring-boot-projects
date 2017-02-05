package com.hmc.event;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="hmc")
public class XmlRootEvent {

	private String root;
	
	private int body;

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public int getBody() {
		return body;
	}

	public void setBody(int body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "XmlRootEvent [root=" + root + ", body=" + body + "]";
	}
}
