package com.tcs.eas.api.tools.common.step;

import static com.tcs.eas.api.tools.common.util.Utils.utilHtmlReportFormatRequest;
import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.output.WriterOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcs.eas.api.tools.common.constant.Constants;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * This class provides implementation of the common testing steps of a
 * scenario.
 * 
 * @author 44745
 *
 */
public class GaugeStepImplementation implements Constants {

	/**
	 * Logger object
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GaugeStepImplementation.class);

	protected static DataStore specDataStore = DataStoreFactory.getSpecDataStore();

	protected static DataStore suiteDataStore = DataStoreFactory.getSuiteDataStore();

	/**
	 * 
	 */
	@Step("reset rest-assured")
	public static void resetRestAssured() {
		Gauge.writeMessage("RESET REST ASSURED");
		RestAssured.reset();
	}

	@Step("reset scenario store")
	public static void resetScenarioStore() {
		GaugeScenarioStore.resetScenarioStore();
	}

	@Step("set base URI")
	public static void setBaseURI() {
		String schema = System.getenv("httpSchema");
		String host = System.getenv("host");
		String port = System.getenv("port");
		if(port!=null && port.length()>0)
			RestAssured.baseURI = schema + "://" + host+":"+port;
		else
			RestAssured.baseURI = schema + "://" + host;
	}

	/**
	 * 
	 * @param baseUrlSpec
	 */
	public static void setSmokeURI(String baseUrlSpec) {
		RestAssured.baseURI = baseUrlSpec;
	}

	/**
	 * 
	 * @param port
	 */
	@Step("use Port from <property>")
	public static void setPort(String port) {
		int portInt = Integer.parseInt(System.getenv(port));
		if (portInt == 9000) {
			RestAssured.port = portInt;
		}
	}

	/**
	 * 
	 * @param path
	 */
	@Step({ "use path <path>" })
	public static void setBasePath(String path) {
		RestAssured.basePath = path;
	}

	/**
	 * 
	 * @param type
	 */
	@Step("send <METHOD> http request")
	public void sendHttpMethodRequest(String type) {
		Method method = Method.valueOf(type.toUpperCase());

		final StringWriter swRequest = new StringWriter();
		final PrintStream requestStream = new PrintStream(new WriterOutputStream(swRequest, Charset.forName("UTF-8")),
				true);
		final StringWriter swResponse = new StringWriter();
		final PrintStream responseStream = new PrintStream(new WriterOutputStream(swResponse, Charset.forName("UTF-8")),
				true);

		RequestSpecification requestSpec = GaugeScenarioStore.getRequestSpec();
		List<Filter> filters = GaugeScenarioStore.getFilters();
		filters.add(new RequestLoggingFilter(requestStream));
		filters.add(new ResponseLoggingFilter(responseStream));

		RequestSpecification finalRequestSpec = new RequestSpecBuilder().addRequestSpecification(requestSpec)
				.addFilters(filters).log(LogDetail.ALL).build();

		Response response = given().spec(finalRequestSpec).when().request(method).then().extract().response();

		GaugeScenarioStore.storeRequestSpec(finalRequestSpec);
		GaugeScenarioStore.storeResponse(response);
		GaugeScenarioStore.storeFullRequestText(swRequest.toString());
		GaugeScenarioStore.storeFullResponseText(swResponse.toString());

		if (GaugeScenarioStore.showRequest()) {
			String msg = utilHtmlReportFormatRequest(swRequest.toString());
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(String.format("Request = %s", msg));
			}
			Gauge.writeMessage("\nREQUEST: \n" + msg);
		}
		if (GaugeScenarioStore.showResponse()) {
			String msg = swResponse.toString();

			if (response.headers().hasHeaderWithName("Content-Type")
					&& response.getHeader("Content-Type").contains("html")) {
				msg = escapeHtml4(msg);
			}
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(String.format("Response = %s", msg));
			}
			Gauge.writeMessage("\nRESPONSE:\n" + msg);
		}
	}

	/**
	 * 
	 */
	@Step("show response")
	public static void showResponse() {
		GaugeScenarioStore.setShowResponse(true);
	}

	/**
	 * 
	 */
	@Step("show request")
	public static void showRequest() {
		GaugeScenarioStore.setShowRequest(true);
	}

	/**
	 * 
	 */
	@Step("hide response")
	public static void hideResponse() {
		GaugeScenarioStore.setShowResponse(false);
	}

	/**
	 * 
	 */
	@Step("hide request")
	public static void hideRequest() {
		GaugeScenarioStore.setShowRequest(false);
	}

	/**
	 * 
	 * @param path
	 * @throws IOException
	 */
	@Step({ "use body from file <file: string>" })
	public void setBodyFromFile(String path) throws IOException {
		setBody(new String(Files.readAllBytes(Paths.get(path))));
	}

	/**
	 * 
	 * @param body
	 */
	@Step({ "use body <body>" })
	public void setBody(String body) {
		Filter myFilter = (requestSpec, responseSpec, ctx) -> {
			requestSpec.body(body);
			return ctx.next(requestSpec, responseSpec);
		};
		GaugeScenarioStore.addFilter(myFilter);
	}

	/**
	 * 
	 * @param table
	 */
	@Step("set headers <table>")
	public void setHeaders(Table table) {
		for (TableRow tableRow : table.getTableRows()) {
			String header = tableRow.getCell(HEADER);
			String value = tableRow.getCell(VALUE);

			setHeader(header, value);
		}
	}

	/**
	 * 
	 * @param name
	 * @param value
	 */
	@Step("set header <name> to <value>")
	public void setHeader(String name, String value) {
		LOGGER.debug(String.format("Setting Header %1$s with %2$s", name, value));
		Filter myFilter = (requestSpec, responseSpec, ctx) -> {
			requestSpec.header(name, value);
			return ctx.next(requestSpec, responseSpec);
		};
		GaugeScenarioStore.addFilter(myFilter);
	}

	/**
	 * 
	 * @param table
	 */
	@Step("set query parameters from the table <table>")
	public void setQueryParams(Table table) {
		for (TableRow tableRow : table.getTableRows()) {
			setQueryParam(tableRow.getCell(KEY), tableRow.getCell(VALUE));
		}
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	private void setQueryParam(String key, String value) {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug(String.format("Setting Query Param %1$s with %2$s", key, value));
		Filter myFilter = (requestSpec, responseSpec, ctx) -> {
			requestSpec.queryParam(key, value);
			return ctx.next(requestSpec, responseSpec);
		};
		GaugeScenarioStore.addFilter(myFilter);
	}

	/**
	 * 
	 * @param table
	 */
	@Step("set path params from store <table>")
	public void setPathParamsFromStore(Table table) {

		for (TableRow tableRow : table.getTableRows()) {
			String key = tableRow.getCell(KEY);
			String value = (String) specDataStore.get(tableRow.getCell(VALUE));
			setPathParam(key, value);
		}
	}

	@Step("set path params from suite data store <table>")
	public void setPathParamsFromSuiteDataStore(Table table) {

		for (TableRow tableRow : table.getTableRows()) {
			String key = tableRow.getCell(KEY);
			String value = (String) suiteDataStore.get(tableRow.getCell(VALUE));
			setPathParam(key, value);
		}
	}

	@Step("set query params from suite data store <table>")
	public void setQueryParamsFromSuiteDataStore(Table table) {

		for (TableRow tableRow : table.getTableRows()) {
			String key = tableRow.getCell(KEY);
			String value = (String) suiteDataStore.get(tableRow.getCell(VALUE));
			setQueryParam(key, value);
		}
	}

	@Step("add form params <table>")
	public void addFormParams(Table table) {
		for (TableRow tableRow : table.getTableRows()) {
			String key = tableRow.getCell(KEY);
			String value = tableRow.getCell(VALUE);
			if (LOGGER.isDebugEnabled())
				LOGGER.debug(String.format("Setting Form Param %1$s with %2$s", key, value));

			Filter myFilter = (requestSpec, responseSpec, ctx) -> {
				requestSpec.formParam(key, value);
				return ctx.next(requestSpec, responseSpec);
			};
			GaugeScenarioStore.addFilter(myFilter);
		}
	}

	@Step("set path params <table>")
	public void setPathParamsAndIgnoreEmptyParamValues(Table table) {
		for (TableRow tableRow : table.getTableRows()) {
			String key = tableRow.getCell(KEY);
			String value = tableRow.getCell(VALUE);
			setPathParam(key, value);
		}
	}

	public void setPathParam(String key, String value) {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug(String.format("Setting Path Param %1$s with %2$s", key, value));
		Filter myFilter = (requestSpec, responseSpec, ctx) -> {
			requestSpec.pathParam(key, value);
			return ctx.next(requestSpec, responseSpec);
		};
		GaugeScenarioStore.addFilter(myFilter);
	}

	/**
	 * 
	 * @param jsonBody
	 */
	public void setJSONBody(String jsonBody) {
		Filter myFilter = (requestSpec, responseSpec, ctx) -> {
			requestSpec.body(jsonBody);
			return ctx.next(requestSpec, responseSpec);
		};
		GaugeScenarioStore.addFilter(myFilter);
	}
}