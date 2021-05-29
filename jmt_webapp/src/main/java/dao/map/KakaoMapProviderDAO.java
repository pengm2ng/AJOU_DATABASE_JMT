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

    public List findPlace(String placeName) {
        
        try {
            File file = new File("./resources/kakaoKey.txt");
            FileInputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String key = br.readLine();
            br.close();
            URL url = new URL("https://dapi.kakao.com/v2/local/search/keyword.json?page=1&size=1&sort=accuracy&query="
                    + URLEncoder.encode(placeName, "utf-8"));
            System.out.println(url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", key);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            System.out.println(conn.getRequestProperties());
            StringBuilder sb = new StringBuilder();
            System.out.println(conn.getResponseCode());
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Stream을 처리해줘야 하는 귀찮음이 있음.
                BufferedReader br2 = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line;
                while ((line = br2.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br2.close();
                System.out.println("" + sb.toString());
            } else {
                System.out.println(conn.getResponseMessage());
            }

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
            JSONArray jsonArray = (JSONArray) jsonObject.get("documents");
            JSONObject jsonObject2 = (JSONObject) jsonArray.get(0);
            List list = new ArrayList<>();
            list.add(jsonObject2.get("address_name"));
            list.add(jsonObject2.get("category_name"));
            list.add(jsonObject2.get("x"));
            list.add(jsonObject2.get("y"));
            conn.disconnect();
            return list;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
