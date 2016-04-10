package com.epam.booking.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epam.booking.model.Human;
import com.epam.booking.model.Reservation;
import com.epam.booking.service.ReservationService;

@Controller
public class ReservationWebController {

	private static final String TO_RESERVATION_PAGE = "reservationPage";
	private static final String TO_RESERVATION_LIST_PAGE = "reservationListPage";
	private static final String RESERVATION_NUMBER_PARAM = "reservationNumber";
	private static final String RESERVATION_LIST_PARAM = "reservations";
	private static final String HUMAN_PARAM = "human";
	private static final String SELECTED_RESERVATION_PARAM = "selecctedReservations";
	private static final String INFO_MESSAGE_PARAM = "infoMessage";
	private static final String NAME_PARAM = "name";
	private static final String SURNAME_PARAM = "surname";

	private final ReservationService reservationService;

	@Autowired
	public ReservationWebController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showAvailableDates(ModelMap model) {

		List<Reservation> reservations = reservationService
				.getAllAvailableReservations();
		model.addAttribute(RESERVATION_LIST_PARAM, reservations);

		return TO_RESERVATION_LIST_PAGE;

	}

	@RequestMapping(value = "/reservationPage", method = RequestMethod.GET)
	public String showReservationById(HttpServletRequest request, Human human,
			ModelMap model,
			@ModelAttribute(RESERVATION_NUMBER_PARAM) String reservationNumber) {

		// Get reservation by provided id
		List<Reservation> reservations = reservationService
				.getReservationByNumber(reservationNumber);

		model.addAttribute(HUMAN_PARAM, human);
		model.addAttribute(SELECTED_RESERVATION_PARAM, reservations.get(0));
		model.addAttribute(RESERVATION_NUMBER_PARAM, reservationNumber);

		return TO_RESERVATION_PAGE;
	}

	@RequestMapping(value = "/deleteReservation", method = RequestMethod.GET)
	public String deleteReservation(HttpServletRequest request, ModelMap model,
			@ModelAttribute(RESERVATION_NUMBER_PARAM) String reservationNumber)
			throws SQLException, InterruptedException {
		reservationService.deleteReservation(reservationNumber);
		List<Reservation> reservations = reservationService
				.getAllAvailableReservations();

		final String infoMessage = "Reservation was with id "
				+ reservationNumber
				+ " was deleted. Now it is free for reservation.";

		model.addAttribute(INFO_MESSAGE_PARAM, infoMessage);
		model.addAttribute(RESERVATION_LIST_PARAM, reservations);
		return TO_RESERVATION_LIST_PAGE;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String reservateTickect(Model model, Human human,
			BindingResult result,
			@ModelAttribute(RESERVATION_NUMBER_PARAM) String reservationNumber,
			@ModelAttribute(NAME_PARAM) String name,
			@ModelAttribute(SURNAME_PARAM) String surname) throws SQLException,
			InterruptedException {
		String returnVal;
		if (name == null || surname == null || name.isEmpty()
				|| surname.isEmpty()) {
			List<Reservation> reservations = reservationService
					.getReservationByNumber(reservationNumber);

			final String infoMessage = "You are not provided name or surname. Try again";

			model.addAttribute(SELECTED_RESERVATION_PARAM, reservations.get(0));
			model.addAttribute(INFO_MESSAGE_PARAM, infoMessage);
			returnVal = TO_RESERVATION_PAGE;
		} else {

			reservationService
					.reservateTicket(name, surname, reservationNumber);

			List<Reservation> reservations = reservationService
					.getAllAvailableReservations();

			final String infoMessage = "Person " + name + " " + surname
					+ " reserved seat.";

			model.addAttribute(INFO_MESSAGE_PARAM, infoMessage);
			model.addAttribute(RESERVATION_LIST_PARAM, reservations);
			model.addAttribute(HUMAN_PARAM, human);
			returnVal = TO_RESERVATION_LIST_PAGE;
		}
		return returnVal;
	}

}