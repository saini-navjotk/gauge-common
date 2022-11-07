package com.tcs.eas.api.tools.common.step;

import java.util.ArrayList;
import java.util.List;

import com.tcs.eas.api.tools.common.constant.Constants;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * 
 * @author 44745
 *
 */
public class GaugeScenarioStore implements Constants{
    protected static DataStore scenarioStore = DataStoreFactory.getScenarioDataStore();
    /**
     * 
     */
    private GaugeScenarioStore() {
        super();
    }

    /**
     * 
     * @return
     */
    public static RequestSpecification getRequestSpec() {

        if (scenarioStore.get(REQUEST) != null) {
            return (RequestSpecification) scenarioStore.get(REQUEST);
        }
        return getEmptyRequestSpec();
    }

    /**
     * 
     * @param request
     */
    public static void storeRequestSpec(RequestSpecification request) {
        scenarioStore.put(REQUEST, request);
    }

    /**
     * 
     * @return
     */
    public static RequestSpecification getEmptyRequestSpec() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        return builder.build();
    }

    /**
     * 
     * @return
     */
    public static List<Filter> getFilters() {
        if (scenarioStore.get(FILTERS) != null) {
            return (List<Filter>) scenarioStore.get(FILTERS);
        }
        return new ArrayList<>();
    }

    /**
     * 
     * @param response
     */
    public static void storeResponse(Response response) {
        scenarioStore.put(RESPONSE, response);
    }

    /**
     * 
     * @return
     */
    public static Response getResponse() {
        return (Response) scenarioStore.get(RESPONSE);
    }

    /**
     * 
     * @param request
     */
    public static void storeFullRequestText(String request) {
        scenarioStore.put("fullRequestText", request);
    }

    /**
     * 
     * @param response
     */
    public static void storeFullResponseText(String response) {
        scenarioStore.put(FULL_RESPONSE_TEXT, response);
    }
    
    /**
     * 
     * @return
     */
    public static String getFullResponseText() {
        return scenarioStore.get(FULL_RESPONSE_TEXT).toString();
    }

    /**
     * 
     * @return
     */
    public static boolean showRequest() {
        boolean showRequest = true;
        if (scenarioStore.get(SHOW_REQUEST) != null) {
            showRequest = (boolean) scenarioStore.get(SHOW_REQUEST);
        }
        return showRequest;
    }

    /**
     * 
     * @return
     */
    public static boolean showResponse() {
        boolean showResponse = true;
        if (scenarioStore.get(SHOW_RESPONSE) != null) {
            showResponse = (boolean) scenarioStore.get(SHOW_RESPONSE);
        }
        return showResponse;
    }

    public static void setShowResponse(boolean showResponse) {
        scenarioStore.put(SHOW_RESPONSE, showResponse);
    }

    public static void setShowRequest(boolean showRequest) {
        scenarioStore.put(SHOW_REQUEST, showRequest);
    }

    public static boolean useProxy(){
        boolean useProxy = false;
        if (scenarioStore.get(USE_PROXY) != null) {
            useProxy = (boolean) scenarioStore.get(USE_PROXY);
        }
        return useProxy;
    }

    public static void setUseProxy(boolean useProxy) {
        scenarioStore.put(USE_PROXY, useProxy);
    }

    public static void addFilter(Filter filter) {
        List<Filter> filters = getFilters();
        filters.add(filter);
        storeFilters(filters);
    }

    public static void storeFilters(List<Filter> filters) {
        scenarioStore.put(FILTERS, filters);
    }

    public static String propertyExpansion(String value) {
        if (value.startsWith("$")) {
            String key = value.substring(1);
            value = getKeyValue(key);
        }
        return value;
    }

    public static String getKeyValue(String key) {
        return (String) scenarioStore.get(key);
    }

    public static void storeAccessToken(String token) {
        scenarioStore.put(ACCESS_TOKEN, token);
    }

    public static void setAccessTokenInHeader() {
        if (scenarioStore.get(ACCESS_TOKEN) != null) {
            Filter myFilter = (requestSpec, responseSpec, ctx) -> {
                requestSpec.header("Authorization", "Bearer " + scenarioStore.get(ACCESS_TOKEN));
                return ctx.next(requestSpec, responseSpec);
            };
            addFilter(myFilter);
        }
    }

    public static void resetScenarioStore() {
        scenarioStore.remove(USE_PROXY);
        scenarioStore.remove(SHOW_RESPONSE);
        scenarioStore.remove(SHOW_REQUEST);
        scenarioStore.remove(FILTERS);
        scenarioStore.remove(REQUEST);
        scenarioStore.remove(RESPONSE);
        scenarioStore.remove("fullRequestText");
        scenarioStore.remove(FULL_RESPONSE_TEXT);
        scenarioStore.remove(ACCESS_TOKEN);
    }

    public static void setUpProxyForSmokeTest(boolean useProxy) {
        scenarioStore.put(USE_PROXY, useProxy);
    }

}
