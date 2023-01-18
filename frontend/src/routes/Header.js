import React from 'react';
import { useEffect, useState } from "react";
import "./Header.css";
import { InputGroup } from "react-bootstrap";
import { FormControl } from "react-bootstrap";
import { Button } from "react-bootstrap";
import { Dropdown } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.min.js";
import axios from "axios";

function Header(props){
  console.log("Header File Enter==");
    const [userName, setUserName] = useState("");
    const [userId, setUserId] = useState("");
    const [userEmail, setUserEmail] = useState("");

    const imgStyle = {
        height: 35,
        width: 35,
      };

    const getUserInfo = () => {
    axios
        .get("http://localhost:8080/`api/user`")
        .then((response) => {
        setUserName(response.data.name);
        props.setUserName(response.data.name);

        setUserId(response.data.id);
        props.setUserId(response.data.id);

        setUserEmail(response.data);
        props.setUserEmail(response.data.email);

        })
        .catch((error) => {});
    };

   //로그인 버튼
   const loginBtnClicked = (event) => {
    event.preventDefault();
    props.setShowLoginModal(true);
    
  };

  const onChangeSearchBar = (event) => {
      console.log(event.target.value);
      const searchText = event.target.value;
      //setSearchName(searchText);
  };
  
  useEffect(() => {
    getUserInfo();
  }, []);

    return (
        <div className="header">
        <div className="parent}">
            <div className="left_div">
                <a href="/" className="header_title">
                  <img src="/LiveRARY.png" />
                </a>
            </div>
            <div className="middle_div">
              <div className="btn1">
                <InputGroup className="mb-3" id="input">
                    <FormControl 
                    placeholder="Search Word here...."
                    aria-label="Search Word here...."
                    aria-describedby="basic-addon2"
                    onChange={onChangeSearchBar}
                    />
                <Button variant="dark" className="searchBtn" id="button-search" size="lg" active
                href="" 
                >
                Search
                </Button>
                </InputGroup>
              </div>
                <div className="btnLogin">
                {userName === "" ? (
                        <Button variant="dark" className="searchBtn" id="button-search" size="lg" active
                        href="/signin" onClick={loginBtnClicked} >
                        로그인dd
                        </Button>
                       ) : (
                         <Dropdown>
                           <Dropdown.Toggle variant="white" id="dropdown-basic">
                             <img
                               className={"rounded-circle"}
                               style={imgStyle}
                               src="logo192.png"
                             />
                             &nbsp;
                             <span>Hi there! {userName}</span>
                             &nbsp;
                           </Dropdown.Toggle>

                           <Dropdown.Menu>
                             <Dropdown.Item href="/logout">Logout</Dropdown.Item>
                           </Dropdown.Menu>
                         </Dropdown>
                )}
           
                </div>
            </div>
        </div>
        
    </div>
    );
}

export default Header;