package com.hackday.timeline.member.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.member.repository.MemberRepository;
import com.hackday.timeline.member.vo.MemberVO;
import com.hackday.timeline.subscription.repository.SubsRepository;

@Service
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final SubsRepository subsRepository;

	@Autowired
	public MemberServiceImpl(MemberRepository memberRepository, SubsRepository subsRepository) {
		this.memberRepository = memberRepository;
		this.subsRepository = subsRepository;
	}

	@Override
	public void register(Member member) throws Exception {
		Member memberEntity = new Member();
		memberEntity.setUserId(member.getUserId());
		memberEntity.setUserPw(member.getUserPw());
		memberEntity.setUserName(member.getUserName());

		memberRepository.save(memberEntity);
	}

	@Override
	public Member read(Long userNo) throws Exception {
		return memberRepository.getOne(userNo);
	}

	@Override
	public void remove(Long userNo) throws Exception {
		memberRepository.deleteById(userNo);
	}

	@Override
	public void modify(Member member) throws Exception {
		Member userEntity = memberRepository.getOne(member.getUserNo());
		userEntity.setUserName(member.getUserName());
		memberRepository.save(userEntity);
	}

	@Override
	public List<MemberVO> listAll(Long userNo) throws Exception {
		List<Member> memberList = memberRepository.findAll();
		List<Object[]> valueArray = subsRepository.memberSubsList(userNo);
		List<MemberVO> voList = new ArrayList<>();
		Map<Long, Long> map = new HashMap<>();

		for (Object[] array : valueArray) {
			map.put((Long)array[1], (Long)array[0]);
		}

		for (Member m : memberList) {
			if (map.containsKey(m.getUserNo())) {
				voList.add(new MemberVO(m.getUserNo(), m.getUserId(), m.getUserName(), true, map.get(m.getUserNo())));
			} else {
				voList.add(new MemberVO(m.getUserNo(), m.getUserId(), m.getUserName(), false));
			}
		}
		return voList;
	}

}
