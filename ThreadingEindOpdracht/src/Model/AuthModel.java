package Model;

import static Model.DatabaseConnector.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthModel
  {

    public String login(String username, String password)
      {
        String salt = getSalt(username);
        String inputPass = hashPass(password, salt);
        String databasePass = null;
        String token;

        String query = "SELECT password FROM user WHERE username = ?";
        PreparedStatement statement;

        try
          {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.executeQuery();

            ResultSet result = statement.getResultSet();

            while (result.next())
              {
                databasePass = result.getString("password");
              }
          }
        catch (SQLException ex)
          {
            Logger.getLogger(AuthModel.class.getName()).log(Level.SEVERE, null, ex);
          }

        if (databasePass.equals(inputPass))
          {
            token = createToken();
          }
        else
          {
            token = null;
          }

        return token;
      }

    public void register(String username, String password)
      {
        String salt = createSalt();
        String hashedPass = hashPass(password, salt);
        PreparedStatement statement;

        String query = "INSERT INTO user (username, password, salt) VALUES (?,?,?)";

        try
          {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, hashedPass);
            statement.setString(3, salt);
            statement.executeUpdate();
          }
        catch (SQLException ex)
          {
            Logger.getLogger(AuthModel.class.getName()).log(Level.SEVERE, null, ex);
          }
      }

    public void logout()
      {

      }

    //hash the incoming pass
    private String hashPass(String password, String salt)
      {
        String oldpass = (password + salt);
        String hashedPass = null;
        StringBuilder sb = new StringBuilder();

        try
          {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(oldpass.getBytes("UTF-8"));

            byte[] hash = md.digest();

            for (int i = 0; i < hash.length; i++)
              {
                sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
              }

            hashedPass = sb.toString();
          }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException ex)
          {
            Logger.getLogger(AuthModel.class.getName()).log(Level.SEVERE, null, ex);
          }

        return hashedPass;
      }

    //create salt for the user.
    private String createSalt()
      {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bytes.length; i++)
          {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
          }

        String salt = sb.toString();
        return salt;
      }

    private String getSalt(String username)
      {
        String query = "SELECT salt FROM user WHERE username = ?";
        String salt = "";
        PreparedStatement statement;

        try
          {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.executeQuery();

            ResultSet result = statement.getResultSet();

            while (result.next())
              {
                salt = result.getString("salt");
              }
          }
        catch (SQLException ex)
          {
            Logger.getLogger(AuthModel.class.getName()).log(Level.SEVERE, null, ex);
          }
        return salt;
      }

    private String createToken()
      {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[10];
        random.nextBytes(bytes);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bytes.length; i++)
          {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
          }

        String token = sb.toString();

        return token;
      }
  }
