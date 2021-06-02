import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.Request;

import dao.impl.OrganizationDAO;
import entity.Organization;

public class memo {
    public void test() {
        try {
            // 상위개념이 선택되었을 때, 카테고리 선택
            // 상위가 null 이면 하위도 무조건 null
            String deptDiv = null;
            String govofcDiv = null;
            String hgdeptDiv = null;
            String dept = null;
            Organization organizationDept = null;
            Organization organizationGovofcDiv = null;
            Organization organizationDeptDiv = null;
            Organization organizationHgdeptDiv = null;

            if (request.getParameter("deptDiv") == null || request.getParameter("deptDiv").isEmpty()) {
                organizationDeptDiv = null;

            } else {
                deptDiv = request.getParameter("deptDiv");
                organizationDeptDiv = new DeptDiv(null, deptDiv);
            }

            if (request.getParameter("govofcDiv") == null || request.getParameter("govofcDiv").isEmpty()) {

                organizationGovofcDiv = null;
            } else {
                govofcDiv = request.getParameter("govofcDiv");
                organizationGovofcDiv = new GovofcDiv(null, govofcDiv);
            }

            if (request.getParameter("hgdeptDiv") == null || request.getParameter("hgdeptDiv").isEmpty()) {
                organizationHgdeptDiv = null;
            } else {
                hgdeptDiv = request.getParameter("hgdeptDiv");
                organizationHgdeptDiv = new HgdeptDiv(null, hgdeptDiv);
            }

            if (request.getParameter("dept") == null || request.getParameter("dept").isEmpty()) {
                organizationDept = null;
            } else {
                dept = request.getParameter("dept");
                organizationDept = new Dept(null, dept);
            }

            List<Organization> list = null;
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();

            if (deptDiv == null || deptDiv.isEmpty()) {

                list = OrganizationDAO.getInstance()
                        .getAllOrganization((Class<Organization>) organizationDeptDiv.getClass());

                for (int i = 0; i < list.size(); i++) {
                    jsonArray.add((list.get(i)).getOrganizationName());
                }

                jsonObject.put("deptDiv", jsonArray);

                System.out.println(jsonObject.toJSONString());

            } else {

                if (govofcDiv == null || govofcDiv.isEmpty()) {

                    list = OrganizationDAO.getInstance().getChildrenOf(organizationDeptDiv, organizationGovofcDiv,
                            organizationHgdeptDiv, organizationDept);
                    for (int i = 0; i < list.size(); i++) {
                        jsonArray.add((list.get(i)).getOrganizationName());
                    }

                    jsonObject.put("govofcDiv", jsonArray);

                    System.out.println(jsonObject.toJSONString());

                } else {

                    if (hgdeptDiv == null || hgdeptDiv.isEmpty()) {

                        list = OrganizationDAO.getInstance().getChildrenOf(organizationDeptDiv, organizationGovofcDiv,
                                organizationHgdeptDiv, organizationDept);
                        for (int i = 0; i < list.size(); i++) {
                            jsonArray.add((list.get(i)).getOrganizationName());
                        }

                        jsonObject.put("hgdeptDiv", jsonArray);

                        System.out.println(jsonObject.toJSONString());
                    } else {

                        list = OrganizationDAO.getInstance().getChildrenOf(organizationDeptDiv, organizationGovofcDiv,
                                organizationHgdeptDiv, organizationDept);
                        for (int i = 0; i < list.size(); i++) {
                            jsonArray.add((list.get(i)).getOrganizationName());
                        }
                        jsonObject.put("dept", jsonArray);
                        System.out.println(jsonObject.toJSONString());
                    }
                }
            }

            out.print(jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
