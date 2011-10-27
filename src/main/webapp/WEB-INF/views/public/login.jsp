<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
</head>
<body>
<c:if test="${not empty statusMessageKey}">
    <p><fmt:message key="${statusMessageKey}"/></p>
</c:if>

<c:url var="url" value="/login" /> 
<form:form action="${url}" modelAttribute="user" method="POST" >

    <fieldset>
        <div class="form-row">
            <label for="username"><fmt:message key="Login.label.username"/></label>
            <span class="input"><form:input path="username"/></span>
            <form:errors path="username" cssClass="error" />
        </div>       
        <div class="form-row">
            <label for="password"><fmt:message key="Login.label.password"/></label>
            <span class="input"><form:password path="password"/></span>
            <form:errors path="password" cssClass="error" />
        </div>
        <div class="form-buttons">
            <div class="button"><input name="submit" type="submit" value="<fmt:message key="Login.button.login"/>" /></div>
        </div>
    </fieldset>
</form:form>



</body>
</html>