package Controller;

import Model.AuthModel;

public class AuthController
  {

    private final AuthModel authModel;

    public AuthController()
      {
        authModel = new AuthModel();
      }

    public String login(String username, String password)
      {
        String token = authModel.login(username, password);
        return token;
      }

    public void logout()
      {
        authModel.logout();
      }

    public void register(String username, String password)
      {
        authModel.register(username, password);
      }
  }
