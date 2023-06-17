import React, { useContext, useEffect, useState } from "react";
import '../App.css';
import { checkLogin } from "../ultils/checkLogin";

function ReactionList({ meme_id, currentReaction, setCurrentReaction }) {

  let [listReaction, setListReaction] = useState([]);

  const userToken = checkLogin();

  useEffect(() => {
    let ignore = false;

    async function fetchData() {
      // if(ignore) return;
      const res = await fetch(`http://localhost:8000/meme_reactions/${meme_id}`, {
        method: 'GET',
      });
      const data = await res.json();
      listReaction = [];
      for(let reaction_id of Object.keys(data)) {
        const res = await fetch(`http://localhost:8000/reactions/${reaction_id}`, {
          method: 'GET',
        });
        const reaction = await res.json();
        console.log(reaction);
        listReaction.push({
          title: reaction.title,
          count: data[reaction_id],
          image: reaction.image.data,
          id: reaction_id,
        });
      }
      listReaction.sort((a, b) => b.count - a.count);
      setListReaction(listReaction);
    }

    fetchData();
    return () => { ignore = true; }        
  }, [currentReaction]);

  const addReaction = async (reaction_id) => {
    const res = await fetch(`http://localhost:8000/meme_reactions/add/`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      body: new URLSearchParams({
        meme_id: meme_id,
        reaction_id: reaction_id,
        user_id: userToken.id,
      }),
    });
    setCurrentReaction(reaction_id);
  }

  return (
    <div className="reactionList">
      {
        (!listReaction.length)?
          <></>
        :
          listReaction.map((reaction, id) => {
            return (
              <div key={id}>
                <img 
                  className="reactionInReactionList"
                  src={`data:image/png;base64, ${reaction.image}`}
                  alt={reaction.title}
                  onClick={() => addReaction(reaction.id)}
                />
                x{reaction.count}
              </div>
            )
          })
      }
    </div>
  );
};

export default ReactionList;