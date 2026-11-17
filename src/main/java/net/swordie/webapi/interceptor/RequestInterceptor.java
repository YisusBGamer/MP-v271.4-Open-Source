package net.swordie.webapi.interceptor;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.WriterInterceptor;
import jakarta.ws.rs.ext.WriterInterceptorContext;

import java.io.IOException;

@Provider
public class RequestInterceptor implements WriterInterceptor {
    @Override
    public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext) throws IOException, WebApplicationException {
        var headers = writerInterceptorContext.getHeaders();

        headers.putSingle("Access-Control-Allow-Origin", "*");
        headers.putSingle("Access-Control-Allow-Methods", "*");
        headers.putSingle("Access-Control-Allow-Headers", "*");
        if (net.swordie.ms.ServerConstants.WEB_NO_SEARCH_ENGINES) {
            headers.putSingle("X-Robots-Tag", "noindex, nofollow");
        }

        writerInterceptorContext.proceed();
    }
}
