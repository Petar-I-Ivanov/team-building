import React from 'react';
import RenderGameComponent from './RenderGameComponent';
import ActionFormComponent from './ActionFormComponent';

function GamePage({ gameboard, setGameboard }) {
  return (
    <div>
      <RenderGameComponent gameboard={gameboard} />
      <ActionFormComponent setGameboard={setGameboard} />
    </div>
  );
}

export default GamePage;