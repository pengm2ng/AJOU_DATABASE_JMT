package rest;

import java.sql.Date;
import java.text.SimpleDateFormat;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import dao.PlaceDAOI;
import dao.impl.ExpendtrExcutDAO;
import dao.impl.OrganizationDAO;
import dao.impl.PlaceDAO;
import entity.Dept;
import entity.ExpendtrExcut;
import entity.GovofcDiv;
import entity.HgdeptDiv;
import entity.Organization;
import entity.Place;

@WebServlet("/rest")

public class Rest_Api {

  private Rest_Api() {

  }

  public String getCategory(String deptDiv, String govofcDiv, String hgdeptDiv, String dept) {
    OrganizationDAO organDAO = new OrganizationDAO();
    // Organization organizationDeptDiv = new Organization(null, deptDiv);
    // Organization organizationGovofcDiv = new Organization(null, govofcDiv);
    // Organization organizationHgdeptDiv = new Organization(null, hgdeptDiv);
    // Organization organizationDept = new Organization(null, dept);
    if()
    return null;
  }

  public boolean setRecommendation(String bizNo) {
    PlaceDAO placeDao = new PlaceDAO();
    if(bizNo.length()!=0){
      Place place = new Place(bizNo, null , 0);
      placeDao.updateLikeCount(place);
      return true;
    }else{
      return false;
    }
   
  }

  public String getTopTen(String deptDiv, String govofcDiv, String hgdeptDiv, String dept, String startDate,
      String endDate) {
    ExpendtrExcutDAO expendDAO = new ExpendtrExcutDAO();
    Organization organizationDeptDiv = new DeptDiv(null, deptDiv);
    Organization organizationGovofcDiv = new GovofcDiv(null, govofcDiv);
    Organization organizationHgdeptDiv = new HgdeptDiv(null, hgdeptDiv);
    Organization organizationDept = new Dept(null, dept);

    if (startDate.length() == 0 && endDate.length() == 0) {
      List<ExpendtrExcut> list = expendDAO.getPlaceTopTen(organizationDeptDiv, organizationGovofcDiv,
          organizationHgdeptDiv, organizationDept);
      //json으로 변환

    } else {
      Date date1 = Date.valueOf(startDate);
      Date date2 = Date.valueOf(endDate);
      List<ExpendtrExcut> list = expendDAO.getPlaceTopTen(organizationDeptDiv, organizationGovofcDiv,
          organizationHgdeptDiv, organizationDept, date1, date2);
      //json으로 변환
    }

    return null;

  }

  private static class Holder {
    public static final Rest_Api INSTANCE = new Rest_Api();
  }

  public static Rest_Api getInstance() {

    return Holder.INSTANCE;
  }

}
