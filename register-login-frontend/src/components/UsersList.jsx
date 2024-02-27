import React, { useContext } from "react";
import UserRow from "./UserRow";
import { Card } from "@material-tailwind/react";
import { UserContext } from "../context/UserContext";

const UsersList = () => {
  const { users = [] } = useContext(UserContext);

  return (
    <Card className="w-full">
      <table className="w-full h-auto">
        <thead>
          <tr>
            <th>Id</th>
            <th>Username</th>
            <th>Email</th>
            <th>Update</th>
            <th>Delete</th>
          </tr>
        </thead>
        <tbody>
          {users &&
            users.map((user) => {
              return (
                <UserRow
                  key={user.id}
                  user={user}
                />
              );
            })}
        </tbody>
      </table>
    </Card>
  );
};

export default UsersList;
