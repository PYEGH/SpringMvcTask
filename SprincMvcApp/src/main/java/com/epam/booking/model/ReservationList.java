package com.epam.booking.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "reservationList")
public class ReservationList {
	private List<Reservation> reservationList;

	public ReservationList() {
		reservationList = new CopyOnWriteArrayList<Reservation>();
	}

	@XmlElement
	@XmlElementWrapper(name = "reservations")
	public List<Reservation> getReservatios() {
		return reservationList;
	}

	public void setReservatios(List<Reservation> reservationList) {
		this.reservationList = reservationList;
	}
}
