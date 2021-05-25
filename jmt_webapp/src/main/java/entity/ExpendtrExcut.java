package entity;

public class ExpendtrExcut {
    private Organization deptDiv;
    private Organization govofcDiv;
    private Organization hgdeptDiv;
    private Organization dept;
    private String accnutYY;
    private int totalAmt;
    private Place place;

    public ExpendtrExcut(Organization deptDiv, Organization govofcDiv, Organization hgdeptDiv, Organization dept, String accnutYY, int totalAmt, Place place) {
        this.deptDiv = deptDiv;
        this.govofcDiv = govofcDiv;
        this.hgdeptDiv = hgdeptDiv;
        this.dept = dept;
        this.accnutYY = accnutYY;
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

    public String getAccnutYY() {
        return this.accnutYY;
    }

    /**
     * 총 사용 금액 가져오기
     * @return 총 사용 금액
     */
    public int getTotalAmt() {
        return this.totalAmt;
    }

    public Place getPlace() {
        return this.place;
    }

}
