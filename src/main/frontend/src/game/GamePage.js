import React from 'react';
import RenderGameComponent from './RenderGameComponent';
import ActionFormComponent from '../form/ActionFormComponent';

function GamePage({ game, setGame }) {
  return (
    <div>
      <RenderGameComponent game={game} />
      <ActionFormComponent game={game} setGame={setGame} />
    </div>
  );
}

export default GamePage;