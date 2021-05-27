package dao.impl;

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

    private ExpendtrExcutDAO() { }

    public static ExpendtrExcutDAO getInstance() {
        return InstHolder.INSTANCE;
    }

    private static class InstHolder {
        public static final ExpendtrExcutDAO INSTANCE = new ExpendtrExcutDAO();
    }

    @Override
    public List<ExpendtrExcut> getPlaceTopTen(Organization deptDiv, Organization govofcDiv, Organization hgdeptDiv,
            Organization dept, Date startDate, Date endDate) {

        /*
            select dept_div_nm,
                govofc_div_nm,
                hgdept_div_nm,
                dept_nm,
                paymnt_command_de,
                like_count,
                accnut_yy,
                place_nm,
                sum(expendtr_rsltn_amt) total_amt
            from "ExpendtrTotalExcut"
            group by dept_div_nm, govofc_div_nm, hgdept_div_nm, dept_nm, paymnt_command_de, like_count, accnut_yy, place_nm
            order by total_amt desc
            limit 10
         */

        List<ExpendtrExcut> data = new ArrayList<>();
        // try (var conn = ConnectionProvider.getJDBCConnection()) {
        //     PreparedStatement pstmt = conn.prepareStatement("select dept_div_nm,
        //     govofc_div_nm,
        //     hgdept_div_nm,
        //     dept_nm,
        //     paymnt_command_de,
        //     like_count,
        //     accnut_yy,
        //     place_nm,
        //     sum(expendtr_rsltn_amt) total_amt
        //     from "ExpendtrTotalExcut"
        //     group by dept_div_nm, govofc_div_nm, hgdept_div_nm, dept_nm, paymnt_command_de, like_count, accnut_yy, place_nm
        //     order by total_amt desc
        //     limit 10
        //     where paymnt_command_de >= ? and
        //             paymnt_command_de <= ?");
        //     pstmt.setDate(1, new Date(2020, 12, 5));
        //     pstmt.setDate(2, new Date(2021, 1, 5));
        //     ResultSet rs = pstmt.executeQuery();

        //     var place = new Place(rs.get, rs.get, 0)

        //     while (rs.next()) {
        //         Organization deptDivTemp = new DeptDiv(rs.getString(1), rs.getString(2));
        //         Organization govoTemp = new GovofcDiv(rs.getString(3), rs.getString(4));
        //         var expTemp = new ExpendtrExcut(deptDivTemp, govoTemp, hgdeptDiv, dept, null, 0, null);
        //         data.add(expTemp);
        //     }
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }
        return data;
    }

    @Override
    public List<ExpendtrExcut> getPlaceTopTen(Organization deptDiv, Organization govofcDiv, Organization hgdeptDiv,
            Organization dept) {
        // TODO Auto-generated method stub
        return null;
    }

}
