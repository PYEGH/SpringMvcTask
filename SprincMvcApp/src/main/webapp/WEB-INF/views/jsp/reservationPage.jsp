
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Reservation Page</title>
</head>
<body>

	<h2>Reservation Page</h2>
	<c:if test="${not empty infoMessage}">
		<td>${infoMessage}</td>
	</c:if>
	<table class="tg" border="1">
		<tr>
			<td>reservationNumber</td>
			<td>filmTitle</td>
			<td>dateOfSeance</td>
			<td>seat</td>
			<td>price</td>
		</tr>
		<tr>
			<td><c:out value="${selecctedReservations.reservationNumber}" /></td>
			<td><c:out value="${selecctedReservations.filmTitle}" /></td>
			<td><c:out value="${selecctedReservations.dateOfSeance}" /></td>
			<td><c:out value="${selecctedReservations.seat}" /></td>
			<td><c:out value="${selecctedReservations.price.roubles}" /></td>
		</tr>
	</table>

	<form:form method="POST" commandName="human">
		<table>
			<tr>
				<td>Enter your name:</td>
				<td><form:input path="name" /></td>
			</tr>
			<tr>
				<td>Enter your surname:</td>
				<td><form:input path="surname" /></td>
			</tr>
			<tr>
				<td><input type="submit" name="submit" value="Submit"></td>
			</tr>
			<tr>
		</table>
	</form:form>



</body>
</html>