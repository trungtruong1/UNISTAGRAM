import React, { useContext, useEffect, useState } from "react";
import '../App.css';

function ReactionList({ meme_id }) {

  let [listReaction, setListReaction] = useState([]);

  // const userToken = checkLogin();

  useEffect(() => {
    let ignore = false;

    async function fetchData() {
      if(ignore) return;
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
  }, []);

  return (
    <div className="reactionList">
      {
        (!listReaction.length)?
          <></>
        :
          listReaction.map((reaction, id) => {
            return (
              <img 
                key={id}
                className="reactionInReactionList"
                src={`data:image/png;base64, ${reaction.image}`}
                alt={reaction.title}
              />
            )
          })
      }
    </div>
  );
};

export default ReactionList;