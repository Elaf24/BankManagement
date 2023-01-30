package allowedBank;

import dbConnector.mySQLCon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AllowBank implements Initializable {

    @FXML
    private TextField account;

    @FXML
    private CheckBox brac;

    @FXML
    private CheckBox ebl;

    @FXML
    private CheckBox trust;

    @FXML
   public void allow(MouseEvent event) throws SQLException {
        Connection con = mySQLCon.getConnection();
        PreparedStatement ps = null;
       String accNO= account.getText();
        System.out.println(trust.isSelected());
        System.out.println(brac.isSelected());
        System.out.println(ebl.isSelected());
        String sql = "insert into allowbank (trustbank,bracbank,eblbank) values (?,?,?)";
        ps = con.prepareStatement(sql);
        ps.setString(1,"");
        ps.setString(2,"");
        ps.setString(3,"");
        if (brac.isSelected()) {
            ps.setString(2,accNO);
        }
        if (ebl.isSelected()) {
            ps.setString(3,accNO);
        }
        if (trust.isSelected()) {
            ps.setString(1,accNO);
        }
        ps.executeUpdate();
    }
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }
}
