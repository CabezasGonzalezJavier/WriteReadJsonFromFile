package com.thedeveloperworldisyours.writereadjsonfromfile.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Actress {

	@SerializedName("name_approx")
	@Expose
	private List<NameApprox> nameApprox = new ArrayList<NameApprox>();

	/**
	 * 
	 * @return The nameApprox
	 */
	public List<NameApprox> getNameApprox() {
		return nameApprox;
	}

	/**
	 * 
	 * @param nameApprox
	 *            The name_approx
	 */
	public void setNameApprox(List<NameApprox> nameApprox) {
		this.nameApprox = nameApprox;
	}

}
