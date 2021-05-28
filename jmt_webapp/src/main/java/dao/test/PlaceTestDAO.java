package dao.test;

import dao.PlaceDAOI;
import entity.Place;

public class PlaceTestDAO implements PlaceDAOI{

    private PlaceTestDAO() { }

    public static PlaceTestDAO getInstance() {
        return InstHolder.INSTANCE;
    }

    private static class InstHolder {
        public static final PlaceTestDAO INSTANCE = new PlaceTestDAO();
    }


    @Override
    public void updateLikeCount(Place place) {
        // TODO Auto-generated method stub
        System.out.println(place.getBizNo());
        System.out.println(place.getPlaceName());
        System.out.println(place.getLikeCount());
    
    }
    
    
}
