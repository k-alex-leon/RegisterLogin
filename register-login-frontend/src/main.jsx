import React from "react";
import ReactDOM from "react-dom/client";
import "./styles.css";
import "react-toastify/dist/ReactToastify.css";
import UsersApp from "./UsersApp";
import { BrowserRouter } from "react-router-dom";
import AuthProvider from "./auth/context/AuthProvider";

ReactDOM.createRoot(document.getElementById("root")).render(
  <BrowserRouter>
    <AuthProvider>
      <UsersApp />
    </AuthProvider>
  </BrowserRouter>
);
