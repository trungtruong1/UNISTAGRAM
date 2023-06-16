import React, { useContext, useEffect, useState } from "react";
import Message from "./Message";
import '../App.css';

const Conversation = () => {

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

export default Conversation;