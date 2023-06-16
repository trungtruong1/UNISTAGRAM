import React, { useContext } from 'react'
// import {signOut} from "firebase/auth"
// import { auth } from '../firebase'
// import { AuthContext } from '../context/AuthContext'
import '../App.css';

const SideBarNav = () => {
//   const {currentUser} = useContext(AuthContext)

  return (
    <div className='sidebarnav'>
      <span className="logo">HeartSync</span>
      <div className="user">
      </div>
    </div>
  )
}

export default SideBarNav