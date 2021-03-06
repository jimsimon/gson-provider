package com.fenix.rs;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
 
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class GsonJSONProvider implements MessageBodyWriter<Object>,
    MessageBodyReader<Object> {
 
  private static final String UTF_8 = "UTF-8";
 
  private Gson gson;
 
  private Gson getGson() {
    if (gson == null) {
      final GsonBuilder gsonBuilder = new GsonBuilder();
      gson = gsonBuilder.create();
    }
    return gson;
  }
 
  @Override
  public boolean isReadable(Class<?> clazz, Type type,
      Annotation[] annotations, MediaType mediaType) {
    return true;
  }
 
  @Override
  public Object readFrom(Class<Object> clazz, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException {
    try (InputStreamReader streamReader = new InputStreamReader(entityStream, UTF_8)) {
      return getGson().fromJson(streamReader, type);
    }
  }
 
  @Override
  public boolean isWriteable(Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType) {
    return true;
  }
 
  @Override
  public long getSize(Object object, Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType) {
    return -1;
  }
 
  @Override
  public void writeTo(Object object, Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws UnsupportedEncodingException, IOException {
    try (OutputStreamWriter writer = new OutputStreamWriter(entityStream, UTF_8)) {
      getGson().toJson(object, type, writer);
    }
  }
}
