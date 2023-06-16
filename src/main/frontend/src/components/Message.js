import React, { useContext, useEffect, useRef } from "react";

const Message = ({ message }) => {
  return (
    <div className="message owner">
        <div className="messageInfo">
            <img className="messageImg" src="https://images.theconversation.com/files/304864/original/file-20191203-67028-qfiw3k.jpeg?ixlib=rb-1.1.0&rect=638%2C2%2C795%2C745&q=20&auto=format&w=320&fit=clip&dpr=2&usm=12&cs=strip"/>
            <span>5 Years Ago</span>
        </div>
        <div className="messageContent">
            <p>Hello</p>
            <img/>
        </div>
    </div>
  );
};

export default Message;