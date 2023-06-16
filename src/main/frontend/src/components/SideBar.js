import React, { useEffect, useState } from "react";
import SideBarNav from "./SideBarNav"
import Conversations from "./Conversations"
import '../App.css';
import { checkLogin } from "../ultils/checkLogin";

const Sidebar = ({ conversation, setConversation, setCurrentFriend }) => {
  const userToken = checkLogin();

  let [listConversations, setlistConversations] = useState([]);

  useEffect(() => {
      let ignore = false;

      async function fetchData() {
          if(ignore) return;
          const res = await fetch(`http://localhost:8000/conversations/users/${userToken.id_num}`, {
              method: 'GET',
          });
          listConversations = await res.json();
          console.log(listConversations);
          setlistConversations(listConversations);
      }

      fetchData();
      return () => { ignore = true; };

  }, []);

  return (
    <div className="sidebar">
      <SideBarNav />
      <Conversations 
        setCurrentFriend={setCurrentFriend}
        listConversations={listConversations} 
        conversation={conversation}
        setConversation={setConversation}
      />
    </div>
  );
};

export default Sidebar;