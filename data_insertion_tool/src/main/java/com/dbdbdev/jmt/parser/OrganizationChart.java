package com.dbdbdev.jmt.parser;

public class OrganizationChart {
    public String dd, gv, hg, dp;

    public OrganizationChart(String dd, String gv, String hg, String dp) {
        this.dd = dd;
        this.gv = gv;
        this.hg = hg;
        this.dp = dp;
    }

    @Override
    public int hashCode() {
        return dd.chars().sum() + gv.chars().sum() + hg.chars().sum() + dp.chars().sum();
    }

    @Override
    public boolean equals(Object obj) {
        OrganizationChart self = (OrganizationChart) obj;

        return self.dd.equals(this.dd) && self.gv.equals(this.gv) && self.hg.equals(this.hg) && self.dp.equals(this.dp);
    }
}
