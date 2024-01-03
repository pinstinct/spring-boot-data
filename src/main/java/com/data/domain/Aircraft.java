package com.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;

/*@Data: 롬복에서 getter, setter, equals(), hashCode(), toString() 메서드를 생성
* @NoArgsConstructor: 롬복에서 매개변수가 없는 생성자를 만들도록 지시
* @AllArgsConstructor: 롬복에서 각 멤버 변수의 매개변수가 있는 생성자를 만들도록 지시
* @JsonIgnoreProperties(ignoreUnknown = true): JSON 응답 필드 중에서 클래스에 상응하는 멤버 변수가 없는 경우,
* Jackson 역직렬화 매커니즘이 이를 무시하도록 함
* @Id: 어노테이션이 달린 멤버 변수가 DB 고유 식별자를 가지도록 지정
* @JsonProperty: 멤버 변수를 다른 이름이 붙은 JSON 필드와 연결
* @RedisHash: 레디스 해시에 저장될 aggregate root임을 표시,
* @RedisHash는 @Entity 어노테이션이 JPA 객체에 수행하는 기능과 유사한 기능을 수행
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash
@JsonIgnoreProperties(ignoreUnknown = true)
public class Aircraft {
    @Id
    private Long id;
    private String callsign, squawk, reg, flightno, route, type, category;
    private int altitude, heading, speed;
    @JsonProperty("vert_rate")
    private int vertRate;
    @JsonProperty("selected_altitude")
    private int selectedAltitude;
    private double lat, lon, barometer;
    @JsonProperty("polar_distance")
    private double polarDistance;
    @JsonProperty("polar_bearing")
    private double polarBearing;
    @JsonProperty("is_adsb")
    private boolean isADSB;
    @JsonProperty("is_on_ground")
    private boolean isOnGround;
    @JsonProperty("last_seen_time")
    private Instant lastSeenTime;
    @JsonProperty("pos_update_time")
    private Instant posUpdateTime;
    @JsonProperty("bds40_seen_time")
    private Instant bds40SeenTime;

    /*Instant 타입 멤버 변수를 위해 필요했던 명시적 접근자와 변경자 제거
    * 스프링 데이터의 repository 지원에 있는 변환기가 복잡한 타입 변환을 쉽게 처리하기 때문에 */
}
