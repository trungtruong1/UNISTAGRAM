import React, { useContext, useEffect, useRef } from "react";

function Message({ message, isOwner=false }) {
  return (
    <div className={`message ${isOwner? "owner" : ""}`}>
        <div className="messageInfo">
            <p>{isOwner? "me" : "friend"}</p>
            <span>5 Years Ago</span>
        </div>
        <div className="messageContent">
            <p>{message}</p>
            <img/>
        </div>
    </div>
  );
};

export default Message;