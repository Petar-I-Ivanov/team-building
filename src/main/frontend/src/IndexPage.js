
export default function IndexPage({setGameboard}) {

  const startNewGame = async () => {
    fetch("http://localhost:8080/game", {
        method: 'POST'
    })
    .then(response => response.json())
    .then(data => setGameboard(data));
  };

  return (
    <div>
      <h1>Welcome to My Game!</h1>
      <button onClick={startNewGame}>Start New Game</button>
    </div>
  );
}