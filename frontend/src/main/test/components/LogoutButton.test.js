import { render, screen } from '@testing-library/react';
import LogoutButton from "../../components/LogoutButton";
import {useAuth0} from "@auth0/auth0-react";
import userEvent from "@testing-library/user-event";

jest.mock("@auth0/auth0-react");

describe("LogoutButton tests", () => {
    beforeEach( () =>{
        useAuth0.mockReturnValue({
            logout: jest.fn(),
        })
    });

    test("Renders correctly", () =>{
        render(<LogoutButton/>);
    });

    test("Logs out on press", () => {
        const logoutSpy = jest.fn();

        useAuth0.mockReturnValueOnce({
            logout: logoutSpy,
        });

        render(<LogoutButton/>);

        userEvent.click(screen.getByText("Log Out"));
        expect(logoutSpy).toHaveBeenCalledTimes(1);
    });
});
