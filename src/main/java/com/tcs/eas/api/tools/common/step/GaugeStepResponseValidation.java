package com.tcs.eas.api.tools.common.step;

import static org.assertj.core.api.Assertions.assertThat;

import com.thoughtworks.gauge.Step;

import io.restassured.response.Response;

/**
 * 
 * @author 44745
 *
 */
public class GaugeStepResponseValidation {

	/**
	 * 
	 * @param statusCode
	 */
	@Step({ "response.status.equals(<status>)", "Then response status code is <status>" })
	public void responseStatusCodeMatches(String statusCode) {
		Response response = GaugeScenarioStore.getResponse();
		int expected = Integer.parseInt(statusCode);
		assertThat(response.statusCode()).isEqualTo(expected);
	}
//The response content-type should be
	@Step("The response content-type should be <contentType>")
	public void responseContentTypeMatches(String contentType) {
		Response response = GaugeScenarioStore.getResponse();
		//int expected = Integer.parseInt(statusCode);
		assertThat(response.getContentType()).isEqualTo(contentType);
	}
}
