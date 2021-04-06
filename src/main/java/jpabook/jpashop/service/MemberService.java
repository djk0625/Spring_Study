package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)  // JPA 데이터 변경 및 로직 - Transaction 안에서 실행되어야 함.
@RequiredArgsConstructor    // final이 있는 필드만 가지고, 생성자 만들어준다.
public class MemberService {

    private final MemberRepository memberRepository;

//    // 생성자 Injection, 중간에 수정 불가, Autowired 없어도 자동으로 Injection
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

//    @Autowired  // Setter Injection
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }     - 요새 잘 쓰이지 않음.

    // 회원 가입
    @Transactional  // 쓰기 동작은 readOnly = false 해줘야하지만, Default 값이 False 이기 때문에, 굳이 ReadOnly를 안 붙여줘도 된다.
    public Long join(Member member) {

        validateDuplicateMember(member);    // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 개별 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
