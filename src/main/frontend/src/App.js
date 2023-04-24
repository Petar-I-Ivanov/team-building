import { useState } from "react";
import IndexPage from "./IndexPage";
import GamePage from "./GamePage";

function App() {

  const [gameboard, setGameboard] = useState(null);

  return (
    <div>
      {!gameboard && <IndexPage setGameboard={setGameboard} />}
      {gameboard && <GamePage gameboard={gameboard} setGameboard={setGameboard} />}
    </div>
  );
}

export default App;
