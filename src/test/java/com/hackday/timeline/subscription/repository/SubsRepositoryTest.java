package com.hackday.timeline.subscription.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.subscription.domain.Subscription;

@DataJpaTest
@TestPropertySource(locations = "/application.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SubsRepositoryTest {

	@Autowired
	private SubsRepository subsRepository;

	@Test
	public void member_save_test() {
		Subscription subs = new Subscription();
		Member member1 = new Member();
		member1.setUserNo(1L);
		Member member2 = new Member();
		member2.setUserNo(2L);
		subs.setMember(member1);
		subs.setSubsMember(member2);
		Subscription saveSubs = subsRepository.save(subs);
		assertEquals(saveSubs.getSubsNo(), is(notNullValue()));
	}

}
