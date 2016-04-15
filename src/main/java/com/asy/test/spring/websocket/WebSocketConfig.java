package com.asy.test.spring.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.io.IOException;

@Configuration
@EnableWebSocket
//@EnableWebMvc
@Controller
public class WebSocketConfig /*extends WebMvcConfigurerAdapter*/ implements WebSocketConfigurer {

	CustomHandler customHandler = null;

/*	@Bean
	public WebSocketHandler aWebSocketHandler() {
		return new PerConnectionWebSocketHandler(MyHandler.class);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}*/

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(myHandler(), "/hello").setAllowedOrigins("*");//.withSockJS();
	}

	@Bean
	public WebSocketHandler myHandler() {
		return getCustomHandler();
		//return new PerConnectionWebSocketHandler(CustomHandler.class);
	}


	private CustomHandler getCustomHandler() {
		if (customHandler == null) {
			customHandler = new CustomHandler();
		}
		return customHandler;
	}


	@Scheduled(fixedDelay = 5000)
	public void sendAMessage() {
		try {
			getCustomHandler().sendMessage("testtesttest");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}