package com.example.OauthGatewayJwt.controller;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
@Slf4j
@Controller
public class GatewayRoutesPropertiesController {
    private static final Logger logger = LoggerFactory.getLogger(GatewayRoutesPropertiesController.class);

    private final RestTemplate restTemplate;

    @Autowired
    public GatewayRoutesPropertiesController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/save-routes")
    public ResponseEntity<String> saveGatewayRoutesProperties() {
        log.info("--------------------------------> move to controller");
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                "http://localhost:9001/actuator/gateway/routes",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String,Object>>>() {
                }
        );
        log.info("=============================================>"+response);

        List<Map<String, Object>> responseBody = response.getBody();
        if (responseBody != null) {
            log.info("move to response body"+responseBody);
            try (FileWriter fileWriter = new FileWriter("E://I5-Intern//Photo//config//gateway-dev.properties")) {
                Properties properties = new Properties();
                int count=0;

                for (Map<String, Object> entry : responseBody) {
                    log.info("--------------------------->entry" + entry);

                    String routeId = entry.get("route_id").toString();
                    String uri = entry.get("uri").toString();
                    String order = entry.get("order").toString();
                    String[] predicate = new String[]{entry.get("predicate").toString()};
                    String[] filter = new String[]{entry.get("filters").toString()};

                    logger.info("--------------------------->route::::"+routeId);
                    logger.info("---------------------------> uri::::"+uri);
                    logger.info("---------------------------> predicate:::"+ Arrays.toString(predicate));
                    logger.info("----------------------------> filter:::::"+ Arrays.toString(filter));

                    String s1 = "spring.cloud.gateway.routes[" + order + "].id= " + routeId;
                    String s2 = "spring.cloud.gateway.routes[" + order + "].uri= " + uri;
                    String s3 = "spring.cloud.gateway.routes[" + order + "].predicates[" + order + "]= " + Arrays.toString(predicate).replaceAll(", match trailing slash: true]", "")
                            .replaceAll(":", "=").replaceAll("\\[", "").replaceAll("\\]", "")
                            .replaceAll(" ", "").replaceAll("Paths", "Path");

                    String targetString = "AuthenticationFilter";
                    String s4 = null;
                    int startIndex = Arrays.toString(filter).indexOf(targetString);
                    String resultString = null;

                    if (startIndex != -1) {
                        int endIndex = startIndex + targetString.length();
                        resultString = Arrays.toString(filter).substring(startIndex, endIndex);
                        logger.info("----------------------->result string---------------->"+resultString);
                    } else if (startIndex== -1) {
                        resultString=" ";
                    }
                    s4 = "spring.cloud.gateway.routes[" + order + "].filters[" + count + "]= " + resultString;

                    log.info("----------------->file writer------------------------>" + fileWriter);
                    try{
                        if(!resultString.equals(" ")){
                            logger.info("file is not null");
                            fileWriter.write(s1 + System.lineSeparator() + s2 + System.lineSeparator()
                                    + s3 + System.lineSeparator() + s4 + System.lineSeparator() + System.lineSeparator());

                        }
                        else{
                            fileWriter.write(s1 + System.lineSeparator() + s2 + System.lineSeparator()
                                    + s3 + System.lineSeparator() + System.lineSeparator());
                        }

                    }catch (Exception e ){
                        System.out.println(" filter is null");
                    }












//                    if(startIndex != -1 && startIndex2 != -1){
//                        int endIndex = startIndex + targetString.length();
//                        resultString = Arrays.toString(filter).substring(startIndex, endIndex);
//                        logger.info("----------------------->result string 2---------------->"+resultString);
//
//                    }


//                    String result = Arrays.toString(filter).replaceFirst(targetString + ".*", targetString);

//                    String s4="spring.cloud.gateway.routes["+order+"].filters["+order+"]= "+Arrays.toString(filter).replaceAll("\\[","")
//                            .replaceAll("\\]","").replaceAll(", order = 0","").replaceAll("com.example.OauthGatewayJwt.config.","")
//                    ;
////                    String  sub4 = Arrays.toString(filter);
//                    String targetString1 = "AuthenticationFilter";
//                    char targetChar=',';
//                    int index = sub4.indexOf(targetString1);
//                    int index2=sub4.indexOf(targetChar);
//                    if (index != -1) {
//                        sub4="AuthenticationFilter";
//                    }
//                    else if(index != -1 && index2 != -1){
//
//                    }
//                    else {
//                        System.out.println("not found in the string");
//                    }
//                    String s5="spring.cloud.gateway.routes["+order+"].filters["+count+1+"]= "+sub4;
//
//                    String  sub4 = Arrays.toString(filter);
//                    char targetChar = ',';
//                    int index = sub4.indexOf(targetChar);
//                    if (index != -1) {
//
//                    } else {
//                        System.out.println("not found in the string");
//                    }






                    //                    properties.setProperty("spring.cloud.gateway.routes[0].id="+routeId+ System.lineSeparator()+"spring.cloud.gateway.routes[0].uri="+uri+ System.lineSeparator()+"spring.cloud.gateway.routes[0].predicates[0]="+ System.lineSeparator()+Arrays.toString(predicate)
                    //                            + System.lineSeparator()+"spring.cloud.gateway.routes[0].filters[0]=", Arrays.toString(filter));
                    //                properties.replace("[^a-zA-Z0-9-_\\.]","");

                    //                log.info("------------------>route======"+routeId);
                    //                log.info("-------------------->uri======"+uri);
                    //                log.info("-------------------->predicate======"+predicate);
                    //                log.info("--------------------->filter"+filter);
                    // Add more route properties as needed
                    //                    properties.store(fileWriter, "Gateway API Routes");
                }
            } catch (IOException e) {
                System.out.println("error save");
                return ResponseEntity.status(500).body("Error occurred while saving properties.");
            }


            return ResponseEntity.ok("Gateway routes properties saved successfully.");
        }

        return ResponseEntity.status(500).body("Failed to retrieve Gateway routes properties.");
    }
//    private String sanitizeKey(String key) {
//        return key.replaceAll("[^a-zA-Z0-9-_\\.]", " ");
//    }
}



