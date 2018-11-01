
package dbsim;


import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
/*This is a databse simulation using the engine of SQLite

*/
public class Dbsim {
    //current location on developer device. Change is require for portability 
    static String  DBLoc = "C:\\Users\\William\\Documents\\NetBeansProjects\\dbsim\\database.db";
    
    //this is called by seveal methods to connect to the database 
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:"+DBLoc;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    //used to create starter database
    public static void createNewDatabase() {
        String url = "jdbc:sqlite:" + DBLoc;
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void createAcctTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:"+DBLoc;
        
        // SQL statement for creating an account table
        String sql = "CREATE TABLE accounts(\n"
                + "	username varchar(30) PRIMARY KEY,\n" /*username 30 char max*/
                + "	password varchar(20),\n"/*password 20 char max*/
                + "	fname varchar(30),\n"   /*names limited to 30 characters*/
                + "     lname varchar(30),\n"
                + "     address varchar(100),\n"/*address limited to 100 characters*/
                + "     city varchar(60),\n"    
                + "     state char(2),\n"       /*state abbreviation*/
                + "     zipCode varchar(5),\n"  /*zipcode does not include extensions*/
                + "     country char(3),\n"     /*Country code*/
                + "     DoB char(10),\n"        /* format to mm/dd/yyyy */
                + "     email varchar(50),\n"   /*character limit 60*/
                + "     cardnumber char(16),\n" /*0000 0000 0000 0000 (do not include spaces!!!)*/
                + "     cardType varchar(25),\n"/*Master card, Visa, etc*/
                + "     SecurityCode varchar(4),\n"/*security number on card*/
                + "     expirationDate char(5),\n"/* mm/yy */
                + "     pinNum varchar(6),\n"   /* 4-6 numbers long*/
                + "     acctType varchar(8)\n"  /*Customer vs admin*/
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
     //creates accounts
    public void newAccount(String inUsername, String inPassword, String inFname,
        String inLname, String inAddress, String inCity, String inState, 
        String inZip, String inCountry, String inDOB, String inEmail, 
        String inCreditCardNum, String inCreditCardType, String inSecurityCode, 
        String inExpDate, String inPinNum, String inAccountType){
        
        String sql = "INSERT INTO accounts(username, password, fname,"+
            "lname, address, city, state, zipCode, country, DoB,"+
            "email, cardnumber, cardType, SecurityCode," +
            "expirationDate, pinNum, acctType) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, inUsername);
            pstmt.setString(2, inPassword);
            pstmt.setString(3, inFname);
            pstmt.setString(4, inLname);
            pstmt.setString(5, inAddress);
            pstmt.setString(6, inCity);
            pstmt.setString(7, inState);
            pstmt.setString(8, inZip);
            pstmt.setString(9, inCountry);
            pstmt.setString(10, inDOB);
            pstmt.setString(11, inEmail);
            pstmt.setString(12, inCreditCardNum);
            pstmt.setString(13, inCreditCardType);
            pstmt.setString(14, inSecurityCode);
            pstmt.setString(15, inExpDate);
            pstmt.setString(16, inPinNum);
            pstmt.setString(17, inAccountType);

            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //checks if username is in the database
    public boolean usernameExists(String user){
        String sql = "SELECT username FROM accounts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                if(rs.getString("username").equals(user) ){
                    return true;
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    //get password using username
    public String getPassword(String user){
        String sql = "SELECT username, password FROM accounts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                if(rs.getString("username").equals(user)){
                    return rs.getString("password");
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updatePassword(String user, String password) {
        String sql = "UPDATE accounts SET password = ? "+
                "WHERE username = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, password);
            pstmt.setString(2, user);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //get first name
    public String getFname(String user){
        String sql = "SELECT username, fname FROM accounts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                if(rs.getString("username").equals(user)){
                    return rs.getString("fname");
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateFname(String user, String fname) {
        String sql = "UPDATE accounts SET fname = ? "+
                "WHERE username = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, fname);
            pstmt.setString(2, user);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //get last name
    public String getLname(String user){
        String sql = "SELECT username, lname FROM accounts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                if(rs.getString("username").equals(user)){
                    return rs.getString("lname");
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateLname(String user, String lname) {
        String sql = "UPDATE accounts SET lname = ? "+
                "WHERE username = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, lname);
            pstmt.setString(2, user);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //get address number and street
    public String getAddress(String user){
        String sql = "SELECT username, address FROM accounts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                if(rs.getString("username").equals(user)){
                    return rs.getString("address");
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateAddress(String user, String address) {
        String sql = "UPDATE accounts SET address = ? "+
                "WHERE username = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, address);
            pstmt.setString(2, user);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //get city address
    public String getCity(String user){
        String sql = "SELECT username, city FROM accounts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                if(rs.getString("username").equals(user)){
                    return rs.getString("city");
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateCity(String user, String city) {
        String sql = "UPDATE accounts SET city = ? "+
                "WHERE username = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, city);
            pstmt.setString(2, user);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // get state address
    public String getState(String user){
        String sql = "SELECT username, state FROM accounts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                if(rs.getString("username").equals(user)){
                    return rs.getString("state");
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateState(String user, String state) {
        String sql = "UPDATE accounts SET state = ? "+
                "WHERE username = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, state);
            pstmt.setString(2, user);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //get zip code
    public String getZipcode(String user){
        String sql = "SELECT username, zipCode FROM accounts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                if(rs.getString("username").equals(user)){
                    return rs.getString("zipCode");
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateZip(String user, String zip) {
        String sql = "UPDATE accounts SET zipCode = ? "+
                "WHERE username = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, zip);
            pstmt.setString(2, user);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //get country code
    public String getCountry(String user){
        String sql = "SELECT username, country FROM accounts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                if(rs.getString("username").equals(user)){
                    return rs.getString("country");
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateCountry(String user, String country) {
        String sql = "UPDATE accounts SET country = ? "+
                "WHERE username = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, country);
            pstmt.setString(2, user);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //get birthday
    public String getDoB(String user){
        String sql = "SELECT username, DoB FROM accounts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                if(rs.getString("username").equals(user)){
                    return rs.getString("DoB");
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateDoB(String user, String DoB) {
        String sql = "UPDATE accounts SET DoB = ? "+
                "WHERE username = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, DoB);
            pstmt.setString(2, user);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //get email address
    public String getEmail(String user){
        String sql = "SELECT username, email FROM accounts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                if(rs.getString("username").equals(user)){
                    return rs.getString("email");
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateEmail(String user, String email) {
        String sql = "UPDATE accounts SET email = ? "+
                "WHERE username = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, email);
            pstmt.setString(2, user);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //get card number
    public String getCardNumber(String user){
        String sql = "SELECT username, cardnumber FROM accounts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                if(rs.getString("username").equals(user)){
                    return rs.getString("cardnumber");
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateCardNumber(String user, String number) {
        String sql = "UPDATE accounts SET cardnumber = ? "+
                "WHERE username = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, number);
            pstmt.setString(2, user);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //card type
    public String getCardType(String user){
        String sql = "SELECT username, cardType FROM accounts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                if(rs.getString("username").equals(user)){
                    return rs.getString("cardType");
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateCardType(String user, String type) {
        String sql = "UPDATE accounts SET cardType = ? "+
                "WHERE username = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, type);
            pstmt.setString(2, user);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //security code
    public String getSecurityCode(String user){
        String sql = "SELECT username, SecurityCode FROM accounts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                if(rs.getString("username").equals(user)){
                    return rs.getString("SecurityCode");
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateSecurityCode(String user, String code) {
        String sql = "UPDATE accounts SET SecurityCode = ? "+
                "WHERE username = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, code);
            pstmt.setString(2, user);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //card expiration date
    public String getExpDate(String user){
        String sql = "SELECT username, expirationDate FROM accounts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                if(rs.getString("username").equals(user)){
                    return rs.getString("expirationDate");
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateExpDate(String user, String exp) {
        String sql = "UPDATE accounts SET expirationDate = ? "+
                "WHERE username = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, exp);
            pstmt.setString(2, user);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //pin number
    public String getPinNum(String user){
        String sql = "SELECT username, pinNum FROM accounts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                if(rs.getString("username").equals(user)){
                    return rs.getString("pinNum");
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updatePinNum(String user, String pin) {
        String sql = "UPDATE accounts SET pinNum = ? "+
                "WHERE username = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, pin);
            pstmt.setString(2, user);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
   //account type customer or admin
    public String getAcctType(String user){
        String sql = "SELECT username, acctType FROM accounts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                if(rs.getString("username").equals(user)){
                    return rs.getString("acctType");
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    //upadtes account information
    public void updateAccount( String inUsername, String inPassword, 
        String inFname, String inLname, String inAddress, String inCity, 
        String inState, String inZip, String inCountry, String inDOB, 
        String inEmail, String inCreditCardNum, String inCreditCardType, 
        String inSecurityCode, String inExpDate, String inPinNum, 
        String inAccountType) {
        String sql = "UPDATE accounts SET password = ? , fname = ? , lname = ? , "+
                "address = ? , city = ? , state = ? , zipCode = ? , country = ? , "+
                "DoB = ? , email = ? , cardnumber = ? , cardType = ? , "+
                "SecurityCode = ? , expirationDate = ? , pinNum = ? , acctType = ? "+
                "WHERE username = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, inPassword);
            pstmt.setString(2, inFname);
            pstmt.setString(3, inLname);
            pstmt.setString(4, inAddress);
            pstmt.setString(5, inCity);
            pstmt.setString(6, inState);
            pstmt.setString(7, inZip);
            pstmt.setString(8, inCountry);
            pstmt.setString(9, inDOB);
            pstmt.setString(10, inEmail);
            pstmt.setString(11, inCreditCardNum);
            pstmt.setString(12, inCreditCardType);
            pstmt.setString(13, inSecurityCode);
            pstmt.setString(14, inExpDate);
            pstmt.setString(15, inPinNum);
            pstmt.setString(16, inAccountType);
            pstmt.setString(17, inUsername);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //deletes account 
    public void deleteAccount(String user) {
        String sql = "DELETE FROM accounts WHERE username = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, user);
            // execute the delete statement
            pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //get account information (used only for testing)
    public void getAllAccountInfo(String user){
    
        System.out.println(getPassword(user));
        System.out.println(getFname(user));
        System.out.println(getLname(user));
        System.out.println(getAddress(user));
        System.out.println(getCity(user));
        System.out.println(getState(user));
        System.out.println(getZipcode(user));
        System.out.println(getCountry(user));
        System.out.println(getDoB(user));
        System.out.println(getEmail(user));
        System.out.println(getCardNumber(user));
        System.out.println(getCardType(user));
        System.out.println(getSecurityCode(user));
        System.out.println(getExpDate(user));
        System.out.println(getPinNum(user));
        System.out.println(getAcctType(user));
        
    }
    
    //creates table of products
    public static void createProductTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:"+DBLoc;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE products(\n"
                + "	id INTEGER PRIMARY KEY,\n" /*item id*/
                + "	name varchar(30),\n"/*item name*/
                + "	type varchar(10),\n"   /*item type (shirt, shoes, etc.*/
                + "     brand varchar(20),\n" //brand name
                + "     price DECIMAL(6,2),\n"//format to 2 decimal places max 
                                                // also max price 9999.99
                + "     picLink varchar(20),\n"//picture link
                + "     desc TEXT,\n" //item description 
                + "     pop INTEGER DEFAULT 0,\n"// item popularity
                + "     gender char(1)\n"// gender M, N, U(unisex), B (baby)
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //creates Ids for items
    public int createNewProductId(){
        String sql = "SELECT MAX(id) AS out FROM products";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            return rs.getInt("out")+1;
               
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }
    
    //new product, also returns the id number of the product
    //use this id for newStock(id, size, color); method
    public int newProduct(String name, String type, String brand, 
            double price, String piclink, String desc, String gender){
        
        String sql = "INSERT INTO products(id, name, type, brand, price, picLink, desc, gender)"
                + " VALUES(?,?,?,?,?,?,?,?)";
 
        int id=createNewProductId();
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, type);
            pstmt.setString(4, brand);
            pstmt.setDouble(5, price);
            pstmt.setString(6, piclink);
            pstmt.setString(7, desc);
            pstmt.setString(8, gender);

            
            pstmt.executeUpdate();
            return id;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
            return -1;
    }
    //item name
    public String getItemName(int id){
        String sql = "SELECT name FROM products WHERE id = ?";
        
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
            // set the value
            pstmt.setInt(1,id);
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                
                    return rs.getString("name");
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateItemName(int id, String name) {
        String sql = "UPDATE products SET password = ? "+
                "WHERE id = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, name);
            pstmt.setInt(2, id);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //item type
    public String getItemType(int id){
        String sql = "SELECT type FROM products WHERE id = ?";
        
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
            // set the value
            pstmt.setInt(1,id);
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                
                    return rs.getString("type");
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateItemType(int id, String type) {
        String sql = "UPDATE products SET type = ? "+
                "WHERE id = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, type);
            pstmt.setInt(2, id);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //item brand
    public String getItemBrand(int id){
        String sql = "SELECT brand FROM products WHERE id = ?";
        
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
            // set the value
            pstmt.setInt(1,id);
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                
                    return rs.getString("brand");
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateItemBrand(int id, String brand) {
        String sql = "UPDATE products SET brand = ? "+
                "WHERE id = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, brand);
            pstmt.setInt(2, id);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //item price
    public String getItemPrice(int id){
        String sql = "SELECT price FROM products WHERE id = ?";
        
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
            // set the value
            pstmt.setInt(1,id);
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                
                    return rs.getString("price");
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateItemPrice(int id, double price) {
        String sql = "UPDATE products SET price = ? "+
                "WHERE id = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setDouble(1, price);
            pstmt.setInt(2, id);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //picture link
    public String getItemPictureLink(int id){
        String sql = "SELECT picLink FROM products WHERE id = ?";
        
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
            // set the value
            pstmt.setInt(1,id);
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                
                    return rs.getString("picLink");
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateItemPrice(int id, String picLink) {
        String sql = "UPDATE products SET picLink = ? "+
                "WHERE id = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, picLink);
            pstmt.setInt(2, id);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //item description
    public String getItemDesc(int id){
        String sql = "SELECT desc FROM products WHERE id = ?";
        
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
            // set the value
            pstmt.setInt(1,id);
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                
                    return rs.getString("desc");
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public void updateItemDesc(int id, String desc) {
        String sql = "UPDATE products SET desc = ? "+
                "WHERE id = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, desc);
            pstmt.setInt(2, id);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //item populartity
    public String getItemPop(int id){
        String sql = "SELECT pop FROM products WHERE id = ?";
        
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
            // set the value
            pstmt.setInt(1,id);
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                
                    return rs.getString("pop");
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    public String getItemGender(int id){
        String sql = "SELECT gender FROM products WHERE id = ?";
        
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
            // set the value
            pstmt.setInt(1,id);
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                
                    return rs.getString("gender");
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "fail";
    }
    //update item by id
    public void updateItemDetails(int id, String name, String brand,
            double price, String piclink, String desc,int pop, String gender) {
        String sql = "UPDATE products SET name = ? , brand = ? , price = ? ,"+
                " picLink = ? , desc = ? , pop = ? , gender = ? "+
                "WHERE id = ? ";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, name);
            pstmt.setString(2, brand);
            pstmt.setDouble(3, price);
            pstmt.setString(4, piclink);
            pstmt.setString(5, desc);
            pstmt.setInt(6, pop);
            pstmt.setString(7, gender);
            pstmt.setInt(8, id);

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void getItemDetails(int id){
        System.out.println(getItemName(id));
        System.out.println(getItemType(id));
        System.out.println(getItemBrand(id));
        System.out.println(getItemPrice(id));
        System.out.println(getItemPictureLink(id));
        System.out.println(getItemDesc(id));
        System.out.println(getItemPop(id));
        System.out.println(getItemGender(id));
    }
    //removes items
    public void deleteItem(int id) {
        String sql = "DELETE FROM accountsproducts WHERE id = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setInt(1,id);
            // execute the delete statement
            pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //stock table
    public static void createStockTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:"+DBLoc;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE stock(\n"
                + "	id INTEGER,\n" /*item id*/
                + "	size varchar(10),\n"/*size*/
                + "	color varchar(10),\n"/*color*/
                + "     quantity INTEGER\n,"//total stock for specific combination of size and color
                + "     PRIMARY KEY (id, size, color)"
                + "     FOREIGN KEY (id) REFERENCES products (id) \n" +
                  "     ON DELETE CASCADE ON UPDATE NO ACTION"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //new item stock, use id searched or created from newStock();
    public void newStock(int id, String size, String color, int quantity){
        
        String sql = "INSERT INTO stock(id, size, color, quantity)"
                + " VALUES(?,?,?,?)";
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, size);
            pstmt.setString(3, color);
            pstmt.setInt(4, quantity);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //retuns an array containing all stock combinations in the format "size/color"
    //use substring to get individual components 
    public String[] getItemStockCombo(int id){
        String sql = "SELECT size, color FROM stock WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            // set the value
            pstmt.setInt(1,id);
            //arraylist to hold data
            ArrayList ar = new ArrayList();
            
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                ar.add(rs.getString("size")+"/"+rs.getString("color"));
            }
            String[] retAr = new String[ar.size()];
            for(int i=0;i<ar.size();i++){
                retAr[i]=(String)ar.get(i);
            }
            return retAr;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            String[] retAr={""};
            return retAr;
        }
    }
    //get current stock
    public int getItemStock(int id,String size,String color){
        String sql = "SELECT quantity FROM stock "
                + "WHERE id = ? AND size = ? AND color = ?";
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
            // set the value
            pstmt.setInt(1,id);
            pstmt.setString(2,size);
            pstmt.setString(3,color);
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                
                    return rs.getInt("quantity");
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }
    //quanity >0 inceaase quantity
    //quantity <0 decrease quantity 
    public void updateStock(int id, String size, String color, int quantity) {
        String sql = "UPDATE stock SET quantity = ? "+
                "WHERE id = ? AND size = ? AND color = ?";
                
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setInt(1, getItemStock(id, size, color)+quantity);
            pstmt.setInt(2, id);
            pstmt.setString(3, size);
            pstmt.setString(4, color );

            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //basic searches such as category and name
    //all searched return an array of product ids that you can get information with
    //search for all products with this type and returns an array of their id's
    //on fail returns array with -1
    public int[] searchBasicType(String type){
        String sql = "SELECT id FROM products WHERE type = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            // set the value
            pstmt.setString(1,type);
            //arraylist to hold data
            ArrayList ar = new ArrayList();
            
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                ar.add(rs.getInt("id"));
            }
            int[] retAr = new int[ar.size()];
            for(int i=0;i<ar.size();i++){
                retAr[i]=(int)ar.get(i);
            }
            return retAr;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            int[] retAr={-1};
            return retAr;
        }
    }
    //search for all products with this string in it's name and returns an array of their id's
    //on fail returns array with -1
    public int[] searchBasicName(String name){
        String sql = "SELECT name, id FROM products";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            //arraylist to hold data
            ArrayList ar = new ArrayList();
            
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                if(rs.getString("name").contains(name))
                ar.add(rs.getInt("id"));
            }
            int[] retAr = new int[ar.size()];
            for(int i=0;i<ar.size();i++){
                retAr[i]=(int)ar.get(i);
            }
            return retAr;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            int[] retAr={-1};
            return retAr;
        }
    }
    public int[] searchBasicBrand(String brand){
        String sql = "SELECT id FROM products WHERE brand = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            // set the value
            pstmt.setString(1,brand);
            //arraylist to hold data
            ArrayList ar = new ArrayList();
            
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                ar.add(rs.getInt("id"));
            }
            int[] retAr = new int[ar.size()];
            for(int i=0;i<ar.size();i++){
                retAr[i]=(int)ar.get(i);
            }
            return retAr;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            int[] retAr={-1};
            return retAr;
        }
    }
    public int[] searchBasicPriceMax(Double priceMax){
        String sql = "SELECT id FROM products WHERE price < ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            // set the value
            pstmt.setDouble(1, priceMax);
            //arraylist to hold data
            ArrayList ar = new ArrayList();
            
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                ar.add(rs.getInt("id"));
            }
            int[] retAr = new int[ar.size()];
            for(int i=0;i<ar.size();i++){
                retAr[i]=(int)ar.get(i);
            }
            return retAr;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            int[] retAr={-1};
            return retAr;
        }
    }
    public int[] searchBasicPriceMin(Double priceMin){
        String sql = "SELECT id FROM products WHERE price > ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            // set the value
            pstmt.setDouble(1, priceMin);
            //arraylist to hold data
            ArrayList ar = new ArrayList();
            
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                ar.add(rs.getInt("id"));
            }
            int[] retAr = new int[ar.size()];
            for(int i=0;i<ar.size();i++){
                retAr[i]=(int)ar.get(i);
            }
            return retAr;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            int[] retAr={-1};
            return retAr;
        }
    }
    public int[] searchBasicPriceRange(Double priceMax, Double priceMin){
        String sql = "SELECT id FROM products WHERE price < ? AND price > ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            // set the value
            pstmt.setDouble(1, priceMax);
            pstmt.setDouble(2, priceMin);
            //arraylist to hold data
            ArrayList ar = new ArrayList();
            
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                ar.add(rs.getInt("id"));
            }
            int[] retAr = new int[ar.size()];
            for(int i=0;i<ar.size();i++){
                retAr[i]=(int)ar.get(i);
            }
            return retAr;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            int[] retAr={-1};
            return retAr;
        }
    }
    public int[] searchBasicSize(String size){
        String sql = "SELECT DISTINCT id FROM stock WHERE size = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            // set the value
            pstmt.setString(1, size);
            //arraylist to hold data
            ArrayList ar = new ArrayList();
            
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                ar.add(rs.getInt("id"));
            }
            int[] retAr = new int[ar.size()];
            for(int i=0;i<ar.size();i++){
                retAr[i]=(int)ar.get(i);
            }
            return retAr;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            int[] retAr={-1};
            return retAr;
        }
    }
    public int[] searchBasicColor(String color){
        String sql = "SELECT DISTINCT id FROM stock WHERE color = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            // set the value
            pstmt.setString(1, color);
            //arraylist to hold data
            ArrayList ar = new ArrayList();
            
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                ar.add(rs.getInt("id"));
            }
            int[] retAr = new int[ar.size()];
            for(int i=0;i<ar.size();i++){
                retAr[i]=(int)ar.get(i);
            }
            return retAr;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            int[] retAr={-1};
            return retAr;
        }
    }
    //checks if individual product has a certain rating
    public boolean isAboveStar(int id, int star){
        String sql = "SELECT AVG(star) as star FROM review WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            // set the value
            pstmt.setInt(1, id);
            //arraylist to hold data
            
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                if(rs.getDouble("star")>star)
                    return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            int[] retAr={-1};
            
        }
        return false;
    }
    //database table of reviews
    public static void createReviewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:"+DBLoc;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE review(\n"
                + "	id INTEGER,\n" /*item id*/
                + "	username varchar(30),\n"/*usename of commenter*/
                + "	comment TEXT,\n"/*actual comment*/
                + "     star INTEGER,\n"//basic reviw based stars 1 to 5
                + "     date char(10),\n"//format to mm/dd/yyyy
                + "     time char(5),\n"//format to (00:00)-(23:59)
                + "     PRIMARY KEY (id, username, comment, date, time),\n"
                + "     FOREIGN KEY (id) REFERENCES products (id) \n" 
                + "     ON DELETE CASCADE ON UPDATE NO ACTION "
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //id is same as item 
    public void newReview(int id, String username, String comment, int star ){
        
        String sql = "INSERT INTO review(id, username, comment, star, date, time)"
                + " VALUES(?,?,?,?,?,?)";
        String dateTime = newReviewTime();
        String date= dateTime.substring(0, 10); 
        String time= dateTime.substring(11, 16);

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, username);
            pstmt.setString(3, comment);
            pstmt.setInt(4, star);
            pstmt.setString(5, date);
            pstmt.setString(6, time);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public String newReviewTime(){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy_HH:mm");
        java.util.Date date = new java.util.Date();
        return dateFormat.format(date);
    }
    //this prints a review
    public void reviewOut(int id){
        String sql = "SELECT id, username, comment, star, date, time FROM review "
                + "WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            // set the value
            pstmt.setInt(1,id);
            
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                //prints output for each review
                //these print statements are what I suggest you edit
                    System.out.println(rs.getInt("id"));
                    System.out.println(rs.getString("username"));
                    System.out.println(rs.getString("comment"));
                    System.out.println(rs.getInt("star"));
                    System.out.println(rs.getString("date"));
                    System.out.println(rs.getString("time"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }
    
    public static void createCartTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:"+DBLoc;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE cart(\n"
                + "	username varchar(30),\n"/*usename*/
                + "	itemId INTEGER,\n"/*id of item to buy*/
                + "     size varchar(10),\n"//size of item
                + "     color varchar(10),\n"//format to mm/dd/yyyy
                + "     quantity INTEGER,\n"
                + "     PRIMARY KEY (username, itemId, size, color),\n"
                + "     FOREIGN KEY (username) REFERENCES accounts (username) \n" 
                + "     ON DELETE CASCADE ON UPDATE NO ACTION "
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //adds new items to cart
    public void newCartItem(String username, int itemId, String size, String color, int quantity){
        
        String sql = "INSERT INTO cart(username, itemId, size, color, quantity)"
                + " VALUES(?,?,?,?,?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, itemId);
            pstmt.setString(3, size);
            pstmt.setString(4, color);
            pstmt.setInt(5, quantity);
            
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //returns an array with the following format
    // itemId/size/color/quantity 
    //I suggest using string.split("/") to get individual values
    public String[] showCartItems(String username){
        String sql = "SELECT itemId, size, color, quantity FROM cart "
                + "WHERE username = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            // set the value
            pstmt.setString(1,username);
            
            //arraylist to hold data
            ArrayList ar = new ArrayList();
            
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                    ar.add(rs.getInt("itemId")+"/"+rs.getString("size")+"/"+
                            rs.getString("color")+"/"+rs.getInt("quanitiy"));
            }
            String[] retAr = new String[ar.size()];
            for(int i=0;i<ar.size();i++){
                retAr[i]=(String)ar.get(i);
            }
            return retAr;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            String[] retAr={""};
            return retAr;
        }
    }
    //removes specific item
    public void removeCartItem(String username, int itemId, String size, String color){
        String sql = "DELETE FROM cart "
                + "WHERE username = ? AND itemId = ? AND size = ? AND color = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, username);
            pstmt.setInt(2,itemId);
            pstmt.setString(3, size);
            pstmt.setString(4, color);
            // execute the delete statement
            pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //removes entire cart for a user
    public void emptyCart(String username){
        String sql = "DELETE FROM cart "
                + "WHERE username = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, username);
            // execute the delete statement
            pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void createOrdersTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:"+DBLoc;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE orders(\n"
                + "	id INTEGER PRIMARY KEY,\n"/*order Id*/
                + "	username varchar(30),\n"  /*id of item to buy*/
                + "     dateSold varchar(10),\n"//order created date mm/dd/yyyy
                + "     dateDeliv varchar(10),\n"//delivery date mm/dd/yyyy
                + "     address varchar(100),\n"/*address limited to 100 characters*/
                + "     city varchar(60),\n"    
                + "     state char(2),\n"       /*state abbreviation*/
                + "     zipCode varchar(5),\n"  /*zipcode does not include extensions*/
                + "     country char(3)\n"     /*Country code(USA,CAN)*/
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public int orderNumGen(){
        String sql = "SELECT MAX(id) AS out FROM orders";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            return rs.getInt("out")+1;
               
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }
    //creates orders here, items are added using newOrderItems(); instead
    public int newOrder(String username, String dateSold, String dateDeliv, 
            String address, String city, String state, String zipCode, String country){
        String sql = "INSERT INTO orders(id, username, dateSold, dateDeliv, \n" +
                     "address, city, state, zipCode, country)"
                + " VALUES(?,?,?,?,?,?,?,?,?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderNumGen());
            pstmt.setString(2, username);
            pstmt.setString(3, dateSold);
            pstmt.setString(4, dateDeliv);
            pstmt.setString(5, address);
            pstmt.setString(6, city);
            pstmt.setString(7, state);
            pstmt.setString(8,zipCode);
            pstmt.setString(9,country);
            
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            
        }
        return 1;
    }
    //get basic order information excluding items
    public void getOrderInfo(int id){
        String sql = "SELECT id, username, dateSold, dateDeliv, \n" +
                     "address, city, state, zipCode, country FROM orders "
                + "WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            // set the value
            pstmt.setInt(1,id);
            
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                //prints output for each review
                //these print statements are what I suggest you edit
                    System.out.println(rs.getInt("id"));
                    System.out.println(rs.getString("username"));
                    System.out.println(rs.getString("dateSold"));
                    System.out.println(rs.getString("dateDeliv"));
                    System.out.println(rs.getString("address"));
                    System.out.println(rs.getString("city"));
                    System.out.println(rs.getString("state"));
                    System.out.println(rs.getString("zipCode"));
                    System.out.println(rs.getString("country"));
                    
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }
    //month input should be like 01, 02, 10, or 12 etc.
    //returns an array of all order numbers within a certain month
    public int[] getOrderByMonth(int id, String month){
        String sql = "SELECT id FROM orders ";
        
        String sql2 = "SELECT dateSold FROM orders WHERE id = ? ";
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            PreparedStatement pstmt2  = conn.prepareStatement(sql2);
            ResultSet rs  = pstmt.executeQuery();
            ResultSet rs2  = pstmt2.executeQuery();
            
            ArrayList ar = new ArrayList();
            // loop through the result set
            while (rs.next()) {
                int comp=rs.getInt("id");
                if(rs2.getString("dateSold").substring(0, 2).equals(month))
                    ar.add(comp);
            }
            int[] retAr = new int[ar.size()];
            for(int i=0;i<ar.size();i++){
                retAr[i]=(int)ar.get(i);
            }
            return retAr;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            int[] retAr={-1};
            return retAr;
        }
        
    }
    public static void createOrdersItemTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:"+DBLoc;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE orderItems(\n"
                + "	id INTEGER,\n"/*order Id*/
                + "     itemId INTEGER,\n"
                + "     size varChar(10),\n"
                + "     color varChar(10),\n"
                + "     quantity INTEGER,\n"
                + "     PRIMARY KEY (id, itemId, size, color),\n"
                + "     FOREIGN KEY (id) REFERENCES orders (id) \n" 
                + "     ON DELETE CASCADE ON UPDATE NO ACTION "
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //when you add a new order the items are kept sepeartely from dat, but utilize the same id
    public void newOrderItems(int id, int itemId, String size, String color, int quantity){
        String sql = "INSERT INTO orderItems(id, itemId, size, color, quantity)"
                + " VALUES(?,?,?,?,?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, itemId);
            pstmt.setString(3, size);
            pstmt.setString(4, color);
            pstmt.setInt(5, quantity);
            
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            
        }
    }
    //I suggest you edit it to provide information on your gui
    public void getOrderItems(int id){
        String sql = "SELECT itemID, size, color, quantity FROM orderItems "
                + "WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            // set the valu
            pstmt.setInt(1,id);
            
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                //item information 
                System.out.println(rs.getInt("itemId"));
                System.out.println(rs.getString("size"));
                System.out.println(rs.getString("color"));
                System.out.println(rs.getInt("quantity"));
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }
    //gets overall price of a certain order
    public double getOrderPrice(int id){
        String sql = "SELECT itemID, quantity FROM orderItems "
                + "WHERE id = ?";

        double total=0.00;
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            // set the valu
            pstmt.setInt(1,id);
            
            ResultSet rs  = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                total+=rs.getInt("quantity")*Double.parseDouble(getItemPrice(rs.getInt("itemId")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return total;
    }
    
    public static void main(String[] args) {
        //original creation call
        //createNewDatabase();
        
        //original create account table
        //createAcctTable();
        
        //original create product table 
        //createProductTable();
        
        //original create stock table
        //createStockTable();
        
        //original create stock table
        //createReviewTable();
        
        //oringial create cart table
        //createCartTable();
        
        //oringal create order table
        //createOrdersTable();
        
        //oringial create Order item list table
        //createOrdersItemTable();
        
        System.out.println("You didn't read the instructions. Did you? \n"+
                "This code requires changes before proper implementation and integration \n"
                + "All suggested changes are as follows\n"
                + "\tChange of database location (change the string of DBLOC to your location)\n"
                + "\tDatabase Adjustments to suit your needs");
    }
    
}
