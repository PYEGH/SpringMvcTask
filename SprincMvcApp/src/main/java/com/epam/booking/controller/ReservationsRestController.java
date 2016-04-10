package com.epam.booking.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.booking.model.Reservation;
import com.epam.booking.model.ReservationList;
import com.epam.booking.service.ReservationService;

@RestController
public class ReservationsRestController {

	private final ReservationService reservationService;

	@Autowired
	public ReservationsRestController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@RequestMapping(value = "/getAllReservations", method = RequestMethod.GET)
	public ReservationList getReservationListXml() {

		ReservationList resrvationList = new ReservationList();
		List<Reservation> reservationList = reservationService
				.getAllAvailableReservations();
		resrvationList.setReservatios(reservationList);
		return resrvationList;

	}

	@RequestMapping(value = "/getReservation/{reservationNumber}", method = RequestMethod.GET)
	public @ResponseBody
	Reservation getReservationXML(@PathVariable String reservationNumber) {

		return reservationService.getReservationByNumber(reservationNumber)
				.get(0);

	}

	@RequestMapping(value = "/updateReservation/{reservationNumber}/{name}/{surname}", method = RequestMethod.GET)
	public @ResponseBody
	Reservation updateReservationAndSeeResultXML(
			@PathVariable String reservationNumber, @PathVariable String name,
			@PathVariable String surname) throws SQLException,
			InterruptedException {

		reservationService.reservateTicket(name, surname, reservationNumber);

		return reservationService.getReservationByNumber(reservationNumber)
				.get(0);

	}
}
