import { useReducer, useState } from "react";
import { usersReducer } from "../reducers/usersReducer";
import Swal from "sweetalert2";
import { useNavigate } from "react-router-dom";
import { findAll, remove, save, update } from "../services/userService";

const initialUsers = [];

const initialUserForm = {
  id: 0,
  username: "",
  email: "",
  password: "",
};

const initialErrors = {
  username: "",
  email: "",
  password: "",
};

export const useUsers = () => {
  // navegar a /users
  const navigate = useNavigate();
  // TODO -- investigar useReducer
  const [users, dispatch] = useReducer(usersReducer, initialUsers);
  // usuario seleccionado -> lo usaremos para editar sus campos
  const [userSelected, setUserSelected] = useState(initialUserForm);

  // ERRORS
  const [errors, setErrors] = useState(initialErrors);

  // se puede ver o no el modal
  const [visibleForm, setVisibleForm] = useState(false);

  // consultando los users de la bd
  const getUsers = async () => {
    // esperamos el resultado de la consulta (error o data obj)
    const result = await findAll();
    dispatch({
      type: "loadingUsers",
      payload: result.data,
    });
  };

  // AGREGAR / EDITAR USUARIO
  const handlerAddUser = async (user) => {
    let response;
    try {
      // el id es 0 si estamos creando un usuario
      if (user.id === 0) {
        response = await save(user);
      } else {
        // con el update solo actualiza email y username
        response = await update(user);
      }

      dispatch({
        type: user.id === 0 ? "addUser" : "updateUser",
        payload: response.data,
      });
      /* notifySuccess(user.id === 0 ? "User created" : "User updated"); */

      handlerCloseForm()
      // navega a /users
      navigate("/users");
    } catch (error) {
      if (error.response && error.response.status === 400) {
        
        setErrors(error.response.data);
        
      }
      // validando campos unicos por medio de la respuesta de la db
      // err 500 == unique key already exist
      else if(error.response && error.response.status === 500){

      if(error.response.data?.message?.includes('UK_username')){
        setErrors({username: 'Username already exist!'})
      }

      if(error.response.data?.message?.includes('UK_email')){
        setErrors({email: 'Email already exist!'})
      }

      }else {
        // cualquier otro error
        throw error
      }

    }
  };

  // ELIMINAR USUARIO
  const handlerRemoveUser = (id) => {
    Swal.fire({
      title: "Delete user?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!",
    }).then((result) => {
      if (result.isConfirmed) {
        remove(id); // <- viene de userService

        dispatch({ type: "removeUser", payload: id });
        /* notifySuccess("User deleted!"); */
      }
    });
  };

  // USUARIO SELECCIONADO PARA EDITAR
  const handlerUserSelected = (user) => {
    setUserSelected({ ...user });
    setVisibleForm(true);
  };

  // FORMULARIO VISIBLE
  const handlerOpenForm = () => {
    setVisibleForm(true);
  };

  const handlerCloseForm = () => {
    setVisibleForm(false);
    setUserSelected(initialUserForm);
    setErrors({})
  };

  return {
    users,
    userSelected,
    initialUserForm,
    visibleForm,
    errors,
    handlerAddUser,
    handlerRemoveUser,
    handlerUserSelected,
    handlerOpenForm,
    handlerCloseForm,
    getUsers,
  };
};
