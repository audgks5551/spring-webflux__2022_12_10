package com.example.springwebflux;

import com.google.auto.service.AutoService;
import reactor.blockhound.BlockHound;
import reactor.blockhound.integration.BlockHoundIntegration;

import java.io.FilterInputStream;
import java.util.ResourceBundle;

/**
 * 저는 spring2.5.0에 추가 된 spring.sql.init 을 사용(r2dbc 지원)하여 schema 를 생성합니다.
 * schema 파일을 읽을 때 RandomAccessFile.readBytes 를 이용하는데 해당 부분은 블록킹 코드지만 구동 시에만 발생하는 블록킹이므로 허용 처리 하였습니다.
 * 그리고 i18n 사용 시 Message를 가져오는 부분도 블록킹 허용 하였습니다.
 * 레디스 사용시 lettuceConnectionFactory.setEagerInitialization(true); 처리 해야 논블로킹으로 작동
 * 참고 사이트 (<a href="https://kevin-park.medium.com/springboot-webflux-with-blockhound-9a7711dab6a8">참고사이트</a>)
 */
@AutoService(BlockHoundIntegration.class)
public class CustomBlockHoundIntegration implements BlockHoundIntegration {
    @Override
    public void applyTo(BlockHound.Builder builder) {
        builder
                // r2dbc initScript FilterInputStream 처리 시 RandomAccessFile.readBytes blocking 허용
                .allowBlockingCallsInside(FilterInputStream.class.getName(), "read")
                // LocaleContextMessage ResourceBundle 처리 시 RandomAccessFile.readBytes 허용
                .allowBlockingCallsInside(ResourceBundle.class.getName(), "getBundle");

    }
}
