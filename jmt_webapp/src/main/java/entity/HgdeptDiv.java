package entity;

/**
 * 실국 클래스
 */
public class HgdeptDiv extends Organization {

    public HgdeptDiv(String organizationCode, String organizationName) {
        super(organizationCode, organizationName);
        if (super.organizationCode != null && super.organizationCode.length() != 4) {
            throw new IllegalArgumentException();
        }
    }
    
}
