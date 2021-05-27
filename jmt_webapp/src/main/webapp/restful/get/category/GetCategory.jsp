 <%@ page language="java" contentType="application/json; charset=utf-8"%>
<%@page import="java.dao.impl.OrganizationDAO"%>
<%

 // 상위개념이 선택되었을 때, 카테고리 선택
        // 상위가 null 이면 하위도 무조건 null
        String deptDiv = request.getParameter("deptDiv");
        String govofcDiv = request.getParameter("govofcDiv");
        String hgdeptDiv = request.getParameter("hgdeptDiv");
        String dept = request.getParameter("dept");
        Organization organizationDeptDiv = new DeptDiv(null, deptDiv);
        Organization organizationGovofcDiv = new GovofcDiv(null, govofcDiv);
        Organization organizationHgdeptDiv = new HgdeptDiv(null, hgdeptDiv);
        Organization organizationDept = new Dept(null, dept);
        List<Organization> list = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        if (deptDiv.length() == 0) {

            list = OrganizationDAO.getInstance()
                    .getAllOrganization((Class<Organization>) organizationDeptDiv.getClass());

            for (int i = 0; i < list.size(); i++) {
                jsonArray.add(((Organization)list.get(i)).getOrganizationName());
            }

            jsonObject.put("deptDiv", jsonArray);

            System.out.println(jsonObject.toJSONString());

        } else if (deptDiv.length() != 0) {

            if (govofcDiv.length() == 0) {

                list = OrganizationDAO.getInstance().getChildrenOf(organizationGovofcDiv);
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.add(((Organization)list.get(i)).getOrganizationName());
                }

                jsonObject.put("govofcDiv", jsonArray);

                System.out.println(jsonObject.toJSONString());

            } else if (govofcDiv.length() != 0) {

                if (hgdeptDiv.length() == 0) {

                    list = OrganizationDAO.getInstance().getChildrenOf(organizationHgdeptDiv);
                    for (int i = 0; i < list.size(); i++) {
                        jsonArray.add(((Organization)list.get(i)).getOrganizationName());
                    }

                    jsonObject.put("hgdeptDiv", jsonArray);

                    System.out.println(jsonObject.toJSONString());
                } else if (hgdeptDiv.length() != 0) {

                    list = OrganizationDAO.getInstance().getChildrenOf(organizationDept);
                    for (int i = 0; i < list.size(); i++) {
                        jsonArray.add(((Organization)list.get(i)).getOrganizationName());
                    }

                    jsonObject.put("dept", jsonArray);

                    System.out.println(jsonObject.toJSONString());
                }

            }

        }

        // json 변환

        out.print(jsonObject);


%>