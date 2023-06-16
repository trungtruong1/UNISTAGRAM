import React from "react";
import SideBarNav from "./SideBarNav"
import Conversations from "./Conversations"
import '../App.css';

const Sidebar = () => {
  return (
    <div className="sidebar">
      <SideBarNav />
      <Conversations/>
    </div>
  );
};

export default Sidebar;