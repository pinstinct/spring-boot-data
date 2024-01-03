package com.data.component;

import com.data.domain.Aircraft;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/*PlaneFinderPoller 빈이 임무를 수행하기 위해서는 두 개의 다른 빈,
* 즉 RedisConnectionFactory(레디스 의존성을 추가했으므로 스프링 부트의 '자동 설정'에 의해 제공)와
* RedisTemplate인 RedisOperations의 구현체에 액세스해야 한다.
* */
@EnableScheduling
@Component
public class PlaneFinderPoller {
    private WebClient client = WebClient.create("http://localhost:7634/aircraft");

    private final RedisConnectionFactory connectionFactory;
    private final RedisOperations<String, Aircraft> redisOperations;

    PlaneFinderPoller(RedisConnectionFactory connectionFactory,
                      RedisOperations<String, Aircraft> redisOperations) {
        this.connectionFactory = connectionFactory;
        this.redisOperations = redisOperations;
    }

    @Scheduled(fixedRate = 1000)  // 폴링 빈도 1,000ms당 한번(초당 1번)
    private void pollPlanes() {
        /*이전에 저장된 항공기를 지우는 선언문
        * 자동연결된 ConnectionFactory로 데이터베이스에 연결하고,
        * 해당 연결로 서버 명령 flushDb()를 실행해 존재하는 모든 키를 삭제
        */
        connectionFactory.getConnection().serverCommands().flushDb();

        /*현 위치를 조회하고 저장하는 선언문
        * WebClient를 사용해 PaineFinder 서비스를 호출하고
        * 일정 범위 내에 있는 항공기와 현 위치 정보를 함께 조회
        * 응답 body는 Aircraft 객체의 Flux로 변환되며, 등록번호가 없는 Aircraft를 제거하고 필터링
        * Aircraft 스트림으로 변환된 후, 레디스 데이터베이스에 저장
        * (Flux는 리액티브 타입이지만, 여기서는 블로킹 없이 전달되는 객체 묶음)
        *  */
        client.get()
                .retrieve()
                .bodyToFlux(Aircraft.class)
                .filter(plane -> !plane.getReg().isEmpty())
                .toStream()
                .forEach(ac -> redisOperations.opsForValue().set(ac.getReg(), ac));

        /*최신 캡처의 결과를 보고하는 선언문
        * 레디스에 정의된 작업 몇 가지를 활용해 모든 키를 조회하고,
        * 각각의 키로 해당 항공기 값을 조회한 다음 출력
        * */
        redisOperations.opsForValue()
                .getOperations()
                .keys("*")
                .forEach(ac ->
                        System.out.println(redisOperations.opsForValue().get(ac)));
    }
}
