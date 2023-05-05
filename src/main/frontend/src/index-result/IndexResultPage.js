import { useState } from "react";
import "./IndexResultPage.css";

export default function IndexPage({game, setGame}) {

  const [error, setError] = useState(null);

  const message = (game.id !== "")
    ? "You " + game.status + "!"
    : "Welcome to TEAM BUILDING game!";

  const startNewGame = async () => {
    
    const response = await fetch("http://localhost:8080/game", {
        method: 'POST'
    });

    const json = await response.json();

    if (response.status === 200) {
      setGame(json);
      setError(null);
    }

    else {
      setError(json.details.split(': ')[2]);
      return
    }
  };

  return (
    <div className="index-container">
      <h1 className="title">{message}</h1>
      <button className="button" onClick={startNewGame}>Start New Game</button>
      {error && (<p className="error">{error}</p>)}
    </div>
  );
}