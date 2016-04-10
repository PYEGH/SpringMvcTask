package com.epam.booking.client;

import java.sql.SQLException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epam.booking.dao.ReservationDao;
import com.epam.booking.model.Reservation;
import com.epam.booking.service.ReservationService;

public class DemonstratorClient {

	static {
		context = new ClassPathXmlApplicationContext("Beans.xml");
	}
	private static ApplicationContext context;
	private static final String ENTERED_BY_USER_DATE = "2016/04/03 18:00:00";
	private static final String ENETERED_BY_USER_NUMBER = "10";
	private static final String ENETERED_BY_USER_NUMBER_FOR_RESERVATION = "10";
	private static final String ENETERED_BY_USER_NUMBER_FOR_RESERVATION_DELETE = "10";
	private static final String ENETERED_BY_USER_NAME = "Michael";
	private static final String ENETERED_BY_USER_SURNAME = "Phelps";

	public static void run() {
		ReservationService reservationService = (ReservationService) context
				.getBean("ReservationServiceImpl"); // the 1st way of
														// getting bean(the 2nd
														// is using of Autowired
														// annotation)
		demonstrateSelectionOfAvailableSeancesByDate(ENTERED_BY_USER_DATE,
				reservationService);
		demonstrateSelectionByNumber(ENETERED_BY_USER_NUMBER,
				reservationService);
		demonstrateTicketReservation(ENETERED_BY_USER_NUMBER_FOR_RESERVATION,
				reservationService);
		demonstrateTicketReservationDelete(
				ENETERED_BY_USER_NUMBER_FOR_RESERVATION_DELETE,
				reservationService);

	}

	private static void demonstrateSelectionOfAvailableSeancesByDate(
			final String selectedDateOfSeance,
			final ReservationService reservationService) {
		System.out.println("\n All available tickets for "
				+ selectedDateOfSeance + " date:");
		showReservations(reservationService
				.getAllAvailableReservations(selectedDateOfSeance));
	}

	private static void demonstrateSelectionByNumber(
			final String selectedReservationNumber,
			final ReservationService reservationService) {
		System.out.println("\n Reservation with number "
				+ selectedReservationNumber + " :");
		showReservations(reservationService
				.getReservationByNumber(selectedReservationNumber));

	}

	private static void demonstrateTicketReservation(
			final String selectedReservationNumber,
			final ReservationService reservationService) {
		System.out.println("\n Reservation with number "
				+ "selectedReservationNumber" + " was updated" + " :");
		try {
			reservationService.reservateTicket(ENETERED_BY_USER_NAME,
					ENETERED_BY_USER_SURNAME,
					ENETERED_BY_USER_NUMBER_FOR_RESERVATION);
			showReservations(reservationService
					.getReservationByNumber(ENETERED_BY_USER_NUMBER_FOR_RESERVATION));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void demonstrateTicketReservationDelete(
			final String selectedReservationNumber,
			final ReservationService reservationService) {
		System.out
				.println("\n Reservation with number was deleted(became free for other people)"
						+ selectedReservationNumber + " :");
		try {
			reservationService
					.deleteReservation(ENETERED_BY_USER_NUMBER_FOR_RESERVATION_DELETE);
			showReservations(reservationService
					.getReservationByNumber(ENETERED_BY_USER_NUMBER_FOR_RESERVATION));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void showReservations(final List<Reservation> reservations) {
		for (Reservation reservation : reservations) {
			System.out.println(reservation.getReservationNumber() + " "
					+ reservation.getFilmTitle() + " "
					+ reservation.getDateOfSeance() + " "
					+ reservation.getSeat() + " "
					+ reservation.getPrice().getRoubles() + " "
					+ reservation.getPerson().getName() + " "
					+ reservation.getPerson().getSurname());
		}
	}
}
