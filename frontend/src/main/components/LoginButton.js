import React from "react";
import {Button} from "react-bootstrap";
import { useHistory } from "react-router-dom";

const LoginButton = () => {
    const history = useHistory();

    const handleClick = () => {
        history.push("/oauth2/authorization/google");
    }

    return (
        <Button href="/oauth2/authorization/google" onClick={handleClick}>
            Login
        </Button>
    );
}

export default LoginButton;