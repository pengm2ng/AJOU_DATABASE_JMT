package dao.test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import dao.ExpendtrExcutI;
import entity.ExpendtrExcut;
import entity.Organization;
import entity.Place;

public class ExpendtrExcutTestDAO implements ExpendtrExcutI {


    private ExpendtrExcutTestDAO() { }

    public static ExpendtrExcutTestDAO getInstance() {
        return InstHolder.INSTANCE;
    }

    private static class InstHolder {
        public static final ExpendtrExcutTestDAO INSTANCE = new ExpendtrExcutTestDAO();
    }

    @Override
    public List<ExpendtrExcut> getPlaceTopTen(Organization deptDiv, Organization govofcDiv, Organization hgdeptDiv,
            Organization dept, Date startDate, Date endDate) {
        // TODO Auto-generated method stub
        
        return null;
    }

    @Override
    public List<ExpendtrExcut> getPlaceTopTen(Organization deptDiv, Organization govofcDiv, Organization hgdeptDiv,
            Organization dept) {
                Place place = new Place("0000", "dfdfd", 23);
                ExpendtrExcut exp = new ExpendtrExcut(deptDiv, govofcDiv, hgdeptDiv , dept, "dfdf",122, place);
                Place place1 = new Place("0001", "dfdfd", 23);
                ExpendtrExcut exp1 = new ExpendtrExcut(deptDiv, govofcDiv, hgdeptDiv , dept, "dfdf",122, place1);
                Place place2 = new Place("0002", "dfdfd", 23);
                ExpendtrExcut exp2 = new ExpendtrExcut(deptDiv, govofcDiv, hgdeptDiv , dept, "dfdf",122, place2);
                Place place3 = new Place("0003", "dfdfd", 23);
                ExpendtrExcut exp3 = new ExpendtrExcut(deptDiv, govofcDiv, hgdeptDiv , dept, "dfdf",122, place3);
                Place place4 = new Place("0004", "dfdfd", 23);
                ExpendtrExcut exp4 = new ExpendtrExcut(deptDiv, govofcDiv,  hgdeptDiv , dept, "dfdf",122, place4);
                List<ExpendtrExcut> list = new ArrayList<>();
                list.add(exp);
                list.add(exp1);
                list.add(exp2);
                list.add(exp3);
                list.add(exp4);


        return list;
    }

    
}
