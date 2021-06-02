<%@ page language="java" contentType="application/json; charset=utf-8"%>
<%@page import="dao.impl.ExpendtrExcutDAO"%>
<%@page import="dao.test.ExpendtrExcutTestDAO"%>
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
        String deptDiv = request.getParameter("deptDiv");
        String govofcDiv = request.getParameter("govofcDiv");
        String hgdeptDiv = request.getParameter("hgdeptDiv");
        String dept = request.getParameter("dept");
        String startDate= null;
        String endDate = null;
        if(request.getParameter("startDate")==null){
             startDate = "";
        }else{
             startDate = request.getParameter("startDate");
        }

        if(request.getParameter("endDate")==null){
             endDate = "";
        }else{
             endDate = request.getParameter("endDate");
            
        }
        Organization organizationDeptDiv = new DeptDiv(null, deptDiv);
        Organization organizationGovofcDiv = new GovofcDiv(null, govofcDiv);
        Organization organizationHgdeptDiv = new HgdeptDiv(null, hgdeptDiv);
        Organization organizationDept = new Dept(null, dept);
        List<ExpendtrExcut> list = new ArrayList();

        if (startDate !="" && endDate != "") {
            Date date1 = Date.valueOf(startDate);
            Date date2 = Date.valueOf(endDate);
            list = ExpendtrExcutDAO.getInstance().getPlaceTopTen(organizationDeptDiv,
                    organizationGovofcDiv, organizationHgdeptDiv, organizationDept, date1, date2);
            // json으로 변환
        } else if(startDate == "" && endDate == ""){
             list = ExpendtrExcutDAO.getInstance().getPlaceTopTen(organizationDeptDiv,
                    organizationGovofcDiv, organizationHgdeptDiv, organizationDept);

        }else if(startDate != "" && endDate == ""){
            Date date1 = Date.valueOf(startDate);

             list = ExpendtrExcutDAO.getInstance().getPlaceTopTen(organizationDeptDiv,
                    organizationGovofcDiv, organizationHgdeptDiv, organizationDept,date1,null);
        }else if(startDate == "" && endDate != ""){
            Date date2 = Date.valueOf(endDate);
             list = ExpendtrExcutDAO.getInstance().getPlaceTopTen(organizationDeptDiv,
                    organizationGovofcDiv, organizationHgdeptDiv, organizationDept,null,date2);
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject restaurantJsonObject = new JSONObject();

        for (int i =0; i<list.size(); i++) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("placeName", list.get(i).getPlace().getPlaceName());
            jsonObject.put("bizNumber", list.get(i).getPlace().getBizNo());
            jsonObject.put("totalAmount", list.get(i).getTotalAmt());
            jsonObject.put("likeCount", list.get(i).getPlace().getLikeCount());
            jsonObject.put("address", KakaoMapProviderDAO.getInstance().findPlace(list.get(i).getPlace().getPlaceName()).get(0));
            jsonObject.put("category", KakaoMapProviderDAO.getInstance().findPlace(list.get(i).getPlace().getPlaceName()).get(1));
            jsonArray.add(jsonObject);
        }
        restaurantJsonObject.put("restaurant", jsonArray);
        System.out.println(restaurantJsonObject.toJSONString());
        out.print(restaurantJsonObject);
}catch(Exception e){
    e.printStackTrace();
}
%>
