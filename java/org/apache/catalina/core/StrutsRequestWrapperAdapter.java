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

import org.apache.struts2.dispatcher.StrutsRequestWrapper;

public class StrutsRequestWrapperAdapter {
	StrutsRequestWrapper strutsRequestWrapper;
	public StrutsRequestWrapperAdapter(HttpServletRequest httpRequest) {
		if(httpRequest instanceof StrutsRequestWrapper){
			this.strutsRequestWrapper = (StrutsRequestWrapper)httpRequest;
		}else{
			throw new RuntimeException("HttpServletRequest[" + httpRequest + "] is not type of {org.apache.struts2.dispatcher.StrutsRequestWrapper}.");
		}
	}
	
	public boolean equals(Object obj) {
		return strutsRequestWrapper.equals(obj);
	}
	
	public Object getAttribute(String name) {
		return strutsRequestWrapper.getAttribute(name);
	}
	
	public Enumeration getAttributeNames() {
		return strutsRequestWrapper.getAttributeNames();
	}

	public String getAuthType() {
		return strutsRequestWrapper.getAuthType();
	}

	public String getCharacterEncoding() {
		return strutsRequestWrapper.getCharacterEncoding();
	}

	public int getContentLength() {
		return strutsRequestWrapper.getContentLength();
	}

	public String getContentType() {
		return strutsRequestWrapper.getContentType();
	}

	public String getContextPath() {
		return strutsRequestWrapper.getContextPath();
	}

	public Cookie[] getCookies() {
		return strutsRequestWrapper.getCookies();
	}

	public long getDateHeader(String arg0) {
		return strutsRequestWrapper.getDateHeader(arg0);
	}

	public String getHeader(String arg0) {
		return strutsRequestWrapper.getHeader(arg0);
	}

	public Enumeration getHeaderNames() {
		return strutsRequestWrapper.getHeaderNames();
	}

	public Enumeration getHeaders(String arg0) {
		return strutsRequestWrapper.getHeaders(arg0);
	}

	/*public String getInfo() {
		return strutsRequestWrapper.getInfo();
	}*/

	public ServletInputStream getInputStream() throws IOException {
		return strutsRequestWrapper.getInputStream();
	}

	public int getIntHeader(String arg0) {
		return strutsRequestWrapper.getIntHeader(arg0);
	}

	public String getLocalAddr() {
		return strutsRequestWrapper.getLocalAddr();
	}

	public Locale getLocale() {
		return strutsRequestWrapper.getLocale();
	}

	public Enumeration getLocales() {
		return strutsRequestWrapper.getLocales();
	}

	public String getLocalName() {
		return strutsRequestWrapper.getLocalName();
	}

	public int getLocalPort() {
		return strutsRequestWrapper.getLocalPort();
	}

	public String getMethod() {
		return strutsRequestWrapper.getMethod();
	}

	public String getParameter(String name) {
		return strutsRequestWrapper.getParameter(name);
	}

	public Map getParameterMap() {
		return strutsRequestWrapper.getParameterMap();
	}

	public Enumeration getParameterNames() {
		return strutsRequestWrapper.getParameterNames();
	}

	public String[] getParameterValues(String name) {
		return strutsRequestWrapper.getParameterValues(name);
	}

	public String getPathInfo() {
		return strutsRequestWrapper.getPathInfo();
	}

	public String getPathTranslated() {
		return strutsRequestWrapper.getPathTranslated();
	}

	public String getProtocol() {
		return strutsRequestWrapper.getProtocol();
	}

	public String getQueryString() {
		return strutsRequestWrapper.getQueryString();
	}

	public BufferedReader getReader() throws IOException {
		return strutsRequestWrapper.getReader();
	}

	public String getRealPath(String arg0) {
		return strutsRequestWrapper.getRealPath(arg0);
	}

	public String getRemoteAddr() {
		return strutsRequestWrapper.getRemoteAddr();
	}

	public String getRemoteHost() {
		return strutsRequestWrapper.getRemoteHost();
	}

	public int getRemotePort() {
		return strutsRequestWrapper.getRemotePort();
	}

	public String getRemoteUser() {
		return strutsRequestWrapper.getRemoteUser();
	}

	public ServletRequest getRequest() {
		return strutsRequestWrapper.getRequest();
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return strutsRequestWrapper.getRequestDispatcher(path);
	}

	public String getRequestedSessionId() {
		return strutsRequestWrapper.getRequestedSessionId();
	}

	public String getRequestURI() {
		return strutsRequestWrapper.getRequestURI();
	}

	public StringBuffer getRequestURL() {
		return strutsRequestWrapper.getRequestURL();
	}

	public String getScheme() {
		return strutsRequestWrapper.getScheme();
	}

	public String getServerName() {
		return strutsRequestWrapper.getServerName();
	}

	public int getServerPort() {
		return strutsRequestWrapper.getServerPort();
	}

	public String getServletPath() {
		return strutsRequestWrapper.getServletPath();
	}

	public HttpSession getSession() {
		return strutsRequestWrapper.getSession();
	}

	public HttpSession getSession(boolean create) {
		return strutsRequestWrapper.getSession(create);
	}

	public Principal getUserPrincipal() {
		return strutsRequestWrapper.getUserPrincipal();
	}

	public int hashCode() {
		return strutsRequestWrapper.hashCode();
	}

	public boolean isRequestedSessionIdFromCookie() {
		return strutsRequestWrapper.isRequestedSessionIdFromCookie();
	}

	public boolean isRequestedSessionIdFromUrl() {
		return strutsRequestWrapper.isRequestedSessionIdFromUrl();
	}

	public boolean isRequestedSessionIdFromURL() {
		return strutsRequestWrapper.isRequestedSessionIdFromURL();
	}

	public boolean isRequestedSessionIdValid() {
		return strutsRequestWrapper.isRequestedSessionIdValid();
	}

	public boolean isSecure() {
		return strutsRequestWrapper.isSecure();
	}

	public boolean isUserInRole(String arg0) {
		return strutsRequestWrapper.isUserInRole(arg0);
	}

	public void removeAttribute(String name) {
		strutsRequestWrapper.removeAttribute(name);
	}

	public void setAttribute(String name, Object value) {
		strutsRequestWrapper.setAttribute(name, value);
	}

	public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
		strutsRequestWrapper.setCharacterEncoding(arg0);
	}

	public void setRequest(ServletRequest arg0) {
		strutsRequestWrapper.setRequest(arg0);
	}

	public String toString() {
		return strutsRequestWrapper.toString();
	}

}
