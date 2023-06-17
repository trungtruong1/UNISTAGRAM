import React, { useContext, useEffect, useState } from "react";
import '../App.css';

const MemeContent = ({ image }) => {

  return (
    <div className="memeContent">
      <img src={`data:image/png;base64, ${image.data}`}/>
    </div>
  );
};

export default MemeContent;