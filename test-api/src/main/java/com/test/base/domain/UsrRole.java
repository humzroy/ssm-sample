package com.test.base.domain;

import java.io.Serializable;

public class UsrRole implements Serializable {
	
    private int id;
    
    private String usr_lev;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsr_lev() {
		return usr_lev;
	}

	public void setUsr_lev(String usr_lev) {
		this.usr_lev = usr_lev;
	}
   
}