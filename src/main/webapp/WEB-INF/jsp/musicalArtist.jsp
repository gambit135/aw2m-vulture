<%-- 
    Document   : musicalArtist
    Created on : 26/11/2012, 04:17:18 AM
    Author     : Alejandro Téllez G. <java.util.fck@hotmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page!!!</title>
    </head>
    <body>
        <h1>Hello World!</h1>

        <form:form method="post" action="add" commandName="musicalArtist" class="form-vertical">

            <form:label path="name">Name</form:label>
            <form:input path="name" />
            
            <!-- <form:label path="imageURL">imageUrl</form:label> -->
            <!-- <form:input path="imageURL" /> -->

            <!-- <form:label path="imageCaption">imagecaption</form:label> -->
            <!-- <form:input path="imageCaption" /> -->
            
            <input type="submit" value="Add MusicalArtist" class="btn"/>
        </form:form>

        <c:if  test="${!empty musicalArtistList}">
            <h3>MusicalArtist</h3>
            <table class="table table-bordered table-striped">
                <thead>
                    <tr>
                        <th>name</th>
                        <th>&nbsp;</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${musicalArtistList}" var="musicalArtist">
                        <tr>
                            <td>${musicalArtist.name}, ${musicalArtist.name}</td>
                            <td><form action="delete/${musicalArtist.name}" method="POST"><input type="submit" class="btn btn-danger btn-mini" value="Delete"/></form></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </body>
</html>
