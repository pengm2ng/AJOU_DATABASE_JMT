import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.Request;

import dao.impl.OrganizationDAO;
import entity.Organization;

public class memo {
    @Test
    public void test() {
        String deptDiv = request.getParameter("deptDiv");
        String govofcDiv = request.getParameter("govofcDiv");
        String hgdeptDiv = request.getParameter("hgdeptDiv");
        String dept = request.getParameter("dept");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        Organization organizationDeptDiv = new DeptDiv(null, deptDiv);
        Organization organizationGovofcDiv = new GovofcDiv(null, govofcDiv);
        Organization organizationHgdeptDiv = new HgdeptDiv(null, hgdeptDiv);
        Organization organizationDept = new Dept(null, dept);

        if (startDate.length() == 0 && endDate.length() == 0) {
            List<ExpendtrExcut> list = ExpendtrExcutDAO.getInstance().getPlaceTopTen(organizationDeptDiv,
                    organizationGovofcDiv, organizationHgdeptDiv, organizationDept);
            // json으로 변환

        } else {
            Date date1 = Date.valueOf(startDate);
            Date date2 = Date.valueOf(endDate);
            List<ExpendtrExcut> list = ExpendtrExcutDAO.getInstance().getPlaceTopTen(organizationDeptDiv,
                    organizationGovofcDiv, organizationHgdeptDiv, organizationDept, date1, date2);
            // json으로 변환

        }

        JSONArray jsonArray = new JSONArray();
        JSONObject restaurantJsonObject = new JSONObject();

        for (int i = 0; i < list.size(); i++) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("placeName", ((Place) ((ExpendtrExcut) list.get(i)).getPlace()).getPlaceName);
            jsonObject.put("bizNumber", ((Place) ((ExpendtrExcut) list.get(i)).getPlace()).getBizNo());
            jsonObject.put("likeCount", ((Place) ((ExpendtrExcut) list.get(i)).getPlace()).getLikeCount());
            jsonArray.add(jsonObject);
        }

        restaurantJsonObject.put("restaurant", jsonArray);
        System.out.println(restaurantJsonObject.toJSONString());

    }
}
