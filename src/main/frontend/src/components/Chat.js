import React, { useContext, useEffect, useState } from "react";
import Conversation from "./Conversation";
import IconButton from '@mui/material/IconButton';
import DeleteIcon from '@mui/icons-material/Delete';
import Input from "./Input";
import '../App.css';
import { checkLogin } from "../ultils/checkLogin";


const Chat = ({ conversation, currentFriend }) => {

  const userToken = checkLogin();

  const handleClick = async () => {
    let sure = window.confirm("Are you sure about that?");
    if(sure) {
      const res = await fetch(`http://localhost:8000/conversations/end/${conversation}`, {
        method: 'PUT',
      });
      window.location.reload();
    }
  }

  return (
    <div className="chat">
      <div className="chatInfo">
        <span>{(currentFriend === -1) ? "Please select" : "Anonymous " + (currentFriend+1)}</span>
        <div className="chatIcons">
          <IconButton onClick={() => handleClick()} aria-label="delete">
            <DeleteIcon/>
          </IconButton>
        </div>
      </div>
      <Conversation conversation={conversation}/>
      <Input conversation={conversation}/>
    </div>
  );
};

export default Chat;