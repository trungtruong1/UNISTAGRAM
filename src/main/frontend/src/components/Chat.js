import React, { useContext, useEffect } from "react";
// import Cam from "../img/cam.png";
// import Add from "../img/add.png";
// import More from "../img/more.png";
import Conversation from "./Conversation";
import Input from "./Input";
// import { ChatContext } from "../context/ChatContext";
import '../App.css';
import { checkLogin } from "../ultils/checkLogin";

const Chat = () => {
//   const { data } = useContext(ChatContext);

  const token = checkLogin();

  return (
    <div className="chat">
      <div className="chatInfo">
        {/* username */}
        <span>{token.username}</span>
        <div className="chatIcons">
          {/* <img src={Cam} alt="" />
          <img src={Add} alt="" />
          <img src={More} alt="" /> */}
        </div>
      </div>
      <Conversation/>
      <Input/>
    </div>
  );
};

export default Chat;