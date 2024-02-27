import React from "react";
import { AuthContext } from "./AuthContext";
import { useAuth } from "../hooks/useAuth";

const AuthProvider = ({ children }) => {
  const { login, handlerLogin, handlerLogout } = useAuth();

  return (
    <AuthContext.Provider
      value={{
        login,
        handlerLogin,
        handlerLogout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;
