package com.handicraft.admin.util;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.services.sheets.v4.Sheets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
@Slf4j
public class GoogleOauth {


    private static String APPLICATION_NAME = "handicraft";

    private static GoogleAuthorizationCodeFlow flow;

    static{
        // Build flow and trigger user authorization request.
        try {
            flow =
                    new GoogleAuthorizationCodeFlow.Builder(
                            GoogleOauthValue.getHttpTransport(), GoogleOauthValue.getJSON_FACTORY() , GoogleOauthValue.getClientSecrets() , GoogleOauthValue.getSCOPES())
                            .setDataStoreFactory(GoogleOauthValue.getDataStoreFactory())
                            .setAccessType("offline")
                            .setRefreshListeners(Arrays.asList(new CredentialRefreshListener() {
                                @Override
                                public void onTokenResponse(Credential credential, TokenResponse tokenResponse) throws IOException {

                                    log.info("token validation success : " + tokenResponse.getAccessToken());
                                    log.info("token validation access token : " + tokenResponse.getAccessToken());


                                    credential.setFromTokenResponse(tokenResponse);
                                }

                                @Override
                                public void onTokenErrorResponse(Credential credential, TokenErrorResponse tokenErrorResponse) throws IOException {

                                    log.info("token validation error : " + tokenErrorResponse.getError());
                                }
                            }))
                            .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GoogleAuthorizationCodeFlow getFlow() throws IOException
    {
        if(flow == null)
        {
            flow =
                    new GoogleAuthorizationCodeFlow.Builder(
                            GoogleOauthValue.getHttpTransport(), GoogleOauthValue.getJSON_FACTORY() , GoogleOauthValue.getClientSecrets() , GoogleOauthValue.getSCOPES())
                            .setDataStoreFactory(GoogleOauthValue.getDataStoreFactory())
                            .setAccessType("offline")
                            .setRefreshListeners(Arrays.asList(new CredentialRefreshListener() {
                                @Override
                                public void onTokenResponse(Credential credential, TokenResponse tokenResponse) throws IOException {

                                    log.info("token validation success : " + tokenResponse.getAccessToken());
                                    log.info("token validation access token : " + tokenResponse.getAccessToken());


                                    credential.setFromTokenResponse(tokenResponse);
                                }

                                @Override
                                public void onTokenErrorResponse(Credential credential, TokenErrorResponse tokenErrorResponse) throws IOException {

                                    log.info("token validation error : " + tokenErrorResponse.getError());
                                }
                            }))
                            .build();
        }

        return flow;
    }

    public static  String newAuthorize() throws IOException {

        getFlow();


        AuthorizationCodeRequestUrl codeRequestUrl = flow
                                                        .newAuthorizationUrl()
                                                        .setRedirectUri(GoogleOauthValue.getClientSecrets().getWeb().getRedirectUris().get(0));



        log.info("Google Authentication URL : " + codeRequestUrl.build());

        return codeRequestUrl.build();
    }

    public static Credential newTokens(String userId , String code) throws IOException {

        getFlow();

        GoogleTokenResponse tokenResponse = flow
                .newTokenRequest(code)
                .setGrantType("authorization_code")
                .setRedirectUri(GoogleOauthValue.getClientSecrets().getWeb().getRedirectUris().get(0))
                .execute();



        log.info("new access token : "+ tokenResponse.getAccessToken());
        log.info("new refresh token : "+ tokenResponse.getRefreshToken());
        log.info("new token type : "+ tokenResponse.getTokenType());
        log.info("new token expires : "+ tokenResponse.getExpiresInSeconds());
        log.info("new token scope : "+ tokenResponse.getScope());


        Credential newCredential = flow.createAndStoreCredential(tokenResponse , userId);
        return newCredential;
    }

    public static Sheets getSheets(Credential credential) throws Exception {

        return new Sheets.Builder(
                                    GoogleOauthValue.getHttpTransport(),
                                    GoogleOauthValue.getJSON_FACTORY() ,
                                    credential
                                    ).setApplicationName(APPLICATION_NAME).build();
    }


}
