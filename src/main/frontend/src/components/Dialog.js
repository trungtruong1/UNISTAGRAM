import React, { PureComponent } from "react";
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, Slide } from "@mui/material";
import { checkLogin } from "../ultils/checkLogin";
import { Form } from "react-bootstrap";

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const styles = {
  dialogPaper: {
    minHeight: "80vh",
    maxHeight: "80vh"
  }
};

class AlertDialogSlide extends PureComponent {
  state = {
    open: false,
    src: null,
    image: null,
    imageUrl: null,
    title: "",
    is_meme: false,
    is_reaction: false,
  };

  userToken = checkLogin();

  onTitleChange = (e) => {
    this.setState({ title: e.target.value});
  };

  handleClickOpen = (meme) => {
    if(meme) this.setState({ open: true, is_meme: true });
    else this.setState({ open: true, is_reaction: true });
  };

  handleClose = () => {
    this.setState({ open: false, is_meme: false, is_reaction: false });
  };

  onSelectFile = e => {
    if (e.target.files && e.target.files.length > 0) {
      const reader = new FileReader();
      reader.addEventListener("load", () =>
        this.setState({ src: reader.result })
      );
      reader.readAsDataURL(e.target.files[0]);
    }
    this.setState({ image: e.target.files[0] })
    this.setState({ imageUrl: URL.createObjectURL(e.target.files[0]) });
  };

  onImageLoaded = image => {
    this.imageRef = image;
  };

  handleSubmit = async (e) => {
    e.preventDefault();

    let formData = new FormData();

    formData.append("title", this.state.title);
    formData.append("image", this.state.image);
    formData.append("author", this.userToken.username);

    const res = await fetch(`http://localhost:8080/${this.state.is_meme? "memes" : "reactions"}`, {
      method: 'POST',
      headers: {
        // 'Content-Type': 'multipart/form-data'
      },
      body: formData,
    });

    if(!res.ok) {
      window.alert("Something went wrong while uploading!");
      return;
    }

    window.alert("Uploaded successfully!");

    window.location.reload();
        

  }

  render() {
    let fileInput = React.createRef();
    const { src, imageUrl } = this.state;
    return (
      <div style={{width: "100%"}}>
        <div className="buttons_2">
          <Button
            variant="outlined"
            color="primary"
            onClick={() => this.handleClickOpen(false)}
          >
            Upload your reactions!
          </Button>
          <Button
            variant="outlined"
            color="primary"
            onClick={() => this.handleClickOpen(true)}
          >
            Upload a meme!
          </Button>
        </div>
        <Dialog
          open={this.state.open}
          TransitionComponent={Transition}
          keepMounted
          onClose={this.TransitionComponenthandleClose}
        >
          <Form onSubmit={this.handleSubmit}>
            <DialogTitle>
              {
                (this.state.is_meme)? 
                  <input onChange={this.onTitleChange} placeholder="Your meme's title" /> 
                : 
                <input onChange={this.onTitleChange} placeholder="Your reaction's name?" /> 
              }
            </DialogTitle>
            <DialogContent>
              <input
                type="file"
                ref={fileInput}
                style={{ display: "none" }}
                onChange={this.onSelectFile}
                multiple
              />
              {imageUrl && <img src={imageUrl} alt="Uploaded Meme" style={{ maxWidth: "100%" }} />}
            </DialogContent>
            <DialogActions>
              <Button onClick={this.handleClose} color="primary">
                close
              </Button>
              <Button onClick={() => fileInput.current.click()}>
                {src === null ? "Upload Photo" : "Change Photo"}
              </Button>
              {src !== null ? (
                <Button type="submit" >
                  Save Photo
                </Button>
              ) : null}
            </DialogActions>
          </Form>
        </Dialog>
      </div>
    );
  }
}

export default AlertDialogSlide;
