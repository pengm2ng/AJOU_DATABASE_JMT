package entity;

public class Place {
    /**
     * 사업자 번호
     */
    private String bizNo;
    /**
     * 장소명
     */
    private String placeName;
    /**
     * 좋아요 수
     */
    private int likeCount;

    public Place(String bizNo, String placeName, int likeCount) {
        this.bizNo = bizNo;
        this.placeName = placeName;
        this.likeCount = likeCount;
    }

    public String getBizNo() {
        return this.bizNo;
    }

    public String getPlaceName() {
        return this.placeName;
    }

    public int getLikeCount() {
        return this.likeCount;
    }

}
