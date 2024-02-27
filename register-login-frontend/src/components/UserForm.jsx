import React, { useContext, useEffect, useState } from "react";
import { notifyWarn } from "../utils/notifications";
import { Button } from "@material-tailwind/react";
import { UserContext } from "../context/UserContext";

const UserForm = () => {
  const {
    handlerAddUser,
    handlerCloseForm,
    initialUserForm,
    userSelected,
    errors,
  } = useContext(UserContext);

  // input password state
  const [seePass, setSeePass] = useState(false);

  const [userForm, setUserForm] = useState(initialUserForm);
  const { username, email, password } = userForm;

  // si existe una seleccion de usuario detecta el cambio
  useEffect(() => {
    setUserForm({ ...userSelected });
  }, [userSelected]);

  // asigna los valores dependiendo los inputs que alteran su valor
  const onInputChange = ({ target }) => {
    const { name, value } = target;
    setUserForm({
      ...userForm,
      [name]: value,
    });
  };

  const onSubmit = (e) => {
    e.preventDefault();

    // validar empty-data

    /* if (!userForm || !password || !email) {
      notifyWarn("Complete the form!");
      return;
    } */

    // retornar valor inicial del pass-input
    setSeePass(false);
    // guarda userform en listado de usuarios
    handlerAddUser(userForm);
    
  };

  const onCloseForm = () => {
    setUserForm(initialUserForm);
    handlerCloseForm();
  };

  return (
    <form
      onSubmit={onSubmit}
      className="flex w-full flex-col space-y-3 p-4"
      autoComplete="off"
    >
      <input
        className="w-full border-2 p-2 border-gray-400 rounded"
        placeholder="UserName"
        name="username"
        type="text"
        value={username}
        onChange={onInputChange}
      />
      {errors && <p className="text-red-700">{errors.username}</p>}
      <input
        className="w-full border-2 p-2 border-gray-400 rounded"
        placeholder="Email"
        name="email"
        type="email"
        value={email}
        onChange={onInputChange}
      />
      {errors && <p className="text-red-700">{errors.email}</p>}
      {userForm.id === 0 && (
        <>
          <div className="w-full relative flex items-center">
            <input
              className="w-full border-2 p-2 border-gray-400 rounded"
              placeholder="Password"
              name="password"
              type={seePass ? "text" : "password"}
              value={password}
              onChange={onInputChange}
            />

            <span
              onClick={() => setSeePass(!seePass)}
              className="absolute right-0 mr-2 z-10 select-none"
            >
              {seePass ? "🐵" : "🙈"}
            </span>
          </div>
          {errors && <p className="text-red-700">{errors.password}</p>}
        </>
      )}

      <div className="flex w-full justify-center space-x-2">
        <Button type="submit" color="blue" className="w-1/5 p-2">
          {userForm.id && userForm.id !== 0 ? "Update" : "Create"}
        </Button>
        <Button
          onClick={() => onCloseForm()}
          type="button"
          color="gray"
          className="w-1/5 p-2"
        >
          Cancel
        </Button>
      </div>
    </form>
  );
};

export default UserForm;
