import React, { useContext } from "react";
// import Cam from "../img/cam.png";
// import Add from "../img/add.png";
// import More from "../img/more.png";
import Conversation from "./Conversation";
import Input from "./Input";
// import { ChatContext } from "../context/ChatContext";
import '../App.css';

const Chat = () => {
//   const { data } = useContext(ChatContext);

  return (
    <div className="chat">
      <div className="chatInfo">
        <span>Mr. Bean</span>
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