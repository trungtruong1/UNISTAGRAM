import React, { useContext, useEffect, useState } from "react";
import '../App.css';
import Title from "./Title";
import Reaction from "./Reactions";
import MemeContent from "./MemeContent";


const Meme = ({ title, image, author, timeStamp }) => {

  return (
    <div className="meme">
      <Title title={title}/>
      <MemeContent image={image}/>
      <Reaction/>
    </div>
  );
};

export default Meme;