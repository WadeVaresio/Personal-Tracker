import React from "react";
import {Switch, Route} from "react-dom";
import {BrowserRouter as Router} from "react-router-dom";
import Home from "./pages/Home";
import './App.css';

function App() {
  return (
      <Router>
          <div className="App">
              <Home/>
          </div>
      </Router>
  );
}

export default App;
