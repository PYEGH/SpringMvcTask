package com.epam.booking.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@XmlRootElement(name = "human")
public class Human {
	private String name;
	private String surname;

	public Human() {
	}

	@Autowired
	public Human(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}
