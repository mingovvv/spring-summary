package spring4;

import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {
	public static void main(String[] args) {
		
		// 순수 자바언어로 객체 생성
//		TranspotationWalk transpotationWalk = new TranspotationWalk();
//		transpotationWalk.move();
		
		// xml 파일에 의한 스프링 컨테이너가 관리하는 빈 꺼내쓰기
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationContext.xml");
		TranspotationWalk walk =  ctx.getBean("walk", TranspotationWalk.class);
		walk.move();
	
		// 사용한 자원에 대한 반환
		ctx.close();
	}
}
