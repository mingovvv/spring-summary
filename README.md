# spring-summary
spring-summary

[Spring Framework 정리]

프레임워크
	-	개발을 보다 쉽게 사용하도록 만들어둔 틀이며 개발자는 규격화된 틀속에서 개발에 집중할 수 있음.
	
스프링 프레임워크 주요모듈
	-	spring-core		:	스프링 핵심인 DI와 IOC를 제공함
		spring-aop		:	AOP 기능 구현
		spring-jdbc		:	데이터베이스를 쉽게 다룰수 있는 기능
		spring-tx		:	스프링에서 제공하는 트랜잭션 관련 기능 제공
		spring-webmvc	:	스프링에서 제공하는 컨트롤러, 뷰, 모델을 이용한 스프링MVC
		spring-context	:	context를 의존하는 모듈이 많음
	  
스프링 컨테이너(IOC)
	-	스프링에서 객체를 생성하고 조립하는 컨테이너
	
빈(bean)
	-	스프링 컨테이너를 통해 생성되고 보관되는 객체
	
maven
	-	빌드 tool

스프링 구조
	-	src
		  ㄴ main
				ㄴ java			: 실제 자바언어를 통해 프로그래밍하는 폴더
				ㄴ resources		: 개발환경에 필요한 리소스의 모음 폴더
		  ㄴ test
		  
pom.xml
	-	메이븐 설정파일로 라이브러리를 연결해주고 빌드를 위한 플랫폼
	
applicaionContext.xml
	-	스프링 설정파일을 통해 스프링 컨테이너(IOC)에 객체를 Bean으로 등록하기
		new 키워드를 통해 객체생성을 하지않아도 사용할 수 있다.(즉, 메모리에 로드가 된 상태)
		
xml 파일안에 정의된 스프링 컨테이너 접근하기
	-	1. 스프링 컨테이너 생성하기(GenericXmlApplicationContext 객체 생성, xml파일 전용 객체)
		2. getBean("빈 이름", 빈 타입.class)
	-	xml을 통한 스프링 컨테이너 생성하기 	
				GenericXmlApplicationContext ctx = 
					new GenericXmlApplicationContext(classpath:xml파일경로);
		
DI(dependency Injection)
	-	의존성 주입
		객체에 의존해서 완전한 애플리케이션을 만듬.
		객체내부에서 의존하는 객체를 직접 생성하지 않고 스프링 컨테이너(IOC)에 빈으로 등록되어 있는 객체를 주입받아서 사용하는 기법.
		필드 / 생성자 / 세터 를 통해서 주입받을 수 있음.
		
DI를 사용하는 이유
	-	객체간의 종속성을 줄여 코드의 유연함을 가지고 유지보수가 수월해지기 때문
	
xml파일에서 다양한 의존 객체 주입
	-	생성자	:	<constructor-arg ref="bean_id"></constructor-arg>
	-	세터	:	<property name="'set'을 제외한 setter 메서드 네임" value="파라미터 값" / >
				ex> setUserName() -> userName
	-	그외	: List, Map 타입도 존재함
	
xml파일에서 스프링 설정 파일 분리
	-	applicaionContext.xml가 너무 길어져서 분리해보자
		
		1. 직접 추가(배열사용)
			appCtx1.xml / appCtx2.xml / appCtx3.xml 
			String[] appCtxs = {"classpath:appCtx1.xml", "classpath:appCtx2.xml", "classpath:appCtx3.xml"};
			GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(appCtxs);
		
		2. import 사용
			<import resource="classpath:appCtx2.xml"/>
			<import resource="classpath:appCtx3.xml"/>

빈(bean)의 범위
	-	sigleton(default)	
			스프링 컨테이너가 생성될때 객체를 미리 다 만들어 두고 필요할때 래퍼런스를 호출만 해서 참조하는것이기 때문에 '싱글톤' 범위를 지님.
			스프링 컨테이너에서 생성된 빈 객체는 동일한 타입에 대해서 기본적으로 한개만 생성된 다는 의미.
			
		prototype
			scope="prototype"
			싱글톤 범위의 반대 개념. 스프링 컨테이너에서 객체를 호출할때마다 새롭게 객체 생성을 함.

의존객체 자동 주입
	-	스프링 설정파일에서 의존객체를 주입할 때 <constructor-arg> 또는 <property> 태그로 의존 대상객체를 명시하지 않아도
		스프링 컨테이너가 자동으로 필요한 의존 대상 객체를 찾아서 의존 대상 객체가 필요한 객체에 주입을 해주는 기능
	
	-	1. @Autowired
			주입하려는 객체의 '타입'이 일치하는 객체를 자동으로 주입함.
			
			* 추가되는 스키마와 태그
			<beans xmlns="http://www.springframework.org/schema/beans"
				xmlns:context="http://www.springframework.org/schema/context"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
				xsi:schemaLocation="http://www.springframework.org/schema/beans 
					http://www.springframework.org/schema/beans/spring-beans.xsd 
					http://www.springframework.org/schema/context 
					http://www.springframework.org/schema/context/spring-context.xsd">
			
			// 등록된 빈들의 어노테이션을 활성화를 위해 사용
			<context:annotation-config /> 

			* 어노테이션이 사용 가능한 부분
				# field 주입
				# constructor 주입
				# setter 주입

	-	2. @Resource
			주입하려는 객체의 '이름'이 일치하는 객체를 자동으로 주입함.


			* 어노테이션이 사용 가능한 부분
				# field 주입
				# setter 주입

	-	3. @Inject
			Autowired와 동일
	
의존 객체 선택
	-	타입이 같은 다수의 빈 객체 중 의존 객체의 대상이 되는 객체를 선택하는 방법
			
			[@Autowired를 사용할 경우]
			
			* Qualifier 사용하기
				스프링 설정파일	: 	<bean id = "userDao" class = "com.devyu.dao.UserDao">
									<qualifier value = "first"> 
								</bean>
				프로세스 로직	: 	@Qualifier("first")

			* bean id와 래퍼런스 변수명 맞춰주기(선호되지 않는 방법)
				스프링 설정파일	:	<bean id = "first" class = "com.devyu.dao.UserDao">
				프로세스 로직	:	private UserDao first;

			[@Inject 사용할 경우]
			
			* Named 사용하기
				스프링 설정파일	: 	<bean id = "first" class = "com.devyu.dao.UserDao" />
				프로세스 로직	: 	@Named("first")


생명주기(Life cycle)
	-	스프링 컨테이너, 빈 객체 생명주기
			생성 :	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:appCtx.xml");
					
					스프링 컨테이너가 생성됨과 동시에 그 속에 빈 객체들도 생명주기를 함께한다.(빈 객체의 생명주기는 스프링 컨테이너의 생명주기와 일치)
					
			소멸	:	ctx.close();
		
	-	빈 객체 생명 주기와 함께하는 메서드를 사용하는 방법(인증절차, db연결 등등.. 사용됨)
		- 1. 자바 파일
			-	InitializingBean 인터페이스 구현
					afterPropertiesSet() 메서드를 통해 빈 객체 생성지점에 호출 가능.
					
			-	DisposableBean 인터페이스 구현
					destroy() 메서드를 통해 빈 객체 소멸시점에 호출 가능.
		
		- 2. 스프링 설정 파일
			-	<bean	id="bookRegisterService" class="com.brms.book.service.BookRegisterService" 
						init-method="initMethod" destroy-method="destroyMethod"/>
				
				init-method, destroy-method에서 사용한 이름과 동일한 이름으로 메서드를 생성해주면 됨.
				
어노테이션을 이용한 스프링 설정
	-	@Configuration	-	스프링 설정파일을 의미함.
	-	@Bean			-	빈 객체를 의미함.
		public StudentDao studentDao(){
			return new StudentDao();
		}
	-	List, Map 등 여러가지 타입의 객체를 주입해주는 경우도 존재함.
	-	Anotation을 통한 스프링 컨테이너 생성하기 	
				* AnnotationConfigApplicationContext ctx = 
					new AnnotationConfigApplicationContext(설정파일.class);
	
어노테이션을 이용한 스프링 설정 파일 분리(applicaionContext.xml가 너무 길어져서 분리해보자)
	-	1. 스프링 컨테이너 생성할때 파라미터로 모든 설정파일 넣어주기
		-	AnnotationConfigApplicationContext ctx = 
					new AnnotationConfigApplicationContext(설정파일1.class, 설정파일2.class, 설정파일3.class);
	
	-	2. @Import 사용하기
		-	하나의 @Configuration을 지닌 설정파일에 @Import 어노테이션을 추가해서 합체시키기
			@Import({MemberConfig2.class, MemberConfig3.class})
				
= web =

웹 프로그래밍 설계
	-	Model 1
			- Html파일 안에 Java코드랑 마크업 언어가 섞여있는 상태
			- 유지보수 굉장히 힘듬
	-	Model 2
			- Controller, Service, Dao의 역할을 모듈화하고 각자의 역할을 확실하게 함
			- 유지보수가 좋음
			- MVC가 정확하게 지켜진 웹 프로그래밍(거의 모든 프로젝트가 이렇게 적용됨)
	-	MVC 프레임워크 사이클
			- 1. 클라이언트의 페이지 요청
			- 2. DispatcherServlet 요청을 받고 HandlerMapping 에게 전달
			- 3. HandlerMapping 은 요청과 부합하는 Controller(@Controller) 를 찾아서 DispatcherServlet 에게 리턴
			- 4. DispatcherServlet 은 Controller 를 HandlerAdapter 에게 전달
			- 5. HandlerAdapter 은 요청과 부합하는 Controller 의 메서드(@RequestMapping)를 찾아서 실행시키고 그 결과값을 리턴
			- 6. DispatcherServlet 은 결과값을 ViewResolver 에게 전달
			- 7. ViewResolver 은 적합한 View를 찾아서 DispatcherServlet 에게 리턴
			- 8. DispatcherServlet 은 View에 데이터를 넣어 클라이언트에게 응답
			
	-	DispatcherServlet 설정하기
	
	-	Controller 객체
		-	<annotation-driven /> 스프링 컨테이너를 사용하기위해 필요한 빈 객체들을 자동으로 추가해줌.
		-	@Controller 어노테이션 추가 // 컨트롤러를 명시적으로 표현해줌.
		-	@RequestMapping 어노테이션 추가

web.xml
	-	웹 어플리케이션에서 최초 사용자의 요청이 발생하면 가장 먼저 DispatcherServlet이 요청을 받는다.
		따라서 개발자는 DispatcherServlet을 서블릿으로 등록해주어야 한다.
		사용자의 모든 요청을 받기위해 서블릿 맵핑 경로를 '/' 로 설정한다.
		
		<servlet>
			<servlet-name>appServlet</servlet-name>
			<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
				<init-param>
					<param-name>contextConfigLocation</param-name>
					<param-value>/WEB-INF/spring/appServlet/servlet-context.xml</param-value>
				</init-param>
			<load-on-startup>1</load-on-startup>
		</servlet>
			
		<servlet-mapping>
			<servlet-name>appServlet</servlet-name>
			<url-pattern>/</url-pattern>
		</servlet-mapping>
		
servlet-context.xml
	-	<!-- Annotation을 사용하기 위한 태그 -->
		<annotation-driven />

		<!-- css, js 등 자원의 사용을 의미 -->
		<resources mapping="/resources/**" location="/resources/" />

		<!-- Resolves views selected for rendering by @Controllers to .jsp resources in the /WEB-INF/views directory -->
		<beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
			<beans:property name="prefix" value="/WEB-INF/views/" />
			<beans:property name="suffix" value=".jsp" />
		</beans:bean>
	
		<context:component-scan base-package="com.bs.lec14" />

스프링 mvc 파일 구조
	-	프로젝트이름
			ㄴ src
				ㄴ main
					ㄴ java
					ㄴ resources
					ㄴ webapp
						ㄴ resources
						ㄴ WEB-INF
							ㄴ spring
							ㄴ views

UTF-8 한글 처리
	-	filter 적용하기
		<filter>
			<filter-name>encodingFilter</filter-name>
			<filter-class>
				org.springframework.web.filter.CharacterEncodingFilter     
			</filter-class>
			<init-param>
				<param-name>encoding</param-name>   
				<param-value>UTF-8</param-value>
			</init-param>
			<init-param>
				<param-name>forceEncoding</param-name>  
				<param-value>true</param-value>
			</init-param>
		</filter>    

		<filter-mapping>
			<filter-name>encodingFilter</filter-name>
			<url-pattern>/*</url-pattern>                 
		</filter-mapping>
				
@Component
	-	Component-Scan 에 의해 하위 패키지의 모든 파일에서
		@Component, @Controller, @Service, @Repository, @Configuration 어노테이션을 스프링 컨테이너 Bean 으로 등록해줌

view에서 전달하는 데이터 받기
	-	1. HttpServletRequest 객체 사용
		-	res.getParameter(name값);
				
	-	2. @RequestParam(name값) 타입 네임
		-	value, defaultValue, required 속성 사용 가능
				
	-	3. 커맨드 객체 사용
		-	Model의 커맨드 객체 사용해서 데이터 받기위해
		-	Member member
		-	객체의 setter를 사용해서 데이터를 주입하는 방식
		
@ModelAttribute
	-	커맨드 객체의 이름을 변경할 수 있고 변경된 이름은 view에서 커맨드 객체를 참조할때 사용할 수 있음.
			public String login(Member member)
				-> view) ${member.id}
			public String login(@ModelAttribute("mem") Member member)
				-> view) ${mem.id}
	
	-	글로벌 메서드로써의 활용(다른 경로의 메서드를 HandlerAdapter가 실행할때 같이 실행되는 메서드를 생성하는 것)
			
			@ModelAttribute("time")
			public String getCurrentTime(){
				return 시간;
			}
				-> view) ${time}

Model vs ModelAndView
	-	Model : 뷰에 데이터만 전달하기 위한 객체
		-	기존에 자주 사용함
			
			public String login(Model model, Member member){
				MemberDao memberDao = service.getAll();
				model.attribute("memberDao", memberDao);
				return model;
			}
			
	-	ModelAndView : 데이터와 뷰를 함께 전달하는 객체
		-	public ModelAndView login(Member member){
				ModelAndView mav = new ModelAndView();
				mav.addObject("memberDao", memberDao);
				mav.setViewName("login");
				return mav;
			}

Session vs Cookie
	-	클라이언트와 서버의 연결을 유지하는 방법
		Http 프로토콜은 클라이언트와 서버의 관계를 유지 하지 않는 특성을 지님( = connectionless)
	
	-	세션은 '서버'에서 연결 정보를 관리하고 쿠키는 '클라이언트'에서 연결 정보를 관리.
	
	-	Session
		-	1. HttpServletRequest를 이용한 세션 얻기
		
				@RequestMapping(value = "/login", method = RequestMethod.POST)
				public String memLogin(Member member, HttpServletRequest request) {
		
				Member mem = service.memberSearch(member);
				
				HttpSession session = request.getSession();
				session.setAttribute("member", mem);
				
				return "/member/loginOk";
				}
		
		-	2. HttpSession을 이용한 세션 얻기
		
				@RequestMapping(value = "/login", method = RequestMethod.POST)
				public String memLogin(Member member, HttpSession session) {
					
					Member mem = service.memberSearch(member);
					
					session.setAttribute("member", mem);
					
					return "/member/loginOk";
				}
		-	위 두가지 세션에 대한 차이점은 세션을 얻는 방식의 차이밖에 없음. 아무거나 사용해도 됨.
		-	세션 삭제 session.invalidate();
		
	-	Cookie
		-	HttpServletResponse 이용한 쿠키 설정
		
			@RequestMapping("/main")
			public String mallMain(Mall mall, HttpServletResponse response){
				
				Cookie genderCookie = new Cookie("gender", mall.getGender());
				
				if(mall.isCookieDel()) {
					genderCookie.setMaxAge(0);
					mall.setGender(null);
				} else {
					genderCookie.setMaxAge(60*60*24*30);
				}
				response.addCookie(genderCookie);
				
				return "/mall/main";
			}
		
		-	@CookieValue를 이용한 쿠키 사용
		
			@RequestMapping("/index")
			public String mallIndex(Mall mall, 
					@CookieValue(value="gender", required=false) Cookie genderCookie, 
					HttpServletRequest request) {
				
				if(genderCookie != null) 
					mall.setGender(genderCookie.getValue());
				
				return "/mall/index";
			}
				
리다이렉트 vs 인터셉트
	-	redirect	: 지금의 페이지에서 특정 페이지로 전환하는 방법
	-	intercept	: 리다이렉트를 사용하는 경우가 많을 경우 HandlerInterceptor를 이용할 수 있음
		-	사용자의 요청후, DispatcherServlet이 받고 HandlerInterceptor 인터페이스가 관여하게 됨.
			preHandle()		- 컨트롤러 작동하기 전
			postHandle()	- 컨트롤러 작동하고 난 후
			afterComplete()	- 컨트롤러와 뷰가 모두 작동 된 후
	
	-	servlet-context.xml 추가
	
		<interceptors>
			<interceptor>
				<mapping path="/member/modifyForm"/>
				<mapping path="/member/removeForm"/>
				<beans:bean class="com.bs.lec21.member.MemberLoginInterceptor"/>
			</interceptor>
		</interceptors>
			

		
