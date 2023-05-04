import React from 'react';
import "./RenderGame.css";

function RenderGameComponent({ game }) {

  const imageNames = ['X', 'S', 'M', 'L', 'G', 'B', '1', '2', '3', '4'];
  const images = imageNames.map(imageName => require('../assets/images/' + imageName + '.jpg'));

  return (
    <div className='game-render-container'>
      {game && (
        <table className='table'>
          <tbody>
            {game.gameboard.map((row, rowIndex) => (
              <tr className='row' key={rowIndex}>
                {row.map((col, colIndex) => (
                  <td className='col' key={`${rowIndex}-${colIndex}`}>
                    <img src={images[imageNames.indexOf(col)]} alt={col} width="50" height="50"></img>
                  </td>
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