package metjelentes;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
    user:metjelentes
    password:nSAsSYDs1t5bUd1w
*/
public class dbConnect {
    private Connection myConn;
    public dbConnect(){
        try{
            myConn= DriverManager.getConnection("jdbc:mysql://localhost:3306/metjelentes","metjelentes","nSAsSYDs1t5bUd1w");
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public ResultSet getResult(String sql){
        try{
            Statement myStmt=this.myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery(sql);
            return myRs;
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public boolean insertToSql(String table,String value){
        //table: "name(rownames)" example: "users(username,password)"
        //value: "Values(values)" example: "VALUES ('username','password')"
        if(table.equals("") || value.equals("")){
            return false;
        }
        try{
            Statement myStmt=this.myConn.createStatement();
            myStmt.executeUpdate("INSERT INTO "+table +" " +value);
            return true;
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        return false;
    }
    
    public boolean deleteFromSql(String statement){
        try{
            String query = "Delete From "+statement; 
            PreparedStatement preparedStmt = this.myConn.prepareStatement(query);
            preparedStmt.execute();
            return true;
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return false;
    }
    
    public void close(){
        try {
            this.myConn.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
