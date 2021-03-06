package com.kotall.rms.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 页面响应entity
 *
 * @author aracwong
 * @date 2017年8月8日 上午11:40:42
 * @since 1.0.0
 */
public class Result extends HashMap<String, Object> {
	
	private static final long serialVersionUID = 1L;
	
	public Result() {
		put("code", 0);
	}
	
	public static Result error() {
		return error(500, "未知异常，请联系管理员");
	}
	
	public static Result error(String msg) {
		return error(500, msg);
	}
	
	public static Result error(int code, String msg) {
		Result result = new Result();
		result.put("code", code);
		result.put("msg", msg);
		return result;
	}

	public static Result ok(String msg) {
		Result result = new Result();
		result.put("msg", msg);
		return result;
	}
	
	public static Result ok(Map<String, Object> map) {
		Result result = new Result();
		result.putAll(map);
		return result;
	}
	
	public static Result ok() {
		return new Result();
	}

	@Override
	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public static Object badArgument(){
		return error(401, "参数不对");
	}

	public static Object badArgumentValue(){
		return error(402, "参数值不对");
	}

	public static Object updatedDateExpired(){
		return error(403, "更新数据已经失效");
	}

	public static Object updatedDataFailed(){
		return error(404, "更新数据失败");
	}

	public static Object unLogin(){
		return error(501, "请登录");
	}

	public static Object serious(){
		return error(502, "系统内部错误");
	}

	public static Object unSupport(){
		return error(503, "业务不支持");
	}
}