package com.handicraft.admin.util;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.handicraft.admin.controller.MemberController;
import com.handicraft.core.service.Excels.ExcelService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Component
public class GoogleOauthValue {

    @Getter
    private static File DATA_STORE_DIR;

    @Getter
    private static JsonFactory JSON_FACTORY;

    @Getter
    private static List<String> SCOPES;

    private static GoogleClientSecrets clientSecrets;

    private static FileDataStoreFactory DATA_STORE_FACTORY;

    private static HttpTransport HTTP_TRANSPORT;


    static {

        DATA_STORE_DIR = new File(System.getProperty("user.home", ".credentials/sheets.googleapis.com-java-quickstart"));
        JSON_FACTORY = JacksonFactory.getDefaultInstance();
        SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);

        // Load client secrets.
        InputStream in = MemberController.class.getResourceAsStream("/client_secret.json");

        try {
            clientSecrets = GoogleClientSecrets.load(GoogleOauthValue.getJSON_FACTORY(), new InputStreamReader(in));

            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }

    }


    public static GoogleClientSecrets getClientSecrets() {

        if(clientSecrets == null)
        {
            // Load client secrets.
            InputStream in = MemberController.class.getResourceAsStream("/client_secret.json");
            try {
                clientSecrets = GoogleClientSecrets.load(GoogleOauthValue.getJSON_FACTORY(), new InputStreamReader(in));


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return clientSecrets;
    }

    public static FileDataStoreFactory getDataStoreFactory() {

        if(DATA_STORE_FACTORY == null)
        {
            try {
                DATA_STORE_FACTORY =  new FileDataStoreFactory(DATA_STORE_DIR);
            } catch (Throwable t) {
                System.exit(1);
            }
        }

        return DATA_STORE_FACTORY;
    }



    public static HttpTransport getHttpTransport() {

        if(HTTP_TRANSPORT == null)
        {
            try {
                HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            } catch (Throwable t) {
                System.exit(1);
            }
        }

        return HTTP_TRANSPORT;
    }



}
