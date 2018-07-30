/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine_p2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;



/**
 *
 * @author khaledd
 */
public class Searchengine_p2 {

    /**
     * @param args the command line arguments
     */
        
        public static void main(String[] args) {
            HashMap<String , HashSet<String> > HM;
        Scanner  S1 = new Scanner(System.in);
        String s1 = S1.nextLine();  
        Database D1 = new Database("root","");
        Queryprocessor  Q1 = new Queryprocessor(D1);
       Q1.set_String(s1);
       HM= Q1.populateHashMap();
       Ranker R = new Ranker(D1,HM);
      R.CalcIDF();
        R.CalcTF();
        R.getWordTable("footbal");
   }
}