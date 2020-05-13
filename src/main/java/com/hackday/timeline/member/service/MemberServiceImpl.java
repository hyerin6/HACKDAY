package com.hackday.timeline.member.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.member.repository.MemberRepository;
import com.hackday.timeline.subscription.repository.SubsRepository;
import com.hackday.timeline.subscription.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberRepository repository;

	@Autowired
	SubsRepository subsRepository;

	@Override
	public void register(Member member) throws Exception {
		Member userEntity = new Member();
		userEntity.setUserId(member.getUserId());
		userEntity.setUserPw(member.getUserPw());
		userEntity.setUserName(member.getUserName());
		repository.save(userEntity);
	}

	@Override
	public Member read(Long userNo) throws Exception {
		return repository.getOne(userNo);
	}

	@Override
	public void remove(Long userNo) throws Exception {
		repository.deleteById(userNo);
	}

	@Override
	public void modify(Member member) throws Exception {
		Member userEntity = repository.getOne(member.getUserNo());
		userEntity.setUserName(member.getUserName());
		repository.save(userEntity);
	}

	@Override
	public List<MemberVO> listAll(Long userNo) throws Exception {
		List<Member> memberList = repository.findAll();
		List<Object[]> valueArray = subsRepository.memberSubsList(userNo);
		List<MemberVO> voList = new ArrayList<>();
		Map<Long, Integer> map = new HashMap<>();
		for (Object[] array : valueArray) {
			map.put((Long)array[2], 0);
		}

		for (Member m : memberList) {
			if (map.containsKey(m.getUserNo())) {
				voList.add(new MemberVO(m.getUserNo(), m.getUserId(), m.getUserName(), true));
			} else {
				voList.add(new MemberVO(m.getUserNo(), m.getUserId(), m.getUserName(), false));
			}
		}
		return voList;
	}

}
