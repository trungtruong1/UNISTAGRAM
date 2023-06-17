import React, { useContext, useEffect, useState } from "react";
import '../App.css';
import Title from "./Title";
import Reaction from "./Reactions";
import MemeContent from "./MemeContent";
import { checkLogin } from "../ultils/checkLogin";


const Meme = ({ id, title, image, author, timeStamp }) => {

  const userToken = checkLogin();
  let [currentReaction, setCurrentReaction] = useState("");

  useEffect(() => {
    let ignore = false;

    async function fetchData() {
      // if(ignore) return;
      const res = await fetch(`http://localhost:8000/meme_reactions?user_id=${userToken.id}&meme_id=${id}`, {
        method: 'GET',
      });
      if(res.ok) {
        const data = await res.json();
        console.log(data);
        setCurrentReaction(data.reaction_id);
      }
    }

    fetchData();
    return () => { ignore = true; }        
  }, []);

  return (
    <div className="meme">
      <Title title={title}/>
      <MemeContent image={image}/>
      <Reaction meme_id={id} currentReaction={currentReaction} setCurrentReaction={setCurrentReaction}/>
    </div>
  );
};

export default Meme;