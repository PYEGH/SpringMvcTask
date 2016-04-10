package com.epam.booking.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epam.booking.dao.ReservationDao;
import com.epam.booking.model.Reservation;

@Component("ReservationServiceImpl")
@Scope("prototype")
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationDao reservationDao;
	
	public ReservationServiceImpl(){
		
	}

	public List<Reservation> getAllAvailableReservations(
			final String selectedDateOfSeance) {
		return reservationDao.getAllAvailableReservations(selectedDateOfSeance);
	}

	public List<Reservation> getReservationByNumber(
			final String selectedReservationNumber) {
		return reservationDao.getReservationByNumber(selectedReservationNumber);
	}

	public void reservateTicket(String name, String surname, String number)
			throws SQLException, InterruptedException {
		reservationDao.updateReservationWithNameAndSureName(name, surname,
				number);

	}

	public void deleteReservation(String number)
			throws SQLException, InterruptedException {
		reservationDao.updateReservationWithNameAndSureName("", "",
				number);
		
	}

	@Override
	public List<String> getAllAvailableDates() {
		return reservationDao.getAllAvailableDates();
	}

	@Override
	public List<Reservation> getAllAvailableReservations() {
		return reservationDao.getAllAvailableReservations();
	}
}
