/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine_p2;

/**
 *
 * @author khaledd
 */

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

private String url = "jdbc:mysql://localhost:3306/searchengine";
private String user = "";
private String password = "";
private Statement statementQ;
public java.sql.Connection connection;


public Database(String User,String Password) 
{
    user=User;
    password=Password;
    try {
        // Load the Connector/J driver
        Object newInstance = Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url+"?useUnicode=yes&characterEncoding=UTF-8", user, password);
        statementQ = connection.createStatement();
        } catch (IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(Searchengine_p2.class.getName()).log(Level.SEVERE, null, ex);
        }     catch (ClassNotFoundException | SQLException ex) {
                  Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
              }
// Establish connection to MySQL
              
// Establish connection to MySQL       
}

 public ResultSet getUrls(String x){
     ResultSet Res = null ;
     try{
     Res = statementQ.executeQuery("Select DISTINCT url_id from word_cnt where word = '"+x+"'");
     }
     catch (SQLException e){
         System.err.println(e);
         return null;
     }
     return Res;
 }
 public double getCountDocuments(String s1,String table,String col)
{
    ResultSet urls = null;
    double counT=0.0;
              try {
                  urls = statementQ.executeQuery("select Count(DISTINCT  url_id) as count1 from "+table+" where "+col+" ='"+s1+"'");
                  
                  if( urls.first())
                  {
                      counT=urls.getInt("count1");
                  }

              } catch (SQLException ex) {
                  Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
              }
            
     return counT;
}
  public double getCountWord(String word,String url)
{
    ResultSet urls = null;
    double counT=0.0;
              try {
                  urls = statementQ.executeQuery("select Count(word) as count1 from word_cnt where word='"+word+"' and url_id='"+url+"'");
                  
                  if( urls.first())
                  {
                      counT=urls.getInt("count1");
                  }

              } catch (SQLException ex) {
                  Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
              }
            
     return counT;
}
  public double getURLwords(String url)
    {
    double count=0.0;
    ResultSet urls= null;
 try {
                  urls = statementQ.executeQuery("SELECT cnt FROM `word_cnt_perpage`  where `url_id` ='"+url+"'");
                  
                  urls.next();
                  
                  count=urls.getInt("cnt");
                  

              } catch (SQLException ex) {
                 // System.out.println("SELECT setID FROM `crawlerset`  where `URL` ='"+url2+"'");
                  Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
              }
            return count;
    }
    public ResultSet getWordURL(String word)
{
    ResultSet urls = null;
   
              try {
                  urls = statementQ.executeQuery("select * from word_cnt where word='"+word+"'");
                  
                  

              } catch (SQLException ex) {
                  Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
              }
            
     return urls;
}
        public ResultSet getWordperurl()
{
    ResultSet urls = null;
   
              try {
                  urls = statementQ.executeQuery("select * from word_cnt_perpage ");
                  
                  

              } catch (SQLException ex) {
                  Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
              }
            
     return urls;
}
}
