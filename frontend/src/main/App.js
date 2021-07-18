import React from "react";
import { Switch, Route } from "react-router-dom";
import Home from "./pages/Home";
import './App.css';
import Weather from "./pages/Weather";
import ApplicationNavBar from "./components/ApplicationNavBar";
import SavedNotes from "./pages/SavedNotes";
import ProtectedRoute from "./components/ProtectedRoute";


function App() {
    const styling = {
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
    };

    return (
        <div className={"App"} style={styling}>
                <Switch>
                    <Route path={"/weather"} component={Weather}/>
                    <ProtectedRoute path={"/savedNotes"} component={SavedNotes}/>
                    <Route path={"/"} component={Home}/>
                </Switch>
                <ApplicationNavBar/>
        </div>
    );
}

export default App;
