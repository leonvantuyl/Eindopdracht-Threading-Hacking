package Controller;

import Model.AuthModel;

public class AuthController
  {
    private final AuthModel authModel;
    
    public AuthController()
      {
        authModel = new AuthModel();
      }
    
    public void login(String username, String password)
      {
        authModel.login(username, password);
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
