package com.hit.geek.thackmaster.utils;

import com.google.gson.Gson;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;
import java.util.List;

public class JsonResponseParser implements ResponseParser {

	@Override
	public void checkResponse(UriRequest request) throws Throwable {
		// TODO Auto-generated method stub
	}

	@Override
	public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
		// TODO Auto-generated method stub
		if (resultClass == List.class) {
			return new Gson().fromJson(result,resultType);
		} else {
			return new Gson().fromJson(result, resultClass);
		}
	}

}
