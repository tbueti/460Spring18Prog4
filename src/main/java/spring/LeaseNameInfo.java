//==========================================================================================================================//
//                                                                               LEASE_NAME_INFO                                                                                   //
//==========================================================================================================================//
//==========================================================================================================================//
// Author: Jon Ingram                                                                                                                                                       //
// NetID: jpingram                                                                                                                                                                 //
// File: LeaseNameInfo.java                                                                                                                                                 //
// Date: 5/01/2018                                                                                                                                                               //
// Description: Holds the resulting tuple values for query 3                                                                                                //
//==========================================================================================================================//

package spring;
import java.util.Date;
import java.text.SimpleDateFormat;

// Lease Name Info Class
public class LeaseNameInfo {

   // Class members
   public String firstName;
   public String lastName;
   public int hallId;
   public int roomNo;
   public double rate;
   public Date dueDate;

   // Constructor
   //first_name, last_name, res_apt_id, room_no, rate, due_date
   public LeaseNameInfo(String fn, String ln, int hid, int rno, double r, String dd) {

      try {
         this.firstName = fn;
         this.lastName = ln;
         this.hallId = hid;
         this.roomNo = rno;
         this.rate = r;
         SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
         this.dueDate = df.parse(dd);
      } catch (Exception e) {

      }
   }

   // Returns true if the due date is
   // earlier than today's date and false
   // if otherwise.
   public boolean isLate() {

     // Get the current date
     Date today = new Date();

     // Compare today's date to the due date
     if (dueDate.compareTo(today) < 0) {

       return true;
     }

     return false;
   }































}
