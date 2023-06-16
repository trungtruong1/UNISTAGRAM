import React, { useContext, useEffect, useState } from "react";
import '../App.css';
import Title from "./Title";
import Reaction from "./Reactions";
import MemeContent from "./MemeContent";


const Meme = () => {

  return (
    <div className="meme">
      <Title/>
      <MemeContent/>
      <Reaction/>
    </div>
  );
};

export default Meme;