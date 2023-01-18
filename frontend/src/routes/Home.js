import { useEffect, useState } from "react";
import Login from "./Login.js";
import styles from "./Home.css";

function Home({showLoginModal, setShowLoginModal}){
    return (
        <div className={styles.home}>
        {showLoginModal === true ? (
          <Login showLoginModal={true} setShowLoginModal={setShowLoginModal} />
        ) : null}
      
      <div>
       <h1>HOME</h1>
      </div>
      </div>
    );
}

export default Home;