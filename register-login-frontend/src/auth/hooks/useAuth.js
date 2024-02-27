import { useReducer } from "react";
import { loginReducer } from "../reducers/loginReducer";
import {
  notifyError,
  notifyInfo,
  notifySuccess,
} from "../../utils/notifications";
import Swal from "sweetalert2";
import { loginService } from "../services/authService";
import { useNavigate } from "react-router-dom";

// obtenemos data de la session / si no pasamos un valor por defecto
const initialLogin = JSON.parse(sessionStorage.getItem("login")) ?? {
  isAuth: false,
  user: undefined,
};

export const useAuth = () => {
  const [login, dispatch] = useReducer(loginReducer, initialLogin);

  const navigate = useNavigate()

  const handlerLogin = ({ email, password }) => {
    if (loginService({email, password})) {
      dispatch({
        type: "login",
        payload: { email, password },
      });

      // guarda data en la session del navegador
      sessionStorage.setItem(
        "login",
        JSON.stringify({
          isAuth: true,
          user: { email },
        })
      );

      // navegar al login
      navigate('/users')

      notifyInfo("Welcome! 😋");
    } else notifyError("data deesn't match");
  };

  const handlerLogout = () => {
    Swal.fire({
      title: login.user.email,
      text: "Are you sure you want to leave?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, leave!",
    }).then((result) => {
      if (result.isConfirmed) {
        // cambia el estado de la var isAuth a false para enviar al user al login-view
        dispatch({
          type: "logout",
        });

        // elimina el objeto almacenado en la session
        sessionStorage.removeItem("login");
        notifySuccess("Come back soon!");
      }
    });
  };

  return {
    login,
    handlerLogin,
    handlerLogout,
  };
};
