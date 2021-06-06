import React from "react";
import { CDBSidebar, CDBSidebarContent, CDBSidebarHeader, CDBSidebarMenu, CDBSidebarMenuItem} from "cdbreact";

const SideBar = () => {
    return (
        <div style={{display: 'flex', height: '100vh', overflow: 'scroll initial'}} >
            <CDBSidebar textColor={"#fcfaf9"} backgroundColor={"#202120"}>
                <CDBSidebarHeader prefix={<i className={"fa fa-bars fa-large"}/> }>
                    <a href={"/"} className={"text-decoration-none"} style={{color: "inherit"}}>Personal Tracker</a>
                </CDBSidebarHeader>

                <CDBSidebarContent>
                    <CDBSidebarMenu>
                        <CDBSidebarMenuItem icon={"list-ul"}>To-Dos</CDBSidebarMenuItem>
                        <CDBSidebarMenuItem icon={"exclamation-circle"}>Deadlines</CDBSidebarMenuItem>
                        <CDBSidebarMenuItem icon={"chart-line"}>Stocks</CDBSidebarMenuItem>
                        <CDBSidebarMenuItem icon={"baseball-ball"}>MLB Data</CDBSidebarMenuItem>
                    </CDBSidebarMenu>
                </CDBSidebarContent>
            </CDBSidebar>
        </div>
    )
};

export default SideBar;
