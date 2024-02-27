import React from "react";
import { UserContext } from "./UserContext";
import { useUsers } from "../hooks/useUsers";

// provee de las funciones del hook a todas las clases hijo
// todas las clases hijo (children) pueden acceder a estas funciones
const UserProvider = ({ children }) => {
  const {
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
  } = useUsers();

  return (
    <>
      <UserContext.Provider
        value={{
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
          getUsers
        }}
      >
        {children}
      </UserContext.Provider>
    </>
  );
};

export default UserProvider;
