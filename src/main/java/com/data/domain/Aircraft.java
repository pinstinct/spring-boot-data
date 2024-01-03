package com.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;

/*@Data: 롬복에서 getter, setter, equals(), hashCode(), toString() 메서드를 생성
* @NoArgsConstructor: 롬복에서 매개변수가 없는 생성자를 만들도록 지시
* @AllArgsConstructor: 롬복에서 각 멤버 변수의 매개변수가 있는 생성자를 만들도록 지시
* @JsonIgnoreProperties(ignoreUnknown = true): JSON 응답 필드 중에서 클래스에 상응하는 멤버 변수가 없는 경우,
* Jackson 역직렬화 매커니즘이 이를 무시하도록 함
* @Id: 어노테이션이 달린 멤버 변수가 DB 고유 식별자를 가지도록 지정
* @JsonProperty: 멤버 변수를 다른 이름이 붙은 JSON 필드와 연결
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public String getLastSeenTime() {
        return lastSeenTime.toString();
    }

    /*Instant: 날짜+시간+UTC
    * LocalDateTime: 날짜+시간
    * ZonedDateTime: 날짜+시간+timezone
    * 글로벌 서비스 개발 시, Instant 혹은 ZonedDateTime 클래스 사용
    * 단일 리전 서비스 개발 시, LocalDateTime 사용
    * */
    public void setLastSeenTime(String lastSeenTime) {
        if (null != lastSeenTime) {
            this.lastSeenTime = Instant.parse(lastSeenTime);
        } else {
            this.lastSeenTime = Instant.ofEpochSecond(0);
        }
    }

    public String getPosUpdateTime() {
        return posUpdateTime.toString();
    }

    public void setPosUpdateTime(String posUpdateTime) {
        if (null != posUpdateTime) {
            this.posUpdateTime = Instant.parse(posUpdateTime);
        } else {
            this.posUpdateTime = Instant.ofEpochSecond(0);
        }
    }

    public String getBds40SeenTime() {
        return this.bds40SeenTime.toString();
    }

    public void setBds40SeenTime(String bds40SeenTime) {
        if (null != bds40SeenTime) {
            this.bds40SeenTime = Instant.parse(bds40SeenTime);
        } else {
            this.bds40SeenTime = Instant.ofEpochSecond(0);
        }
    }
}
