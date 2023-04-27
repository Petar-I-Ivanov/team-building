import React from 'react';
import "./RenderAvailableCommands.css"

const commandAction = ['w', 'd', 's', 'a', 'c', 'f'];
const CADescription = [
    'Forward movement',
    'Right movement',
    'Backward movement',
    'Left movement',
    'Change first hero',
    'Place bomb'
];

const heroPickAction = ['1', '2', '3', '4'];
const HPDescription = [
    'Tank hero',
    'Sniper hero',
    'Spy hero',
    'Saboteur hero'
];

function RenderAvailableCommandsComponent({ showHeroPick }) {

    const actions = showHeroPick ? heroPickAction : commandAction;
    const descriptions = showHeroPick ? HPDescription : CADescription;

  return (
    <ul className='list'>
        {actions.map((action, i) => (
            <li className='item' key={action}>
                <span className='action'>{action}</span> - <span className='description'>{descriptions[i]}</span>
            </li>
        ))}
    </ul>
  );
}

export default RenderAvailableCommandsComponent;