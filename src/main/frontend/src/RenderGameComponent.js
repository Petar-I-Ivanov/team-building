import React from 'react';

function RenderGameComponent({ gameboard }) {
  return (
    <div>
      <h1>My Game</h1>
      {gameboard && (
        <table>
          <tbody>
            {gameboard.map((row, rowIndex) => (
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