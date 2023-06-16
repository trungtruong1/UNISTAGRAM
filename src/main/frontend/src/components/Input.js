import React, { useContext, useEffect, useRef, useState } from "react";
import '../App.css';
import { Form } from "react-bootstrap";

import SockJS from "sockjs-client"
import { Stomp } from "@stomp/stompjs"
import { checkLogin } from "../ultils/checkLogin";

const Input = ({ conversation }) => {
  const [message, setMessage] = useState();

  const userToken = checkLogin();
  
  const stomp = useRef({});
  
  useEffect(() => {
    const socket = new SockJS("http://localhost:8000/ws");
    stomp.client = Stomp.over(socket);
    stomp.client.connect({}, (frame) => {
      console.log('Connected: ' + frame);
    });
    console.log(stomp.client);
  }, []);
  
  const handleSubmit = async e => {
    e.preventDefault();
    let trimmed_message = message.trim();

    setMessage("");
    e.target[0].value = "";

    if(trimmed_message.length == 0) {
      return;
    }

    stomp.client.send("/pub/chat/sendMessage", {}, JSON.stringify({
      conversation: conversation,
      sender: userToken.id,
      content: trimmed_message,
    }));
    
  }
  
  return (
    <Form onSubmit={handleSubmit} className="input">
      <input onChange={e => setMessage(e.target.value)} type="text" placeholder="Say something I'm giving up on you..."/>
      <div className="send">
        <button>Send</button>
      </div>
    </Form>
  );
};

export default Input;