package kr.aipeppers.pep.core.util.noti;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.aipeppers.pep.core.data.Box;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PushNotification {

	public static void pushNotification(List<Box> boxList) {
//        try {
//            // 1. JSON 데이터를 String으로 변환합니다.
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writeValueAsString(boxList);
//            log.debug("json: {}", json);
//            // 2. HttpURLConnection 객체를 만들고 요청을 설정합니다.
//            URL url = new URL("https://api.aimeplz.com:50443/apis/callPushNotification");
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("POST");
//            con.setRequestProperty("Content-Type", "application/json");
//            con.setRequestProperty("Catch-control", "no-cache");
//            con.setRequestProperty("Api-key", "156c4675-9608-4591-2022-08161");
//
//            con.setDoOutput(true);
//
//            // 3. 요청 본문에 JSON 데이터를 쓰고, 요청을 보냅니다.
//            OutputStream os = con.getOutputStream();
//            os.write(json.getBytes());
//            log.debug("os: {}", os);
//            os.flush();
//            os.close();
//
//            // 4. 응답을 처리합니다.
//            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            StringBuilder response = new StringBuilder();
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            System.out.println(response.toString());
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//        }
    }
}

