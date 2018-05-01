package spring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
public class MainController {

	@Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        String sql = "insert into aakashrathore.people values (?)";
        jdbcTemplate.update(sql, name);
        return "greeting";
    }
    
    @GetMapping("/managers")
    public String managers(@RequestParam(name="name", required=false, defaultValue="World") String sampleText, Model model){
        model.addAttribute("sampleText", sampleText);//sets up sampleText variable for managers.html
        System.out.println("\n\n\ntest\n\n\n");
        System.out.println(sampleText);
        return "managers";
    }
    
    @GetMapping("/leases")
    public String managers(Model model){
        model.addAttribute("sampleText", "wow");//sets up sampleText variable for leases.html
        System.out.println("\n\n\ntest\n\n\n");
        return "leases";
    }

    
    @GetMapping("/student")
    public String student(
                          @RequestParam(name="fname", required=true) String fname,
                          @RequestParam(name="lname", required=true) String lname,
                          @RequestParam(name="addr", required=true) String addr,
                          @RequestParam(name="phone", required=true) String phone,
                          @RequestParam(name="email", required=true) String email,
                          @RequestParam(name="gender", required=true) String gender,
                          @RequestParam(name="dob", required=true) String dob,
                          @RequestParam(name="major", required=true) String major,
                          @RequestParam(name="minor", required=true) String minor){
        System.out.println("fname = " + fname);
        System.out.println("lname = " + lname);
        System.out.println("addr = " + addr);
        System.out.println("phone = " + phone);
        System.out.println("email = " + email);
        System.out.println("gender = " + gender);
        System.out.println("dob = " + dob);
        System.out.println("major = " + major);
        System.out.println("minor = " + minor);
        return "complete";
    }
}

