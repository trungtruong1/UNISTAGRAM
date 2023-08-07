import React, { useContext, useEffect, useState } from "react";
import Message from "./Message";
import '../App.css';

const Title = ({ title }) => {

  return (
    <div className="title">
        <span className="caption">{title}</span>
    </div>
  );
};

export default Title;