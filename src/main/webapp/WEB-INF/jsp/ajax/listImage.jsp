<?xml version="1.0" encoding='UTF-8'?>
<%-- 
    Document   : listImage
    Created on : 4-Nov-2009, 5:19:09 PM
    Author     : yang
--%>

<%@page contentType="text/xml" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include.jsp"%>



<c:forEach var="image" items="${images}">
	<image>
		<id>${image.id}</id>
		<name>${image.name}</name>
		<url>/image/view?id=${image.id}</url>
	</image>
</c:forEach>



