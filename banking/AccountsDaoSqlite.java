package banking;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AccountsDaoSqlite {
    private Connection conn;

    AccountsDaoSqlite(String fileName) {

        String createTableSql = "CREATE TABLE IF NOT EXISTS accounts (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	number text NOT NULL,\n"
                + "	pin text NOT NULL,\n"
                + "	balance integer\n"
                + ");";

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + fileName);

            Statement s = conn.createStatement();
            s.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveAccount(BankAccount bankAccount) {
        String sql = "INSERT INTO accounts (id, number, pin, balance) VALUES(?, ?, ?, ?)";

        try  {
            PreparedStatement s = conn.prepareStatement(sql);
            s.setInt(1, Integer.parseInt(bankAccount.getAccStr()));
            s.setString(2, bankAccount.getCardStr());
            s.setString(3, bankAccount.getPin());
            s.setInt(4, bankAccount.getBalance());

            s.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
