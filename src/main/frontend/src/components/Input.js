import React, { useContext, useState } from "react";
import '../App.css';

const Input = () => {
  
  return (
    <div className="input">
      <input type="text" placeholder="Say something I'm giving up on you..."/>
      <div className="send">
        <img></img>
        <input type="file" style={{display: "none"}} id="file"/>
        <label htmlFor="file">
            <img src="" alt=""/>
        </label>
        <button>Send</button>
      </div>
    </div>
  );
};

export default Input;