import {
  Button,
  Card,
  CardBody,
  CardFooter,
  Checkbox,
  Dialog,
  Input,
  Typography,
} from "@material-tailwind/react";
import React, { useContext, useState } from "react";
import { notifyWarn } from "../../utils/notifications";
import { AuthContext } from "../context/AuthContext";

const initialLoginForm = {
  email: "",
  password: "",
};
const LoginPage = () => {

  const {handlerLogin} = useContext(AuthContext)

  const [loginForm, setLoginForm] = useState(initialLoginForm);
  const { email, password } = loginForm;

  const onInputChange = ({ target }) => {
    const { name, value } = target;
    setLoginForm({
      ...loginForm,
      [name]: value,
    });
  };

  const onSubmit = (e) => {
    e.preventDefault();

    if (!email || !password) notifyWarn("Complete the form!");
    else handlerLogin({email, password})

    // clean the form
    setLoginForm(initialLoginForm);
  };

  return (
  
      <Dialog
        open={true}
        /* handler={handleOpen} */
        
        classes={{tooltip : 'bg-opacity-0'}}
        className="bg-transparent shadow-none -z-10"
      >
        <Card className="mx-auto w-full max-w-[24rem]">
          <form onSubmit={onSubmit} className="space-y-4">
            <CardBody className="flex flex-col gap-4">
              <Typography variant="h4" color="blue-gray">
                Sign In
              </Typography>
              <Typography
                className="mb-3 font-normal"
                variant="paragraph"
                color="gray"
              >
                Enter your email and password to Sign In.
              </Typography>

              <Input
                label="Email"
                name="email"
                size="lg"
                type="email"
                value={email}
                onChange={onInputChange}
              />

              <Input
                label="Password"
                name="password"
                type="password"
                size="lg"
                value={password}
                onChange={onInputChange}
              />

              <div className="-ml-2.5 -mt-3">
                <Checkbox label="Remember Me" />
              </div>
            </CardBody>
            <CardFooter className="pt-0">
              <Button variant="gradient" color="blue" type="submit" fullWidth>
                Sign In
              </Button>
              <Typography variant="small" className="mt-4 flex justify-center">
                Don&apos;t have an account?
                <Typography
                  as="a"
                  href="#signup"
                  variant="small"
                  color="blue-gray"
                  className="ml-1 font-bold"
                  /* onClick={handleOpen} */
                >
                  Sign up
                </Typography>
              </Typography>
            </CardFooter>
          </form>
        </Card>
      </Dialog>
  
  );
};

export default LoginPage;
