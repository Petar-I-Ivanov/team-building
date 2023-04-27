import { useState } from "react";
import RenderAvailableCommandsComponent from "./RenderAvailableCommandsComponent";
import "./ActionForm.css";

export default function ActionFormComponent({ game, setGame }) {

    const [error, setError] = useState(null);
    const [showHeroPick, setShowHeroPick] = useState(false);

    async function submitAction(e) {

        e.preventDefault();

        const commandValue = showHeroPick ? 'c' : e.target.command.value;
        const heroPickValue = showHeroPick ? e.target.heroPick.value : null;

        if (isCommandHeroSwap(commandValue) && !showHeroPick) {
            setShowHeroPick(true);
            return;
        }

        if (areInputsUnavailable(commandValue, heroPickValue)) {
            setError('Invalid input. Please use from the available commands.');
            return;
        }

        const response = await fetch("http://localhost:8080/game", {
            method: 'PUT',
            headers: { 'content-type': 'application/json' },
            body: JSON.stringify({ command: commandValue, heroPick: heroPickValue, gameId: game.id })
        })

        const json = await response.json();

        if (response.status === 200) {
            setGame(json);
            setError(null);
        } else {
            setError(extractErrorMessageFromJson(json));
        }

        if (showHeroPick) {
            e.target.heroPick.value = "";
            setShowHeroPick(false);
        } else {
            e.target.command.value = "";
        }
    };

    return (
        <div className="form-container">
            <RenderAvailableCommandsComponent showHeroPick={showHeroPick} />
            
            <form className="form" onSubmit={submitAction}>

                {error && <p className="error">{error}</p>}

                {!showHeroPick && (<>
                    <label className="label" htmlFor="command">Make action:</label>
                    <input className="input" type="text" id="command" maxLength="1" autoFocus />
                </>)}
                
                {showHeroPick && (<>
                <label className="label" htmlFor="heroPick">Pick hero:</label>
                <input className="input" type="text" id="heroPick" maxLength="1" autoFocus />
                </>)}

                <input className="submit" type="submit" value="Submit" />
            </form>
        </div>
    );
}

function areInputsUnavailable(command, heroPick) {
    return isCommandUnavailable(command) || isHeroPickUnavailable(command, heroPick);
}

function isCommandUnavailable(command) {

    const availableCommands = ['w', 'd', 's', 'a', 'f', 'c'];
    return !availableCommands.includes(command);
}

function isHeroPickUnavailable(command, heroPick) {

    const availableHeroPicks = ['1', '2', '3', '4'];
    return !availableHeroPicks.includes(heroPick) && isCommandHeroSwap(command);
}

function isCommandHeroSwap(command) {
    return command === 'c';
}

function extractErrorMessageFromJson(json) {
    return json.details.split(': ')[2];
}