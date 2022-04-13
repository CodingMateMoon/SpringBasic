package hello.hellospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 아무데나 @Component가 있어도 되나요?
 *
 * @Target(ElementType.TYPE)
 * @Retention(RetentionPolicy.RUNTIME)
 * @Documented
 * @Inherited
 * @SpringBootConfiguration
 * @EnableAutoConfiguration
 * @ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
 *                @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
 * public @interface SpringBootApplication {
 *
 * @SpringBootApplication 이 있는 hello.hellospring과 동일하거나 하위 패키지만 컴포넌트 스캔. 어떤 설정을 하면 되지만 기본적으로는
 * 컴포넌트 스캔이 안됩니다.
 *
 * 스프링은 스프링 컨테이너에 스프링 빈을 등록할 때 기본적으로 싱글톤으로 등록합니다. (유일하게 하나만 등록해서 공유합니다.)
 * 예를 들어 멤버서비스말고 주문서비스가 있어서 그게 autowired로 멤버레포지토리를 달라고 하면 똑같은 인스턴스를 제공해줍니다. 메모리를 절약할 수 있습니다.
 * 따라서 같은 스프링 빈이면 모두 같은 인스턴스입니다. 설정으로 싱글톤이 아니게 설정할 수 있지만, 특별한 경우를 제외하면 싱글톤을 사용합니다.
 *
 */
@SpringBootApplication
public class HelloSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloSpringApplication.class, args);
	}

}

