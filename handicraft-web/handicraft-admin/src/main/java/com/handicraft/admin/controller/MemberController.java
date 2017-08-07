package com.handicraft.admin.controller;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Controller
public class MemberController {
    private static final String APPLICATION_NAME = "HandiCraft";
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home", ".credentials/sheets.googleapis.com-java-quickstart"));
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT;
    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);
    private static String spreadsheetId;
    private static String range;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public static Credential authorize() throws IOException {
        InputStream in = MemberController.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();

        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");

        System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
    }

    // 여기는 들어가자마자 보이는것들 ,db연동
    @RequestMapping(value = "/member")
    public ModelAndView getMember() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("member");
        return mv;
    }


    @RequestMapping(value = "/sheets", method = RequestMethod.GET)
    public ModelAndView getFile(@RequestParam(value = "google") String google) throws IOException {

        // 여기는 조회 누르면 밑에 sheet 제목 띄우는 함수
        ModelAndView results = new ModelAndView();


        String url[] = google.split("/");

        Sheets service = getSheetsService();
        spreadsheetId = url[5];

        Spreadsheet spreadsheet = service.spreadsheets().get("/").setSpreadsheetId(spreadsheetId).setIncludeGridData(true)
                .set("fields", "sheets.properties").execute();

        List<String> list = new ArrayList<>();

        for (Sheet sheet : spreadsheet.getSheets()) {
            list.add(sheet.getProperties().getTitle());
            System.out.println(sheet.getProperties().getTitle());
        }
        results.addObject("list", list);

//        return "redirect:/sheets/" + spreadsheetId + "/" + list.get(0);
        return results;
    }


    // data 출력하는 함수
    @RequestMapping(value = "/sheets/{sheets_id}/{title}", method = RequestMethod.GET)
    public ResponseEntity getFileBySheetsId(@PathVariable(value = "sheets_id") String sheets_id, @PathVariable(value = "title") String title) throws IOException {

        System.out.println("오 돌아간다!");
        ResponseEntity<ArrayList<List<Object>>> results;

        Sheets service = getSheetsService();
        spreadsheetId = sheets_id;
        range = title + "!A1:U1000";

        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();

        List<List<Object>> values = response.getValues();
        results = getSpreadSheetsData(values);

        return results;
    }

    public ResponseEntity<ArrayList<List<Object>>> getSpreadSheetsData(List<List<Object>> values) {
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

                System.out.println(row.toString());
                System.out.println(row.getClass());
                result.add(row);
            }

            results = new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        }

        return results;
    }
}
