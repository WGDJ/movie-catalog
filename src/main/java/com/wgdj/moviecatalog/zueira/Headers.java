package com.wgdj.moviecatalog.zueira;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Accept", "Accept-Encoding", "Host", "User-Agent", "X-Amzn-Trace-Id" })
public class Headers {

	@JsonProperty("Accept")
	private String accept;
	@JsonProperty("Accept-Encoding")
	private String acceptEncoding;
	@JsonProperty("Host")
	private String host;
	@JsonProperty("User-Agent")
	private String userAgent;
	@JsonProperty("X-Amzn-Trace-Id")
	private String xAmznTraceId;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("Accept")
	public String getAccept() {
		return accept;
	}

	@JsonProperty("Accept")
	public void setAccept(String accept) {
		this.accept = accept;
	}

	@JsonProperty("Accept-Encoding")
	public String getAcceptEncoding() {
		return acceptEncoding;
	}

	@JsonProperty("Accept-Encoding")
	public void setAcceptEncoding(String acceptEncoding) {
		this.acceptEncoding = acceptEncoding;
	}

	@JsonProperty("Host")
	public String getHost() {
		return host;
	}

	@JsonProperty("Host")
	public void setHost(String host) {
		this.host = host;
	}

	@JsonProperty("User-Agent")
	public String getUserAgent() {
		return userAgent;
	}

	@JsonProperty("User-Agent")
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@JsonProperty("X-Amzn-Trace-Id")
	public String getXAmznTraceId() {
		return xAmznTraceId;
	}

	@JsonProperty("X-Amzn-Trace-Id")
	public void setXAmznTraceId(String xAmznTraceId) {
		this.xAmznTraceId = xAmznTraceId;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}