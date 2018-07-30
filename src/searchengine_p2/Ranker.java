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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omar
 */
public class Ranker {
    private Database Db;
    private HashMap<String , HashSet<String> > Hm;
    private HashMap<String ,Double> IDF;
    private HashMap<String ,Double> wordperurl;
     private HashMap<Pair<String,String> ,Double> wordCount;
    private HashMap<Pair<String,String>,Double> TF;
     public Ranker( Database d1, HashMap<String , HashSet<String> > Hash1) {
         this.Db=d1;
         Hm=Hash1;
         
     }
     
     public void CalcIDF ()
             
     {  
         IDF = new HashMap();
         for (String key : Hm.keySet()) {
                       Double  x= (Db.getCountDocuments(key,"word_cnt","word")/5000);
                       IDF.put(key,x) ;
                       System.out.println(IDF.get(key));  
            }
     }
     
    public void CalcTF()
    {
       
        System.out.println("TF :");
        TF =new HashMap();
        getWordTablePerPage();
        for (String key : Hm.keySet()) {
            getWordTable(key);
            for(String url : Hm.get(key))
            {
                Pair<String,String> pa = new Pair(key,url);
                Double count = wordCount.get(pa);
                Double count2= wordperurl.get(url);
                double x= count/count2;
                TF.put(pa, x);
                System.out.println(TF.get(pa));
            }
            
            }
    }
     public void getWordTable(String word){
          wordCount = new HashMap();
         ResultSet S=Db.getWordURL(word);
         
        try {
            while(S.next())
            {
                Pair<String,String> pa = new Pair(word,S.getString("url_id"));
                Double count=1.0;
    if(wordCount.get(pa)!=null)
     count= wordCount.get(pa)+1;
                wordCount.put(pa,count);
            }
           return;
        } catch (SQLException ex) {
            Logger.getLogger(Ranker.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
     }
         public void getWordTablePerPage(){
          wordperurl = new HashMap();
         ResultSet S=Db.getWordperurl();
         
        try {
            while(S.next())
            {
               wordperurl.put(S.getString("url_id"),S.getDouble("cnt"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Ranker.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
     }
         
    
}
