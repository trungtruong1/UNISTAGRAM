import React, { PureComponent } from "react";
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, Slide } from "@mui/material";
import ReactCrop from "react-image-crop";
import "react-image-crop/dist/ReactCrop.css";
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
    crop: {
      unit: "%",
      width: 30,
      aspect: 16 / 9
    },
    meme_title: "",
  };

  userToken = checkLogin();

  onTitleChange = (e) => {
    this.setState({ meme_title: e.target.value});
  };

  handleClickOpen = () => {
    this.setState({ open: true });
  };

  handleClose = () => {
    this.setState({ open: false });
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
  };

  // If you setState the crop in here you should return false.
  onImageLoaded = image => {
    this.imageRef = image;
  };

  onCropComplete = crop => {
    this.makeClientCrop(crop);
  };

  onCropChange = (crop, percentCrop) => {
    // You could also use percentCrop:
    // this.setState({ crop: percentCrop });
    this.setState({ crop });
  };

  handleSubmitMeme = async (e) => {
    e.preventDefault();

    let formData = new FormData();

    formData.append("title", this.state.meme_title);
    formData.append("image", this.state.image);
    formData.append("author", this.userToken.username);

    const res = await fetch('http://localhost:8000/memes', {
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

  async makeClientCrop(crop) {
    if (this.imageRef && crop.width && crop.height) {
      const croppedImageUrl = await this.getCroppedImg(
        this.imageRef,
        crop,
        "newFile.jpeg"
      );
      this.setState({ croppedImageUrl });
    }
  }

  getCroppedImg(image, crop, fileName) {
    const canvas = document.createElement("canvas");
    const scaleX = image.naturalWidth / image.width;
    const scaleY = image.naturalHeight / image.height;
    canvas.width = crop.width;
    canvas.height = crop.height;
    const ctx = canvas.getContext("2d");

    ctx.drawImage(
      image,
      crop.x * scaleX,
      crop.y * scaleY,
      crop.width * scaleX,
      crop.height * scaleY,
      0,
      0,
      crop.width,
      crop.height
    );

    return new Promise((resolve, reject) => {
      canvas.toBlob(blob => {
        if (!blob) {
          //reject(new Error('Canvas is empty'));
          console.error("Canvas is empty");
          return;
        }
        blob.name = fileName;
        window.URL.revokeObjectURL(this.fileUrl);
        this.fileUrl = window.URL.createObjectURL(blob);
        resolve(this.fileUrl);
      }, "image/jpeg");
    });
  }

  render() {
    let fileInput = React.createRef();
    const { crop, croppedImageUrl, src } = this.state;
    return (
      <div>
        <Button
          variant="outlined"
          color="primary"
          onClick={this.handleClickOpen}
        >
          Add Memes
        </Button>
        <Dialog
          open={this.state.open}
          TransitionComponent={Transition}
          keepMounted
          onClose={this.TransitionComponenthandleClose}
        >
          <Form onSubmit={this.handleSubmitMeme}>
            <DialogTitle>
              <input 
                onChange={this.onTitleChange}
              />
            </DialogTitle>
            <DialogContent>
              <input
                type="file"
                ref={fileInput}
                style={{ display: "none" }}
                onChange={this.onSelectFile}
                multiple
              />

              {src && (
                <ReactCrop
                  src={src}
                  crop={crop}
                  onImageLoaded={this.onImageLoaded}
                  onComplete={this.onCropComplete}
                  onChange={this.onCropChange}
                />
              )}
              {croppedImageUrl && (
                <img
                  alt="Crop"
                  style={{ maxWidth: "100%" }}
                  src={croppedImageUrl}
                />
              )}
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
