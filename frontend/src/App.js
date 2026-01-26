import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import "./App.css";
import Home from './pages/Home';
import Tasks from './pages/Tasks';

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          {/* <Route path="/" element={<Home />} /> */} {/* Just for now since login/register is not set up yet */}
          <Route path="/" element={<Tasks />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;