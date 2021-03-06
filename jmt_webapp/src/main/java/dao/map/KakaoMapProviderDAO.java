package dao.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class KakaoMapProviderDAO {

    private KakaoMapProviderDAO() {
    }

    public static KakaoMapProviderDAO getInstance() {
        return InstHolder.INSTANCE;
    }

    private static class InstHolder {
        public static final KakaoMapProviderDAO INSTANCE = new KakaoMapProviderDAO();
    }

    public List<String> findPlace(String placeName) {

        File file = new File("webapps/ROOT/WEB-INF/resources/kakaoKey.txt");
        try (BufferedReader inFiles = new BufferedReader(
                new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "UTF-8"));) {

            String key = inFiles.readLine();
            URL url = new URL("https://dapi.kakao.com/v2/local/search/keyword.json?page=1&size=1&sort=accuracy&query="
                    + URLEncoder.encode(placeName, "utf-8"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", key);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            StringBuilder sb = new StringBuilder();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Stream을 처리해줘야 하는 귀찮음이 있음.
                BufferedReader br2 = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = br2.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br2.close();

            }

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
            JSONArray jsonArray = (JSONArray) jsonObject.get("documents");
            if (jsonArray.isEmpty()) {
                List<String> list = new ArrayList<>();
                list.add("찾을 수 없음");
                list.add("찾을 수 없음");
                return list;
            } else {

                JSONObject jsonObject2 = (JSONObject) jsonArray.get(0);
                List<String> list = new ArrayList<>();

                String[] array = jsonObject2.get("category_name").toString().split("> ");

                list.add(jsonObject2.get("address_name").toString());
                list.add((array[array.length - 1]));
                list.add(jsonObject2.get("x").toString());
                list.add(jsonObject2.get("y").toString());
                conn.disconnect();
                return list;
            }
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        } catch (ParseException e) {

            e.printStackTrace();
        }

        return new ArrayList<>();
    }

}
