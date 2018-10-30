package com.hzh.demo.util;

import com.alibaba.fastjson.JSON;
import com.hzh.demo.DTO.SessionDataBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

@Slf4j
public class AppUserContext {

	private static ThreadLocal<SessionDataBo> local = new ThreadLocal<>();

    private static ThreadLocal<String> currentToken = new ThreadLocal<String>();
	
	public static String getCurrentToken() {
		return currentToken.get();
	}
	
	public static void setCurrentToken(String token) {
		currentToken.set(token);
	}

	public static void emptyToken() {
		currentToken.remove();
	}

	public static SessionDataBo getCurrentUser() {
		SessionDataBo user = local.get();
		Assert.notNull(user, "Unable to get User!");
		return user;
	}
	
	public static String getUserNo() {
		return getCurrentUser().getUserNo();
	}

	public static boolean isExistsUser() {
		return local.get() != null;
	}

	public static void setCurrentUser(SessionDataBo user) {
		log.info("current user :" + JSON.toJSON(user) + " ,current thread Id:"+Thread.currentThread().getId());
		local.set(user);
	}

	public static void emptyUser() {
		local.remove();
	}

	public static void logout() {
		emptyUser();
		emptyToken();
	}

	public static void login(String token, SessionDataBo user) {
		setCurrentToken(token);
		setCurrentUser(user);
	}

	public static boolean isExistsToken(String token) {
		return token.equals(currentToken.get());
	}

	public static void main(String[] args) {
		log.info("{}", AppUserContext.isExistsUser());

		AppUserContext.setCurrentUser(new SessionDataBo());

		log.info("{}", AppUserContext.isExistsUser());

		AppUserContext.emptyUser();

		log.info("{}", AppUserContext.isExistsUser());

	}
}