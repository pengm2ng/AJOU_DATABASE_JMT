package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.PlaceDAOI;
import entity.Place;
import jdbc.ConnectionProvider;

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

        try (Connection conn = ConnectionProvider.getJDBCConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                "update \"ExpendtrTotalExcut\" set like_count=like_count+1 where place_nm='?';");
            pstmt.setString(1, "");
            ResultSet rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
