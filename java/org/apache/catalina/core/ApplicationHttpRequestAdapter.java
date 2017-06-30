package org.apache.catalina.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
 * 为了访问Tomcat的非public类 org.apache.catalina.core.ApplicationHttpRequest
 */
public class ApplicationHttpRequestAdapter{
	ApplicationHttpRequest applicationHttpRequest;

	public ApplicationHttpRequestAdapter(HttpServletRequest httpRequest) {
		if(httpRequest instanceof ApplicationHttpRequest){
			this.applicationHttpRequest = (ApplicationHttpRequest)httpRequest;
		}else{
			throw new RuntimeException("HttpServletRequest[" + httpRequest + "] is not type of {org.apache.catalina.core.ApplicationHttpRequest}.");
		}
	}

	public boolean equals(Object obj) {
		return applicationHttpRequest.equals(obj);
	}

	public Object getAttribute(String name) {
		return applicationHttpRequest.getAttribute(name);
	}

	public Enumeration getAttributeNames() {
		return applicationHttpRequest.getAttributeNames();
	}

	public String getAuthType() {
		return applicationHttpRequest.getAuthType();
	}

	public String getCharacterEncoding() {
		return applicationHttpRequest.getCharacterEncoding();
	}

	public int getContentLength() {
		return applicationHttpRequest.getContentLength();
	}

	public String getContentType() {
		return applicationHttpRequest.getContentType();
	}

	public String getContextPath() {
		return applicationHttpRequest.getContextPath();
	}

	public Cookie[] getCookies() {
		return applicationHttpRequest.getCookies();
	}

	public long getDateHeader(String arg0) {
		return applicationHttpRequest.getDateHeader(arg0);
	}

	public String getHeader(String arg0) {
		return applicationHttpRequest.getHeader(arg0);
	}

	public Enumeration getHeaderNames() {
		return applicationHttpRequest.getHeaderNames();
	}

	public Enumeration getHeaders(String arg0) {
		return applicationHttpRequest.getHeaders(arg0);
	}

	/*public String getInfo() {
		return applicationHttpRequest.getInfo();
	}*/

	public ServletInputStream getInputStream() throws IOException {
		return applicationHttpRequest.getInputStream();
	}

	public int getIntHeader(String arg0) {
		return applicationHttpRequest.getIntHeader(arg0);
	}

	public String getLocalAddr() {
		return applicationHttpRequest.getLocalAddr();
	}

	public Locale getLocale() {
		return applicationHttpRequest.getLocale();
	}

	public Enumeration getLocales() {
		return applicationHttpRequest.getLocales();
	}

	public String getLocalName() {
		return applicationHttpRequest.getLocalName();
	}

	public int getLocalPort() {
		return applicationHttpRequest.getLocalPort();
	}

	public String getMethod() {
		return applicationHttpRequest.getMethod();
	}

	public String getParameter(String name) {
		return applicationHttpRequest.getParameter(name);
	}

	public Map getParameterMap() {
		return applicationHttpRequest.getParameterMap();
	}

	public Enumeration getParameterNames() {
		return applicationHttpRequest.getParameterNames();
	}

	public String[] getParameterValues(String name) {
		return applicationHttpRequest.getParameterValues(name);
	}

	public String getPathInfo() {
		return applicationHttpRequest.getPathInfo();
	}

	public String getPathTranslated() {
		return applicationHttpRequest.getPathTranslated();
	}

	public String getProtocol() {
		return applicationHttpRequest.getProtocol();
	}

	public String getQueryString() {
		return applicationHttpRequest.getQueryString();
	}

	public BufferedReader getReader() throws IOException {
		return applicationHttpRequest.getReader();
	}

	public String getRealPath(String arg0) {
		return applicationHttpRequest.getRealPath(arg0);
	}

	public String getRemoteAddr() {
		return applicationHttpRequest.getRemoteAddr();
	}

	public String getRemoteHost() {
		return applicationHttpRequest.getRemoteHost();
	}

	public int getRemotePort() {
		return applicationHttpRequest.getRemotePort();
	}

	public String getRemoteUser() {
		return applicationHttpRequest.getRemoteUser();
	}

	public ServletRequest getRequest() {
		return applicationHttpRequest.getRequest();
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return applicationHttpRequest.getRequestDispatcher(path);
	}

	public String getRequestedSessionId() {
		return applicationHttpRequest.getRequestedSessionId();
	}

	public String getRequestURI() {
		return applicationHttpRequest.getRequestURI();
	}

	public StringBuffer getRequestURL() {
		return applicationHttpRequest.getRequestURL();
	}

	public String getScheme() {
		return applicationHttpRequest.getScheme();
	}

	public String getServerName() {
		return applicationHttpRequest.getServerName();
	}

	public int getServerPort() {
		return applicationHttpRequest.getServerPort();
	}

	public String getServletPath() {
		return applicationHttpRequest.getServletPath();
	}

	public HttpSession getSession() {
		return applicationHttpRequest.getSession();
	}

	public HttpSession getSession(boolean create) {
		return applicationHttpRequest.getSession(create);
	}

	public Principal getUserPrincipal() {
		return applicationHttpRequest.getUserPrincipal();
	}

	public int hashCode() {
		return applicationHttpRequest.hashCode();
	}

	public boolean isRequestedSessionIdFromCookie() {
		return applicationHttpRequest.isRequestedSessionIdFromCookie();
	}

	public boolean isRequestedSessionIdFromUrl() {
		return applicationHttpRequest.isRequestedSessionIdFromUrl();
	}

	public boolean isRequestedSessionIdFromURL() {
		return applicationHttpRequest.isRequestedSessionIdFromURL();
	}

	public boolean isRequestedSessionIdValid() {
		return applicationHttpRequest.isRequestedSessionIdValid();
	}

	public boolean isSecure() {
		return applicationHttpRequest.isSecure();
	}

	public boolean isUserInRole(String arg0) {
		return applicationHttpRequest.isUserInRole(arg0);
	}

	public void recycle() {
		applicationHttpRequest.recycle();
	}

	public void removeAttribute(String name) {
		applicationHttpRequest.removeAttribute(name);
	}

	public void setAttribute(String name, Object value) {
		applicationHttpRequest.setAttribute(name, value);
	}

	public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
		applicationHttpRequest.setCharacterEncoding(arg0);
	}

	public void setRequest(ServletRequest arg0) {
		applicationHttpRequest.setRequest(arg0);
	}

	public String toString() {
		return applicationHttpRequest.toString();
	}

}
