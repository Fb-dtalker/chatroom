package com.fb.chatroom.filter;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssAndSqlHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private HttpServletRequest request;
    public XssAndSqlHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public String getParameter(String name){
        String value = request.getParameter(name);
        if (!StringUtils.isEmpty(value)){
            value = StringEscapeUtils.escapeHtml4(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name){
        String[] parameterValues = super.getParameterValues(name);
        if (parameterValues == null){
            return null;
        }
        for (int i=0; i < parameterValues.length; ++i){
            parameterValues[i] = StringEscapeUtils.escapeHtml4(parameterValues[i]);
        }
        return parameterValues;
    }
}
