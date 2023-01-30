package deposit;

import Login.LoginController;
import dbConnector.mySQLCon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class DepositAmount implements Initializable {
    Calendar cal = Calendar.getInstance();
    int year =cal.get(Calendar.YEAR);
    int month =cal.get(Calendar.MONTH);
    int day =cal.get(Calendar.DAY_OF_MONTH);
    int hour =cal.get(Calendar.HOUR);
    int minutes=cal.get(Calendar.MINUTE);
    int seconds = cal.get(Calendar.SECOND);

    int dayAndNight =cal.get(Calendar.AM_PM);
    DateFormat dateFormat =new SimpleDateFormat("yyyy/MM/dd");
    Date d =new Date();
    String date = dateFormat.format(d);

    LocalTime localTime =LocalTime.now();
    DateTimeFormatter dt =DateTimeFormatter.ofPattern("hh:mm:ss a");
    String time =localTime.format(dt);

    @FXML
    private Label account_no;
    @FXML
    private Label balance;
    @FXML
    private TextField amt_field;
    @FXML
    private TextField pin_field;


    public  void setInfo(){
        Connection con= mySQLCon.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            String sql = "SELECT * FROM userdata WHERE AccountNo=?";
            ps = con.prepareStatement(sql);
            ps.setString(1,  LoginController.acc);
            ps.setString(1,  LoginController.acc);
            rs = ps.executeQuery();
            if(rs.next()){
                account_no.setText(rs.getString("AccountNo"));
                balance.setText(rs.getString("Balance"));
            }


        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in login");
            a.setContentText("There is some error. PLEASE TRY AGAIN..!!!");
            a.showAndWait();

        }

    }


    public  void depositButton(){
        Connection con= mySQLCon.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            String sql = "SELECT * FROM userdata WHERE AccountNo=? and PIN=?";
            ps = con.prepareStatement(sql);
            ps.setString(1,  LoginController.acc);
            ps.setString(2, pin_field.getText());
            rs=ps.executeQuery();
            if(rs.next()){
                int da =Integer.parseInt(amt_field.getText());
                int ta =Integer.parseInt(balance.getText());

                    int total=ta+da;
                    String sql1 = "UPDATE userdata SET Balance='"+total+"'WHERE AccountNo='"+ LoginController.acc+"'";
                    ps=con.prepareStatement(sql1);
                    ps.execute();
                    String sql2="INSERT INTO deposit(AccountNo, DepositAmount, NewAmount, Date, Time) VALUES (?,?,?,?,?)";
                    ps=con.prepareStatement(sql2);
                    ps.setString(1, LoginController.acc);
                    ps.setString(2,String.valueOf(da));
                    ps.setString(3,String.valueOf(total));
                    ps.setString(4,date);
                    ps.setString(5,time);
                    int i =ps.executeUpdate();
                    if(i>0){
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("Amount Deposit");
                        a.setHeaderText("Amount Deposit Successful");
                        a.setContentText("Amount"+da+" has been successfully deposited\n"+"Current Balance = "+total);
                        a.showAndWait();
                        amt_field.setText("");
                        pin_field.setText("");
                        balance.setText(String.valueOf(total));

                    }
            }
            else
            {   Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Error in login");
                a.setContentText("Your account number or pin is wrong. Enter again..!!!" );
                a.showAndWait();

            }


        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in login .");
            a.setContentText(e.getMessage());
            a.showAndWait();



        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
setInfo();
    }
}
