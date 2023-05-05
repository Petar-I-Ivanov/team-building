import { useState } from "react";
import IndexResultPage from "./index-result/IndexResultPage";
import GamePage from "./game/GamePage";
import "./App.css";

function App() {

  const [game, setGame] = useState(
  {
    id: "",
    turn: 0,
    status: "ONGOING",
    gameboard: [...Array(15)].map(e => Array(15))
  });

  return (
    <div className="container">
      {isGameNullOrNotOngoing(game) && <IndexResultPage game={game} setGame={setGame} />}
      {isGameNotNullAndOngoing(game) && <GamePage game={game} setGame={setGame} />}
    </div>
  );
}

function isGameNullOrNotOngoing(game) {
  return game.id === "" || game.status !== "ONGOING";
}

function isGameNotNullAndOngoing(game) {
  return game.id !== "" && game.status === "ONGOING";
}

export default App;
