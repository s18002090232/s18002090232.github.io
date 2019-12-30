package cn.wzvtc.soft;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://127.0.0.1:5500"},allowCredentials = "true")
@RestController
public class TestController {

   @Autowired
   LvliRepository lvliRepository;

    @RequestMapping(value = "/addlvli")
    public void addlvli(String lvli) {
       Lvli lvli1=new Lvli("1800",lvli);
       this.lvliRepository.save(lvli1);
    }

    @RequestMapping(value = "/lvlilist")
    public List<Lvli> addlvli() {

       return this.lvliRepository.findAll();
    }

    @RequestMapping(value = "/userinfo")
    public Map<String, String> userinfo(HttpServletRequest httpServletRequest) {

        Map<String, String> resultMap = new HashMap<>();
        //判断是否已经登陆,判断SESSION里面是否有我的用户名信息
        if (httpServletRequest.getSession().getAttribute("loginnumber") != null) {
            resultMap.put("myname", (String) httpServletRequest.getSession(true).getAttribute("loginnumber"));
            resultMap.put("mynumber", (String) httpServletRequest.getSession(true).getAttribute("username"));
        }
        return resultMap;
    }

    @RequestMapping(value = "/login")
    public Map<String, String> login(@RequestBody Map<String, String> map, HttpServletRequest httpServletRequest) {
        String password = map.get("password");
        String number = map.get("number");
        Map<String, String> resultMap = new HashMap<>();
        if (number!=null && number.equals(password)) {
            //将用户名存入到session中
            httpServletRequest.getSession().setAttribute("loginnumber", number);
            httpServletRequest.getSession().setAttribute("username", number);
            resultMap.put("result", "success");
        }
        return resultMap;
    }

    @RequestMapping(value = "/logout")
    public void logout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().removeAttribute("loginnumber");
    }

}