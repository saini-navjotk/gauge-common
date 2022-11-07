package com.tcs.eas.api.tools.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.eas.api.tools.common.step.GaugeScenarioStore;
import com.thoughtworks.gauge.Gauge;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.filter.Filter;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.ProxySpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
/**
 * 
 * @author 44745
 *
 */
public class Utils {

	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

	/**
	 * 
	 */
	private Utils() {

	}

	/**
	 * 
	 * @param jsonPath
	 * @return
	 */
	public static List<String> extractElementFromResponseReturnArray(String jsonPath) {
		Response response = GaugeScenarioStore.getResponse();
		utilHtmlReportWriteJsonSnippet(response, jsonPath);
		return (response.getBody().path(jsonPath) != null) ? response.getBody().path(jsonPath) : null;
	}

	/**
	 * 
	 * @param response
	 * @param xmlPath
	 */
	public static void utilHtmlReportWriteXmlSnippet(Response response, String xmlPath) {
        try {
            XmlPath rb = response.getBody().xmlPath();
            Gauge.writeMessage(rb.get(xmlPath));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error in utilHtmlReportWriteXmlSnippet", e);
        }
    }

	/**
	 * 
	 * @param s
	 * @return
	 */
    public static String utilHtmlReportFormatRequest(String s) {
        return Pattern.compile("^[a-z A-Z]+:\\s+<none>(\r?\n?)", Pattern.MULTILINE).matcher(s).replaceAll("");
    }

    
	/**
	 * 
	 */
    public static void setUpProxy() {
        if (GaugeScenarioStore.useProxy()) {
            PreemptiveBasicAuthScheme auth = new PreemptiveBasicAuthScheme();
            auth.setUserName(System.getenv("apigee_proxy_username"));
            auth.setPassword(System.getenv("apigee_proxy_password"));

           Filter proxy = (requestSpec, responseSpec, ctx) -> {
                ProxySpecification spec = ProxySpecification.host(System.getenv("apigee_proxy_host"));
                spec = spec.withPort(Integer.valueOf(System.getenv("apigee_proxy_port")));
                requestSpec.proxy(spec);
                return ctx.next(requestSpec, responseSpec);
            };

            Filter header = (requestSpec, responseSpec, ctx) -> {
                requestSpec.header("Proxy-Authorization", auth.generateAuthToken());
                return ctx.next(requestSpec, responseSpec);
            };
            GaugeScenarioStore.addFilter(proxy);
            GaugeScenarioStore.addFilter(header);
        }
    }
    
	/**
	 * 
	 * @param xmlPath
	 * @return
	 */
	public static String extractXmlValueFromResponse(String xmlPath) {
        Response response = GaugeScenarioStore.getResponse();
        utilHtmlReportWriteXmlSnippet(response, xmlPath);
        return (response.getBody().xmlPath().get(xmlPath) != null) ? response.getBody().xmlPath().get(xmlPath).toString() : null;
    }
	
	/**
	 * 
	 * @param response
	 * @param jsonPath
	 */
	public static void utilHtmlReportWriteJsonSnippet(Response response, String jsonPath) {
		Object rb;
		try {
			rb = response.getBody().path(jsonPath);
			String json = jsonPrettyPrint(rb);
			Gauge.writeMessage(json);
		} catch (IllegalArgumentException | JsonProcessingException e) {
			LOGGER.error("Unable to write JSON snippet to HTML report", e);
		}
	}

	/**
	 * 
	 * @param jsonPath
	 * @return
	 */
	public static String extractElementFromResponse(String jsonPath) {
        Response response = GaugeScenarioStore.getResponse();
        utilHtmlReportWriteJsonSnippet(response, jsonPath);
        return (response.getBody().path(jsonPath) != null) ? response.getBody().path(jsonPath).toString() : null;
    }
	
	/**
	 * 
	 * @param validationString
	 * @param items
	 * @return
	 */
	 public static boolean containsItemFromArray(String validationString, String[] items) {
	        return Arrays.stream(items).anyMatch(validationString::contains);
	    }

	/**
	 * 
	 * @param rb
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String jsonPrettyPrint(Object rb) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rb);
	}
}