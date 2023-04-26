import { useState } from "react";
import RenderAvailableCommandsComponent from "./RenderAvailableCommandsComponent";

export default function ActionFormComponent({ setGameboard }) {

    const availableCommands = ['w', 'd', 's', 'a', 'f', 'c'];
    const availableHeroPicks = ['1', '2', '3', '4'];

    const [message, setMessage] = useState(null);
    const [showHeroPick, setShowHeroPick] = useState(false);

    async function submitAction(e) {

        e.preventDefault();

        const commandValue = showHeroPick ? 'c' : e.target.command.value;
        let heroPickValue = showHeroPick ? e.target.heroPick.value : null;

        if (commandValue === 'c' && !showHeroPick) {
            setShowHeroPick(true);
            return;
        }

        if (!availableCommands.includes(commandValue) || (commandValue === 'c' && !availableHeroPicks.includes(heroPickValue))) {
            setMessage('Invalid input. Please use from the available commands.');
            return;
        }

        const response = await fetch("http://localhost:8080/game", {
            method: 'PUT',
            headers: { 'content-type': 'application/json' },
            body: JSON.stringify({ command: commandValue, heroPick: heroPickValue })
        })

        const json = await response.json();

        if (response.status === 200) {
            setGameboard(json);
        }
        
        else {
            console.log(json.details.split(': ')[2]);
            setMessage(json.details.split(': ')[2]);
            return;
        }

        if (commandValue === 'c' && showHeroPick) {
            e.target.heroPick.value = "";
            setShowHeroPick(false);
        }

        e.target.command.value = "";
        setMessage(null);
    };

    return (
        <div>
            <RenderAvailableCommandsComponent showHeroPick={showHeroPick} />

            <form onSubmit={submitAction}>

                {!showHeroPick && (<>
                    <label>Make action:</label>
                    <input type="text" id="command" maxLength="1" autoFocus />
                </>)}
                
                {showHeroPick && (<>
                <label>Pick hero:</label>
                <input type="text" id="heroPick" maxLength="1" autoFocus />
                </>)}

                <input type="submit" value="Submit" />

                {message && <p>{message}</p>}
            </form>
        </div>
    );
}