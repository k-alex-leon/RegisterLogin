import React, { useContext, useEffect } from "react";
import UsersList from "../components/UsersList";
import { Button } from "@material-tailwind/react";
import UserModalForm from "../components/UserModalForm";
import { UserContext } from "../context/UserContext";

const UsersPage = () => {
  // obtenemos del context solo las funciones que se van a ejecutar
  const { handlerOpenForm, users, getUsers } = useContext(UserContext);

  useEffect(() => {
    getUsers();
  }, [])

  return (
    <>
      <div className="m-4">
        <div className="flex">
          <UserModalForm />

          <div className="w-full">
            <Button color="blue" onClick={handlerOpenForm} className="my-4">
              Nuevo usuario
            </Button>

            <UsersList />

            {users.length === 0 && (
              <div className="w-full text-xl italic rounded-md mt-4 p-4 shadow-lg bg-yellow-200 text-orange-800">
                No hay usuarios registrados...
              </div>
            )}
          </div>
        </div>
      </div>
    </>
  );
};

export default UsersPage;
