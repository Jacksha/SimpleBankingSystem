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

    public Map<String, BankAccount> mapAllAccounts() {
        Map<String, BankAccount> accountsMap = new HashMap<>();
        String sql = "SELECT * FROM accounts";

        try {
            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                BankAccount bankAccount = new BankAccount();
                bankAccount.setAccStr(result.getString("id"));
                bankAccount.setCardStr(result.getString("number"));
                bankAccount.setPin(result.getString("pin"));
                bankAccount.setBalance(result.getInt("balance"));

                accountsMap.put(bankAccount.getAccStr(), bankAccount);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return accountsMap;
    }
}
