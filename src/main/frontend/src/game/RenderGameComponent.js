import React from 'react';
import "./RenderGame.css";

function RenderGameComponent({ game }) {
  return (
    <div>
      {game && (
        <table className='table'>
          <tbody>
            {game.gameboard.map((row, rowIndex) => (
              <tr className='row' key={rowIndex}>
                {row.map((col, colIndex) => (
                  <td className='col' key={`${rowIndex}-${colIndex}`}>{col}</td>
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