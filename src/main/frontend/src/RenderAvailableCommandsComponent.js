import React from 'react';

const commandAction = ['w', 'd', 's', 'a', 'c', 'f'];
const CADescription = [
    'Forward movement',
    'Right movement',
    'Backward movement',
    'Left movement',
    'Change first hero',
    'Place bomb (only possible if \'4\' is first in order)'
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
    <ul>
        {actions.map((action, i) => (
            <li key={action}>
                {action} - {descriptions[i]}
            </li>
        ))}
    </ul>
  );
}

export default RenderAvailableCommandsComponent;