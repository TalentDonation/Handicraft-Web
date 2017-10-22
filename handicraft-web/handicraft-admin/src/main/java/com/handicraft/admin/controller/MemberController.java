package com.handicraft.admin.controller;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.handicraft.admin.util.GoogleOauth;
import com.handicraft.admin.util.GoogleOauthValue;
import com.handicraft.core.dto.Excels.Excel;
import com.handicraft.core.service.Excels.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@Slf4j
public class MemberController {

    @Value("${USERID}")
    private String userId;

    private static String range;

    @Autowired
    ExcelService sheetsService;


    @RequestMapping(value = "/member/redirect/auth", method = RequestMethod.GET)
    public ModelAndView googleOauthRedirectAuth(@RequestParam("code") String code) throws Exception {

        log.info("auth code : " + code);

        /*
        * 직접 통신해서 새로운 token을 받아온 겅우
        * */
/*

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/x-www-form-urlencoded");

        MultiValueMap<String , String> map = new LinkedMultiValueMap<>();
        map.add("code" , code);
        map.add("client_id" , GoogleOauthValue.getClientSecrets().getWeb().getClientId());
        map.add("client_secret" , GoogleOauthValue.getClientSecrets().getWeb().getClientSecret());
        map.add("redirect_uri" , GoogleOauthValue.getClientSecrets().getWeb().getRedirectUris().get(2));
        map.add("grant_type" , "authorization_code");


        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map , headers);

        ResponseEntity<GoogleTokens> responseEntity = restTemplate.exchange(GoogleOauthValue.getClientSecrets().getWeb().getTokenUri(), HttpMethod.POST , httpEntity , GoogleTokens.class);

        GoogleTokens googleTokens = responseEntity.getBody();
        if(responseEntity.getStatusCode() == HttpStatus.OK)
        {
            log.info("access_token : " + googleTokens.getAccess_token());
            log.info("refresh_token : " + googleTokens.getRefresh_token());
            log.info("expires_in : " + googleTokens.getExpires_in());
            log.info("token_type : " + googleTokens.getToken_type());


        }
        else
        {
            throw new AuthorizationServiceException("Not Access Google Account");
        }
*/

        Credential newCredential = GoogleOauth.newTokens(userId , code);

        return new ModelAndView("forward:/member");
    }

    @RequestMapping(value = "/member/create/credentials", method = RequestMethod.GET)
    public void createCredentials(HttpServletResponse httpServletResponse) throws Exception {

        httpServletResponse.sendRedirect(GoogleOauth.newAuthorize());

    }

    // 여기는 들어가자마자 보이는것들 ,db연동
    @RequestMapping(value = "/member", method = RequestMethod.GET)
    public ModelAndView getMember() throws Exception {

        ModelAndView mv ;

        log.info("user " + userId);

        GoogleAuthorizationCodeFlow flow = GoogleOauth.getFlow();
        Credential userCredential = flow.loadCredential(userId);

        /*
        * token 유효성 검사
        * */
        if(userCredential == null || userCredential.refreshToken() )
        {
           return new ModelAndView("forward:/member/create/credentials");
        }

        log.info("access_token :  " + userCredential.getAccessToken());

        mv = setSheetsList(GoogleOauth.getSheets(userCredential));

        mv.setViewName("member");
        return mv;
    }


    @RequestMapping(value = "/member", method = RequestMethod.POST)
    public RedirectView insertUrl(@RequestParam("url") String url){

        Excel sheets = new Excel();
        Excel lastSheets = sheetsService.findLastSheetsBySid();
        if(lastSheets == null)
        {
            sheets.setSid(1);
        }
        else
        {
            sheets.setSid(lastSheets.getSid()+1);
        }

        sheets.setUrl(url);

        sheetsService.insertSheets(sheets);

        return new RedirectView("/member");
    }


    // data 출력하는 함수, ajax url을 넣어줄때 이런 형식으로 넣어준다
    @RequestMapping(value = "/member/sheets/{sheets_id}/{title}", method = RequestMethod.GET)
    public ResponseEntity getFileBySheetsId( @PathVariable(value = "sheets_id") String sheets_id, @PathVariable(value = "title") String title) throws Exception {

        ResponseEntity<ArrayList<List<Object>>> results;
        String spreadsheetId;

        GoogleAuthorizationCodeFlow flow = GoogleOauth.getFlow();
        Credential userCredential = flow.loadCredential(userId);

        /*
        * token 유효성 검사
        * */
        log.info("test");
        if(userCredential.refreshToken())
        {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        log.info("access_token :  " + userCredential.getAccessToken());

        Sheets service = GoogleOauth.getSheets(userCredential);
        spreadsheetId = sheets_id;
        range = title + "!A1:U1000";

        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();

        List<List<Object>> values = response.getValues();
        results = getSpreadSheetsData(values);

        return results;

    }

    private ResponseEntity<ArrayList<List<Object>>> getSpreadSheetsData(List<List<Object>> values) {
        ResponseEntity<ArrayList<List<Object>>> results = null;
        if (values == null || values.size() == 0) {
            System.out.println("No data found");
        } else {
            ArrayList<List<Object>> result = new ArrayList<>();
            System.out.println("members");

            for (List<Object> row : values) {
                if (row.isEmpty()) continue;

                if (row.contains("")) {
                    for (Iterator<Object> iterator = row.iterator(); iterator.hasNext(); ) {
                        String element = (String) iterator.next();
                        if (element.equals("")) {
                            iterator.remove();
                        }
                    }
                }

                log.info(row.toString());
                log.info(row.getClass().toString());
                result.add(row);
            }

            results = new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        }

        return results;
    }

    private ModelAndView setSheetsList(Sheets sheetParm) throws IOException {

        Spreadsheet spreadsheet;
        String spreadsheetId;
        String url[];

        List<Excel> sheetsList = sheetsService.findSheets();
        List<String> google = new ArrayList<>();
        List<List<String>> list = new ArrayList<>();

        ModelAndView mv = new ModelAndView();


        for(Excel sheets : sheetsList) {


            url = sheets.getUrl().split("/");

            spreadsheetId = url[5];

            log.info("SpreadsheetId : " + spreadsheetId);

            spreadsheet = sheetParm.spreadsheets().get("/").setSpreadsheetId(spreadsheetId).setIncludeGridData(true)
                                    .set("fields", "sheets.properties").execute();
            List<String> subList = new ArrayList<>();

            for (Sheet sheet : spreadsheet.getSheets()) {

                subList.add(sheet.getProperties().getTitle());

                log.info(sheet.getProperties().getTitle());
            }
            list.add(subList);
            google.add(spreadsheetId);

        }
        log.info(list.toString());
        log.info(google.toString());

        mv.addObject("title",list);
        mv.addObject("url",google);

        return mv;
    }

}
