package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /*
    슬래시(/)는 도메인 첫번째인 localhost:8080으로 들어오면 @GetMapping("/")로 연결된 public String home()이 호출됩니다.
    그리고 해당 함수안의 내용을 보고 src\main\resources\templates에 있는 home.html이 호출됩니다
    src\main\resources\static 의 index.html이 실행되지 않는 이유? -> 우선순위가 있습니다. 클라이언트 요청이 왔을 때
    스프링 컨테이너안에 있는 관련 컨트롤러를 먼저 찾고 없으면 static 파일을 찾아서 띄워줍니다.
    localhost:8080 요청이 있으면 먼저 컨트롤러에서 찾아서  @GetMapping("/")를 보고 해당 컨트롤러의 함수가 호출되고 home.html을 보여줍니다.

     */
    @GetMapping("/")
    public String home(){
        return "home";
    }

}
