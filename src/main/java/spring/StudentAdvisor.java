//==========================================================================================================================//
//                                                                               STUDENT_ADVISOR                                                                                   //
//==========================================================================================================================//
//==========================================================================================================================//
// Author: Jon Ingram                                                                                                                                                       //
// NetID: jpingram                                                                                                                                                                 //
// File: StudentAdvisor.java                                                                                                                                                 //
// Date: 5/01/2018                                                                                                                                                               //
// Description: Holds the resulting tuple values for query 4 (student names (and their advisors'
//     contact email) of students who live on campus)                                                                                                //
//==========================================================================================================================//

package spring;

// Student Advisor Class
public class StudentAdvisor {

   // Class members
   public String sfirstName;
   public String slastName;
   public String afirstName;
   public String alastName;
   public String aEmail;

   // Constructor
   public StudentAdvisor(String fn, String ln, String afn, String aln, String email) {

      try {
         this.sfirstName = fn;
         this.slastName = ln;
         this.afirstName = afn;
         this.alastName = aln;
         this.aEmail = email;
         
      } catch (Exception e) {

      }
   }
}