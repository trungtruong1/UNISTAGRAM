import React, { useContext, useEffect, useState } from "react";
import '../App.css';
import Title from "./Title";
import Reaction from "./Reactions";
import MemeContent from "./MemeContent";


const Meme = ({ id, title, image, author, timeStamp }) => {

  return (
    <div className="meme">
      <Title title={title}/>
      <MemeContent image={image}/>
      <Reaction meme_id={id}/>
    </div>
  );
};

export default Meme;