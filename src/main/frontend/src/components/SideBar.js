import React from "react";
import SideBarNav from "./SideBarNav"
import Chats from "./Chats"
import '../App.css';

const Sidebar = () => {
  return (
    <div className="sidebar">
      <SideBarNav />
      <Chats/>
    </div>
  );
};

export default Sidebar;