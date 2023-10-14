package org.itccanarias.t4.thesaurus.ws;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.itccanarias.t4.thesaurus.Auth;
import org.itccanarias.t4.thesaurus.model.TaxonDS;
import org.itccanarias.t4.thesaurus.model.TaxonBaseDS;
import org.itccanarias.t4.thesaurus.model.TaxonValidationResponse;

public class ThesaurusAPIConsumer_Impl implements ThesaurusAPIConsumer {

    private final String apiUrl;

    public ThesaurusAPIConsumer_Impl(final String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public ResponseWrapper login(
            final Auth auth,
            final String endpoint) throws Exception {

        String apiUrl = this.apiUrl + endpoint;
        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        String jsonString = "{\"email\": \"" + auth.getEmail() + "\", \"password\": \"" + auth.getPassword() + "\"}";

        return getResponse(connection, "POST", jsonString);
    }

    @Override
    public TaxonDS addTaxon(
            final String endpoint,
            final String taxon,
            final String token) throws Exception {

        String apiUrl = this.apiUrl + endpoint;
        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Authorization", "Bearer " + token);

        String jsonString = "{\"taxon\": \"" + taxon + "\"}";

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            String resp = readResponse(conn);

            ObjectMapper objectMapper = new ObjectMapper();
            TaxonDS tds = objectMapper.readValue(resp, TaxonDS.class);

            return tds;

        } else {
            return null;
        }
    }

    @Override
    public TaxonDS[] addTaxa(
            final String endpoint,
            final TaxonBaseDS[] taxa,
            final String token) throws Exception {

        TaxonDS[] taxaDS = new TaxonDS[taxa.length];

        for (int i = 0; i < taxa.length; i++) {
            taxaDS[i] = this.addTaxon(endpoint, taxa[i].getName(), token);
        }

        return taxaDS;
    }

    @Override
    public boolean deleteTaxon(
            final String endpoint,
            final int taxon_id,
            final String token) throws Exception {

        String apiUrl = this.apiUrl + endpoint + "/" + taxon_id;
        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestMethod("DELETE");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public TaxonDS getTaxon(
            final String endpoint,
            final int taxon_id) throws Exception {

        String apiUrl = this.apiUrl + endpoint;

        String queryString = "taxon=" + taxon_id;
        URL url = new URL(apiUrl + "?" + queryString);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            String resp = readResponse(conn);

            ObjectMapper objectMapper = new ObjectMapper();
            TaxonDS tds = objectMapper.readValue(resp, TaxonDS.class);

            return tds;

        } else {
            return null;
        }
    }

    @Override
    public TaxonDS[] listTaxon(final String endpoint) throws Exception {
        String apiUrl = this.apiUrl + endpoint;
        URL url = new URL(apiUrl);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            String resp = readResponse(conn);

            ObjectMapper objectMapper = new ObjectMapper();
            TaxonDS[] tds_array = objectMapper.readValue(resp, TaxonDS[].class);

            return tds_array;

        } else {
            return null;
        }
    }

    @Override
    public boolean updateTaxon(
            final String endpoint,
            final Integer taxon_id,
            final String new_name,
            final String token) throws Exception {
        
        String apiUrl = this.apiUrl + endpoint + "/" + taxon_id;
        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Authorization", "Bearer " + token);

        String jsonString = "{\"taxon\": \"" + new_name + "\"}";

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            return true;
        } else {
            return false;
        }
        
    }

    @Override
    public TaxonValidationResponse[] validateTaxaName(
            final String endpoint,
            final String[] taxa) throws Exception {

        TaxonValidationResponse tvr_array[] = new TaxonValidationResponse[taxa.length];

        for (int i = 0; i < taxa.length; i++) {
            tvr_array[i] = this.validateTaxonName(endpoint, taxa[i]);
        }

        return tvr_array;
    }

    @Override
    public TaxonValidationResponse validateTaxonName(
            final String endpoint,
            final String taxon) throws Exception {

        String apiUrl = this.apiUrl + endpoint;
        String taxonEncoded = URLEncoder.encode(taxon, "UTF-8");

        String queryString = "taxon=" + taxonEncoded;
        URL url = new URL(apiUrl + "?" + queryString);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            String resp = readResponse(conn);

            ObjectMapper objectMapper = new ObjectMapper();
            TaxonValidationResponse tvr = objectMapper.readValue(resp, TaxonValidationResponse.class);

            return tvr;

        } else {
            return null;
        }
    }

    public ResponseWrapper loadMatrix(
            final String token,
            final String endPoint,
            final String matrix) throws Exception {

        String apiUrl = this.apiUrl + endPoint;
        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Authorization", "Bearer " + token);

        String jsonString = "{\"matrix\": \"" + matrix + "\"}";

        return getResponse(connection, "POST", jsonString);
    }

    private String readResponse(
            final HttpURLConnection connection) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder json_response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            json_response.append(line);
        }

        reader.close();
        connection.disconnect();

        return json_response.toString();
    }

    private ResponseWrapper getResponse(
            final HttpURLConnection connection,
            final String method,
            final String jsonString) throws Exception {

        connection.setRequestMethod(method);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

        wr.writeBytes(jsonString);
        wr.flush();
        wr.close();

        int responseCode = connection.getResponseCode();

        JSONObject resp = new JSONObject(this.readResponse(connection));
        String message = resp.getString("message");

        return new ResponseWrapper(responseCode, message);
    }

}
