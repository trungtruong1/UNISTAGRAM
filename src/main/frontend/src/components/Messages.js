import React, { useContext, useEffect, useState } from "react";
import Message from "./Message";
import '../App.css';

const Messages = () => {

  return (
    <div className="messages">
        <Message/>
        <Message/>
        <Message/>
        <Message/>
        <Message/>
    </div>
  );
};

export default Messages;