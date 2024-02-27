import { Dialog, DialogHeader } from "@material-tailwind/react";
import React, { useContext } from "react";
import UserForm from "./UserForm";
import { UserContext } from "../context/UserContext";

const UserModalForm = () => {
  const { userSelected,visibleForm } = useContext(UserContext);

  return (
    <Dialog open={visibleForm}>
      <DialogHeader>
        {userSelected.id === 0 ? "Create user" : "Update user"}
      </DialogHeader>

      <UserForm />
    </Dialog>
  );
};

export default UserModalForm;
