package spring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

    @GetMapping("/managers")
    public String managers(@RequestParam(name="name", required=false, defaultValue="World") String sampleText, Model model){
		String sql = "Select first_name,last_name, phone from staff join resHall on staff.Location = ResHall.hall_id";
		List<String> rows = jdbcTemplate.query(sql, new RowMapper<String>(){
                    public String mapRow(ResultSet rs, int rowNum) 
                                                    throws SQLException {
						String res = rs.getString(1) + " " + rs.getString(2) + "	" + rs.getString(3);
                        return res;
                        }
                    });
		model.addAttribute("managers", rows);
	
        return "managers";
    }

    @GetMapping("/leases")
    public String managers(Model model){
        model.addAttribute("sampleText", "wow");//sets up sampleText variable for leases.html
        model.addAttribute("sampleText", "such wow\nhello");
        System.out.println("\n\n\ntest\n\n\n");
        return "leases";
    }

    @GetMapping("/dStudent")
    public String deleteStudent( @RequestParam(name="sid", required=true) String sid)
    {
        System.out.println("sid = " + sid);
        return "complete";
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
                          @RequestParam(name="minor", required=false) String minor)
    {
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


    @GetMapping("/staff")
    public String staff(
                          @RequestParam(name="fname", required=true) String fname,
                          @RequestParam(name="lname", required=true) String lname,
                          @RequestParam(name="gender", required=true) String gender,
                          @RequestParam(name="dob", required=true) String dob,
                          @RequestParam(name="cat", required=true) String cat,
                          @RequestParam(name="title", required=true) String title,
                          @RequestParam(name="location", required=true) String location)
    {
        System.out.println("fname = " + fname);
        System.out.println("lname = " + lname);
        System.out.println("gender = " + gender);
        System.out.println("dob = " + dob);
        System.out.println("cat = " + cat);
        System.out.println("title = " + title);
        System.out.println("location = " + location);
        return "complete";
    }

    @GetMapping("/nlease")
    public String staff(
                        @RequestParam(name="fname", required=true) String fname,
                        @RequestParam(name="lname", required=true) String lname,
                        @RequestParam(name="sid", required=true) String sid,
                        @RequestParam(name="rid", required=true) String rid,
                        @RequestParam(name="rate", required=true) String rate)
    {
        System.out.println("fname = " + fname);
        System.out.println("lname = " + lname);
        System.out.println("sid = " + sid);
        System.out.println("rid = " + rid);
        System.out.println("rate = " + rate);
        return "complete";
    }

    @GetMapping("/uRent")
    public String staff(
                        @RequestParam(name="resaptid", required=true) String resaptid,
                        @RequestParam(name="newRent", required=true) String newRent)
    {
        System.out.println("resaptid = " + resaptid);
        System.out.println("newRent = " + newRent);
        return "complete";
    }


}
