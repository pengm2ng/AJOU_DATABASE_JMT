package entity;

/**
 * 부서 구분 클래스
 */
public class DeptDiv extends Organization {

    public DeptDiv(String organizationCode, String organizationName) {
        super(organizationCode, organizationName);
        if (super.organizationCode != null && super.organizationCode.length() != 2) {
            throw new IllegalArgumentException();
        }
    }

}
