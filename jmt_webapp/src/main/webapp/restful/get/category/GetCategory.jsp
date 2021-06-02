<%@ page language="java" contentType="application/json; charset=utf-8"%>
<%@page import="dao.test.OrganizationTestDAO"%>
<%@page import="dao.impl.OrganizationDAO"%>
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
<%
    response.setHeader("Access-Control-Allow-Origin", "*");
    try{
     // 상위개념이 선택되었을 때, 카테고리 선택
            // 상위가 null 이면 하위도 무조건 null
            String deptDiv = null;
            String govofcDiv = null;
            String hgdeptDiv = null;
            String dept = null;
            Organization organizationDept;
            Organization organizationGovofcDiv;
            Organization organizationDeptDiv;
            Organization organizationHgdeptDiv;

            if (request.getParameter("deptDiv") == null) {
                 deptDiv = "";
               organizationDeptDiv = new DeptDiv(null, null);

            } else {
                 deptDiv = request.getParameter("deptDiv");
                 organizationDeptDiv = new DeptDiv(null, deptDiv);


            }

            if (request.getParameter("govofcDiv") == null) {
                 govofcDiv = "";
                organizationGovofcDiv = new GovofcDiv(null, null);
            } else {
                 govofcDiv = request.getParameter("govofcDiv");
                 organizationGovofcDiv = new GovofcDiv(null, govofcDiv);
            }


            if (request.getParameter("hgdeptDiv") == null) {
                 hgdeptDiv = "";
                organizationHgdeptDiv = new HgdeptDiv(null, null);
            } else {
                 hgdeptDiv = request.getParameter("hgdeptDiv");
               organizationHgdeptDiv = new HgdeptDiv(null, hgdeptDiv);
            }

            if (request.getParameter("dept") == null) {
                 dept = "";
                organizationDept = new Dept(null, null);
            } else {
                dept = request.getParameter("dept");
                organizationDept = new Dept(null, dept);
            }

            List<Organization> list = new ArrayList<>();
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();

            if (deptDiv.equals("")) {

                list = OrganizationDAO.getInstance()
                        .getAllOrganization((Class<Organization>) organizationDeptDiv.getClass());

                for (int i = 0; i < list.size(); i++) {
                    jsonArray.add((list.get(i)).getOrganizationName());
                }

                jsonObject.put("deptDiv", jsonArray);

                System.out.println(jsonObject.toJSONString());

            } else if (deptDiv != "") {

                if (govofcDiv == "") {

                    list = OrganizationDAO.getInstance().getChildrenOf(organizationDeptDiv,organizationGovofcDiv,organizationHgdeptDiv,organizationDept);
                    for (int i = 0; i < list.size(); i++) {
                        jsonArray.add((list.get(i)).getOrganizationName());
                    }

                    jsonObject.put("govofcDiv", jsonArray);

                    System.out.println(jsonObject.toJSONString());

                } else if (govofcDiv != "") {

                    if (hgdeptDiv == "") {

                        list = OrganizationDAO.getInstance().getChildrenOf(organizationDeptDiv,organizationGovofcDiv,organizationHgdeptDiv,organizationDept);
                        for (int i = 0; i < list.size(); i++) {
                            jsonArray.add((list.get(i)).getOrganizationName());
                        }

                        jsonObject.put("hgdeptDiv", jsonArray);

                        System.out.println(jsonObject.toJSONString());
                    } else if (hgdeptDiv != "") {

                        list = OrganizationDAO.getInstance().getChildrenOf(organizationDeptDiv,organizationGovofcDiv,organizationHgdeptDiv,organizationDept);
                        for (int i = 0; i < list.size(); i++) {
                            jsonArray.add((list.get(i)).getOrganizationName());
                        }

                        jsonObject.put("dept", jsonArray);

                        System.out.println(jsonObject.toJSONString());
                    }

                }

            }

            // json 변환

            out.print(jsonObject);

    }catch(Exception e){
        e.printStackTrace();

    }
%>
