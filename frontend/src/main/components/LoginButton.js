import React from "react";
import {Button} from "react-bootstrap";
import { useHistory } from "react-router-dom";
import useSWR from "swr";

const LoginButton = () => {
    const history = useHistory();
    const fetcher = url => fetch(url).then(res => res.json());
    const {data: loggedIn} = useSWR("/api/user/loggedIn", fetcher);

    const handleClick = () => {
        history.push("/oauth2/authorization/google");
    }

    return (
        <>
            {!loggedIn &&
            <Button href="/oauth2/authorization/google" onClick={handleClick}>
                Google Login
            </Button>}

            {loggedIn &&
            <Button href="/logout" onClick={handleClick}>
                Logout
            </Button>}

        </>
    );
}

export default LoginButton;