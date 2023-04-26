import { useState } from "react";
import IndexPage from "./IndexPage";
import GamePage from "./GamePage";

function App() {

  const [game, setGame] = useState(
  {
    id: 0,
    status: "ONGOING",
    gameboard: [...Array(15)].map(e => Array(15))
  });

  console.log(game);

  return (
    <div>
      {game.id === 0 && <IndexPage setGame={setGame} />}
      {game.id !== 0 && <GamePage game={game} setGame={setGame} />}
    </div>
  );
}

export default App;
