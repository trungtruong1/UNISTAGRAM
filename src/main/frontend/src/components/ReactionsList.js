import React, { useContext, useEffect, useState } from "react";
import '../App.css';

function ReactionList(props) {
  const { reactionStats } = props;

  return (
    <div className="reactionList">
      <img className="reactionInReactionList" src="https://cdn11.bigcommerce.com/s-dl22izwaan/images/stencil/1280x1280/products/1997/18883/1104_1k__93990.1627320048.jpg?c=1"/>
    </div>
  );
};

export default ReactionList;