import React, { useContext, useEffect, useRef, useState } from "react";
import Message from "./Message";
import '../App.css';

import SockJS from "sockjs-client"
import { Stomp } from "@stomp/stompjs"
import { checkLogin } from "../ultils/checkLogin";

const Conversation = ({ conversation }) => {

  const userToken = checkLogin();
  // const roomId = "648bd875544b5a7b3efd3c2f";

  let [listMessages, setListMessages] = useState([]);
  
  const stomp = useRef({});

  useEffect(() => {
    let ignore = false || !conversation.length;

    async function fetchData() {
      if(ignore) return;
      const res = await fetch(`http://localhost:8000/messages/${conversation}`, {
        method: 'GET',
      });
      listMessages = await res.json();
      setListMessages(listMessages);
    }

    fetchData();
    return () => { ignore = true; };

  }, [conversation]);
  
  useEffect(() => {
    const socket = new SockJS("http://localhost:8000/ws");
    if(stomp.client) {
      stomp.client.disconnect((frame) => {});
    }
    stomp.client = Stomp.over(socket);
    stomp.client.connect({}, (frame) => {
      console.log('Connected: ' + frame);
      stomp.client.subscribe('/sub/chat/room/' + conversation, function (greeting) {
        let msg = JSON.parse(greeting.body)
        listMessages = [...listMessages, msg];
        setListMessages(listMessages);
      });
    });
    console.log(stomp.client);
  }, [conversation]);

  const messagesEndRef = useRef(null);
  const scrollToBottom = () => {
    messagesEndRef.current.scrollIntoView({ });
  };

  useEffect(scrollToBottom, [listMessages]);

  return (
    <div className="messages">
      {
        listMessages.map((msg, id) => {
          return (
            <Message key={id} message={msg.content} isOwner={msg.sender === userToken.id}/>
          )
        })
      }
      <div ref={messagesEndRef} />
    </div>
  );
};

export default Conversation;