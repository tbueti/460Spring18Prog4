//==========================================================================================================================//
//                                                                               COUNT_AVG                                                                                  //
//==========================================================================================================================//
//==========================================================================================================================//
// Author: Jon Ingram                                                                                                                                                       //
// NetID: jpingram                                                                                                                                                                 //
// File: CountAvg.java                                                                                                                                                 //
// Date: 5/01/2018                                                                                                                                                               //
// Description: Holds the resulting tuple values for query 5 (given a reshall name, the number of students
//    and average rent at that reshall)                                                                                      //
//==========================================================================================================================//

package spring;

// Count/Avg Class
public class CountAvg {

   // Class members
   public double count;
   public double avg;

   // Constructor
   public CountAvg(String c, String a) {

      try {
         this.count = Double.parseDouble(c);
         this.avg = Double.parseDouble(a);

      } catch (Exception e) {

      }
   }
}
