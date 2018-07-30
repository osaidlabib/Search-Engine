/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine_p2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;


/**
 *
 * @author khaledd
 */
public class Queryprocessor {
   
    private String[] Querystring;
    private String[] StemmedQueryString;
    private HashMap<String , HashSet<String> > Hm;
    private Porterstemmer Port;
    private Database Db;
    
    
    public Queryprocessor( Database d1) {
        Hm = new HashMap();
        this.Db = d1;
        this.Port = new Porterstemmer();
    }
   public void set_String(String s)
   {
       Querystring = s.split(" ");
   }
   
   private void stemm_string(){
       StemmedQueryString = new String [Querystring.length];
       int cnt = 0;
       for(String  x : Querystring){
              Port.add(x.toCharArray(),x.length());
              Port.stem();
              StemmedQueryString[cnt++] = Port.toString();
              Port.clean();
         }
   }
   public HashMap<String , HashSet<String> > populateHashMap(){
       Hm.clear();
       String  url ;
       stemm_string();
       for(String X : StemmedQueryString){
           Hm.put(X, new HashSet());
           ResultSet res =Db.getUrls(X);
           if(!res.equals(null))
           {
               try{
                   while(res.next())
                  {
                     url = res.getString(1);
                     Hm.get(X).add(url);
                  }
               }
               catch (SQLException e)
               {
                   System.err.print(e);
               }
           }
    
       }
       return Hm;
   }
}
