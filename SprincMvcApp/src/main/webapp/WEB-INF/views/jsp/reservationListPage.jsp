
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Maven + Spring MVC + @JavaConfig</title>

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css"
	var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
</head>


<div class="container">

	<c:if test="${not empty infoMessage}">
		<td>${infoMessage}</td>
	</c:if>

	<div class="row">
		List of available dates. Please choose date for reservation:
		<table class="tg" border="1">
			<tr>
				<td>reservationNumber</td>
				<td>filmTitle</td>
				<td>dateOfSeance</td>
				<td>seat</td>
				<td>price</td>
				<td>state</td>
				<td>action</td>
			</tr>
			<c:forEach var="reservation" items="${reservations}">
				<tr>
					<td><c:out value="${reservation.reservationNumber}" /></td>
					<td><c:out value="${reservation.filmTitle}" /></td>
					<td><c:out value="${reservation.dateOfSeance}" /></td>
					<td><c:out value="${reservation.seat}" /></td>
					<td><c:out value="${reservation.price.roubles}" /></td>

					<c:if test="${not empty reservation.person.name}">
						<td>reserved</td>
						<td><form:form method="GET"
								action="/SprincMvcApp/deleteReservation">
								<table>
									<tr>
										<td><input type="hidden" name=reservationNumber
											value="${reservation.reservationNumber}" /> <input
											type="submit" value="Delete reservation" /></td>
									</tr>
								</table>
							</form:form></td>
					</c:if>

					<c:if test="${empty reservation.person.name}">
						<td>available</td>
						<td><form:form method="GET"
								action="/SprincMvcApp/reservationPage">
								<table>
									<tr>

										<td><input type="hidden" name=reservationNumber
											value="${reservation.reservationNumber}" /> <input
											type="submit" value="reserve" /></td>
									</tr>
								</table>
							</form:form></td>
					</c:if>
				</tr>
			</c:forEach>
		</table>

	</div>

</div>

<spring:url value="/resources/core/css/hello.js" var="coreJs" />
<spring:url value="/resources/core/css/bootstrap.min.js"
	var="bootstrapJs" />

<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

</body>
</html>