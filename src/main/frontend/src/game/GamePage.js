import React from 'react';
import RenderGameComponent from './RenderGameComponent';
import ActionFormComponent from '../form/ActionFormComponent';
import "./GamePage.css";

function GamePage({ game, setGame }) {
  return (
    <div className='game-container'>
      <RenderGameComponent game={game} />
      <ActionFormComponent game={game} setGame={setGame} />
    </div>
  );
}

export default GamePage;