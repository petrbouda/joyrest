package org.joyrest.exception.processor;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.joyrest.context.ApplicationContextImpl;
import org.joyrest.exception.handler.InternalExceptionHandler;
import org.joyrest.exception.processor.exceptions.ContactException;
import org.joyrest.exception.processor.exceptions.FourthException;
import org.joyrest.exception.processor.exceptions.SecondException;
import org.joyrest.exception.processor.exceptions.ThirdException;
import org.joyrest.exception.type.RestException;
import org.joyrest.model.http.HeaderName;
import org.joyrest.model.http.HttpStatus;
import org.joyrest.stubs.RequestStub;
import org.joyrest.stubs.ResponseStub;
import org.joyrest.transform.StringReaderWriter;
import org.junit.Test;
import static org.joyrest.model.http.MediaType.JSON;
import static org.joyrest.model.http.MediaType.PLAIN_TEXT;
import static org.joyrest.routing.entity.RequestType.Req;
import static org.junit.Assert.assertEquals;

import static java.util.Collections.singletonList;

public class ExceptionProcessorImplTest {

    @Test
    public void handle_exact_exception() throws Exception {
        ApplicationContextImpl context = getApplicationContextOneHandler();

        ExceptionProcessor testedClass = new ExceptionProcessorImpl(context);
        ResponseStub response = new ResponseStub();
        testedClass.process(new SecondException(), new RequestStub(), response);

        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatus());
    }

    @Test
    public void handle_inheritance_exception() throws Exception {
        ApplicationContextImpl context = getApplicationContextOneHandler();

        ExceptionProcessor testedClass = new ExceptionProcessorImpl(context);
        ResponseStub response = new ResponseStub();
        testedClass.process(new ThirdException(), new RequestStub(), response);

        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatus());
    }

    @Test
    public void handle_twice_inheritance_exception() throws Exception {
        ApplicationContextImpl context = getApplicationContextOneHandler();

        ExceptionProcessor testedClass = new ExceptionProcessorImpl(context);
        ResponseStub response = new ResponseStub();
        testedClass.process(new FourthException(), new RequestStub(), response);

        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatus());
    }

    @Test
    public void handle_multiple_exception_in_tree() throws Exception {
        InternalExceptionHandler secondHandler = new InternalExceptionHandler(SecondException.class,
            (req, resp, ex) -> resp.status(HttpStatus.BAD_GATEWAY));

        InternalExceptionHandler thirdHandler = new InternalExceptionHandler(ThirdException.class,
            (req, resp, ex) -> resp.status(HttpStatus.CONFLICT));

        Map<Class<? extends Exception>, InternalExceptionHandler> map = new HashMap<>();
        map.put(ThirdException.class, thirdHandler);
        map.put(SecondException.class, secondHandler);

        ApplicationContextImpl context = new ApplicationContextImpl();
        context.setExceptionHandlers(map);

        ExceptionProcessor testedClass = new ExceptionProcessorImpl(context);
        ResponseStub response = new ResponseStub();
        testedClass.process(new FourthException(), new RequestStub(), response);

        assertEquals(HttpStatus.CONFLICT, response.getStatus());
    }

    @Test(expected = ContactException.class)
    public void handle_not_found() throws Exception {
        ApplicationContextImpl context = getApplicationContextOneHandler();

        ExceptionProcessor testedClass = new ExceptionProcessorImpl(context);
        ResponseStub response = new ResponseStub();
        testedClass.process(new ContactException(), new RequestStub(), response);
    }

    @Test
    public void handle_write_body_string_with_accept_charset() throws Exception {
        ApplicationContextImpl context = getApplicationContextWithBody();

        ResponseStub response = new ResponseStub();
        response.setOutputStream(new ByteArrayOutputStream());

        RequestStub request = new RequestStub();
        request.setAccept(singletonList(PLAIN_TEXT));
        Map<HeaderName, String> headers = request.getHeaders();
        headers.put(HeaderName.ACCEPT_CHARSET, "UTF-8");

        ExceptionProcessor testedClass = new ExceptionProcessorImpl(context);
        testedClass.process(new FourthException(), request, response);

        ByteArrayOutputStream outputStream = (ByteArrayOutputStream) response.getOutputStream();
        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatus());
        assertEquals("Well Done!!", outputStream.toString());
    }

    @Test
    public void handle_write_body_string_with_matched_accept() throws Exception {
        ApplicationContextImpl context = getApplicationContextWithBody();

        ResponseStub response = new ResponseStub();
        response.setOutputStream(new ByteArrayOutputStream());

        RequestStub request = new RequestStub();
        request.setAccept(singletonList(PLAIN_TEXT));
        request.setMatchedAccept(PLAIN_TEXT);

        ExceptionProcessor testedClass = new ExceptionProcessorImpl(context);
        testedClass.process(new FourthException(), request, response);

        ByteArrayOutputStream outputStream = (ByteArrayOutputStream) response.getOutputStream();
        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatus());
        assertEquals("Well Done!!", outputStream.toString());
    }

    @Test(expected = RestException.class)
    public void handle_write_body_string_no_writer() throws Exception {
        ApplicationContextImpl context = getApplicationContextWithBody();

        ResponseStub response = new ResponseStub();
        response.setOutputStream(new ByteArrayOutputStream());

        RequestStub request = new RequestStub();
        request.setAccept(singletonList(JSON));

        ExceptionProcessor testedClass = new ExceptionProcessorImpl(context);
        testedClass.process(new FourthException(), request, response);
    }

    private ApplicationContextImpl getApplicationContextWithBody() {
        InternalExceptionHandler secondHandler = new InternalExceptionHandler(SecondException.class,
            (req, resp, ex) -> {
                resp.status(HttpStatus.BAD_GATEWAY);
                resp.entity("Well Done!!");
            }, Req(String.class));
        secondHandler.addWriter(new StringReaderWriter());

        Map<Class<? extends Exception>, InternalExceptionHandler> map = new HashMap<>();
        map.put(SecondException.class, secondHandler);

        ApplicationContextImpl context = new ApplicationContextImpl();
        context.setExceptionHandlers(map);
        return context;
    }

    private ApplicationContextImpl getApplicationContextOneHandler() {
        InternalExceptionHandler handler = new InternalExceptionHandler(SecondException.class,
            (req, resp, ex) -> resp.status(HttpStatus.BAD_GATEWAY));

        Map<Class<? extends Exception>, InternalExceptionHandler> map = new HashMap<>();
        map.put(SecondException.class, handler);

        ApplicationContextImpl context = new ApplicationContextImpl();
        context.setExceptionHandlers(map);
        return context;
    }

}