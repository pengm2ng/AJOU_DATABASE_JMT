 <%@ page language="java" contentType="application/json; charset=utf-8"%>
<%@page import="java.dao.impl.ExpendtrExcutDAO"%>
<%

    String deptDiv = request.getParameter("deptDiv");
    String govofcDiv = request.getParameter("govofcDiv");
    String hgdeptDiv = request.getParameter("hgdeptDiv");
    String dept = request.getParameter("dept");
    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");


    ExpendtrExcutDAO expendDAO = new ExpendtrExcutDAO();
    Organization organizationDeptDiv = new DeptDiv(null, deptDiv);
    Organization organizationGovofcDiv = new GovofcDiv(null, govofcDiv);
    Organization organizationHgdeptDiv = new HgdeptDiv(null, hgdeptDiv);
    Organization organizationDept = new Dept(null, dept);

    if (startDate.length() == 0 && endDate.length() == 0) {
      List<ExpendtrExcut> list = expendDAO.getPlaceTopTen(organizationDeptDiv, organizationGovofcDiv,
          organizationHgdeptDiv, organizationDept);
      //json으로 변환

      out.print();

    } else {
      Date date1 = Date.valueOf(startDate);
      Date date2 = Date.valueOf(endDate);
      List<ExpendtrExcut> list = expendDAO.getPlaceTopTen(organizationDeptDiv, organizationGovofcDiv,
          organizationHgdeptDiv, organizationDept, date1, date2);
      //json으로 변환
      
      
      out.print();
    
    }






%>