package com.hackday.timeline.member.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.member.repository.MemberRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "/application.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {
	@Autowired
	private MemberRepository memberRepository;

	@Test
	public void member_save_test() {
		Member member = new Member();
		member.setUserId("kwon");
		member.setUserPw("1234");
		member.setUserName("권대환");
		Member saveMember = memberRepository.save(member);
		assertThat(saveMember.getUserNo(), is(notNullValue()));
	}

}
