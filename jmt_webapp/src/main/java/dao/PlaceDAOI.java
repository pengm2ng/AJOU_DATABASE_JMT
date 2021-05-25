package dao;

import entity.Place;

public interface PlaceDAOI {

    /**
     * 해당 음식점 좋아요 수 1개 증가
     * 
     * @param place 음식점
     */
    public void updateLikeCount(Place place);
}
