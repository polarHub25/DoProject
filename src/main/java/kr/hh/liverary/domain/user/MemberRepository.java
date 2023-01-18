package kr.hh.liverary.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //생성된 사용자인지 처음 가입자인지 판단 메소드
    Optional<Member> findByEmailAndProvider(String email, String provider);
}
