package com.brms.book.service;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.brms.book.Book;
import com.brms.book.dao.BookDao;

public class BookRegisterService implements InitializingBean, DisposableBean{

	@Autowired
	private BookDao bookDao;
	
	public BookRegisterService() { }
	
	public void register(Book book) {
		bookDao.insert(book);
	}
	
	public void initMethod() {
		System.out.println("BookRegisterService 빈(Bean)객체 생성 단계");
	}
	
	public void destroyMethod() {
		System.out.println("BookRegisterService 빈(Bean)객체 소멸 단계");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("빈 객체 소멸시 출력");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("빈 객체 생성시 출력");
	}
}