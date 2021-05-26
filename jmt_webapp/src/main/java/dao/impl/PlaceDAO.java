package dao.impl;

import dao.PlaceDAOI;
import entity.Place;

public class PlaceDAO implements PlaceDAOI {

    private PlaceDAO() { }

    public static PlaceDAO getInstance() {
        return InstHolder.INSTANCE;
    }

    private static class InstHolder {
        public static final PlaceDAO INSTANCE = new PlaceDAO();
    }

    @Override
    public void updateLikeCount(Place place) {
        // TODO Auto-generated method stub

    }

}
