<%@ page language="java" contentType="application/json; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@page import="entity.Place"%>
<%@page import="dao.impl.PlaceDAO"%>
<%
    response.setHeader("Access-Control-Allow-Origin", "*");
    try {
        String bizNo = request.getParameter("bizNo");
        Place place = new Place(bizNo, null , 0);
        PlaceDAO.getInstance().updateLikeCount(place);
        response.setStatus(200);
    } catch(Exception e) {
        e.printStackTrace();
        response.sendError(400, "데이터베이스에 존재하지 않음");
    }
    
%>
