package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

// 홈 컨트롤러
@Controller
@Slf4j  // logger 사용 : 로그 찍힘
public class HomeController {

    @RequestMapping("/") // 기본 홈 url
    public String home() {
        log.info("home controller");
        return "home"; // home 뷰 파일로 이동
    }
}
