package entity;

/**
 * 관서 클래스
 */
public class GovofcDiv extends Organization {

    public GovofcDiv(String organizationCode, String organizationName) {
        super(organizationCode, organizationName);
        if (super.organizationCode.length() != 4) {
            throw new IllegalArgumentException();
        }
    }
    
}
