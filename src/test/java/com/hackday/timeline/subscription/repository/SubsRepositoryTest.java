package com.hackday.timeline.subscription.repository;

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
import com.hackday.timeline.subscription.domain.Subscription;
import com.hackday.timeline.subscription.repository.SubsRepository;

@RunWith(SpringRunner.class)
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
		assertThat(saveSubs.getSubsNo(), is(notNullValue()));
	}

}
