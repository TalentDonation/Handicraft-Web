package com.handicraft.admin.util;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
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

            // TODO: 2017. 10. 23. ApprovalPrompt 제거할 예정
            flow =
                    new GoogleAuthorizationCodeFlow.Builder(
                            GoogleOauthValue.getHttpTransport(), GoogleOauthValue.getJSON_FACTORY() , GoogleOauthValue.getClientSecrets() , GoogleOauthValue.getSCOPES())
                            .setDataStoreFactory(GoogleOauthValue.getDataStoreFactory())
                            .setAccessType("offline")
                            .setApprovalPrompt("force")
                            .setRefreshListeners(Arrays.asList(getCredentialRefreshListener()))
                            .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized GoogleAuthorizationCodeFlow getFlow() throws IOException
    {
        if(flow == null)
        {
            // TODO: 2017. 10. 23. ApprovalPrompt 제거할 예정
            flow =
                    new GoogleAuthorizationCodeFlow.Builder(
                            GoogleOauthValue.getHttpTransport(), GoogleOauthValue.getJSON_FACTORY() , GoogleOauthValue.getClientSecrets() , GoogleOauthValue.getSCOPES())
                            .setDataStoreFactory(GoogleOauthValue.getDataStoreFactory())
                            .setAccessType("offline")
                            .setApprovalPrompt("force")
                            .setRefreshListeners(Arrays.asList(getCredentialRefreshListener()))
                            .build();
        }

        return flow;
    }

    private static CredentialRefreshListener getCredentialRefreshListener() {
        return new CredentialRefreshListener() {
            @Override
            public void onTokenResponse(Credential credential, TokenResponse tokenResponse) throws IOException {

                log.info("token validation success:");
                log.info("token validation access token : " + tokenResponse.getAccessToken());
                log.info("token validation refresh token : " + tokenResponse.getRefreshToken());
                log.info("token validation token expires : " + tokenResponse.getExpiresInSeconds());
                log.info("token validation token type : " + tokenResponse.getTokenType());

                credential.setFromTokenResponse(tokenResponse);
            }

            @Override
            public void onTokenErrorResponse(Credential credential, TokenErrorResponse tokenErrorResponse) throws IOException {

                log.info("token validation error : " + tokenErrorResponse.getError());
            }
        };
    }

    public static  String newAuthorize() throws IOException {

        AuthorizationCodeRequestUrl codeRequestUrl = getFlow()
                                                        .newAuthorizationUrl()
                                                        .setRedirectUri(GoogleOauthValue.getClientSecrets().getWeb().getRedirectUris().get(0));



        log.info("Google Authentication URL : " + codeRequestUrl.build());

        return codeRequestUrl.build();
    }

    public static Credential newTokens(String userId , String code) throws IOException {

        GoogleTokenResponse tokenResponse = getFlow()
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

    // TODO: 2017. 10. 23. Credential refreshToken() 확인 후 제거 예정 
    public static void refreshTokens(String userId , String refreshToken) throws IOException {

        GoogleRefreshTokenRequest refreshTokenRequest = new GoogleRefreshTokenRequest(
                GoogleOauthValue.getHttpTransport(),
                GoogleOauthValue.getJSON_FACTORY(),
                refreshToken,
                GoogleOauthValue.getClientSecrets().getWeb().getClientId(),
                GoogleOauthValue.getClientSecrets().getWeb().getClientSecret()
        );

        GoogleTokenResponse tokenResponse = refreshTokenRequest
                                                                .setGrantType("refresh_token")
                                                                .setTokenServerUrl(new GenericUrl(GoogleOauthValue.getClientSecrets().getWeb().getTokenUri()))
                                                                .setScopes(GoogleOauthValue.getSCOPES())
                                                                .execute();


        log.info("refreshed access token : "+ tokenResponse.getAccessToken());
        log.info("refreshed token type : "+ tokenResponse.getTokenType());
        log.info("refreshed token expires : "+ tokenResponse.getExpiresInSeconds());

        flow.loadCredential(userId).setFromTokenResponse(tokenResponse);
    }

    public static Sheets getSheets(Credential credential) throws Exception {

        return new Sheets.Builder(
                                    GoogleOauthValue.getHttpTransport(),
                                    GoogleOauthValue.getJSON_FACTORY() ,
                                    credential
                                    ).setApplicationName(APPLICATION_NAME).build();
    }


}
