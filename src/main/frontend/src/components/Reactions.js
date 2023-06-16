import React, { useContext, useEffect, useState, useRef } from "react";
import Message from "./Message";
import '../App.css';
import Button from '@mui/material/Button';
import ClickAwayListener from '@mui/material/ClickAwayListener';
import Grow from '@mui/material/Grow';
import Paper from '@mui/material/Paper';
import Popper from '@mui/material/Popper';
import MenuList from '@mui/material/MenuList';
import Stack from '@mui/material/Stack';
import ReactionList from "./ReactionsList";

function Reaction(props) {
  const [open, setOpen] = useState(false);
  const [reactionStats, setReactionStats] = useState({key: 0});
  const anchorRef = useRef(null);

  const handleToggle = () => {
    setOpen((prevOpen) => !prevOpen);
  };

  const handleClose = (event) => {
    let id = event.target.id;
    // if (
    //   anchorRef.current &&
    //   anchorRef.current.contains(event.target)
    // ) {
    //   return;
    // }
    if(id in reactionStats){
      reactionStats[id] += 1;
    } else{
      let newState = {
        ...reactionStats,
        [id]: 0,
      }
      setReactionStats(newState);
    }

    setOpen(false);
  };

  function handleListKeyDown(event) {
    if (event.key === 'Tab') {
      event.preventDefault();
      setOpen(false);
    } else if (event.key === 'Escape') {
      setOpen(false);
    }
  }

  // return focus to the button when we transitioned from !open -> open
  const prevOpen = useRef(open);
  useEffect(() => {
    if (prevOpen.current === true && open === false) {
      anchorRef.current.focus();
    }

    prevOpen.current = open;
  }, [open]);

  return (
    <div className="reactions">
        <Stack direction="row" spacing={2}>
          <div style={{display: "flex"}}>
            <Button
              ref={anchorRef}
              id="composition-button"
              aria-controls={open ? 'composition-menu' : undefined}
              aria-expanded={open ? 'true' : undefined}
              aria-haspopup="true"
              onClick={handleToggle}
            >
              React!
            </Button>
            <Popper
              open={open}
              anchorEl={anchorRef.current}
              role={undefined}
              placement="bottom-start"
              transition
              disablePortal
            >
              {({ TransitionProps, placement }) => (
                <Grow
                  {...TransitionProps}
                  style={{
                    transformOrigin:
                      placement === 'bottom-start' ? 'left top' : 'left bottom',
                  }}
                >
                  <Paper>
                    <ClickAwayListener onClickAway={handleClose}>
                      <MenuList
                        autoFocusItem={open}
                        id="composition-menu"
                        aria-labelledby="composition-button"
                        onKeyDown={handleListKeyDown}
                        style={{display: "flex"}}
                      >
                        <img onClick={handleClose} id="angry" className="reaction" src="https://cdn11.bigcommerce.com/s-dl22izwaan/images/stencil/1280x1280/products/1997/18883/1104_1k__93990.1627320048.jpg?c=1"/>
                        <img onClick={handleClose} id="angry" className="reaction" src="https://cdn11.bigcommerce.com/s-dl22izwaan/images/stencil/1280x1280/products/1997/18883/1104_1k__93990.1627320048.jpg?c=1"/>
                        <img onClick={handleClose} id="angry" className="reaction" src="https://cdn11.bigcommerce.com/s-dl22izwaan/images/stencil/1280x1280/products/1997/18883/1104_1k__93990.1627320048.jpg?c=1"/>
                        <img onClick={handleClose} id="angry" className="reaction" src="https://cdn11.bigcommerce.com/s-dl22izwaan/images/stencil/1280x1280/products/1997/18883/1104_1k__93990.1627320048.jpg?c=1"/>
                      </MenuList>
                    </ClickAwayListener>
                  </Paper>
                </Grow>
              )}
            </Popper>
            <ReactionList
              reactionStats={reactionStats} 
            ></ReactionList>
          </div>
        </Stack>
    </div>
  );
};

export default Reaction;