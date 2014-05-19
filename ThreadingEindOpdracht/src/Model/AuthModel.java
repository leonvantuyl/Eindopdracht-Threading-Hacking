package Model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthModel
  {

    private final StringBuilder stringBuilder = new StringBuilder();

    public String login(String username, String password)
      {
        String salt = null; //get salt from db

        String hashedPass = hashPass(password, salt);

        String token = null; //make token if true
        return token;
      }

    public void register(String username, String password)
      {
        String salt = createSalt();
        String hashedPass = hashPass(password, salt);

        //save shit naar de db
      }

    public void logout()
      {

      }

    //hash the incoming pass
    private String hashPass(String password, String salt)
      {
        String oldpass = (password + salt);
        String hashedPass = null;
        try
          {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(oldpass.getBytes("UTF-8"));

            for (int i = 0; i < hash.length; i++)
              {
                stringBuilder.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
              }

            hashedPass = stringBuilder.toString();
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

        for (int i = 0; i < bytes.length; i++)
          {
            stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
          }

        String salt = stringBuilder.toString();
        return salt;
      }

  }
