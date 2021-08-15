import { render, screen } from '@testing-library/react';
import LoginButton from "../../components/LoginButton";
import { useAuth0 } from "@auth0/auth0-react";
import userEvent from "@testing-library/user-event";

jest.mock("@auth0/auth0-react");

describe("LoginButton Tests", () => {
    beforeEach(() => {
        useAuth0.mockReturnValue({
            loginWithRedirect: jest.fn(),
        });
    });

    test("Renders LoginButton", () => {
        const { getByText } = render(<LoginButton/>);
        const buttonComponent = getByText("Log In");
        expect(buttonComponent).toBeInTheDocument();
    });

    test("Log In Redirect Works", () => {
       const loginRedirectSpy = jest.fn();

        useAuth0.mockReturnValueOnce({
            loginWithRedirect: loginRedirectSpy,
        });

       render(<LoginButton/>);

       userEvent.click(screen.getByText("Log In"));
       expect(loginRedirectSpy).toHaveBeenCalledTimes(1);
    });
});
