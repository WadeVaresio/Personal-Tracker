import React from "react";
import {BrowserRouter, Switch, Route} from "react-router-dom";
import Home from "./pages/Home";
import './App.css';
import Weather from "./pages/Weather";
import ApplicationNavBar from "./components/ApplicationNavBar";


function App() {
    const styling = {
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
    };

    return (
        <div className={"App"} style={styling}>
            <BrowserRouter>
                <Switch>
                    <Route path={"/weather"} component={Weather}/>
                    <Route path={"/"} component={Home}/>
                </Switch>
                <ApplicationNavBar/>
            </BrowserRouter>
        </div>
    );
}

export default App;
