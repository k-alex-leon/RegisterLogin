import React, { useContext } from "react";
import { Navigate, Route, Routes } from "react-router-dom";
import Navbar from "../components/layout/Navbar";
import UsersPage from "../pages/UsersPage";
import UserProvider from "../context/UserProvider";
import { AuthContext } from "../auth/context/AuthContext";

const UserRoutes = () => {

  const { handlerLogout } = useContext(AuthContext)
  
  return (
    <>
      <UserProvider>
        <Navbar handlerLogout={handlerLogout} />
        <Routes>
          <Route path="users" element={<UsersPage />} />
          <Route path="/" element={<Navigate to="/users" />} />
        </Routes>
      </UserProvider>
    </>
  );
};

export default UserRoutes;
