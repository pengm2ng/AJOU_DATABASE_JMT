package entity;

import java.sql.Date;

public class ExpendtrExcut {
    private Organization deptDiv;
    private Organization govofcDiv;
    private Organization hgdeptDiv;
    private Organization dept;
    private Date startDate;
    private Date endDate;
    private long totalAmt;
    private Place place;

    public ExpendtrExcut(Organization deptDiv, Organization govofcDiv, Organization hgdeptDiv, Organization dept,
            Date startDate, Date endDate, long totalAmt, Place place) {

        if (!deptDiv.getClass().equals(DeptDiv.class)) {
            throw new RuntimeException("deptDiv argument has to be DeptDiv type");
        }
        if (!govofcDiv.getClass().equals(GovofcDiv.class)) {
            throw new RuntimeException("deptDiv argument has to be DeptDiv type");
        }
        if (!hgdeptDiv.getClass().equals(HgdeptDiv.class)) {
            throw new RuntimeException("deptDiv argument has to be DeptDiv type");
        }
        if (!dept.getClass().equals(Dept.class)) {
            throw new RuntimeException("deptDiv argument has to be DeptDiv type");
        }

        this.deptDiv = deptDiv;
        this.govofcDiv = govofcDiv;
        this.hgdeptDiv = hgdeptDiv;
        this.dept = dept;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmt = totalAmt;
        this.place = place;
    }

    public ExpendtrExcut(Date startDate, Date endDate, long totalAmt, Place place) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmt = totalAmt;
        this.place = place;
    }

    public Organization getDeptDiv() {
        return this.deptDiv;
    }

    public Organization getGovofcDiv() {
        return this.govofcDiv;
    }

    public Organization getHgdeptDiv() {
        return this.hgdeptDiv;
    }

    public Organization getDept() {
        return this.dept;
    }

    public Date startDate() {
        return this.startDate;
    }

    public Date endDate(){
        return this.endDate;
    }

    /**
     * 총 사용 금액 가져오기
     * @return 총 사용 금액
     */
    public long getTotalAmt() {
        return this.totalAmt;
    }

    public Place getPlace() {
        return this.place;
    }

}
