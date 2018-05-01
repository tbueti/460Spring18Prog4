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
import java.util.ArrayList;

@Controller
public class MainController {

	@Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

		// Responds to request made to the
		// controller for answering query
		// 1: What are the manager names and
		// telephone numbers of each Residence Hall?
		// Collects the query results into a list of
		// strings that represent each row
     @GetMapping("/managers")
    public String managers(@RequestParam(name="name", required=false, defaultValue="World") String sampleText, Model model){
		String sql = "Select first_name,last_name, phone from staff join resHall on staff.Location = ResHall.hall_id";
		List<Manager> rows = jdbcTemplate.query(sql, new RowMapper<Manager>(){
                    public Manager mapRow(ResultSet rs, int rowNum)
                                                    throws SQLException {
						Manager manager = new Manager();
						manager.firstname = rs.getString(1);
						manager.lastname = rs.getString(2);
						manager.phone = rs.getString(3);
						return manager;
                        }
                    });
		model.addAttribute("managers", rows);

        return "managers";
    }

		// Responds to request made to the controller
		// by the client for answering query 2:
		// What are the numbers of students in each
		// category currently living in Residence Halls and
		// Furnished Apartments? Collects the query
		// results by performing 6 separate queries and
		// determining which of those students have active leases
		// and displaying the total number of each to the DOM.
    @GetMapping("/leases")
    public String leases(Model model){

				// Query strings
				String fyRH = "Select start_date, duartion from lease, ResHall, Room, Student where lease.room_no=room.room_no and lease.res_apt_id=room.res_apt_id and Student.student_id=lease.student_id and Student.category = 'First Year' and lease.res_apt_id in (Select hall_id from ResHall)";
				String fyFA = "Select start_date, duartion from lease, ResHall, Room, Student where lease.room_no=room.room_no and lease.res_apt_id=room.res_apt_id and Student.student_id=lease.student_id and Student.category = 'First Year' and lease.res_apt_id not in (Select hall_id from ResHall)";
				String ugRH = "Select start_date, duartion from lease, ResHall, Room, Student where lease.room_no=room.room_no and lease.res_apt_id=room.res_apt_id and Student.student_id=lease.student_id and Student.category = 'Undergraduate' and lease.res_apt_id in (Select hall_id from ResHall)";
				String ugFA = "Select start_date, duartion from lease, ResHall, Room, Student where lease.room_no=room.room_no and lease.res_apt_id=room.res_apt_id and Student.student_id=lease.student_id and Student.category = 'Undergraduate' and lease.res_apt_id not in (Select hall_id from ResHall)";
				String pgRH = "Select start_date, duartion from lease, ResHall, Room, Student where lease.room_no=room.room_no and lease.res_apt_id=room.res_apt_id and Student.student_id=lease.student_id and Student.category = 'Postgraduate' and lease.res_apt_id in (Select hall_id from ResHall)";
				String pgFA = "Select start_date, duartion from lease, ResHall, Room, Student where lease.room_no=room.room_no and lease.res_apt_id=room.res_apt_id and Student.student_id=lease.student_id and Student.category = 'Postgraduate' and lease.res_apt_id not in (Select hall_id from ResHall)";

				// The counts
				int fyRHCount = 0;
				int fyFACount = 0;
				int ugRHCount = 0;
				int ugFACount = 0;
				int pgRHCount = 0;
				int pgFACount = 0;

				// Vars
				int i = 0;

				// Run the queries
				// First Year Students in Residence Halls
				List<LeaseSummary> fyRHrows = jdbcTemplate.query(fyRH, new RowMapper<LeaseSummary>(){
					public LeaseSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
						LeaseSummary ls = new LeaseSummary(rs.getString(1), rs.getString(2));
						return ls;
		      }
		    });

				// First Year Students in Furnished Apartments
				List<LeaseSummary> fyFArows = jdbcTemplate.query(fyFA, new RowMapper<LeaseSummary>(){
					public LeaseSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
						LeaseSummary ls = new LeaseSummary(rs.getString(1), rs.getString(2));
						return ls;
		      }
		    });

				// Undergraduates in Residence Halls
				List<LeaseSummary> ugRHrows = jdbcTemplate.query(ugRH, new RowMapper<LeaseSummary>(){
					public LeaseSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
						LeaseSummary ls = new LeaseSummary(rs.getString(1), rs.getString(2));
						return ls;
		      }
		    });

				// Undergraduates in Furnished Apartments
				List<LeaseSummary> ugFArows = jdbcTemplate.query(ugFA, new RowMapper<LeaseSummary>(){
					public LeaseSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
						LeaseSummary ls = new LeaseSummary(rs.getString(1), rs.getString(2));
						return ls;
		      }
		    });

				// Postgraduates in Residence Halls
				List<LeaseSummary> pgRHrows = jdbcTemplate.query(pgRH, new RowMapper<LeaseSummary>(){
					public LeaseSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
						LeaseSummary ls = new LeaseSummary(rs.getString(1), rs.getString(2));
						return ls;
		      }
		    });

				// Postgraduates in Furnished Apartments
				List<LeaseSummary> pgFArows = jdbcTemplate.query(pgFA, new RowMapper<LeaseSummary>(){
					public LeaseSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
						LeaseSummary ls = new LeaseSummary(rs.getString(1), rs.getString(2));
						return ls;
		      }
		    });

				// Count FYRH Tuples
				for (i = 0; i < fyRHrows.size(); i++) {

					// If the student is still living there
					if (fyRHrows.get(i).isActive()) {
						fyRHCount++;
					}
				}

				// Count FYFA Tuples
				for (i = 0; i < fyFArows.size(); i++) {

					// If the student is still living there
					if (fyFArows.get(i).isActive()) {
						fyFACount++;
					}
				}

				// Count UGRH Tuples
				for (i = 0; i < ugRHrows.size(); i++) {

					// If the student is still living there
					if (ugRHrows.get(i).isActive()) {
						ugRHCount++;
					}
				}

				// Count UGFA Tuples
				for (i = 0; i < ugFArows.size(); i++) {

					// If the student is still living there
					if (ugFArows.get(i).isActive()) {
						ugFACount++;
					}
				}

				// Count pgRH Tuples
				for (i = 0; i < pgRHrows.size(); i++) {

					// If the student is still living there
					if (pgRHrows.get(i).isActive()) {
						pgRHCount++;
					}
				}

				// Count pgFA Tuples
				for (i = 0; i < pgFArows.size(); i++) {

					// If the student is still living there
					if (pgFArows.get(i).isActive()) {
						pgFACount++;
					}
				}

				// Create the resulting list
				List<LeaseCount> summaries = new ArrayList<LeaseCount>();
				summaries.add(new LeaseCount("First Year", fyRHCount, fyFACount));
				summaries.add(new LeaseCount("Undergraduate", ugRHCount, ugFACount));
				summaries.add(new LeaseCount("Postgraduate", pgRHCount, pgFACount));

				// Change the DOM
        model.addAttribute("leases", summaries);
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
                          @RequestParam(name="cat", required=true) String cat,
                          @RequestParam(name="advisor", required=true) String advisor,
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
        System.out.println("cat = " + cat);
        System.out.println("advisor = " + advisor);
        System.out.println("major = " + major);
        System.out.println("minor = " + minor);

		String sql = "select MAX(student_id) from student";
		int sid = jdbcTemplate.queryForObject(sql, Integer.class) +1;
		System.out.println("new sid = " + sid);
		jdbcTemplate.update(
			"INSERT INTO Student (student_id, First_name, Last_name, Address, Phone, Email,Gender, DOB, Category, Major, Minor, advisor_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
			sid,fname,lname,addr,phone,email,gender,dob,cat,major,minor,advisor
		);
        return "complete";
    }


    @GetMapping("/staff")
    public String staff(
                          @RequestParam(name="fname", required=true) String fname,
                          @RequestParam(name="lname", required=true) String lname,
                          @RequestParam(name="gender", required=true) String gender,
                          @RequestParam(name="dob", required=true) String dob,
                          @RequestParam(name="title", required=true) String title,
                          @RequestParam(name="location", required=true) String location)
    {
        System.out.println("fname = " + fname);
        System.out.println("lname = " + lname);
        System.out.println("gender = " + gender);
        System.out.println("dob = " + dob);
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
