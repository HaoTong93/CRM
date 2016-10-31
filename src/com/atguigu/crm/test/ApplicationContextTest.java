package com.atguigu.crm.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.crm.entity.User;
import com.atguigu.crm.mapper.UserMapper;

public class ApplicationContextTest {

	private ApplicationContext ctx = null;
	private UserMapper userMapping = null;
	
	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		userMapping = ctx.getBean(UserMapper.class);
	}
	
	@Test
	public void testGetSalesChance() {
		User user = userMapping.getUserByName("a");
		System.out.println(user.getName());
	}

}
