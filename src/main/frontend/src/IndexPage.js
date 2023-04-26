
export default function IndexPage({setGame}) {

  const startNewGame = async () => {
    fetch("http://localhost:8080/game", {
        method: 'POST'
    })
    .then(response => response.json())
    .then(json => setGame(json));
  };

  return (
    <div>
      <h1>Welcome to My Game!</h1>
      <button onClick={startNewGame}>Start New Game</button>
    </div>
  );
}