import { Button } from "@material-tailwind/react";
import React, { useContext } from "react";
import { UserContext } from "../context/UserContext";

const UserRow = ({user}) => {

  const {handlerRemoveUser, handlerUserSelected} = useContext(UserContext)

  return (
    <tr>
      <th>{user.id}</th>
      <th>{user.username}</th>
      <th>{user.email}</th>
      <th>
        <Button 
        color="amber"
        className="p-3"
        onClick={() => handlerUserSelected(user)}>
          ✏
        </Button>
      </th>
      <th>
        <Button
        color="red" 
        className="p-3"
        onClick={() => handlerRemoveUser(user.id)}>
          🗑
        </Button>
      </th>
    </tr>
  );
};

export default UserRow;
