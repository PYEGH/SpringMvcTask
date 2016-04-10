package com.epam.booking.dao;

import java.sql.SQLException;
import java.util.List;

import com.epam.booking.model.Reservation;

public interface ReservationDao {

	List<Reservation> getAllAvailableReservations(
			final String selectedDateOfSeance);

	List<Reservation> getReservationByNumber(
			final String selectedReservationNumber);

	void updateReservationWithNameAndSureName(final String name,
			final String surname, final String number) throws SQLException, InterruptedException;

	List<String> getAllAvailableDates();

	List<Reservation> getAllAvailableReservations();

}
