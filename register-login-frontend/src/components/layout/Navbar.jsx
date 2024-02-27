import { Typography } from "@material-tailwind/react";
import React from "react";
import { NavLink } from "react-router-dom";

const NavbarList = ({handlerLogout}) => {

  return (
    <>
      <ul className="my-2 flex gap-2 lg:mb-0 lg:mt-0 lg:flex-row lg:items-center lg:gap-6">
        <Typography
          as="li"
          variant="small"
          color="blue-gray"
          className="p-1 font-medium"
        >
          <NavLink
            to='/users'
            className="flex items-center hover:text-blue-500 transition-colors"
          >
            Users
          </NavLink>
        </Typography>
        

        <Typography
          as="li"
          variant="small"
          color="blue-gray"
          className="p-1 font-medium absolute right-0 mx-4"
        >
          <a
            onClick={handlerLogout}
            className="flex items-center hover:text-blue-500 transition-colors"
          >
            Logout
          </a>
        </Typography>
      </ul>
    </>
  );
};

const Navbar = ({ handlerLogout }) => {
  return (
    <nav className="flex w-full mx-auto px-6 py-3 select-none">
      <div className="flex items-center justify-between text-blue-gray-900">
        <Typography
          as="a"
          href="#"
          variant="h6"
          className="mr-4 cursor-pointer py-1.5"
        >
          Users app
        </Typography>

        <NavbarList handlerLogout={handlerLogout}/>

      </div>
      
    </nav>
  );
};

export default Navbar;
