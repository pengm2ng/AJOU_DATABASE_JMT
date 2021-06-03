package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ExpendtrExcutI;
import entity.ExpendtrExcut;
import entity.GovofcDiv;
import entity.Organization;
import entity.Place;
import jdbc.ConnectionProvider;

public class ExpendtrExcutDAO implements ExpendtrExcutI {
    private static final String EXPENDTREXCUT_QUERY1 ="select place_nm, sum(expendtr_rsltn_amt) total_amt, coalesce(like_count,0)\n"
    + "from \"ExpendtrTotalExcut\"\n"
    + "where dept_div_nm like '?' and govofc_div_nm like '?' and hgdept_div_nm like '?' and dept_nm like '?'\n"
    + "and paymnt_command_de >= '?'' and paymnt_command_de <= '?'"
    + "group by place_nm, like_count order by total_amt desc limit 10"; 

    private static final String EXPENDTREXCUT_QUERY2 ="select place_nm, sum(expendtr_rsltn_amt) total_amt, coalesce(like_count,0)\n"
    + "from \"ExpendtrTotalExcut\"\n"
    + "where dept_div_nm like '?' and govofc_div_nm like '?' and hgdept_div_nm like '?' and dept_nm like '?'\n"
    + "group by place_nm, like_count order by total_amt desc limit 10"; 

private ExpendtrExcutDAO(){ }

public static ExpendtrExcutDAO getInstance() {
    return InstHolder.INSTANCE;
}

private static class InstHolder {
    public static final ExpendtrExcutDAO INSTANCE = new ExpendtrExcutDAO();
}

// 범위를 지정했을때
@Override
public List<ExpendtrExcut> getPlaceTopTen(Organization deptDiv, Organization govofcDiv, Organization hgdeptDiv,
        Organization dept, Date startDate, Date endDate) {

    String deptDivName = deptDiv == null ? "%" : deptDiv.getOrganizationName();
    String govofcDivName = govofcDiv == null ? "%" : govofcDiv.getOrganizationName();
    String hgdeptDivName = hgdeptDiv == null ? "%" : hgdeptDiv.getOrganizationName();
    String deptName = dept == null ? "%" : dept.getOrganizationName();


    List<ExpendtrExcut> placeTopTen = new ArrayList<>();
    try (Connection conn = ConnectionProvider.getJDBCConnection()) {
        PreparedStatement pstmt = conn.prepareStatement(EXPENDTREXCUT_QUERY1);

        pstmt.setString(1, deptDivName);
        pstmt.setString(2, govofcDivName);
        pstmt.setString(3, hgdeptDivName);
        pstmt.setString(4, deptName);
        pstmt.setDate(5, startDate);
        pstmt.setDate(6, endDate);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            pstmt = conn.prepareStatement("select * from BizPlace where place_nm='?");
            pstmt.setString(1, rs.getString("place_nm"));
            ResultSet rsPlace = pstmt.executeQuery();
            Place place = new Place(rsPlace.getString("biz_reg_no"), rs.getString("place_nm"), rs.getInt("like_count"));
            placeTopTen.add(new ExpendtrExcut(deptDiv, govofcDiv, hgdeptDiv, dept, startDate, endDate, rs.getInt("total_amt"), place));
        }

        pstmt.close();
        rs.close();

        return placeTopTen;
    }catch (SQLException e) {
        e.printStackTrace();
    }
    return new ArrayList<>();
}

//범위를 지정하지 않았을때
@Override
public List<ExpendtrExcut> getPlaceTopTen(Organization deptDiv, Organization govofcDiv, Organization hgdeptDiv,
        Organization dept) {
    
    String deptDivName = deptDiv == null ? "%" : deptDiv.getOrganizationName();
    String govofcDivName = govofcDiv == null ? "%" : govofcDiv.getOrganizationName();
    String hgdeptDivName = hgdeptDiv == null ? "%" : hgdeptDiv.getOrganizationName();
    String deptName = dept == null ? "%" : dept.getOrganizationName();

    List<ExpendtrExcut> placeTopTen = new ArrayList<>();
    try (Connection conn = ConnectionProvider.getJDBCConnection()) {
        PreparedStatement pstmt = conn.prepareStatement(EXPENDTREXCUT_QUERY2);

        pstmt.setString(1, deptDivName);
        pstmt.setString(2, govofcDivName);
        pstmt.setString(3, hgdeptDivName);
        pstmt.setString(4, deptName);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            pstmt = conn.prepareStatement("select * from BizPlace where place_nm='?");
            pstmt.setString(1, rs.getString("place_nm"));
            ResultSet rsPlace = pstmt.executeQuery();
            Place place = new Place(rsPlace.getString("biz_reg_no"), rs.getString("place_nm"), rs.getInt("like_count"));
            placeTopTen.add(new ExpendtrExcut(deptDiv, govofcDiv, hgdeptDiv, dept, null, null, rs.getInt("total_amt"), place));
        }

        pstmt.close();
        rs.close();

        return placeTopTen;
    }catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}
}
