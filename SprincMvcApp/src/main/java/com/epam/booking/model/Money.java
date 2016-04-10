package com.epam.booking.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "money")
public class Money {

	private int roubles;

	public Money() {

	}

	public Money(int roubles) {
		this.roubles = roubles;
	}

	@XmlElement
	public int getRoubles() {
		return roubles;
	}

	public void setRoubles(int roubles) {
		this.roubles = roubles;
	}
}
