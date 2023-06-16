// import { doc, onSnapshot } from "firebase/firestore";
import React, { useContext, useEffect, useRef, useState } from "react";
// import { AuthContext } from "../context/AuthContext";
// import { ChatContext } from "../context/ChatContext";
// import { db } from "../firebase";
import '../App.css';
import { checkLogin } from "../ultils/checkLogin";

const Conversations = ({ listConversations, conversation, setConversation, setCurrentFriend }) => {

    const userToken = checkLogin();

    const handleSelectConv = (conv, id) => {
        setConversation(conv.id);
        setCurrentFriend(id);
    }

    return (
        <div className="chats">
            {
                !listConversations.length?
                    (<></>)
                :
                    listConversations.map((_, id, arr) => {
                        id = arr.length - 1 - id;
                        let conv = arr[id];
                        if(conv.status !== "ONGOING") {
                            return;
                        }
                        return (
                            <div onClick={() => handleSelectConv(conv, id)} key={id} id={conv.id} className="userChat">
                                <img 
                                    className="chatImage"
                                    src="https://images.theconversation.com/files/304864/original/file-20191203-67028-qfiw3k.jpeg?ixlib=rb-1.1.0&rect=638%2C2%2C795%2C745&q=20&auto=format&w=320&fit=clip&dpr=2&usm=12&cs=strip"
                                    alt=""
                                />
                                <div className="userChatInfo">
                                    <span>Anonymous {id + 1}</span>
                                    <p>Click here to have a talk</p>
                                </div>
                            </div>
                        )
                    })
            }
        </div>
    );
};

export default Conversations;