package com.dbdbdev.jmt.parser;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dbdbdev.jmt.parser.reader.CSVReaderProvider;

public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("id pw");
            return;
        }
        var csvProvider = new CSVReaderProvider();


        Map<String, String> deptDivMap = new HashMap<>();   // 부서 구분
        Map<String, String[]> govofcDivMap = new HashMap<>(); // 관서
        Map<String, String[]> hgDeptDivMap = new HashMap<>();    // 실국
        Map<String, String[]> deptMap = new HashMap<>();      // 부서
        Map<String, String> accnutDivMap = new HashMap<>(); // 회계 구분
        Map<String, String> bizPlaceMap = new HashMap<>(); // 회계 구분

        List<String[]> expendTrList = new ArrayList<>();


        csvProvider.getCSVFileLineStream().forEach(line -> {
            if (!line[0].matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*") && line[15].equals("20301") && (!line[20].matches("\\*+")) && (!line[21].isEmpty())) {
                accnutDivMap.putIfAbsent(line[1], line[2]);
                deptDivMap.putIfAbsent(line[3], line[4]);
                govofcDivMap.putIfAbsent(line[5], new String[] {line[6], line[3]});
                hgDeptDivMap.putIfAbsent(line[7], new String[] {line[8], line[5]});
                deptMap.putIfAbsent(line[9], new String[] {line[10], line[7]});
                bizPlaceMap.putIfAbsent(line[21], line[20]);

                expendTrList.add(new String[] {line[0], line[1], line[9], line[19], line[18], line[21]});
            }
        });

        try (
            var conn = DriverManager.getConnection("jdbc:postgresql://lanihome.iptime.org", args[0], args[1]);
            PreparedStatement expendtrPstmt = conn.prepareStatement("INSERT INTO ExpendtrExcut VALUES (?, ?, ?, ?, ?, ?)");  // 지출 내역 stmt
            PreparedStatement deptDivPstmt = conn.prepareStatement("INSERT INTO DeptDiv VALUES (?, ?)"); // 부서 구분 stmt
            PreparedStatement govofcDivPstmt = conn.prepareStatement("INSERT INTO GovofcDiv VALUES (?, ?, ?)");
            PreparedStatement hgDeptDivPstmt = conn.prepareStatement("INSERT INTO HgdeptDiv VALUES (?, ?, ?)");
            PreparedStatement deptPstmt = conn.prepareStatement("INSERT INTO Dept VALUES (?, ?, ?)");
            PreparedStatement accnutDivPstmt = conn.prepareStatement("INSERT INTO AccnutDiv VALUES (?, ?)");
            PreparedStatement bizPlacePstmt = conn.prepareStatement("INSERT INTO bizPlace VALUES (?, ?)");
        ) {
            deptDivMap.entrySet().stream().forEach(entry -> {
                try {
                    deptDivPstmt.setString(1, entry.getKey());
                    deptDivPstmt.setString(2, entry.getValue());
                    deptDivPstmt.executeUpdate();
                    deptDivPstmt.clearParameters();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            govofcDivMap.entrySet().stream().forEach(entry -> {
                try {
                    govofcDivPstmt.setString(1, entry.getKey());
                    govofcDivPstmt.setString(2, entry.getValue()[1]);
                    govofcDivPstmt.setString(3, entry.getValue()[0]);
                    govofcDivPstmt.executeUpdate();
                    govofcDivPstmt.clearParameters();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            hgDeptDivMap.entrySet().stream().forEach(entry -> {
                try {
                    hgDeptDivPstmt.setString(1, entry.getKey());
                    hgDeptDivPstmt.setString(2, entry.getValue()[1]);
                    hgDeptDivPstmt.setString(3, entry.getValue()[0]);
                    hgDeptDivPstmt.executeUpdate();
                    hgDeptDivPstmt.clearParameters();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            deptMap.entrySet().stream().forEach(entry -> {
                try {
                    deptPstmt.setString(1, entry.getKey());
                    deptPstmt.setString(2, entry.getValue()[1]);
                    deptPstmt.setString(3, entry.getValue()[0]);
                    deptPstmt.executeUpdate();
                    deptPstmt.clearParameters();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            bizPlaceMap.entrySet().stream().forEach(entry -> {
                try {
                    bizPlacePstmt.setString(1, entry.getKey());
                    bizPlacePstmt.setString(2, entry.getValue());
                    bizPlacePstmt.executeUpdate();
                    bizPlacePstmt.clearParameters();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            accnutDivMap.entrySet().stream().forEach(entry -> {
                try {
                    accnutDivPstmt.setString(1, entry.getKey());
                    accnutDivPstmt.setString(2, entry.getValue());
                    accnutDivPstmt.executeUpdate();
                    accnutDivPstmt.clearParameters();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            expendTrList.stream().forEach(tr -> {
                try {
                    expendtrPstmt.setString(1, tr[0]);
                    expendtrPstmt.setString(2, tr[1]);
                    expendtrPstmt.setString(3, tr[3]);  // TODO INT PARSE
                    expendtrPstmt.setString(4, tr[4]);  // TODO DATE PARSE
                    expendtrPstmt.setString(5, tr[5]);
                    expendtrPstmt.executeUpdate();
                    expendtrPstmt.clearParameters();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
