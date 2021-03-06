package com.github.kerner1000.etoro.stats.api;

import com.github.kerner1000.etoro.stats.taxonomy.prototypes.APICallsProto;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

class APICalls extends APICallsProto {

    private static final Logger logger = LoggerFactory.getLogger(APICalls.class);

    private final String apiKey;

    public APICalls(String apiKey) {
        this.apiKey = apiKey;
    }

    JsonNode requestForJsonNode(String request) {
        try {
            // we need to give the API some rest
            Thread.sleep(DEFAULT_API_REST_TIME);
            HttpResponse<JsonNode> response = Unirest.get(request)
                    .header("accept", "string")
                    .header("x-rapidapi-key", apiKey)
                    .header("x-rapidapi-host", "morningstar1.p.rapidapi.com")
                    .asJson();
            if (response.getStatus() == 200) {
                return response.getBody();
            } else {
                logger.error("Got unexpected return code from response {}, {}", response.getStatus(), response.getStatusText());
            }
        } catch (UnirestException | InterruptedException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    JsonNode getExchanges() {
        return requestForJsonNode("https://morningstar1.p.rapidapi.com/exchanges/list-mics");
    }

    JSONArray getCompaniesByExchange(String mic) {

        JsonNode response = requestForJsonNode("https://morningstar1.p.rapidapi.com/companies/list-by-exchange?Mic=" + mic);
        return (JSONArray) response.getObject().get("results");
    }

    JsonNode search(String searchString) {
        try {
            return requestForJsonNode("https://morningstar1.p.rapidapi.com/companies/auto-complete-search?SearchText=" + encodeValue(searchString));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return null;
    }


    JsonNode getCompanyProfile(String instrument, String mic)  {

        return requestForJsonNode("https://morningstar1.p.rapidapi.com/companies/get-company-profile?Ticker=" + instrument.trim().toUpperCase() + "&Mic=" + mic.trim().toUpperCase());


    }

}
