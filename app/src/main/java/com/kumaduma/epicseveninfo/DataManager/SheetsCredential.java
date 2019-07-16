package com.kumaduma.epicseveninfo.DataManager;

import android.content.Context;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.kumaduma.epicseveninfo.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class SheetsCredential {
    private final String APPLICATION_NAME = "Epic Seven Journal Android App";
    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private final String CREDENTIALS_FILE_PATH = "gsheet_credentials.json";

    private Context c;
    SheetsCredential(Context c){
        this.c = c;
    }

    /**
     * Creates an authorized Service Account Credential object.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    Credential getServiceCredentials() throws IOException {


        InputStream input = c.getResources().openRawResource(R.raw.gsheet_credentials);
        if (input == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }

        return GoogleCredential.fromStream(input)
                .createScoped(SCOPES);
    }

    String getApplicationName() {
        return APPLICATION_NAME;
    }

    JsonFactory getJsonFactory() {
        return JSON_FACTORY;
    }

    public String getTokensDirectoryPath() {
        return TOKENS_DIRECTORY_PATH;
    }
}
