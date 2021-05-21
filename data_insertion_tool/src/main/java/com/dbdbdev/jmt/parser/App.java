package com.dbdbdev.jmt.parser;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dbdbdev.jmt.parser.reader.CSVReaderProvider;

public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("id pw");
            return;
        }
        var csvProvider = new CSVReaderProvider();

        Map<String, String> deptDivMap = new HashMap<>(); // 부서 구분
        Map<String, String[]> govofcDivMap = new HashMap<>(); // 관서
        Map<String, String> hgDeptDivMap = new HashMap<>(); // 실국
        Map<String, String> deptMap = new HashMap<>(); // 부서
        Map<String, String> accnutDivMap = new HashMap<>(); // 회계 구분
        Map<String, String> bizPlaceMap = new HashMap<>(); // 회계 구분

        Set<OrganizationChart> organizationSet = new HashSet<>();

        Map<String, List<String>> govo_hgMap = new HashMap<>();
        Map<String, List<String>> hg_deptMap = new HashMap<>();

        List<String[]> expendTrList = new ArrayList<>();

        csvProvider.getCSVFileLineStream().forEach(line -> {
            if (filterInputAPIData(line)) {
                accnutDivMap.putIfAbsent(line[1].strip(), line[2].strip());
                deptDivMap.putIfAbsent(line[3].strip(), line[4].strip());
                govofcDivMap.putIfAbsent(line[5].strip(), new String[] { line[6].strip(), line[3].strip() });
                hgDeptDivMap.putIfAbsent(line[7].strip(), line[8].strip());
                deptMap.putIfAbsent(line[9].strip(), line[10].strip());
                bizPlaceMap.putIfAbsent(line[21].strip(), line[20].strip());

                organizationSet.add(new OrganizationChart(line[3], line[5], line[7], line[9]));
                govo_hgMap.computeIfAbsent(line[5].strip(), k -> {
                    var list = new ArrayList<String>();
                    list.add(line[7]);
                    return list;
                });

                if (govo_hgMap.containsKey(line[5]) && !govo_hgMap.get(line[5]).contains(line[7])) {
                    govo_hgMap.get(line[5]).add(line[7]);
                }

                hg_deptMap.computeIfAbsent(line[7].strip(), k -> {
                    var list = new ArrayList<String>();
                    list.add(line[9]);
                    return list;
                });

                if (hg_deptMap.containsKey(line[7]) && !hg_deptMap.get(line[7]).contains(line[9])) {
                    hg_deptMap.get(line[7]).add(line[9]);
                }


                expendTrList.add(new String[] { line[0].strip(), line[1].strip(), line[9].strip(), line[19].strip(),
                        line[18].strip(), line[21].strip() });
            }
        });

        System.out.println("Parse Complete");

        try (var conn = DriverManager.getConnection("jdbc:postgresql://lanihome.iptime.org/ajoujmt", args[0], args[1]);
                PreparedStatement expendtrPstmt = conn
                        .prepareStatement("INSERT INTO \"ExpendtrExcut\" VALUES (?, ?, ?, ?, ?, ?)"); // 지출 내역 stmt
                PreparedStatement deptDivPstmt = conn.prepareStatement("INSERT INTO \"DeptDiv\" VALUES (?, ?)"); // 부서 구분
                                                                                                             // stmt
                PreparedStatement govofcDivPstmt = conn.prepareStatement("INSERT INTO \"GovofcDiv\" VALUES (?, ?, ?)");
                PreparedStatement hgDeptDivPstmt = conn.prepareStatement("INSERT INTO \"HgdeptDiv\" VALUES (?, ?)");
                PreparedStatement deptPstmt = conn.prepareStatement("INSERT INTO \"Dept\" VALUES (?, ?)");
                PreparedStatement accnutDivPstmt = conn.prepareStatement("INSERT INTO \"AccnutDiv\" VALUES (?, ?)");
                PreparedStatement bizPlacePstmt = conn.prepareStatement("INSERT INTO \"BizPlace\" VALUES (?, ?)");
                PreparedStatement govoHgPstmt = conn.prepareStatement("INSERT INTO \"GovofcDiv_HgdeptDiv\" VALUES (?, ?)");
                PreparedStatement hgdeptDeptPstmt = conn.prepareStatement("INSERT INTO \"HgdeptDiv_Dept\" VALUES (?, ?)");
                PreparedStatement organizationPstmt = conn.prepareStatement("INSERT INTO \"OrganizationChart\" VALUES (?, ?, ?, ?)");) {
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
                    hgDeptDivPstmt.setString(2, entry.getValue());
                    hgDeptDivPstmt.executeUpdate();
                    hgDeptDivPstmt.clearParameters();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            deptMap.entrySet().stream().forEach(entry -> {
                try {
                    deptPstmt.setString(1, entry.getKey());
                    deptPstmt.setString(2, entry.getValue());
                    deptPstmt.executeUpdate();
                    deptPstmt.clearParameters();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("dept  " + entry.getValue());
                }
            });

            govo_hgMap.entrySet().stream().forEach(entry -> {
                entry.getValue().stream().forEach(hgValue -> {
                    try {
                        govoHgPstmt.setString(1, entry.getKey());
                        govoHgPstmt.setString(2, hgValue);
                        govoHgPstmt.executeUpdate();
                        govoHgPstmt.clearParameters();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            });

            hg_deptMap.entrySet().stream().forEach(entry -> {
                entry.getValue().stream().forEach(deptValue -> {
                    try {
                        hgdeptDeptPstmt.setString(1, entry.getKey());
                        hgdeptDeptPstmt.setString(2, deptValue);
                        hgdeptDeptPstmt.executeUpdate();
                        hgdeptDeptPstmt.clearParameters();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            });

            bizPlaceMap.entrySet().stream().forEach(entry -> {
                try {
                    bizPlacePstmt.setString(1, entry.getKey());
                    bizPlacePstmt.setString(2, entry.getValue());
                    bizPlacePstmt.executeUpdate();
                    bizPlacePstmt.clearParameters();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("fds  " + entry.getKey());
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
                    expendtrPstmt.setString(3, tr[2]);
                    expendtrPstmt.setDate(4, java.sql.Date.valueOf(toDateStringForm(tr[3])));
                    expendtrPstmt.setInt(5, Integer.parseInt(tr[4]));
                    expendtrPstmt.setString(6, tr[5]);
                    expendtrPstmt.executeUpdate();
                    expendtrPstmt.clearParameters();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            organizationSet.stream().forEach(set -> {
                try {
                    organizationPstmt.setString(1, set.dd);
                    organizationPstmt.setString(2, set.gv);
                    organizationPstmt.setString(3, set.hg);
                    organizationPstmt.setString(4, set.dp);
                    organizationPstmt.executeUpdate();
                    organizationPstmt.clearParameters();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String toDateStringForm(String nonDateString) {
        return String.format("%s-%s-%s", nonDateString.subSequence(0, 4), nonDateString.subSequence(4, 6),
                nonDateString.subSequence(6, 8));
    }

    public static boolean filterInputAPIData(String[] line) {
        return (
            (!line[0].matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) &&    // 한글 행 제외
            (
                line[15].equals("20301") || // 20301 통계목코드 포함
                line[15].equals("20302") || // 20302
                line[15].equals("20303")    // 20303
            ) &&
            (!line[20].matches("\\*+")) &&  // 사업장 명 별표 제외
            (line[21].strip().length() == 10) &&    // 사업자번호 공란 제외
            (!line[21].strip().substring(3, 5).matches("81|86|87|88|83|84|85")) &&
            (!line[20].strip().matches("(마트)|(스토어)|(카페)"))
            );
    }
}
