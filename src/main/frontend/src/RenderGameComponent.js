import React from 'react';

function RenderGameComponent({ game }) {
  return (
    <div>
      <h1>My Game</h1>
      {game && (
        <table>
          <tbody>
            {game.gameboard.map((row, rowIndex) => (
              <tr key={rowIndex}>
                {row.map((cell, cellIndex) => (
                  <td key={`${rowIndex}-${cellIndex}`}>{cell}</td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default RenderGameComponent;