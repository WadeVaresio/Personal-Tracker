import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import App from './main/App';
import reportWebVitals from './reportWebVitals';
import Auth0ProviderWithHistory from "./main/components/Auth0ProviderWithHistory";
import {BrowserRouter} from "react-router-dom";

ReactDOM.render(
  <React.StrictMode>
      <BrowserRouter>
          <Auth0ProviderWithHistory>
              <App />
          </Auth0ProviderWithHistory>
      </BrowserRouter>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
