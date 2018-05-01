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
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

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

		//Query 3: List the names of students with outstanding unpaid invoices, identifying information for the Room/Apartment
//associated with the Invoice (including Residence Hall if necessary), and the amounts due. In addition,
//calculate the total outstanding debt of all unpaid invoices.

//Runs the query and then checks if the unpaid invoices
//were outstanding before adding it to the result list.
//Result list is then displayed.

@GetMapping("/unpaidInvoices")
public String invoices(Model model){

	 //STEP 1: STEP UP QUERY AND VARIABLES
	 double totalDebt = 0;
	 int i = 0;
	 String sql =   "SELECT first_name, last_name, res_apt_id, room_no, rate, due_date FROM Lease l, Invoice i WHERE l.lease_id = i.lease_id AND i.paid_date IS NULL";

	 //STEP 2: EXCECUTE QUERY AND STORE RESULTS
	 List<LeaseNameInfo> unpaidInvoices = jdbcTemplate.query(sql, new RowMapper<LeaseNameInfo>(){
			public LeaseNameInfo mapRow(ResultSet rs, int rowNum)
			throws SQLException {
				LeaseNameInfo ls = new LeaseNameInfo(rs.getString(1), rs.getString(2), rs.getInt(3),	rs.getInt(4), rs.getDouble(5), rs.getString(6));
				return ls;
		}
	 });

	 // Final query result
	 List<LeaseNameInfo> lateInvoices = new ArrayList<LeaseNameInfo>();

	 // Find all invoices that are late
	 for (i = 0; i < unpaidInvoices.size(); i++) {

		 // Determine if it's late
		 if (unpaidInvoices.get(i).isLate()) {
			 totalDebt += unpaidInvoices.get(i).rate;
			 lateInvoices.add(unpaidInvoices.get(i));
		 }
	 }

	 // Change the DOM
	 model.addAttribute("invoices", lateInvoices);
	 model.addAttribute("totalDebt", totalDebt);

	 return "unpaidInvoices";
}

//Query 4 (Custom Query 1): Of all of the students who live on-campus (in a Residence Hall),
//print the students full name as well as their advisors full name and email address.

@GetMapping("/advisors")
public String advisors(Model model){

   //STEP 1: STEP UP QUERY AND VARIABLES
   String sql =   "SELECT Student.first_name, Student.last_name, Advisor.first_name, Advisor.last_name, Advisor.email FROM Student, Advisor, Lease WHERE Student.advisor_id = Advisor.advisor_id and Lease.student_id = Student.student_id and Lease.res_apt_id in (select res_apt_id from ResHall)";

   //STEP 2: EXCECUTE QUERY AND STORE RESULTS
   List<StudentAdvisor> advisorList = jdbcTemplate.query(sql, new RowMapper<StudentAdvisor>(){
   public StudentAdvisor mapRow(ResultSet rs, int rowNum)
   throws SQLException {
      StudentAdvisor ls = new StudentAdvisor(rs.getString(1),
                                             rs.getString(2),
                                             rs.getString(3),
                                             rs.getString(4),
                                             rs.getString(5));
         return ls;
      }
   });
   model.addAttribute("advisors", advisorList);

   return "advisors";
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


        String sql = "select MAX(staff_id) from staff";
        int sid = jdbcTemplate.queryForObject(sql, Integer.class) +1;
        int loc = Integer.parseInt(location);
        System.out.println("new sid = " + sid);
        jdbcTemplate.update(
                            "INSERT INTO Staff (staff_id, first_name, last_name, gender, dob, job_title, location) VALUES (?, ?,?,?,?,?,?)",
                            sid,fname,lname,gender,dob, title, location
                            );


        return "complete";
    }

    @GetMapping("/nlease")
    public String staff(
                        @RequestParam(name="fname", required=true) String fname,
                        @RequestParam(name="lname", required=true) String lname,
                        @RequestParam(name="sid", required=true) String sid,
                        @RequestParam(name="rid", required=true) String rid,
                        @RequestParam(name="rapid", required=true) String rapid,
                        @RequestParam(name="rate", required=true) String rate,
                        @RequestParam(name="sDate", required=true) String sDate,
                        @RequestParam(name="duration", required=true) String duration)
    {
        System.out.println("fname = " + fname);
        System.out.println("lname = " + lname);
        System.out.println("sid = " + sid);
        System.out.println("rid = " + rid);
        System.out.println("rate = " + rate);

        String sql = "select MAX(lease_id) from lease";
        int lid = jdbcTemplate.queryForObject(sql, Integer.class) +1;
        int sidInt = Integer.parseInt(sid);
        int ridInt = Integer.parseInt(rid);
        int rapidInt = Integer.parseInt(rapid);
        int durationInt = Integer.parseInt(duration);

        System.out.println("new sid = " + sid);
        jdbcTemplate.update(
                            "INSERT INTO Lease(lease_id, res_apt_id, room_no, student_id, rate, start_date, duartion, first_name, last_name) VALUES (?,?,?,?,?,?,?,?,?)",
                            lid,rapidInt,ridInt,sidInt,rate, sDate,durationInt,fname,lname
                            );



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
