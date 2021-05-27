package rest;

import javax.servlet.annotation.WebServlet;

@WebServlet("/rest")

public class Rest_Api {

  private Rest_Api() {

  }

  public String getCategory(String deptDiv, String govofcDiv, String hgdeptDiv, String dept) {

    return null;
  }

  public boolean setRecommendation(String bizNo) {

    return false;
  }

  public String getTopeTen(String deptDiv, String govofcDiv, String hgdeptDiv, String dept, String startDate,
      String endDate) {

    return null;

  }

  private static class Holder {
    public static final Rest_Api INSTANCE = new Rest_Api();
  }

  public static Rest_Api getInstance() {

    return Holder.INSTANCE;
  }

}
