import React, { useContext } from "react";
import { ToastContainer } from "react-toastify";
import LoginPage from "./auth/pages/LoginPage";
import { Navigate, Route, Routes } from "react-router-dom";
import UserRoutes from "./routes/userRoutes";
import { AuthContext } from "./auth/context/AuthContext";

const UsersApp = () => {
  const { login } = useContext(AuthContext);

  return (
    <>
      <Routes>
        {login.isAuth ? (
          <Route
            path="/*"
            element={<UserRoutes/>}
          />
        ) : (
          <>
            <Route
              path="/login"
              element={<LoginPage />}
            />
            {/* CUALQUIER RUTA NO DEFINIDA NAVEGA AL LOGIN */}
            <Route path="/*" element={<Navigate to="/login" />} />
          </>
        )}
      </Routes>
      <ToastContainer />
    </>
  );
};

export default UsersApp;
