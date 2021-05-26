package dao.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ExpendtrExcutI;
import entity.ExpendtrExcut;
import entity.Organization;
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
        try (var conn = ConnectionProvider.getJDBCConnection()) {
            conn.prepareStatement("SELECT * ????");
            data.add(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public List<ExpendtrExcut> getPlaceTopTen(Organization deptDiv, Organization govofcDiv, Organization hgdeptDiv,
            Organization dept) {
        // TODO Auto-generated method stub
        return null;
    }

}
