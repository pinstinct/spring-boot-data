package com.data.repository;

import com.data.domain.Aircraft;
import org.springframework.data.repository.CrudRepository;

/*객체의 키와 함께 저장할 객체 유형을 제공해 repository 인터페이스를 생성*/
public interface AircraftRepository extends CrudRepository<Aircraft, Long> {
}
