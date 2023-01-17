import logo from './logo.svg';
import './App.css';
import React, {useState, useEffect} from 'react';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from './routes/Header';
import Footer from './routes/Footer';
import Home from './routes/Home';

function App() {

const [message, setMessage] = useState([]);

const [userName, setUserName] = useState("");
const [showLoginModal, setShowLoginModal] = useState(false);

const [userId, setUserId] = useState("");
const [userEmail, setUserEmail] = useState("");

useEffect(() => {
  const appName = "LiveRARY";
  document.title = appName;

}, []);

  return (
    <div className="App">
      <Router>
        <Header
          setUserName={setUserName}
          setShowLoginModal={setShowLoginModal}
          setUserId={setUserId}
          setUserEmail={setUserEmail}
        />
      <hr />
      <Routes>
        <Route path="/" 
        element={
          <Home
          userName={userName}
          showLoginModal={showLoginModal}
          setShowLoginModal={setShowLoginModal}
          />
        }
        />
      </Routes>
      <hr />
      <Footer />
      </Router>
      
    </div>
  );
}

export default App;
