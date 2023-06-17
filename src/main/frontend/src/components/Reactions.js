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
import { checkLogin } from "../ultils/checkLogin";

function Reaction({ meme_id, currentReaction, setCurrentReaction }) {
  const [open, setOpen] = useState(false);

  const anchorRef = useRef(null);

  const [listReaction, setListReaction] = useState([]);

  const userToken = checkLogin();

  console.log("currentReaction", currentReaction);

  useEffect(() => {
    let ignore = false;

    async function fetchData() {
      if(ignore) return;
      const res = await fetch(`http://localhost:8080/reactions/user/${userToken.username}`, {
        method: 'GET',
      });
      const data = await res.json();
      setListReaction(data);
    }

    fetchData();
    return () => { ignore = true; }        
  }, []);

  const handleToggle = () => {
    setOpen((prevOpen) => !prevOpen);
  };

  const handleClose = async (event, reaction_id) => {
    setOpen(false);

    if(reaction_id !== null) {
      if(reaction_id === currentReaction) {
        const res = await fetch(`http://localhost:8080/meme_reactions/del`, {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          body: new URLSearchParams({
            meme_id: meme_id,
            reaction_id: reaction_id,
            user_id: userToken.id,
          }),
        });
        setCurrentReaction("");
        return;
      }
      const res = await fetch(`http://localhost:8080/meme_reactions/add/`, {
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
      if(res.ok) setCurrentReaction(reaction_id);
    }

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
              style={{float: "right"}}
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
                    <ClickAwayListener onClickAway={(e) => handleClose(e, null)}>
                      <MenuList
                        autoFocusItem={open}
                        id="composition-menu"
                        aria-labelledby="composition-button"
                        onKeyDown={handleListKeyDown}
                        style={{display: "flex"}}
                      >
                        {
                            !listReaction.length?
                                (<></>)
                            :
                                listReaction.map((reaction, id, arr) => {
                                    return (
                                      <img 
                                        style={
                                          (reaction.id === currentReaction)? 
                                            {
                                              width: "50px",
                                              height: "50px",
                                              border:  "2px solid red",
                                              boxShadow: "0 0 10px #333",
                                            }
                                          : 
                                            null
                                        }
                                        key={id}
                                        onClick={(e) => handleClose(e, reaction.id)} 
                                        id={reaction.id} 
                                        className="reaction" 
                                        src={`data:image/png;base64, ${reaction.image.data}`}
                                        alt={reaction.title}
                                      />
                                    )
                                })
                        }
                      </MenuList>
                    </ClickAwayListener>
                  </Paper>
                </Grow>
              )}
            </Popper>
            <ReactionList
              meme_id={meme_id} 
              currentReaction={currentReaction}
              setCurrentReaction={setCurrentReaction}
            ></ReactionList>
          </div>
        </Stack>
    </div>
  );
};

export default Reaction;