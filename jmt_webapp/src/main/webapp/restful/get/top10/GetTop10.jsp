<%@ page language="java" contentType="application/json; charset=utf-8"%>
<%@page import="dao.impl.ExpendtrExcutDAO"%>
<%@page import="entity.Organization"%>
<%@page import="entity.DeptDiv"%>
<%@page import="entity.GovofcDiv"%>
<%@page import="entity.HgdeptDiv"%>
<%@page import="entity.Dept"%>
<%@page import="entity.ExpendtrExcut"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.sql.Date"%>
<%@page import="entity.Place"%>
<%@page import="dao.map.KakaoMapProviderDAO"%>
<%
    response.setHeader("Access-Control-Allow-Origin", "*");
try{
        Organization organizationDept = null;
        Organization organizationGovofcDiv = null;
        Organization organizationDeptDiv = null;
        Organization organizationHgdeptDiv = null;
        Date startDate = null;
        Date endDate = null;
        if (request.getParameter("deptDiv") == null && request.getParameter("deptDiv").isEmpty()) {
            organizationDeptDiv = null;

        } else {
            deptDiv = request.getParameter("deptDiv");
            organizationDeptDiv = new DeptDiv(null, deptDiv);
        }

        if (request.getParameter("govofcDiv") == null && request.getParameter("govofcDiv").isEmpty()) {
            organizationGovofcDiv = null;
        } else {
            govofcDiv = request.getParameter("govofcDiv");
            organizationGovofcDiv = new GovofcDiv(null, govofcDiv);
        }

        if (request.getParameter("hgdeptDiv") == null && request.getParameter("hgdeptDiv").isEmpty()) {
            organizationHgdeptDiv = null;
        } else {
            hgdeptDiv = request.getParameter("hgdeptDiv");
            organizationHgdeptDiv = new HgdeptDiv(null, hgdeptDiv);
        }

        if (request.getParameter("dept") == null && request.getParameter("dept").isEmpty()) {
            organizationDept = null;
        } else {
            dept = request.getParameter("dept");
            organizationDept = new Dept(null, dept);
        }

        if(request.getParameter("startDate")==null && request.getParameter("startDate").isEmpty()){
             startDate = null;
        }else{
             startDate = new Date(request.getParameter("startDate"));
        }

        if(request.getParameter("endDate")==null && request.getParameter("endDate").isEmpty()){
             endDate = null;
        }else{
            endDate = new Date(request.getParameter("endDate"));
            
        }
        
        List<ExpendtrExcut> list = new ArrayList();

        if (startDate == null && endDate == null) {
           
            list = ExpendtrExcutDAO.getInstance().getPlaceTopTen(organizationDeptDiv,
                    organizationGovofcDiv, organizationHgdeptDiv, organizationDept);
            // json으로 변환
        } else {
             list = ExpendtrExcutDAO.getInstance().getPlaceTopTen(organizationDeptDiv,
                    organizationGovofcDiv, organizationHgdeptDiv, organizationDept, startDate, endDate);

        }
        JSONArray jsonArray = new JSONArray();
        JSONObject restaurantJsonObject = new JSONObject();

        for (ExpendtrExcut expendtrExcut: list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("placeName", expendtrExcut.getPlace().getPlaceName());
            jsonObject.put("bizNumber", expendtrExcut.getPlace().getBizNo());
            jsonObject.put("totalAmount", expendtrExcut.getTotalAmt());
            jsonObject.put("likeCount", expendtrExcut.getPlace().getLikeCount());
            jsonObject.put("address", KakaoMapProviderDAO.getInstance().findPlace(expendtrExcut.getPlace().getPlaceName()).get(0));
            jsonObject.put("category", KakaoMapProviderDAO.getInstance().findPlace(expendtrExcut.getPlace().getPlaceName()).get(1));
            jsonArray.add(jsonObject);
        }
        restaurantJsonObject.put("restaurant", jsonArray);
        System.out.println(restaurantJsonObject.toJSONString());
        out.print(restaurantJsonObject);
    } catch(Exception e) {
    e.printStackTrace();
    }
%>
