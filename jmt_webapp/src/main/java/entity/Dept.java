package entity;

/**
 * 부서 클래스
 */
public class Dept extends Organization {

    public Dept(String organizationCode, String organizationName) {
        super(organizationCode, organizationName);
        if (super.organizationCode != null && super.organizationCode.length() != 7) {
            throw new IllegalArgumentException();
        }
    }

}
