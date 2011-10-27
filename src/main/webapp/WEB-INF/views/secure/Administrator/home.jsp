<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello <security:authentication property="principal.username" />!
</h1>

<p>  The time on the server is ${serverTime}. The number of users in the db ${users.size()}</p>
<p>Your role is:</p>
<security:authentication var="authorities" property="principal.authorities" />
<c:forEach items="${authorities }" var="role">
	<c:out value="${role.authority }"></c:out>
</c:forEach>

<h2>You can logout <a href="<c:url value="/j_spring_security_logout"/>">here</a></h2>
</body>
</html>
