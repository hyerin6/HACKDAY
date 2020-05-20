package com.hackday.timeline.member.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackday.timeline.mail.service.MailService;
import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.member.domain.MemberAuth;
import com.hackday.timeline.member.dto.MemberDTO;
import com.hackday.timeline.member.repository.MemberRepository;
import com.hackday.timeline.subscription.dto.SubsDTO;
import com.hackday.timeline.subscription.repository.SubsRepository;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final SubsRepository subsRepository;
	private final MailService mailService;

	public MemberServiceImpl(MemberRepository memberRepository, SubsRepository subsRepository,
		MailService mailService) {
		this.memberRepository = memberRepository;
		this.subsRepository = subsRepository;
		this.mailService = mailService;
	}

	@Override
	public void register(Member member) throws Exception {

		MemberAuth memberAuth = new MemberAuth();
		memberAuth.setAuth("ROLE_USER");
		member.addAuth(memberAuth);

		memberRepository.save(member);

		mailService.mailSendWithMemberKey(member.getUserId());
	}

	@Override
	@Transactional(readOnly = true)
	public Member read(Long userNo) throws UsernameNotFoundException {
		return memberRepository.getOne(userNo);
	}

	@Override
	public void remove(Long userNo) throws UsernameNotFoundException {
		memberRepository.deleteById(userNo);
	}

	@Override
	public void modify(Member member) throws UsernameNotFoundException {
		Member userEntity = memberRepository.getOne(member.getUserNo());
		userEntity.setUserName(member.getUserName());
		memberRepository.save(userEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MemberDTO> listAll(Long userNo) throws Exception {
		List<Member> memberList = memberRepository.findAll();
		List<SubsDTO> valueArray = subsRepository.memberSubsList(userNo);
		List<MemberDTO> memberVOList = new ArrayList<>();
		Map<Long, Long> map = new HashMap<>();

		for (SubsDTO subsVO : valueArray) {
			map.put(subsVO.getUserNo(), subsVO.getSubsNo());
		}

		memberList.stream().forEach(member -> {

			if (map.containsKey(member.getUserNo())) {
				memberVOList.add(MemberDTO.builder()
					.userNo(member.getUserNo())
					.userId(member.getUserId())
					.userName(member.getUserName())
					.subsOk(true)
					.subsNo(map.get(member.getUserNo()))
					.build());
			} else {
				memberVOList.add(MemberDTO.builder()
					.userNo(member.getUserNo())
					.userId(member.getUserId())
					.userName(member.getUserName())
					.subsOk(false)
					.build());
			}
		});
		return memberVOList;
	}

	@Override
	public boolean userIdCheck(String userId) throws Exception {
		return memberRepository.userIdCheck(userId) == 1 ? true : false;
	}

}
