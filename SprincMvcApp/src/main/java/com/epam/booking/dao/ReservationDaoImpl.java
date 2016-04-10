package com.epam.booking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epam.booking.db.ConnectionPool;
import com.epam.booking.model.Human;
import com.epam.booking.model.Money;
import com.epam.booking.model.Reservation;

@Component("ReservationDaoImplBean")
@Scope("prototype")
public class ReservationDaoImpl implements ReservationDao {
	@Autowired
	private ConnectionPool connector;
	private static final String SQL_SELECT_ALL_AVAILABLE_RESERVATIONS_BY_DATE = "SELECT * FROM Reservation"
			+ " where dateOfSeance=TO_DATE(?, 'yyyy/mm/dd hh24:mi:ss') "
			+ " and personName is null" + " and personSurname is null";

	private static final String SQL_SELECT_RESERVATION_BY_NUMBER = "SELECT * FROM Reservation"
			+ " where reservationNumber=?";

	private static final String SQL_UPDATE_RESERVATION_BY_NUMBER = "UPDATE Reservation SET personName = ?,"
			+ "personSurname =? where reservationNumber = ?";

	private static final String SQL_SELECT_ALL_AVAILABLE_DATES = "SELECT distinct dateOfSeance  FROM Reservation";
	
	private static final String SQL_SELECT_ALL_AVAILABLE_RESERVATIONS = "SELECT * FROM Reservation";

	public ReservationDaoImpl() {

	}

	public void updateReservationWithNameAndSureName(final String name,
			final String surname, final String number) throws SQLException,
			InterruptedException {
		Connection connection = connector.getConnection();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(SQL_UPDATE_RESERVATION_BY_NUMBER);
			// setting parameters to request into DB
			ps.setString(1, name);
			ps.setString(2, surname);
			ps.setString(3, number);

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.close();
			}
			connector.closeConnection(connection);
		}
	}

	public List<Reservation> getReservationByNumber(
			final String selectedReservationNumber) {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try {
			// 1.Getting connection
			Connection connection = connector.getConnection();

			// 2.Reading of serialized Persons
			PreparedStatement ps = connection
					.prepareStatement(SQL_SELECT_RESERVATION_BY_NUMBER);
			ps.setString(1, selectedReservationNumber);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Reservation reservation = getReservationFromResultSet(rs);
				reservations.add(reservation);
				break;
			}
			ps.close();
			connector.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return reservations;
	}

	public List<Reservation> getAllAvailableReservations(
			final String selectedDateOfSeance) {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try {
			// 1.Getting connection
			Connection connection = connector.getConnection();

			// 2.Reading of serialized Persons
			PreparedStatement ps = connection
					.prepareStatement(SQL_SELECT_ALL_AVAILABLE_RESERVATIONS_BY_DATE);
			ps.setString(1, selectedDateOfSeance);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Reservation reservation = getReservationFromResultSet(rs);
				reservations.add(reservation);
			}
			ps.close();
			connector.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return reservations;
	}

	private Reservation getReservationFromResultSet(ResultSet rs)
			throws SQLException {
		String reservationNumber = rs.getString(1);
		String filmTitle = rs.getString(2);
		String dateOfSeance = rs.getDate(3).toString();
		int seat = rs.getInt(4);
		int price = rs.getInt(5);
		String personName = rs.getString(6);
		String personSurname = rs.getString(7);

		Money filmPrice = new Money(price);
		Human person = new Human(personName, personSurname);
		return new Reservation(reservationNumber, filmTitle, dateOfSeance,
				seat, filmPrice, person);
	}

	@Override
	public List<String> getAllAvailableDates() {
		List<String> availableDates = new ArrayList<String>();
		try {
			// 1.Getting connection
			Connection connection = connector.getConnection();

			// 2.Reading of serialized Persons
			PreparedStatement ps = connection
					.prepareStatement(SQL_SELECT_ALL_AVAILABLE_DATES);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String dateOfSeance = rs.getDate(1).toString();
				availableDates.add(dateOfSeance);
			}
			ps.close();
			connector.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return availableDates;
	}

	@Override
	public List<Reservation> getAllAvailableReservations() {
		
		List<Reservation> reservations = new ArrayList<Reservation>();
		try {
			// 1.Getting connection
			Connection connection = connector.getConnection();

			// 2.Reading of serialized Persons
			PreparedStatement ps = connection
					.prepareStatement(SQL_SELECT_ALL_AVAILABLE_RESERVATIONS);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Reservation reservation = getReservationFromResultSet(rs);
				reservations.add(reservation);
			}
			ps.close();
			connector.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return reservations;
	}



}
